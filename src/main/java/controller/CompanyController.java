package controller;

import model.Company;
import model.BusLine;
import service.CompanyService;
import service.BusLineService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CompanyController/*")
public class CompanyController extends HttpServlet {
    private CompanyService companyService;
    private BusLineService busLineService;

    public void init() {
        companyService = new CompanyService();
        busLineService = new BusLineService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list"; // 默认行为
        }
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertCompany(request, response);
                    break;
                case "/delete":
                    deleteCompany(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateCompany(request, response);
                    break;
                case "/list":
                    listCompany(request, response);
                    break;
                case "/assign":
                    showAssignForm(request, response);
                    break;
                case "/assignCompanyToLine":
                    assignCompanyToLine(request, response);
                    break;
                default:
                    listCompany(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listCompany(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Company> listCompany = companyService.getAllCompanies();
        request.setAttribute("listCompany", listCompany);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/companyList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/companyForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Company existingCompany = companyService.getCompany(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/companyForm.jsp");
        request.setAttribute("company", existingCompany);
        dispatcher.forward(request, response);
    }

    private void insertCompany(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String website = request.getParameter("website");

        Company newCompany = new Company();
        newCompany.setName(name);
        newCompany.setWebsite(website);
        companyService.saveCompany(newCompany);
        response.sendRedirect("list");
    }

    private void updateCompany(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String website = request.getParameter("website");

        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setWebsite(website);
        companyService.updateCompany(company);
        response.sendRedirect("list");
    }

    private void deleteCompany(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        companyService.deleteCompany(id);
        response.sendRedirect("list");
    }

    private void showAssignForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Company> listCompany = companyService.getAllCompanies();
        List<BusLine> listBusLine = busLineService.getAllBusLines();
        request.setAttribute("listCompany", listCompany);
        request.setAttribute("listBusLine", listBusLine);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/assignCompanyToLine.jsp");
        dispatcher.forward(request, response);
    }

    private void assignCompanyToLine(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int lineId = Integer.parseInt(request.getParameter("lineId"));
        int companyId = Integer.parseInt(request.getParameter("companyId"));

        BusLine busLine = busLineService.getBusLine(lineId);
        busLine.setCompanyId(companyId);
        busLineService.updateBusLine(busLine);

        response.sendRedirect("list");
    }
}
