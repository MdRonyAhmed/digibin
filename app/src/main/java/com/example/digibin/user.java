package com.example.digibin;

public class user {

    // variables for storing our data.
    private String Name, Location,Status, PhoneNumber, Email;


    public user() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public user( String userName, String userLocation,String PhoneNumber, String status, String email) {
        this.Name = userName;
        this.Location = userLocation;
        this.PhoneNumber = PhoneNumber;
        this.Status = status;
        this.Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getLocation() {
        return Location;
    }

    // setter method for all variables.
    public void setLocation(String location) {
        this.Location = location;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}

