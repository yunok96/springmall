package com.example.shop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class CustomUser extends User {
    private int userId; // 사용자 일련번호
    private String displayName; // 사용자 표시 이름

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authority) {
        super(username, password, authority);
    }

}
