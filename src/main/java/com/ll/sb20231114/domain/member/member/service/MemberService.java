package com.ll.sb20231114.domain.member.member.service;

import com.ll.sb20231114.domain.member.member.entity.Member;
import com.ll.sb20231114.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 저는 단 한번만 생성되고, 그 이후에는 재사용되는 객체입니다.
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password) {
        Member member = new Member(username, password);

        memberRepository.save(member);

        return member;
    }
    

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    public void delete(long id) {
        memberRepository.delete(id);
    }

    public void modify(long id,String username, String password) {
        Member member = findById(id).get();
        member.setUsername(username);
        member.setPassword(password);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}