package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class LegalOpinionDetailDTO extends RequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -254562515562462940L;
	Long id;
	Long cseId;
	Long locId;
	Long opniondeptId;
	String matterOfDispute;
	String opinion;
	String sectionActApplied;
	String remark;
	String deptRemark;
	Long apmApplicationId;
	Long smServiceId;
	Long orgId;
	Long createdBy;
	Date createdDate;
	Long updatedBy;
	Date updateddate;
	String ipMacAdrress;
	String updatedIpMac;
	Long taskId;
	String comments;
	String decision;

	List<DocumentDetailsVO> docList = new ArrayList<>();
	
	List<DocumentDetailsVO> documentDetailsList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCseId() {
		return cseId;
	}

	public void setCseId(Long cseId) {
		this.cseId = cseId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	
	public Long getOpniondeptId() {
		return opniondeptId;
	}

	public void setOpniondeptId(Long opniondeptId) {
		this.opniondeptId = opniondeptId;
	}

	public String getMatterOfDispute() {
		return matterOfDispute;
	}

	public void setMatterOfDispute(String matterOfDispute) {
		this.matterOfDispute = matterOfDispute;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getSectionActApplied() {
		return sectionActApplied;
	}

	public void setSectionActApplied(String sectionActApplied) {
		this.sectionActApplied = sectionActApplied;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptRemark() {
		return deptRemark;
	}

	public void setDeptRemark(String deptRemark) {
		this.deptRemark = deptRemark;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
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

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public String getIpMacAdrress() {
		return ipMacAdrress;
	}

	public void setIpMacAdrress(String ipMacAdrress) {
		this.ipMacAdrress = ipMacAdrress;
	}

	public String getUpdatedIpMac() {
		return updatedIpMac;
	}

	public void setUpdatedIpMac(String updatedIpMac) {
		this.updatedIpMac = updatedIpMac;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<DocumentDetailsVO> getDocList() {
		return docList;
	}

	public void setDocList(List<DocumentDetailsVO> docList) {
		this.docList = docList;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<DocumentDetailsVO> getDocumentDetailsList() {
		return documentDetailsList;
	}

	public void setDocumentDetailsList(List<DocumentDetailsVO> documentDetailsList) {
		this.documentDetailsList = documentDetailsList;
	}

	
	

}
