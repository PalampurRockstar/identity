package com.model.category;

import lombok.Getter;

@Getter
public enum ClaimType {
    ACCESS("Refresh"),
    REFRESH("Access");

    private final String displayName;

    ClaimType(String displayName) {
        this.displayName = displayName;
    }

}