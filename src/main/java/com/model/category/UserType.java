package com.model.category;

import lombok.Getter;

@Getter
public enum UserType {
    SELLER("Seller"),
    BUYER("Buyer"),
    SYSTEM("System");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

}