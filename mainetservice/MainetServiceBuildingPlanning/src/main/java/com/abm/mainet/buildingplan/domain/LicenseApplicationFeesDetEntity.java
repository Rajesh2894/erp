package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_bpms_fees_det")
public class LicenseApplicationFeesDetEntity implements Serializable {

	private static final long serialVersionUID = -4172501212383484097L;	
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "lic_fee_det_id", nullable = false)
    private Long licFeeDetId;
	
	@Column(name = "CWC_Purpose_id")
    private Long cwcPurposeId;
	
	@Column(name = "CWC_IPC")
    private Long cwcIpc;
	
	@Column(name = "CWC_CALCULATIONS")
    private String cwcCalc;

	public Long getLicFeeDetId() {
		return licFeeDetId;
	}

	public void setLicFeeDetId(Long licFeeDetId) {
		this.licFeeDetId = licFeeDetId;
	}

	public Long getCwcPurposeId() {
		return cwcPurposeId;
	}

	public void setCwcPurposeId(Long cwcPurposeId) {
		this.cwcPurposeId = cwcPurposeId;
	}

	public Long getCwcIpc() {
		return cwcIpc;
	}

	public void setCwcIpc(Long cwcIpc) {
		this.cwcIpc = cwcIpc;
	}

	public String getCwcCalc() {
		return cwcCalc;
	}

	public void setCwcCalc(String cwcCalc) {
		this.cwcCalc = cwcCalc;
	}
	
	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_fees_det", "lic_fee_det_id" };
	}
	
}
