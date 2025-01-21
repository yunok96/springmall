package com.example.shop.repository;

import com.example.shop.model.entity.ItemComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCommentRepository extends JpaRepository<ItemComment, Integer> {

    Page<ItemComment> findPageByItemId(int itemId, Pageable page);

    List<ItemComment> findAllByItemId(int itemId);
}
