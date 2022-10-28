package com.applications.asm.domain.entities;

import java.util.List;

public class FoundPlaces {
    private List<Place> places;
    private Integer totalPages;

    public FoundPlaces(List<Place> places, Integer totalPages) {
        this.places = places;
        this.totalPages = totalPages;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
