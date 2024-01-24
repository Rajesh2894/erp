package com.abm.mainet.cfc.objection.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class HearingInspectionDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -324308597930934565L;

    private Long insHaerId;

    private Long createdBy;

    private Date createdDate;

    private String nameofHearingAuthoritys;

    private Date actualdate;

    private String emailid;

    private String mobno;

    private String name;

    private String availPerson;

    private Date insHearDate;

    private String insHearNo;

    private String remark;

    private Long hearingStatus;// by prefix

    private String inspStatus; // MainetConstant

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long objId;

    private Long orgId;

    private Long updatedBy;

    private Date updatedDate;

    private Long empId;

    private String pioRepresentativeName;

    private String applicantName;

    private String applicantRepName;

    private String repName;

    private Long decisionInFavorOf;// by prefix
    
    private Long dsgid;

    List<DocumentDetailsVO> hearingDocs = new ArrayList<>();

    public Long getInsHaerId() {
        return insHaerId;
    }

    public void setInsHaerId(Long insHaerId) {
        this.insHaerId = insHaerId;
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

    public Date getActualdate() {
        return actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailPerson() {
        return availPerson;
    }

    public void setAvailPerson(String availPerson) {
        this.availPerson = availPerson;
    }

    public String getInsHearNo() {
        return insHearNo;
    }

    public void setInsHearNo(String insHearNo) {
        this.insHearNo = insHearNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Date getInsHearDate() {
        return insHearDate;
    }

    public void setInsHearDate(Date insHearDate) {
        this.insHearDate = insHearDate;
    }

    public Long getHearingStatus() {
        return hearingStatus;
    }

    public void setHearingStatus(Long hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    public String getInspStatus() {
        return inspStatus;
    }

    public void setInspStatus(String inspStatus) {
        this.inspStatus = inspStatus;
    }

    public String getNameofHearingAuthoritys() {
        return nameofHearingAuthoritys;
    }

    public void setNameofHearingAuthoritys(String nameofHearingAuthoritys) {
        this.nameofHearingAuthoritys = nameofHearingAuthoritys;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public List<DocumentDetailsVO> getHearingDocs() {
        return hearingDocs;
    }

    public void setHearingDocs(List<DocumentDetailsVO> hearingDocs) {
        this.hearingDocs = hearingDocs;
    }

    public String getPioRepresentativeName() {
        return pioRepresentativeName;
    }

    public void setPioRepresentativeName(String pioRepresentativeName) {
        this.pioRepresentativeName = pioRepresentativeName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantRepName() {
        return applicantRepName;
    }

    public void setApplicantRepName(String applicantRepName) {
        this.applicantRepName = applicantRepName;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public Long getDecisionInFavorOf() {
        return decisionInFavorOf;
    }

    public void setDecisionInFavorOf(Long decisionInFavorOf) {
        this.decisionInFavorOf = decisionInFavorOf;
    }

	public Long getDsgid() {
		return dsgid;
	}

	public void setDsgid(Long dsgid) {
		this.dsgid = dsgid;
	}

}
