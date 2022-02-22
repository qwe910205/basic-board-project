package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.MemberUpdateDto;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() {
        // given
        Member member = createMember();

        // when
        memberService.join(member);

        // then
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    public void searchByMemberId() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberService.searchMemberById(member.getMemberId());

        // then
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    public void updateName() {
        // given
        Member member = createMember();
        memberRepository.save(member);
        MemberUpdateDto dto = new MemberUpdateDto(member.getMemberId(), member.getPassword(), "김깐덜", member.getPhone());

        // when
        memberService.update(dto);

        // then
        assertThat(memberRepository.findByMemberId(member.getMemberId()).getName()).isEqualTo("김깐덜");
    }

    @Test
    public void secession() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        memberService.secession(member);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(0);
    }

    @Test
    public void duplicateMember() {
        // given
        Member member = createMember();
        memberService.join(member);
        Member member1 = createMember();

        // when

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member1));
    }

    private Member createMember() {
        return new Member("qwe123456", "김대현", "qwe910205@naver.com", "01056588994");
    }

}