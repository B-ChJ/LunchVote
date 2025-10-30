package com.sparta.lunchvote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.SimpleTimeZone;

@Getter
public class RoundTodayResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final List<MenuResponse> menus;
    private final int totalVotes;

    public RoundTodayResponse(Long id, String name, LocalDate date, List<MenuResponse> menuList, int totalVotes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.menus = menuList;
        this.totalVotes = totalVotes;
    }
}
