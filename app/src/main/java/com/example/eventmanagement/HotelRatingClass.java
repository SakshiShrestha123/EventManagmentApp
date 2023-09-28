package com.example.eventmanagement;

public class HotelRatingClass {
    private String userId; // User's unique identifier
    private String hotelId; // Unique identifier for the hotel
    private float userRating; // User's rating
    private String userFeedback; // User's feedback
    private float averageRating; // Average rating for the hotel

    // Default constructor (required for Firebase)
    public HotelRatingClass() {
        // Default constructor required for calls to DataSnapshot.getValue(HotelRating.class)
    }

    public HotelRatingClass(String userId, String hotelId, float averageRating, String userFeedback) {
        // Initialize the HotelRatingClass object with the provided arguments
        this.userId = userId;
        this.hotelId = hotelId;
        this.averageRating = averageRating;
        this.userFeedback = userFeedback;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}
