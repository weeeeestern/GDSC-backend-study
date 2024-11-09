package com.example.todoapi.member;

import org.assertj.core.api.Assertions;
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
    public void saveTest() {
        Member member = new Member("user","password");
        memberRepository.save(member);

        Assertions.assertThat(member.getLoginId()).isNotNull();
    }

    @Test
    @Transactional
    public void findByUserIdTest() {
        Member member1 = new Member("user1","password1");
        memberRepository.save(member1);

        Member member2 = new Member("user2","password2");
        memberRepository.save(member2);

        Member found = memberRepository.findByUserId(member1.getUserId());
        Assertions.assertThat(found.getUserId()).isEqualTo(member1.getUserId());
    }

    @Test
    @Transactional
    public void findByLoginIdTest() {
        Member member1 = new Member("user1","password1");
        memberRepository.save(member1);

        Member member2 = new Member("user2","password2");
        memberRepository.save(member2);

        Assertions.assertThat(memberRepository.findByLoginId("user1")).isEqualTo(member1);
    } // h2 콘솔로 확인 과정은 건너뛰었다. 1차 캐시 막기 과정(flush) 생략

    @Test
    @Transactional
    public void deleteTest() {
        Member April = new Member("April","AprilPassword");
        memberRepository.save(April);
        memberRepository.delete(April);
        Assertions.assertThat(memberRepository.findByLoginId("April")).isNull();

    }
}

