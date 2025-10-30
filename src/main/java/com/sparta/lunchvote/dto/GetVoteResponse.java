package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetVoteResponse {
    private final Long id;
    private final Long menuId;
    private final LocalDateTime createdAt;

    public static GetVoteResponse from(Vote vote) {
        return new GetVoteResponse(vote.getId(),
                vote.getMenu().getId(),
                vote.getCreatedAt());
    }
}
