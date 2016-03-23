package org.asanderson.muni.model;

/**
 * Created by asanderson on 3/19/16.
 */
public class Prediction {

    String epochTime;

    String seconds;

    String minutes;

    String isEnroute;

    String vehicleId;

    String tripId;

    public Prediction(String epochTime, String seconds, String minutes, String isEnroute, String vehicleId, String tripId) {
        this.epochTime = epochTime;
        this.seconds = seconds;
        this.minutes = minutes;
        this.isEnroute = isEnroute;
        this.vehicleId = vehicleId;
        this.tripId = tripId;
    }

    public String getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(String epochTime) {
        this.epochTime = epochTime;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getIsEnroute() {
        return isEnroute;
    }

    public void setIsEnroute(String isEnroute) {
        this.isEnroute = isEnroute;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
