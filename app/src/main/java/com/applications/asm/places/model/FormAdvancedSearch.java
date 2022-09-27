package com.applications.asm.places.model;

import com.applications.asm.data.framework.network.api_rest.dto.Category;
import com.applications.asm.domain.entities.Criterion;

import java.util.List;

public class FormAdvancedSearch {
    private String place;
    private Integer radius;
    private List<Category> categories;
    private Criterion sortCriterion;
    private List<Criterion> pricesCriterion;
    private Integer page;


}
