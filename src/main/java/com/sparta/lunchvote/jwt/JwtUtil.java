package com.sparta.lunchvote.jwt;

import com.sparta.lunchvote.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer ";

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_KEY = "auth";

    private final JwtParser parser;
    private Key secretKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    public JwtUtil(@Value("${jwt.secret.key}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(30)
                .build();
    }

    //1. JWT create
    public String createToken(String userEmail, long TOKEN_TIME) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userEmail) // 사용자 식별자값(ID)
//                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(secretKey, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    //2. JWT Cookie에 저장
    public void addJwtToCookie(String token, HttpServletResponse response) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    //3. JWT substring
    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    //4. JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("유효하지 않은 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("지원되지 않는 토큰입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("만료된 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 토큰입니다.");
        }
        return false;
    }

    //5. JWT 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
    }
}
