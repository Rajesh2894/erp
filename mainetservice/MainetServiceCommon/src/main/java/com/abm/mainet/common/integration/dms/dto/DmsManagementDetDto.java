package com.abm.mainet.common.integration.dms.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public class DmsManagementDetDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long dmsDetId;

	private DmsManagementDto dmsDocsMetadataDto;

	private String mtKey;

	private String mtVal;

	private String docDesc;

	private Organisation orgId;

	private Employee userId;

	private Date lmodDate;

	private Employee updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	public Long getDmsDetId() {
		return dmsDetId;
	}

	public void setDmsDetId(Long dmsDetId) {
		this.dmsDetId = dmsDetId;
	}

	public String getMtKey() {
		return mtKey;
	}

	public void setMtKey(String mtKey) {
		this.mtKey = mtKey;
	}

	public String getMtVal() {
		return mtVal;
	}

	public void setMtVal(String mtVal) {
		this.mtVal = mtVal;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public Organisation getOrgId() {
		return orgId;
	}

	public void setOrgId(Organisation orgId) {
		this.orgId = orgId;
	}

	public Employee getUserId() {
		return userId;
	}

	public void setUserId(Employee userId) {
		this.userId = userId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
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

	public DmsManagementDto getDmsDocsMetadataDto() {
		return dmsDocsMetadataDto;
	}

	public void setDmsDocsMetadataDto(DmsManagementDto dmsDocsMetadataDto) {
		this.dmsDocsMetadataDto = dmsDocsMetadataDto;
	}

}
