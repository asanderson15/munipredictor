package org.asanderson.muni.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asanderson on 3/19/16.
 */
public class Direction {

    String directionTitle;

    String directionBound;

    ArrayList<String> stopIds;

    HashMap<String, Stop> stops;

    public Direction(String directionTitle, String directionBound) {
        this.directionTitle = directionTitle;
        this.directionBound = directionBound;
        this.stopIds = new ArrayList<String>();
        this.stops = new HashMap<String, Stop>();
    }

    public String getDirectionTitle() {
        return directionTitle;
    }

    public void setDirectionTitle(String directionTitle) {
        this.directionTitle = directionTitle;
    }

    public String getDirectionBound() {
        return directionBound;
    }

    public void setDirectionBound(String directionBound) {
        this.directionBound = directionBound;
    }

    public HashMap<String,Stop> getStops() {
        return stops;
    }

    public void addStop(String stopId, Stop stop) {
        this.stops.put(stopId, stop);
    }

    public void setStopIds(ArrayList<String> stopIds) {
        this.stopIds = stopIds;
    }

    public ArrayList<String> getStopIds() {
        return stopIds;
    }
}
