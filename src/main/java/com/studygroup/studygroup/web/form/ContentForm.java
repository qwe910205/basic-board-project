package com.studygroup.studygroup.web.form;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ContentForm {

    @NotBlank
    private String title;
    private String mainText;

    public ContentForm(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }
}
