package me.choi.blog.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.choi.blog.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 모든 final 필드에 대한 생성자를 자동으로 생성하는 Lombok 어노테이션
@RequiredArgsConstructor
// 각 요청에 대해 한 번만 실행되는 필터를 구현하기 위해 OncePerRequestFilter를 상속
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // JWT 토큰을 처리하는 프로바이더
    private final TokenProvider tokenProvider;

    // Authorization 헤더의 이름을 상수로 정의
    private final static String HEADER_AUTHORIZATION = "Authorization";
    // Bearer 토큰임을 나타내는 접두사를 상수로 정의
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 Authorization 값을 가져옴
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // Authorization 헤더에서 실제 토큰 값을 추출
        String token = getAccessToken(authorizationHeader);

        // 토큰이 유효한 경우
        if (tokenProvider.validToken(token)) {
            // 토큰에서 인증 정보를 가져옴
            Authentication authentication = tokenProvider.getAuthentication(token);
            // SecurityContext에 인증 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 토큰이 유효하지 않은 경우 SecurityContext에서 인증 정보를 제거하고 getAccessToken 메서드에서 null을 반환
        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 실제 접근 토큰을 추출하는 메서드
    private String getAccessToken(String authorizationHeader) {
        // 헤더가 null이 아니고 "Bearer "로 시작하는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            // "Bearer " 다음의 문자열(실제 토큰)을 반환
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        // 유효한 토큰이 없는 경우 null 반환
        return null;
    }
}
