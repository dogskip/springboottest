package me.choi.blog.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter // 모든 필드에 대한 getter 메서드를 자동으로 생성
public class JwtFactory {

    private String subject = "test@email.com"; // 기본 subject 값

    private Date issuedAt = new Date(); // 토큰 발급 시간 (현재 시간)

    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis()); // 토큰 만료 시간 (현재 시간 + 14일)

    private Map<String, Object> claims = emptyMap(); // 기본 클레임 (빈 맵)

    @Builder // 빌더 패턴을 사용하여 객체를 생성할 수 있도록 함
    public JwtFactory(String subject, Date issuedAt, Date expiration,
                      Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject; // subject가 null이 아니면 설정, null이면 기본값 사용
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt; // issuedAt이 null이 아니면 설정, null이면 기본값 사용
        this.expiration = expiration != null ? expiration : this.expiration; // expiration이 null이 아니면 설정, null이면 기본값 사용
        this.claims = claims != null ? claims : this.claims; // claims가 null이 아니면 설정, null이면 기본값 사용
    }

    // 기본값을 사용하여 JwtFactory 객체를 생성하는 정적 메서드
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build(); // 빌더를 사용하여 기본값으로 객체 생성
    }

    // JWT 토큰을 생성하는 메서드
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject) // subject 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더에 JWT 타입 설정
                .setIssuer(jwtProperties.getIssuer()) // 발급자 설정
                .setIssuedAt(issuedAt) // 발급 시간 설정
                .setExpiration(expiration) // 만료 시간 설정
                .addClaims(claims) // 클레임 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘과 비밀키 설정
                .compact(); // 토큰을 생성하고 문자열로 반환
    }
}