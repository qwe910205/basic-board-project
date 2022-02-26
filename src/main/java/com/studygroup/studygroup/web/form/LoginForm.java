package com.studygroup.studygroup.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
