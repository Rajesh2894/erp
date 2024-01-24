package com.abm.mainet.buildingplan.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class DeveloperStakeholderDTO {
	
	private Long stakeholderId;
	
	private DeveloperRegistrationDTO developerRegMas; 
	
	private String stakeholderName;
	
	private String stakeholderDesignation;
	
	private Long stakeholderPercentage;
	
	private String stakeholderDocument;

	private List<DocumentDetailsVO> stakeholderDoc = new ArrayList<>();

	private List<AttachDocs> stakeholderDocsList = new ArrayList<>();
	
    private Long createdBy;
    
    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String is_deleted;

	public String getStakeholderName() {
		return stakeholderName;
	}

	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}

	public String getStakeholderDesignation() {
		return stakeholderDesignation;
	}

	public void setStakeholderDesignation(String stakeholderDesignation) {
		this.stakeholderDesignation = stakeholderDesignation;
	}

	public String getStakeholderDocument() {
		return stakeholderDocument;
	}

	public void setStakeholderDocument(String stakeholderDocument) {
		this.stakeholderDocument = stakeholderDocument;
	}

	public Long getStakeholderId() {
		return stakeholderId;
	}

	public void setStakeholderId(Long stakeholderId) {
		this.stakeholderId = stakeholderId;
	}

	public List<DocumentDetailsVO> getStakeholderDoc() {
		return stakeholderDoc;
	}

	public void setStakeholderDoc(List<DocumentDetailsVO> stakeholderDoc) {
		this.stakeholderDoc = stakeholderDoc;
	}

	public List<AttachDocs> getStakeholderDocsList() {
		return stakeholderDocsList;
	}

	public void setStakeholderDocsList(List<AttachDocs> stakeholderDocsList) {
		this.stakeholderDocsList = stakeholderDocsList;
	}

	public Long getStakeholderPercentage() {
		return stakeholderPercentage;
	}

	public void setStakeholderPercentage(Long stakeholderPercentage) {
		this.stakeholderPercentage = stakeholderPercentage;
	}

	public DeveloperRegistrationDTO getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(DeveloperRegistrationDTO developerRegMas) {
		this.developerRegMas = developerRegMas;
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

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

}
