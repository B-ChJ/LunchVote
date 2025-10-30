package com.sparta.lunchvote.entity;

import com.sparta.lunchvote.dto.MenuResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lunch_round")
@NoArgsConstructor
public class LunchRound extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    public LunchRound(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }
}
