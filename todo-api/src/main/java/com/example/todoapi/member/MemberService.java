package com.example.todoapi.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    @Transactional
    public String registerMember(Member member) throws Exception {
        if (memberRepository.findByLoginId(member.getLoginId()) != null) {
            throw new Exception("이미 존재하는 회원입니다.");
        }
        else {
            memberRepository.save(member);
            return "회원 가입이 완료되었습니다.";
        }
    }

    @Transactional(readOnly = true)
    public String login(String loginId, String password) throws Exception {
        Member member = memberRepository.findByLoginId(loginId);

        if (member == null) {
           throw new Exception("아이디와 일치하는 사용자가 존재하지 않습니다.") ;
        } else {
            if (member.getPassword().equals(password)) {
                return "로그인 성공";
            } else {
                throw new Exception("비밀번호가 일치하지 않습니다.");
            }
        }
    }

    @Transactional
    public String deleteMember(String loginId, String password) throws Exception {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new Exception("일치하는 사용자가 존재하지 않습니다.");
        }
        if (!member.getPassword().equals(password)) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        } else{
            memberRepository.delete(member);
            return "회원 탈퇴가 완료되었습니다.";
        }
    }

}
