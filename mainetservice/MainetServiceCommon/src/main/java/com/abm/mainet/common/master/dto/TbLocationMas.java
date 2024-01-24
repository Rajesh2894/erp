package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbLocationMas implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long locId;
    private String locNameEng;
    private String locNameReg;
    private String locName;
    private String locActive;
    private Long locDwzId;
    private Long locParentid;
    private String locSource;
    private String locAutStatus;
    private Long locAutBy;
    private Date locAutDate;
    private Integer langId;
    private Date lmoddate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;
    private String centraleno;
    private String parentLocationName;
    private Long GISNo;

    private String latitude;
    private String longitude;

    private String locArea;
    private String locAreaReg;
    private String locAddress;
    private String locAddressReg;
    private Long pincode;
    private Character deptLoc;
    private Long orgId;
    private Long userId;
    private String landmark;
    private String hiddeValue;
    /* private List<LocAdminWZMappingDto> locAdminWZMappingDto=new ArrayList<>(); */
    private List<LocElectrolWZMappingDto> locElectrolWZMappingDto = new ArrayList<>();
    private List<LocRevenueWZMappingDto> locRevenueWZMappingDto = new ArrayList<>();
    private List<LocOperationWZMappingDto> locOperationWZMappingDto = new ArrayList<>();
    private List<LocationYearDetDto> yearDtos = new ArrayList<>();

    private Long locCategory;
    private String locCategoryDesc;
    private String locCode;

    public Long getLocId() {
        return locId;
    }

    public void setLocId(final Long locId) {
        this.locId = locId;
    }

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

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocActive() {
        return locActive;
    }

    public void setLocActive(final String locActive) {
        this.locActive = locActive;
    }

    public Long getLocDwzId() {
        return locDwzId;
    }

    public void setLocDwzId(final Long locDwzId) {
        this.locDwzId = locDwzId;
    }

    public Long getLocParentid() {
        return locParentid;
    }

    public void setLocParentid(final Long locParentid) {
        this.locParentid = locParentid;
    }

    public String getLocSource() {
        return locSource;
    }

    public void setLocSource(final String locSource) {
        this.locSource = locSource;
    }

    public String getLocAutStatus() {
        return locAutStatus;
    }

    public void setLocAutStatus(final String locAutStatus) {
        this.locAutStatus = locAutStatus;
    }

    public Long getLocAutBy() {
        return locAutBy;
    }

    public void setLocAutBy(final Long locAutBy) {
        this.locAutBy = locAutBy;
    }

    public Date getLocAutDate() {
        return locAutDate;
    }

    public void setLocAutDate(final Date locAutDate) {
        this.locAutDate = locAutDate;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(final Integer langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getCentraleno() {
        return centraleno;
    }

    public void setCentraleno(final String centraleno) {
        this.centraleno = centraleno;
    }

    public String getParentLocationName() {
        return parentLocationName;
    }

    public void setParentLocationName(
            final String parentLocationName) {
        this.parentLocationName = parentLocationName;
    }

    public Long getGISNo() {
        return GISNo;
    }

    public void setGISNo(final Long gISNo) {
        GISNo = gISNo;
    }

    public String getLocArea() {
        return locArea;
    }

    public void setLocArea(final String locArea) {
        this.locArea = locArea;
    }

    public String getLocAreaReg() {
        return locAreaReg;
    }

    public void setLocAreaReg(final String locAreaReg) {
        this.locAreaReg = locAreaReg;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(final String locAddress) {
        this.locAddress = locAddress;
    }

    public String getLocAddressReg() {
        return locAddressReg;
    }

    public void setLocAddressReg(final String locAddressReg) {
        this.locAddressReg = locAddressReg;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(final Long pincode) {
        this.pincode = pincode;
    }

    public Character getDeptLoc() {
        return deptLoc;
    }

    public void setDeptLoc(final Character deptLoc) {
        this.deptLoc = deptLoc;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getHiddeValue() {
        return hiddeValue;
    }

    public void setHiddeValue(final String hiddeValue) {
        this.hiddeValue = hiddeValue;
    }

    /*
     * public List<LocAdminWZMappingDto> getLocAdminWZMappingDto() { return locAdminWZMappingDto; } public void
     * setLocAdminWZMappingDto( List<LocAdminWZMappingDto> locAdminWZMappingDto) { this.locAdminWZMappingDto =
     * locAdminWZMappingDto; }
     */
    public List<LocElectrolWZMappingDto> getLocElectrolWZMappingDto() {
        return locElectrolWZMappingDto;
    }

    public void setLocElectrolWZMappingDto(
            final List<LocElectrolWZMappingDto> locElectrolWZMappingDto) {
        this.locElectrolWZMappingDto = locElectrolWZMappingDto;
    }

    public List<LocRevenueWZMappingDto> getLocRevenueWZMappingDto() {
        return locRevenueWZMappingDto;
    }

    public void setLocRevenueWZMappingDto(
            final List<LocRevenueWZMappingDto> locRevenueWZMappingDto) {
        this.locRevenueWZMappingDto = locRevenueWZMappingDto;
    }

    public List<LocOperationWZMappingDto> getLocOperationWZMappingDto() {
        return locOperationWZMappingDto;
    }

    public void setLocOperationWZMappingDto(
            final List<LocOperationWZMappingDto> locOperationWZMappingDto) {
        this.locOperationWZMappingDto = locOperationWZMappingDto;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(final String landmark) {
        this.landmark = landmark;
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

    public Long getLocCategory() {
        return locCategory;
    }

    public void setLocCategory(Long locCategory) {
        this.locCategory = locCategory;
    }

    public String getLocCategoryDesc() {
        return locCategoryDesc;
    }

    public void setLocCategoryDesc(String locCategoryDesc) {
        this.locCategoryDesc = locCategoryDesc;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

	public List<LocationYearDetDto> getYearDtos() {
		return yearDtos;
	}

	public void setYearDtos(List<LocationYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
	}

	public String getFullAddress() {

        final String fullAddress = replaceNull(getLocArea()) + MainetConstants.WHITE_SPACE + replaceNull(getLocNameEng())
                + MainetConstants.WHITE_SPACE + replaceNull(getLocAddress());
        return fullAddress;

    }

    public String getLatLong() {
        final String latLong = getLatitude() + MainetConstants.operator.COMMA + getLongitude();
        return latLong;
    }

    private String replaceNull(String name) {
        if (name == null) {

            name = MainetConstants.BLANK;
        }
        return name;
    }

}
