package com.example.shop.service;

import com.example.shop.model.Role;
import com.example.shop.model.entity.Member;
import com.example.shop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void addMember() {
        // given
        String username = "testuser";
        String displayName = "Test User";
        String hashPassword = "hashedPassword123";
        Role role = Role.ADMIN;

        // when
        memberService.addMember(username, displayName, hashPassword, role);

        // then
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(captor.capture());

        Member savedMember = captor.getValue();
        assertEquals(username, savedMember.getUsername());
        assertEquals(displayName, savedMember.getDisplayName());
        assertEquals(hashPassword, savedMember.getPassword());
        assertEquals(role, savedMember.getRole());
    }
}