package com.studygroup.studygroup.domain.service;

import com.studygroup.studygroup.domain.dto.ContentUpdateDto;
import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional(readOnly = false)
    public void post(Content content) {
        contentRepository.save(content);
    }

    public List<Content> searchByTitle(String text) {
        return contentRepository.findByTitle(text);
    }

    public List<Content> searchByMainText(String text) {
        return contentRepository.findByMainText(text);
    }

    public List<Content> searchByMember(Member member) {
        return contentRepository.findByMemberId(member);
    }

    @Transactional(readOnly = false)
    public void update(ContentUpdateDto dto) {
        Content content = contentRepository.findById(dto.getId());
        content.update(dto.getTitle(), dto.getMainText());
    }

    @Transactional(readOnly = false)
    public void delete(Content content) {
        contentRepository.remove(content);
    }
}
