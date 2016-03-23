package org.asanderson.muni.worker;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.util.concurrent.RateLimiter;
import org.asanderson.muni.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asanderson on 3/22/16.
 */
public class CsvOutputRunner implements Runnable {

    // Generate one permit every 1 min
    private final RateLimiter writeLimiter = RateLimiter.create(0.0166666666);

    // Generate one permit every 30 min
    private final RateLimiter fileLimiter = RateLimiter.create(0.000555555555);

    private boolean updating = false;
    private Thread thread;

    private MuniData muniData;
    private String outputPath;

    private CSVWriter routeWriter;
    private CSVWriter directionWriter;
    private CSVWriter stopWriter;
    private CSVWriter predictionWriter;

    public CsvOutputRunner(MuniData muniData, String outputPath) {
        this.muniData = muniData;
        this.outputPath = outputPath;
    }

    public synchronized void start() {
        updating = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        updating = false;
        thread.interrupt();
    }

    public synchronized void run() {
        this.initializeWriters();
        while (updating) {
            writeLimiter.acquire(1);
            if (fileLimiter.tryAcquire(1)) {
                writeBuffer(true);
                flushWriters();
            } else {
                writeBuffer(false);
            }
        }
    }

    private void writeBuffer(boolean writeRoutes) {
        String time = LocalDateTime.now().toString();

        try {
            List<String[]> parsedRoutes = new ArrayList<>();
            List<String[]> parsedDirections = new ArrayList<>();
            List<String[]> parsedStops = new ArrayList<>();
            List<String[]> parsedPredictions = new ArrayList<>();

            for (String routeId : muniData.getRouteIds()) {
                // Routes
                Route route = muniData.getRoutes().get(routeId);
                parsedRoutes.add(new String[]{
                        route.getId(),
                        route.getTitle(),
                        route.getColor(),
                        route.getOppositeColor(),
                        time
                });

                // Directions
                for (String directionId : route.getDirectionIds()) {
                    Direction direction = route.getDirection(directionId);
                    parsedDirections.add(new String[]{
                            route.getId(),
                            direction.getDirectionTitle(),
                            direction.getDirectionBound(),
                            time
                    });

                    // Stops
                    for (String stopId : direction.getStopIds()) {
                        Stop stop = direction.getStops().get(stopId);
                        parsedStops.add(new String[]{
                                direction.getDirectionTitle(),
                                stop.getId(),
                                stop.getTitle(),
                                stop.getLat(),
                                stop.getLon(),
                                time
                        });

                        // Predictions
                        for (Prediction prediction : stop.getPredictions()) {
                            if(prediction.getIsEnroute().equals("true")) {
                                parsedPredictions.add(new String[]{
                                        stop.getId(),
                                        prediction.getMinutes(),
                                        prediction.getTripId(),
                                        prediction.getVehicleId(),
                                        time
                                });
                            }
                        }
                    }
                }
            }

            if (parsedRoutes.size() > 0 && writeRoutes) {
                routeWriter.writeAll(parsedRoutes);
                routeWriter.close();
            }

            if (parsedDirections.size() > 0 && writeRoutes) {
                directionWriter.writeAll(parsedDirections);
                directionWriter.close();
            }

            if (parsedStops.size() > 0 && writeRoutes) {
                stopWriter.writeAll(parsedStops);
                stopWriter.close();
            }

            if (parsedPredictions.size() > 0) {
                predictionWriter.writeAll(parsedPredictions);
                predictionWriter.close();
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
    }

    private void initializeWriters() {
        String time = new Long(System.currentTimeMillis()).toString();
        String filename = outputPath + time;

        try {
            // Routes
            File routeFile = new File(filename + "-routes.csv");
            routeWriter = new CSVWriter(new BufferedWriter(new FileWriter(routeFile)));

            // Directions
            File directionFile = new File(filename + "-directions.csv");
            directionWriter = new CSVWriter(new BufferedWriter(new FileWriter(directionFile)));

            // Stops
            File stopFile = new File(filename + "-stops.csv");
            stopWriter = new CSVWriter(new BufferedWriter(new FileWriter(stopFile)));

            // Predictions
            File predictionFile = new File(filename + "-predictions.csv");
            predictionWriter = new CSVWriter(new BufferedWriter(new FileWriter(predictionFile), 524288));
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
    }

    private void flushWriters() {
        try {
            routeWriter.flush();
            routeWriter.close();
            directionWriter.flush();
            directionWriter.close();
            stopWriter.flush();
            stopWriter.close();
            predictionWriter.flush();
            predictionWriter.close();
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        initializeWriters();
    }
}
