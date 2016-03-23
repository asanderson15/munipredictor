package org.asanderson.muni.worker;

import org.asanderson.muni.NextBusClient.MuniClientWrapper;
import org.asanderson.muni.model.Direction;
import org.asanderson.muni.model.MuniData;
import org.asanderson.muni.model.Route;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asanderson on 3/22/16.
 */
public class MuniDataUpdater {

    private MuniData muniData;
    private MuniClientWrapper client;

    String lastRoute = "";

    public MuniDataUpdater(MuniData muniData) {
        this.muniData = muniData;
        this.client = new MuniClientWrapper();
    }

    public synchronized void gatherRoutes() {
        HashMap<String,Route> routes = client.getRoutes();
        if(routes != null) {
            muniData.setRoutes(client.getRoutes());
            muniData.setRouteIds(new ArrayList<>(muniData.getRoutes().keySet()));
        }
        else {
            System.out.println("Failed to load routes.");
        }
        System.out.println("Routes loaded.");
    }

    public synchronized void gatherPredictions() {
        String routeId = getNextRoute();
        for (String directionId : muniData.getRoute(routeId).getDirectionIds()) {
            Direction direction = muniData.getRoute(routeId).getDirection(directionId);
            if (direction != null) {
                client.getPredictions(routeId, direction.getStops());
            }
        }
    }

    private String getNextRoute() {
        if(lastRoute.equals("") || muniData.getRouteIds().indexOf(lastRoute) < 0) {
            lastRoute = muniData.getRouteIds().get(0);
        }
        else {
            Integer index = muniData.getRouteIds().indexOf(lastRoute);
            if(index == muniData.getRouteIds().size()-1) {
                lastRoute = muniData.getRouteIds().get(0);
            }
            else {
                lastRoute = muniData.getRouteIds().get(index+1);
            }
        }
        return lastRoute;
    }
}
