package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
public class ScheduleOfRateDetDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long sordId;
    private Long sordCategory;
    private String sordSubCategory;
    private String sorDIteamNo;
    private String sorDDescription;
    private Long sorIteamUnit;
    private BigDecimal sorBasicRate;
    private BigDecimal sorLabourRate;
    private String sorDActive;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String igIpMac;
    private String igIpMacUpd;
    private BigDecimal leadUpto;
    private BigDecimal liftUpto;
    private Long leadUnit;
    private boolean checkBox;

    private String sordCategoryDesc;
    private String sorIteamUnitDesc;
    private String leadUnitDesc;
    private Long sorId;
    private String schActiveFlag;
    private Long workEstimateId;
    private boolean estimatechildAvailble;
    private String workEFlag;
    private String deletedFlagForSor;
    private String flag;

    public Long getSordId() {
        return sordId;
    }

    public void setSordId(Long sordId) {
        this.sordId = sordId;
    }

    public String getSorDIteamNo() {
        return sorDIteamNo;
    }

    public void setSorDIteamNo(String sorDIteamNo) {
        this.sorDIteamNo = sorDIteamNo;
    }

    public String getSorDDescription() {
        return sorDDescription;
    }

    public void setSorDDescription(String sorDDescription) {
        this.sorDDescription = sorDDescription;
    }

    public Long getSorIteamUnit() {
        return sorIteamUnit;
    }

    public void setSorIteamUnit(Long sorIteamUnit) {
        this.sorIteamUnit = sorIteamUnit;
    }

    public BigDecimal getSorBasicRate() {
        return sorBasicRate;
    }

    public void setSorBasicRate(BigDecimal sorBasicRate) {
        this.sorBasicRate = sorBasicRate;
    }

    public BigDecimal getSorLabourRate() {
        return sorLabourRate;
    }

    public void setSorLabourRate(BigDecimal sorLabourRate) {
        this.sorLabourRate = sorLabourRate;
    }

    public String getSorDActive() {
        return sorDActive;
    }

    public void setSorDActive(String sorDActive) {
        this.sorDActive = sorDActive;
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

    public String getIgIpMac() {
        return igIpMac;
    }

    public void setIgIpMac(String igIpMac) {
        this.igIpMac = igIpMac;
    }

    public String getIgIpMacUpd() {
        return igIpMacUpd;
    }

    public void setIgIpMacUpd(String igIpMacUpd) {
        this.igIpMacUpd = igIpMacUpd;
    }

    public BigDecimal getLeadUpto() {
        return leadUpto;
    }

    public void setLeadUpto(BigDecimal leadUpto) {
        this.leadUpto = leadUpto;
    }

    public BigDecimal getLiftUpto() {
        return liftUpto;
    }

    public void setLiftUpto(BigDecimal liftUpto) {
        this.liftUpto = liftUpto;
    }

    public Long getLeadUnit() {
        return leadUnit;
    }

    public void setLeadUnit(Long leadUnit) {
        this.leadUnit = leadUnit;
    }

    public Long getSordCategory() {
        return sordCategory;
    }

    public void setSordCategory(Long sordCategory) {
        this.sordCategory = sordCategory;
    }

    public String getSordSubCategory() {
        return sordSubCategory;
    }

    public void setSordSubCategory(String sordSubCategory) {
        this.sordSubCategory = sordSubCategory;
    }

    public String getSordCategoryDesc() {
        return sordCategoryDesc;
    }

    public void setSordCategoryDesc(String sordCategoryDesc) {
        this.sordCategoryDesc = sordCategoryDesc;
    }

    public String getSorIteamUnitDesc() {
        return sorIteamUnitDesc;
    }

    public void setSorIteamUnitDesc(String sorIteamUnitDesc) {
        this.sorIteamUnitDesc = sorIteamUnitDesc;
    }

    public String getLeadUnitDesc() {
        return leadUnitDesc;
    }

    public void setLeadUnitDesc(String leadUnitDesc) {
        this.leadUnitDesc = leadUnitDesc;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public String getSchActiveFlag() {
        return schActiveFlag;
    }

    public void setSchActiveFlag(String schActiveFlag) {
        this.schActiveFlag = schActiveFlag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sorDIteamNo == null) ? 0 : sorDIteamNo.hashCode());
        result = prime * result + ((sordCategory == null) ? 0 : sordCategory.hashCode());
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
        ScheduleOfRateDetDto other = (ScheduleOfRateDetDto) obj;
        if (sorDIteamNo == null) {
            if (other.sorDIteamNo != null)
                return false;
        } else if (!sorDIteamNo.equals(other.sorDIteamNo))
            return false;
        if (sordCategory == null) {
            if (other.sordCategory != null)
                return false;
        } else if (!sordCategory.equals(other.sordCategory))
            return false;
        return true;
    }

    public Long getWorkEstimateId() {
        return workEstimateId;
    }

    public void setWorkEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
    }

    public boolean isEstimatechildAvailble() {
        return estimatechildAvailble;
    }

    public void setEstimatechildAvailble(boolean estimatechildAvailble) {
        this.estimatechildAvailble = estimatechildAvailble;
    }

    public String getWorkEFlag() {
        return workEFlag;
    }

    public void setWorkEFlag(String workEFlag) {
        this.workEFlag = workEFlag;
    }

    public String getDeletedFlagForSor() {
        return deletedFlagForSor;
    }

    public void setDeletedFlagForSor(String deletedFlagForSor) {
        this.deletedFlagForSor = deletedFlagForSor;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
