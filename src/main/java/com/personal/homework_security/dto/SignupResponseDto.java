package com.personal.homework_security.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class SignupResponseDto {

        private HttpStatus code;

        private String message;

    public SignupResponseDto(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
