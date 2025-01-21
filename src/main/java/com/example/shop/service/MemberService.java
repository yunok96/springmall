package com.example.shop.service;

import com.example.shop.model.Role;
import com.example.shop.model.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void addMember(String username, String displayName, String hashPassword, Role role) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(hashPassword);
        member.setDisplayName(displayName);
        member.setRole(role);

        memberRepository.save(member);
    }


}
