package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.ContentUpdateDto;
import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.ContentRepository;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    public void post(Content content) {
        contentRepository.save(content);
    }

    public void update(ContentUpdateDto dto) {
        Content content = contentRepository.findById(dto.getId());
        content.update(dto.getTitle(), dto.getMainText());
    }

    public void delete(Long contentId) {
        Content content = contentRepository.findById(contentId);
        contentRepository.remove(content);
    }

    public void addView(Long contentId) {
        Content content = contentRepository.findById(contentId);
        content.addView();
    }
}
