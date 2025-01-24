package com.example.shop.model;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_BUSINESS_USER("비즈니스 사용자"),
    ROLE_INDIVIDUAL_USER("개인 사용자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
