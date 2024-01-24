package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class CircularNotificationMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5885578238098941708L;
	
	private Long cnId;
	
	private String circularTitle;
	
	private String circularNo;
	
	private Long convenerOfCircular;
	
	private String convenerOfCircularName;
	
	private String convenerName;
	
	private Date dateOfCircular;
	
	private String emailBody;
	
	private String smsBody;

	private String docDesc;
	
	private String status;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private List<CircularNotiicationDetDto> circularNotiicationDetDtos = new ArrayList<>();
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	
	
	
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

	public Long getCnId() {
		return cnId;
	}

	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}

	public String getCircularTitle() {
		return circularTitle;
	}

	public void setCircularTitle(String circularTitle) {
		this.circularTitle = circularTitle;
	}

	public String getCircularNo() {
		return circularNo;
	}

	public void setCircularNo(String circularNo) {
		this.circularNo = circularNo;
	}

	public Long getConvenerOfCircular() {
		return convenerOfCircular;
	}

	public void setConvenerOfCircular(Long convenerOfCircular) {
		this.convenerOfCircular = convenerOfCircular;
	}

	public String getConvenerName() {
		return convenerName;
	}

	public void setConvenerName(String convenerName) {
		this.convenerName = convenerName;
	}

	public Date getDateOfCircular() {
		return dateOfCircular;
	}

	public void setDateOfCircular(Date dateOfCircular) {
		this.dateOfCircular = dateOfCircular;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<CircularNotiicationDetDto> getCircularNotiicationDetDtos() {
		return circularNotiicationDetDtos;
	}

	public void setCircularNotiicationDetDtos(List<CircularNotiicationDetDto> circularNotiicationDetDtos) {
		this.circularNotiicationDetDtos = circularNotiicationDetDtos;
	}

	public String getConvenerOfCircularName() {
		return convenerOfCircularName;
	}

	public void setConvenerOfCircularName(String convenerOfCircularName) {
		this.convenerOfCircularName = convenerOfCircularName;
	}

	
	
}
