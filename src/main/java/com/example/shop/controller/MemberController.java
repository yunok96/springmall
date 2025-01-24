package com.example.shop.controller;

import com.example.shop.model.Role;
import com.example.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/addMember")
    public String addMember(@RequestParam(name = "username") String username
                          , @RequestParam(name = "displayName") String displayName
                          , @RequestParam(name = "password") String password
                          , @RequestParam(name = "role") String role) {
        Role userRole = Role.valueOf(role);

        String hashPassword = passwordEncoder.encode(password);

        memberService.addMember(username, displayName, hashPassword, userRole);

        return "redirect:/list";
    }

    @GetMapping("/myPage")
    public String myPage(Authentication auth) {
        if( auth == null ) {
            return "redirect:/login";
        }
        return "member/myPage";
    }

}
