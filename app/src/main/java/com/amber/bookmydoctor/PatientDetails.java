package com.amber.bookmydoctor;

public class PatientDetails {
    private String selectedDate;
    private String selectedTimeSlot;
    private String userEmail;
    private String userName;
    private String userPhone;

    public PatientDetails() {
        // Default constructor required for Firebase
    }

    public PatientDetails(String selectedDate, String selectedTimeSlot, String userEmail, String userName, String userPhone) {
        this.selectedDate = selectedDate;
        this.selectedTimeSlot = selectedTimeSlot;
        this.userEmail = userEmail;
        this.userName = userName;
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

    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
