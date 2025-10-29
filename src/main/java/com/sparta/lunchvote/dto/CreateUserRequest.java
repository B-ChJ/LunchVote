package com.sparta.lunchvote.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email
    private String email;
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
}