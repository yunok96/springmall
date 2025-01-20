package com.example.shop.service;

import com.example.shop.CustomUser;
import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUsername(username);
        if( result.isEmpty() ) {
            throw new UsernameNotFoundException("User not found");
        }

        Member member = result.get();
        List<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority("일반 사용자"));

//        User user = new User(member.getUsername(), member.getPassword(), authority);
        CustomUser user = new CustomUser(member.getUsername(), member.getPassword(), authority);
        user.setDisplayName(member.getDisplayName());
        user.setUserId(member.getId());

        return user;
    }
}

