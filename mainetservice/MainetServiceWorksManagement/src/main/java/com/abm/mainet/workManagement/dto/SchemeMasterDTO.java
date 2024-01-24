package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author Vishwajeet.Kumar
 * @since 5 Dec 2017
 */
public class SchemeMasterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long wmSchId;

	private String wmSchCode;

	private String wmSchNameEng;

	private String wmSchNameReg;

	private String wmSchDesc;

	private Date wmSchStrDate;

	private Date wmSchEndDate;

	private Long wmSchFund;

	private String schFundName;

	private String schFundDesc;

	private BigDecimal wmSchPRev;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String schemeActive;

	private String schStrDateDesc;

	private String schEndDateDesc;

	private List<SchemeMastDatailsDTO> mastDetailsDTO = new ArrayList<>();

	private List<WmsProjectMasterDto> schemeProjectlist = new ArrayList<>();

	private Long wmSchCodeId1;

	private Long wmSchCodeId2;

	public Long getWmSchId() {
		return wmSchId;
	}

	public void setWmSchId(Long wmSchId) {
		this.wmSchId = wmSchId;
	}

	public String getWmSchCode() {
		return wmSchCode;
	}

	public void setWmSchCode(String wmSchCode) {
		this.wmSchCode = wmSchCode;
	}

	public String getWmSchNameEng() {
		return wmSchNameEng;
	}

	public void setWmSchNameEng(String wmSchNameEng) {
		this.wmSchNameEng = wmSchNameEng;
	}

	public String getWmSchNameReg() {
		return wmSchNameReg;
	}

	public void setWmSchNameReg(String wmSchNameReg) {
		this.wmSchNameReg = wmSchNameReg;
	}

	public String getWmSchDesc() {
		return wmSchDesc;
	}

	public void setWmSchDesc(String wmSchDesc) {
		this.wmSchDesc = wmSchDesc;
	}

	public Date getWmSchStrDate() {
		return wmSchStrDate;
	}

	public void setWmSchStrDate(Date wmSchStrDate) {
		this.wmSchStrDate = wmSchStrDate;
	}

	public Date getWmSchEndDate() {
		return wmSchEndDate;
	}

	public void setWmSchEndDate(Date wmSchEndDate) {
		this.wmSchEndDate = wmSchEndDate;
	}

	public Long getWmSchFund() {
		return wmSchFund;
	}

	public void setWmSchFund(Long wmSchFund) {
		this.wmSchFund = wmSchFund;
	}

	public BigDecimal getWmSchPRev() {
		return wmSchPRev;
	}

	public void setWmSchPRev(BigDecimal wmSchPRev) {
		this.wmSchPRev = wmSchPRev;
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

	public String getSchemeActive() {
		return schemeActive;
	}

	public void setSchemeActive(String schemeActive) {
		this.schemeActive = schemeActive;
	}

	public List<SchemeMastDatailsDTO> getMastDetailsDTO() {
		return mastDetailsDTO;
	}

	public void setMastDetailsDTO(List<SchemeMastDatailsDTO> mastDetailsDTO) {
		this.mastDetailsDTO = mastDetailsDTO;
	}

	public List<WmsProjectMasterDto> getSchemeProjectlist() {
		return schemeProjectlist;
	}

	public void setSchemeProjectlist(List<WmsProjectMasterDto> schemeProjectlist) {
		this.schemeProjectlist = schemeProjectlist;
	}

	public String getSchFundDesc() {
		return schFundDesc;
	}

	public void setSchFundDesc(String schFundDesc) {
		this.schFundDesc = schFundDesc;
	}

	public String getSchFundName() {
		return schFundName;
	}

	public void setSchFundName(String schFundName) {
		this.schFundName = schFundName;
	}

	public String getSchStrDateDesc() {
		return schStrDateDesc;
	}

	public void setSchStrDateDesc(String schStrDateDesc) {
		this.schStrDateDesc = schStrDateDesc;
	}

	public String getSchEndDateDesc() {
		return schEndDateDesc;
	}

	public void setSchEndDateDesc(String schEndDateDesc) {
		this.schEndDateDesc = schEndDateDesc;
	}

	@Override
	public String toString() {
		return "WmsSchemeMasterDTO [wmSchId=" + wmSchId + ", wmSchCode=" + wmSchCode + ", wmSchNameEng=" + wmSchNameEng
				+ ", wmSchNameReg=" + wmSchNameReg + ", wmSchDesc=" + wmSchDesc + ", wmSchStrDate=" + wmSchStrDate
				+ ", wmSchEndDate=" + wmSchEndDate + ", wmSchFund=" + wmSchFund + ", wmSchPRev=" + wmSchPRev
				+ ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ "]";
	}

	public Long getWmSchCodeId1() {
		return wmSchCodeId1;
	}

	public void setWmSchCodeId1(Long wmSchCodeId1) {
		this.wmSchCodeId1 = wmSchCodeId1;
	}

	public Long getWmSchCodeId2() {
		return wmSchCodeId2;
	}

	public void setWmSchCodeId2(Long wmSchCodeId2) {
		this.wmSchCodeId2 = wmSchCodeId2;
	}

}
