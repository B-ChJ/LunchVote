package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.VoteRequest;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.service.VoteService;
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
public class VoteController {
    private final VoteService voteservice;

    @PostMapping("/votes")
    public ResponseEntity<Void> vote(@RequestBody VoteRequest request, @AuthenticationPrincipal User user) {
        voteservice.vote(request.getMenuId(), user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
