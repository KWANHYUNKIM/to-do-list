package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.manager.SessionManager;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @Secured("ROLE_USER")
    @GetMapping("/members/home")
    public String loginHome(){
        return "login/home";
    }
}
