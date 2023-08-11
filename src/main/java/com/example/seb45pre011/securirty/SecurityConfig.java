package com.example.seb45pre011.securirty;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 아래와 같이 인증과 로그인 관련 설정을 주석 처리 또는 비활성화
        // http.formLogin().loginPage("/login").permitAll();
        // http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
        // ...
    }
}
