/**
 * 
 */
package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author sarojkumar.yadav
 *
 */
public class PaymentResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783929959503537413L;

	private String payRequestMsg;
	private String status;
	private long orgId;
	private long langId;
	private List<String> errorlist;
	private String responseMsg;

	public String getPayRequestMsg() {
		return payRequestMsg;
	}

	public void setPayRequestMsg(String payRequestMsg) {
		this.payRequestMsg = payRequestMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
	}

	public List<String> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(List<String> errorlist) {
		this.errorlist = errorlist;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	@Override
	public String toString() {
		return "PaymentResponseDTO [payRequestMsg=" + payRequestMsg + ", status=" + status + ", orgId=" + orgId
				+ ", langId=" + langId + ", errorlist=" + errorlist + ", responseMsg=" + responseMsg + "]";
	}

}
