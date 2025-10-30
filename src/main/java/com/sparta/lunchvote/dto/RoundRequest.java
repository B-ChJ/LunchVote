package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoundRequest {
    private LocalDate date;
    private List<Menu> menus;
}
