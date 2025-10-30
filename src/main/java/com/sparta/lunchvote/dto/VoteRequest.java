package com.sparta.lunchvote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequest {
    private Long menuId;

    public VoteRequest(Long menuId) {
        this.menuId = menuId;
    }
}
