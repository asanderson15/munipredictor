package org.asanderson.muni.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by asanderson on 3/19/16.
 */
public class Route {

    String id;

    String title;

    String color;

    String oppositeColor;

    ArrayList<String> directionIds;

    HashMap<String,Direction> directions;

    public Route(String id, String title, String color, String oppositeColor) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.oppositeColor = oppositeColor;
        this.directions = new HashMap<String, Direction>();
    }

    public HashMap<String, Direction> getDirections() {
        return directions;
    }

    public void setDirectionIds(ArrayList<String> directionIds) {
        this.directionIds = directionIds;
    }

    public void addDirection(String directionId, Direction direction) {
        this.directions.put(directionId, direction);
    }

    public void setDirections(HashMap<String, Direction> directions) {
        this.directions = directions;
    }

    public Direction getDirection(String directionId) {
        if(directions.containsKey(directionId)) {
            return directions.get(directionId);
        } else {
            return null;
        }
    }

    public ArrayList<String> getDirectionIds() {
        return directionIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOppositeColor() {
        return oppositeColor;
    }

    public void setOppositeColor(String oppositeColor) {
        this.oppositeColor = oppositeColor;
    }

}
