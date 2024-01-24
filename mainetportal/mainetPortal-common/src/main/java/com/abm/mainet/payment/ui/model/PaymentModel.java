package com.abm.mainet.payment.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
/**
 *
 * @author umashanker.kanaujiya
 *
 */
@Component
@Scope("session")
public class PaymentModel extends AbstractFormModel {
	private static final long serialVersionUID = 1673485505725926573L;
	private PaymentRequestDTO paymentRequestDTO;
	private String emailId;
	private String mobNo;
	private Long orgId;
	private int langId;
	private String orgName;
	private Long empId;
	PaymentTransactionMasDTO paymentTransactionMas = new PaymentTransactionMasDTO();
	
	ProvisionalCertificateDTO provisional = new ProvisionalCertificateDTO();

	ChallanReceiptPrintDTO receiptDTO = new ChallanReceiptPrintDTO();
	/**
	 * @return the paymentRequestDTO
	 */
	public PaymentRequestDTO getPaymentRequestDTO() {
		return paymentRequestDTO;
	}

	/**
	 * @param paymentRequestDTO
	 *            the paymentRequestDTO to set
	 */
	public void setPaymentRequestDTO(final PaymentRequestDTO paymentRequestDTO) {
		this.paymentRequestDTO = paymentRequestDTO;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the mobNo
	 */
	public String getMobNo() {
		return mobNo;
	}

	/**
	 * @param mobNo
	 *            the mobNo to set
	 */
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the langId
	 */
	public int getLangId() {
		return langId;
	}

	/**
	 * @param langId
	 *            the langId to set
	 */
	public void setLangId(final int langId) {
		this.langId = langId;
	}

	/**
	 * @return the empId
	 */
	public Long getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(final Long empId) {
		this.empId = empId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(final String orgName) {
		this.orgName = orgName;
	}

	public PaymentTransactionMasDTO getPaymentTransactionMas() {
		return paymentTransactionMas;
}

	public void setPaymentTransactionMas(PaymentTransactionMasDTO paymentTransactionMas) {
		this.paymentTransactionMas = paymentTransactionMas;
	}

	public ProvisionalCertificateDTO getProvisional() {
		return provisional;
	}

	public void setProvisional(ProvisionalCertificateDTO provisional) {
		this.provisional = provisional;
	}

	public ChallanReceiptPrintDTO getReceiptDTO() {
		return receiptDTO;
	}

	public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
		this.receiptDTO = receiptDTO;
	}
	

}
