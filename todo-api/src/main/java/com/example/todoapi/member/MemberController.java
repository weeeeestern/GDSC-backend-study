package com.example.todoapi.member;

import com.example.todoapi.member.dto.MemberDeleteRequest;
import com.example.todoapi.member.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerMember(@RequestBody Member member) throws Exception {
        memberService.registerMember(member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginMember(@RequestBody MemberLoginRequest request) throws Exception {
        memberService.login(request.getLoginId(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@RequestBody MemberDeleteRequest request) throws Exception {
        memberService.deleteMember(request.getLoginId(), request.getPassword());
        return ResponseEntity.ok().build();
    }

}
