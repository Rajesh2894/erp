/**
 * 
 */
package com.abm.mainet.water.rest.dto;

/**
 * @author akshata.bhat
 *
 */
public class KDMCWaterDetailsRequestDTO {
	
	private Integer langId;

	private Long orgId;
	
	private String waterConnNo;
	
	private Long rmRcptNo;

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getWaterConnNo() {
		return waterConnNo;
	}

	public void setWaterConnNo(String waterConnNo) {
		this.waterConnNo = waterConnNo;
	}

	public Long getRmRcptNo() {
		return rmRcptNo;
	}

	public void setRmRcptNo(Long rmRcptNo) {
		this.rmRcptNo = rmRcptNo;
	}

	@Override
	public String toString() {
		return "KDMCWaterDetailsRequestDTO [langId=" + langId + ", orgId=" + orgId + ", waterConnNo=" + waterConnNo
				+ ", rmRcptNo=" + rmRcptNo + "]";
	}

}
