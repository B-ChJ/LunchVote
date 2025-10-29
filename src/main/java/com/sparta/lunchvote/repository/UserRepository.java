package com.sparta.lunchvote.repository;

import com.sparta.lunchvote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
