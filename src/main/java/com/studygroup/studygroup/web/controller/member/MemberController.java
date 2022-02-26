package com.studygroup.studygroup.web.controller.member;

import com.studygroup.studygroup.domain.dto.MemberUpdateDto;
import com.studygroup.studygroup.domain.entity.Member;
import com.studygroup.studygroup.domain.exception.NotUniqueMemberException;
import com.studygroup.studygroup.domain.repository.MemberRepository;
import com.studygroup.studygroup.domain.service.MemberService;
import com.studygroup.studygroup.web.argumentresolver.Login;
import com.studygroup.studygroup.web.form.EditPasswordForm;
import com.studygroup.studygroup.web.form.MemberEditForm;
import com.studygroup.studygroup.web.session.SessionConst;
import com.studygroup.studygroup.web.form.JoinForm;
import com.studygroup.studygroup.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "member/login-form";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult result,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (result.hasErrors()) {
            return "member/login-form";
        }
        List<Member> members = memberRepository.findByEmail(loginForm.getEmail());
        if (members.size() == 0) {
            result.reject("NotRegistered");
            return "member/login-form";
        }
        Member member = members.get(0);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getMemberId());
        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "member/join-form";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinForm joinForm, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "member/join-form";
        }

        Member member = new Member(joinForm.getPassword(), joinForm.getName(), joinForm.getEmail(), joinForm.getPhone());

        try {
            memberService.join(member);
        } catch (NotUniqueMemberException e) {
            result.rejectValue("email", "NotUnique");
            return "member/join-form";
        }

        redirectAttributes.addAttribute("status", true);
        return "redirect:/";
    }

    @GetMapping("/my-info")
    public String myInfo(@Login Member loginMember, Model model) {
        MemberEditForm memberEditForm = new MemberEditForm(loginMember.getName(), loginMember.getPhone());
        model.addAttribute("email", loginMember.getEmail());
        model.addAttribute("memberEditForm", memberEditForm);
        model.addAttribute("createdDate", loginMember.getCreatedDate());
        return "member/my-info";
    }

    @PostMapping("/my-info")
    public String editMyInfo(@Validated @ModelAttribute MemberEditForm form, BindingResult result, @Login Member loginMember) {

        if (result.hasErrors()) {
            return "member/my-info";
        }

        MemberUpdateDto memberUpdateDto = new MemberUpdateDto(loginMember.getMemberId(), loginMember.getPassword(), form.getName(), form.getPhone());
        memberService.update(memberUpdateDto);
        return "redirect:/members/my-info";
    }

    @GetMapping("/edit-password")
    public String editPasswordForm(Model model) {
        model.addAttribute("editPasswordForm", new EditPasswordForm());
        return "member/edit-password";
    }

    @PostMapping("/edit-password")
    public String editPassword(@Validated @ModelAttribute EditPasswordForm form, BindingResult result, @Login Member loginMember,RedirectAttributes redirectAttributes) {

        // 현재 비밀번호를 맞춰봄
        if (!loginMember.getPassword().equals(form.getCurrentPassword())) {
            result.rejectValue("currentPassword", "NotMatch");
            return "member/edit-password";
        }

        // 바꿀 비밀번호의 형식을 검사함
        if (result.hasErrors()) {
            return "member/edit-password";
        }

        // 비밀번호 확인을 다르게 썼는지 검사
        if (!form.getNextPassword().equals(form.getCheckPassword())) {
            result.rejectValue("checkPassword", "NotEqual");
            return "member/edit-password";
        }

        memberService.update(new MemberUpdateDto(loginMember.getMemberId(), form.getNextPassword(), loginMember.getName(), loginMember.getPhone()));

        redirectAttributes.addAttribute("status", true);
        return "redirect:/members/edit-password";
    }

    @GetMapping("/secession")
    public String getSecessionPage() {
        return "member/secession";
    }

    @PostMapping("/secession")
    public String secession(@RequestParam String password, @Login Member loginMember, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (password.equals(loginMember.getPassword())) {
            memberService.secession(loginMember);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            redirectAttributes.addAttribute("secession", true);
            return "redirect:/";
        }
        redirectAttributes.addAttribute("passwordNotMatch", true);
        return "redirect:/members/secession";
    }

}
