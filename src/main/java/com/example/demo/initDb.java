package com.example.demo;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.domain.kbo.Team;
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
        initBoard.dbTeam1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = createMember("molba06@naver.com", "김관현"," 950326","1234","010-6620-2454","ROLE_USER");
            em.persist(member);
        }

        public void dbInit2() {
            System.out.println("Init2" + this.getClass());
            Member member = createMember("manager@gmail.com", "김매니저","비공개","1234","비공개","ROLE_ADMIN");
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

        public void dbTeam1(){
            Team team = createTeam(1,"LG",144,84,56,2,0.606,"-","1승");
            Team team1 = createTeam(2,"KT",144,79,62,3,0.560,"6.5","3승");
            Team team2 = createTeam(3,"SSG",144,76,65,3,0.539,"9.5","2승");
            Team team3 = createTeam(4,"NC",144,75,67,2,0.528,"11","2패");
            Team team4 = createTeam(5,"두산",144,74,68,2,0.521,"12","3패");
            Team team5 = createTeam(6,"KIA",144,73,69,2,0.514,"13","2승");
            Team team6 = createTeam(7,"롯데",144,68,76,0,0.472,"19","1승");
            Team team7 = createTeam(8,"삼성",144,61,82,1,0.427,"25.5","1패");
            Team team8 = createTeam(9,"한화",144,58,80,6,0.420,"26","1패");
            Team team9 = createTeam(10,"키움",144,58,83,3,0.411,"27.5","2패");

            em.persist(team);
            em.persist(team1);
            em.persist(team2);
            em.persist(team3);
            em.persist(team4);
            em.persist(team5);
            em.persist(team6);
            em.persist(team7);
            em.persist(team8);
            em.persist(team9);

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

        private Team createTeam (int ranking, String teamName, int matches, int win, int defeat,
                                 int draw, double winningRate, String gamesBehind, String winningSteak ){
            Team team = new Team();
            team.setRanking(ranking);
            team.setTeamName(teamName);
            team.setMatches(matches);
            team.setWin(win);
            team.setDefeat(defeat);
            team.setDraw(draw);
            team.setWinningRate(winningRate);
            team.setGamesBehind(gamesBehind);
            team.setWinningSteak(winningSteak);
            return team;
        }
    }
}
