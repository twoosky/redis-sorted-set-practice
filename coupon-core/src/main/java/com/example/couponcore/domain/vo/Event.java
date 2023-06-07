package com.example.couponcore.domain.vo;

import lombok.Getter;

@Getter
public enum Event {
    CHICKEN("치킨");

    private String name;

    private Event(String name) {
        this.name = name;
    }
}
