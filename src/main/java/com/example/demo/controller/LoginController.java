package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/members/login")
    public String loginForm(@ModelAttribute("LoginForm") LoginForm form){
        return "members/createLoginForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/createLoginForm";
        }

        Member loginMember = loginService.login(form.getEmail(), form.getPassword());

        log.info("login? {}", loginMember);
            if(loginMember == null){
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "members/createLoginForm";
            }
            return "members/createLoginForm";
    }
}
