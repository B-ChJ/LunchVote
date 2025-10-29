package com.sparta.lunchvote.dto;

import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.entity.UserRoleEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final UserRoleEnum role;
    private final LocalDateTime createdDate;

    public GetUserResponse(Long id, String email, String name, UserRoleEnum role, LocalDateTime createdDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdDate = createdDate;
    }

    public static GetUserResponse from(User user) {
        return new GetUserResponse(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt());
    }
}
