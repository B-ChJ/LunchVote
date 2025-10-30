package com.sparta.lunchvote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "menu")
@NoArgsConstructor
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

    public Menu(String name, String type, int price, LunchRound round) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.round = round;
    }
}
