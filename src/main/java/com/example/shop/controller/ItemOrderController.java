package com.example.shop.controller;

import com.example.shop.model.CustomUser;
import com.example.shop.model.dto.PaymentResponse;
import com.example.shop.model.entity.ItemOrder;
import com.example.shop.service.ItemOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ItemOrderController {

    private final ItemOrderService itemOrderService;
    private static final int pageSize = 5; // 한 페이지에 표시할 항목 수

    @PostMapping("/processPayment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentResponse paymentResponse, Authentication auth) throws IOException {
        boolean verifyPayMent = itemOrderService.verifyPayment(paymentResponse.getOrderId());
        if (verifyPayMent) {
            CustomUser user = (CustomUser) auth.getPrincipal();
            int userId = user.getUserId();

            itemOrderService.postOrder(paymentResponse, userId);
        } else {
            return ResponseEntity.badRequest().body("Payment verification failed");
        }
        return ResponseEntity.ok("Payment processed successfully");
    }

    @GetMapping("/getOrderBySeller")
    public String getOrderBySeller(Authentication auth, @RequestParam(defaultValue = "1") int page, Model model) {
        int adjustedPage = page - 1; // 페이지는 0부터 시작하므로 -1 처리
        Page<ItemOrder> result = itemOrderService.getOrderPageBySeller(auth, adjustedPage, pageSize);

        model.addAttribute("items", result.getContent()); // 현재 페이지의 데이터
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", result.getTotalPages()); // 전체 페이지 수
        model.addAttribute("isEmpty", result.isEmpty()); // 결과가 없는지 확인

        return "itemOrder/orderBySeller";
    }

}
