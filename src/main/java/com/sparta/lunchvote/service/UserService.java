package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.CreateUserRequest;
import com.sparta.lunchvote.dto.GetUserResponse;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public GetUserResponse save(CreateUserRequest request) {
        User user = new User(request.getEmail(), request.getPassword(), request.getName());
        User savedUser = userRepository.save(user);

        return GetUserResponse.from(savedUser);
    }

}
