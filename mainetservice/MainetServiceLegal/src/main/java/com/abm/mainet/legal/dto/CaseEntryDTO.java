package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 */
public class CaseEntryDTO implements Serializable {

    private static final long serialVersionUID = -4336943828401854892L;

    private Long cseId;

    private Long crtId;

    private String crtName;

    private Long cseState;

    private String cseCity;

    private Long advId;

    private Long cseCaseStatusId;

    private Long cseCatId1;

    private Long cseCatId2;

    private Date cseDate;

    private String cseDateDesc;

    private Long cseDeptid;

    private String cseDeptName;

    private Date cseEntryDt;

    private String cseMatdet1;

    private String cseName;

    private Long csePeicDroa;

    private String cseReferenceno;

    private String cseRefsuitNo;

    private String cseRemarks;

    private String cseSectAppl;

    private String cseSuitNo;

    private Long cseTypId;

    private String cseTypeName;

    /* private Long locId; */

    private Long orgid;
    
    private Long concernedUlb;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long officeIncharge;

    private Date appointmentDate;

    private String oicEmail;

    private String oicMobile;

    private String oicDepartment;

    private String orderNo;

    private String address;

    private String advName;
    
    private Date orderDate;
    
    private Long parentOrgid;

    private List<CaseEntryDetailDTO> tbLglCasePddetails;

    private List<CaseEntryArbitoryFeeDto> tbLglArbitoryFee;

    private List<OfficerInchargeDetailsDTO> tbLglCaseOICdetails;
    
  //Added by rahul.chaubey
    //compairing the judgementMaster date with judgementimplementation date
    private Date judgementMasterDate;
    
    private String caseNo;
    
    private String hearingDate;
    private String Status;
    private String cseCategoryDesc;
    private String cseFilingNumber;
    private Date cseFilingDate;
    private String courtName;
    private String noteShowFlag;
    
    private Long locId;
    
    private String physicalNo;
    
    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public Long getCseCatId1() {
        return cseCatId1;
    }

    public void setCseCatId1(Long cseCatId1) {
        this.cseCatId1 = cseCatId1;
    }

    public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getCseCatId2() {
        return cseCatId2;
    }

    public void setCseCatId2(Long cseCatId2) {
        this.cseCatId2 = cseCatId2;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
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

    public Long getCseState() {
        return cseState;
    }

    public void setCseState(Long cseState) {
        this.cseState = cseState;
    }

    public String getCseCity() {
        return cseCity;
    }

    public void setCseCity(String cseCity) {
        this.cseCity = cseCity;
    }

    public Long getCseCaseStatusId() {
        return cseCaseStatusId;
    }

    public void setCseCaseStatusId(Long cseCaseStatusId) {
        this.cseCaseStatusId = cseCaseStatusId;
    }

    /*
     * public Long getCseCatId() { return cseCatId; } public void setCseCatId(Long cseCatId) { this.cseCatId = cseCatId; } public
     * Long getCseSubcatId() { return cseSubcatId; } public void setCseSubcatId(Long cseSubcatId) { this.cseSubcatId =
     * cseSubcatId; }
     */

    public Date getCseDate() {
        return cseDate;
    }

    public void setCseDate(Date cseDate) {
        this.cseDate = cseDate;
    }

    public String getCseDateDesc() {
        return cseDateDesc;
    }

    public void setCseDateDesc(String cseDateDesc) {
        this.cseDateDesc = cseDateDesc;
    }

    public Long getCseDeptid() {
        return cseDeptid;
    }

    public void setCseDeptid(Long cseDeptid) {
        this.cseDeptid = cseDeptid;
    }

    public String getCseDeptName() {
        return cseDeptName;
    }

    public void setCseDeptName(String cseDeptName) {
        this.cseDeptName = cseDeptName;
    }

    public Date getCseEntryDt() {
        return cseEntryDt;
    }

    public void setCseEntryDt(Date cseEntryDt) {
        this.cseEntryDt = cseEntryDt;
    }

    public String getCseMatdet1() {
        return cseMatdet1;
    }

    public void setCseMatdet1(String cseMatdet1) {
        this.cseMatdet1 = cseMatdet1;
    }

    public String getCseName() {
        return cseName;
    }

    public void setCseName(String cseName) {
        this.cseName = cseName;
    }

    public Long getCsePeicDroa() {
        return csePeicDroa;
    }

    public void setCsePeicDroa(Long csePeicDroa) {
        this.csePeicDroa = csePeicDroa;
    }

    public String getCseReferenceno() {
        return cseReferenceno;
    }

    public void setCseReferenceno(String cseReferenceno) {
        this.cseReferenceno = cseReferenceno;
    }

    public String getCseRefsuitNo() {
        return cseRefsuitNo;
    }

    public void setCseRefsuitNo(String cseRefsuitNo) {
        this.cseRefsuitNo = cseRefsuitNo;
    }

    public String getCseRemarks() {
        return cseRemarks;
    }

    public void setCseRemarks(String cseRemarks) {
        this.cseRemarks = cseRemarks;
    }

    public String getCseSectAppl() {
        return cseSectAppl;
    }

    public void setCseSectAppl(String cseSectAppl) {
        this.cseSectAppl = cseSectAppl;
    }

    public String getCseSuitNo() {
        return cseSuitNo;
    }

    public void setCseSuitNo(String cseSuitNo) {
        this.cseSuitNo = cseSuitNo;
    }

    public Long getCseTypId() {
        return cseTypId;
    }

    public void setCseTypId(Long cseTypId) {
        this.cseTypId = cseTypId;
    }

    public String getCseTypeName() {
        return cseTypeName;
    }

    public void setCseTypeName(String cseTypeName) {
        this.cseTypeName = cseTypeName;
    }

    /*
     * public Long getLocId() { return locId; } public void setLocId(Long locId) { this.locId = locId; }
     */

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public List<CaseEntryDetailDTO> getTbLglCasePddetails() {
        return tbLglCasePddetails;
    }

    public void setTbLglCasePddetails(List<CaseEntryDetailDTO> tbLglCasePddetails) {
        this.tbLglCasePddetails = tbLglCasePddetails;
    }

    /**
     * @return the officeIncharge
     */
    public Long getOfficeIncharge() {
        return officeIncharge;
    }

    /**
     * @param officeIncharge the officeIncharge to set
     */
    public void setOfficeIncharge(Long officeIncharge) {
        this.officeIncharge = officeIncharge;
    }

    /**
     * @return the appointmentDate
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * @return the oicEmail
     */
    public String getOicEmail() {
        return oicEmail;
    }

    /**
     * @param oicEmail the oicEmail to set
     */
    public void setOicEmail(String oicEmail) {
        this.oicEmail = oicEmail;
    }

    /**
     * @return the oicMobile
     */
    public String getOicMobile() {
        return oicMobile;
    }

    /**
     * @param oicMobile the oicMobile to set
     */
    public void setOicMobile(String oicMobile) {
        this.oicMobile = oicMobile;
    }

    /**
     * @return the oicDepartment
     */
    public String getOicDepartment() {
        return oicDepartment;
    }

    /**
     * @param oicDepartment the oicDepartment to set
     */
    public void setOicDepartment(String oicDepartment) {
        this.oicDepartment = oicDepartment;
    }

    public List<CaseEntryArbitoryFeeDto> getTbLglArbitoryFee() {
        return tbLglArbitoryFee;
    }

    public void setTbLglArbitoryFee(List<CaseEntryArbitoryFeeDto> tbLglArbitoryFee) {
        this.tbLglArbitoryFee = tbLglArbitoryFee;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OfficerInchargeDetailsDTO> getTbLglCaseOICdetails() {
        return tbLglCaseOICdetails;
    }

    public void setTbLglCaseOICdetails(List<OfficerInchargeDetailsDTO> tbLglCaseOICdetails) {
        this.tbLglCaseOICdetails = tbLglCaseOICdetails;
    }

	public Date getJudgementMasterDate() {
		return judgementMasterDate;
	}

	public void setJudgementMasterDate(Date judgementMasterDate) {
		this.judgementMasterDate = judgementMasterDate;
	}

	public Long getConcernedUlb() {
		return concernedUlb;
	}

	public void setConcernedUlb(Long concernedUlb) {
		this.concernedUlb = concernedUlb;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getParentOrgid() {
		return parentOrgid;
	}

	public void setParentOrgid(Long parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getHearingDate() {
		return hearingDate;
	}

	public void setHearingDate(String hearingDate) {
		this.hearingDate = hearingDate;
	}
	
	public String getCseCategoryDesc() {
		return cseCategoryDesc;
	}

	public void setCseCategoryDesc(String cseCategoryDesc) {
		this.cseCategoryDesc = cseCategoryDesc;
	}

	public String getCseFilingNumber() {
		return cseFilingNumber;
	}

	public void setCseFilingNumber(String cseFilingNumber) {
		this.cseFilingNumber = cseFilingNumber;
	}

	public Date getCseFilingDate() {
		return cseFilingDate;
	}

	public void setCseFilingDate(Date cseFilingDate) {
		this.cseFilingDate = cseFilingDate;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getNoteShowFlag() {
		return noteShowFlag;
	}

	public void setNoteShowFlag(String noteShowFlag) {
		this.noteShowFlag = noteShowFlag;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getPhysicalNo() {
		return physicalNo;
	}

	public void setPhysicalNo(String physicalNo) {
		this.physicalNo = physicalNo;
	}

	
}