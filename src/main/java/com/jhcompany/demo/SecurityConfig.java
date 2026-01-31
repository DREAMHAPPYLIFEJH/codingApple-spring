package com.jhcompany.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // FilterChain : 유저의 요청과 서버의 응답사이에 자동으로 실행해주고싶은 코드를 담는 곳
    // csrf : client에서 server측으로 random 문자를 전송하고 이를 server에서 확인하는 security방식
    // jwt방식으로는 headers에 random 문자를 전송하는 식으로 검증하기에 csrf를 사용하지않는 경우가 많음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); 
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 모든 url에서 login을 요구하지않게 하기에 login화면이 안뜸
        );
        return http.build();
    }

    // id, pw를 치고 db에 저장시키면 그것이 회원가입기능임
    // pw는 hashing 알고리즘을 사용하여 암호화해야함
}
