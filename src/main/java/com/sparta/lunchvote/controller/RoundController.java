package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.RoundListResponse;
import com.sparta.lunchvote.dto.RoundRequest;
import com.sparta.lunchvote.dto.RoundResponse;
import com.sparta.lunchvote.dto.RoundTodayResponse;
import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.service.RoundService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService roundservice;

    @PostMapping("/rounds")
    public ResponseEntity<RoundResponse> create(@RequestBody RoundRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roundservice.createRound(request, user));
    }
    @GetMapping("/rounds/today")
    public ResponseEntity<RoundTodayResponse> getToday(@AuthenticationPrincipal User user) {
        RoundTodayResponse result = roundservice.getToday(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/rounds")
    public ResponseEntity<List<RoundListResponse>> getAll(@AuthenticationPrincipal User user) {
        List<RoundListResponse> result = roundservice.getAll(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/rounds/{roundId}")
    public ResponseEntity<Void> deleteROund(@PathVariable Long roundId, @AuthenticationPrincipal User user) {
        roundservice.delete(roundId, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
