package com.applications.asm.data.model;

import java.util.List;

public class ResponseReviewsModel {
    private List<ReviewModel> reviewModels;
    private Integer total;

    public ResponseReviewsModel() {}

    public ResponseReviewsModel(List<ReviewModel> reviewModels, Integer total) {
        this.reviewModels = reviewModels;
        this.total = total;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
