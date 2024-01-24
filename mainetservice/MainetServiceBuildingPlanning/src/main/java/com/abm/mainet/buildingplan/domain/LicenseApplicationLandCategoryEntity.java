package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_LAND_CATOGORY")
public class LicenseApplicationLandCategoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LAND_CATOGORY_ID", nullable = false)
	private Long landCategoryID;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "tc_lic_mstr_id", unique = true)
	private LicenseApplicationMaster licenseApplicationMaster;

	@Column(name = "existing_approach")
	private String existingApproach;

	@Column(name = "a_app_W_rev_ras")
	private String aAppWRevRas;

	@Column(name = "b_app_ASR")
	private String bAppASR;

	@Column(name = "c_app_CSR")
	private String cAppCSR;

	@Column(name = "d_app_acc_LOA")
	private String dAppAccLOA;

	@Column(name = "d1_app_acc_GPM")
	private String d1AppAccGPM;

	@Column(name = "e_app_OL")
	private String eAppOL;

	@Column(name = "e1_app_OL_GM")
	private String e1AppOLGM;

	@Column(name = "cat_II_wid")
	private BigDecimal cat2Width;

	@Column(name = "cat_II_irrv")
	private String cat2irrv;

	public Long getLandCategoryID() {
		return landCategoryID;
	}

	public void setLandCategoryID(Long landCategoryID) {
		this.landCategoryID = landCategoryID;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public String getExistingApproach() {
		return existingApproach;
	}

	public void setExistingApproach(String existingApproach) {
		this.existingApproach = existingApproach;
	}

	public String getaAppWRevRas() {
		return aAppWRevRas;
	}

	public void setaAppWRevRas(String aAppWRevRas) {
		this.aAppWRevRas = aAppWRevRas;
	}

	public String getbAppASR() {
		return bAppASR;
	}

	public void setbAppASR(String bAppASR) {
		this.bAppASR = bAppASR;
	}

	public String getcAppCSR() {
		return cAppCSR;
	}

	public void setcAppCSR(String cAppCSR) {
		this.cAppCSR = cAppCSR;
	}

	public String getdAppAccLOA() {
		return dAppAccLOA;
	}

	public void setdAppAccLOA(String dAppAccLOA) {
		this.dAppAccLOA = dAppAccLOA;
	}

	public String getD1AppAccGPM() {
		return d1AppAccGPM;
	}

	public void setD1AppAccGPM(String d1AppAccGPM) {
		this.d1AppAccGPM = d1AppAccGPM;
	}

	public String geteAppOL() {
		return eAppOL;
	}

	public void seteAppOL(String eAppOL) {
		this.eAppOL = eAppOL;
	}

	public String getE1AppOLGM() {
		return e1AppOLGM;
	}

	public void setE1AppOLGM(String e1AppOLGM) {
		this.e1AppOLGM = e1AppOLGM;
	}

	public BigDecimal getCat2Width() {
		return cat2Width;
	}

	public void setCat2Width(BigDecimal cat2Width) {
		this.cat2Width = cat2Width;
	}

	public String getCat2irrv() {
		return cat2irrv;
	}

	public void setCat2irrv(String cat2irrv) {
		this.cat2irrv = cat2irrv;
	}

	public String[] getPkValues() {
		return new String[] { "NL", "TB_BPMS_LAND_CATOGORY", "LAND_CATOGORY_ID" };
	}

}
