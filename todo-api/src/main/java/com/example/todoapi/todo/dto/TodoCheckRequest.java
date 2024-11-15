package com.example.todoapi.todo.dto;

import lombok.Getter;

@Getter
public class TodoCheckRequest {
    private String loginId;
    private boolean checked;
}
