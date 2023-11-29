package com.example.demo;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;
    private final InitBoard initBoard;
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initBoard.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = createMember("molba06@naver.com", "김관현"," 950326","ehsqjfwk123!","010-6620-2454","client");
            em.persist(member);
        }

        public void dbInit2() {
            System.out.println("Init2" + this.getClass());
            Member member = createMember("manager@gmail.com", "김매니저","비공개","1234","비공개","manager");
            em.persist(member);
        }

        private Member createMember(String email, String username, String birthday, String password, String phonenumber, String position) {
            Member member = new Member();
            String encodePassword = passwordEncoder.encode(password);
            member.setEmail(email);
            member.setUsername(username);
            member.setBirthday(birthday);
            member.setPassword(encodePassword);
            member.setPhonenumber(phonenumber);
            member.setPosition(position);
            return member;
        }
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitBoard {

        private final EntityManager em;
        private final MemberRepository memberRepository;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());

            Optional<Member> optionalMember = Optional.ofNullable(memberRepository.findByEmail("molba06@naver.com"));

            // 결과가 존재할 경우에만 Member 객체를 가져와서 사용
            optionalMember.ifPresent(member -> {
                Board board = createBoard("하이루", "안녕하세요 이번에 입사한 최해영입니다", "57c87c27-5361-413d-9fae-1c5e62019c60_00.jpg", "/images/57c87c27-5361-413d-9fae-1c5e62019c60_00.jpg", member);
                em.persist(board);

                Comment comment = createComment("안녕하세요! 반가워요", board, member);
                em.persist(comment);
            });
        }

        private Board createBoard(String content, String title, String filename, String filepath, Member member) {
            Board board = new Board();
            board.setContent(content);
            board.setTitle(title);
            board.setFilename(filename);
            board.setFilepath(filepath);
            board.setMember(member);
            return board;
        }

        private Comment createComment(String commentContent, Board board, Member member) {
            Comment comment = new Comment();
            comment.setComment(commentContent);
            comment.setBoard(board);
            comment.setMember(member);
            return comment;
        }
    }
}
