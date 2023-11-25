package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/login")
    public String loginForm(@ModelAttribute("LoginForm") LoginForm form){
        return "login/createLoginForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, Model model){
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        System.out.println("encodedPassword " + " " + encodedPassword);
        Member loginMember = loginService.login(form.getEmail(), encodedPassword);
        String databaseEncodedPassword = loginMember.getPassword();
        System.out.println("databaseEncodedPassword" + " " + databaseEncodedPassword);

        if (passwordEncoder.matches(form.getPassword(), databaseEncodedPassword)) {
            // 로그인 성공
            // 세션이 들어갈수있음.

            return "redirect:/";
        } else {
            // 로그인 실패
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");

            // LoginForm 모델 속성 추가
            model.addAttribute("loginForm", new LoginForm());

            // 각 필드에 대한 오류 메시지 추가
            if (bindingResult.hasFieldErrors("email")) {
                model.addAttribute("emailError", bindingResult.getFieldError("email").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("password")) {
                model.addAttribute("passwordError", bindingResult.getFieldError("password").getDefaultMessage());
            }
            return "login/createLoginForm";
        }
    }
    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
