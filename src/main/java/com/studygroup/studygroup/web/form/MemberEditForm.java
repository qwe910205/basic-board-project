package com.studygroup.studygroup.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditForm {

    @Pattern(regexp = "^[\\S]{2,20}$")
    private String name;

    @Pattern(regexp = "^[\\d]{6,20}$")
    private String phone;

}
