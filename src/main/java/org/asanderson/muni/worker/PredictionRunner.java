package org.asanderson.muni.worker;

import com.google.common.util.concurrent.RateLimiter;
import org.asanderson.muni.model.MuniData;

/**
 * Created by asanderson on 3/19/16.
 */
public class PredictionRunner implements Runnable {

    // Generate one permit every 30 min
    private final RateLimiter routeLimiter = RateLimiter.create(0.000555555555);

    // Generate one permit every 30 sec
    private final Double BYTES_PER_SECOND = 90000.0;
    private final RateLimiter predictionLimiter = RateLimiter.create(BYTES_PER_SECOND);

    private boolean updating = false;
    private Thread thread;

    private MuniDataUpdater muniDataUpdater;

    public PredictionRunner(MuniData muniData) {
        this.muniDataUpdater = new MuniDataUpdater(muniData);
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
        while(updating) {
            gatherRoutes();
            gatherPredictions();
        }
    }

    private void gatherRoutes() {
        if(routeLimiter.tryAcquire(1)) {
            muniDataUpdater.gatherRoutes();
        }
    }

    private void gatherPredictions() {
        Integer lastLength = 32000;
        predictionLimiter.acquire(lastLength);
        muniDataUpdater.gatherPredictions();
    }
}
