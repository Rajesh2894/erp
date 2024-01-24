/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author satish.rathore
 *
 */
public class AssetDetailsReportDto {

    private Long assetGroup;
    private Long assetType;
    private Long assetClass1;
    private Long assetClass2;
    private Long assetId;
    private String astName;
    private String astTypeDesc;
    private String astGroupDes;
    private String astClassification;
    private String astClass;
    private Date dateOfacquisition;
    private Long lifeYear;
    private Long savValue;
    private Long reportTypeId;
    private String serialNo;
    private String assetCode;
    List<AssetDetailsReportDto> list = new ArrayList<>();
    private BigDecimal currentvalue;
    private BigDecimal costOfacqui;

    /**
     * @return the list
     */
    public List<AssetDetailsReportDto> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<AssetDetailsReportDto> list) {
        this.list = list;
    }

    /**
     * @return the assetGroup
     */
    public Long getAssetGroup() {
        return assetGroup;
    }

    /**
     * @param assetGroup the assetGroup to set
     */
    public void setAssetGroup(Long assetGroup) {
        this.assetGroup = assetGroup;
    }

    /**
     * @return the assetType
     */
    public Long getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    /**
     * @return the assetClass1
     */
    public Long getAssetClass1() {
        return assetClass1;
    }

    /**
     * @param assetClass1 the assetClass1 to set
     */
    public void setAssetClass1(Long assetClass1) {
        this.assetClass1 = assetClass1;
    }

    /**
     * @return the assetClass2
     */
    public Long getAssetClass2() {
        return assetClass2;
    }

    /**
     * @param assetClass2 the assetClass2 to set
     */
    public void setAssetClass2(Long assetClass2) {
        this.assetClass2 = assetClass2;
    }

    /**
     * @return the assetId
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the astName
     */
    public String getAstName() {
        return astName;
    }

    /**
     * @param astName the astName to set
     */
    public void setAstName(String astName) {
        this.astName = astName;
    }

    /**
     * @return the astTypeDesc
     */
    public String getAstTypeDesc() {
        return astTypeDesc;
    }

    /**
     * @param astTypeDesc the astTypeDesc to set
     */
    public void setAstTypeDesc(String astTypeDesc) {
        this.astTypeDesc = astTypeDesc;
    }

    /**
     * @return the astGroupDes
     */
    public String getAstGroupDes() {
        return astGroupDes;
    }

    /**
     * @param astGroupDes the astGroupDes to set
     */
    public void setAstGroupDes(String astGroupDes) {
        this.astGroupDes = astGroupDes;
    }

    /**
     * @return the astClassification
     */
    public String getAstClassification() {
        return astClassification;
    }

    /**
     * @param astClassification the astClassification to set
     */
    public void setAstClassification(String astClassification) {
        this.astClassification = astClassification;
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
     * @return the dateOfacquisition
     */
    public Date getDateOfacquisition() {
        return dateOfacquisition;
    }

    /**
     * @param dateOfacquisition the dateOfacquisition to set
     */
    public void setDateOfacquisition(Date dateOfacquisition) {
        this.dateOfacquisition = dateOfacquisition;
    }

    /**
     * @return the lifeYear
     */
    public Long getLifeYear() {
        return lifeYear;
    }

    /**
     * @param lifeYear the lifeYear to set
     */
    public void setLifeYear(Long lifeYear) {
        this.lifeYear = lifeYear;
    }

    /**
     * @return the savValue
     */
    public Long getSavValue() {
        return savValue;
    }

    /**
     * @param savValue the savValue to set
     */
    public void setSavValue(Long savValue) {
        this.savValue = savValue;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return the assetCode
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * @param assetCode the assetCode to set
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public BigDecimal getCurrentvalue() {
        return currentvalue;
    }

    public void setCurrentvalue(BigDecimal currentvalue) {
        this.currentvalue = currentvalue;
    }

    public BigDecimal getCostOfacqui() {
        return costOfacqui;
    }

    public void setCostOfacqui(BigDecimal costOfacqui) {
        this.costOfacqui = costOfacqui;
    }

    @Override
    public String toString() {
        return "AssetDetailsReportDto [assetGroup=" + assetGroup + ", assetType=" + assetType + ", assetClass1=" + assetClass1
                + ", assetClass2=" + assetClass2 + ", assetId=" + assetId + ", astName=" + astName + ", astTypeDesc="
                + astTypeDesc + ", astGroupDes=" + astGroupDes + ", astClassification=" + astClassification + ", astClass="
                + astClass + ", dateOfacquisition=" + dateOfacquisition + ", lifeYear=" + lifeYear + ", savValue=" + savValue
                + ", reportTypeId=" + reportTypeId + ", serialNo=" + serialNo + ", assetCode=" + assetCode + ", list=" + list
                + ", currentvalue=" + currentvalue + ", costOfacqui=" + costOfacqui + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

}
