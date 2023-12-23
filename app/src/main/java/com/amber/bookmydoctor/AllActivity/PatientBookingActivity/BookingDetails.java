package com.amber.bookmydoctor.AllActivity.PatientBookingActivity;

public class BookingDetails {
    private String userName;
    private String userEmail;
    private String userPhone;
    private String selectedDate;
    private String selectedTimeSlot;
    private  String did;
    private  String pid;

    // Default constructor required for Firebase
    public BookingDetails() {
    }

    public BookingDetails(String userName, String userEmail, String userPhone, String selectedDate, String selectedTimeSlot, String did, String pid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.selectedDate = selectedDate;
        this.selectedTimeSlot = selectedTimeSlot;
        this.did = did;
        this.pid = pid;
    }

    // Add getters and setters for each field

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTimeSlot() {
        return selectedTimeSlot;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDid() {
        return did;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }
}
