package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.MemberUpdateDto;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public Member searchMemberById(Long id) {
        Member member = memberRepository.findByMemberId(id);
        return member;
    }

    @Transactional(readOnly = false)
    public void update(MemberUpdateDto dto) {
        Member findMember = memberRepository.findByMemberId(dto.getId());
        findMember.update(dto.getPassword(), dto.getName(), dto.getPhone());
    }

    @Transactional(readOnly = false)
    public void secession(Member member) {
        memberRepository.remove(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (findMembers.size() > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
