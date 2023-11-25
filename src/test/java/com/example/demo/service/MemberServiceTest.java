package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepositor;
    @Autowired EntityManager em;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception{
        //given
        String password = "ehsqjfwk123!";
        String encodePassword = passwordEncoder.encode(password);

        Member member = new Member();
        member.setUsername("kim");
        member.setBirthday("950326");
        member.setEmail("molba06@naver.com");

        member.setPassword(encodePassword);
        member.setPhonenumber("010-1234-1234");
        //when
        Long saveId = memberService.join(member);
        //then
        em.flush();
        assertEquals(member,memberRepositor.findOne(saveId));

    }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);
        //then
        fail("예외가 발생해야 한다.");
    }
}