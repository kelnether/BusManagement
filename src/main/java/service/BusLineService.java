package service;

import dao.BusLineDAO;
import model.BusLine;

import java.sql.SQLException;
import java.util.List;

public class BusLineService {
    private BusLineDAO busLineDAO = new BusLineDAO();

    public void saveBusLine(BusLine busLine, List<Integer> stopIds) throws SQLException {
        busLineDAO.insertBusLine(busLine, stopIds);
    }

    public List<BusLine> getAllBusLines() throws SQLException {
        return busLineDAO.listAllBusLines();
    }

    public BusLine getBusLine(int id) throws SQLException {
        return busLineDAO.getBusLine(id);
    }

    public void updateBusLine(BusLine busLine) throws SQLException {
        busLineDAO.updateBusLine(busLine);
    }

    public void deleteBusLine(int id) throws SQLException {
        busLineDAO.deleteBusLine(id);
    }

    public void saveBusLine(BusLine busLine) throws SQLException {
        busLineDAO.saveBusLine(busLine);
    }

    public List<BusLine> searchBusLines(String query) throws SQLException {
        return busLineDAO.searchBusLines(query);
    }
}
