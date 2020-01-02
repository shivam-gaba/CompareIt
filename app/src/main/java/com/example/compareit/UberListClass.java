package com.example.compareit;

public class UberListClass {
    String cabType;
    double distance,minutes;

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    public UberListClass(String cabType, double distance, double minutes) {
        this.cabType = cabType;
        this.distance = distance;
        this.minutes = minutes;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }
}
