package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.RoundRequest;
import com.sparta.lunchvote.dto.RoundResponse;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService roundservice;

    @PostMapping("/rounds")
    public ResponseEntity<RoundResponse> create(@RequestBody RoundRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roundservice.createRound(request, user));
    }
}
