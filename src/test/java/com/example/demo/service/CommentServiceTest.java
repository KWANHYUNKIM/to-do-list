package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.repository.LoginRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {


    @Autowired CommentService commentService;
    @Autowired BoardService boardService;
    @Autowired EntityManager em;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired LoginRepository loginRepository;

    /**
     * 댓글 달기
     */
    @Test
    //@Rollback(false) // 롤백하지 않도록 설정
    public void 댓글달기() throws Exception {

        // 회원가입
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

        Comment comment = new Comment();
        comment.setComment("와우 이게 뭔가요?");
        comment.setMember(member);
        comment.setBoard(board);
        em.persist(comment);
    }
}


