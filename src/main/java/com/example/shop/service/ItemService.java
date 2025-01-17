package com.example.shop.service;

import com.example.shop.entity.Item;
import com.example.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> getItemPage(int page, int pageSize) {
        return itemRepository.findPageBy(PageRequest.of(page, pageSize));
    }

    public Page<Item> searchItem(String title, int page, int pageSize) {
        return itemRepository.searchPageByTitle(title, PageRequest.of(page, pageSize));
    }

    public Optional<Item> getItem(int id) {
        return itemRepository.findById(id);
    }

    public boolean deleteItemById(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // 삭제할 아이템 없음
    }

    public void addItem(String name, double price, String fileURL, String username) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setImageURL(fileURL);
        item.setWriter(username);

        itemRepository.save(item);
    }

    public void editItem(int id, String name, double price) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setPrice(price);

        itemRepository.save(item);
    }

}
