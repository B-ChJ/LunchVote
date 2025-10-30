package com.sparta.lunchvote.repository;

import com.sparta.lunchvote.entity.LunchRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RoundRepository extends JpaRepository<LunchRound, Long> {
    Optional<LunchRound> findByDate(LocalDate date);
    boolean existsByDate(LocalDate date);
}
