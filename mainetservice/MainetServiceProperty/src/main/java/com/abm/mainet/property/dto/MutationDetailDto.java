package com.abm.mainet.property.dto;

import java.io.Serializable;

/**
 * @author anwarul.hassan
 * @since 08-Feb-2021
 */
public class MutationDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String valid;
    private String asOnDateTime;
    private String propertyID;
    private String zoneName;
    private String wardName;
    private String mohallaName;
    private String ownerName;
    private String fatherName;
    private String address;
    private String mobile;
    private String areaOfLand;
    private String fullHouseTaxPaid;
    private String ulbCode;
    private String arv;
    private String zoneID;

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getAsOnDateTime() {
        return asOnDateTime;
    }

    public void setAsOnDateTime(String asOnDateTime) {
        this.asOnDateTime = asOnDateTime;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getMohallaName() {
        return mohallaName;
    }

    public void setMohallaName(String mohallaName) {
        this.mohallaName = mohallaName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAreaOfLand() {
        return areaOfLand;
    }

    public void setAreaOfLand(String areaOfLand) {
        this.areaOfLand = areaOfLand;
    }

    public String getFullHouseTaxPaid() {
		return fullHouseTaxPaid;
	}

	public void setFullHouseTaxPaid(String fullHouseTaxPaid) {
		this.fullHouseTaxPaid = fullHouseTaxPaid;
	}

	public String getUlbCode() {
		return ulbCode;
	}

	public void setUlbCode(String ulbCode) {
		this.ulbCode = ulbCode;
	}

	public String getArv() {
        return arv;
    }

    public void setArv(String arv) {
        this.arv = arv;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

}
