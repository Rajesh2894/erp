package com.abm.mainet.common.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
    private String landmark;

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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(final String landmark) {
        this.landmark = landmark;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((locId == null) ? 0 : locId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocationDTO other = (LocationDTO) obj;
        if (locId == null) {
            if (other.locId != null)
                return false;
        } else if (!locId.equals(other.locId))
            return false;
        return true;
    }

}
