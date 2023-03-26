package model;

import javax.swing.plaf.synth.SynthComboBoxUI;

public class Pharmacy {
    int pharmacyID;
    Company company;
    String location;

    public int getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(int pharmacy_ID) {
        this.pharmacyID = pharmacyID;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Pharmacy(int pharmacyID, Company company, String location) {
        this.pharmacyID = pharmacyID;
        this.company = company;
        this.location = location;
    }

}
