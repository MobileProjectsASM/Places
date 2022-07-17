package com.applications.asm.data.mapper;

import com.applications.asm.domain.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public List<Category> categoriesDTOToCategories(List<com.applications.asm.data.framework.network.api_rest.dto.Category> categoriesDTO) {
        List<Category> categories = new ArrayList<>();
        for(com.applications.asm.data.framework.network.api_rest.dto.Category category : categoriesDTO)
            categories.add(categoryDTOToCategory(category));
        return categories;
    }

    @Override
    public Category categoryDTOToCategory(com.applications.asm.data.framework.network.api_rest.dto.Category category) {
        return new Category(category.alias, category.title);
    }
}
