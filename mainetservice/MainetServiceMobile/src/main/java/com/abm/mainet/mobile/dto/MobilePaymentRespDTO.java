package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.payment.dto.PayURequestDTO;

public class MobilePaymentRespDTO implements Serializable {

	private static final long serialVersionUID = -8483560391742055558L;
	private String payRequestMsg;
	private String status;
	private long orgId;
	private long langId;
	private List<String> errorlist;
	private String responseMsg;
	private PayURequestDTO payuReqdto;
	private Long txnId;

	/**
	 * @return the payuReqdto
	 */
	public PayURequestDTO getPayuReqdto() {
		return payuReqdto;
	}

	/**
	 * @param payuReqdto
	 *            the payuReqdto to set
	 */
	public void setPayuReqdto(final PayURequestDTO payuReqdto) {
		this.payuReqdto = payuReqdto;
	}

	public String getPayRequestMsg() {
		return payRequestMsg;
	}

	public void setPayRequestMsg(final String payRequestMsg) {
		this.payRequestMsg = payRequestMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(final long orgId) {
		this.orgId = orgId;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(final long langId) {
		this.langId = langId;
	}

	public List<String> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(final List<String> errorlist) {
		this.errorlist = errorlist;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(final String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	@Override
	public String toString() {
		return "MobilePaymentRespDTO [payRequestMsg=" + payRequestMsg + ", status=" + status + ", orgId=" + orgId
				+ ", langId=" + langId + ", errorlist=" + errorlist + ", responseMsg=" + responseMsg + ", payuReqdto="
				+ payuReqdto + ", txnId=" + txnId + "]";
	}

}
