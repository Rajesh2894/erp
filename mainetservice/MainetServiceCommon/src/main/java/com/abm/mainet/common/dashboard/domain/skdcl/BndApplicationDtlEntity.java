package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BndApplicationDtlEntity {
	@Id
	@Column(name = "NUM")
	private Long id;

	@Column(name = "APPLICANT_NAME")
	private String applicationNamee;

	@Column(name = "APM_APPLICATION_ID")
	private Long apmAapplicationId;

	@Column(name = "APM_APPLICATION_DATE")
	private Date apmApplicationDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNamee() {
		return applicationNamee;
	}

	public void setApplicationNamee(String applicationNamee) {
		this.applicationNamee = applicationNamee;
	}

	public Long getApmAapplicationId() {
		return apmAapplicationId;
	}

	public void setApmAapplicationId(Long apmAapplicationId) {
		this.apmAapplicationId = apmAapplicationId;
	}

	public Date getApmApplicationDate() {
		return apmApplicationDate;
	}

	public void setApmApplicationDate(Date apmApplicationDate) {
		this.apmApplicationDate = apmApplicationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "BndApplicationDtlEntity [id=" + id + ", applicationNamee=" + applicationNamee + ", apmAapplicationId="
				+ apmAapplicationId + ", apmApplicationDate=" + apmApplicationDate + ", status=" + status
				+ ", serviceName=" + serviceName + "]";
	}
}
