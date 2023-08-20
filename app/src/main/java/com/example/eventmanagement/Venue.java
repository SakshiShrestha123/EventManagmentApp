package com.example.eventmanagement;

public class Venue {
    private String venueId; // Add this field
    private String name;
    private String address;
    private String occupancy;
    private String email;
    private String number;

    // Empty constructor required for Firebase
    public Venue() {
    }

    // Constructor with all fields including venueId
    public Venue(String venueId, String name, String address, String occupancy, String email, String number) {
        this.venueId = venueId;
        this.name = name;
        this.address = address;
        this.occupancy = occupancy;
        this.email = email;
        this.number = number;
    }

    // Getter and setter methods for all fields
    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    // ... Implement getter and setter methods for other fields
}
