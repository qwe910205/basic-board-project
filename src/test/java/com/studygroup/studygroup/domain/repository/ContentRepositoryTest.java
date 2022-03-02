package com.studygroup.studygroup.domain.repository;

import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContentRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired ContentRepository contentRepository;

    @Test
    public void save() {
        // given
        Content content = createContent();

        // when
        contentRepository.save(content);

        // then
        assertThat(contentRepository.findById(content.getContentId())).isEqualTo(content);
    }

    @Test
    public void findById() {
        // given
        Content content = createContent();
        contentRepository.save(content);

        // when
        Content findContent = contentRepository.findById(content.getContentId());

        // then
        assertThat(content).isEqualTo(findContent);
    }

    @Test
    public void remove() {
        // given
        Content content = createContent();
        contentRepository.save(content);

        // when
        contentRepository.remove(content);

        // then
        assertThat(contentRepository.findById(content.getContentId())).isNull();
    }

    @Test
    public void getMember() {
        // given
        Content content = createContent();
        contentRepository.save(content);
        em.flush();
        em.clear();

        // when
        Content findContent = contentRepository.findById(content.getContentId());
        String name = findContent.getMember().getName();

        // then
        assertThat(name).isEqualTo("김대현");
    }

    @Test
    public void count() {
        // given
        Member member = new Member("+qwe123456", "김대현", "qwe910205@naver.com", "01056588994");
        memberRepository.save(member);
        for (int i = 0; i < 100; i++) {
            Content content = new Content("스프링", "재밌음", member);
            contentRepository.save(content);
        }

        // when
        Long count = contentRepository.count();

        // then
        assertThat(count).isEqualTo(100L);
    }

    private Content createContent() {
        Member member = new Member("qwe123456", "김대현", "qwe910205@naver.com", "01056588994");
        memberRepository.save(member);
        return new Content("스프링 재밌다", "진짜임. 님들도 해보세요. 객체지향 프로그래밍 재밌음. ㅇㅇ", member);
    }

}