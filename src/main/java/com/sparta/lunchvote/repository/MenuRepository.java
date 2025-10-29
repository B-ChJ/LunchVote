package com.sparta.lunchvote.repository;

import com.sparta.lunchvote.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
