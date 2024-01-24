/**
 * 
 */
package com.abm.mainet.bill.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saiprasad.Vengurekar
 *
 */
public class BillGenErrorMapDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353417223004584820L;

	private Long refId;

	private Date errDate;

	private String errMsg;

	private Long orgId;

	private String refnumber;

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Date getErrDate() {
		return errDate;
	}

	public void setErrDate(Date errDate) {
		this.errDate = errDate;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getRefnumber() {
		return refnumber;
	}

	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber;
	}



}
