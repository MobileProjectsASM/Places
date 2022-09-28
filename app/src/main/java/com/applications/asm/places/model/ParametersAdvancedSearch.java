package com.applications.asm.places.model;

import com.applications.asm.domain.entities.Criterion;

import java.util.List;

public class ParametersAdvancedSearch {
    private final String place;
    private final CoordinatesVM coordinatesVM;
    private final Integer radius;
    private final List<CategoryVM> categories;
    private final CriterionVM sortCriterion;
    private final List<CriterionVM> pricesCriterion;
    private final Boolean isOpenNow;
    private final Integer page;

    public ParametersAdvancedSearch(String place, CoordinatesVM coordinatesVM, Integer radius, List<CategoryVM> categories, CriterionVM sortCriterion, List<CriterionVM> pricesCriterion, Boolean isOpenNow, Integer page) {
        this.place = place;
        this.coordinatesVM = coordinatesVM;
        this.radius = radius;
        this.categories = categories;
        this.sortCriterion = sortCriterion;
        this.pricesCriterion = pricesCriterion;
        this.isOpenNow = isOpenNow;
        this.page = page;
    }

    public String getPlace() {
        return place;
    }

    public CoordinatesVM getCoordinatesVM() {
        return coordinatesVM;
    }

    public Integer getRadius() {
        return radius;
    }

    public List<CategoryVM> getCategories() {
        return categories;
    }

    public CriterionVM getSortCriterion() {
        return sortCriterion;
    }

    public List<CriterionVM> getPricesCriterion() {
        return pricesCriterion;
    }

    public Boolean getOpenNow() {
        return isOpenNow;
    }

    public Integer getPage() {
        return page;
    }
}
