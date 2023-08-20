package com.example.eventmanagement;


public class User {
    private String userId;
    private String username;
    private String address;
    private String email;
    private String phoneno;
    private String password;


    public User() {
        // Required empty constructor for Firebase
    }

    public User(String userId, String username, String address, String email, String phoneno, String password) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;

    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password=password;}


}
