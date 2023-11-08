package com.example.demo.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter

public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다. ")
    private String username;
    private String email;
    private String birthday;
    private String password;
    private String phonenumber;


}
