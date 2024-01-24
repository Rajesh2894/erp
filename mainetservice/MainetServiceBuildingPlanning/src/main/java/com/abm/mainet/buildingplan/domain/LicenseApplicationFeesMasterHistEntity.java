package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_bpms_lic_fees_hist")
public class LicenseApplicationFeesMasterHistEntity implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "h_lic_fee_id", nullable = false)
    private Long hLicFeeid;
	
	@Column(name = "lic_fee_id", nullable = false)
	private Long licFeeid;
	
	@ManyToOne
	@JoinColumn(name = "tcp_lic_app_no")
    private LicenseApplicationMaster licenseApplicationMaster;
	
	@Column(name = "CWC_Purpose")
	private String taxCategory;
	
	@Column(name = "CWC_IPC")
	private BigDecimal fees;
	
	@Column(name = "CWC_CALCULATIONS")
	private String calculation;
	
	@Column(name = "H_STATUS")
	private String hStatus;
	
	@Column(name = "TASK_ID")
	private Long taskId;
	
	@Column(name = "FIELD_DECISION")
	private String decision;
	
	@Column(name = "FIELD_REMARK")
	private String resolutionComments;

	public Long gethLicFeeid() {
		return hLicFeeid;
	}

	public void sethLicFeeid(Long hLicFeeid) {
		this.hLicFeeid = hLicFeeid;
	}

	public Long getLicFeeid() {
		return licFeeid;
	}

	public void setLicFeeid(Long licFeeid) {
		this.licFeeid = licFeeid;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public String getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
	
	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_lic_fees_hist", "h_lic_fee_mas_id" };
	}

}
