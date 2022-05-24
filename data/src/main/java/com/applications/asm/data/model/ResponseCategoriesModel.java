package com.applications.asm.data.model;

import java.util.List;

public class ResponseCategoriesModel {
    private List<CategoryModel> categoryModelList;
    private Integer total;

    public ResponseCategoriesModel() {

    }

    public ResponseCategoriesModel(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
