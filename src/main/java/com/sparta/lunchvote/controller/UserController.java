package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.CreateUserRequest;
import com.sparta.lunchvote.dto.GetUserResponse;
import com.sparta.lunchvote.dto.LoginRequest;
import com.sparta.lunchvote.dto.LoginResponse;
import com.sparta.lunchvote.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<GetUserResponse> signup(@Valid @RequestBody CreateUserRequest request) {
        log.info("회원가입 API 호출");
        return ResponseEntity.ok(userService.save(request));
    }

    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse res) {
        log.info("로그인 API 호출");
        return ResponseEntity.ok(userService.login(request, res));
    }
}
