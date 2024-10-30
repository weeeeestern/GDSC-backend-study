package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import com.example.todoapi.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public class TodoRepositoryTest {

        @Autowired
        private TodoRepository todoRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Test
        @Transactional
        @Rollback(false)
        void todoSaveTest() {
            Todo todo = new Todo("Todo Content!", null);
            todoRepository.save(todo);

            Assertions.assertThat(todo.getId()).isNotNull();
        }

        @Test
        @Transactional
        void todoFindOneIdTest(){
            //given
            Todo todo = new Todo("Todo Content!", null);
            todoRepository.save(todo);

            todoRepository.flushAndClear(); // 1차 캐시 막기, DB에 select 쿼리 직접 보내기

            //when
            Todo findTodo = todoRepository.findById(todo.getId());

            //then
            Assertions.assertThat(findTodo.getId()).isEqualTo(todo.getId());
        }

        @Test
        @Transactional
        void todoFindAllTest(){
            Todo todo1 = new Todo("Todo Content! 1", null);
            Todo todo2 = new Todo("Todo Content! 2", null);
            Todo todo3 = new Todo("Todo Content! 3", null);
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);

            List<Todo> todoList = todoRepository.findAll();

            Assertions.assertThat(todoList).hasSize(3);
        }

        @Test
        @Transactional
        void todoFindAllByMemberTest(){
            Member member1 = new Member("user1","password1");
            Member member2 = new Member("user2","password2");
            memberRepository.registerMember(member1);
            memberRepository.registerMember(member2);

            Todo todo1 = new Todo("Todo Content! 1", member1);
            Todo todo2 = new Todo("Todo Content! 2", member1);
            Todo todo3 = new Todo("Todo Content! 3", member2);
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);

            List<Todo> member1TodoList = todoRepository.findAllByMember(member1);
            List<Todo> member2TodoList =todoRepository.findAllByMember(member2);

            Assertions.assertThat(member1TodoList).hasSize(2);
            Assertions.assertThat(member2TodoList).hasSize(1);
        }

        @Test
        @Transactional
        @Rollback(false)
        void todoUpdateTest(){
            Todo todo1 = new Todo("todo content1",null);
            todoRepository.save(todo1); // 조회하는 코드

            todoRepository.flushAndClear();

            Todo findTodo1 =todoRepository.findById(todo1.getId());
            findTodo1.updateContent("new content 입니다..");
            //수정하는 코드
        }

        @Test
        @Transactional
        @Rollback(false)
        void todoDeleteTest(){
            Todo todo1 = new Todo("todo content1",null);
            Todo todo2= new Todo("todo content2",null);
            todoRepository.save(todo1);
            todoRepository.save(todo2);// 조회하는 코드

            todoRepository.flushAndClear();

            todoRepository.deleteById(todo1.getId());
            //수정하는 코드
        }

        // in-memory-database
        @AfterAll
        public static void doNotFinish() {
            System.out.println("Test Finished");
            while (true){}
        }

    }


