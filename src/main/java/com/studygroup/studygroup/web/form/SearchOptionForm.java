package com.studygroup.studygroup.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchOptionForm {

    private String searchOption = "title";
    private String text;

}
