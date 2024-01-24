package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_bpms_lic_fees")
public class LicenseApplicationFeesMasterEntity implements Serializable {

	private static final long serialVersionUID = -4172501212383484097L;	
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
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

	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_lic_fee_mas", "lic_fee_mas_id" };
	}

	
}
