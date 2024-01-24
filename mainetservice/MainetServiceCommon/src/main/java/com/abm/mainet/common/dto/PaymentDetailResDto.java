package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class PaymentDetailResDto implements Serializable {
	private static final long serialVersionUID = 719867071805495974L;
	private String service_information;
	private String applicant_name;
	private String contact_number;
	private String email_id;
	private String payment_amount;
	private String order_number;

	private String transaction_reference_number;
	private String transaction_date_time;
	private String Transaction_status;
	private String labelName;

	public String getService_information() {
		return service_information;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public String getContact_number() {
		return contact_number;
	}

	public String getEmail_id() {
		return email_id;
	}

	public String getPayment_amount() {
		return payment_amount;
	}

	public String getOrder_number() {
		return order_number;
	}

	public String getTransaction_reference_number() {
		return transaction_reference_number;
	}

	public String getTransaction_date_time() {
		return transaction_date_time;
	}

	public String getTransaction_status() {
		return Transaction_status;
	}

	public void setService_information(String service_information) {
		this.service_information = service_information;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public void setTransaction_reference_number(String transaction_reference_number) {
		this.transaction_reference_number = transaction_reference_number;
	}

	public void setTransaction_date_time(String transaction_date_time) {
		this.transaction_date_time = transaction_date_time;
	}

	public void setTransaction_status(String transaction_status) {
		Transaction_status = transaction_status;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public String toString() {
		return "PaymentDetailResDto [service_information=" + service_information + ", applicant_name=" + applicant_name
				+ ", contact_number=" + contact_number + ", email_id=" + email_id + ", payment_amount=" + payment_amount
				+ ", order_number=" + order_number + ", transaction_reference_number=" + transaction_reference_number
				+ ", transaction_date_time=" + transaction_date_time + ", Transaction_status=" + Transaction_status
				+ ", labelName=" + labelName + "]";
	}

}
