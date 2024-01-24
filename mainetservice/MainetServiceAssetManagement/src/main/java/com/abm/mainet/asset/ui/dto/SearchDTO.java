/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;

/**
 * DTO class for Asset Search
 * @author sarojkumar.yadav
 *
 */
public class SearchDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6554589734426341108L;

    private String astModelId;
    private String astClass;
    private String details;
    private Long costCenter;
    private Long assetStatusId;// prefix AST
    private String location;
    private Long locationId;
    private String deptName;
    private Long deptId;

    private Long assetClass1;
    private Long assetClass2;
    private Long assetNo;

    private String astSerialNo;
    private String subId;
    private Long astInventId;
    private Long parentId;

    private Long orgId;

    private String flagType;

    private boolean searchCheck;

    private int langId;
    private Long acquisitionMethodId;
    private Long employeeId;

    private String astAppNo;
    // T#85539
    private String roadName;
    private Long pincode;
    private String moduleDeptCode;

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the astClass
     */
    public String getAstClass() {
        return astClass;
    }

    /**
     * @param astClass the astClass to set
     */
    public void setAstClass(String astClass) {
        this.astClass = astClass;
    }

    /**
     * @return the astSerialNo
     */
    public String getAstSerialNo() {
        return astSerialNo;
    }

    /**
     * @param astSerialNo the astSerialNo to set
     */
    public void setAstSerialNo(String astSerialNo) {
        this.astSerialNo = astSerialNo;
    }

    /**
     * @return the astModelId
     */
    public String getAstModelId() {
        return astModelId;
    }

    /**
     * @param astModelId the astModelId to set
     */
    public void setAstModelId(String astModelId) {
        this.astModelId = astModelId;
    }

    /**
     * @return the subId
     */
    public String getSubId() {
        return subId;
    }

    /**
     * @param subId the subId to set
     */
    public void setSubId(String subId) {
        this.subId = subId;
    }

    /**
     * @return the costCenter
     */
    public Long getCostCenter() {
        return costCenter;
    }

    public Long getAssetStatusId() {
        return assetStatusId;
    }

    public void setAssetStatusId(Long assetStatusId) {
        this.assetStatusId = assetStatusId;
    }

    /**
     * @return the astInventId
     */
    public Long getAstInventId() {
        return astInventId;
    }

    /**
     * @param astInventId the astInventId to set
     */
    public void setAstInventId(Long astInventId) {
        this.astInventId = astInventId;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getAssetClass1() {
        return assetClass1;
    }

    public void setAssetClass1(Long assetClass1) {
        this.assetClass1 = assetClass1;
    }

    public Long getAssetClass2() {
        return assetClass2;
    }

    public void setAssetClass2(Long assetClass2) {
        this.assetClass2 = assetClass2;
    }

    public void setCostCenter(Long costCenter) {
        this.costCenter = costCenter;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(Long assetNo) {
        this.assetNo = assetNo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType;
    }

    public boolean isSearchCheck() {
        return searchCheck;
    }

    public void setSearchCheck(boolean searchCheck) {
        this.searchCheck = searchCheck;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Long getAcquisitionMethodId() {
        return acquisitionMethodId;
    }

    public void setAcquisitionMethodId(Long acquisitionMethodId) {
        this.acquisitionMethodId = acquisitionMethodId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getAstAppNo() {
        return astAppNo;
    }

    public void setAstAppNo(String astAppNo) {
        this.astAppNo = astAppNo;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    public String getModuleDeptCode() {
        return moduleDeptCode;
    }

    public void setModuleDeptCode(String moduleDeptCode) {
        this.moduleDeptCode = moduleDeptCode;
    }

    @Override
    public String toString() {
        return "SearchDTO [astModelId=" + astModelId + ", astClass=" + astClass + ", details=" + details + ", costCenter="
                + costCenter + ", assetStatusId=" + assetStatusId + ", location=" + location + ", locationId=" + locationId
                + ", deptName=" + deptName + ", deptId=" + deptId + ", assetClass1=" + assetClass1 + ", assetClass2="
                + assetClass2 + ", assetNo=" + assetNo + ", astSerialNo=" + astSerialNo + ", subId=" + subId + ", astInventId="
                + astInventId + ", parentId=" + parentId + ", orgId=" + orgId + ", flagType=" + flagType + ", searchCheck="
                + searchCheck + ", langId=" + langId + ", acquisitionMethodId=" + acquisitionMethodId + ", employeeId="
                + employeeId + ", astAppNo=" + astAppNo + ", roadName=" + roadName + ", pincode=" + pincode + "]";
    }

}
