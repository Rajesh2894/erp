package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourtMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long crtType;
    private String crtName;
    private String crtNameReg;
    private String crtStartTime;
    private String crtEndTime;
    private String crtPhoneNo;
    private String crtEmailId;
    private String crtWebsite;
	private String crtAddress;
    private String crtStatus;
    private Long crtState;
    private String crtCity;
    private Long orgId;
    private Long createdBy;
    private Date createDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    
    private List<String> cseTypId;
    
    
    private List<JudgeDetailMasterDTO> judgeDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrtType() {
        return crtType;
    }

    public void setCrtType(Long crtType) {
        this.crtType = crtType;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtNameReg() {
        return crtNameReg;
    }

    public void setCrtNameReg(String crtNameReg) {
        this.crtNameReg = crtNameReg;
    }

    public String getCrtPhoneNo() {
        return crtPhoneNo;
    }

    public void setCrtPhoneNo(String crtPhoneNo) {
        this.crtPhoneNo = crtPhoneNo;
    }

    public String getCrtEmailId() {
        return crtEmailId;
    }

    public void setCrtEmailId(String crtEmailId) {
        this.crtEmailId = crtEmailId;
    }

    public String getCrtAddress() {
        return crtAddress;
    }

    public void setCrtAddress(String crtAddress) {
        this.crtAddress = crtAddress;
    }

    public String getCrtStatus() {
        return crtStatus;
    }

    public void setCrtStatus(String crtStatus) {
        this.crtStatus = crtStatus;
    }

    public String getCrtStartTime() {
        return crtStartTime;
    }

    public void setCrtStartTime(String crtStartTime) {
        this.crtStartTime = crtStartTime;
    }

    public String getCrtEndTime() {
        return crtEndTime;
    }

    public void setCrtEndTime(String crtEndTime) {
        this.crtEndTime = crtEndTime;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public List<JudgeDetailMasterDTO> getJudgeDetails() {
        return judgeDetails;
    }

    public void setJudgeDetails(List<JudgeDetailMasterDTO> judgeDetails) {
        this.judgeDetails = judgeDetails;
    }
    
    public String getCrtWebsite() {
		return crtWebsite;
	}

	public void setCrtWebsite(String crtWebsite) {
		this.crtWebsite = crtWebsite;
	}

	public Long getCrtState() {
		return crtState;
	}

	public void setCrtState(Long crtState) {
		this.crtState = crtState;
	}

	public String getCrtCity() {
		return crtCity;
	}

	public void setCrtCity(String crtCity) {
		this.crtCity = crtCity;
	}

	public List<String> getCseTypId() {
		return cseTypId;
	}

	public void setCseTypId(List<String> cseTypId) {
		this.cseTypId = cseTypId;
	}	
	
	
}
