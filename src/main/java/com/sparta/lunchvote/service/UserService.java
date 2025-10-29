package com.sparta.lunchvote.service;

import com.sparta.lunchvote.dto.CreateUserRequest;
import com.sparta.lunchvote.dto.GetUserResponse;
import com.sparta.lunchvote.dto.LoginRequest;
import com.sparta.lunchvote.dto.LoginResponse;
import com.sparta.lunchvote.entity.User;
import com.sparta.lunchvote.jwt.JwtUtil;
import com.sparta.lunchvote.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final long ACCESS_TOKEN_TIME = 60*60*1000L;
    private final long REFRESH_TOKEN_TIME = 7 * 24 * 60*60*1000L;

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

    //user 로그인
    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        String userEmail = request.getEmail();
        String password = request.getPassword();

        //사용자가 존재하는지 확인
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new IllegalArgumentException("등록된 이메일이 없습니다."));

        // 비밀번호 맞는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String accessToken = jwtUtil.createToken(user.getEmail(), ACCESS_TOKEN_TIME);
        String refreshToken = jwtUtil.createToken(user.getEmail(), REFRESH_TOKEN_TIME);
        jwtUtil.addJwtToCookie(accessToken, response);

        return new LoginResponse(accessToken, refreshToken);
    }

}
