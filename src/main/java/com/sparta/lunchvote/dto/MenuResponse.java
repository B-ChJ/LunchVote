package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.Menu;
import lombok.Getter;

@Getter
public class MenuResponse {
    private final Long id;
        private final String name;
    private final String type;
    private final int price;
    private final int voteCount;

    public MenuResponse(Long id, String name, String type, int price, int voteCount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.voteCount = voteCount;
    }

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(menu.getId(),
                menu.getName(),
                menu.getType(),
                menu.getPrice(),
                menu.getVoteCount());
    }
}
