package com.jhcompany.demo.Sales;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

// [ 제 2정규화 ]
// 다른 테이블에 이미 있던 컬럼은 중복해서 만들 필요가 없음 > 중복된 데이터를 제거하고 데이터 테이블을 가리키는 컬럼만을 기재함.
// ex) 구매자 id로 member table의 member 데이터를 추정함.
// 이유1. displayName같은 것을 바꾸었을 때 다른 테이블 displayName 데이터를 수정할 수 없기때문임.
// 이유2. item 테이블에서 price값을 바꾸었을 때 Sales price값을 바꿀 수 없기에 price값도 제거하는 것이 좋음.
// 이유3. 테이블과 관련이 없는 컬럼은 빼는 것이 좋음.
@Entity
public class Sales {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String itemName;
    Integer price;
    Integer count;
    Long memberId;
    @CreationTimestamp
    LocalDateTime created;
}
