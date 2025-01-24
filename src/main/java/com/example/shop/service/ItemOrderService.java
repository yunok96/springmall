package com.example.shop.service;

import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.ItemComment;
import com.example.shop.model.entity.ItemOrder;
import com.example.shop.repository.ItemOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;

    public void postOrder(int itemId, int quantity, double price, int memberId) {
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItemById(itemId);
        itemOrder.setQuantity(quantity);
        itemOrder.setPrice(price);
        itemOrder.setMemberById(memberId);

        itemOrderRepository.save(itemOrder);
    }

    public Page<ItemOrder> getOrderPageBySeller(Authentication auth, int page, int pageSize) {
        // Authentication 객체에서 CustomUser를 가져옴
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        int sellerId = customUser.getUserId();

        return itemOrderRepository.findOrderPageBySellerId(sellerId, PageRequest.of(page, pageSize));
    }
}
