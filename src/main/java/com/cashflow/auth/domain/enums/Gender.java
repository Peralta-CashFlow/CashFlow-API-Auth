package com.cashflow.auth.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {

    M("Male"),
    F("Female");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

}
