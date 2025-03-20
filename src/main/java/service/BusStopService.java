package service;

import dao.BusStopDAO;
import model.BusStop;
import model.BusLine;
import model.BusStopDistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BusStopService {
    private BusStopDAO busStopDAO = new BusStopDAO();

    public List<BusStop> listAllBusStops() throws SQLException {
        return busStopDAO.listAllBusStops();
    }

    public BusStop getBusStopByName(String stopName) throws SQLException {
        return busStopDAO.getBusStopByName(stopName);
    }

    public void saveBusStop(BusStop busStop) throws SQLException {
        busStopDAO.insertBusStop(busStop);
    }

    public List<BusLine> getBusLinesByStop(int stopId) throws SQLException {
        return busStopDAO.getBusLinesByStop(stopId);
    }

    public List<BusStop> getBusStopsByLine(int lineId) throws SQLException {
        return busStopDAO.getBusStopsByLine(lineId);
    }

    public void saveBusStopDistance(BusStopDistance distance) throws SQLException {
        BusStopDistance existingDistance = busStopDAO.getBusStopDistance(distance.getStartStopId(), distance.getEndStopId());
        if (existingDistance == null) {
            busStopDAO.insertBusStopDistance(distance);
        } else {
            busStopDAO.updateBusStopDistance(distance);
        }
    }


    public List<BusStopDistance> listAllBusStopDistances() throws SQLException {
        return busStopDAO.listAllBusStopDistances();
    }

    public BusStopDistance getBusStopDistance(int startStopId, int endStopId) throws SQLException {
        return busStopDAO.getBusStopDistance(startStopId, endStopId);
    }

    public double calculateTotalDistance(List<Integer> stopIds) throws SQLException {
        double totalDistance = 0.0;
        for (int i = 0; i < stopIds.size() - 1; i++) {
            BusStopDistance distance = getBusStopDistance(stopIds.get(i), stopIds.get(i + 1));
            if (distance != null) {
                totalDistance += distance.getDistance();
            } else {
                distance = getBusStopDistance(stopIds.get(i + 1), stopIds.get(i)); // 反向查找距离
                if (distance != null) {
                    totalDistance += distance.getDistance();
                }
            }
        }
        return totalDistance;
    }


    public List<BusStop> getBusStopsWithDistancesByLine(int lineId) throws SQLException {
        return busStopDAO.getBusStopsWithDistancesByLine(lineId);
    }

    public void insertBusStopLine(int lineId, int stopId, int sequence) throws SQLException {
        busStopDAO.insertBusStopLine(lineId, stopId, sequence);
    }

    public void insertBusStopDistance(int startStopId, int endStopId, double distance) throws SQLException {
        busStopDAO.insertBusStopDistance(startStopId, endStopId, distance);
    }


    public List<BusStop> searchBusStops(String query) throws SQLException {
        return busStopDAO.searchBusStops(query);
    }
    public void deleteBusStopLinesByLineId(int lineId) throws SQLException {
        busStopDAO.deleteBusStopLinesByLineId(lineId);
    }

    public List<BusStop> getBusStopsByLineId(int lineId) throws SQLException {
        return busStopDAO.getBusStopsByLineId(lineId);
    }


}
