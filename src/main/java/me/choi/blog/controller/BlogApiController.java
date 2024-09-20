package me.choi.blog.controller;

import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.Article;
import me.choi.blog.dto.ArticleResponse;
import me.choi.blog.dto.UpdateArticleRequest;
import me.choi.blog.service.BlogService;
import me.choi.blog.dto.AddArticleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 자바 객체를 바로 넣어주기 위해 사용
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        // HTTP 상태 코드 201 Created를 반환하고, 저장된 Article 객체를 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        // blogService.findAll() 메서드를 호출하여 Article 객체를 가져옴
        // .stream() 메서드를 호출하여 스트림을 생성
        // .map(ArticleResponse::new) 메서드를 호출하여 각 Article 객체를 ArticleResponse 객체로 변환
        // .toList() 메서드를 호출하여 스트림을 리스트로 변환
        List<ArticleResponse> articles = blogService.findAll()
                .stream() // Article 객체 리스트를 스트림으로 변환
                .map(ArticleResponse::new) // 각 Article 객체를 ArticleResponse 객체로 매핑
                .toList(); // 스트림을 리스트로 변환
        // HTTP 상태 코드 200 OK를 반환하고, 변환된 ArticleResponse 리스트를 반환
        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}