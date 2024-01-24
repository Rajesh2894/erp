package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LicenseApplicationLandCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long landCategoryID;
    private LicenseApplicationMasterDTO tcLicMstrId;
    private String aAppWRevRas;
    private String bAppASR;
    private String cAppCSR;
    private String dAppAccLOA;
    private String d1AppAccGPM;
    private String eAppOL;
    private String e1AppOLGM;
    private String existingApproach;
	private BigDecimal cat2Width;
	private String cat2irrv;

	
	public Long getLandCategoryID() {
		return landCategoryID;
	}

	public void setLandCategoryID(Long landCategoryID) {
		this.landCategoryID = landCategoryID;
	}

	public LicenseApplicationMasterDTO getTcLicMstrId() {
		return tcLicMstrId;
	}

	public void setTcLicMstrId(LicenseApplicationMasterDTO tcLicMstrId) {
		this.tcLicMstrId = tcLicMstrId;
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

	public String getExistingApproach() {
		return existingApproach;
	}

	public void setExistingApproach(String existingApproach) {
		this.existingApproach = existingApproach;
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
	
}
