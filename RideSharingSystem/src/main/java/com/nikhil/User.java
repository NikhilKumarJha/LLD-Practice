package com.nikhil;

import java.util.UUID;

public abstract class User {
    private String id;
    private String name;
    private String emailId;
    private String phoneNumber;
    private double rating;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public User(String name, String emailId, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.rating = 5.0; // Default rating
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
