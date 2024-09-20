package me.choi.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 엔티티 클래스 Article 정의
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    // 기본 키로 사용될 id 필드 정의
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 제목을 저장할 title 필드 정의
    @Column(name = "title", nullable = false)
    private String title;

    // 내용을 저장할 content 필드 정의
    @Column(name = "content", nullable = false)
    private String content;

    // 빌더 패턴을 사용하여 Article 객체를 생성하는 생���자 정의
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Article 객체의 title과 content를 업데이트하는 메서드 정의
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}