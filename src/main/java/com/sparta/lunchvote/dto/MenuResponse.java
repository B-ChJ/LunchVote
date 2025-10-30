package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MenuResponse {
    private final Long id;
    private final Long roundId;
    private final String name;
    private final String type;
    private final int price;
    private final int voteCount;

    public MenuResponse(Long id, Long roundId, String name, String type, int price, int voteCount) {
        this.id = id;
        this.roundId = roundId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.voteCount = voteCount;
    }

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(menu.getId(),
                menu.getRound().getId(),
                menu.getName(),
                menu.getType(),
                menu.getPrice(),
                menu.getVoteCount());
    }
}
