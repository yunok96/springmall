package com.example.shop.controller;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import com.example.shop.CustomUser;
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

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/addMember")
    public String addMember(@RequestParam(name = "username") String username
                          , @RequestParam(name = "displayName") String displayName
                          , @RequestParam(name = "password") String password) {
        String hashPassword = passwordEncoder.encode(password);

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(hashPassword);
        member.setDisplayName(displayName);

        memberRepository.save(member);

        return "redirect:/list";
    }

    @GetMapping("/myPage")
    public String myPage(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();

        System.out.println(user.getDisplayName());


        if( auth == null ) {
            return "redirect:/login";
        }
        return "myPage.html";
    }

}
