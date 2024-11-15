package com.example.todoapi.member.dto;

import lombok.Getter;

@Getter
public class MemberLoginRequest {
    private String loginId;
    private String password;
}
