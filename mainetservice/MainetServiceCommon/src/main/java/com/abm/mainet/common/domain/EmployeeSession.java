package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EMPLOYEE_SESSION")
public class EmployeeSession {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SESSION_ID", nullable = false)
	private Long sessionId;

	@Column(name = "EMPID", nullable = false)
	private Long empId;

	@Column(name = "DATE_OF_ACTION", nullable = true)
	private Date dateOfAction;

	@Column(name = "LOGINDATE", nullable = true)
	private Date loginDate;

	@Column(name = "LOGOUTDATE", nullable = true)
	private Date logOutDate;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "TRANS_MODE", nullable = false)
	private String transMode;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getDateOfAction() {
		return dateOfAction;
	}

	public void setDateOfAction(Date dateOfAction) {
		this.dateOfAction = dateOfAction;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogOutDate() {
		return logOutDate;
	}

	public void setLogOutDate(Date logOutDate) {
		this.logOutDate = logOutDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String[] getPkValues() {
		return new String[] { "AUT", "EMPLOYEE_SESSION", "SESSION_ID" };
	}

}
