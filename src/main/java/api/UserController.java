package api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import exception.APIRequestException;
import model.Report;
import model.Status;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostPersist;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private DataAccessService dataAccessService;
    @Autowired
    private jwt.JwtTokenUtil jwtTokenUtil;



    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserController(UserService userService, DataAccessService dataAccessService) {
        this.userService = userService;
        this.dataAccessService = dataAccessService;
    }

    @GetMapping("/getUser")
    public User getRims(@RequestHeader("Authorization") String authorization,
                             @RequestParam(name = "username") String username) {
        if(username == null)
        {
            throw new APIRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "User null");
        }
        verifyUserFromTokenAndIfLoggedIn(authorization, username);
        try {
            return userService.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new APIRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare din baza de date");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody ObjectNode objectNode) {
        if (objectNode.get("username") == null || objectNode.get("username").asText().length() == 0) {
            System.out.println(objectNode.get("username"));
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Username este empty");
        }
        if (objectNode.get("password") == null || objectNode.get("password").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Password este empty");
        }
        if (objectNode.get("confirmPassword") == null || objectNode.get("confirmPassword").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Confirm Password este empty");
        }
        if (objectNode.get("email") == null || objectNode.get("email").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Email-ul este empty");
        }

        String emailValidator = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(objectNode.get("email").asText().matches(emailValidator) == false)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Email format not ok");
        }

        User user;
        try {
            user = dataAccessService.getUserFromUsername(objectNode.get("username").asText());
        } catch (SQLException e1) {
            e1.printStackTrace();

            throw new APIRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare din baza de date");
        }
        if (user != null) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Acest username este deja folosit");
        }

        if (objectNode.get("password").equals(objectNode.get("confirmPassword")) == false) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Parolele nu corespund");
        }

        try {
            userService.registerUser(objectNode.get("username").asText(),
                    bcryptEncoder.encode(objectNode.get("password").asText()), objectNode.get("email").asText());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new APIRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Eroare din baza de date");
        }

        throw new APIRequestException(HttpStatus.OK, "User audaugat cu succes");
    }

    @GetMapping("/getReportsUser")
    public List<Report> getReportsUser(@RequestHeader("Authorization")String authorization ,@RequestParam(name = "username") String username, @RequestParam(name = "medicineName") String medicineName) throws SQLException {
        if(username == null)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "User null");
        }
        verifyUserFromTokenAndIfLoggedIn(authorization, username);
        if(medicineName == null)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Medicine null");
        }
        return userService.getReportsUser(username, medicineName);
    }

    @GetMapping("/getConversationsUser")
    public List<Report> getReportsUserWithoutMedicine(@RequestHeader("Authorization")String authorization ,@RequestParam(name = "username") String username) throws SQLException {
        if(username == null)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "User null");
        }
        verifyUserFromTokenAndIfLoggedIn(authorization, username);
        return userService.getReportsUerWithoutUsername(username);
    }

    @GetMapping("/getReportsCompany")
    public List<Report> getReportsCompany(@RequestHeader("Authorization")String authorization ,@RequestParam(name = "companyName") String username) throws SQLException {
        if(username == null)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "User null");
        }
        verifyUserFromTokenAndIfLoggedIn(authorization, username);
        return userService.getReportsCompany(username);
    }

    @PostMapping("/postReport")
    public void postReport(@RequestHeader("Authorization")String authorization, @RequestBody ObjectNode objectNode) throws SQLException {
        if (objectNode.get("pharmacyID") == null || objectNode.get("pharmacyID").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "PharmacyID este empty");
        }
        if (objectNode.get("username") == null || objectNode.get("username").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "UserID este empty");
        }
        if (objectNode.get("status") == null || objectNode.get("status").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Status este empty");
        }
        if (objectNode.get("medicineID") == null || objectNode.get("medicineID").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "MedicineID este empty");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        currentTime = LocalDateTime.parse(currentTime.toString(), formatter);
        String currentTimeString = currentTime.toString().replace("T", " ");
        currentTimeString = currentTimeString.substring(0, currentTimeString.length() - 10);
        System.out.println(currentTimeString);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(currentTimeString, formatter);

        verifyUserFromTokenAndIfLoggedIn(authorization, objectNode.get("username").asText());
        userService.insertReport(dateTime, objectNode.get("pharmacyID").asInt(), dataAccessService.getUserFromUsername(objectNode.get("username").asText()).getUserID(), Status.valueOf(objectNode.get("status").asText()), objectNode.get("medicineID").asInt());

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse(objectNode.get("date").asText(), formatter);
//        verifyUserFromTokenAndIfLoggedIn(authorization, dataAccessService.getUserFromUserID(objectNode.get("userID").asInt()).getUsername());
//        userService.insertReport(dateTime, objectNode.get("pharmacyID").asInt(), objectNode.get("userID").asInt(), Status.valueOf(objectNode.get("status").asText()), objectNode.get("medicineID").asInt());

    }

    @PostMapping("/updateReport")
    public void updateReport(@RequestHeader("Authorization")String authorization, @RequestBody ObjectNode objectNode) throws SQLException {

        //verifyUserFromTokenAndIfLoggedIn(authorization, dataAccessService.getUserFromUserID(objectNode.get("userID").asInt()).getUsername());
        verifyUserFromTokenAndIfLoggedIn(authorization, objectNode.get("username").asText());

        //User user = dataAccessService.getUserCompanyFromUsername(dataAccessService.getUserFromUserID(objectNode.get("userID").asInt()).getUsername());
        User user = dataAccessService.getUserCompanyFromUsername(objectNode.get("username").asText());
        if(user.getCompany() == null)
        {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "User not company");
        }
        if (objectNode.get("reportID") == null || objectNode.get("reportID").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "ReportID este empty");
        }
        if (objectNode.get("status") == null || objectNode.get("status").asText().length() == 0) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST, "Status este empty");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        currentTime = LocalDateTime.parse(currentTime.toString(), formatter);
        String currentTimeString = currentTime.toString().replace("T", " ");
        currentTimeString = currentTimeString.substring(0, currentTimeString.length() - 10);
        System.out.println(currentTimeString);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(currentTimeString, formatter);

        userService.updateReport(objectNode.get("reportID").asInt(), objectNode.get("status").asText(), dateTime);

    }

    public void verifyUserFromTokenAndIfLoggedIn(String authorization, String username) {
        String jwtToken = null;
        String usernameToken = null;

        if (authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            usernameToken = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }
        if (!username.equals(usernameToken)) {
            throw new APIRequestException(HttpStatus.BAD_REQUEST,
                    "Username ul din token este diferit de cel din request");
        }
    }

}
