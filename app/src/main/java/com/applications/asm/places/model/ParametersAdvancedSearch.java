package com.applications.asm.places.model;

import java.util.List;

public class ParametersAdvancedSearch {
    private String place;
    private CoordinatesVM coordinatesVM;
    private Integer radius;
    private List<CategoryVM> categories;
    private CriterionVM sortCriterion;
    private List<CriterionVM> pricesCriterion;
    private Boolean isOpenNow;
    private Integer page;

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

    public void setPlace(String place) {
        this.place = place;
    }

    public CoordinatesVM getCoordinatesVM() {
        return coordinatesVM;
    }

    public void setCoordinatesVM(CoordinatesVM coordinatesVM) {
        this.coordinatesVM = coordinatesVM;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public List<CategoryVM> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVM> categories) {
        this.categories = categories;
    }

    public CriterionVM getSortCriterion() {
        return sortCriterion;
    }

    public void setSortCriterion(CriterionVM sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    public List<CriterionVM> getPricesCriterion() {
        return pricesCriterion;
    }

    public void setPricesCriterion(List<CriterionVM> pricesCriterion) {
        this.pricesCriterion = pricesCriterion;
    }

    public Boolean getOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(Boolean openNow) {
        isOpenNow = openNow;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
