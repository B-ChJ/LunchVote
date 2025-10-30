package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoundResponse {
    private final Long id;
    private final Long userId;
    private final LocalDate date;
    private final List<MenuResponse> menus;

    public static RoundResponse from(LunchRound round) {
        return new RoundResponse(round.getId(),
                round.getUser().getId(),
                round.getDate(),
                round.getMenus().stream().map(MenuResponse::from).toList());
    }
}
