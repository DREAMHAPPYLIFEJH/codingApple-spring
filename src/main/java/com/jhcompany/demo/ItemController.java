package com.jhcompany.demo; // 파일의 경로가 이렇게 명시되어있지않으면 class를 쓸 수 없음

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

// Azure로 mysql db배포
// DB Browser plugin으로 db관리 + DBeaver프로그램으로 db 데이터 확인
// 보통 서버코드에서 코드로 데이터를 입출력
// ORM(Oriented Relational Mapping) : 객체와 데이터베이스 테이블을 연결(Mapping)해주는 기술
// JPA(Java Persistence API) : Java에서 ORM을 사용하기위한 표준 규격(인터페이스)
// Spring Boot의 JPA를 사용하면 자동으로 Hibernate가 설치되고 사용됨
// application.properties는 환경설정을 하는 파일임. JPA를 사용하는 라이브러리를 사용하기위해선 엔드포인트 URL, ID, PassWD가 필요함.
// Database 안에 table들이 존재함(포함개념)
@Controller
public class ItemController {
    @GetMapping("/list")
    String list(Model model) {
        model.addAttribute("name", "이지훈");
        return "list.html";
    }
}