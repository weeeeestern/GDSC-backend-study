package com.example.todoapi.todo.dto;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private String loginId;
    private String newContent;
}
