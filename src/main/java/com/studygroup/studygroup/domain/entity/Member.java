package com.studygroup.studygroup.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long memberId;

    private String password;

    private String name;

    private String email;

    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contents = new ArrayList<>();

    public Member(String password, String name, String email, String phone) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void update(String password, String name, String phone) {
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
