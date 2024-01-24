package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author vishwajeet.kumar
 *
 */
@Entity
@Table(name = "TB_WMS_WORKESTIMATE_MAS")
public class WorkEstimateMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WORKE_ID", nullable = false)
    private Long workEstemateId;

    @Column(name = "WORK_ID", nullable = false)
    private Long workId;

    @Column(name = "WORKE_ESTIMATE_TYPE", nullable = false)
    private String estimateType;

    @Column(name = "WORKE_ESTIMATE_NO", nullable = false)
    private String workeEstimateNo;

    @Column(name = "WORKE_PID", nullable = true)
    private Long workEstimPId;

    @Column(name = "WORKE_File_Name", nullable = true)
    private String workEstimFileName;

    @Column(name = "WORKE_PRE_ESTID", nullable = true)
    private Long workPrevEstimate;

    @Column(name = "SOR_ID", nullable = true)
    private Long sorId;

    @Column(name = "SORD_ID", nullable = true)
    private Long sordId;

    @Column(name = "MA_ID", nullable = true)
    private Long gRateMastId;

    @Column(name = "MA_PID", nullable = true)
    private Long maPId;

    @Column(name = "SORD_CATEGORY", nullable = false)
    private Long sordCategory;

    @Column(name = "SORD_SUBCATEGORY", nullable = false)
    private String sordSubCategory;

    @Column(name = "SORD_ITEMNO", nullable = false)
    private String sorDIteamNo;

    @Column(name = "SORD_DESCRIPTION", nullable = false)
    private String sorDDescription;

    @Column(name = "SORD_ITEM_UNIT", nullable = false)
    private Long sorIteamUnit;

    @Column(name = "SORD_BASIC_RATE", nullable = false)
    private BigDecimal sorBasicRate;

    @Column(name = "SORD_LABOUR_RATE", nullable = false)
    private BigDecimal sorLabourRate;

    @Column(name = "WORKE_QUANTITY", nullable = true)
    private BigDecimal workEstimQuantity;

    @Column(name = "WORKE_QUANTITY_UTL", nullable = true)
    private BigDecimal workEstimQuantityUtl;

    @Column(name = "WORKE_AMOUNT", nullable = false)
    private BigDecimal workEstimAmount;

    @Column(name = "WORKE_AMOUNT_UTL", nullable = false)
    private BigDecimal workEstimAmountUtl;

    @Column(name = "WORKE_FLAG", nullable = false)
    private String workEstimFlag;

    @Column(name = "WORKE_ACTIVE", nullable = false)
    private String workEstimActive;

    @Column(name = "ME_REMARK", nullable = true)
    private String meRemark;

    @Column(name = "ME_NOS", nullable = true)
    private Long meNos;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "WORKE_REVISEFLAG")
    private String workeReviseFlag;

    @Column(name = "CONT_ID")
    private Long contractId;

    @Column(name = "ME_LENGTH", nullable = true)
    private BigDecimal meMentLength;

    @Column(name = "ME_BREADTH", nullable = true)
    private BigDecimal meMentBreadth;

    @Column(name = "ME_HEIGHT", nullable = true)
    private BigDecimal meMentHeight;

    @Column(name = "WORK_PID")
    private Long workPreviousId;

    public Long getWorkEstemateId() {
        return workEstemateId;
    }

    public void setWorkEstemateId(Long workEstemateId) {
        this.workEstemateId = workEstemateId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

    public String getWorkeEstimateNo() {
        return workeEstimateNo;
    }

    public void setWorkeEstimateNo(String workeEstimateNo) {
        this.workeEstimateNo = workeEstimateNo;
    }

    public Long getWorkEstimPId() {
        return workEstimPId;
    }

    public void setWorkEstimPId(Long workEstimPId) {
        this.workEstimPId = workEstimPId;
    }

    public String getWorkEstimFileName() {
        return workEstimFileName;
    }

    public void setWorkEstimFileName(String workEstimFileName) {
        this.workEstimFileName = workEstimFileName;
    }

    public Long getWorkPrevEstimate() {
        return workPrevEstimate;
    }

    public void setWorkPrevEstimate(Long workPrevEstimate) {
        this.workPrevEstimate = workPrevEstimate;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public Long getSordId() {
        return sordId;
    }

    public void setSordId(Long sordId) {
        this.sordId = sordId;
    }

    public Long getgRateMastId() {
        return gRateMastId;
    }

    public void setgRateMastId(Long gRateMastId) {
        this.gRateMastId = gRateMastId;
    }

    public Long getMaPId() {
        return maPId;
    }

    public void setMaPId(Long maPId) {
        this.maPId = maPId;
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

    public BigDecimal getWorkEstimQuantity() {
        return workEstimQuantity;
    }

    public void setWorkEstimQuantity(BigDecimal workEstimQuantity) {
        this.workEstimQuantity = workEstimQuantity;
    }

    public BigDecimal getWorkEstimAmount() {
        return workEstimAmount;
    }

    public void setWorkEstimAmount(BigDecimal workEstimAmount) {
        this.workEstimAmount = workEstimAmount;
    }

    public BigDecimal getWorkEstimQuantityUtl() {
        return workEstimQuantityUtl;
    }

    public void setWorkEstimQuantityUtl(BigDecimal workEstimQuantityUtl) {
        this.workEstimQuantityUtl = workEstimQuantityUtl;
    }

    public BigDecimal getWorkEstimAmountUtl() {
        return workEstimAmountUtl;
    }

    public void setWorkEstimAmountUtl(BigDecimal workEstimAmountUtl) {
        this.workEstimAmountUtl = workEstimAmountUtl;
    }

    public String getWorkEstimFlag() {
        return workEstimFlag;
    }

    public void setWorkEstimFlag(String workEstimFlag) {
        this.workEstimFlag = workEstimFlag;
    }

    public String getWorkEstimActive() {
        return workEstimActive;
    }

    public void setWorkEstimActive(String workEstimActive) {
        this.workEstimActive = workEstimActive;
    }

    public String getMeRemark() {
        return meRemark;
    }

    public void setMeRemark(String meRemark) {
        this.meRemark = meRemark;
    }

    public Long getMeNos() {
        return meNos;
    }

    public void setMeNos(Long meNos) {
        this.meNos = meNos;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getWorkeReviseFlag() {
        return workeReviseFlag;
    }

    public void setWorkeReviseFlag(String workeReviseFlag) {
        this.workeReviseFlag = workeReviseFlag;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getMeMentLength() {
        return meMentLength;
    }

    public void setMeMentLength(BigDecimal meMentLength) {
        this.meMentLength = meMentLength;
    }

    public BigDecimal getMeMentBreadth() {
        return meMentBreadth;
    }

    public void setMeMentBreadth(BigDecimal meMentBreadth) {
        this.meMentBreadth = meMentBreadth;
    }

    public BigDecimal getMeMentHeight() {
        return meMentHeight;
    }

    public void setMeMentHeight(BigDecimal meMentHeight) {
        this.meMentHeight = meMentHeight;
    }

    public Long getWorkPreviousId() {
        return workPreviousId;
    }

    public void setWorkPreviousId(Long workPreviousId) {
        this.workPreviousId = workPreviousId;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_WORKESTIMATE_MAS", "WORKE_ID" };
    }

}
