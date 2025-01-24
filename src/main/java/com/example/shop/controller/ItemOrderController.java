package com.example.shop.controller;

import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.ItemOrder;
import com.example.shop.service.ItemOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ItemOrderController {

    private final ItemOrderService itemOrderService;
    private static final int pageSize = 5; // 한 페이지에 표시할 항목 수

    @PostMapping("/postOrder")
    public String order(@RequestParam("itemId") int itemId
                      , @RequestParam("price") double price
                      , @RequestParam("quantity") int quantity
                      , Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        int userId = user.getUserId();

        itemOrderService.postOrder(itemId, quantity, price, userId);

        return "redirect:/list";
    }

    @GetMapping("/getOrderBySeller")
    public String getOrderBySeller(Authentication auth, @DefaultValue("1") int page, Model model) {
        // TODO : 판매자가 주문 정보를 조회하는 기능 구현 추가
        Page<ItemOrder> result = itemOrderService.getOrderPageBySeller(auth, page, pageSize);

        model.addAttribute("items", result.getContent()); // 현재 페이지의 데이터
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", result.getTotalPages()); // 전체 페이지 수
        model.addAttribute("isEmpty", result.isEmpty()); // 결과가 없는지 확인

        return "redirect:/list";
    }

}
