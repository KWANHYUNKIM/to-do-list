package com.example.demo.controller;

import com.example.demo.controller.form.MemberForm;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid MemberForm form, BindingResult result) {


        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = new Member();
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        member.setUsername(form.getUsername());
        member.setEmail(form.getEmail());
        member.setBirthday(form.getBirthday());
        member.setPassword(encodedPassword);
        member.setPhonenumber(form.getPhonenumber());
        member.setPosition("ROLE_USER");
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/admin/member/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
