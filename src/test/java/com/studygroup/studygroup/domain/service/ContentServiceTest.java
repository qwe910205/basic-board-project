package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.ContentUpdateDto;
import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.ContentRepository;
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
class ContentServiceTest {

    @Autowired ContentService contentService;
    @Autowired MemberService memberService;
    @Autowired
    ContentRepository contentRepository;
    @Autowired
    EntityManager em;

    @Test
    public void post() {
        // given
        Member member = createMember();
        memberService.join(member);
        Content content = createContent(member);

        // when
        contentService.post(content);

        // then
        assertThat(contentRepository.findById(content.getContentId())).isEqualTo(content);
    }

    @Test
    public void update() {
        Member member = createMember();
        memberService.join(member);
        Content content = createContent(member);
        contentService.post(content);
        em.flush();
        em.clear();

        ContentUpdateDto dto = new ContentUpdateDto(content.getContentId(), "리액트 개꿀잼", content.getMainText());
        contentService.update(dto);


        assertThat(contentRepository.findById(content.getContentId()).getTitle()).isEqualTo("리액트 개꿀잼");
    }

    @Test
    public void addViews() {
        Member member = createMember();
        memberService.join(member);
        Content content = createContent(member);
        contentService.post(content);

        contentService.addView(content.getContentId());

        em.flush();
        em.clear();

        Content findContent = contentRepository.findById(content.getContentId());

        assertThat(findContent.getViews()).isEqualTo(1);
    }

    private Member createMember() {
        return new Member("qwe123456", "김대현", "qwe910205@naver.com", "01056588994");
    }

    private Content createContent(Member member) {
        return new Content("스프링 개존잼", "님들 진짜임 한번 해보셈. 객체지향적으로 짜는게 재밌음ㅋㅋ", member);
    }

}