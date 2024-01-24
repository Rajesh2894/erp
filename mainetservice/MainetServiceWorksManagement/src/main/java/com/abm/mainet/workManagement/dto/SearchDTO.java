package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class SearchDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
    
    private String astSerialNo;
   
    private Long orgId;
    
    

	public String getAstSerialNo() {
		return astSerialNo;
	}

	public void setAstSerialNo(String astSerialNo) {
		this.astSerialNo = astSerialNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
    
}
