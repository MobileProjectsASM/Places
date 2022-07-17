package com.applications.asm.data.mapper;

import com.applications.asm.domain.entities.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> categoriesDTOToCategories(List<com.applications.asm.data.framework.network.api_rest.dto.Category> categories);
    Category categoryDTOToCategory(com.applications.asm.data.framework.network.api_rest.dto.Category category);
}
