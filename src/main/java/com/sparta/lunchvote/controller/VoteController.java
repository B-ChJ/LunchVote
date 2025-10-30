package com.sparta.lunchvote.controller;

import com.sparta.lunchvote.dto.GetVoteResponse;
import com.sparta.lunchvote.dto.VoteRequest;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteservice;

    @PostMapping("/votes")
    public ResponseEntity<Void> vote(@RequestBody VoteRequest request, @AuthenticationPrincipal User user) {
        voteservice.vote(request.getMenuId(), user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/votes/my")
    public ResponseEntity<List<GetVoteResponse>> getMyVotes(@AuthenticationPrincipal User user) {
        List<GetVoteResponse> result = voteservice.getMyVote(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/votes/{voteId}")
    public ResponseEntity<Void> delete(@PathVariable Long voteId, @AuthenticationPrincipal User user) {
        voteservice.deleteVote(voteId, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
