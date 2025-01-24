package com.example.shop.service;

import com.example.shop.model.CustomUser;
import com.example.shop.model.entity.Member;
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
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name())); // Role을 기반으로 권한을 설정

        CustomUser user = new CustomUser(member.getUsername(), member.getPassword(), authorities);
        user.setDisplayName(member.getDisplayName());
        user.setUserId(member.getId());

        return user;
    }
}

