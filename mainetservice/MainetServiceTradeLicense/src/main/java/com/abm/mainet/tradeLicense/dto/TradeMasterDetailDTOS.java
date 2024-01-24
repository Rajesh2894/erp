package com.abm.mainet.tradeLicense.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TradeMasterDetailDTOS{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6816027960395238997L;
	/**
	 * 
	 */
	@JsonIgnore
	private Long trdType;
	@JsonIgnore
	private Long trdLictype;
	private String trdLictypeDesc;
	private String trdBusnm;
	private String trdBusadd;
	private String trdOldlicno;
	private Date trdLicfromDate;
	private Date trdLictoDate;
	private Date trdLicisdate;
	private String trdWard1;
	private String trdWard2;
	private String trdWard3;
	private String trdWard4;
	private String trdWard5;
	@JsonIgnore
	private Long trdFtype;
	@JsonIgnore
	private String trdFarea;
	@JsonIgnore
	private String trdEty;
	private String pmPropNo;
	private String trdRationNo;
	private String trdRationAuth;
	private double totalOutsatandingAmt;
	private String usage;
	private String trdStatus;
	private String trdLicno;
	private String trdOwnerNm;
	private Long trdOthCatType;
	
	@JsonIgnore
	private String licenseType;
	@JsonIgnore
	private String licenseFromDate;
	@JsonIgnore
	private String licenseToDate;
	private Date agreementFromDate;
	private Date agreementToDate;
	private String trdFlatNo;
	private String gstNo;
	private List<TradeLicenseItemDetailDTOS> tradeLicenseItemDetailDTO = new ArrayList<TradeLicenseItemDetailDTOS>();
	private List<TradeLicenseOwnerDetailDTOS> tradeLicenseOwnerdetailDTO = new ArrayList<TradeLicenseOwnerDetailDTOS>();
	

	public String getTrdLictypeDesc() {
		return trdLictypeDesc;
	}

	public void setTrdLictypeDesc(String trdLictypeDesc) {
		this.trdLictypeDesc = trdLictypeDesc;
	}

	public Long getTrdType() {
		return trdType;
	}


	public void setTrdType(Long trdType) {
		this.trdType = trdType;
	}

	public Long getTrdLictype() {
		return trdLictype;
	}

	public void setTrdLictype(Long trdLictype) {
		this.trdLictype = trdLictype;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public Date getTrdLicfromDate() {
		return trdLicfromDate;
	}

	public void setTrdLicfromDate(Date trdLicfromDate) {
		this.trdLicfromDate = trdLicfromDate;
	}

	public Date getTrdLictoDate() {
		return trdLictoDate;
	}

	public void setTrdLictoDate(Date trdLictoDate) {
		this.trdLictoDate = trdLictoDate;
	}

	public Date getTrdLicisdate() {
		return trdLicisdate;
	}

	public void setTrdLicisdate(Date trdLicisdate) {
		this.trdLicisdate = trdLicisdate;
	}


	
	public Long getTrdFtype() {
		return trdFtype;
	}

	public void setTrdFtype(Long trdFtype) {
		this.trdFtype = trdFtype;
	}

	public String getTrdFarea() {
		return trdFarea;
	}

	public void setTrdFarea(String trdFarea) {
		this.trdFarea = trdFarea;
	}

	public String getTrdEty() {
		return trdEty;
	}

	public void setTrdEty(String trdEty) {
		this.trdEty = trdEty;
	}


	public List<TradeLicenseOwnerDetailDTOS> getTradeLicenseOwnerdetailDTO() {
		return tradeLicenseOwnerdetailDTO;
	}

	public void setTradeLicenseOwnerdetailDTO(List<TradeLicenseOwnerDetailDTOS> tradeLicenseOwnerdetailDTO) {
		this.tradeLicenseOwnerdetailDTO = tradeLicenseOwnerdetailDTO;
	}

	public double getTotalOutsatandingAmt() {
		return totalOutsatandingAmt;
	}

	public void setTotalOutsatandingAmt(double totalOutsatandingAmt) {
		this.totalOutsatandingAmt = totalOutsatandingAmt;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public String getTrdStatus() {
		return trdStatus;
	}

	public void setTrdStatus(String trdStatus) {
		this.trdStatus = trdStatus;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public String getPmPropNo() {
		return pmPropNo;
	}

	public void setPmPropNo(String pmPropNo) {
		this.pmPropNo = pmPropNo;
	}

	public String getTrdOwnerNm() {
		return trdOwnerNm;
	}

	public void setTrdOwnerNm(String trdOwnerNm) {
		this.trdOwnerNm = trdOwnerNm;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseFromDate() {
		return licenseFromDate;
	}

	public void setLicenseFromDate(String licenseFromDate) {
		this.licenseFromDate = licenseFromDate;
	}

	public String getLicenseToDate() {
		return licenseToDate;
	}

	public void setLicenseToDate(String licenseToDate) {
		this.licenseToDate = licenseToDate;
	}

	
	public String getTrdFlatNo() {
		return trdFlatNo;
	}

	public void setTrdFlatNo(String trdFlatNo) {
		this.trdFlatNo = trdFlatNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public Date getAgreementFromDate() {
		return agreementFromDate;
	}

	public void setAgreementFromDate(Date agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}

	public Date getAgreementToDate() {
		return agreementToDate;
	}

	public void setAgreementToDate(Date agreementToDate) {
		this.agreementToDate = agreementToDate;
	}


	public String getTrdRationNo() {
		return trdRationNo;
	}

	public void setTrdRationNo(String trdRationNo) {
		this.trdRationNo = trdRationNo;
	}

	public String getTrdRationAuth() {
		return trdRationAuth;
	}

	public void setTrdRationAuth(String trdRationAuth) {
		this.trdRationAuth = trdRationAuth;
	}

	public Long getTrdOthCatType() {
		return trdOthCatType;
	}

	public void setTrdOthCatType(Long trdOthCatType) {
		this.trdOthCatType = trdOthCatType;
	}

	public List<TradeLicenseItemDetailDTOS> getTradeLicenseItemDetailDTO() {
		return tradeLicenseItemDetailDTO;
	}

	public void setTradeLicenseItemDetailDTO(List<TradeLicenseItemDetailDTOS> tradeLicenseItemDetailDTO) {
		this.tradeLicenseItemDetailDTO = tradeLicenseItemDetailDTO;
	}

	public String getTrdWard1() {
		return trdWard1;
	}

	public void setTrdWard1(String trdWard1) {
		this.trdWard1 = trdWard1;
	}

	public String getTrdWard2() {
		return trdWard2;
	}

	public void setTrdWard2(String trdWard2) {
		this.trdWard2 = trdWard2;
	}

	public String getTrdWard3() {
		return trdWard3;
	}

	public void setTrdWard3(String trdWard3) {
		this.trdWard3 = trdWard3;
	}

	public String getTrdWard4() {
		return trdWard4;
	}

	public void setTrdWard4(String trdWard4) {
		this.trdWard4 = trdWard4;
	}

	public String getTrdWard5() {
		return trdWard5;
	}

	public void setTrdWard5(String trdWard5) {
		this.trdWard5 = trdWard5;
	}

	


}
