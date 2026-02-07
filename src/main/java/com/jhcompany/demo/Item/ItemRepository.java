package com.jhcompany.demo.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Spring Data JPA가 런타임에 자동으로 함수들을 생성해줌
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable page);

    List<Item> findAllByTitleContains(String title);

    // match against 문법이 자동적으로 연관도가 높은 데이터를 위로 가져와줌
    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    List<Item> rawQuery1(String text);

    // public Item save(Item item) { /* 저장 로직 */ }

    // public Optional<Item> findById(Long id) { /* 조회 로직 */ }

    // public List<Item> findAll() { /* 전체 조회 로직 */ }

    // public void deleteById(Long id) { /* 삭제 로직 */ }
}
