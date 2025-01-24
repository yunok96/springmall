package com.example.shop.service;

import com.example.shop.model.entity.Item;
import com.example.shop.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private static final int MOCK_ITEM_ID = 1;
    private static final String MOCK_ITEM_NAME = "mock item";
    private static final double MOCK_ITEM_PRICE = 100;
    private static final String MOCK_ITEM_FILE_URL = "http://test.com/image.jpg";
    private static final String MOCK_ITEM_WRITER = "testuser";

    @Test
    @DisplayName("상품 페이지 조회 테스트")
    void getItemPage() {
        // given
        Page<Item> mockPage = new PageImpl<>(Arrays.asList(new Item(), new Item()));
        when(itemRepository.findPageBy(any(PageRequest.class))).thenReturn(mockPage);

        // when
        Page<Item> result = itemService.getItemPage(0, 10);

        // then
        assertEquals(2, result.getContent().size());
        verify(itemRepository, times(1)).findPageBy(any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 검색 테스트")
    void searchItem() {
        // given
        String title = "test";
        Page<Item> mockPage = new PageImpl<>(Arrays.asList(new Item(), new Item(), new Item()));
        when(itemRepository.findByNameContaining(eq(title), any(PageRequest.class))).thenReturn(mockPage);

        // when
        Page<Item> result = itemService.searchItem(title, 0, 10);

        // then
        assertEquals(3, result.getContent().size());
        verify(itemRepository, times(1)).findByNameContaining(eq(title), any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 조회 테스트")
    void getItem() {
        // given
        int itemId = 1;
        Item mockItem = new Item();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItem));

        // when
        Optional<Item> result = itemService.getItem(itemId);

        // then
        assertTrue(result.isPresent());
        verify(itemRepository, times(1)).findById(itemId);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemById() {
        // given
        int itemId = 1;
        when(itemRepository.existsById(itemId)).thenReturn(true);

        // when
        boolean result = itemService.deleteItemById(itemId);

        // then
        assertTrue(result);
        verify(itemRepository, times(1)).existsById(itemId);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addItem() {
        // given

        // when
        itemService.addItem(MOCK_ITEM_NAME, MOCK_ITEM_PRICE, MOCK_ITEM_FILE_URL, MOCK_ITEM_WRITER);

        // then
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository, times(1)).save(captor.capture());
        Item savedItem = captor.getValue();

        assertEquals(MOCK_ITEM_NAME, savedItem.getName());
        assertEquals(MOCK_ITEM_PRICE, savedItem.getPrice());
        assertEquals(MOCK_ITEM_FILE_URL, savedItem.getImageURL());
        assertEquals(MOCK_ITEM_WRITER, savedItem.getWriter());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void editItem() {
        // given

        // when
        itemService.editItem(MOCK_ITEM_ID, MOCK_ITEM_NAME, MOCK_ITEM_FILE_URL, MOCK_ITEM_PRICE);

        // then
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository, times(1)).save(captor.capture());
        Item updatedItem = captor.getValue();

        assertEquals(MOCK_ITEM_ID, updatedItem.getId());
        assertEquals(MOCK_ITEM_NAME, updatedItem.getName());
        assertEquals(MOCK_ITEM_FILE_URL, updatedItem.getImageURL());
        assertEquals(MOCK_ITEM_PRICE, updatedItem.getPrice());
    }
}