package com.applications.asm.places.view;

import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;

public interface CoordinatesView {
    void callbackCoordinates(Resource<CoordinatesVM> resource);
    void callbackCoordinatesSaved(Resource<Boolean> resource);
}
