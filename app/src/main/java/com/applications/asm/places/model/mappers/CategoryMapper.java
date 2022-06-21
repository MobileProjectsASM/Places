package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.places.model.CategoryVM;

import java.util.List;

public interface CategoryMapper {
    CategoryVM getCategoryMV(Category category);
    Category getCategory(CategoryVM categoryVM);
    List<CategoryVM> getCategoriesVM(List<Category> categories);
    List<Category> getCategories(List<CategoryVM> categoriesVM);
}
