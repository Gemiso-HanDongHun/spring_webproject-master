package com.project.web_prj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티 설정을 웹에 적용
public class SecurityConfig {

    @Bean    // 빈 등록을 해두었다
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 기본 설정을 처리하는 빈
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 초기에 나오는 디폴트 로그인 화면 안뜨게 하기
        http.csrf().disable() // csrf공격 방어토큰 자동 생성 해제 
                .authorizeRequests() //권한요청 범위 설정       <-   여기 설정을 하지 않으면 계속 검사를 진행한다
                .antMatchers("/member/**")
                .permitAll() // /member로 시작하는 요청은 따로 권한 검증하지 말아라      <-  따로 검증을 만들테니 처음부터 검증을 하지 말라는 것(즉, 초기에 로그인폼이 뜨는 것을 막아줌)
                ;

        return http.build();
    }
}
