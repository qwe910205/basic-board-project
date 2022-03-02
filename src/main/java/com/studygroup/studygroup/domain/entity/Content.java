package com.studygroup.studygroup.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id @GeneratedValue
    private Long contentId;

    private String title;

    @Lob
    private String mainText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long views = 0L;

    public Content(String title, String mainText, Member member) {
        this.title = title;
        this.mainText = mainText;
        this.member = member;
    }

    public void update(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }

    public void addView() {
        this.views++;
    }
}
