package com.james.ourht.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("박준형");
        member.setAge(27);
        member.setEmail("wnsgud0428@naver.com");

        // when
        Long saveId = memberService.joinMember(member);

        // then
        Member findMember = memberRepository.findById(saveId).get();
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findMemberById() {
    }

    @Test
    void findMembers() {
    }

    @Test
    void updateMember() {
    }
}