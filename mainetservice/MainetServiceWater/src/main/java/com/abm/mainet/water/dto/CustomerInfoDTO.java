package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerInfoDTO implements Serializable {

	private static final long serialVersionUID = 7554462229058906937L;

	private long csIdn;

	private String csCcn;

	private Date csApldate;

	private String csOldccn;

	private Long pmPrmstid;

	private String csTitle;

	private String csName;

	private String csMname;

	private String csLname;

	private String csOrgName;

	private String csAdd;

	private String csFlatno;

	private String csBldplt;

	private String csLanear;

	private String csRdcross;

	private String csContactno;

	private String csOtitle;

	private String csOname;

	private String csOmname;

	private String csOlname;

	private String csOorgName;

	private String csOadd;

	private String csOflatno;

	private String csObldplt;

	private String csOlanear;

	private String csOrdcross;

	private String csOcontactno;

	private Long csHousetype;

	private String csCcntype;

	private Long csNoofusers;

	private Long csCcnsize;

	private String csRemark;

	private Long trdPremise;

	private Long csNooftaps;

	private Long csMeteredccn;

	private String pcFlg;

	private Date pcDate;

	private Long plumId;

	private Long csCcnstatus;

	private Date csFromdt;

	private Date csTodt;

	private int langId;

	private Date lmodDate;

	private Date updatedDate;

	private String csPremisedesc;

	private String csBbldplt;

	private String csBlanear;

	private String csBrdcross;

	private String csBadd;

	private String regno;

	private Long meterreader;

	private String ported;

	private String electoralWard;

	private Long csListatus;

	private Long codDwzid1;

	private Long codDwzid2;

	private Long codDwzid3;

	private Long codDwzid4;

	private Long codDwzid5;

	private String csPowner;

	private Long cpaCscid1;

	private Long cpaCscid2;

	private Long cpaCscid3;

	private Long cpaCscid4;

	private Long cpaCscid5;

	private Long cpaOcscid1;

	private Long cpaOcscid2;

	private Long cpaOcscid3;

	private Long cpaOcscid4;

	private Long cpaOcscid5;

	private Long cpaBcscid1;

	private Long cpaBcscid2;

	private Long cpaBcscid3;

	private Long cpaBcscid4;

	private Long cpaBcscid5;

	private Long trmGroup1;

	private Long trmGroup2;

	private Long trmGroup3;

	private Long trmGroup4;

	private Long trmGroup5;

	private Long csCcncategory1;

	private Long csCcncategory2;

	private Long csCcncategory3;

	private Long csCcncategory4;

	private Long csCcncategory5;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String wtV1;

	private String wtV2;

	private String wtV3;

	private String wtV4;

	private String wtV5;

	private Long csCfcWard;

	private Long wtN2;

	private Long wtN3;

	private Long wtN4;

	private Long wtN5;

	private Date wtD1;

	private Date wtD2;

	private Date wtD3;

	private String wtLo1;

	private String wtLo2;

	private String wtLo3;

	private String csTaxPayerFlag;

	private String csOldpropno;

	private Double csSeqno;

	private String csEntryFlag;

	private Double csOpenSecdepositAmt;

	private String csBulkEntryFlag;

	private String gisRef;

	private Long csUid;

	private Long applicationNo;

	private String typeOfApplication;

	private Date fromDate;

	private Date toDate;

	private String bplFlag;

	private String bplNo;

	private Long noOfFamilies;

	private Long applicantType;

	private String csIsBillingActive;

	public String getTypeOfApplication() {
		return typeOfApplication;
	}

	public void setTypeOfApplication(final String typeOfApplication) {
		this.typeOfApplication = typeOfApplication;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(final Date toDate) {
		this.toDate = toDate;
	}

	public String getBplFlag() {
		return bplFlag;
	}

	public void setBplFlag(final String bplFlag) {
		this.bplFlag = bplFlag;
	}

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(final String bplNo) {
		this.bplNo = bplNo;
	}

	public Long getNoOfFamilies() {
		return noOfFamilies;
	}

	public void setNoOfFamilies(final Long noOfFamilies) {
		this.noOfFamilies = noOfFamilies;
	}

	public Long getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(final Long applicantType) {
		this.applicantType = applicantType;
	}

	public String getCsIsBillingActive() {
		return csIsBillingActive;
	}

	public void setCsIsBillingActive(final String csIsBillingActive) {
		this.csIsBillingActive = csIsBillingActive;
	}

	@Transient
	private Integer ccnsize;

	@Transient
	private Integer size;

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(final Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	@JsonIgnore
	public long getId() {
		return csIdn;
	}

	public long getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final long csIdn) {
		this.csIdn = csIdn;
	}

	public String getCsCcn() {
		return csCcn;
	}

	public void setCsCcn(final String csCcn) {
		this.csCcn = csCcn;
	}

	public Date getCsApldate() {
		return csApldate;
	}

	public void setCsApldate(final Date csApldate) {
		this.csApldate = csApldate;
	}

	public String getCsOldccn() {
		return csOldccn;
	}

	public void setCsOldccn(final String csOldccn) {
		this.csOldccn = csOldccn;
	}

	public Long getPmPrmstid() {
		return pmPrmstid;
	}

	public void setPmPrmstid(final Long pmPrmstid) {
		this.pmPrmstid = pmPrmstid;
	}

	public String getCsTitle() {
		return csTitle;
	}

	public void setCsTitle(final String csTitle) {
		this.csTitle = csTitle;
	}

	public String getCsName() {
		return csName;
	}

	public void setCsName(final String csName) {
		this.csName = csName;
	}

	public String getCsMname() {
		return csMname;
	}

	public void setCsMname(final String csMname) {
		this.csMname = csMname;
	}

	public String getCsLname() {
		return csLname;
	}

	public void setCsLname(final String csLname) {
		this.csLname = csLname;
	}

	public String getCsOrgName() {
		return csOrgName;
	}

	public void setCsOrgName(final String csOrgName) {
		this.csOrgName = csOrgName;
	}

	public String getCsAdd() {
		return csAdd;
	}

	public void setCsAdd(final String csAdd) {
		this.csAdd = csAdd;
	}

	public String getCsFlatno() {
		return csFlatno;
	}

	public void setCsFlatno(final String csFlatno) {
		this.csFlatno = csFlatno;
	}

	public String getCsBldplt() {
		return csBldplt;
	}

	public void setCsBldplt(final String csBldplt) {
		this.csBldplt = csBldplt;
	}

	public String getCsLanear() {
		return csLanear;
	}

	public void setCsLanear(final String csLanear) {
		this.csLanear = csLanear;
	}

	public String getCsRdcross() {
		return csRdcross;
	}

	public void setCsRdcross(final String csRdcross) {
		this.csRdcross = csRdcross;
	}

	public String getCsContactno() {
		return csContactno;
	}

	public void setCsContactno(final String csContactno) {
		this.csContactno = csContactno;
	}

	public String getCsOtitle() {
		return csOtitle;
	}

	public void setCsOtitle(final String csOtitle) {
		this.csOtitle = csOtitle;
	}

	public String getCsOname() {
		return csOname;
	}

	public void setCsOname(final String csOname) {
		this.csOname = csOname;
	}

	public String getCsOmname() {
		return csOmname;
	}

	public void setCsOmname(final String csOmname) {
		this.csOmname = csOmname;
	}

	public String getCsOlname() {
		return csOlname;
	}

	public void setCsOlname(final String csOlname) {
		this.csOlname = csOlname;
	}

	public String getCsOorgName() {
		return csOorgName;
	}

	public void setCsOorgName(final String csOorgName) {
		this.csOorgName = csOorgName;
	}

	public String getCsOadd() {
		return csOadd;
	}

	public void setCsOadd(final String csOadd) {
		this.csOadd = csOadd;
	}

	public String getCsOflatno() {
		return csOflatno;
	}

	public void setCsOflatno(final String csOflatno) {
		this.csOflatno = csOflatno;
	}

	public String getCsObldplt() {
		return csObldplt;
	}

	public void setCsObldplt(final String csObldplt) {
		this.csObldplt = csObldplt;
	}

	public String getCsOlanear() {
		return csOlanear;
	}

	public void setCsOlanear(final String csOlanear) {
		this.csOlanear = csOlanear;
	}

	public String getCsOrdcross() {
		return csOrdcross;
	}

	public void setCsOrdcross(final String csOrdcross) {
		this.csOrdcross = csOrdcross;
	}

	public String getCsOcontactno() {
		return csOcontactno;
	}

	public void setCsOcontactno(final String csOcontactno) {
		this.csOcontactno = csOcontactno;
	}

	public Long getCsHousetype() {
		return csHousetype;
	}

	public void setCsHousetype(final Long csHousetype) {
		this.csHousetype = csHousetype;
	}

	public String getCsCcntype() {
		return csCcntype;
	}

	public void setCsCcntype(final String csCcntype) {
		this.csCcntype = csCcntype;
	}

	public Long getCsNoofusers() {
		return csNoofusers;
	}

	public void setCsNoofusers(final Long csNoofusers) {
		this.csNoofusers = csNoofusers;
	}

	public String getCsRemark() {
		return csRemark;
	}

	public void setCsRemark(final String csRemark) {
		this.csRemark = csRemark;
	}

	public Long getTrdPremise() {
		return trdPremise;
	}

	public void setTrdPremise(final Long trdPremise) {
		this.trdPremise = trdPremise;
	}

	public Long getCsNooftaps() {
		return csNooftaps;
	}

	public void setCsNooftaps(final Long csNooftaps) {
		this.csNooftaps = csNooftaps;
	}

	public Long getCsMeteredccn() {
		return csMeteredccn;
	}

	public void setCsMeteredccn(final Long csMeteredccn) {
		this.csMeteredccn = csMeteredccn;
	}

	public String getPcFlg() {
		return pcFlg;
	}

	public void setPcFlg(final String pcFlg) {
		this.pcFlg = pcFlg;
	}

	public Date getPcDate() {
		return pcDate;
	}

	public void setPcDate(final Date pcDate) {
		this.pcDate = pcDate;
	}

	public Long getPlumId() {
		return plumId;
	}

	public void setPlumId(final Long plumId) {
		this.plumId = plumId;
	}

	public Long getCsCcnstatus() {
		return csCcnstatus;
	}

	public void setCsCcnstatus(final Long csCcnstatus) {
		this.csCcnstatus = csCcnstatus;
	}

	public Date getCsFromdt() {
		return csFromdt;
	}

	public void setCsFromdt(final Date csFromdt) {
		this.csFromdt = csFromdt;
	}

	public Date getCsTodt() {
		return csTodt;
	}

	public void setCsTodt(final Date csTodt) {
		this.csTodt = csTodt;
	}

	public String getCsPremisedesc() {
		return csPremisedesc;
	}

	public void setCsPremisedesc(final String csPremisedesc) {
		this.csPremisedesc = csPremisedesc;
	}

	public String getCsBbldplt() {
		return csBbldplt;
	}

	public void setCsBbldplt(final String csBbldplt) {
		this.csBbldplt = csBbldplt;
	}

	public String getCsBlanear() {
		return csBlanear;
	}

	public void setCsBlanear(final String csBlanear) {
		this.csBlanear = csBlanear;
	}

	public String getCsBrdcross() {
		return csBrdcross;
	}

	public void setCsBrdcross(final String csBrdcross) {
		this.csBrdcross = csBrdcross;
	}

	public String getCsBadd() {
		return csBadd;
	}

	public void setCsBadd(final String csBadd) {
		this.csBadd = csBadd;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(final String regno) {
		this.regno = regno;
	}

	public Long getMeterreader() {
		return meterreader;
	}

	public void setMeterreader(final Long meterreader) {
		this.meterreader = meterreader;
	}

	public String getPorted() {
		return ported;
	}

	public void setPorted(final String ported) {
		this.ported = ported;
	}

	public String getElectoralWard() {
		return electoralWard;
	}

	public void setElectoralWard(final String electoralWard) {
		this.electoralWard = electoralWard;
	}

	public Long getCsListatus() {
		return csListatus;
	}

	public void setCsListatus(final Long csListatus) {
		this.csListatus = csListatus;
	}

	public Long getCodDwzid1() {
		return codDwzid1;
	}

	public void setCodDwzid1(final Long codDwzid1) {
		this.codDwzid1 = codDwzid1;
	}

	public Long getCodDwzid2() {
		return codDwzid2;
	}

	public void setCodDwzid2(final Long codDwzid2) {
		this.codDwzid2 = codDwzid2;
	}

	public Long getCodDwzid3() {
		return codDwzid3;
	}

	public void setCodDwzid3(final Long codDwzid3) {
		this.codDwzid3 = codDwzid3;
	}

	public Long getCodDwzid4() {
		return codDwzid4;
	}

	public void setCodDwzid4(final Long codDwzid4) {
		this.codDwzid4 = codDwzid4;
	}

	public Long getCodDwzid5() {
		return codDwzid5;
	}

	public void setCodDwzid5(final Long codDwzid5) {
		this.codDwzid5 = codDwzid5;
	}

	public String getCsPowner() {
		return csPowner;
	}

	public void setCsPowner(final String csPowner) {
		this.csPowner = csPowner;
	}

	public Long getCpaCscid1() {
		return cpaCscid1;
	}

	public void setCpaCscid1(final Long cpaCscid1) {
		this.cpaCscid1 = cpaCscid1;
	}

	public Long getCpaCscid2() {
		return cpaCscid2;
	}

	public void setCpaCscid2(final Long cpaCscid2) {
		this.cpaCscid2 = cpaCscid2;
	}

	public Long getCpaCscid3() {
		return cpaCscid3;
	}

	public void setCpaCscid3(final Long cpaCscid3) {
		this.cpaCscid3 = cpaCscid3;
	}

	public Long getCpaCscid4() {
		return cpaCscid4;
	}

	public void setCpaCscid4(final Long cpaCscid4) {
		this.cpaCscid4 = cpaCscid4;
	}

	public Long getCpaCscid5() {
		return cpaCscid5;
	}

	public void setCpaCscid5(final Long cpaCscid5) {
		this.cpaCscid5 = cpaCscid5;
	}

	public Long getCpaOcscid1() {
		return cpaOcscid1;
	}

	public void setCpaOcscid1(final Long cpaOcscid1) {
		this.cpaOcscid1 = cpaOcscid1;
	}

	public Long getCpaOcscid2() {
		return cpaOcscid2;
	}

	public void setCpaOcscid2(final Long cpaOcscid2) {
		this.cpaOcscid2 = cpaOcscid2;
	}

	public Long getCpaOcscid3() {
		return cpaOcscid3;
	}

	public void setCpaOcscid3(final Long cpaOcscid3) {
		this.cpaOcscid3 = cpaOcscid3;
	}

	public Long getCpaOcscid4() {
		return cpaOcscid4;
	}

	public void setCpaOcscid4(final Long cpaOcscid4) {
		this.cpaOcscid4 = cpaOcscid4;
	}

	public Long getCpaOcscid5() {
		return cpaOcscid5;
	}

	public void setCpaOcscid5(final Long cpaOcscid5) {
		this.cpaOcscid5 = cpaOcscid5;
	}

	public Long getCpaBcscid1() {
		return cpaBcscid1;
	}

	public void setCpaBcscid1(final Long cpaBcscid1) {
		this.cpaBcscid1 = cpaBcscid1;
	}

	public Long getCpaBcscid2() {
		return cpaBcscid2;
	}

	public void setCpaBcscid2(final Long cpaBcscid2) {
		this.cpaBcscid2 = cpaBcscid2;
	}

	public Long getCpaBcscid3() {
		return cpaBcscid3;
	}

	public void setCpaBcscid3(final Long cpaBcscid3) {
		this.cpaBcscid3 = cpaBcscid3;
	}

	public Long getCpaBcscid4() {
		return cpaBcscid4;
	}

	public void setCpaBcscid4(final Long cpaBcscid4) {
		this.cpaBcscid4 = cpaBcscid4;
	}

	public Long getCpaBcscid5() {
		return cpaBcscid5;
	}

	public void setCpaBcscid5(final Long cpaBcscid5) {
		this.cpaBcscid5 = cpaBcscid5;
	}

	public Long getTrmGroup1() {
		return trmGroup1;
	}

	public void setTrmGroup1(final Long trmGroup1) {
		this.trmGroup1 = trmGroup1;
	}

	public Long getTrmGroup2() {
		return trmGroup2;
	}

	public void setTrmGroup2(final Long trmGroup2) {
		this.trmGroup2 = trmGroup2;
	}

	public Long getTrmGroup3() {
		return trmGroup3;
	}

	public void setTrmGroup3(final Long trmGroup3) {
		this.trmGroup3 = trmGroup3;
	}

	public Long getTrmGroup4() {
		return trmGroup4;
	}

	public void setTrmGroup4(final Long trmGroup4) {
		this.trmGroup4 = trmGroup4;
	}

	public Long getTrmGroup5() {
		return trmGroup5;
	}

	public void setTrmGroup5(final Long trmGroup5) {
		this.trmGroup5 = trmGroup5;
	}

	public Long getCsCcncategory1() {
		return csCcncategory1;
	}

	public void setCsCcncategory1(final Long csCcncategory1) {
		this.csCcncategory1 = csCcncategory1;
	}

	public Long getCsCcncategory2() {
		return csCcncategory2;
	}

	public void setCsCcncategory2(final Long csCcncategory2) {
		this.csCcncategory2 = csCcncategory2;
	}

	public Long getCsCcncategory3() {
		return csCcncategory3;
	}

	public void setCsCcncategory3(final Long csCcncategory3) {
		this.csCcncategory3 = csCcncategory3;
	}

	public Long getCsCcncategory4() {
		return csCcncategory4;
	}

	public void setCsCcncategory4(final Long csCcncategory4) {
		this.csCcncategory4 = csCcncategory4;
	}

	public Long getCsCcncategory5() {
		return csCcncategory5;
	}

	public void setCsCcncategory5(final Long csCcncategory5) {
		this.csCcncategory5 = csCcncategory5;
	}

	public String getWtV1() {
		return wtV1;
	}

	public void setWtV1(final String wtV1) {
		this.wtV1 = wtV1;
	}

	public String getWtV2() {
		return wtV2;
	}

	public void setWtV2(final String wtV2) {
		this.wtV2 = wtV2;
	}

	public String getWtV3() {
		return wtV3;
	}

	public void setWtV3(final String wtV3) {
		this.wtV3 = wtV3;
	}

	public String getWtV4() {
		return wtV4;
	}

	public void setWtV4(final String wtV4) {
		this.wtV4 = wtV4;
	}

	public String getWtV5() {
		return wtV5;
	}

	public void setWtV5(final String wtV5) {
		this.wtV5 = wtV5;
	}

	public Long getCsCfcWard() {
		return csCfcWard;
	}

	public void setCsCfcWard(final Long csCfcWard) {
		this.csCfcWard = csCfcWard;
	}

	public Long getWtN2() {
		return wtN2;
	}

	public void setWtN2(final Long wtN2) {
		this.wtN2 = wtN2;
	}

	public Long getWtN3() {
		return wtN3;
	}

	public void setWtN3(final Long wtN3) {
		this.wtN3 = wtN3;
	}

	public Long getWtN4() {
		return wtN4;
	}

	public void setWtN4(final Long wtN4) {
		this.wtN4 = wtN4;
	}

	public Long getWtN5() {
		return wtN5;
	}

	public void setWtN5(final Long wtN5) {
		this.wtN5 = wtN5;
	}

	public Date getWtD1() {
		return wtD1;
	}

	public void setWtD1(final Date wtD1) {
		this.wtD1 = wtD1;
	}

	public Date getWtD2() {
		return wtD2;
	}

	public void setWtD2(final Date wtD2) {
		this.wtD2 = wtD2;
	}

	public Date getWtD3() {
		return wtD3;
	}

	public void setWtD3(final Date wtD3) {
		this.wtD3 = wtD3;
	}

	public String getWtLo1() {
		return wtLo1;
	}

	public void setWtLo1(final String wtLo1) {
		this.wtLo1 = wtLo1;
	}

	public String getWtLo2() {
		return wtLo2;
	}

	public void setWtLo2(final String wtLo2) {
		this.wtLo2 = wtLo2;
	}

	public String getWtLo3() {
		return wtLo3;
	}

	public void setWtLo3(final String wtLo3) {
		this.wtLo3 = wtLo3;
	}

	public String getCsOldpropno() {
		return csOldpropno;
	}

	public void setCsOldpropno(final String csOldpropno) {
		this.csOldpropno = csOldpropno;
	}

	public Double getCsSeqno() {
		return csSeqno;
	}

	public void setCsSeqno(final Double csSeqno) {
		this.csSeqno = csSeqno;
	}

	public String getCsEntryFlag() {
		return csEntryFlag;
	}

	public void setCsEntryFlag(final String csEntryFlag) {
		this.csEntryFlag = csEntryFlag;
	}

	public Double getCsOpenSecdepositAmt() {
		return csOpenSecdepositAmt;
	}

	public void setCsOpenSecdepositAmt(final Double csOpenSecdepositAmt) {
		this.csOpenSecdepositAmt = csOpenSecdepositAmt;
	}

	public String getCsBulkEntryFlag() {
		return csBulkEntryFlag;
	}

	public void setCsBulkEntryFlag(final String csBulkEntryFlag) {
		this.csBulkEntryFlag = csBulkEntryFlag;
	}

	public String getGisRef() {
		return gisRef;
	}

	public void setGisRef(final String gisRef) {
		this.gisRef = gisRef;
	}

	public Long getCsUid() {
		return csUid;
	}

	public void setCsUid(final Long csUid) {
		this.csUid = csUid;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(final Integer size) {
		this.size = size;
	}

	public Integer getCcnsize() {
		return ccnsize;
	}

	public void setCcnsize(final Integer ccnsize) {
		this.ccnsize = ccnsize;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(final int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	@JsonIgnore
	public String getConsumerName() {

		final String fullName = replaceNull(getCsName()) + MainetConstants.WHITE_SPACE + replaceNull(getCsMname())
				+ MainetConstants.WHITE_SPACE + replaceNull(getCsLname());

		return fullName.trim();

	}

	private String replaceNull(String name) {
		if (name == null) {

			name = MainetConstants.BLANK;
		}
		return name;
	}

	public String getCsTaxPayerFlag() {
		return csTaxPayerFlag;
	}

	public void setCsTaxPayerFlag(String csTaxPayerFlag) {
		this.csTaxPayerFlag = csTaxPayerFlag;
	}

	public Long getCsCcnsize() {
		return csCcnsize;
	}

	public void setCsCcnsize(Long csCcnsize) {
		this.csCcnsize = csCcnsize;
	}

}