package me.choi.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users") // 데이터베이스 테이블 "users"와 매핑
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 생성
@Getter // 모든 필드에 대한 getter 메서드 생성
@Entity // JPA 엔티티로 지정
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
    @Column(name = "id", updatable = false) // "id" 컬럼과 매핑, 수정 불가
    private Long id;

    @Column(name = "email", nullable = false, unique = true) // "email" 컬럼과 매핑, null 불가, 유니크 제약 조건
    private String email;

    @Column(name = "password") // "password" 컬럼과 매핑
    private String password;

    @Builder // 빌더 패턴을 사용하여 객체 생성
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한을 반환
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        // 사용자의 이름(이메일)을 반환
        return email;
    }

    @Override
    public String getPassword() {
        // 사용자의 비밀번호를 반환
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았음을 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠기지 않았음을 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되지 않았음을 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었음을 반환
        return true;
    }
}