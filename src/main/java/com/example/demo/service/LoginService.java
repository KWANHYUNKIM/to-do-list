package com.example.demo.service;

import com.example.demo.controller.Password;
import com.example.demo.domain.Member;
import com.example.demo.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * @return null 로그인 실패
     */
    public Member login(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return loginRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(password,encodedPassword))
                .orElse(null);
        }
}

