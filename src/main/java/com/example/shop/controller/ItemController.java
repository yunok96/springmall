package com.example.shop.controller;

import com.example.shop.exception.ItemNotFoundException;
import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.Item;
import com.example.shop.service.ItemService;
import com.example.shop.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final S3Service s3Service;

    private static final int pageSize = 5; // 한 페이지에 표시할 항목 수

    @GetMapping("/list")
    public String getListPage(@RequestParam(defaultValue = "1") int page, Model model) {
        int adjustedPage = page - 1; // 페이지는 0부터 시작하므로 -1 처리
        Page<Item> result = itemService.getItemPage(adjustedPage, pageSize);

        model.addAttribute("items", result.getContent()); // 현재 페이지의 데이터
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", result.getTotalPages()); // 전체 페이지 수
        model.addAttribute("isEmpty", result.isEmpty()); // 결과가 없는지 확인

        return "item/list"; // list 렌더링
    }

    @GetMapping("/search")
    public String itemSearch(@RequestParam(defaultValue = "1") int page
                           , @RequestParam(name = "keyword") String keyword
                           , Model model) {
        int adjustedPage = page - 1; // 페이지는 0부터 시작하므로 -1 처리
        Page<Item> result = itemService.searchItem(keyword, adjustedPage, pageSize);
        model.addAttribute("items", result);
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", result.getTotalPages()); // 전체 페이지 수
        model.addAttribute("isEmpty", result.isEmpty()); // 결과가 없는지 확인
        model.addAttribute("keyword", keyword); // 검색어를 모델에 추가

        return "item/search";
    }

    @GetMapping("/write")
    public String write() {
        return "item/write";
    }

    @PostMapping("/save")
    public String addItem(@RequestParam(name = "name") String name
                        , @RequestParam(name = "price") double price
                        , @RequestParam(value = "fileURL", required = false) String fileURL
                        , @AuthenticationPrincipal CustomUser customUser) {
        String username = customUser.getUsername();
        itemService.addItem(name, price, fileURL, username);

        return "redirect:/list";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam("filename") String filename) {
        return s3Service.createPresignedUrl("shop-item-image/"+filename);
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Item item = itemService.getItem(id);
        model.addAttribute("item", item);

        return "item/detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Item item = itemService.getItem(id);
        model.addAttribute("item", item);

        return "item/edit";
    }

    @PostMapping("/editing")
    public String editItem(@RequestParam(name = "id") int id
                         , @RequestParam(name = "name") String name
                         , @RequestParam(name = "fileURL") String fileURL
                         , @RequestParam(name = "price") double price) {
        itemService.editItem(id, name, fileURL, price);
        return "redirect:/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable(name = "id") int id) {
        try {
            itemService.deleteItemById(id);
            return ResponseEntity.status(200).body("Item deleted successfully.");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
