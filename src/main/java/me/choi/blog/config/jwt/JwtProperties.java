package me.choi.blog.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // application.yml 파일에 있는 jwt.issuer, jwt.secret-key 프로퍼티를 읽어옴
public class JwtProperties {
    private String issuer;
    private String secretKey;
}