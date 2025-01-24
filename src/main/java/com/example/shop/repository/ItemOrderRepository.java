package com.example.shop.repository;

import com.example.shop.model.entity.ItemOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Integer> {
    // 구매자 기준으로 주문 목록 조회
    Page<ItemOrder> findOrderPageByMemberId(int memberId, Pageable pageable);

    // 판매자 기준으로 주문 목록 조회
    @Query("SELECT io FROM ItemOrder io WHERE io.item.writer.id = :sellerId")
    Page<ItemOrder> findOrderPageBySellerId(@Param("sellerId") int sellerId, Pageable pageable);
}
