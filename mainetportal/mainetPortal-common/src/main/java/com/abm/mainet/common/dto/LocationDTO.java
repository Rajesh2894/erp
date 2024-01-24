package com.abm.mainet.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String locNameEng;
    private String locNameReg;
    private String locName;
    private Long locId;
    private Long pincode;
    private String latitude;
    private String longitude;

    public String getLocNameEng() {
        return locNameEng;
    }

    public void setLocNameEng(final String locNameEng) {
        this.locNameEng = locNameEng;
    }

    public String getLocNameReg() {
        return locNameReg;
    }

    public void setLocNameReg(final String locNameReg) {
        this.locNameReg = locNameReg;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(final String locName) {
        this.locName = locName;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(final Long pincode) {
        this.pincode = pincode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getLatLong() {
        final String latLong=  getLatitude() +MainetConstants.operator.COMA + getLongitude();
                return latLong;         
    }

}
