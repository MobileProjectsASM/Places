package com.applications.asm.data.model;

import java.util.List;

public class ResponsePlacesModel {
    private List<PlaceModel> places;
    private Integer total;

    public ResponsePlacesModel() {

    }

    public ResponsePlacesModel(List<PlaceModel> places, Integer total) {
        this.places = places;
        this.total = total;
    }

    public List<PlaceModel> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceModel> places) {
        this.places = places;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
