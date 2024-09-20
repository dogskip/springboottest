package me.choi.blog.service;

import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.User;
import me.choi.blog.dto.AddUserRequest;
import me.choi.blog.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성
@Service // 이 클래스가 서비스 레이어의 빈임을 나타냄
public class UserService {
    private final UserRepository userRepository; // 사용자 저장소
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화

    // 사용자를 저장하고, 저장된 사용자의 ID를 반환
    public Long save(AddUserRequest dto) {
        // 사용자 객체를 생성하고, 비밀번호를 암호화하여 저장
        return userRepository.save(User.builder()
                .email(dto.getEmail()) // 이메일 설정
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 비밀번호 암호화 후 설정
                .build()).getId(); // 저장된 사용자 ID 반환
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}