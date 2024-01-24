package com.abm.mainet.intranet.dto.report;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChoiceRequest {
    @NotBlank
    @Size(max = 40)
    private String text;
    
    private String lgIpMac;
	private String lgIpMacUpd;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private int langId;
	private Date lmodDate;
	private Long orgId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
    
    
    
}
