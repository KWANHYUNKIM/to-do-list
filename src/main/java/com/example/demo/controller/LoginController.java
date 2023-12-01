package com.example.demo.controller;

import com.example.demo.controller.form.LoginForm;
import com.example.demo.domain.Member;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("LoginForm") LoginForm form){
        return "login/createLoginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request, Model model){
        Member loginMember = loginService.login(form.getEmail(), form.getPassword());
        System.out.println("loginMember" + loginMember);
        if(bindingResult.hasErrors()){
            model.addAttribute("error","입력값을 확인해주세요");
            return "login/createLoginForm";
        }

        if(loginMember != null ){
            // SUCCESS: LOGIN
            // 세션이 있으면 세션을 반환, 없다면 신규 세션 생성
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            // 쿠키에 시간 정보를 주지 않으면 세션 쿠키가 된다. (브라우저 종료시 모두 종료)
            // Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
            // response.addCookie(idCookie);

            return "login/home";
        } else {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            model.addAttribute("error","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/createLoginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
