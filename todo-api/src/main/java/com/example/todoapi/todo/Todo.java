package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private long id;

    @Column(name="todo_content", columnDefinition = "varchar(200)")
    private String content;

    @Column(name="todo_is_checked")
    private boolean checked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    public Todo(String content, Member member){
        this.content = content;
        this.member = member;
    }

    public void updateContent(String newContent){
        this.content = newContent;
    }

    public boolean getChecked() { return checked; }
}
