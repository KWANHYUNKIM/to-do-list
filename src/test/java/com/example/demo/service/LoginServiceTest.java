package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoginServiceTest {

   @Autowired LoginRepository loginRepository;
   @Autowired PasswordEncoder passwordEncoder;
   @Autowired MemberService memberService;
   @Autowired MemberRepository memberRepository;
   @Autowired EntityManager em;


    /**
     * 회원가입
     */
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

        memberRepository.save(member);
        assertEquals(member,memberRepository.findOne(saveId));

    }
    /**
     * 로그인
     */
    @Test
    public void 로그인() throws Exception {
        String email = "molba06@naver.com";
        String password = "ehsqjfwk123!";

        // 주입된 passwordEncoder를 사용하여 비밀번호를 인코딩
        String encodedPassword = passwordEncoder.encode(password);
        String dbPassword = loginRepository.findByEmail(email).get().getPassword();

        // findByEmail 메서드의 반환 타입이 Optional<Member> 인 경우에만 filter 사용
        assertAll(
                () -> assertNotEquals(encodedPassword,dbPassword),
                () -> assertTrue(passwordEncoder.matches(password,encodedPassword))
        );
    }


    /**
     *  로그아웃
     */

}