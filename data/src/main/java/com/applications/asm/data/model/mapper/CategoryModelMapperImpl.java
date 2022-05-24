package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.domain.entities.Category;

public class CategoryModelMapperImpl implements CategoryModelMapper {
    @Override
    public Category getCategoryFromCategoryModel(CategoryModel categoryModel) {
        return new Category(categoryModel.getId(), categoryModel.getName());
    }
}
