package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.places.model.LatitudeStateMV;
import com.applications.asm.places.model.LongitudeStateMV;
import com.applications.asm.places.model.RadiusStateMV;

public interface StatesMapper {
    LatitudeStateMV getLatitudeStateMV(LatitudeState latitudeState);
    LongitudeStateMV getLongitudeStateMV(LongitudeState longitudeState);
    RadiusStateMV getRadiusStateMV(RadiusState radiusState);
}
