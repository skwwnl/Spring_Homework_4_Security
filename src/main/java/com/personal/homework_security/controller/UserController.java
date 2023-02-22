package com.personal.homework_security.controller;

import com.personal.homework_security.dto.LoginRequestDto;
import com.personal.homework_security.dto.LoginResponseDto;
import com.personal.homework_security.dto.SignupRequestDto;
import com.personal.homework_security.dto.SignupResponseDto;
import com.personal.homework_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userservice;

    // ADMIN_TOKEN
    // 회원 가입할 때 Admin or User인지 확인하기 위한 admin token을 가져옴
    // 원래는 service인데 빠른 진행을 위해 controller에서 작성
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        // 회원가입 정보 유효성 확인
//        if(result.hasErrors()){
//            return Res
//        }
        return userservice.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userservice.login(loginRequestDto, response);
    }
}