package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findPageBy(Pageable page);

    Page<Item> searchPage(Pageable page);

    @Query(value = "SELECT * FROM shop.item WHERE MATCH(name) AGAINST(?1)", nativeQuery = true)
    Page<Item> searchPageByTitle(String title, Pageable page);

}
