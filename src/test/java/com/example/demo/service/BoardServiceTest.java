package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
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
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired CommentService commentService;
    @Autowired BoardService boardService;
    @Autowired EntityManager em;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired LoginRepository loginRepository;

    @Test
    //@Rollback(false) // 롤백하지 않도록 설정
    public void 게시판_생성() throws Exception {
        String password = "ehsqjfwk123!";
        String encodePassword = passwordEncoder.encode(password);

        Member member = new Member();
        member.setUsername("kim");
        member.setBirthday("950326");
        member.setEmail("molba06@naver.com");
        member.setPassword(encodePassword);
        member.setPhonenumber("010-1234-1234");

        em.persist(member);

        Board board = new Board();
        board.setMember(member);
        board.setTitle("하이루");
        board.setContent("하이루");

        em.persist(board);
    }
}