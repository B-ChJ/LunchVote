package com.sparta.lunchvote.repository;

import com.sparta.lunchvote.entity.LunchRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RoundRepository extends JpaRepository<LunchRound, Long> {
    boolean existsByDate(LocalDate date);
}
