package com.example.shop.service;

import com.example.shop.exception.ItemNotFoundException;
import com.example.shop.model.entity.Item;
import com.example.shop.model.entity.Member;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public Page<Item> getItemPage(int page, int pageSize) {
        return itemRepository.findPageBy(PageRequest.of(page, pageSize));
    }

    public Page<Item> searchItem(String title, int page, int pageSize) {
        return itemRepository.findByNameContaining(title, PageRequest.of(page, pageSize));
    }

    public Item getItem(int id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + id));
    }

    public void deleteItemById(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void addItem(String name, double price, String fileURL, String username) {
        Member writer = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setImageURL(fileURL);
        item.setWriter(writer);

        itemRepository.save(item);
    }

    public void editItem(int id, String name, String fileURL, double price) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setImageURL(fileURL);
        item.setPrice(price);

        //TODO : 이미지 수정 기능 추가. 서비스는 테스트 완료. 화면 구현 남음

        itemRepository.save(item);
    }

}
