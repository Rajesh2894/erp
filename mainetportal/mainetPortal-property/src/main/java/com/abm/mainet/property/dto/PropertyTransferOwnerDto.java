package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class PropertyTransferOwnerDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 376728953755477981L;

    private long ownerDtlId;

    private PropertyTransferMasterDto tbAsTransferrMast;

    private String active;

    private Long addharno;

    private Long apmApplicationId;

    private String assNo;

    private Long createdBy;

    private Date createdDate;

    private Date endDate;

    private Long genderId;

    private String guardianName;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String mobileno;

    private Long orgId;

    private String otype;

    private String ownerName;

    private String panno;

    private Long propertyShare;

    private Long relationId;

    private String eMail;

    private Long smServiceId;

    private Date startDate;

    private String type;

    private Long updatedBy;

    private Date updatedDate;

    private String genderIdDesc;

    private String relationIdDesc;
    
    private String assoOwnerNameReg;

    public long getOwnerDtlId() {
        return ownerDtlId;
    }

    public void setOwnerDtlId(long ownerDtlId) {
        this.ownerDtlId = ownerDtlId;
    }

    public PropertyTransferMasterDto getTbAsTransferrMast() {
        return tbAsTransferrMast;
    }

    public void setTbAsTransferrMast(PropertyTransferMasterDto tbAsTransferrMast) {
        this.tbAsTransferrMast = tbAsTransferrMast;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getAddharno() {
        return addharno;
    }

    public void setAddharno(Long addharno) {
        this.addharno = addharno;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public Long getPropertyShare() {
        return propertyShare;
    }

    public void setPropertyShare(Long propertyShare) {
        this.propertyShare = propertyShare;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getGenderIdDesc() {
        return genderIdDesc;
    }

    public void setGenderIdDesc(String genderIdDesc) {
        this.genderIdDesc = genderIdDesc;
    }

    public String getRelationIdDesc() {
        return relationIdDesc;
    }

    public void setRelationIdDesc(String relationIdDesc) {
        this.relationIdDesc = relationIdDesc;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

	public String getAssoOwnerNameReg() {
		return assoOwnerNameReg;
	}

	public void setAssoOwnerNameReg(String assoOwnerNameReg) {
		this.assoOwnerNameReg = assoOwnerNameReg;
	}
    
    

}
