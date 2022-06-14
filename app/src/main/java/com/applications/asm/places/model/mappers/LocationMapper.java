package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Location;
import com.applications.asm.places.model.LocationVM;

public interface LocationMapper {
    LocationVM getLocationVM(Location location);
}
