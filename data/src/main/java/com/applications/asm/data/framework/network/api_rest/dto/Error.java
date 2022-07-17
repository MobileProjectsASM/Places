package com.applications.asm.data.framework.network.api_rest.dto;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("code")
    public String code;

    @SerializedName("description")
    public String description;

    @SerializedName("text")
    public String text;

    @SerializedName("instance")
    public Object instance;
}
