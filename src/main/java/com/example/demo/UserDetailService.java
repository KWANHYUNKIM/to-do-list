package com.example.demo;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.logging.Logger;

public class UserDetailService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    private MemberRepository memberRepository;

    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        logger.info("email ===" + email);
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException("해당 유저가 없습니다.");
        }

    }


}
