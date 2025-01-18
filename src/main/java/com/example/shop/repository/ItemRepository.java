package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findPageBy(Pageable page);

    // 상품명 검색 (LIKE %title%)
    Page<Item> findByNameContaining(String title, Pageable page);

    // MySQL Full-Text Search. 두글자 이상인 경우
//    @Query(value = "SELECT * FROM shop.item WHERE MATCH(name) AGAINST(?1)", nativeQuery = true)
//    Page<Item> searchPageByTitleMatch(String title, Pageable page);

}
