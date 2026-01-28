package com.jhcompany.demo; // 파일의 경로가 이렇게 명시되어있지않으면 class를 쓸 수 없음

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.*;

// Azure로 mysql db배포
// DB Browser plugin으로 db관리 + DBeaver프로그램으로 db 데이터 확인
// 보통 서버코드에서 코드로 데이터를 입출력
// ORM(Oriented Relational Mapping) : 객체와 데이터베이스 테이블을 연결(Mapping)해주는 기술
// JPA(Java Persistence API) : Java에서 ORM을 사용하기위한 표준 규격(인터페이스)
// Spring Boot의 JPA를 사용하면 자동으로 Hibernate가 설치되고 사용됨
// application.properties는 환경설정을 하는 파일임. JPA를 사용하는 라이브러리를 사용하기위해선 엔드포인트 URL, ID, PassWD가 필요함.
// Database 안에 table들이 존재함(포함개념)
// JPA 입출력을 위해서는 1. repo를 만들고 2. Controller 클래스에 repo를 등록하고 3. repo.입출력함수() 사용
// th:each라는 html 반복문을 사용해서 card를 여러개 생성할 수 있음.
// 개발자는 처음보는 것도 응용해서 사용할줄 알아야한다.
@Controller
@RequiredArgsConstructor // final필드나 @NonNull이 붙은 필드에대한 생성자를 자동으로 생성
public class ItemController {

    private final ItemRepository itemRepository;
//    @Autowired
//    public ItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping("/list") // 이 URL에 방문해야 아래의 함수가 실행됨
    String list(Model model) {
//        ArrayList<Object> a = new ArrayList<>(); // List(상위) <- ArrayList(하위), LinkedList(하위)
//        a.add(30);
//        a.add(40);
//        System.out.println(a.get(0));
//        System.out.println(a.get(1));

        var a = new Item();
        System.out.println(a);

        var b = new Human();
        b.setName("이지훈");
        b.setAge(20);
        b.한살더하기();
        b.나이설정(22);
        System.out.println(b.getAge() + b.getName());

        var result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }
    // GET방식 : URL에 데이터가 보임 EX) search?keyword=노트북
    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    // POST방식 : URL에 데이터가 보이지않음
    @PostMapping("/add")
    String addPost(String title, Integer price) { // URL로부터 데이터를 받는 어노테이션(그냥 맞춰쓰면됨)
        // 유저가 보낸 데이터를 검증하고 전송함.
//        Map<String, Object> test = new HashMap<>();
//        test.put("name", "kim");
//        test.put("age", 20);

        // 서버에서 데이터를 받아서 Item 테이블에 저장해주는 기능을 제공
        // 1. 서버에서 데이터를 받음 2. 받은 데이터를 sql에 저장
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);

        return "redirect:/list";
    }
    // ERROR : id에 문자 abc가 들어오면 자동으로 에러페이지로 이동함. error.html을 생성하면 error페이지로 이동
    // 400 - 유저 fault, 500 - 서버 fault
    @GetMapping("/detail/{id}") // {id} 자리에 들어온 값이 동적으로 컨트롤러 메서드로 전달됩니다.
    String detail1(@PathVariable Long id, Model model) {
        try {
            Optional<Item> result = itemRepository.findById(id);
            if(result.isPresent()) {
                model.addAttribute("data", result.get());
                return "detail.html";
            } else {
                return "redirect:/list";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error.html";
//          return ResponseEntity.status(400).body("니잘못임"); // 이건 itemRepo에서의 데이터 받는 에러만 처리됨. type에러는 안됨.
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.status(400).body("에러입니다");
    }
}