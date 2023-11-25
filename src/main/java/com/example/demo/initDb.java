package com.example.demo;

import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
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
}
