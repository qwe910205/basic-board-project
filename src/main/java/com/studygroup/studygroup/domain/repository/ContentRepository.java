package com.studygroup.studygroup.domain.repository;

import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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

    public List<Content> findByTitle(String text, int offset, int limit) {
        return em.createQuery("select c from Content c where c.title like :text order by c.contentId desc", Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    public List<Content> findByMainText(String text, int offset, int limit) {
        return em.createQuery("select c from Content c where c.mainText like :text order by c.contentId desc", Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    public List<Content> findByMemberId(Member member, int offset, int limit) {
        return em.createQuery("select c from Content c where c.member = :member order by c.contentId desc", Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("member", member)
                .getResultList();
    }

    public List<Content> findByMemberName(String name, int offset, int limit) {
        return em.createQuery("select c from Content c join c.member m where m.name = :name order by c.contentId desc", Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("name", name)
                .getResultList();
    }

    public void remove(Content content) {
        em.remove(content);
    }

    public List<Content> findAll(int offset, int limit) {
        List<Content> contents = em.createQuery("select c from Content c order by c.contentId desc", Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return contents;
    }

    public Long count() {
        return em.createQuery("select count(c) from Content c", Long.class)
                .getSingleResult();
    }

    public Long countByTitle(String text) {
        return em.createQuery("select count(c) from Content c where c.title like :text", Long.class)
                .setParameter("text", "%" + text + "%")
                .getSingleResult();
    }

    public Long countByMainText(String text) {
        return em.createQuery("select count(c) from Content c where c.mainText like :text", Long.class)
                .setParameter("text", "%" + text + "%")
                .getSingleResult();
    }

    public Long countByMemberId(Member member) {
        return em.createQuery("select count(c) from Content c where c.member = :member", Long.class)
                .setParameter("member", member)
                .getSingleResult();
    }

    public Long countByMemberName(String name) {
        return em.createQuery("select count(c) from Content c join c.member m where m.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
