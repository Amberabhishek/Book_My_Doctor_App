package com.amber.bookmydoctor;

public class HospitalModel {
    private String imageUrl;
    private String name;
    private String address;


    public HospitalModel(String name, String address, String imageUrl) {

        this.name = name;
        this.address = address;

        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }



    public String getImageUrl() {
        return imageUrl;
    }



}



