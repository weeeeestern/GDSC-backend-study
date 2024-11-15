package com.example.todoapi.todo;


import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createTodo(String content, Long userId) throws Exception {
        Member member = memberRepository.findByUserId(userId);

        if (member == null) {
            throw new Exception("존재하지 않는 유저 ID 입니다.");
        }
        Todo todo = new Todo(content, member);
        todoRepository.save(todo);
        return todo.getId();
    }

    @Transactional(readOnly = true)
    public List<Todo> readTools(Long userId) throws Exception {
        Member member = memberRepository.findByUserId(userId);

        if (member == null) {
            throw new Exception("존재하지 않는 유저 ID 입니다.");
        }
        return todoRepository.findAllByMember(member);
    }

    @Transactional
    public void updateContent(Long todoId, String loginId, String newContent) throws Exception {

        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new Exception("존재하지 않는 Todo 입니다.");
        }

        Member member = memberRepository.findByLoginId(loginId); // 로그인한 사용자가 있는 지 확인
        if (member == null || !todo.getMember().getLoginId().equals(loginId)) {
            throw new Exception("접근할 수 없는 Todo 입니다.");
        } else {
            todo.updateContent(newContent);
//            todoRepository.save(todo);
        }
    }

    @Transactional
    public void deleteTodo(Long todoId, String loginId) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new Exception("존재하지 않는 Todo 입니다.");
        }

        Member member = memberRepository.findByLoginId(loginId);
        if (member == null || todo.getMember().getLoginId().equals(loginId)) {
            todoRepository.deleteById(todoId);
        } else {
            throw new Exception("접근할 수 없는 Todo 입니다.");
        }
    }

    @Transactional
    public void checkTodo(Long todoId, String loginId, Boolean checked) throws Exception {
        Todo todo = todoRepository.findById(todoId);
        if (todo == null) {
            throw new Exception("존재하지 않는 Todo 입니다.");
        }

        Member member = memberRepository.findByLoginId(loginId);
        if (member == null || !todo.getMember().getLoginId().equals(loginId)) {
            throw new Exception("접근할 수 없는 Todo 입니다.");
        }

        todo.setChecked(checked);
        todoRepository.save(todo);
    }
}

