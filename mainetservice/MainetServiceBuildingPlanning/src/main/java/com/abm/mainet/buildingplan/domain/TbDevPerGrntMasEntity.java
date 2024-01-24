package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_DEV_PER_GRNT")
public class TbDevPerGrntMasEntity implements Serializable {

	private static final long serialVersionUID = -3757018620744437099L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID", nullable = false)
    private Long hDRULicenseId;

	@ManyToOne
	@JoinColumn(name = "TCP_DEV_REG_ID")
	private TbDeveloperRegistrationEntity developerRegMas;

    @Column(name = "DEV_PER_LIC", length = 45, nullable = true)
    private String licenseNo;

    @Column(name = "DEV_PER_LIC_DATE", nullable = true)
    private Date dateOfGrantLicense;
  
    @Column(name = "DEV_PER_LIC_PUR", length = 45, nullable = true)
    private Long purposeOfColony;

    @Column(name = "DEV_PER_LIC_VAL", nullable = true)
    private Date dateOfValidityLicense;
	
	public String[] getPkValues() {
        return new String[] { "DRN", "TB_BPMS_DEV_PER_GRNT", "ID" };
    }

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Date getDateOfGrantLicense() {
		return dateOfGrantLicense;
	}

	public void setDateOfGrantLicense(Date dateOfGrantLicense) {
		this.dateOfGrantLicense = dateOfGrantLicense;
	}

	public Long getPurposeOfColony() {
		return purposeOfColony;
	}

	public void setPurposeOfColony(Long purposeOfColony) {
		this.purposeOfColony = purposeOfColony;
	}

	public Date getDateOfValidityLicense() {
		return dateOfValidityLicense;
	}

	public void setDateOfValidityLicense(Date dateOfValidityLicense) {
		this.dateOfValidityLicense = dateOfValidityLicense;
	}

	public TbDeveloperRegistrationEntity getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(TbDeveloperRegistrationEntity developerRegMas) {
		this.developerRegMas = developerRegMas;
	}

	public Long gethDRULicenseId() {
		return hDRULicenseId;
	}

	public void sethDRULicenseId(Long hDRULicenseId) {
		this.hDRULicenseId = hDRULicenseId;
	}
}