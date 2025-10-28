package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.CreateUserRequest;
import com.sparta.lunchvote.dto.GetUserResponse;
import com.sparta.lunchvote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<GetUserResponse> signup(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }
}
