package me.choi.blog.service;

import lombok.RequiredArgsConstructor;
import me.choi.blog.config.jwt.TokenProvider;
import me.choi.blog.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

// Lombok 어노테이션: 모든 final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
// 이 클래스를 Spring의 서비스 컴포넌트로 지정
@Service
public class TokenService {
    // JWT 토큰 생성 및 검증을 담당하는 컴포넌트
    private final TokenProvider tokenProvider;
    // RefreshToken 관련 서비스
    private final RefreshTokenService refreshTokenService;
    // User 관련 서비스
    private final UserService userService;

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성
     *
     * @param refreshToken 클라이언트로부터 받은 리프레시 토큰
     * @return 새로 생성된 액세스 토큰
     * @throws IllegalArgumentException 리프레시 토큰이 유효하지 않을 경우 발생
     */
    public String createNewAccessToken(String refreshToken) {
        // 리프레시 토큰의 유효성 검사
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        // 리프레시 토큰으로부터 사용자 ID를 조회
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        // 사용자 ID로 User 객체 조회
        User user = userService.findById(userId);

        // 새로운 액세스 토큰 생성 (유효기간 2시간)
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
