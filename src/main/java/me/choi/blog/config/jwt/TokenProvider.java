package me.choi.blog.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성
@Service // 이 클래스가 서비스 컴포넌트임을 나타냄
public class TokenProvider {

    private final JwtProperties jwtProperties; // JWT 관련 설정을 담고 있는 프로퍼티 클래스

    // JWT 토큰 생성
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date(); // 현재 시간
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user); // 만료 시간을 더해 토큰 생성
    }

    // JWT 토큰을 실제로 생성하는 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date(); // 현재 시간

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 토큰의 타입을 지정
                .setIssuer(jwtProperties.getIssuer()) // 토큰 발급자 지정
                .setIssuedAt(now) // iat 토큰 발급 시간 지정
                .setExpiration(expiry) // exp 토큰 만료 시간 지정
                .setSubject(user.getEmail()) // sub 토큰 제목 지정 (사용자의 이메일)
                .claim("id", user.getId()) // 클레임 ID를 지정 (사용자의 ID)

                // 토큰 서명
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘과 비밀키 사용
                .compact(); // 토큰을 생성하고 문자열로 반환
    }

    // JWT 토큰의 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 서명 검증을 위한 비밀키 설정
                    .parseClaimsJws(token); // 토큰을 파싱하고 서명을 검증

            return true; // 유효한 토큰인 경우 true 반환
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰인 경우 false 반환
        }
    }

    // JWT 토큰에서 인증 정보를 가져옴
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token); // 토큰에서 클레임을 추출
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); // 사용자 권한 설정

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), // 사용자 정보 생성
                token, // 인증된 토큰
                authorities // 사용자 권한
        );
    }

    // JWT 토큰에서 사용자 ID를 추출
    public Long getUserId(String token) {
        Claims claims = getClaims(token); // 토큰에서 클레임을 추출
        return claims.get("id", Long.class); // 사용자 ID를 반환
    }

    // JWT 토큰에서 클레임을 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 서명 검증을 위한 비밀키 설정
                .parseClaimsJws(token) // 토큰을 파싱하고 클레임을 추출
                .getBody(); // 클레임 반환
    }
}
