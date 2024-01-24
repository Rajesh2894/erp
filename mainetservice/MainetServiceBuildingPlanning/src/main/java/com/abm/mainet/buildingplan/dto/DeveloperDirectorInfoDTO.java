package com.abm.mainet.buildingplan.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class DeveloperDirectorInfoDTO {
	private Long directorId;
	
	private DeveloperRegistrationDTO developerRegMas;
	
	private String dinNumber;
	
	private String directorName;
	
	private Long directorContactNumber;
	
	private List<DocumentDetailsVO> directorDoc = new ArrayList<>();

	private List<AttachDocs> directorDocsList= new ArrayList<>();

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String isDeleted;
	
	public Long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}

	public String getDinNumber() {
		return dinNumber;
	}

	public void setDinNumber(String dinNumber) {
		this.dinNumber = dinNumber;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public List<DocumentDetailsVO> getDirectorDoc() {
		return directorDoc;
	}

	public void setDirectorDoc(List<DocumentDetailsVO> directorDoc) {
		this.directorDoc = directorDoc;
	}

	public List<AttachDocs> getDirectorDocsList() {
		return directorDocsList;
	}

	public void setDirectorDocsList(List<AttachDocs> directorDocsList) {
		this.directorDocsList = directorDocsList;
	}

	public Long getDirectorContactNumber() {
		return directorContactNumber;
	}

	public void setDirectorContactNumber(Long directorContactNumber) {
		this.directorContactNumber = directorContactNumber;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public DeveloperRegistrationDTO getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(DeveloperRegistrationDTO developerRegMas) {
		this.developerRegMas = developerRegMas;
	}
	
}
