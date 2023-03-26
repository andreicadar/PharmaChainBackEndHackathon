package model;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Report {
    private int reportID;
   private LocalDateTime postTime;
   private Pharmacy pharmacy;
   private User user;
   private Status status;

    private Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Report(int reportID, LocalDateTime postTime, Pharmacy pharmacy, Medicine medicine, User user, Status status) {
        this.reportID = reportID;
        this.postTime = postTime;
        this.pharmacy = pharmacy;
        this.medicine = medicine;
        this.user = user;
        this.status = status;
    }

}
