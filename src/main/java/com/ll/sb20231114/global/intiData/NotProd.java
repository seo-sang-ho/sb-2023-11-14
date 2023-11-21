package com.ll.sb20231114.global.intiData;

import com.ll.sb20231114.domain.article.article.service.ArticleService;
import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
public class NotProd {
    @Bean
    public ApplicationRunner initNotProd(
            MemberService memberService,
            ArticleService articleService
    ){
        return args -> {
            Member admin = memberService.join("admin", "1234");
            Member user1 = memberService.join("user1", "1234");
            Member user2 = memberService.join("user2", "1234");

            articleService.write(admin,"제목1","내용1");
            articleService.write(user1,"제목2","내용2");
            articleService.write(user2,"제목3","내용3");
        };
    }
}
