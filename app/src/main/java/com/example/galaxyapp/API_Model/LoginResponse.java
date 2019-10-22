package com.example.galaxyapp.API_Model;

public class LoginResponse {
    private String message;
    private String token;
    private String verified;
    private String status;
    private String username;
    private  String id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getVerified() {
        return verified;
    }
}
