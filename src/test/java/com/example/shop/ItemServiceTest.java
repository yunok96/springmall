package com.example.shop;

import com.example.shop.entity.Item;
import com.example.shop.repository.ItemRepository;
import com.example.shop.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // JUnit5에서는 이 어노테이션을 사용
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository; // mock 객체

    @InjectMocks
    private ItemService itemService; // mock 객체가 주입되는 실제 서비스

    @Test
    void testAddItem() { // 상품 추가 테스트
        // 준비: 필요한 값 설정
        String name = "Test Item";
        double price = 100.0;
        String fileURL = "http://example.com/item.jpg";
        String username = "testUser";

        // 호출: service 메서드 실행
        itemService.addItem(name, price, fileURL, username);

        // 검증: itemRepository.save()가 한 번 호출되었는지 확인
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void testSearchPageByTitle() { // 상품 검색 테스트
        // Given: 테스트 데이터를 저장
        Item item1 = new Item();
        item1.setName("반바지");
        item1.setPrice(10000);
        item1.setImageURL("http://example.com/item1.jpg");
        item1.setWriter("testUser1");

        Item item2 = new Item();
        item2.setName("바지");
        item2.setPrice(20000);
        item2.setImageURL("http://example.com/item2.jpg");
        item2.setWriter("testUser2");

        // When: 검색 메서드 호출에 대한 동작을 명시적으로 설정
        Mockito.when(itemRepository.searchPageByTitle("반바", PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(item1)));  // 원하는 데이터를 반환하도록 설정

        // Then: 검색 메서드 호출 후 결과 검증
        Page<Item> result = itemRepository.searchPageByTitle("반", PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements()); // '반바지' 한 건만 검색
        assertEquals("반바지", result.getContent().get(0).getName());
    }
}
