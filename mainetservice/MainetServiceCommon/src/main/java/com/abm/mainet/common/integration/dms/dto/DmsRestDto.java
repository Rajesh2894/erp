package com.abm.mainet.common.integration.dms.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.utility.LookUp;

public class DmsRestDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orgCode;
	private String empLoginId;
	private String deptCode;
	List<LookUp> dmsMetadataList;
	List<LookUp> attachmentList;
	private long formId;

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getEmpLoginId() {
		return empLoginId;
	}

	public void setEmpLoginId(String empLoginId) {
		this.empLoginId = empLoginId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<LookUp> getDmsMetadataList() {
		return dmsMetadataList;
	}

	public void setDmsMetadataList(List<LookUp> dmsMetadataList) {
		this.dmsMetadataList = dmsMetadataList;
	}

	public List<LookUp> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<LookUp> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
