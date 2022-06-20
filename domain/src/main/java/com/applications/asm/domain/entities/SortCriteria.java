package com.applications.asm.domain.entities;

public enum SortCriteria {
    BEST_MATCH("best_match"),
    RATING("rating"),
    REVIEW_COUNT("review_count"),
    DISTANCE("distance");

    private final String key;

    SortCriteria(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
