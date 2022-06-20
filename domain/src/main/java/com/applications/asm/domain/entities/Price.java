package com.applications.asm.domain.entities;

import com.sun.org.apache.bcel.internal.classfile.Unknown;

public enum Price {
    CHEAP("1"),
    REGULAR("2"),
    EXPENSIVE("3"),
    VERY_EXPENSIVE("4"),
    UNKNOWN("");

    private final String key;

    Price(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
