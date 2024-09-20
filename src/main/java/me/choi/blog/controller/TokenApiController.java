package me.choi.blog.controller;

import lombok.RequiredArgsConstructor;
import me.choi.blog.dto.CreateAccessTokenRequest;
import me.choi.blog.dto.CreateAccessTokenResponse;
import me.choi.blog.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Lombok 어노테이션: 모든 final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
// RESTful 웹 서비스의 컨트롤러임을 나타냄
@RestController
public class TokenApiController {
    // TokenService 의존성 주입
    private final TokenService tokenService;

    /**
     * 새로운 액세스 토큰을 생성하는 API 엔드포인트
     *
     * @param request 리프레시 토큰을 포함한 요청 객체
     * @return 새로 생성된 액세스 토큰을 포함한 응답
     */
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        // TokenService를 사용하여 새로운 액세스 토큰 생성
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        // HTTP 상태 코드 201(Created)와 함께 새로운 액세스 토큰을 응답
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
