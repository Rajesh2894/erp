package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyTransferMasterDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -644395188741972174L;

    private long transferMstId;

    private Date actualTransferDate;

    private Long apmApplicationId;

    private Long autBy;

    private Date autDate;

    private String autStatus;

    private Double baseValue;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private BigDecimal marketValue;

    private long orgId;

    private Long ownerType;

    private String proAssNo;

    private BigDecimal salesDeedValue;

    private String status;

    private Long transferType;

    private Long updatedBy;

    private Date updatedDate;

    private Long smServiceId;

    private double billTotalAmt;

    private String proAssOwnerTypeName;

    private Long deptId;

    private int langId;

    private Long empId;

    private Long locationId;

    private List<DocumentDetailsVO> docs = new ArrayList<>(0);

    private List<PropertyTransferOwnerDto> propTransferOwnerList = new ArrayList<>(0);

    List<BillDisplayDto> charges = new ArrayList<>(0);

    private String appliChargeFlag;

    private Long smFeesSchedule;

    private String mutIntiFlag;

    private long mutId;

    private Long assLandType;

    private ProvisionalAssesmentMstDto assesmentMstDto = new ProvisionalAssesmentMstDto();

    private String scrutinyChargeFlag;

    private Long receiptNo;

    private String certificateNo;

    private Long receiptId;

    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Long wardNo;

    private String referenceNo;

    private String regNo;
    
    private String flatNo;
    
    private String authoStatus;
    
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    
    private String flatOrParentLevelChange;
    
    public long getTransferMstId() {
        return transferMstId;
    }

    public void setTransferMstId(long transferMstId) {
        this.transferMstId = transferMstId;
    }

    public Date getActualTransferDate() {
        return actualTransferDate;
    }

    public void setActualTransferDate(Date actualTransferDate) {
        this.actualTransferDate = actualTransferDate;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getAutBy() {
        return autBy;
    }

    public void setAutBy(Long autBy) {
        this.autBy = autBy;
    }

    public Date getAutDate() {
        return autDate;
    }

    public void setAutDate(Date autDate) {
        this.autDate = autDate;
    }

    public String getAutStatus() {
        return autStatus;
    }

    public void setAutStatus(String autStatus) {
        this.autStatus = autStatus;
    }

    public Double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(Double baseValue) {
        this.baseValue = baseValue;
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

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public String getProAssNo() {
        return proAssNo;
    }

    public void setProAssNo(String proAssNo) {
        this.proAssNo = proAssNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<PropertyTransferOwnerDto> getPropTransferOwnerList() {
        return propTransferOwnerList;
    }

    public void setPropTransferOwnerList(List<PropertyTransferOwnerDto> propTransferOwnerList) {
        this.propTransferOwnerList = propTransferOwnerList;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public double getBillTotalAmt() {
        return billTotalAmt;
    }

    public void setBillTotalAmt(double billTotalAmt) {
        this.billTotalAmt = billTotalAmt;
    }

    public List<DocumentDetailsVO> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentDetailsVO> docs) {
        this.docs = docs;
    }

    public Long getTransferType() {
        return transferType;
    }

    public void setTransferType(Long transferType) {
        this.transferType = transferType;
    }

    public String getProAssOwnerTypeName() {
        return proAssOwnerTypeName;
    }

    public void setProAssOwnerTypeName(String proAssOwnerTypeName) {
        this.proAssOwnerTypeName = proAssOwnerTypeName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getSalesDeedValue() {
        return salesDeedValue;
    }

    public void setSalesDeedValue(BigDecimal salesDeedValue) {
        this.salesDeedValue = salesDeedValue;
    }

    public List<BillDisplayDto> getCharges() {
        return charges;
    }

    public void setCharges(List<BillDisplayDto> charges) {
        this.charges = charges;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getAppliChargeFlag() {
        return appliChargeFlag;
    }

    public void setAppliChargeFlag(String appliChargeFlag) {
        this.appliChargeFlag = appliChargeFlag;
    }

    public Long getSmFeesSchedule() {
        return smFeesSchedule;
    }

    public void setSmFeesSchedule(Long smFeesSchedule) {
        this.smFeesSchedule = smFeesSchedule;
    }

    public String getMutIntiFlag() {
        return mutIntiFlag;
    }

    public void setMutIntiFlag(String mutIntiFlag) {
        this.mutIntiFlag = mutIntiFlag;
    }

    public long getMutId() {
        return mutId;
    }

    public void setMutId(long mutId) {
        this.mutId = mutId;
    }

    public Long getAssLandType() {
        return assLandType;
    }

    public void setAssLandType(Long assLandType) {
        this.assLandType = assLandType;
    }

    public ProvisionalAssesmentMstDto getAssesmentMstDto() {
        return assesmentMstDto;
    }

    public void setAssesmentMstDto(ProvisionalAssesmentMstDto assesmentMstDto) {
        this.assesmentMstDto = assesmentMstDto;
    }

    public String getScrutinyChargeFlag() {
        return scrutinyChargeFlag;
    }

    public void setScrutinyChargeFlag(String scrutinyChargeFlag) {
        this.scrutinyChargeFlag = scrutinyChargeFlag;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public Long getWardNo() {
        return wardNo;
    }

    public void setWardNo(Long wardNo) {
        this.wardNo = wardNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getAuthoStatus() {
		return authoStatus;
	}

	public void setAuthoStatus(String authoStatus) {
		this.authoStatus = authoStatus;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public String getFlatOrParentLevelChange() {
		return flatOrParentLevelChange;
	}

	public void setFlatOrParentLevelChange(String flatOrParentLevelChange) {
		this.flatOrParentLevelChange = flatOrParentLevelChange;
	}

}
