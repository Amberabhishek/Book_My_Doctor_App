package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDetails {
    private String bloodType;
    private String name;
    private int age;
    private String contact;
    private String gender;

    UserDetails(){

    }

    public UserDetails(String bloodType, String name, int age, String contact, String gender) {
        this.bloodType = bloodType;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
