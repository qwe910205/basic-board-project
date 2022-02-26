package com.studygroup.studygroup.domain.repository;

import com.studygroup.studygroup.domain.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public Long save(Member member) {
        em.persist(member);
        return member.getMemberId();
    }

    public Member findByMemberId(Long id) {
        return em.find(Member.class, id);
    }

    @Transactional(readOnly = false)
    public void remove(Member member) {
        em.flush();
        em.createQuery("delete from Member m where m.memberId = :id")
                .setParameter("id", member.getMemberId())
                .executeUpdate();
    }

    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }

    public List<Member> findByEmail(String email) {
        List<Member> members = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return members;
    }
}
