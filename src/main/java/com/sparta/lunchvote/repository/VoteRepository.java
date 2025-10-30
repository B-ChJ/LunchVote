package com.sparta.lunchvote.repository;

import com.sparta.lunchvote.entity.Menu;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserAndMenu(User user, Menu menu);
}
