package com.amber.bookmydoctor.MedicinesAllListModel;

public class SkinModel {


    private String imageUrl;
    private String medicineName;
    private String medicinePrice;
    private String companyName;
    private int manufacturingYear;

    public SkinModel() {
        // Default constructor required for Firebase
    }

    public SkinModel(String imageUrl, String medicineName, String medicinePrice, String companyName, int manufacturingYear) {
        this.imageUrl = imageUrl;
        this.medicineName = medicineName;
        this.medicinePrice = medicinePrice;
        this.companyName = companyName;
        this.manufacturingYear = manufacturingYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(String medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }
}
