package com.example.shop.service;

import com.example.shop.entity.ItemOrder;
import com.example.shop.repository.ItemOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;

    public void order(int itemId, int quantity, double price, int memberId) {
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItemById(itemId);
        itemOrder.setQuantity(quantity);
        itemOrder.setPrice(price);
        itemOrder.setMemberById(memberId);

        itemOrderRepository.save(itemOrder);
    }

    public List<ItemOrder> getOrders(Authentication auth) {
        return itemOrderRepository.findAll();
    }
}
