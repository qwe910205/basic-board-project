package com.studygroup.studygroup.domain.repository;

import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ContentRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Content content) {
        em.persist(content);
        return content.getContentId();
    }

    public Content findById(Long contentId) {
        Content content = em.find(Content.class, contentId);
        return content;
    }

    public List<Content> findByTitle(String text) {
        return em.createQuery("select c from Content c where c.title like :text", Content.class)
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    public List<Content> findByMainText(String text) {
        return em.createQuery("select c from Content c where c.mainText like :text", Content.class)
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    public List<Content> findByMemberId(Member member) {
        return em.createQuery("select c from Content c where c.member = :member", Content.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Transactional(readOnly = false)
    public void remove(Content content) {
        em.remove(content);
    }

    public List<Content> findAll() {
        List<Content> contents = em.createQuery("select c from Content c", Content.class)
                .getResultList();
        return contents;
    }
}
