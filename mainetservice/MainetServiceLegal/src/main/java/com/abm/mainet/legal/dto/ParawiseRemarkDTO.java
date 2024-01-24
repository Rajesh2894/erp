package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * The persistent class for the tb_lgl_caseparawise_remark database table.
 * 
 */
public class ParawiseRemarkDTO implements Serializable {

    private static final long serialVersionUID = -6172088355468137234L;

    private Long parId;

    private String parPagno;

    private String parSectionno;

    private String parComment;

    private String parUadRemark;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    private Long caseId;

    private Long atdId;
    
    private String RefCaseNo;
 

    private Long parentOrgId;
    private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AttachDocs> attachDocsList1 = new ArrayList<>();

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<CFCAttachment> getFetchDocumentList() {
        return fetchDocumentList;
    }

    public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
        this.fetchDocumentList = fetchDocumentList;
    }

    public Long getParId() {
        return this.parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public String getParUadRemark() {
        return parUadRemark;
    }

    public void setParUadRemark(String parUadRemark) {
        this.parUadRemark = parUadRemark;
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

    public String getParComment() {
        return this.parComment;
    }

    public void setParComment(String parComment) {
        this.parComment = parComment;
    }

    public String getParPagno() {
        return this.parPagno;
    }

    public void setParPagno(String parPagno) {
        this.parPagno = parPagno;
    }

    public String getParSectionno() {
        return this.parSectionno;
    }

    public void setParSectionno(String parSectionno) {
        this.parSectionno = parSectionno;
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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getAtdId() {
        return atdId;
    }

    public void setAtdId(Long atdId) {
        this.atdId = atdId;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

	public List<AttachDocs> getAttachDocsList1() {
		return attachDocsList1;
	}

	public void setAttachDocsList1(List<AttachDocs> attachDocsList1) {
		this.attachDocsList1 = attachDocsList1;
	}

	public String getRefCaseNo() {
		return RefCaseNo;
	}

	public void setRefCaseNo(String refCaseNo) {
		RefCaseNo = refCaseNo;
	}

	

	
}