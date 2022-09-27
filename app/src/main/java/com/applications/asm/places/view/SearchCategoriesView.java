package com.applications.asm.places.view;

import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;

import java.util.List;

public interface SearchCategoriesView {
    void callbackCoordinates(Resource<CoordinatesVM> resource);
    void callbackCategories(Resource<List<CategoryVM>> resource);
}
