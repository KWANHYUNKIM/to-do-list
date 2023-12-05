package com.example.demo.controller.config;

import com.example.demo.controller.auth.MemberDetailsService;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
// securedEnabled: @Secured 어노테이션 활성화. @Secured? 특정 URL에 대해서만 간단하게 권한 처리를 할 수 있는 어노테이션
// prePostEnabled: @PreAuthorize, @PostAuthorize 어노테이션 활성화.
// @PreAuthorize: 해당 메소드 진입 전 처리. @PostAuthorize: 해당 메소드 진입 후 처리

@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDetailsService detailsService;
    private final DataSource dataSource;

    /**
     * Spring Security의 앞단 설정을 할수 있다.
     * debug, firewall, ignore등의 설정이 가능
     * 단 여기서 resource에 대한 모든 접근을 허용하는 설정할수도 있는데
     * 그럴경우 SpringSecuity에서 접근을 통제하는 설정은 무시해버린다.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    // 인증 관리자 권한 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
    }
    @Bean //비밀번호 암호화
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/images/**", "/css/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers("/members/**").hasAnyRole("ROLE_USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/joinProc")
                .usernameParameter("email")
                .successHandler(loginSuccessHandler())  // 커스텀 로그인 성공 핸들러 추가
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired=true");
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        LoginSuccessHandler handler = new LoginSuccessHandler();
        handler.setDefaultTargetUrl("/members/home");
        return handler;
    }
    // remember-me tokenRepository의 구현체
    @Bean
    public PersistentTokenRepository tokenRepository() {

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}