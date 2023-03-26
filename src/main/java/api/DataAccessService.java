package api;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataAccessService {

    Connection con;
    private JdbcTemplate jdbc;

    @Autowired
    public DataAccessService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        try {
            con = jdbc.getDataSource().getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private User mapUserFromResultSetWithPassword(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("username"), resultSet.getInt("iduser"), resultSet.getString("password"), resultSet.getString("email"));
        return user;
    }

    public User getUserFromUsernameWithPassword(String username) throws SQLException {

        PreparedStatement queryString = con.prepareStatement("SELECT * FROM user WHERE user.username = ?");
        queryString.setString(1, username);

        ResultSet resultSet = queryString.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapUserFromResultSetWithPassword(resultSet));
        }
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    private User mapUserFromUsername(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("username"), resultSet.getInt("iduser"), resultSet.getString("email"));
        return user;
    }

    private User mapUserFromUsernameWithCompany(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("username"), resultSet.getInt("iduser"), resultSet.getString("email"), getCompanyFromCompanyID(resultSet.getInt("idcompany_FKuser")));
        return user;
    }

    public User getUserCompanyFromUsername(String username) throws SQLException {

        PreparedStatement queryString = con.prepareStatement("SELECT * FROM user WHERE user.username = ?");
        queryString.setString(1, username);

        ResultSet resultSet = queryString.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapUserFromUsernameWithCompany(resultSet));
        }
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    public User getUserFromUsername(String username) throws SQLException {

        PreparedStatement queryString = con.prepareStatement("SELECT * FROM user WHERE user.username = ?");
        queryString.setString(1, username);

        ResultSet resultSet = queryString.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapUserFromUsername(resultSet));
        }
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    public User getUserFromUserID(int userID) throws SQLException {

        PreparedStatement queryString = con.prepareStatement("SELECT * FROM user WHERE user.iduser = ?");
        queryString.setInt(1, userID);

        ResultSet resultSet = queryString.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapUserFromUsername(resultSet));
        }
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    public void insertReport(LocalDateTime date, int pharmacyID, int userID, Status status, int medicineID) throws SQLException {
        PreparedStatement queryString = con.prepareStatement("INSERT INTO report (DATE, IDPHARMACY_FK, IDUSER_FK, STATUS, IDMEDICINE_FKREPORT) VALUES(?,?,?,?,?)");
        queryString.setString(1, date.toString());
        queryString.setInt(2, pharmacyID);
        queryString.setInt(3, userID);
        System.out.println(status.name());
        System.out.println(status.toString());
        queryString.setString(4, status.name());
        queryString.setInt(5, medicineID);
        int rowsAffected = queryString.executeUpdate();

    }
    public void registerUser(String username, String password, String email) throws SQLException {
        PreparedStatement queryString = con.prepareStatement("INSERT INTO user (USERNAME, PASSWORD, EMAIL) VALUES(?,?,?)");
        queryString.setString(1, username);
        queryString.setString(2, password);
        queryString.setString(3, email);
        int rowsAffected = queryString.executeUpdate();
    }

    public Company mapCompanyFromDB(ResultSet resultSet) throws SQLException
    {
        Company company = new Company(resultSet.getString("name_company"), resultSet.getInt("idcompany"));
        return company;
    }

    public Company getCompanyFromCompanyID(int companyID) throws  SQLException
    {
        PreparedStatement queryString = con.prepareStatement("SELECT * FROM company WHERE company.idcompany = ?");
        queryString.setInt(1, companyID);

        ResultSet resultSet = queryString.executeQuery();
        List<Company> companies = new ArrayList<>();
        while (resultSet.next()) {
            companies.add(mapCompanyFromDB(resultSet));
        }
        if (companies.size() == 0)
            return null;
        return companies.get(0);
    }

    public Pharmacy getPharmacyFromPharmacyID(int pharmacyID) throws  SQLException
    {
        PreparedStatement queryString = con.prepareStatement("SELECT * FROM pharmacy WHERE pharmacy.idpharmacy = ?");
        queryString.setInt(1, pharmacyID);

        ResultSet resultSet = queryString.executeQuery();
        List<Pharmacy> pahrmacies = new ArrayList<>();
        while (resultSet.next()) {
            pahrmacies.add(mapPharmacyFromDB(resultSet));
        }
        if (pahrmacies.size() == 0)
            return null;
        return pahrmacies.get(0);
    }

    public Medicine getMedicineFromMedicineID(int medicineID) throws  SQLException
    {
        PreparedStatement queryString = con.prepareStatement("SELECT * FROM medicine WHERE medicine.idmedicine = ?");
        queryString.setInt(1, medicineID);

        ResultSet resultSet = queryString.executeQuery();
        List<Medicine> medicines = new ArrayList<>();
        while (resultSet.next()) {
            medicines.add(mapMedicineFromDB(resultSet));
        }
        if (medicines.size() == 0)
            return null;
        return medicines.get(0);
    }

    public Pharmacy mapPharmacyFromDB(ResultSet resultSet) throws SQLException
    {
        Pharmacy pharmacy = new Pharmacy(resultSet.getInt("idpharmacy"), getCompanyFromCompanyID(resultSet.getInt("idcompany_FK")) , resultSet.getString("location"));
        return pharmacy;
    }

    private Report mapReportFromDB(ResultSet resultSet) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("date").toString(), formatter);

        Report report = new Report(resultSet.getInt("idreport"), dateTime, getPharmacyFromPharmacyID(resultSet.getInt("idpharmacy_FK")) , getMedicineFromMedicineID(resultSet.getInt("idmedicine_FKreport")), getUserFromUserID(resultSet.getInt("iduser_FK")), Status.valueOf(resultSet.getString("status")));
        return report;
    }

    private Medicine mapMedicineFromDB(ResultSet resultSet) throws  SQLException{
        Medicine medicine = new Medicine(resultSet.getInt("idmedicine"), resultSet.getString("name_medicine"), resultSet.getInt("weight"));
        return medicine;
    }

    public List<Report> getReportsUser(String username, String medicineName) throws SQLException {
        PreparedStatement queryString = con.prepareStatement("SELECT * FROM pharmachain.medicine INNER JOIN report ON medicine.idmedicine = report.idmedicine_FKreport INNER JOIN pharmacy ON pharmacy.idpharmacy = report.idpharmacy_FK INNER JOIN company ON company.idcompany = pharmacy.idcompany_FK WHERE medicine.name_medicine = ?");
        queryString.setString(1, medicineName);
        ResultSet resultSet = queryString.executeQuery();
        List<Report> reports = new ArrayList<>();
        while (resultSet.next()) {
            reports.add(mapReportFromDB(resultSet));
        }
        if (reports.size() == 0)
            return null;
        return reports;
    }

    public List<Report> getReportsCompany(String username) throws SQLException {

        User user  = getUserCompanyFromUsername(username);

        PreparedStatement queryString = con.prepareStatement("SELECT * FROM pharmachain.medicine INNER JOIN report ON medicine.idmedicine = report.idmedicine_FKreport INNER JOIN pharmacy ON pharmacy.idpharmacy = report.idpharmacy_FK INNER JOIN company ON company.idcompany = pharmacy.idcompany_FK WHERE company.name_company = ? AND report.status = ? AND report.date > '1951-01-01 01:00:00'");
        queryString.setString(1, user.getCompany().getName());
        queryString.setString(2, Status.UNKNOWN.toString());

        ResultSet resultSet = queryString.executeQuery();
        List<Report> reports = new ArrayList<>();
        while (resultSet.next()) {
            reports.add(mapReportFromDB(resultSet));
        }
        if (reports.size() == 0)
            return null;
        return reports;
    }

}
