package com.example.todoapi.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findByUserId(long userId) {
        return em.find(Member.class, userId);
    }

    public Member findByLoginId(String loginId) {
         return em.createQuery("select m from Member m where m.loginId = :member_loginId", Member.class)
                    .setParameter("member_loginId", loginId)
                    .getSingleResult();
    }

    public void delete(Member member) {
        em.remove(member);
    }
}
