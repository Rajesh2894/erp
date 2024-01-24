package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class EstatePropMaster implements Serializable {

    private static final long serialVersionUID = -6827482696074647043L;
    private Long propId;
    private Long estateId;
    private String estatecode;
    private String assesmentPropId;
    private String code;
    private String oldPropNo;
    private String name;
    private Integer unitNo;
    private Integer occupancy;
    private Integer usage;
    private Integer floor;
    private Integer roadType;
    private String gisId;
    private Character courtCase;
    private Character stopBilling;
    private Character status;
    private Double totalArea;
    private Character flag;
    private Integer securityDeposite;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String imagesPath;
    private String docsPath;
    private String hiddeValue;
    private int langId;
    private String propLatitude;
    private String propLongitude;
    private Long propCapacity;
    private Long propNoDaysAllow;
    private Long propMaintainBy;

    private List<EstatePropDtlMaster> details = new ArrayList<>();

    private List<EstatePropertyAmenityDTO> aminityDTOlist = new ArrayList<>();
    private List<EstatePropertyAmenityDTO> facilityDTOlist = new ArrayList<>();
    private List<EstatePropertyEventDTO> eventDTOList = new ArrayList<>();
    private List<EstatePropertyShiftDTO> propertyShiftDTOList = new ArrayList<>();

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getEstatecode() {
        return estatecode;
    }

    public void setEstatecode(String estatecode) {
        this.estatecode = estatecode;
    }

    public String getAssesmentPropId() {
        return assesmentPropId;
    }

    public void setAssesmentPropId(String assesmentPropId) {
        this.assesmentPropId = assesmentPropId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOldPropNo() {
        return oldPropNo;
    }

    public void setOldPropNo(String oldPropNo) {
        this.oldPropNo = oldPropNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(Integer unitNo) {
        this.unitNo = unitNo;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRoadType() {
        return roadType;
    }

    public void setRoadType(Integer roadType) {
        this.roadType = roadType;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }

    public Character getCourtCase() {
        return courtCase;
    }

    public void setCourtCase(Character courtCase) {
        this.courtCase = courtCase;
    }

    public Character getStopBilling() {
        return stopBilling;
    }

    public void setStopBilling(Character stopBilling) {
        this.stopBilling = stopBilling;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

   
    public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}

	public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
    }

    public Integer getSecurityDeposite() {
        return securityDeposite;
    }

    public void setSecurityDeposite(Integer securityDeposite) {
        this.securityDeposite = securityDeposite;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getDocsPath() {
        return docsPath;
    }

    public void setDocsPath(String docsPath) {
        this.docsPath = docsPath;
    }

    public String getHiddeValue() {
        return hiddeValue;
    }

    public void setHiddeValue(String hiddeValue) {
        this.hiddeValue = hiddeValue;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getPropLatitude() {
        return propLatitude;
    }

    public void setPropLatitude(String propLatitude) {
        this.propLatitude = propLatitude;
    }

    public String getPropLongitude() {
        return propLongitude;
    }

    public void setPropLongitude(String propLongitude) {
        this.propLongitude = propLongitude;
    }

    public Long getPropCapacity() {
        return propCapacity;
    }

    public void setPropCapacity(Long propCapacity) {
        this.propCapacity = propCapacity;
    }

    public Long getPropNoDaysAllow() {
        return propNoDaysAllow;
    }

    public void setPropNoDaysAllow(Long propNoDaysAllow) {
        this.propNoDaysAllow = propNoDaysAllow;
    }

    public Long getPropMaintainBy() {
        return propMaintainBy;
    }

    public void setPropMaintainBy(Long propMaintainBy) {
        this.propMaintainBy = propMaintainBy;
    }

    public List<EstatePropDtlMaster> getDetails() {
        return details;
    }

    public void setDetails(List<EstatePropDtlMaster> details) {
        this.details = details;
    }

    public List<EstatePropertyAmenityDTO> getAminityDTOlist() {
        return aminityDTOlist;
    }

    public void setAminityDTOlist(List<EstatePropertyAmenityDTO> aminityDTOlist) {
        this.aminityDTOlist = aminityDTOlist;
    }

    public List<EstatePropertyAmenityDTO> getFacilityDTOlist() {
        return facilityDTOlist;
    }

    public void setFacilityDTOlist(List<EstatePropertyAmenityDTO> facilityDTOlist) {
        this.facilityDTOlist = facilityDTOlist;
    }

    public List<EstatePropertyEventDTO> getEventDTOList() {
        return eventDTOList;
    }

    public void setEventDTOList(List<EstatePropertyEventDTO> eventDTOList) {
        this.eventDTOList = eventDTOList;
    }

    public List<EstatePropertyShiftDTO> getPropertyShiftDTOList() {
        return propertyShiftDTOList;
    }

    public void setPropertyShiftDTOList(List<EstatePropertyShiftDTO> propertyShiftDTOList) {
        this.propertyShiftDTOList = propertyShiftDTOList;
    }

}
