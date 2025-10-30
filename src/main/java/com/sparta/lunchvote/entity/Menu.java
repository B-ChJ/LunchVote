package com.sparta.lunchvote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private LunchRound round;

    private String name;
    private String type;
    private int price;

    private int voteCount;
}
