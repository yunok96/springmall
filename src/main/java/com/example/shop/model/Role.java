package com.example.shop.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("관리자"),
    BUSINESS_USER("비즈니스 사용자"),
    INDIVIDUAL_USER("개인 사용자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
