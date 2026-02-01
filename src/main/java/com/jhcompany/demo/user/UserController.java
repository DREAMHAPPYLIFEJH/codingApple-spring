package com.jhcompany.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor // 이게 자동으로 repo를 초기화시켜줌
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    // 로그인을 하는 방식에는 1. session방식 2. token방식이 존재한다.
    // 1.session방식은 유저마다 session id를 발급하고 서버에 제출된 session id와 db의 session id가 동일한지를 확인한다.
    // redis : session id를 보관하는 전용 db(입출력 빠름)
    // 2. token방식은 아이디, 로그인날짜, 유효기간 data로 server에서 검증함. 이는 짧은 문자로 암호화되어서 해킹의 문제는 없다.
    // 장점 : user <-> server만 소통을하기에 GET, POST같은 통신 방식은 사용하지않아도된다. --> Q. 그럼 어떻게 계정검증?
    // OAuth : kakao, google 로그인 같은 기능 구현 규칙, Sprin Security로 기능을 구현함
    // OAuth기능 - Spring Security, thymeleaf Spring Security 라이브러리로 구현
    // Spring Security는 기본적으로 모든 POST, PUT, DELETE 요청에 대해 CSRF 토큰을 요구합니다. 토큰이 없으면 403 Forbidden 에러가 발생합니다.
    @PostMapping("/addUser")
    public String signIn(String id, String pw) {
        userService.signIn(id, pw);
        return "redirect:/list";
    }

    @GetMapping("/signIn")
    public String signInScreen() {
        return "signIn.html";
    }
}
