package com.studygroup.studygroup.domain.repository;

import com.studygroup.studygroup.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save() {
        // given
        Member member = createMember();

        // when
        memberRepository.save(member);

        // then
        assertThat(memberRepository.findByMemberId((member.getMemberId())).getName()).isEqualTo("김대현");
    }

    @Test
    public void findByMemberId() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByMemberId(member.getMemberId());

        // then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void remove() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        memberRepository.remove(member);

        // then
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        assertThat(findMember).isNull();
    }

    @Test
    public void findAll() {
        // given
        Member member1 = createMember();
        Member member2 = createMember();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).containsExactly(member1, member2);
    }

    private Member createMember() {
        return new Member("qwe123456", "김대현", "qwe910205@naver.com", "01056588994");
    }

}