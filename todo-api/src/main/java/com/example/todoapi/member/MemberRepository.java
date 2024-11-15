package com.example.todoapi.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        try {
            return em.find(Member.class, userId);
        }
        catch (NoResultException e) { return null; }
    }

    public Member findByLoginId(String loginId) {
        try {
            return em.createQuery("select m from Member m where m.loginId = :member_loginId", Member.class)
                    .setParameter("member_loginId", loginId)
                    .getSingleResult();
        }
        catch (NoResultException e) { return null;}
    }

    public void delete(Member member) {
        em.remove(member);
    }
}
