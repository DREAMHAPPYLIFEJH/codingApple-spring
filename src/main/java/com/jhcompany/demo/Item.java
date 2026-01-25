package com.jhcompany.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity는 class내의 attribute를 자동으로 sql문으로 변환시켜주는 역할을 함.
// @Id @GeneratedValue는 각 데이터 별로 구분할 수 있는 인덱스를 추가시켜주는 변수에 붙이는 config이다.
// attribute마다 @Column을 붙여 세부 config를 설정할 수 있는데, 그떄마다 라이브러리를 찾아서 사용하면 된다.(뭐든지 이러는게 좋음)
// shop이라는 db안에 item, announcement라는 테이블을 만드는 것이다.
@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;
    public String title;
    public Integer price;
}
