package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowDocumentDTO implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String dmsDocId;
	private String dmsDocName;
	private String dmsDocVersion;
	private String dmsDocPath;
	private Long orgId;
	private Date createdDate;
	private Long createdBy;
	private Date modifiedDate;
	private Long modifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

	public String getDmsDocName() {
		return dmsDocName;
	}

	public void setDmsDocName(String dmsDocName) {
		this.dmsDocName = dmsDocName;
	}

	public String getDmsDocVersion() {
		return dmsDocVersion;
	}

	public void setDmsDocVersion(String dmsDocVersion) {
		this.dmsDocVersion = dmsDocVersion;
	}

	public String getDmsDocPath() {
		return dmsDocPath;
	}

	public void setDmsDocPath(String dmsDocPath) {
		this.dmsDocPath = dmsDocPath;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	

}
