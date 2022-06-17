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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesModelDeserializer implements JsonDeserializer<ResponseCategoriesModel> {
    private final Gson gson;

    public CategoriesModelDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public ResponseCategoriesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Log.i(getClass().getName(), gson.toJson(json));
        JsonObject body = json.getAsJsonObject();
        JsonElement categoriesJson = body.get("categories");

        List<CategoryModel> categoriesModel = categoriesJson != null && !categoriesJson.isJsonNull() ? deserializeCategoriesModel(categoriesJson.getAsJsonArray()) : new ArrayList<>();

        ResponseCategoriesModel responseCategoriesModel = new ResponseCategoriesModel();
        responseCategoriesModel.setCategoryModelList(categoriesModel);
        responseCategoriesModel.setTotal(categoriesModel.size());

        return responseCategoriesModel;
    }

    private List<CategoryModel> deserializeCategoriesModel(JsonArray categories) {
        if(categories.size() > 0) {
            List<CategoryModel> categoriesModel = new ArrayList<>();
            for(JsonElement suggestedPlaceModel: categories)
                categoriesModel.add(deserializeCategoryModel(suggestedPlaceModel.getAsJsonObject()));
            return categoriesModel;
        }
        return new ArrayList<>();
    }

    private CategoryModel deserializeCategoryModel(JsonObject categoryJson) {
        JsonElement aliasJson = categoryJson.get("alias");
        JsonElement titleJson = categoryJson.get("title");

        String alias = aliasJson != null && !aliasJson.isJsonNull() ? aliasJson.getAsString() : "";
        String name = titleJson != null && !titleJson.isJsonNull() ?  titleJson.getAsString() : "";

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(alias);
        categoryModel.setName(name);

        return categoryModel;
    }

}
