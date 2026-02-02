package com.jhcompany.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.Authenticator;

@Controller
@RequiredArgsConstructor // 이게 자동으로 repo를 초기화시켜줌
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 로그인을 하는 방식에는 1. session방식 2. token방식이 존재한다.
    // 1.session방식은 유저마다 session id를 발급하고 서버에 제출된 session id와 db의 session id가 동일한지를 확인한다.
    // redis : session id를 보관하는 전용 db(입출력 빠름)
    // 2. token방식은 아이디, 로그인날짜, 유효기간 data로 server에서 검증함. 이는 짧은 문자로 암호화되어서 해킹의 문제는 없다.
    // 장점 : user <-> server만 소통을하기에 GET, POST같은 통신 방식은 사용하지않아도된다. --> Q. 그럼 어떻게 계정검증?
    // OAuth : kakao, google 로그인 같은 기능 구현 규칙, Sprin Security로 기능을 구현함
    // OAuth기능 - Spring Security, thymeleaf Spring Security 라이브러리로 구현
    // Spring Security는 기본적으로 모든 POST, PUT, DELETE 요청에 대해 CSRF 토큰을 요구합니다. 토큰이 없으면 403 Forbidden 에러가 발생합니다.
    @PostMapping("/addUser")
    public String signIn(String id, String pw, String displayName) {
        memberService.signIn(id, pw, displayName);
        return "redirect:/list";
    }

    @GetMapping("/signIn")
    public String signInScreen() {
        return "signIn.html";
    }

    // 1. login screen만들기 2. security에 login form설정하기(성공 실패시 연결되는 url) 3. db에 있던
    // Spring Security가 입력된 id, pw를 db의 id, pw와 비교해준다. 하지만 db의 id, pw는 가져와줘야함.
    // Spring Security가 세션에 인증정보를 저장함. 이후 모든 요청마다 세션에서 인증정보를 꺼내서 authentication객체에 주입함.
    // 로그인에 성공하면 서버에서 쿠키를 보내줘 기존에 로그인한 고객인지를 확인할 수 있음.
    // @PreAuthorize라는 API도 있어서 사용가능함.
    @GetMapping("/login")
    public String loginScreen() {
        var result = memberRepository.findByUserid("id1234");
        System.out.println(result);
        return "login.html";
    }

    // Security의 Authentication 객체로 MyUserDetailService함수 결과값이 날라옴.
    @GetMapping("/my-page") // thymeleaf에서 login된 정보를 제공함
    public String myPage(Authentication auth) { // auth가 null을 반환해서 에러를 발생시킴(로그인 x시)
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "mypage.html";
    }
}
