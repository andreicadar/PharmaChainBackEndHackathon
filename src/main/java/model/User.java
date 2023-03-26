package model;

public class User {

    private String username;
    private int userID;

    private String email;

    private String password;

    private Company company;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User(String username, int userID, String password, String email) {
        super();
        this.username = username;
        this.userID = userID;
        this.password = password;
        this.email = email;
    }

    public User(String username, int userID, String password, String email, Company company) {
        super();
        this.username = username;
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.company = company;
    }

    public User(String username, int userID, String email) {
        super();
        this.username = username;
        this.userID = userID;
        this.email = email;
    }

    public User(String username, int userID, String email, Company company) {
        super();
        this.username = username;
        this.userID = userID;
        this.email = email;
        this.company = company;
    }

}
