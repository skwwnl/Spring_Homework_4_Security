package com.personal.homework_security.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SignupRequestDto {

    /*
    회원 가입을 위한 Dto
            */

    @NotNull(message = "id 입력은 필수입니다.")
//    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "id 입력 조건은 4~10자 사이 소문자, 숫자만 가능합니다")
    // @Pattern(regexp = "^[a-z0-9]")
    private String username; // 계정명

    @NotNull(message = "Password 입력은 필수입니다.")
//    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "Password는 8~15자 사이 대/소문자, 숫자와 특수문자만 가능합니다.")
    //@Pattern(regexp = "^[a-zA-Z0-9]")
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }
}
