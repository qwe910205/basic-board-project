package com.studygroup.studygroup.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinForm {

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    private String password;

    @Pattern(regexp = "^[\\S]{2,20}$")
    private String name;

    @Pattern(regexp = "^[\\d]{6,20}$")
    private String phone;
}
