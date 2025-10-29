package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.CreateUserRequest;
import com.sparta.lunchvote.dto.GetUserResponse;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //user 회원가입
    @Transactional
    public GetUserResponse save(CreateUserRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(password)
                .name(request.getName())
                .build();
        User savedUser = userRepository.save(user);

        return GetUserResponse.from(savedUser);
    }

}
