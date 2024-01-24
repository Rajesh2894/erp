package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Employee;

/**
 * @author cherupelli.srikanth
 *@since 28 sep 2020
 */
public class PgBankParameterMasDto implements Serializable{

	private static final long serialVersionUID = -2221341433875037515L;

	private long pgId;

    private String merchantId;

    private String pgName;

    private String pgUrl;

    private String pgStatus;

    private Long bankid;

    private Long baAccountid;

    private Long orgId;

    private Long createdBy;

    private Date lmodDate;

    private int langId;

    private Employee updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long commN1;

    private String commV1;

    private Date commD1;

    private String commLo1;
    
    private List<PgBankParameterDetailDto> pgBankParameterList = new ArrayList<>();
    
    private String deptCode;

	public long getPgId() {
		return pgId;
	}

	public void setPgId(long pgId) {
		this.pgId = pgId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

	public String getPgUrl() {
		return pgUrl;
	}

	public void setPgUrl(String pgUrl) {
		this.pgUrl = pgUrl;
	}

	public String getPgStatus() {
		return pgStatus;
	}

	public void setPgStatus(String pgStatus) {
		this.pgStatus = pgStatus;
	}

	public Long getBankid() {
		return bankid;
	}

	public void setBankid(Long bankid) {
		this.bankid = bankid;
	}

	public Long getBaAccountid() {
		return baAccountid;
	}

	public void setBaAccountid(Long baAccountid) {
		this.baAccountid = baAccountid;
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

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Long getCommN1() {
		return commN1;
	}

	public void setCommN1(Long commN1) {
		this.commN1 = commN1;
	}

	public String getCommV1() {
		return commV1;
	}

	public void setCommV1(String commV1) {
		this.commV1 = commV1;
	}

	public Date getCommD1() {
		return commD1;
	}

	public void setCommD1(Date commD1) {
		this.commD1 = commD1;
	}

	public String getCommLo1() {
		return commLo1;
	}

	public void setCommLo1(String commLo1) {
		this.commLo1 = commLo1;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
    
	
    
}
