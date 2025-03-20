package service;

import dao.CompanyDAO;
import model.Company;

import java.sql.SQLException;
import java.util.List;

public class CompanyService {
    private CompanyDAO companyDAO = new CompanyDAO();

    public Company getCompanyById(int id) throws SQLException {
        return companyDAO.getCompanyById(id);
    }

    public List<Company> getAllCompanies() throws SQLException {
        return companyDAO.listAllCompanies();
    }

    public void saveCompany(Company company) throws SQLException {
        companyDAO.saveCompany(company);
    }

    public void updateCompany(Company company) throws SQLException {
        companyDAO.updateCompany(company);
    }

    public void deleteCompany(int id) throws SQLException {
        companyDAO.deleteCompany(id);
    }
    public Company getCompany(int id) throws SQLException {
        return companyDAO.getCompanyById(id);
    }
}
