package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author vishwajeet.kumar
 *
 */
public class WorkEstimateMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long workEstemateId;

    private Long workId;

    private String estimateType;

    private Long workEstimPId;

    private String workEstimFileName;

    private Long workPrevEstimate;

    private Long sorId;

    private Long sordId;

    private Long gRateMastId;

    private Long maPId;

    private Long sordCategory;

    private String sordCategoryStr;

    private String sordSubCategory;

    private String sorDIteamNo;

    private String sorDDescription;

    private Long sorIteamUnit;

    private String sorIteamUnitStr;

    private String sorIteamUnitDesc;

    private BigDecimal sorBasicRate;

    private BigDecimal sorLabourRate;

    private BigDecimal workEstimQuantity;

    private BigDecimal workEstimAmount;

    private BigDecimal totalEsimateAmount;

    private String workEstimFlag;

    private String workEstimActive;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long projId;

    private String reportType;

    private Long mbId;

    private Long mbDetId;

    private boolean checkBox;

    private boolean mbDetails;

    private BigDecimal workEstimQuantityUtl;

    private BigDecimal workEstimAmountUtl;

    private boolean childAvailable;

    private BigDecimal rate;

    private String workeReviseFlag;

    private Long contractId;

    private String workeEstimateNo;

    private List<ScheduleOfRateMstDto> scheduleOfRateMastList;

    private List<WorkEstimateMasterDto> workEstimateList = new ArrayList<>();

    private List<WmsMaterialMasterDto> rateList = new ArrayList<>();

    List<WorkEstimateMeasureDetailsDto> workMeasurementDto = new ArrayList<>();

    private BigDecimal meMentLength;

    private BigDecimal meMentBreadth;

    private BigDecimal meMentHeight;

    private BigDecimal totalMbAmount;

    private String workMbFlag;

    private BigDecimal meActValue;

    private BigDecimal meActDirTotal;

    private String meRemark;

    private Long meNos;

    private Long meActualNos;

    private BigDecimal cummulativeAmount;

    private Long workPreviousId;

    private BigDecimal reviseEstimQty;

    private BigDecimal workRevisedEstimAmount;

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

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

    public List<ScheduleOfRateMstDto> getScheduleOfRateMastList() {
        return scheduleOfRateMastList;
    }

    public void setScheduleOfRateMastList(List<ScheduleOfRateMstDto> scheduleOfRateMastList) {
        this.scheduleOfRateMastList = scheduleOfRateMastList;
    }

    public List<WorkEstimateMasterDto> getWorkEstimateList() {
        return workEstimateList;
    }

    public void setWorkEstimateList(List<WorkEstimateMasterDto> workEstimateList) {
        this.workEstimateList = workEstimateList;
    }

    public BigDecimal getTotalEsimateAmount() {
        return totalEsimateAmount;
    }

    public void setTotalEsimateAmount(BigDecimal totalEsimateAmount) {
        this.totalEsimateAmount = totalEsimateAmount;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getSorIteamUnitDesc() {
        return sorIteamUnitDesc;
    }

    public void setSorIteamUnitDesc(String sorIteamUnitDesc) {
        this.sorIteamUnitDesc = sorIteamUnitDesc;
    }

    public List<WmsMaterialMasterDto> getRateList() {
        return rateList;
    }

    public void setRateList(List<WmsMaterialMasterDto> rateList) {
        this.rateList = rateList;
    }

    public List<WorkEstimateMeasureDetailsDto> getWorkMeasurementDto() {
        return workMeasurementDto;
    }

    public void addMeasurementDto(WorkEstimateMeasureDetailsDto measurementDto) {
        if (null != measurementDto) {
            workMeasurementDto.add(measurementDto);
        }

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

    public boolean isMbDetails() {
        return mbDetails;
    }

    public void setMbDetails(boolean mbDetails) {
        this.mbDetails = mbDetails;
    }

    public Long getMbDetId() {
        return mbDetId;
    }

    public void setMbDetId(Long mbDetId) {
        this.mbDetId = mbDetId;
    }

    public Long getMbId() {
        return mbId;
    }

    public void setMbId(Long mbId) {
        this.mbId = mbId;
    }

    public boolean isChildAvailable() {
        return childAvailable;
    }

    public void setChildAvailable(boolean childAvailable) {
        this.childAvailable = childAvailable;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public String getWorkeEstimateNo() {
        return workeEstimateNo;
    }

    public void setWorkeEstimateNo(String workeEstimateNo) {
        this.workeEstimateNo = workeEstimateNo;
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

    public String getSordCategoryStr() {
        return sordCategoryStr;
    }

    public void setSordCategoryStr(String sordCategoryStr) {
        this.sordCategoryStr = sordCategoryStr;
    }

    public String getSorIteamUnitStr() {
        return sorIteamUnitStr;
    }

    public void setSorIteamUnitStr(String sorIteamUnitStr) {
        this.sorIteamUnitStr = sorIteamUnitStr;
    }

    public BigDecimal getTotalMbAmount() {
        return totalMbAmount;
    }

    public void setTotalMbAmount(BigDecimal totalMbAmount) {
        this.totalMbAmount = totalMbAmount;
    }

    public String getWorkMbFlag() {
        return workMbFlag;
    }

    public void setWorkMbFlag(String workMbFlag) {
        this.workMbFlag = workMbFlag;
    }

    public BigDecimal getMeActValue() {
        return meActValue;
    }

    public void setMeActValue(BigDecimal meActValue) {
        this.meActValue = meActValue;
    }

    public BigDecimal getMeActDirTotal() {
        return meActDirTotal;
    }

    public void setMeActDirTotal(BigDecimal meActDirTotal) {
        this.meActDirTotal = meActDirTotal;
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

    public Long getMeActualNos() {
        return meActualNos;
    }

    public void setMeActualNos(Long meActualNos) {
        this.meActualNos = meActualNos;
    }

    public BigDecimal getCummulativeAmount() {
        return cummulativeAmount;
    }

    public void setCummulativeAmount(BigDecimal cummulativeAmount) {
        this.cummulativeAmount = cummulativeAmount;
    }

    public Long getWorkPreviousId() {
        return workPreviousId;
    }

    public void setWorkPreviousId(Long workPreviousId) {
        this.workPreviousId = workPreviousId;
    }

    public BigDecimal getReviseEstimQty() {
        return reviseEstimQty;
    }

    public void setReviseEstimQty(BigDecimal reviseEstimQty) {
        this.reviseEstimQty = reviseEstimQty;
    }

    public BigDecimal getWorkRevisedEstimAmount() {
        return workRevisedEstimAmount;
    }

    public void setWorkRevisedEstimAmount(BigDecimal workRevisedEstimAmount) {
        this.workRevisedEstimAmount = workRevisedEstimAmount;
    }

}
