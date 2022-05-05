package com.applications.asm.data.model;

import com.applications.asm.domain.entities.SuggestedPlace;

import java.util.List;

public class ResponseSuggestedPlacesModel {
    private List<SuggestedPlaceModel> suggestPlacesModel;
    private Integer total;

    public ResponseSuggestedPlacesModel() {}

    public ResponseSuggestedPlacesModel(List<SuggestedPlaceModel> suggestPlacesModel, Integer total) {
        this.suggestPlacesModel = suggestPlacesModel;
        this.total = total;
    }

    public List<SuggestedPlaceModel> getSuggestPlacesModel() {
        return suggestPlacesModel;
    }

    public void setSuggestPlacesModel(List<SuggestedPlaceModel> suggestPlacesModel) {
        this.suggestPlacesModel = suggestPlacesModel;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
