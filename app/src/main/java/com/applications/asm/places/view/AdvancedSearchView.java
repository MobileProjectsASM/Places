package com.applications.asm.places.view;

import com.applications.asm.places.model.Resource;

import java.util.Map;

public interface AdvancedSearchView {
    void callbackLoadData(Resource<Map<String, Object>> resource);
}
