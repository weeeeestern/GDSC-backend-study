package com.example.todoapi.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void testRegisterMember() {
        Member member1 = new Member("user1", "password1");
        Member member2 = new Member("user2", "password2");

        boolean result1 = memberRepository.registerMember(member1);
        boolean result2 = memberRepository.registerMember(member2);

        Assertions.assertThat(result1).isEqualTo(true);
        Assertions.assertThat(result2).isEqualTo(true);

        boolean result3 = memberRepository.registerMember(member1);
        Assertions.assertThat(result3).isEqualTo(false);
    }

    @Test
    @Transactional
    public void testLogin(){
        Member member = new Member("testUser", "Password");
        memberRepository.registerMember(member); //회원가입

        // 올바른 정보로 로그인
        Object correctLogin = memberRepository.login("testUser", "Password");
        Assertions.assertThat(correctLogin).isInstanceOf(Member.class);

        // 틀린 정보로 로그인
        Object wrongLogin = memberRepository.login("testUser", "wrongPassword");
        Assertions.assertThat(wrongLogin).isEqualTo("일치하는 사용자가 없습니다.");
    }
}

