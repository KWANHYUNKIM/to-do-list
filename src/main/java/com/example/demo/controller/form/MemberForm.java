package com.example.demo.controller.form;


import com.example.demo.controller.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter

public class MemberForm {

    @NotEmpty(message = "ex) 홍길동.")
    private String username;

    @Email @NotEmpty(message = "4~15자 이내로 입력해주세요.")
    private String email;
    @NotEmpty(message = "ex) 950326.")
    private String birthday;

    @Password
    @NotEmpty
    private String password;
    @NotEmpty(message = "ex) 010-1234-5678.")
    private String phonenumber;


}
