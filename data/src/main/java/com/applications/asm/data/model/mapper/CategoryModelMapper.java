package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.domain.entities.Category;

public interface CategoryModelMapper {
    Category getCategoryFromCategoryModel(CategoryModel categoryModel);
}
