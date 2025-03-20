package model;

public class BusStop {
    private int id;
    private String stopName;
    private int sequence;
    private Double distanceToNextStop;

    // 构造函数
    public BusStop() {
    }

    public BusStop(int id, String stopName, int sequence) {
        this.id = id;
        this.stopName = stopName;
        this.sequence = sequence;
    }

    public BusStop(int id, String stopName) {
        this.id = id;
        this.stopName = stopName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Double getDistanceToNextStop() {
        return distanceToNextStop;
    }

    public void setDistanceToNextStop(Double distanceToNextStop) {
        this.distanceToNextStop = distanceToNextStop;
    }
}
