package com.jhcompany.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

// 다른 페이지에 짜도 스프링 프레임워크가 알아서 메인함수에 넣어서 실행해줌
// 웹 페이지 가동방식 - [ url + 데이터요청방식(POST, GET, DELETE 등) -(요청)> 서버 ]
// REST API 권장사항 1. Uniform Interface(url은 일관적으로) 2. Client-server 구분(Client에게 server역할을 맡기지마라)
// 3. Stateless(요청끼리 의존성이 있으면 안된다) 4. Cacheability(요청은 캐싱이 가능해야함)
// 5. Layered system(요청은 응답전까지 여러 단계를 거쳐도 된다.) 6. Code on demand(서버는 유저에게 실행가능한 코드를 보낼 수 있음)
@Controller
public class BasicController {
    @GetMapping("/")
    String hello() {
        return "main.html";
    }

    @GetMapping("/about")
    @ResponseBody // 문자 데이터를 그대로 보내는 문법
    String about() {
        return "피싱사이트에요";
    }

    @GetMapping("/date")
    @ResponseBody
    String todayDate() {
        return ZonedDateTime.now().toString();
    }
}
