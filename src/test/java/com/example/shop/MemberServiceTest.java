package com.example.shop;


import com.example.shop.model.Role;
import com.example.shop.model.entity.Member;
import com.example.shop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // "test" 프로파일을 활성화
public class MemberServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll(); // 테스트 전에 기존 데이터 삭제
    }

    @Test
    @WithMockUser // 인증된 사용자로 테스트 (옵션)
    public void testAddMember() throws Exception {
        // given: 테스트 데이터 준비
        String username = "testuser";
        String password = "password123";
        String displayName = "Test User";
        String role = "ADMIN";  // 권한은 enum 값

        // when: POST 요청 보내기
        mockMvc.perform(post("/addMember")
                        .param("username", username)
                        .param("displayName", displayName)
                        .param("password", password)
                        .param("authority", role))
                .andExpect(status().is3xxRedirection()) // 리다이렉트가 정상적으로 발생하는지 확인
                .andExpect(redirectedUrl("/list"));  // 리다이렉트 URL이 /list인지 확인

        // then: DB에 데이터가 저장되었는지 확인
        Member savedMember = memberRepository.findByUsername(username).orElseThrow();
        assert savedMember.getUsername().equals(username);
        assert savedMember.getDisplayName().equals(displayName);
        assert savedMember.getRole() == Role.ADMIN;
    }
}
