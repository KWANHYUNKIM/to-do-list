package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    /**
     * @return null 로그인 실패
     */
    public Member login(String email, String password) {
        return loginRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
        }
}

