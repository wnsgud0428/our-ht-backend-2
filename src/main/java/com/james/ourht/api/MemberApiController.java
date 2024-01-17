package com.james.ourht.api;

import com.james.ourht.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public void saveMember() {

    }

    static class CreateMemberRequest {
        private String name;
        private String email;
        private int age;
    }


}
