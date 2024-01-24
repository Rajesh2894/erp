package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class FineChargeCollectionDTO implements Serializable {

    private static final long serialVersionUID = -7847960911531995873L;

    private Long fchId;

    private Long empid;

    private BigDecimal fchAmount;

    private Date fchEntrydate;

    private String fchFlag;

    private String fchManualNo;

    private String fchMobno;

    private String fchName;

    private Long fchType;

    private Long registrationId;

    private Long reiceptNo;

    private String lattiude;

    private String longitude;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private List<DocumentDetailsVO> documentList;

    private String payMode;

    private Long monthNo;

    private String monthName;

    private String address;

    private String wardEng;

    private String wardhnd;

    private String chargeDescEng;

    private String chargeDescHnd;

    private String chargeAmount;

    private String districtName;

    private List<FineChargeCollectionDTO> fineChargeCollectionList;
    
    private String serviceShortCode;

    public FineChargeCollectionDTO() {
    }

    public Long getFchId() {
        return this.fchId;
    }

    public void setFchId(Long fchId) {
        this.fchId = fchId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getEmpid() {
        return this.empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public BigDecimal getFchAmount() {
        return this.fchAmount;
    }

    public void setFchAmount(BigDecimal fchAmount) {
        this.fchAmount = fchAmount;
    }

    public Date getFchEntrydate() {
        return this.fchEntrydate;
    }

    public void setFchEntrydate(Date fchEntrydate) {
        this.fchEntrydate = fchEntrydate;
    }

    public String getFchFlag() {
        return this.fchFlag;
    }

    public void setFchFlag(String fchFlag) {
        this.fchFlag = fchFlag;
    }

    public String getFchManualNo() {
        return this.fchManualNo;
    }

    public void setFchManualNo(String fchManualNo) {
        this.fchManualNo = fchManualNo;
    }

    public String getFchMobno() {
        return this.fchMobno;
    }

    public void setFchMobno(String fchMobno) {
        this.fchMobno = fchMobno;
    }

    public String getFchName() {
        return this.fchName;
    }

    public void setFchName(String fchName) {
        this.fchName = fchName;
    }

    public Long getFchType() {
        return fchType;
    }

    public void setFchType(Long fchType) {
        this.fchType = fchType;
    }

    public String getLattiude() {
        return this.lattiude;
    }

    public void setLattiude(String lattiude) {
        this.lattiude = lattiude;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Long getReiceptNo() {
        return reiceptNo;
    }

    public void setReiceptNo(Long reiceptNo) {
        this.reiceptNo = reiceptNo;
    }

    public Long getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(Long monthNo) {
        this.monthNo = monthNo;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String getWardEng() {
        return wardEng;
    }

    public void setWardEng(String wardEng) {
        this.wardEng = wardEng;
    }

    public String getWardhnd() {
        return wardhnd;
    }

    public void setWardhnd(String wardhnd) {
        this.wardhnd = wardhnd;
    }

    public String getChargeDescEng() {
        return chargeDescEng;
    }

    public void setChargeDescEng(String chargeDescEng) {
        this.chargeDescEng = chargeDescEng;
    }

    public String getChargeDescHnd() {
        return chargeDescHnd;
    }

    public void setChargeDescHnd(String chargeDescHnd) {
        this.chargeDescHnd = chargeDescHnd;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public List<FineChargeCollectionDTO> getFineChargeCollectionList() {
        return fineChargeCollectionList;
    }

    public void setFineChargeCollectionList(List<FineChargeCollectionDTO> fineChargeCollectionList) {
        this.fineChargeCollectionList = fineChargeCollectionList;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

}