// Appointment.java

package com.amber.bookmydoctor.AllActivity.MyAppointment;

public class Appointment {
    private String appointmentId;
    private String selectedDate;
    private String selectedTimeSlot;
    private String userEmail;
    private String userName;
    private String userPhone;

    // Default constructor (required for Firebase)
    public Appointment() {
        // Default constructor required for calls to DataSnapshot.getValue(Appointment.class)
    }

    // Parameterized constructor
    public Appointment(String appointmentId, String selectedDate, String selectedTimeSlot, String userEmail, String userName, String userPhone) {
        this.appointmentId = appointmentId;
        this.selectedDate = selectedDate;
        this.selectedTimeSlot = selectedTimeSlot;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    // Getters and setters for other fields...

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
