package com.example.todoapi.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public boolean registerMember(Member member) {
        if (existsByLoginId(member.getLoginId())){
            return false;
        }
        else {
            em.persist(member);
            return true;
        }
    }

    private boolean existsByLoginId(String loginId) {
        Long count = em.createQuery("select count(m) from Member as m where m.loginId = :member_loginId", Long.class)
                .setParameter("member_loginId", loginId)
                .getSingleResult();
        return count > 0;

    }

    public Object login(String loginId, String password) {
        try {
            Member result = em.createQuery("select m from Member m where m.loginId = :member_loginId and m.password = :member_password", Member.class)
                    .setParameter("member_loginId", loginId)
                    .setParameter("member_password", password)
                    .getSingleResult();
            return result; // 일치하는 사용자가 있으면 Member 객체 반환
        } catch (NoResultException e) {
            return "일치하는 사용자가 없습니다."; // 일치하는 사용자가 없을 경우 메시지 반환
        }
    }
}
