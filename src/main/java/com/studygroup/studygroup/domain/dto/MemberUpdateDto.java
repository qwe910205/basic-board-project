package com.studygroup.studygroup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberUpdateDto {

    private Long id;
    private String password;
    private String name;
    private String phone;
}
