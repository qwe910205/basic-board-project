package com.studygroup.studygroup.web.controller.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contents")
@Slf4j
public class ContentController {

    @GetMapping
    public String contents() {
        return "/content/contents";
    }
}
