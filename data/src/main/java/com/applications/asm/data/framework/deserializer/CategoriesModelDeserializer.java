package com.applications.asm.data.framework.deserializer;

import android.util.Log;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.data.model.ResponseCategoriesModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesModelDeserializer implements JsonDeserializer<ResponseCategoriesModel> {
    private final Gson gson;

    public CategoriesModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponseCategoriesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String TAG = "CategoriesModelDeserializer";
        Log.i(TAG, gson.toJson(json));
        JsonObject body = json.getAsJsonObject();

        List<CategoryModel> categoriesModel = deserializeCategoriesModel(body.getAsJsonArray("categories"));

        ResponseCategoriesModel responseCategoriesModel = new ResponseCategoriesModel();
        responseCategoriesModel.setCategoryModelList(categoriesModel);
        responseCategoriesModel.setTotal(categoriesModel.size());

        return responseCategoriesModel;
    }

    private List<CategoryModel> deserializeCategoriesModel(JsonArray categories) {
        List<CategoryModel> categoriesModel = new ArrayList<>();

        for(JsonElement suggestedPlaceModel: categories)
            categoriesModel.add(deserializeCategoryModel(suggestedPlaceModel.getAsJsonObject()));

        return categoriesModel;
    }

    private CategoryModel deserializeCategoryModel(JsonObject categoryJson) {
        CategoryModel categoryModel = new CategoryModel();

        categoryModel.setId(categoryJson.get("alias").getAsString());
        categoryModel.setName(categoryJson.get("title").getAsString());

        return categoryModel;
    }

}
