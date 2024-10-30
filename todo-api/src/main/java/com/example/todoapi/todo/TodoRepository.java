package com.example.todoapi.todo;

import com.example.todoapi.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;

    //Create, 만들어서 DB에 Todo 객체 형태의 데이터 저장.
    public void save(Todo todo) {
        em.persist(todo);
    }

    //Read
    //단건 조회 : 한 개의 데이터 조회
    public Todo findById(Long id){
        return em.find(Todo.class, id);
    }

    //다건 조회 : 모든 데이터 조회
    public List<Todo> findAll() {
        return em.createQuery("select t from Todo as t",Todo.class).getResultList();
    }

    //조건 조회
    public List<Todo> findAllByMember(Member member){
        return em.createQuery("select t from Todo as t where t.member = :todo_member",Todo.class)
                .setParameter("todo_member",member)
                .getResultList();
    }

    //Update
    // 엔티티 클래스의 필드를 수정하는 메서드를 투두 클래스에 작성하여 수정하자.

    //Delete
    public void deleteById(Long todoId){
        Todo todo = findById(todoId);
        em.remove(todo);
    }

    //테스트 용도로만 사용
    public void flushAndClear() {
        em.flush(); // 지금까지의 모든 변경 사항들을 다 데이터베이스에 반영해라
        em.clear(); // 다 반영했으니 영속성 컨텍스트를 비워라
    }
}
