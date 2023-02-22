package com.personal.homework_security.dto;


import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String username;
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
