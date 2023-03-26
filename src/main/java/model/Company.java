package model;

public class Company {

    private String name;
    private int companyID;

    public String getName() {
        return name;
    }
    public int getCompanyID() {
        return companyID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Company(String name, int companyID) {
        this.name = name;
        this.companyID = companyID;
    }

}
