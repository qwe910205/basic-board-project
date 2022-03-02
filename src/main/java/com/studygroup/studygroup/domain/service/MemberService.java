package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.MemberUpdateDto;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.exception.NotUniqueMemberException;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public void update(MemberUpdateDto dto) {
        Member findMember = memberRepository.findByMemberId(dto.getId());
        findMember.update(dto.getPassword(), dto.getName(), dto.getPhone());
    }

    public void secession(Member member) {
        memberRepository.remove(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (findMembers.size() > 0) {
            throw new NotUniqueMemberException("이미 가입된 이메일입니다.");
        }
    }
}
