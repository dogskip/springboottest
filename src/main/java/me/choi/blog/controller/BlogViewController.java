package me.choi.blog.controller;

import lombok.RequiredArgsConstructor;
import me.choi.blog.domain.Article;
import me.choi.blog.dto.ArticleViewResponse;
import me.choi.blog.service.BlogService;
import me.choi.blog.dto.ArticleListViewResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        // id 값이 null이면 새로운 Article 객체를 생성하여 뷰에 전달
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            // id 값이 존재하면 해당 id에 해당하는 Article 객체를 조회하여 뷰에 전달
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}