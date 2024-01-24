package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class NOCforOtherGovtDeptDto implements Serializable {

	private static final long serialVersionUID = -6739169959719822788L;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<DocumentDetailsVO> documentList;
	private Long cfcWard1;
	private Long cfcWard2;
	private Long cfcWard3;
	private Long cfcWard4;
	private Long cfcWard5;
	private Long orgId;

	private String birthRegstatus;
	private String birthRegremark;
	private Long smServiceId;
	private Long apmApplicationId;

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getBirthRegstatus() {
		return birthRegstatus;
	}

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public String getBirthRegremark() {
		return birthRegremark;
	}

	public void setBirthRegremark(String birthRegremark) {
		this.birthRegremark = birthRegremark;
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

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public Long getCfcWard1() {
		return cfcWard1;
	}

	public void setCfcWard1(Long cfcWard1) {
		this.cfcWard1 = cfcWard1;
	}

	public Long getCfcWard2() {
		return cfcWard2;
	}

	public void setCfcWard2(Long cfcWard2) {
		this.cfcWard2 = cfcWard2;
	}

	public Long getCfcWard3() {
		return cfcWard3;
	}

	public void setCfcWard3(Long cfcWard3) {
		this.cfcWard3 = cfcWard3;
	}

	public Long getCfcWard4() {
		return cfcWard4;
	}

	public void setCfcWard4(Long cfcWard4) {
		this.cfcWard4 = cfcWard4;
	}

	public Long getCfcWard5() {
		return cfcWard5;
	}

	public void setCfcWard5(Long cfcWard5) {
		this.cfcWard5 = cfcWard5;
	}

}
