package com.example.shop.service;

import com.example.shop.exception.ItemNotFoundException;
import com.example.shop.exception.UserNotFoundException;
import com.example.shop.model.CustomUser;
import com.example.shop.model.dto.PaymentResponse;
import com.example.shop.model.entity.ItemOrder;
import com.example.shop.repository.ItemOrderRepository;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;
    private final PayPalHttpClient payPalHttpClient;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public void postOrder(PaymentResponse paymentResponse, int memberId) {
        int itemId = paymentResponse.getItemId();
        int quantity = paymentResponse.getQuantity();
        String orderId = paymentResponse.getOrderId();
        String payerId = paymentResponse.getPayerId();
        double amount = paymentResponse.getAmount();

        // 주문할 상품이 존재하는지 확인
        itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + itemId));
        // 주문자가 존재하는지 확인
        memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException("Member not found with id: " + memberId));

        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItemById(itemId);
        itemOrder.setPaypalOrderId(orderId);
        itemOrder.setPaypalPayerId(payerId);
        itemOrder.setQuantity(quantity);
        itemOrder.setTotalPrice(amount);
        itemOrder.setMemberById(memberId);

        itemOrderRepository.save(itemOrder);
    }

    // PayPal API를 사용하여 결제 검증
    public boolean verifyPayment(String orderId) throws IOException {
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        HttpResponse<Order> response = payPalHttpClient.execute(request);
        if (response.statusCode() == 200) {
            // 결제 상태 확인
            Order order = response.result();
            if ("COMPLETED".equals(order.status())) {
                return true; // 결제 완료
            }
        }
        return false; // 결제 실패 또는 취소
    }

    public Page<ItemOrder> getOrderPageBySeller(Authentication auth, int page, int pageSize) {
        // Authentication 객체에서 CustomUser를 가져옴
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        int sellerId = customUser.getUserId();

        return itemOrderRepository.findOrderPageBySellerId(sellerId, PageRequest.of(page, pageSize));
    }
}
