package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void testCreateTodo() throws Exception{
        given(memberRepository.findByUserId(anyLong())).willReturn(new Member());

        todoService.createTodo("content",1L);

        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testCreateTodo_fail_WhenUserNotExist() throws Exception{
        given(memberRepository.findByUserId(anyLong())).willReturn(null);

        Assertions.assertThatThrownBy(()-> todoService.createTodo("content",1L))
                .isInstanceOf(Exception.class)
                .hasMessage("존재하지 않는 유저 ID 입니다.");
    }

    @Test
    void testReadTools() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!

        Todo todo1 = new Todo("Test Todo1",member);
        Todo todo2 = new Todo("Test Todo2",member);
        Todo todo3 = new Todo("Test Todo3",member);
        List<Todo> todos = Arrays.asList(todo1, todo2, todo3);

        given(memberRepository.findByUserId(member.getUserId())).willReturn(member);
        given(todoRepository.findAllByMember(member)).willReturn(todos);

        List<Todo> results = todoService.readTools(member.getUserId());

        Assertions.assertThat(results).hasSize(3);

        verify(memberRepository, times(1)).findByUserId(member.getUserId());
        verify(todoRepository, times(1)).findAllByMember(member);
    }

    @Test
    void testReadTools_fail_WhenUserNotExist() throws Exception {
        Long fakeUserId = 99L;

        given(memberRepository.findByUserId(anyLong())).willReturn(null);

        Assertions.assertThatThrownBy(() -> todoService.readTools(fakeUserId))
                .isInstanceOf(Exception.class)
                .hasMessage("존재하지 않는 유저 ID 입니다.");
    }

    @Test
    void testUpdateTodo() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!
        Todo todo1 = new Todo("Gonna be updated Test Todo1",member);
        todo1.setId(1L); // todoId를 수동으로 설정
        Todo todo2 = new Todo("Test Todo2",member);
        Todo todo3 = new Todo("Test Todo3",member);
        List<Todo> todos = Arrays.asList(todo1, todo2, todo3);

        given(todoRepository.findById(todo1.getId())).willReturn(todo1);

        todoService.updateContent(todo1.getId(), member.getLoginId(), "updated content");

        verify(todoRepository, times(1)).findById(todo1.getId());

        Assertions.assertThat(todo1.getContent()).isEqualTo("updated content");
    }

    @Test
    void testUpdateTodo_fail_WhenUserNotExist() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!
        Todo todo1 = new Todo("Gonna be updated Test Todo1",member);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(null);

        Assertions.assertThatThrownBy(()-> todoService.updateContent(todo1.getId(), member.getLoginId(), "updated content"))
                .isInstanceOf(Exception.class)
                .hasMessage("존재하지 않는 Todo 입니다.");
    }

    @Test
    void testUpdateTodo_fail_Unauthorized() throws Exception{ // 권한 있는 유저, 없는 유저 설정 후 없는 유저 예상결과 확인
        Member ownerMember = new Member("ownerUser", "password");
        ownerMember.setUserId(1L);
        Member unauthorizedMember = new Member("unauthorizedUser", "password");
        unauthorizedMember.setUserId(2L);

        Todo todo1 = new Todo("Gonna be updated Test Todo1",ownerMember);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(todo1);
        given(memberRepository.findByLoginId(unauthorizedMember.getLoginId())).willReturn(unauthorizedMember);

        Assertions.assertThatThrownBy(()->
                todoService.updateContent(todo1.getId(), unauthorizedMember.getLoginId(),"updated content"))
                .isInstanceOf(Exception.class)
                .hasMessage("접근할 수 없는 Todo 입니다.");
    }

    @Test
    void testDeleteTodo() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!
        Todo todo1 = new Todo("Gonna be deleted Test Todo1",member);
        todo1.setId(1L); // todoId를 수동으로 설정
        Todo todo2 = new Todo("Test Todo2",member);
        Todo todo3 = new Todo("Test Todo3",member);

        List<Todo> todos = Arrays.asList(todo1, todo2, todo3);

        given(todoRepository.findById(todo1.getId())).willReturn(todo1);

        todoService.deleteTodo(todo1.getId(), member.getLoginId());

        verify(todoRepository, times(1)).deleteById(todo1.getId());
    }

    @Test
    void testDeleteTodo_fail_WhenUserNotExist() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!
        Todo todo1 = new Todo("Gonna be deleted Test Todo1",member);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(null);

        Assertions.assertThatThrownBy(()-> todoService.deleteTodo(todo1.getId(), member.getLoginId()))
                .isInstanceOf(Exception.class)
                .hasMessage("존재하지 않는 Todo 입니다.");
    }

    @Test
    void testDeleteTodo_fail_Unauthorized() throws Exception{
        Member ownerMember = new Member("ownerUser", "password");
        ownerMember.setUserId(1L);
        Member unauthorizedMember = new Member("unauthorizedUser", "password");
        unauthorizedMember.setUserId(2L);

        Todo todo1 = new Todo("Gonna be deleted Test Todo1",ownerMember);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(todo1);
        given(memberRepository.findByLoginId(unauthorizedMember.getLoginId())).willReturn(unauthorizedMember);

        Assertions.assertThatThrownBy(()->
                todoService.deleteTodo(todo1.getId(), unauthorizedMember.getLoginId()))
                .isInstanceOf(Exception.class)
                .hasMessage("접근할 수 없는 Todo 입니다.");
    }

    @Test
    void testCheckTodo_setTrue() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L);
        Todo todo = new Todo("Test Todo",member);

        given(todoRepository.findById(todo.getId())).willReturn(todo);
        todoService.checkTodo(todo.getId(),member.getLoginId(),true);
        Assertions.assertThat(todo.getChecked()).isTrue();
    }
    @Test
    void testCheckTodo_setFalse() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L);
        Todo todo = new Todo("Test Todo",member);

        given(todoRepository.findById(todo.getId())).willReturn(todo);
        todoService.checkTodo(todo.getId(),member.getLoginId(),false);
        Assertions.assertThat(todo.getChecked()).isFalse();
    }

    @Test
    void testCheckTodo_fail_WhenUserNotExist() throws Exception{
        Member member = new Member("testUser", "password");
        member.setUserId(1L); // userId를 수동으로 설정!!
        Todo todo1 = new Todo("Gonna be checked Test Todo1",member);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(null);

        Assertions.assertThatThrownBy(()-> todoService.checkTodo(todo1.getId(),member.getLoginId() ,true))
                .isInstanceOf(Exception.class)
                .hasMessage("존재하지 않는 Todo 입니다.");
    }

    @Test
    void testCheckTodo_fail_Unauthorized() throws Exception{
        Member ownerMember = new Member("ownerUser", "password");
        ownerMember.setUserId(1L);
        Member unauthorizedMember = new Member("unauthorizedUser", "password");
        unauthorizedMember.setUserId(2L);

        Todo todo1 = new Todo("Gonna be checked Test Todo1",ownerMember);
        todo1.setId(1L); // todoId를 수동으로 설정

        given(todoRepository.findById(todo1.getId())).willReturn(todo1);
        given(memberRepository.findByLoginId(unauthorizedMember.getLoginId())).willReturn(unauthorizedMember);

        Assertions.assertThatThrownBy(()->
                        todoService.checkTodo(todo1.getId(),unauthorizedMember.getLoginId(), true))
                .isInstanceOf(Exception.class)
                .hasMessage("접근할 수 없는 Todo 입니다.");

    }
}
