package com.cpe.cardgame.viewmodel;

public class AuthDTO {
    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    private String password;
    public AuthDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthDTO() {
        this.username = "";
        this.password = "";
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}