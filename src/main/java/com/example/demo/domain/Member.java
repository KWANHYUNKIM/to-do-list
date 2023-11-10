package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String email; // 이메일

    private String username; // 이름

    private String birthday; // 생일

    private String password; // 패스워드

    private String phonenumber; // 핸드폰번호
}
