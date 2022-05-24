package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Location;

public interface LocationRepository {
    Boolean saveLocation(Double latitude, Double longitude);
    Location getLocation();
}
