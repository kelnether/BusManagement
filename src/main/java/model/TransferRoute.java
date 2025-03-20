package model;

import java.util.List;

public class TransferRoute {
    private BusLine busLine;
    private List<BusStop> stops;

    public TransferRoute(BusLine busLine, List<BusStop> stops) {
        this.busLine = busLine;
        this.stops = stops;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }
}
