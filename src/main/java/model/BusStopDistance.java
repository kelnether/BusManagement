package model;

public class BusStopDistance {
    private int id;
    private int startStopId;
    private int endStopId;
    private double distance;

    public BusStopDistance() {
    }

    public BusStopDistance(int startStopId, int endStopId, double distance) {
        this.startStopId = startStopId;
        this.endStopId = endStopId;
        this.distance = distance;
    }

    public BusStopDistance(int id, int startStopId, int endStopId, double distance) {
        this.id = id;
        this.startStopId = startStopId;
        this.endStopId = endStopId;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartStopId() {
        return startStopId;
    }

    public void setStartStopId(int startStopId) {
        this.startStopId = startStopId;
    }

    public int getEndStopId() {
        return endStopId;
    }

    public void setEndStopId(int endStopId) {
        this.endStopId = endStopId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
