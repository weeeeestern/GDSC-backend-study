package com.example.todoapi.member;


import com.example.todoapi.todo.Todo;
import com.example.todoapi.todo.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void testRegisterMember() throws Exception {
        Member member = new Member("testUser", "password");

        given(memberRepository.findByLoginId(anyString())).willReturn(null);
        memberService.registerMember(member);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testRegisterMember_fail_WhenUserExist() throws Exception {
        Member member = new Member("testUser", "password");
        memberService.registerMember(member);
        Member existMember = new Member("testUser", "password");
        given(memberRepository.findByLoginId(anyString())).willReturn(member);
        Assertions.assertThatThrownBy(()-> memberService.registerMember(existMember))
                .isInstanceOf(Exception.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

    @Test
    void testLogin()throws Exception {
        Member member = new Member("testUser", "password");
        memberService.registerMember(member);
        given(memberRepository.findByLoginId(anyString())).willReturn(member);
        String result = memberService.login(member.getLoginId(), member.getPassword());

        Assertions.assertThat(result).isEqualTo("로그인 성공");
    }

    @Test
    void testLogin_fail_WhenUserNotExist() throws Exception {
        String loginId = "testUser";
        String password = "password";
        given(memberRepository.findByLoginId(anyString())).willReturn(null);

        Assertions.assertThatThrownBy(()-> memberService.login(loginId, password))
                .isInstanceOf(Exception.class)
                .hasMessage("아이디와 일치하는 사용자가 존재하지 않습니다.");
    }

    @Test
    void testLogin_fail_WhenPasswordNotMatch() throws Exception {
        Member member = new Member("testUser", "password");
        memberService.registerMember(member);
        given(memberRepository.findByLoginId(anyString())).willReturn(member);
        String wrongPassword = "wrongPassword";
        Assertions.assertThatThrownBy(()-> memberService.login(member.getLoginId(), wrongPassword))
                .isInstanceOf(Exception.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void testDelete() throws Exception {
        Member member = new Member("testUser", "password");
        memberService.registerMember(member);
        given(memberRepository.findByLoginId(anyString())).willReturn(member);
        String result = memberService.deleteMember(member.getLoginId(), member.getPassword());
        verify(memberRepository, times(1)).delete(member);
        Assertions.assertThat(result).isEqualTo("회원 탈퇴가 완료되었습니다.");
    }

    @Test
    void testDelete_fail_WhenUserNotExist() throws Exception {
        String loginId = "testUser";
        String password = "password";
        given(memberRepository.findByLoginId(anyString())).willReturn(null);
        Assertions.assertThatThrownBy(()-> memberService.deleteMember(loginId, password))
                .isInstanceOf(Exception.class)
                .hasMessage("일치하는 사용자가 존재하지 않습니다.");
    }

    @Test
    void testDelete_fail_WhenPasswordNotMatch() throws Exception {
        Member member = new Member("testUser", "password");
        memberService.registerMember(member);
        given(memberRepository.findByLoginId(anyString())).willReturn(member);
        String wrongPassword = "wrongPassword";
        Assertions.assertThatThrownBy(()-> memberService.deleteMember(member.getLoginId(), wrongPassword))
                .isInstanceOf(Exception.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
