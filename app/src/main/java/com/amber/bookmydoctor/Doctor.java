package com.amber.bookmydoctor;

public class Doctor {
    private String name;
    private String doctorType;
    private String about;
    private String imageUrl;

    private String Did;

    private String city;

    public Doctor() {
        // Default constructor required for Firebase
    }

    public Doctor(String name, String doctorType, String about, String imageUrl, String did, String city) {
        this.name = name;
        this.doctorType = doctorType;
        this.about = about;
        this.imageUrl = imageUrl;
        this.Did = did;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setSpeciality(String speciality) {
        this.doctorType = doctorType;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        this.Did = did;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public boolean getId() {
        return false;

    }
}
