package me.choi.blog.service;

import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.Article;
import me.choi.blog.dto.AddArticleRequest;
import me.choi.blog.dto.UpdateArticleRequest;
import me.choi.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //final로 선언된 필드를 인자값으로 하는 생성자를 생성
@Service //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    //블로그 글 전체 조회 메서드
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //블로그 글 단건 조회 메서드
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id=" + id));
    }

    //블로그 글 삭제 메서드
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    //블로그 글 수정 메서드
    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id=" + id));
        article.update(request.getTitle(), request.getContent());
        return article;
    }
}