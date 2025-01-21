package com.example.shop.service;

import com.example.shop.model.entity.ItemComment;
import com.example.shop.repository.ItemCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ItemCommentRepository itemCommentRepository;

    public Page<ItemComment> getItemCommentPage(int itemId, int page, int pageSize) {
        return itemCommentRepository.findPageByItemId(itemId, PageRequest.of(page, pageSize));
    }

    public void addComment(int itemId, String comment, String username) {
        ItemComment data = new ItemComment();
        data.setItemId(itemId);
        data.setComment(comment);
        data.setUsername(username);

        itemCommentRepository.save(data);
    }

}
