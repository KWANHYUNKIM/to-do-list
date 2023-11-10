package com.example.demo.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter

public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String username;
    @NotEmpty(message = "이메일 이름은 필수 입니다.")
    private String email;
    @NotEmpty(message = "생년월일은 필수 입니다.")
    private String birthday;
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String password;
    @NotEmpty(message = "핸드폰번호는 필수 입니다.")
    private String phonenumber;


}
