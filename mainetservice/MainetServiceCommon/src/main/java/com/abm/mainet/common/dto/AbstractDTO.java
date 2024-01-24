package com.abm.mainet.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AbstractDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Long orgId;
	// private Long langId;
	private Long userId;

	@NotNull
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	/*
	 * public Long getLangId() { return langId; } public void setLangId(Long langId)
	 * { this.langId = langId; }
	 */

	@NotNull
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
