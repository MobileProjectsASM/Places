package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.places.model.LatitudeStateMV;
import com.applications.asm.places.model.LongitudeStateMV;
import com.applications.asm.places.model.RadiusStateMV;

public class StatesMapperImpl implements StatesMapper {
    @Override
    public LatitudeStateMV getLatitudeStateMV(LatitudeState latitudeState) {
        switch (latitudeState) {
            case EMPTY: return LatitudeStateMV.EMPTY;
            case INVALID: return LatitudeStateMV.INVALID;
            case OUT_OF_RANGE: return LatitudeStateMV.OUT_OF_RANGE;
            default: return LatitudeStateMV.OK;
        }
    }

    @Override
    public LongitudeStateMV getLongitudeStateMV(LongitudeState longitudeState) {
        switch (longitudeState) {
            case EMPTY: return LongitudeStateMV.EMPTY;
            case INVALID: return LongitudeStateMV.INVALID;
            case OUT_OF_RANGE: return LongitudeStateMV.OUT_OF_RANGE;
            default: return LongitudeStateMV.OK;
        }
    }

    @Override
    public RadiusStateMV getRadiusStateMV(RadiusState radiusState) {
        switch (radiusState) {
            case EMPTY: return RadiusStateMV.EMPTY;
            case INVALID: return RadiusStateMV.INVALID;
            case OUT_OF_RANGE: return RadiusStateMV.OUT_OF_RANGE;
            default: return RadiusStateMV.OK;
        }
    }
}
