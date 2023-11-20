package com.ll.sb20231114.domain.article.article.controller;

import com.ll.sb20231114.domain.article.article.entity.Article;
import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Validated
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;  // 실제로 rq 에는 진짜 rq가 아닌 대리자가 들어간다.


    @GetMapping("/article/write")
    String showWrite() {
        return "article/article/write";
    }

    @Data
    public static class WriteForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PostMapping("/article/write")
    String write(@Valid WriteForm writeForm) {

        Article article = articleService.write(writeForm.title, writeForm.body);

        return rq.redirect("/article/list","%d번 게시물 생성되었습니다.".formatted(article.getId()));

    }

    @GetMapping("/article/list")
    String showList(Model model, HttpServletRequest req){
        // 쿠키 이름이 loginedMemberId 이것인 것의 값을 가져와서 long타입으로 변환, 만약에 그런게 없다면,0을 반환
        long loginedMemberId = Optional.
                ofNullable(req.getSession().getAttribute("loginedMemberId"))
                .map(id -> (long) id)
                .orElse(0L);


        if(loginedMemberId > 0){
            Member loginedMember = memberService.findById(loginedMemberId).get();
            model.addAttribute("loginedMember", loginedMember);
        }

        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);

        return "article/article/list";
    }

    @GetMapping("/article/detail/{id}")
    String showDetail(Model model,@PathVariable long id) {
        Article article = articleService.findById(id).get();

        model.addAttribute("article",article);

        return "article/article/detail";
    }

    @Data
    public static class ModifyForm{
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @GetMapping("/article/modify/{id}")
    String modifyArticleById(Model model,@PathVariable long id) {

        Article article = articleService.findById(id).get();

        model.addAttribute("article",article);

        return "article/article/modify";
    }

    @PostMapping("/article/modify/{id}")
    String modify(@PathVariable long id, @Valid ModifyForm modifyForm){
        articleService.modify(id,modifyForm.title,modifyForm.body);

        return rq.redirect("/article/list","%d번 게시물 수정되었습니다.".formatted(id));
    }

    @GetMapping("/article/delete/{id}")
    String deleteArticleById(Model model,@PathVariable long id) {

        articleService.delete(id);
        
        return rq.redirect("/article/list","%d번 게시물 삭제되었습니다.".formatted(id));
    }

}