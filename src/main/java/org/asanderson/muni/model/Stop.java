package org.asanderson.muni.model;

import java.util.ArrayList;

/**
 * Created by asanderson on 3/19/16.
 */
public class Stop {

    String id;

    String title;

    String lat;

    String lon;

    ArrayList<Prediction> predictions;

    public Stop(String id, String title, String lat, String lon) {
        this.id = id;
        this.title = title;
        this.lat = lat;
        this.lon = lon;
        this.predictions = new ArrayList<Prediction>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public ArrayList<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }

    public void addPrediction(Prediction prediction) {
        this.predictions.add(prediction);
    }
}
