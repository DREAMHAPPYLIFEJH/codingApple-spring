package com.jhcompany.demo.Item;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Entity는 class내의 attribute를 자동으로 sql문으로 변환시켜주는 역할을 함.
// @Id @GeneratedValue는 각 데이터 별로 구분할 수 있는 인덱스를 추가시켜주는 변수에 붙이는 config이다.
// attribute마다 @Column을 붙여 세부 config를 설정할 수 있는데, 그떄마다 라이브러리를 찾아서 사용하면 된다.(뭐든지 이러는게 좋음)
// shop이라는 db안에 item, announcement라는 테이블을 만드는 것이다.
@Entity
@ToString // ToString함수를 자동으로 만들어줌
public class Item {
    // static은 원본 그대로를 사용하는 함수이다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter
    public Long Id;
    
    @Setter @Getter // 자동으로 setter, getter함수를 만들어줌
    public String title;

    @Setter @Getter
    public Integer price;
}
