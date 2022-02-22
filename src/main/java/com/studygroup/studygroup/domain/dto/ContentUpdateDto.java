package com.studygroup.studygroup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ContentUpdateDto {

    private Long id;
    private String title;
    private String mainText;
}
