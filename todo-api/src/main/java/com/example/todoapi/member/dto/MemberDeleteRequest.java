package com.example.todoapi.member.dto;

import lombok.Getter;

@Getter
public class MemberDeleteRequest {
    private String loginId;
    private String password;
}
