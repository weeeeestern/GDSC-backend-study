package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private long id;

    @Column(name="todo_content", columnDefinition = "varchar(200)")
    private String content;

    @Column(name="todo_is_checked")
    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;
}
