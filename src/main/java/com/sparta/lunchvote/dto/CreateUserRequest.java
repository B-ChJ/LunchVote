package com.sparta.lunchvote.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String email;
    private String password;
    private String name;
}