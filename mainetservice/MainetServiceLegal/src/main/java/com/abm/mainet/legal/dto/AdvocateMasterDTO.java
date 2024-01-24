package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AdvocateMasterDTO implements Serializable {

    private static final long serialVersionUID = -7764045423809754891L;

    private Long advId;

    private String advAddress;

    private Date advAppfromdate;

    private Date advApptodate;

    private Date advDob;

    private String advEmail;

    private BigDecimal advExperience;

	private String advFirstNm;

    private Long advGen;

    private String advLastNm;

    private String advMaritalstatus;

    private String advMiddleNm;

    private String advMobile;

    private String advPanno;

    private String advStatus;

    private String advUid;

    private Long advFeeType;

	private BigDecimal advFeeAmt;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;
    
   	private String advOfficeAddress;
    
    private String advChamberNo;
    
    private String fullName;
    
  
    private String adv_barCouncilNo;
    private Long adv_courtNameId;
    private Long adv_advocateTypeId;
    
    private Long crtId;

    private String crtName;
    
    private Long langId;
    
    private Long applicationId;
    
    Long taskId;
    
    private List<AdvocateMasterDTO> advocateMasterDTOList = new ArrayList();
    
    private  List<AdvocateEducationDetailDTO> advEducationDetailDTOList = new ArrayList<>();
    
    
    public List<AdvocateMasterDTO> getAdvocateMasterDTOList() {
		return advocateMasterDTOList;
	}

	public void setAdvocateMasterDTOList(List<AdvocateMasterDTO> advocateMasterDTOList) {
		this.advocateMasterDTOList = advocateMasterDTOList;
	}

	public Long getCrtId() {
		return crtId;
	}

	public void setCrtId(Long crtId) {
		this.crtId = crtId;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

	

	public String getAdv_barCouncilNo() {
		return adv_barCouncilNo;
	}

	public void setAdv_barCouncilNo(String adv_barCouncilNo) {
		this.adv_barCouncilNo = adv_barCouncilNo;
	}

	public Long getAdv_courtNameId() {
		return adv_courtNameId;
	}

	public void setAdv_courtNameId(Long adv_courtNameId) {
		this.adv_courtNameId = adv_courtNameId;
	}

	public Long getAdv_advocateTypeId() {
		return adv_advocateTypeId;
	}

	public void setAdv_advocateTypeId(Long adv_advocateTypeId) {
		this.adv_advocateTypeId = adv_advocateTypeId;
	}

	public AdvocateMasterDTO() {
    }

    public Long getAdvId() {
        return this.advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public String getAdvAddress() {
        return this.advAddress;
    }

    public void setAdvAddress(String advAddress) {
        this.advAddress = advAddress;
    }

    public Date getAdvAppfromdate() {
        return this.advAppfromdate;
    }

    public void setAdvAppfromdate(Date advAppfromdate) {
        this.advAppfromdate = advAppfromdate;
    }

    public Date getAdvApptodate() {
        return this.advApptodate;
    }

    public void setAdvApptodate(Date advApptodate) {
        this.advApptodate = advApptodate;
    }

    public Date getAdvDob() {
        return this.advDob;
    }

    public void setAdvDob(Date advDob) {
        this.advDob = advDob;
    }

    public String getAdvEmail() {
        return this.advEmail;
    }

    public void setAdvEmail(String advEmail) {
        this.advEmail = advEmail;
    }

    public String getAdvFirstNm() {
        return this.advFirstNm;
    }

    public void setAdvFirstNm(String advFirstNm) {
        this.advFirstNm = advFirstNm;
    }

    public BigDecimal getAdvExperience() {
		return advExperience;
	}

	public void setAdvExperience(BigDecimal advExperience) {
		this.advExperience = advExperience;
	}
    
    public Long getAdvGen() {
        return this.advGen;
    }

    public void setAdvGen(Long advGen) {
        this.advGen = advGen;
    }

    public String getAdvLastNm() {
        return this.advLastNm;
    }

    public void setAdvLastNm(String advLastNm) {
        this.advLastNm = advLastNm;
    }

    public String getAdvMaritalstatus() {
        return this.advMaritalstatus;
    }

    public void setAdvMaritalstatus(String advMaritalstatus) {
        this.advMaritalstatus = advMaritalstatus;
    }

    public String getAdvMiddleNm() {
        return this.advMiddleNm;
    }

    public void setAdvMiddleNm(String advMiddleNm) {
        this.advMiddleNm = advMiddleNm;
    }

    public String getAdvMobile() {
        return this.advMobile;
    }

    public void setAdvMobile(String advMobile) {
        this.advMobile = advMobile;
    }

    public String getAdvPanno() {
        return this.advPanno;
    }

    public void setAdvPanno(String advPanno) {
        this.advPanno = advPanno;
    }

    public String getAdvStatus() {
        return this.advStatus;
    }

    public void setAdvStatus(String advStatus) {
        this.advStatus = advStatus;
    }

    public String getAdvUid() {
        return this.advUid;
    }

    public void setAdvUid(String advUid) {
        this.advUid = advUid;
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

    public Long getAdvFeeType() {
		return advFeeType;
	}

	public void setAdvFeeType(Long advFeeType) {
		this.advFeeType = advFeeType;
	}

    public BigDecimal getAdvFeeAmt() {
        return advFeeAmt;
    }

    public void setAdvFeeAmt(BigDecimal advFeeAmt) {
        this.advFeeAmt = advFeeAmt;
    }
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAdvOfficeAddress() {
		return advOfficeAddress;
	}

	public void setAdvOfficeAddress(String advOfficeAddress) {
		this.advOfficeAddress = advOfficeAddress;
	}

	public String getAdvChamberNo() {
		return advChamberNo;
	}

	public void setAdvChamberNo(String advChamberNo) {
		this.advChamberNo = advChamberNo;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<AdvocateEducationDetailDTO> getAdvEducationDetailDTOList() {
		return advEducationDetailDTOList;
	}

	public void setAdvEducationDetailDTOList(List<AdvocateEducationDetailDTO> advEducationDetailDTOList) {
		this.advEducationDetailDTOList = advEducationDetailDTOList;
	}
	


}