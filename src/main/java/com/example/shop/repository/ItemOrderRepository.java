package com.example.shop.repository;

import com.example.shop.model.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Integer> {
}
