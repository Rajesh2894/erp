/*
 * Created on 9 Dec 2015 ( Time 12:11:59 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocOperationWZMappingDto implements Serializable {

	private static final long serialVersionUID = -7956762006291274470L;
	private Long locowzmpId;
	private Long dpDeptId;
	private String dpDeptDesc;
	private String dpDeptDescReg;
	private Long orgId;
	private Long locId;
	private Long codIdOperLevel1;
	private Long codIdOperLevel2;
	private Long codIdOperLevel3;
	private Long codIdOperLevel4;
	private Long codIdOperLevel5;
	private Long userId;
	private int langId;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMac;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMacUpd;
	private boolean opertionalChkBox;

	private String codIdOperLevel1Desc;
	private String codIdOperLevel2Desc;
	private String codIdOperLevel3Desc;
	private String codIdOperLevel4Desc;
	private String codIdOperLevel5Desc;
	private String codIdOperLevel1DescReg;
	private String codIdOperLevel2DescReg;
	private String codIdOperLevel3DescReg;
	private String codIdOperLevel4DescReg;
	private String codIdOperLevel5DescReg;

	public Long getLocowzmpId() {
		return locowzmpId;
	}

	public void setLocowzmpId(final Long locowzmpId) {
		this.locowzmpId = locowzmpId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(final Long locId) {
		this.locId = locId;
	}

	public Long getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(final Long codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public Long getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(final Long codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public Long getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(final Long codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public Long getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(final Long codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public Long getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(final Long codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(final int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(final Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public String getDpDeptDescReg() {
		return dpDeptDescReg;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public boolean isOpertionalChkBox() {
		return opertionalChkBox;
	}

	public void setOpertionalChkBox(final boolean opertionalChkBox) {
		this.opertionalChkBox = opertionalChkBox;
	}

	public String getCodIdOperLevel1Desc() {
		return codIdOperLevel1Desc;
	}

	public void setCodIdOperLevel1Desc(String codIdOperLevel1Desc) {
		this.codIdOperLevel1Desc = codIdOperLevel1Desc;
	}

	public String getCodIdOperLevel2Desc() {
		return codIdOperLevel2Desc;
	}

	public void setCodIdOperLevel2Desc(String codIdOperLevel2Desc) {
		this.codIdOperLevel2Desc = codIdOperLevel2Desc;
	}

	public String getCodIdOperLevel3Desc() {
		return codIdOperLevel3Desc;
	}

	public void setCodIdOperLevel3Desc(String codIdOperLevel3Desc) {
		this.codIdOperLevel3Desc = codIdOperLevel3Desc;
	}

	public String getCodIdOperLevel4Desc() {
		return codIdOperLevel4Desc;
	}

	public void setCodIdOperLevel4Desc(String codIdOperLevel4Desc) {
		this.codIdOperLevel4Desc = codIdOperLevel4Desc;
	}

	public String getCodIdOperLevel5Desc() {
		return codIdOperLevel5Desc;
	}

	public void setCodIdOperLevel5Desc(String codIdOperLevel5Desc) {
		this.codIdOperLevel5Desc = codIdOperLevel5Desc;
	}

	public String getDpDeptDesc() {
		return dpDeptDesc;
	}

	public void setDpDeptDesc(String dpDeptDesc) {
		this.dpDeptDesc = dpDeptDesc;
	}

	public String getCodIdOperLevel1DescReg() {
		return codIdOperLevel1DescReg;
	}

	public String getCodIdOperLevel2DescReg() {
		return codIdOperLevel2DescReg;
	}

	public String getCodIdOperLevel3DescReg() {
		return codIdOperLevel3DescReg;
	}

	public String getCodIdOperLevel4DescReg() {
		return codIdOperLevel4DescReg;
	}

	public String getCodIdOperLevel5DescReg() {
		return codIdOperLevel5DescReg;
	}

	public void setDpDeptDescReg(String dpDeptDescReg) {
		this.dpDeptDescReg = dpDeptDescReg;
	}

	public void setCodIdOperLevel1DescReg(String codIdOperLevel1DescReg) {
		this.codIdOperLevel1DescReg = codIdOperLevel1DescReg;
	}

	public void setCodIdOperLevel2DescReg(String codIdOperLevel2DescReg) {
		this.codIdOperLevel2DescReg = codIdOperLevel2DescReg;
	}

	public void setCodIdOperLevel3DescReg(String codIdOperLevel3DescReg) {
		this.codIdOperLevel3DescReg = codIdOperLevel3DescReg;
	}

	public void setCodIdOperLevel4DescReg(String codIdOperLevel4DescReg) {
		this.codIdOperLevel4DescReg = codIdOperLevel4DescReg;
	}

	public void setCodIdOperLevel5DescReg(String codIdOperLevel5DescReg) {
		this.codIdOperLevel5DescReg = codIdOperLevel5DescReg;
	}
}
