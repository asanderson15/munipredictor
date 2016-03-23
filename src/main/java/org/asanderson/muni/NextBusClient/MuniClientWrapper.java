package org.asanderson.muni.NextBusClient;

import org.asanderson.muni.NextBusClient.routedetails.RouteDetails;
import org.asanderson.muni.NextBusClient.routedetails.RouteDirection;
import org.asanderson.muni.NextBusClient.routedetails.RouteStop;
import org.asanderson.muni.NextBusClient.routepredictions.DirectionPrediction;
import org.asanderson.muni.NextBusClient.routepredictions.PredictionDetail;
import org.asanderson.muni.NextBusClient.routepredictions.RoutePredictions;
import org.asanderson.muni.NextBusClient.routepredictions.StopPrediction;
import org.asanderson.muni.NextBusClient.routes.RouteList;
import org.asanderson.muni.NextBusClient.routes.RouteWrapper;
import org.asanderson.muni.model.Direction;
import org.asanderson.muni.model.Prediction;
import org.asanderson.muni.model.Route;
import org.asanderson.muni.model.Stop;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asanderson on 3/22/16.
 */
public class MuniClientWrapper {

    private Retrofit retrofit;

    public MuniClientWrapper() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://webservices.nextbus.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public HashMap<String, Route> getRoutes() {
        HashMap<String,Route> routes = new HashMap<>();
        MuniPredictionClient client = retrofit.create(MuniPredictionClient.class);

        RouteList routeList;
        ArrayList<RouteWrapper> parsedRoutes;

        // Retrieve route list
        try {
            routeList = client.getRoutes().execute().body();
            parsedRoutes = new ArrayList<>(routeList.routes);
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            parsedRoutes = new ArrayList<>();
        }

        // Get details for each route and add to route HashMap
        for(RouteWrapper wrapper : parsedRoutes) {
            Route route = this.getRouteDetails(wrapper.id);
            if(route != null) {
                routes.put(route.getId(), route);
            }
        }

        return routes;
    }

    public Route getRouteDetails(String routeId) {
        Route route;
        MuniPredictionClient client = retrofit.create(MuniPredictionClient.class);

        RouteDetails routeDetails;

        // Retrieve route list
        try {
            routeDetails = client.getRouteConfig(routeId).execute().body();
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

        // Initialize Route
        route = new Route(routeId,
                routeDetails.title,
                routeDetails.color,
                routeDetails.oppositeColor);

        // Gather stop details
        HashMap<String, Stop> stops = new HashMap<>();
        for(RouteStop routeStop : routeDetails.routeStops) {
            Stop stop = new Stop(routeStop.id,
                    routeStop.title,
                    routeStop.lat,
                    routeStop.lon);
            stops.put(stop.getId(), stop);
        }

        // Set up directions
        HashMap<String,Direction> directions = new HashMap<>();
        ArrayList<String> directionIds = new ArrayList<>();
        for(RouteDirection routeDirection : routeDetails.routeDirections) {
            Direction direction = new Direction(routeDirection.title,
                    routeDirection.directionBound);

            // Add stops
            ArrayList<String> stopList = routeDirection.getStopList();
            direction.setStopIds(stopList);
            for(String stopId : stopList) {
                if(stops.containsKey(stopId)) {
                    direction.addStop(stopId, stops.get(stopId));
                }
            }

            route.addDirection(direction.getDirectionTitle(), direction);
            directionIds.add(direction.getDirectionTitle());

        }

        route.setDirectionIds(directionIds);
        return route;
    }

    public void getPredictions(String routeId, HashMap<String, Stop> stops) {

        ArrayList<String> params = getStopQueryParams(routeId, stops);
        MuniPredictionClient client = retrofit.create(MuniPredictionClient.class);
        ArrayList<StopPrediction> stopPredictions;

        // Retrieve predictions
        try {
            stopPredictions = new ArrayList<>(client.getRoutePredictions(params).execute().body().stopPredictions);
            if(stopPredictions == null && stopPredictions.size() <= 0) {
                return;
            }
        }
        catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }

        for(StopPrediction stopPrediction : stopPredictions) {
            if(stops.containsKey(stopPrediction.stopId) && stopPrediction.directions != null && stopPrediction.directions.size() > 0) {
                Stop stop = stops.get(stopPrediction.stopId);
                DirectionPrediction directionPrediction = stopPrediction.directions.get(0);
                ArrayList<Prediction> predictions = new ArrayList<>();
                for(PredictionDetail predictionDetail : directionPrediction.predictionDetailList) {
                    String isEnroute = (predictionDetail.enroute == null || !predictionDetail.equals("true")) ? "true" : "false";
                    Prediction prediction = new Prediction(predictionDetail.epochTime,
                            predictionDetail.seconds,
                            predictionDetail.minutes,
                            isEnroute,
                            predictionDetail.vehicleId,
                            predictionDetail.tripId);
                    predictions.add(prediction);
                }
                stop.setPredictions(predictions);
            }
        }

    }

    private ArrayList<String> getStopQueryParams(String routeId, HashMap<String, Stop> stops) {

        ArrayList<String> params = new ArrayList<>();
        for(String key : stops.keySet()) {
            params.add(routeId + "|" + key);
        }

        return params;

    }

}
