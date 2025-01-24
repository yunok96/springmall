package com.example.shop.controller;

import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.ItemComment;
import com.example.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/getComment/{itemId}")
    public ResponseEntity<?> getComments(@PathVariable("itemId") int itemId
                                       , @RequestParam(defaultValue = "0") int page
                                       , @RequestParam(defaultValue = "10") int size) {
        Page<ItemComment> comments = commentService.getItemCommentPage(itemId, page, size);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/postComment")
    @PreAuthorize("isAuthenticated()")
    String postComment(@RequestParam("itemId") int itemId, @RequestParam("comment") String comment, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        user.getUsername();

        ItemComment data = new ItemComment();
        data.setComment(comment);
        commentService.addComment(itemId, comment, user.getUsername());
        return "redirect:/list";
    }

}
