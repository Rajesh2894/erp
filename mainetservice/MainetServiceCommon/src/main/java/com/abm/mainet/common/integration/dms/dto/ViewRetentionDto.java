package com.abm.mainet.common.integration.dms.dto;

import java.io.Serializable;

public class ViewRetentionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long level1;
	private Long level2;

	public Long getLevel1() {
		return level1;
	}

	public void setLevel1(Long level1) {
		this.level1 = level1;
	}

	public Long getLevel2() {
		return level2;
	}

	public void setLevel2(Long level2) {
		this.level2 = level2;
	}

}
