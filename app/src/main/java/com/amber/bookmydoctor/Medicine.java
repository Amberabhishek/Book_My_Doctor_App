package com.amber.bookmydoctor;

public class Medicine {
    private String name;
    private String company;
    private String price;
    private String imageUrl;

    public Medicine(String name, String company, String price, String imageUrl) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

