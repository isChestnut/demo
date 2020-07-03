package com.location.forddemo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LocationType {
    @Id
    private Long locationTypeId;
    private String locationTypeName;

    public LocationType(Long locationTypeId, String locationTypeName) {
        this.locationTypeId = locationTypeId;
        this.locationTypeName = locationTypeName;
    }

    public Long getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Long locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public String getLocationTypeName() {
        return locationTypeName;
    }

    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
    }

    public LocationType() {
    }
}
