package com.amber.bookmydoctor;

public class User {
    private String name;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private String imageUrl; // New field for storing the image URL

    private String role;
    // Default constructor
    public User() {
        // Default constructor required for Firebase
    }

    // Parameterized constructor
    public User(String name, String phoneNumber, String dateOfBirth, String gender, String imageUrl, String role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter for dateOfBirth
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter for dateOfBirth
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter for gender
    public String getGender() {
        return gender;
    }

    // Setter for gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for imageUrl
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // Getter for imageUrl
    public String getRole() {
        return role;
    }

    // Setter for imageUrl
    public void setRole(String role) {
        this.role = role;
    }

}
