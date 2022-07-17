package com.applications.asm.data.framework.network.api_rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutocompleteSuggestions {

    @SerializedName("terms")
    public List<Term> terms;

    @SerializedName("businesses")
    public List<Business> businesses;

    @SerializedName("categories")
    public List<Category> categories;
}