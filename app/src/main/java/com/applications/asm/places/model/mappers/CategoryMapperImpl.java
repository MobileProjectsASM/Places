package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.places.model.CategoryVM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CategoryMapperImpl implements CategoryMapper {

    @Inject
    public CategoryMapperImpl() {

    }

    @Override
    public CategoryVM getCategoryMV(Category category) {
        return new CategoryVM(category.getId(), category.getName());
    }

    @Override
    public Category getCategory(CategoryVM categoryVM) {
        return new Category(categoryVM.getId(), categoryVM.getName());
    }

    @Override
    public List<CategoryVM> getCategoriesVM(List<Category> categories) {
        List<CategoryVM> categoriesVM = new ArrayList<>();
        for(Category category: categories)
            categoriesVM.add(getCategoryMV(category));
        return categoriesVM;
    }

    @Override
    public List<Category> getCategories(List<CategoryVM> categoriesVM) {
        List<Category> categories = new ArrayList<>();
        for(CategoryVM categoryVM: categoriesVM)
            categories.add(getCategory(categoryVM));
        return categories;
    }
}
