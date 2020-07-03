package com.location.forddemo.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    private Integer userId;
    private String locationName;
    private String locationUid;
    private String locationTypeId;
    private String numberOfSelect;
    private String locationTypeName;

    public String getLocationTypeName() {
        return locationTypeName;
    }

    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
    }

    public String getNumberOfSelect() {
        return numberOfSelect;
    }

    public void setNumberOfSelect(String numberOfSelect) {
        this.numberOfSelect = numberOfSelect;
    }

    public LocationRecord(Integer userId,
                          String locationName, String locationUid, String locationTypeId) {
        this.userId = userId;
        this.locationUid = locationUid;
        this.locationName = locationName;
        this.locationTypeId=locationTypeId;
    }

    public LocationRecord(Integer userId,
                           String locationUid, String locationTypeId) {
        this.userId = userId;
        this.locationUid = locationUid;
        this.locationName = locationName;
        this.locationTypeId=locationTypeId;
    }

    public LocationRecord(Long recordId,Integer userId,
                          String locationName,String locationUid,String locationTypeId) {
        this.recordId = recordId;
        this.userId = userId;
        this.locationUid = locationUid;
        this.locationName = locationName;
        this.locationTypeId=locationTypeId;
    }

    public LocationRecord() {
    }

    public String getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(String locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocationUid() {
        return locationUid;
    }

    public void setLocationUid(String locationUid) {
        this.locationUid = locationUid;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
