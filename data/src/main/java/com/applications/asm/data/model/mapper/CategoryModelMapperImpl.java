package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.domain.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryModelMapperImpl implements CategoryModelMapper {
    @Override
    public Category getCategory(CategoryModel categoryModel) {
        return new Category(categoryModel.getId(), categoryModel.getName());
    }

    @Override
    public CategoryModel getCategoryModel(Category category) {
        return new CategoryModel(category.getId(), category.getName());
    }

    @Override
    public List<Category> getCategories(List<CategoryModel> categoriesModel) {
        List<Category> categories = new ArrayList<>();
        for(CategoryModel categoryModel: categoriesModel)
            categories.add(getCategory(categoryModel));
        return categories;
    }

    @Override
    public List<CategoryModel> getCategoriesModel(List<Category> categories) {
        List<CategoryModel> categoriesModel = new ArrayList<>();
        for(Category category: categories)
            categoriesModel.add(getCategoryModel(category));
        return categoriesModel;
    }
}
