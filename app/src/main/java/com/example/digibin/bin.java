package com.example.digibin;

public class bin {

    // variables for storing our data.
    private String binId, binLocation;


    public bin() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public bin( String BinId, String binLocation) {
        this.binId = BinId;
        this.binLocation = binLocation;
    }

    public String getBinId() {
        return binId;
    }

    public void setBinId(String id) {
        this.binId = id;
    }

    public String getBinLocation() {
        return binLocation;
    }

    // setter method for all variables.
    public void setBinLocation(String location) {
        this.binLocation = location;
    }

}

