package com.example.shop.controller;

import com.example.shop.CustomUser;
import com.example.shop.service.ItemOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ItemOrderController {

    private final ItemOrderService itemOrderService;

    @PostMapping("/order")
    public String order(@RequestParam("itemId") int itemId
                      , @RequestParam("price") double price
                      , @RequestParam("quantity") int quantity
                      , Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        int userId = user.getUserId();

        itemOrderService.order(itemId, quantity, price, userId);

        return "redirect:/list";
    }

    @GetMapping("/getOrder")
    public String getOrderData(@RequestParam("itemId") int itemId, @RequestParam("count") int count) {

//        orderService.order();

        return "redirect:/list";
    }

}
