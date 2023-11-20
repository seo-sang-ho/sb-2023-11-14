package com.ll.sb20231114.domain.member.member.controller;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.service.MemberService;
import com.ll.sb20231114.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;  // 실제로 rq 에는 진짜 rq가 아닌 대리자가 들어간다.

    @Data
    public static class LoginForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @GetMapping("/member/login")
    String showLogin() {
        return "member/member/login";
    }

    @PostMapping("/member/login")
    String login(@Valid LoginForm loginForm, HttpServletRequest req, HttpServletResponse response) {
        Member member = memberService.findByUsername(loginForm.username).get();
        // 없는 optional 에 get() 하면 터진다.
        
        if( !member.getPassword().equals(loginForm.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        rq.setSessionAttr("loginMemberId",member.getId());

        return rq.redirect("/article/list", "로그인이 완료되었습니다.");

    }

    @PostMapping("/member/logout")
    String logout

    @GetMapping("/member/join")
    String showJoin() {
        return "member/member/join";
    }

    @Data
    public static class JoinForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/member/join")
    String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.username, joinForm.password);

        return rq.redirect("/member/login", "회원가입이 완료되었습니다.");

    }
}