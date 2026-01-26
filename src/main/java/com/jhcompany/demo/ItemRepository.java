package com.jhcompany.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA가 런타임에 자동으로 함수들을 생성해줌
public interface ItemRepository extends JpaRepository<Item, Long> {
    // public Item save(Item item) { /* 저장 로직 */ }

    // public Optional<Item> findById(Long id) { /* 조회 로직 */ }

    // public List<Item> findAll() { /* 전체 조회 로직 */ }

    // public void deleteById(Long id) { /* 삭제 로직 */ }
}
