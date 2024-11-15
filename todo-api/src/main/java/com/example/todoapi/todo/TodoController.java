package com.example.todoapi.todo;

import com.example.todoapi.todo.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestParam("userId") Long userId) throws Exception{
        List<Todo> todos = todoService.readTools(userId);
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<Void> createTodo(@RequestBody TodoCreateRequest request) throws Exception{
        Long todo_id = todoService.createTodo(request.getContent(),request.getUserId());
        return ResponseEntity.created(URI.create("/todos/"+ todo_id)).build();
        // create 안에 데이터를 생성할 path 적기
    }

    @PatchMapping("/{todo_id}/update")
    public ResponseEntity<Void> updateTodo(@RequestBody TodoUpdateRequest request,
                                           @PathVariable("todo_id") Long todo_id) throws Exception{
        todoService.updateContent(todo_id,request.getLoginId(), request.getNewContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{todo_id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todo_id") Long todo_id,
                                           @RequestParam("loginId") String loginId) throws Exception {
        todoService.deleteTodo(todo_id, loginId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{todo_id}/check")
    public ResponseEntity<Void> checkTodo(@RequestBody TodoCheckRequest request,
                                          @PathVariable("todo_id") Long todo_id) throws Exception{
        todoService.checkTodo(todo_id, request.getLoginId(), request.isChecked());
        return ResponseEntity.ok().build();
    }

}

