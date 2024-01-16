package com.james.ourht.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long joinMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return member;
    }

    public List<Member> findMembers() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public void updateMember(Long memberId, String name, int age, String email) {
        Member member = memberRepository.findById(memberId).get();
        member.setName(name);
        member.setAge(age);
        member.setEmail(email);
    }




}
