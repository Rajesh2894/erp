package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;

public class LicenseApplicationFeesDetDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long licFeeDetId;
	private LicenseApplicationFeeMasDTO licFeeMasId; // Assuming this is the ID of LicenseApplicationFeesMasterEntity
	private Long cwcPurposeId;
	private Long cwcIpc;
	private String cwcCalc;
	public Long getLicFeeDetId() {
		return licFeeDetId;
	}
	public void setLicFeeDetId(Long licFeeDetId) {
		this.licFeeDetId = licFeeDetId;
	}
	public LicenseApplicationFeeMasDTO getLicFeeMasId() {
		return licFeeMasId;
	}
	public void setLicFeeMasId(LicenseApplicationFeeMasDTO licFeeMasId) {
		this.licFeeMasId = licFeeMasId;
	}
	public Long getCwcPurposeId() {
		return cwcPurposeId;
	}
	public void setCwcPurposeId(Long cwcPurposeId) {
		this.cwcPurposeId = cwcPurposeId;
	}
	public Long getCwcIpc() {
		return cwcIpc;
	}
	public void setCwcIpc(Long cwcIpc) {
		this.cwcIpc = cwcIpc;
	}
	public String getCwcCalc() {
		return cwcCalc;
	}
	public void setCwcCalc(String cwcCalc) {
		this.cwcCalc = cwcCalc;
	}
	
	
}
