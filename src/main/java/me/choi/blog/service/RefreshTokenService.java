package me.choi.blog.service;

import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.RefreshToken;
import me.choi.blog.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

// Lombok 어노테이션: 모든 final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
// 이 클래스를 Spring의 서비스 컴포넌트로 지정
@Service
public class RefreshTokenService {

    // RefreshToken 엔티티에 대한 데이터베이스 작업을 수행하는 리포지토리
    // final로 선언되어 있어 @RequiredArgsConstructor에 의해 생성자 주입됨
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 주어진 리프레시 토큰 문자열로 RefreshToken 엔티티를 조회
     *
     * @param refreshToken 조회할 리프레시 토큰 문자열
     * @return 조회된 RefreshToken 엔티티
     * @throws IllegalArgumentException 해당 리프레시 토큰이 존재하지 않을 경우 발생
     */
    public RefreshToken findByRefreshToken(String refreshToken) {
        // 리포지토리의 findByRefreshToken 메서드를 호출하여 RefreshToken 엔티티를 조회
        // orElseThrow를 사용하여 결과가 없을 경우 예외를 발생시킴
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
