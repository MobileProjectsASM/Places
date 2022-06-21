package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.LatitudeState;
import com.applications.asm.domain.entities.LongitudeState;
import com.applications.asm.domain.entities.RadiusState;
import com.applications.asm.places.model.LatitudeStateVM;
import com.applications.asm.places.model.LongitudeStateVM;
import com.applications.asm.places.model.RadiusStateMV;

public class StatesMapperImpl implements StatesMapper {
    @Override
    public LatitudeStateVM getLatitudeStateMV(LatitudeState latitudeState) {
        switch (latitudeState) {
            case EMPTY: return LatitudeStateVM.EMPTY;
            case INVALID: return LatitudeStateVM.INVALID;
            case OUT_OF_RANGE: return LatitudeStateVM.OUT_OF_RANGE;
            default: return LatitudeStateVM.OK;
        }
    }

    @Override
    public LongitudeStateVM getLongitudeStateMV(LongitudeState longitudeState) {
        switch (longitudeState) {
            case EMPTY: return LongitudeStateVM.EMPTY;
            case INVALID: return LongitudeStateVM.INVALID;
            case OUT_OF_RANGE: return LongitudeStateVM.OUT_OF_RANGE;
            default: return LongitudeStateVM.OK;
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
