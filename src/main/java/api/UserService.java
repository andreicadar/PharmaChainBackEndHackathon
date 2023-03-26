package api;

import model.Report;
import model.Status;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    DataAccessService dataAccessService;

    @Autowired
    public UserService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    public void registerUser(String username, String password, String email) throws SQLException {
        dataAccessService.registerUser(username, password, email);
    }

    public User getUser(String username) throws SQLException {
        return dataAccessService.getUserFromUsername(username);
    }

    public List<Report> getReportsUser(String username, String medicineName) throws SQLException {
        return dataAccessService.getReportsUser(username, medicineName);
    }

    public List<Report> getReportsUerWithoutUsername(String username) throws SQLException {
        return dataAccessService.getReportsUserWithoutMedicine(username);
    }

    public List<Report> getReportsCompany(String username) throws SQLException {
        return dataAccessService.getReportsCompany(username);
    }

    public void insertReport(LocalDateTime date, int pharmacyID, int userID, Status status, int medicineID) throws SQLException {
        dataAccessService.insertReport(date, pharmacyID, userID, status, medicineID);
    }

    public void updateReport(int reportID, String  status, LocalDateTime currentTime) throws  SQLException{
        dataAccessService.updateReport(reportID, status, currentTime);
    }

}
