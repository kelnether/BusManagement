package service;

import dao.BusStopDAO;
import model.BusStop;
import model.BusLine;
import model.TransferRoute;

import java.sql.SQLException;
import java.util.*;

public class BusTransferService {
    private BusStopDAO busStopDAO = new BusStopDAO();

    private Map<String, List<String>> busStopMap = new HashMap<>(); // 假设这是一个站点邻接表

    public List<List<String>> findTransferRoutesByStopNames(String startStopName, String endStopName) {
        List<List<String>> transferRoutes = new ArrayList<>();
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // 初始化队列
        queue.offer(Collections.singletonList(startStopName));
        visited.add(startStopName);

        while (!queue.isEmpty()) {
            List<String> route = queue.poll();
            String lastStop = route.get(route.size() - 1);

            // 到达终点
            if (lastStop.equals(endStopName)) {
                transferRoutes.add(route);
                continue;
            }

            // 扩展节点
            for (String neighbor : busStopMap.getOrDefault(lastStop, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newRoute = new ArrayList<>(route);
                    newRoute.add(neighbor);
                    queue.offer(newRoute);
                }
            }
        }
        return transferRoutes;
    }


    public List<List<TransferRoute>> findTransferRoutes(int startStopId, int endStopId) throws SQLException {
        List<BusLine> startLines = busStopDAO.getBusLinesByStop(startStopId);
        List<BusLine> endLines = busStopDAO.getBusLinesByStop(endStopId);

        List<TransferRoute> directRoute = findDirectRoute(startLines, endLines);
        if (directRoute != null) {
            List<List<TransferRoute>> result = new ArrayList<>();
            result.add(directRoute);
            return result;
        }

        return findTransferRoute(startLines, endLines);
    }

    private List<TransferRoute> findDirectRoute(List<BusLine> startLines, List<BusLine> endLines) throws SQLException {
        for (BusLine startLine : startLines) {
            for (BusLine endLine : endLines) {
                if (startLine.getId() == endLine.getId()) {
                    List<BusStop> stops = busStopDAO.getBusStopsByLine(startLine.getId());
                    List<TransferRoute> route = new ArrayList<>();
                    route.add(new TransferRoute(startLine, stops));
                    return route;
                }
            }
        }
        return null;
    }

    private List<List<TransferRoute>> findTransferRoute(List<BusLine> startLines, List<BusLine> endLines) throws SQLException {
        List<List<TransferRoute>> transferRoutes = new ArrayList<>();

        for (BusLine startLine : startLines) {
            for (BusLine endLine : endLines) {
                List<BusStop> startStops = busStopDAO.getBusStopsByLine(startLine.getId());
                List<BusStop> endStops = busStopDAO.getBusStopsByLine(endLine.getId());

                for (BusStop startStop : startStops) {
                    for (BusStop endStop : endStops) {
                        if (startStop.getId() == endStop.getId()) {
                            List<TransferRoute> transferRoute = new ArrayList<>();
                            transferRoute.add(new TransferRoute(startLine, startStops));
                            transferRoute.add(new TransferRoute(endLine, endStops));
                            transferRoutes.add(transferRoute);
                        }
                    }
                }
            }
        }

        return transferRoutes;
    }
}
