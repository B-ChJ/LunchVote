package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.LunchRound;
import com.sparta.lunchvote.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoundListResponse {
    private final Long id;
    private final Long userId;
    private final LocalDate date;
    private final int menuCount;
    private final int totalVotes;
    private MenuResponse winnerMenu;

    public static RoundListResponse from(LunchRound round) {
        List<Menu> menus = round.getMenus();

        int totalVotes = menus.stream()
                .mapToInt(Menu::getVoteCount)
                .sum();

        Menu winner = menus.stream()
                .max(Comparator.comparingInt(Menu::getVoteCount))
                .orElse(null);

        MenuResponse winnerMenu = MenuResponse.from(winner);

        return new RoundListResponse(round.getId(),
                round.getUser().getId(),
                round.getDate(),
                menus.size(),
                totalVotes,
                winnerMenu);
    }
}
