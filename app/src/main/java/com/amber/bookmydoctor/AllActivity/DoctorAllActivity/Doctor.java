package com.amber.bookmydoctor.AllActivity.DoctorAllActivity;



public class Doctor {
    private String age;
    private String doctorType;
    private String city;
    private String contact;
    private String about;
    private String gender;
    private String imageUrl;
    private String name;
    private String role;

    // Default constructor required for Firebase
    public Doctor() {}

    public Doctor(String name, String city, String age, String doctorType, String contact, String about, String gender,  String imageUrl, String role) {
        this.age = age;
        this.doctorType = doctorType;
        this.city = city;
        this.contact = contact;
        this.about = about;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.name = name;
        this.role = role;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Add getters and setters for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
