package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.*;
import service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class BusLineController extends HttpServlet {
    private BusLineService busLineService = new BusLineService();
    private BusTransferService busTransferService = new BusTransferService();
    private BusStopService busStopService = new BusStopService();
    private UserService userService = new UserService();
    private CompanyService companyService = new CompanyService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        System.out.println("Processing request for action: " + action); // 添加调试信息

        try {
            if ("/login".equals(action)) {
                handleLogin(request, response);
            } else {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
                } else {
                    switch (action) {
                        case "/new":
                            showNewForm(request, response);
                            break;
                        case "/insert":
                            insertBusLine(request, response);
                            break;
                        case "/delete":
                            deleteBusLine(request, response);
                            break;
                        case "/edit":
                            showEditForm(request, response);
                            break;
                        case "/update":
                            updateBusLine(request, response);
                            break;
                        case "/list":
                            listBusLine(request, response);
                            break;
                        case "/transfer":
                            showTransferRoutes(request, response);
                            break;
                        case "/distance":
                            saveBusStopDistance(request, response);
                            break;
                        case "/info":
                            showLineInfo(request, response);
                            break;
                        case "/search":
                            searchBusLineOrStop(request, response);
                            break;
                        default:
                            showHomePage(request, response);
                            break;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        System.out.println("Handling login for user: " + username); // 添加日志输出

        try {
            if (userService.validateUser(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                System.out.println("User validated successfully");
                response.sendRedirect(request.getContextPath());
                System.out.println("login success");
            } else {
                request.setAttribute("errorMessage", "Invalid Username or Password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(request, response);
                System.out.println("login fail");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception during login: " + e.getMessage());
            throw new ServletException("Login failed!", e);
        }
    }


    private void listBusLine(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<BusLine> listBusLine = busLineService.getAllBusLines();
        request.setAttribute("listBusLine", listBusLine);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/viewLines.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<BusStop> listBusStop = busStopService.listAllBusStops();
        List<Company> listCompanies = companyService.getAllCompanies();
        request.setAttribute("listBusStop", listBusStop);
        request.setAttribute("listCompanies", listCompanies);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/newLineForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertBusLine(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String lineName = request.getParameter("lineName");
        String[] stopsArray = request.getParameterValues("stops");
        String[] startStopsArray = request.getParameterValues("startStop");
        String[] endStopsArray = request.getParameterValues("endStop");
        String[] distancesArray = request.getParameterValues("distance");
        int companyId = Integer.parseInt(request.getParameter("companyId"));

        BusLine newBusLine = new BusLine();
        newBusLine.setLineName(lineName);
        newBusLine.setCompanyId(companyId);

        // 验证公司是否存在
        if (companyService.getCompany(companyId) == null) {
            request.setAttribute("errorMessage", "Invalid Company ID");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        busLineService.saveBusLine(newBusLine);

        int lineId = newBusLine.getId();

        for (int i = 0; i < stopsArray.length; i++) {
            String stopName = stopsArray[i].trim();
            BusStop stop = busStopService.getBusStopByName(stopName);
            if (stop == null) {
                stop = new BusStop();
                stop.setStopName(stopName);
                busStopService.saveBusStop(stop);
            }
            busStopService.insertBusStopLine(lineId, stop.getId(), i + 1);
        }

        if (startStopsArray != null && endStopsArray != null && distancesArray != null) {
            for (int i = 0; i < startStopsArray.length; i++) {
                String startStopName = startStopsArray[i].trim();
                String endStopName = endStopsArray[i].trim();
                double distance = Double.parseDouble(distancesArray[i].trim());

                BusStop startStop = busStopService.getBusStopByName(startStopName);
                BusStop endStop = busStopService.getBusStopByName(endStopName);

                if (startStop != null && endStop != null) {
                    busStopService.insertBusStopDistance(startStop.getId(), endStop.getId(), distance);
                }
            }
        }

        response.sendRedirect("list");
    }


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BusLine existingBusLine = busLineService.getBusLine(id);
        List<BusStop> listBusStop = busStopService.listAllBusStops();
        request.setAttribute("busLine", existingBusLine);
        request.setAttribute("listBusStop", listBusStop);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/editLineForm.jsp");
        dispatcher.forward(request, response);
    }

    private void updateBusLine(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String lineName = request.getParameter("lineName");
        String[] stopsArray = request.getParameterValues("stops");
        String[] distancesArray = request.getParameterValues("distances");

        BusLine busLine = new BusLine();
        busLine.setId(id);
        busLine.setLineName(lineName);

        // 更新 busLine
        busLineService.updateBusLine(busLine);

        // 删除旧的站点关联
        busStopService.deleteBusStopLinesByLineId(id);

        // 插入新的站点和距离关联
        for (int i = 0; i < stopsArray.length; i++) {
            String stopName = stopsArray[i].trim();
            BusStop stop = busStopService.getBusStopByName(stopName);
            if (stop == null) {
                stop = new BusStop();
                stop.setStopName(stopName);
                busStopService.saveBusStop(stop);
            }
            busStopService.insertBusStopLine(id, stop.getId(), i + 1);

            if (i < distancesArray.length) {
                String distanceStr = distancesArray[i].trim();
                if (!distanceStr.isEmpty()) {
                    double distance = Double.parseDouble(distanceStr);
                    busStopService.insertBusStopDistance(id, stop.getId(), distance);
                }
            }
        }

        response.sendRedirect("list");
    }

    private void deleteBusLine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            busLineService.deleteBusLine(id);
            response.sendRedirect(request.getContextPath() + "/BusLineController/list");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting bus line: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showTransferRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startStopName = request.getParameter("startStopName");
        String endStopName = request.getParameter("endStopName");

        try {
            BusStop startStop = busStopService.getBusStopByName(startStopName.trim());
            BusStop endStop = busStopService.getBusStopByName(endStopName.trim());

            if (startStop == null || endStop == null) {
                request.setAttribute("errorMessage", "Invalid start or end stop name.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
                dispatcher.forward(request, response);
                return;
            }

            List<List<TransferRoute>> transferRoutes = busTransferService.findTransferRoutes(startStop.getId(), endStop.getId());

            List<Map.Entry<List<TransferRoute>, Double>> routesWithDistances = new ArrayList<>();
            for (List<TransferRoute> route : transferRoutes) {
                List<Integer> stopIds = new ArrayList<>();

                for (TransferRoute transferRoute : route) {
                    List<BusStop> stops = transferRoute.getStops();

                    // 确保站点顺序正确
                    boolean isReversed = false;
                    if (stops.size() > 1) {
                        int firstStopId = stops.get(0).getId();
                        int lastStopId = stops.get(stops.size() - 1).getId();
                        if (firstStopId > lastStopId) {
                            isReversed = true;
                        }
                    }

                    if (isReversed) {
                        Collections.reverse(stops);
                    }

                    for (BusStop stop : stops) {
                        stopIds.add(stop.getId());
                    }
                }

                double totalDistance = busStopService.calculateTotalDistance(stopIds);
                routesWithDistances.add(new AbstractMap.SimpleEntry<>(route, totalDistance));
            }

            routesWithDistances.sort(Map.Entry.comparingByValue());

            request.setAttribute("routesWithDistances", routesWithDistances);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/transferRoutes.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error finding transfer routes: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }



    private void showHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<BusStop> listBusStop = busStopService.listAllBusStops();
            request.setAttribute("listBusStop", listBusStop);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading home page: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void saveBusStopDistance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int startStopId = Integer.parseInt(request.getParameter("startStopId"));
        int endStopId = Integer.parseInt(request.getParameter("endStopId"));
        double distance = Double.parseDouble(request.getParameter("distance"));

        try {
            BusStopDistance busStopDistance = new BusStopDistance(startStopId, endStopId, distance);
            busStopService.saveBusStopDistance(busStopDistance);
            response.sendRedirect(request.getContextPath() + "/BusLineController/list");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error saving bus stop distance: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showLineInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            BusLine busLine = busLineService.getBusLine(id);
            Company company = companyService.getCompanyById(busLine.getCompanyId());
            List<BusStop> stops = busStopService.getBusStopsByLineId(id); // 获取线路的所有站点

            busLine.setStops(stops); // 设置线路的站点

            request.setAttribute("busLine", busLine);
            request.setAttribute("company", company);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/lineInfo.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading line information: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }


    private void searchBusLineOrStop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        try {
            List<BusLine> busLines = busLineService.searchBusLines(query);
            List<BusStop> busStops = busStopService.searchBusStops(query);

            // 排序处理
            if (sortBy != null && !sortBy.isEmpty()) {
                if ("lineName".equals(sortBy)) {
                    busLines.sort((b1, b2) -> sortOrder.equals("desc") ? b2.getLineName().compareTo(b1.getLineName()) : b1.getLineName().compareTo(b2.getLineName()));
                } else if ("ascii".equals(sortBy)) {
                    busLines.sort((b1, b2) -> sortOrder.equals("desc") ? b2.getLineName().compareToIgnoreCase(b1.getLineName()) : b1.getLineName().compareToIgnoreCase(b2.getLineName()));
                }
            }

            request.setAttribute("busLines", busLines);
            request.setAttribute("busStops", busStops);
            request.setAttribute("query", query); // 保留查询字符串
            request.setAttribute("sortBy", sortBy); // 保留排序字段
            request.setAttribute("sortOrder", sortOrder); // 保留排序顺序

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/searchResults.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error searching for bus lines or stops: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
