package com.youngmok.myboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    // passwordEncoder를 사용하기 위한 passwordEncoder 설정
    @Bean // 직접 빈으로 등록하는 태그
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
