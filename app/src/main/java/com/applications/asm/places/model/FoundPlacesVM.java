package com.applications.asm.places.model;

import java.util.List;

public class FoundPlacesVM {
    private List<PlaceVM> places;
    private int pages;

    public FoundPlacesVM(List<PlaceVM> places, int pages) {
        this.places = places;
        this.pages = pages;
    }

    public List<PlaceVM> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceVM> places) {
        this.places = places;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
