package com.jhcompany.demo.Item; // 파일의 경로가 이렇게 명시되어있지않으면 class를 쓸 수 없음

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
// AJAX(Asynchronous JavaScript And XML) : 비동기적으로 화면 새로고침없이 서버와 데이터를 통신하는 방법
// VPC(Virtual Private Cloud) : 클라우드 환경에서 논리적으로 격리된 가상의 네트워크 공간
// java -jar 어쩌구.jar 명령어를 터미널에서 타이핑하면 웹서버가 실행
@Controller
@RequiredArgsConstructor // final필드나 @NonNull이 붙은 필드에대한 생성자를 자동으로 생성
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3service s3service;
    // Spring에게 @Controller같은 annotation을 붙이면 Spring IoC Container에 controller같은 bean객체를 집어넣음.

//    @Autowired --> 스프링에게 알아서 Repo Object를 넣으라는 뜻
//    public ItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    // !!! error : list.html파일에서 totalPages, currentPage 데이터를 사용해서 ui를 구성해서 이 GetMapping은 적용되지않음.
    @GetMapping("/list") // 이 URL에 방문해야 아래의 함수가 실행됨
    String list(Model model) {
        var result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "/list";
    }

    // GET방식 : URL에 데이터가 보임 EX) search?keyword=노트북 / 데이터 조회
    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    // POST방식 : URL에 데이터가 보이지않음(데이터를 보낸다(바꾼다))
    @PostMapping("/add")
    String addPost(String title, Integer price) { // URL로부터 데이터를 받는 어노테이션(그냥 맞춰쓰면됨)
        // 유저가 보낸 데이터를 검증하고 전송함.
        // 서버에서 데이터를 받아서 Item 테이블에 저장해주는 기능을 제공
        // 1. 서버에서 데이터를 받음 2. 받은 데이터를 sql에 저장
        itemService.saveItem(title, price);

        return "redirect:/list";
    }
    
    // edit 조회
    @GetMapping("/edit/{id}")
    String editForm(@PathVariable Long id, Model model) {
        // 1. list.html에 수정버튼 만들기 2. edit.html파일 만들고
        // 1. 유저가 입력한 데이터를 db에 전송 2. 입력한 데이터를 db에 입력 3. 다시 edit.html에 출력
        try {
            Optional<Item> result = itemRepository.findById(id);
            if(result.isPresent()) {
                model.addAttribute("data", result.get());
                return "edit.html";
            } else {
                return "redirect:/list";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error.html";
//          return ResponseEntity.status(400).body("니잘못임"); // 이건 itemRepo에서의 데이터 받는 에러만 처리됨. type에러는 안됨.
        }
    }

    // 한번에 모든걸 만들려고하면 어려움
    // edit 수정
    @Transactional // SQL Transaction을 자동으로 실행해줌
    @PostMapping("/edit/{id}") // PathVariable - url연결 vari / RequestParam - html로부터 전달된 param
    String editItem(@PathVariable Long id, @RequestParam String title, @RequestParam Integer price) {
        itemService.editItem(id, title, price);
        // 예외처리 1. price값에 음수값 입력 2. title값을 100자 이상 작성

        return "redirect:/list";
    }
    
    // ERROR : url id에 문자 abc가 들어오면 자동으로 에러페이지로 이동함. error.html을 생성하면 error페이지로 이동
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

    @GetMapping("/test1") // query string 전송방식 : url 뒤에 ?이름=값&~~ 형식의 데이터를 나열하여 데이터를 전송
    String test1(@RequestParam String age) { // @RequestBody는 POST방식 body,
        System.out.println(age);
        return "redirect:/list";
    }

    // 변수에 뭐가 들어있는지 항상 체크하면서 하는것이 코드 안정성을 높인다.
    // @ResponseEntity는 HTTP 응답 전체를 직접 제어하기위해 사용하는 객체이다.
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/page/{num}")
    public String getListPage(Model model, @PathVariable int num) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(num - 1,5));
        var totalPages = result.getTotalPages(); // 전체 페이지수
        model.addAttribute("items", result);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", num);
        return "list.html";
    }

    // Presigned URL : 유저가 서버를 거치지않고 서버로부터 Presigned URL을 받아 fetch방식으로 이미지를 업로드
    // 단점 : 이상한 이미지를 upload하는 걸 막을 수 없음.
    // 엔드포인트 : 네트워크에 연결되어있는 모든 물리적 장치
    // 기능 : aws s3에 folder를 만들고 image를 업로드 -> 업로드된 이미지를 가져와서 getListPage함수에 넣어주면 될듯.
    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename) { // onchange에 getURL함수를 연동시켜놓음
        var result = s3service.createPresignedUrl("images/" + filename); // test/파일이름 경로로 이미지 파일을 저장함.
        System.out.println(result);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e) {
        return ResponseEntity.status(400).body("에러입니다");
    }
}