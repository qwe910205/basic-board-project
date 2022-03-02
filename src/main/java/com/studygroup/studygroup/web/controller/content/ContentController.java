package com.studygroup.studygroup.web.controller.content;

import com.studygroup.studygroup.domain.dto.ContentUpdateDto;
import com.studygroup.studygroup.domain.entity.Content;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.repository.ContentRepository;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import com.studygroup.studygroup.domain.service.ContentService;
import com.studygroup.studygroup.web.argumentresolver.Login;
import com.studygroup.studygroup.web.form.ContentForm;
import com.studygroup.studygroup.web.form.Pagination;
import com.studygroup.studygroup.web.form.SearchOptionForm;
import com.studygroup.studygroup.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class ContentController {

    private final ContentService contentService;
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public String getContents(Model model, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,
                              @ModelAttribute SearchOptionForm form) {

        int offset = (page - 1) * pageSize;

        if (form == null) {
            form = new SearchOptionForm();
        }

        List<Content> contents = null;
        Long totalCount = 0L;

        if (StringUtils.hasText(form.getText())) {
            switch(form.getSearchOption()) {
                case "title":
                    contents = contentRepository.findByTitle(form.getText(), offset, pageSize);
                    totalCount = contentRepository.countByTitle(form.getText());
                    break;
                case "mainText":
                    contents = contentRepository.findByMainText(form.getText(), offset, pageSize);
                    totalCount = contentRepository.countByMainText(form.getText());
                    break;
                case "author":
                    contents = contentRepository.findByMemberName(form.getText(), offset, pageSize);
                    totalCount = contentRepository.countByMemberName(form.getText());
                    break;
                default:
                    contents = contentRepository.findAll(offset, pageSize);
                    totalCount = contentRepository.count();
            }
        } else {
            contents = contentRepository.findAll(offset, pageSize);
            totalCount = contentRepository.count();
        }

        Pagination pagination = new Pagination(totalCount.intValue(), page, pageSize);

        model.addAttribute("contents", contents);
        model.addAttribute("pagination", pagination);

        return "/board/list";
    }

    @GetMapping("/content-form")
    public String getContentForm(Model model) {
        model.addAttribute("contentForm", new ContentForm());
        return "board/content-form";
    }

    @PostMapping("/content-form")
    public String post(@Validated @ModelAttribute ContentForm form, BindingResult result, @Login Member loginMember) {

        if (result.hasErrors()) {
            return "board/content-form";
        }

        Content content = new Content(form.getTitle(), form.getMainText(), loginMember);
        contentService.post(content);
        return "redirect:/board/" + content.getContentId();
    }

    @GetMapping("/{contentId}")
    public String getContent(@PathVariable Long contentId, Model model, @Login Member loginMember) {
        Content content = contentRepository.findById(contentId);
        
        // 조회수 증가
        if (loginMember.getMemberId() != content.getMember().getMemberId()) {
            contentService.addView(content.getContentId());
        }
        model.addAttribute("content", content);
        return "board/content";
    }

    @GetMapping("/{contentId}/update")
    public String updateContentForm(@PathVariable Long contentId, Model model) {
        Content content = contentRepository.findById(contentId);
        ContentForm contentForm = new ContentForm(content.getTitle(), content.getMainText());
        model.addAttribute("contentForm", contentForm);
        return "board/content-update-form";
    }

    @PostMapping("/{contentId}/update")
    public String updateContent(@Validated @ModelAttribute ContentForm form, BindingResult result, @PathVariable Long contentId,
                                @SessionAttribute(name = "loginMember") Long memberId) {

        if (!validAccess(contentId, memberId)) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            return "board/content-update-form";
        }
        contentService.update(new ContentUpdateDto(contentId, form.getTitle(), form.getMainText()));
        return "redirect:/board/" + contentId;
    }

    @GetMapping("/{contentId}/delete")
    public String deleteContent(@PathVariable Long contentId, @SessionAttribute(name = "loginMember") Long memberId) {

        if (!validAccess(contentId, memberId)) {
            return "redirect:/";
        }

        contentService.delete(contentId);

        return "redirect:/board/list";
    }

    // 로그인한 사용자가 자신의 게시글에 접근하는지 확인하는 메서드
    private boolean validAccess(final long contentId, final long memberId) {
        Content content = contentRepository.findById(contentId);
        return content.getMember().getMemberId() == memberId;
    }
}
