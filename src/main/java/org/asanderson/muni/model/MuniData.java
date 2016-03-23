package org.asanderson.muni.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asanderson on 3/22/16.
 */
public class MuniData {

    private ArrayList<String> routeIds;

    private HashMap<String, Route> routes;

    public synchronized ArrayList<String> getRouteIds() {
        return routeIds;
    }

    public synchronized void setRouteIds(ArrayList<String> routeIds) {
        this.routeIds = routeIds;
    }

    public synchronized Route getRoute(String routeId) {
        return this.routes.get(routeId);
    }

    public synchronized void setRoute(String routeId, Route route) {
        this.routes.put(routeId, route);
    }

    public void setRoutes(HashMap<String, Route> routes) {
        this.routes = routes;
    }

    public HashMap<String, Route> getRoutes() {
        return routes;
    }
}
