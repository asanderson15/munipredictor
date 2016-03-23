package org.asanderson.muni;

import org.asanderson.muni.NextBusClient.MuniPredictionClient;
import org.asanderson.muni.NextBusClient.routepredictions.RoutePredictions;
import org.asanderson.muni.model.MuniData;
import org.asanderson.muni.model.Route;
import org.asanderson.muni.worker.CsvOutputRunner;
import org.asanderson.muni.worker.PredictionRunner;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.time.LocalDateTime;
import java.util.Arrays;


/**
 * Created by asanderson on 3/15/16.
 */
public class MuniPredictionApplication {

    public static void main(String args[]) throws Exception {

        String outputPath;

        if(args.length > 0) {
            outputPath = args[0];
            if(!outputPath.substring(outputPath.length()-1).equals("/")) {
                outputPath += "/";
            }
        } else {
            System.out.println("usage: munipredictor outputPath");
            System.exit(1);
            return;
        }

        MuniData muniData = new MuniData();
        PredictionRunner predictionRunner = new PredictionRunner(muniData);
        predictionRunner.start();

        CsvOutputRunner csvOutputRunner = new CsvOutputRunner(muniData, outputPath);
        csvOutputRunner.start();

    }

}
