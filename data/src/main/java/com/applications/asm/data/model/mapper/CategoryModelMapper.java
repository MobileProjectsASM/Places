package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.domain.entities.Category;

import java.util.List;

public interface CategoryModelMapper {
    Category getCategory(CategoryModel categoryModel);
    CategoryModel getCategoryModel(Category category);
    List<Category> getCategories(List<CategoryModel> categoriesModel);
    List<CategoryModel> getCategoriesModel(List<Category> categories);
}
