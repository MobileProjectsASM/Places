package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.places.model.LatitudeStateVM;
import com.applications.asm.places.model.LongitudeStateVM;
import com.applications.asm.places.model.RadiusStateMV;

public interface StatesMapper {
    LatitudeStateVM getLatitudeStateMV(LatitudeState latitudeState);
    LongitudeStateVM getLongitudeStateMV(LongitudeState longitudeState);
    RadiusStateMV getRadiusStateMV(RadiusState radiusState);
}
