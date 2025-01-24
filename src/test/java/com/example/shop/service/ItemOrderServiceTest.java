package com.example.shop.service;

import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.ItemOrder;
import com.example.shop.repository.ItemOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemOrderServiceTest {

    @Mock
    private ItemOrderRepository itemOrderRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ItemOrderService itemOrderService;

    private CustomUser customUser;

    @BeforeEach
    void setUp() {
        // CustomUser 설정
        customUser = new CustomUser("testuser", "password", null);
        customUser.setUserId(1); // 사용자 ID 설정
    }

    @Test
    void postOrder() {
        // given
        int itemId = 1;
        int quantity = 2;
        double price = 100.0;
        int memberId = 1;

        // ItemOrder 객체를 저장하는 메서드가 호출될 때 mock 설정
        doNothing().when(itemOrderRepository).save(any(ItemOrder.class));

        // when
        itemOrderService.postOrder(itemId, quantity, price, memberId);

        // then
        // itemOrderRepository.save() 메서드가 1번 호출되었는지 확인
        verify(itemOrderRepository, times(1)).save(any(ItemOrder.class));
    }

    @Test
    void getOrderPageBySeller() {
        // given
        int page = 0;
        int pageSize = 5;

        // Authentication 객체에서 CustomUser를 반환하도록 설정
        when(authentication.getPrincipal()).thenReturn(customUser);

        // ItemOrder 객체 리스트 설정
        ItemOrder order1 = new ItemOrder();
        ItemOrder order2 = new ItemOrder();
        List<ItemOrder> orders = List.of(order1, order2);
        Page<ItemOrder> orderPage = new PageImpl<>(orders);

        // itemOrderRepository의 findOrderPageBySellerId 메서드가 호출될 때 mock 설정
        when(itemOrderRepository.findOrderPageBySellerId(anyInt(), any(PageRequest.class)))
                .thenReturn(orderPage);

        // when
        Page<ItemOrder> result = itemOrderService.getOrderPageBySeller(authentication, page, pageSize);

        // then
        // 반환된 결과가 null이 아니어야 한다
        assertNotNull(result);
        // 반환된 페이지의 크기가 2여야 한다
        assertEquals(2, result.getContent().size());
        // findOrderPageBySellerId 메서드가 1번 호출되었는지 확인
        verify(itemOrderRepository, times(1)).findOrderPageBySellerId(1, PageRequest.of(page, pageSize));
    }
}