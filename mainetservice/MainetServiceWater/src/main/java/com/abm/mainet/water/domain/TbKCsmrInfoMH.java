package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jugnu.pandey
 * @since 01 Feb 2016
 * @comment This table stores Consumer Information.
 */
@Entity
@Table(name = "TB_CSMR_INFO")
public class TbKCsmrInfoMH implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4393152421616898363L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CS_IDN", nullable = false)
    private long csIdn;

    @Column(name = "CS_CCN", length = 20, nullable = true)
    private String csCcn;

    @Column(name = "CS_APLDATE", nullable = true)
    private Date csApldate;

    @Column(name = "CS_OLDCCN", length = 30, nullable = true)
    private String csOldccn;

    @Column(name = "PM_PROP_NO", length = 20, nullable = true)
    private String propertyNo;
    
    @Column(name = "PM_FLAT_NO ", length = 30, nullable = true)
    private String flatNo;

    @Column(name = "CS_TITLE", length = 15, nullable = true)
    private String csTitle;

    @Column(name = "CS_NAME", length = 600, nullable = true)
    private String csName;

    @Column(name = "CS_MNAME", length = 600, nullable = true)
    private String csMname;

    @Column(name = "CS_LNAME", length = 600, nullable = true)
    private String csLname;

    @Column(name = "CS_CCITY_NAME", length = 100, nullable = true)
    private String csCcityName;

    @Column(name = "CS_ADD", length = 1000, nullable = true)
    private String csAdd;

    @Column(name = "CS_FLATNO", length = 10, nullable = true)
    private String csFlatno;

    @Column(name = "CS_BLDPLT", length = 300, nullable = true)
    private String csBldplt;

    @Column(name = "CS_LANEAR", length = 100, nullable = true)
    private String csLanear;

    @Column(name = "CS_RDCROSS", length = 200, nullable = true)
    private String csRdcross;

    @Column(name = "CS_CONTACTNO", length = 50, nullable = true)
    private String csContactno;

    @Column(name = "CS_OTITLE", length = 15, nullable = true)
    private String csOtitle;

    @Column(name = "CS_OGENDER", length = 10, nullable = true)
    private Long csOGender;

    @Column(name = "CS_ONAME", length = 600, nullable = true)
    private String csOname;

    @Column(name = "CS_OMNAME", length = 600, nullable = true)
    private String csOmname;

    @Column(name = "CS_OLNAME", length = 600, nullable = true)
    private String csOlname;

    @Column(name = "CS_OCITY_NAME", length = 100, nullable = true)
    private String csOcityName;

    @Column(name = "CS_OADD", length = 1000, nullable = true)
    private String csOadd;

    @Column(name = "CS_OFLATNO", length = 10, nullable = true)
    private String csOflatno;

    @Column(name = "CS_OBLDPLT", length = 1000, nullable = true)
    private String csObldplt;

    @Column(name = "CS_OLANEAR", length = 1000, nullable = true)
    private String csOlanear;

    @Column(name = "CS_ORDCROSS", length = 1000, nullable = true)
    private String csOrdcross;

    @Column(name = "CS_OCONTACTNO", length = 50, nullable = true)
    private String csOcontactno;

    @Column(name = "CS_CPINCODE", precision = 12, scale = 0, nullable = true)
    private Long csCpinCode;

    @Column(name = "CS_CCNTYPE", length = 1, nullable = true)
    private String csCcntype;

    @Column(name = "CS_NOOFUSERS", precision = 5, scale = 0, nullable = true)
    private Long csNoofusers;

    @Column(name = "CS_CCNSIZE", precision = 10, scale = 0, nullable = true)
    private Long csCcnsize;

    @Column(name = "CS_REMARK", length = 200, nullable = true)
    private String csRemark;

    @Column(name = "TRD_PREMISE", precision = 12, scale = 0, nullable = true)
    private Long trdPremise;

    @Column(name = "CS_NOOFTAPS", precision = 12, scale = 0, nullable = true)
    private Long csNooftaps;

    @Column(name = "CS_METEREDCCN", precision = 10, scale = 0, nullable = true)
    private Long csMeteredccn;

    @Column(name = "PC_FLG", length = 1, nullable = true)
    private String pcFlg;

    @Column(name = "PC_DATE", nullable = true)
    private Date pcDate;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    private Long plumId;

    @Column(name = "CS_CCNSTATUS", length = 12, nullable = true)
    private Long csCcnstatus;

    @Column(name = "CS_FROMDT", nullable = true)
    private Date csFromdt;

    @Column(name = "CS_TODT", nullable = true)
    private Date csTodt;
    
    @Column(name = "CS_DISTRICT", nullable = true)
    private Long csDistrict;

    @JsonIgnore
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "Created_By", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "Created_date", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "CS_PREMISEDESC", length = 250, nullable = true)
    private String csPremisedesc;

    @Column(name = "CS_BBLDPLT", length = 150, nullable = true)
    private String csBbldplt;

    @Column(name = "CS_BLANEAR", length = 50, nullable = true)
    private String csBlanear;

    @Column(name = "CS_BRDCROSS", length = 100, nullable = true)
    private String csBrdcross;

    @Column(name = "CS_BADD", length = 1000, nullable = true)
    private String csBadd;

    @Column(name = "REGNO", length = 50, nullable = true)
    private String regno;

    @Column(name = "METERREADER", precision = 12, scale = 0, nullable = true)
    private Long meterreader;

    @Column(name = "PORTED", length = 1, nullable = true)
    private String ported;

    @Column(name = "ELECTORAL_WARD", length = 5, nullable = true)
    private String electoralWard;

    @Column(name = "CS_LISTATUS", precision = 12, scale = 0, nullable = true)
    private Long csListatus;

    @Column(name = "COD_DWZID1", precision = 12, scale = 0, nullable = true)
    private Long codDwzid1;

    @Column(name = "COD_DWZID2", precision = 12, scale = 0, nullable = true)
    private Long codDwzid2;

    @Column(name = "COD_DWZID3", precision = 12, scale = 0, nullable = true)
    private Long codDwzid3;

    @Column(name = "COD_DWZID4", precision = 12, scale = 0, nullable = true)
    private Long codDwzid4;

    @Column(name = "COD_DWZID5", precision = 12, scale = 0, nullable = true)
    private Long codDwzid5;

    @Column(name = "CS_POWNER", length = 1, nullable = true)
    private String csPowner;

    @Column(name = "CPA_CSCID1", precision = 12, scale = 0, nullable = true)
    private Long cpaCscid1;

    @Column(name = "CPA_CSCID2", precision = 12, scale = 0, nullable = true)
    private Long cpaCscid2;

    @Column(name = "CPA_CSCID3", precision = 12, scale = 0, nullable = true)
    private Long cpaCscid3;

    @Column(name = "CPA_CSCID4", precision = 12, scale = 0, nullable = true)
    private Long cpaCscid4;

    @Column(name = "CPA_CSCID5", precision = 12, scale = 0, nullable = true)
    private Long cpaCscid5;

    @Column(name = "CPA_OCSCID1", precision = 12, scale = 0, nullable = true)
    private Long cpaOcscid1;

    @Column(name = "CPA_OCSCID2", precision = 12, scale = 0, nullable = true)
    private Long cpaOcscid2;

    @Column(name = "CPA_OCSCID3", precision = 12, scale = 0, nullable = true)
    private Long cpaOcscid3;

    @Column(name = "CPA_OCSCID4", precision = 12, scale = 0, nullable = true)
    private Long cpaOcscid4;

    @Column(name = "CPA_OCSCID5", precision = 12, scale = 0, nullable = true)
    private Long cpaOcscid5;

    @Column(name = "CPA_BCSCID1", precision = 12, scale = 0, nullable = true)
    private Long cpaBcscid1;

    @Column(name = "CPA_BCSCID2", precision = 12, scale = 0, nullable = true)
    private Long cpaBcscid2;

    @Column(name = "CPA_BCSCID3", precision = 12, scale = 0, nullable = true)
    private Long cpaBcscid3;

    @Column(name = "CPA_BCSCID4", precision = 12, scale = 0, nullable = true)
    private Long cpaBcscid4;

    @Column(name = "CPA_BCSCID5", precision = 12, scale = 0, nullable = true)
    private Long cpaBcscid5;

    @Column(name = "TRM_GROUP1", precision = 12, scale = 0, nullable = true)
    private Long trmGroup1;

    @Column(name = "TRM_GROUP2", precision = 12, scale = 0, nullable = true)
    private Long trmGroup2;

    @Column(name = "TRM_GROUP3", precision = 12, scale = 0, nullable = true)
    private Long trmGroup3;

    @Column(name = "TRM_GROUP4", precision = 12, scale = 0, nullable = true)
    private Long trmGroup4;

    @Column(name = "TRM_GROUP5", precision = 12, scale = 0, nullable = true)
    private Long trmGroup5;

    @Column(name = "CS_CCNCATEGORY1", precision = 12, scale = 0, nullable = true)
    private Long csCcncategory1;

    @Column(name = "CS_CCNCATEGORY2", precision = 12, scale = 0, nullable = true)
    private Long csCcncategory2;

    @Column(name = "CS_CCNCATEGORY3", precision = 12, scale = 0, nullable = true)
    private Long csCcncategory3;

    @Column(name = "CS_CCNCATEGORY4", precision = 12, scale = 0, nullable = true)
    private Long csCcncategory4;

    @Column(name = "CS_CCNCATEGORY5", precision = 12, scale = 0, nullable = true)
    private Long csCcncategory5;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    /*
     * @Column(name = "WT_V1", length = 100, nullable = true) private String wtV1;
     */

    @Column(name = "CS_CFC_WARD", precision = 15, scale = 0, nullable = true)
    private Long csCfcWard;

    @Column(name = "CS_ANNUALRENT", precision = 15, scale = 2, nullable = true)
    private Double annualRent;

    @Column(name = "CS_ILLEGAL_CDATE", nullable = true)
    private Date csIllegalDate;

    @Column(name = "CS_TAXPAYER", length = 1, nullable = true)
    private String csTaxPayerFlag;

    @Column(name = "CS_OLDPROPNO", length = 30, nullable = true)
    private String csOldpropno;

    @Column(name = "CS_SEQNO", precision = 12, scale = 2, nullable = true)
    private Double csSeqno;

    @Column(name = "CS_ENTRY_FLAG", length = 1, nullable = true)
    private String csEntryFlag;

    @Column(name = "CS_OPEN_SECDEPOSIT_AMT", precision = 12, scale = 2, nullable = true)
    private Double csOpenSecdepositAmt;

    @Column(name = "CS_BULK_ENTRY_FLAG", length = 1, nullable = true)
    private String csBulkEntryFlag;

    @Column(name = "GIS_REF", length = 20, nullable = true)
    private String gisRef;

    @Column(name = "CS_UID", precision = 12, scale = 0, nullable = true)
    private Long csUid;

    @Column(name = "CS_TAXPAYER_PANNO", precision = 10, scale = 0, nullable = true)
    private String csPanNo;

    @Column(name = "APM_APPLICATION_ID", nullable = true)
    private Long applicationNo;

    @Column(name = "CS_P_T_FLAG", length = 1, nullable = true)
    private String typeOfApplication;

    @Column(name = "T_FROM_DATE", nullable = true)
    private Date fromDate;

    @Column(name = "T_TO_DATE", nullable = true)
    private Date toDate;

    @Column(name = "BPL_FLAG", length = 1, nullable = true)
    private String bplFlag;

    @Column(name = "BPL_NO", length = 50, nullable = true)
    private String bplNo;

    @Column(name = "CS_NO_OF_FAMILIES", precision = 5, scale = 0, nullable = true)
    private Long noOfFamilies;

    @Column(name = "CS_CPD_APT", precision = 10, scale = 0, nullable = true)
    private Long applicantType;

    @Column(name = "CS_IS_BILLING_ACTIVE", length = 1, nullable = true)
    private String csIsBillingActive;

    @Column(name = "CS_BCITY_NAME", length = 100, nullable = true)
    private String csBcityName;

    @Column(name = "CS_SEWARAGE_ID", length = 100, nullable = true)
    private String csSewerageId;

    @Column(name = "CS_REASON", length = 100, nullable = true)
    private String csReason;

    @Column(name = "CS_SERVICE_CHARGE", nullable = true)
    private Double csServiceCharge;

    @Column(name = "CS_EMAIL", length = 50, nullable = true)
    private String csEmail;

    @Column(name = "CS_ESWATERREQ", nullable = true)
    private Double waterRequirement;

    @Column(name = "CS_DISCAP", nullable = true)
    private Double dischargeCapacity;

    @Column(name = "LOC_ID", nullable = true)
    private Long loccationId;

    @Column(name = "CS_PINCODE", length = 50, nullable = true)
    private String opincode;

    @Column(name = "CS_BPINCODE", length = 50, nullable = true)
    private String bpincode;

    @Column(name = "CS_DEPOSIT_DATE", length = 50, nullable = true)
    private Date depositDate;

    @Column(name = "CS_DEPOSIT_AMOUNT", length = 50, nullable = true)
    private Double depositAmount;

    @Column(name = "CS_RECEIPT_NUMBER", length = 50, nullable = true)
    private String receiptNumber;

    @Column(name = "CS_DMLNM", length = 100, nullable = true)
    private String distributionMainLineName;

    @Column(name = "CS_DMLNU", length = 50, nullable = true)
    private String distributionMainLineNumber;

    @Column(name = "CS_DCLNM", length = 100, nullable = true)
    private String distributionChildLineName;

    @Column(name = "CS_DCLNU", length = 50, nullable = true)
    private String distributionChildLineNumber;

    @Column(name = "CS_PTYPE", length = 1, nullable = true)
    private String csPtype;

    @Column(name = "CS_ILLEGAL_NOTICE_NO", length = 20, nullable = true)
    private String csIllegalNoticeNo;

    @Column(name = "CS_ILLEGAL_NOTICE_DATE", nullable = true)
    private Date csIllegalNoticeDate;

    @Column(name = "PM_PROP_USG_TYP", length = 100, nullable = true)
    private String propertyUsageType;

    @Column(name = "ARV", length = 12, nullable = true)
    private Double arv;

    @Column(name = "PTIN", length = 20, nullable = true)
    private String ptin;

    @Column(name = "con_active", length = 1, nullable = true)
    private String conActive;

    @Column(name = "cs_no_of_flts", length = 5, nullable = true)
    private Long noOfFlats;

    @Column(name = "cs_no_of_fmls", length = 5, nullable = true)
    private Long noOffmls;

    @Column(name = "cs_no_of_mmbrs", length = 5, nullable = true)
    private Long noOfmembrs;

    @Column(name = "MOBILENO_OTP", length = 10, nullable = true)
    private String mobileNoOTP;
    
    @Column(name = "HOUSE_NUMBER", length = 30, nullable = true)
    private String houseNumber;
    
    @Column(name = "CS_SOURCE_LINE", length = 12, nullable = true)
    private String csSourceLine ;
    
    @Column(name = "HOLE_MAN", length = 200, nullable = true)
    private String holeMan;
    
    @Column(name = "FATHER_GUARDIAN_NAME", length = 300, nullable = true)
    private String fatherName;
    
    @Column(name = "LANDMARK", length = 300, nullable = true)
    private String landmark;
    
    @Column(name = "HOUSE_NUMBER_BILLING", length = 30, nullable = true)
    private String houseNumberBilling;
    
    @Column(name = "FATHER_GUARDIAN_NAME_BILLING", length = 300, nullable = true)
    private String fatherNameBilling;
    
    @Column(name = "LANDMARK_BILLING", length = 300, nullable = true)
    private String landmarkBilling;
    
    @Column(name = "MOBILE_BILLING", length = 15, nullable = true)
    private String contactNoBilling;
    
    @Column(name = "EMAIL_BILLING", length = 300, nullable = true)
    private String emailBilling;
    
    @Column(name = "AADHAR_BILLING", length = 30, nullable = true)
    private String aadharBilling;
    
    @Column(name = "DISTRICT_BILLING", nullable = true)
    private Long districtBilling;
    
    @Column(name = "COB_DWZID1", precision = 12, scale = 0, nullable = true)
    private Long coBDwzid1;

    @Column(name = "COB_DWZID2", precision = 12, scale = 0, nullable = true)
    private Long coBDwzid2;

    @Column(name = "COB_DWZID3", precision = 12, scale = 0, nullable = true)
    private Long coBDwzid3;

    @Column(name = "COB_DWZID4", precision = 12, scale = 0, nullable = true)
    private Long coBDwzid4;

    @Column(name = "COB_DWZID5", precision = 12, scale = 0, nullable = true)
    private Long coBDwzid5;
    
    @Column(name = "con_relation_id", precision = 12, scale = 0, nullable = true)
    private Long conRelationId;
    
    @Column(name = "no_of_toilet_seats", precision = 12, scale = 0, nullable = true)
    private Long noOfToiletSeats;
    
    @Column(name = "floor_no")
    private Long floorNo;
    
    @Column(name = "built_up_area")
    private Double builtUpArea;
    
    @Column(name = "noc_appl")
    private String nocAppl;
    
    @Column(name = "estimate_charge")
    private Double estimateCharge;
        
    @Column(name = "con_relation_name")
    private String conRelationName;
    
    @Column(name = "CS_GENDER", length = 10, nullable = true)
    private Long csGender;
    
    public Long getNoOfFlats() {
        return noOfFlats;
    }

    public void setNoOfFlats(Long noOfFlats) {
        this.noOfFlats = noOfFlats;
    }

    public Long getNoOffmls() {
        return noOffmls;
    }

    public void setNoOffmls(Long noOffmls) {
        this.noOffmls = noOffmls;
    }

    public Long getNoOfmembrs() {
        return noOfmembrs;
    }

    public void setNoOfmembrs(Long noOfmembrs) {
        this.noOfmembrs = noOfmembrs;
    }

    public Date getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Transient
    private Integer size;

    @Transient
    private Integer ccnsize;

    @Transient
    private Long processSessionId;

    /**
     * @return the applicantType
     */
    public Long getApplicantType() {
        return applicantType;
    }

    /**
     * @param applicantType the applicantType to set
     */
    public void setApplicantType(final Long applicantType) {
        this.applicantType = applicantType;
    }

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "csIdn", cascade = CascadeType.ALL)
	private List<AdditionalOwnerInfo> ownerList = new ArrayList<>(0);

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //  mappedBy = "csIdn",
	@JoinColumn(name = "CS_IDN" , referencedColumnName = "CS_IDN") //, nullable = false)
	private TBCsmrrCmdMas distribution;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crtCsIdn", cascade = CascadeType.ALL)
	private List<TbWtCsmrRoadTypes> roadList = new ArrayList<>(0);

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csIdn", cascade = CascadeType.ALL)
	@Where(clause = "IS_DELETED='N'")
	private List<TbKLinkCcn> linkDetails = new ArrayList<>(0);

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

    public Long getCsCcnsize() {
        return csCcnsize;
    }

    public void setCsCcnsize(final Long csCcnsize) {
        this.csCcnsize = csCcnsize;
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

    /*
     * public String getWtV1() { return wtV1; } public void setWtV1(final String wtV1) { this.wtV1 = wtV1; }
     */

    public Long getCsCfcWard() {
        return csCfcWard;
    }

    public void setCsCfcWard(final Long csCfcWard) {
        this.csCfcWard = csCfcWard;
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

    public String getCsPanNo() {
        return csPanNo;
    }

    public void setCsPanNo(String csPanNo) {
        this.csPanNo = csPanNo;
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

    public String[] getPkValues() {
        return new String[] { "WT", "TB_CSMR_INFO", "CS_IDN" };
        /*
         * return new String[] { "WT", "TB_CSMR_INFO", "CS_IDN", Long.toString(this.getOrgId()),"C",null };
         */
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
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

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(final Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public List<AdditionalOwnerInfo> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(final List<AdditionalOwnerInfo> ownerList) {
        this.ownerList = ownerList;
    }

    /**
     * @return the distribution
     */
    public TBCsmrrCmdMas getDistribution() {
        return distribution;
    }

    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(final TBCsmrrCmdMas distribution) {
        this.distribution = distribution;
    }

    /**
     * @return the roadList
     */
    public List<TbWtCsmrRoadTypes> getRoadList() {
        return roadList;
    }

    /**
     * @param roadList the roadList to set
     */
    public void setRoadList(final List<TbWtCsmrRoadTypes> roadList) {
        this.roadList = roadList;
    }

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

    public List<TbKLinkCcn> getLinkDetails() {
        return linkDetails;
    }

    public void setLinkDetails(final List<TbKLinkCcn> linkDetails) {
        this.linkDetails = linkDetails;
    }

    public Long getNoOfFamilies() {
        return noOfFamilies;
    }

    public void setNoOfFamilies(final Long noOfFamilies) {
        this.noOfFamilies = noOfFamilies;
    }

    public Long getCsOGender() {
        return csOGender;
    }

    public void setCsOGender(final Long csOGender) {
        this.csOGender = csOGender;
    }

    /**
     * @return the csIsBillingActive
     */
    public String getCsIsBillingActive() {
        return csIsBillingActive;
    }

    /**
     * @param csIsBillingActive the csIsBillingActive to set
     */
    public void setCsIsBillingActive(final String csIsBillingActive) {
        this.csIsBillingActive = csIsBillingActive;
    }

    /**
     * @return the csBcityName
     */
    public String getCsBcityName() {
        return csBcityName;
    }

    /**
     * @param csBcityName the csBcityName to set
     */
    public void setCsBcityName(final String csBcityName) {
        this.csBcityName = csBcityName;
    }

    public Long getProcessSessionId() {
        return processSessionId;
    }

    public void setProcessSessionId(final Long processSessionId) {
        this.processSessionId = processSessionId;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getCsSewerageId() {
        return csSewerageId;
    }

    public void setCsSewerageId(String csSewerageId) {
        this.csSewerageId = csSewerageId;
    }

    public String getCsReason() {
        return csReason;
    }

    public void setCsReason(String csReason) {
        this.csReason = csReason;
    }

    public Double getCsServiceCharge() {
        return csServiceCharge;
    }

    public void setCsServiceCharge(Double csServiceCharge) {
        this.csServiceCharge = csServiceCharge;
    }

    public String getCsEmail() {
        return csEmail;
    }

    public void setCsEmail(String csEmail) {
        this.csEmail = csEmail;
    }

    public Double getWaterRequirement() {
        return waterRequirement;
    }

    public void setWaterRequirement(Double waterRequirement) {
        this.waterRequirement = waterRequirement;
    }

    public Double getDischargeCapacity() {
        return dischargeCapacity;
    }

    public void setDischargeCapacity(Double dischargeCapacity) {
        this.dischargeCapacity = dischargeCapacity;
    }

    public Long getLoccationId() {
        return loccationId;
    }

    public void setLoccationId(Long loccationId) {
        this.loccationId = loccationId;
    }

    public String getOpincode() {
        return opincode;
    }

    public void setOpincode(String opincode) {
        this.opincode = opincode;
    }

    public String getBpincode() {
        return bpincode;
    }

    public void setBpincode(String bpincode) {
        this.bpincode = bpincode;
    }

    public String getCsCcityName() {
        return csCcityName;
    }

    public void setCsCcityName(String csCcityName) {
        this.csCcityName = csCcityName;
    }

    public String getCsOcityName() {
        return csOcityName;
    }

    public void setCsOcityName(String csOcityName) {
        this.csOcityName = csOcityName;
    }

    public Long getCsCpinCode() {
        return csCpinCode;
    }

    public void setCsCpinCode(Long csCpinCode) {
        this.csCpinCode = csCpinCode;
    }

    public Double getAnnualRent() {
        return annualRent;
    }

    public void setAnnualRent(Double annualRent) {
        this.annualRent = annualRent;
    }

    public String getCsTaxPayerFlag() {
        return csTaxPayerFlag;
    }

    public void setCsTaxPayerFlag(String csTaxPayerFlag) {
        this.csTaxPayerFlag = csTaxPayerFlag;
    }

    public String getDistributionMainLineName() {
        return distributionMainLineName;
    }

    public void setDistributionMainLineName(String distributionMainLineName) {
        this.distributionMainLineName = distributionMainLineName;
    }

    public String getDistributionMainLineNumber() {
        return distributionMainLineNumber;
    }

    public void setDistributionMainLineNumber(String distributionMainLineNumber) {
        this.distributionMainLineNumber = distributionMainLineNumber;
    }

    public String getDistributionChildLineName() {
        return distributionChildLineName;
    }

    public void setDistributionChildLineName(String distributionChildLineName) {
        this.distributionChildLineName = distributionChildLineName;
    }

    public String getDistributionChildLineNumber() {
        return distributionChildLineNumber;
    }

    public void setDistributionChildLineNumber(String distributionChildLineNumber) {
        this.distributionChildLineNumber = distributionChildLineNumber;
    }

    public String getCsPtype() {
        return csPtype;
    }

    public void setCsPtype(String csPtype) {
        this.csPtype = csPtype;
    }

    public Date getCsIllegalDate() {
        return csIllegalDate;
    }

    public void setCsIllegalDate(Date csIllegalDate) {
        this.csIllegalDate = csIllegalDate;
    }

    public String getCsIllegalNoticeNo() {
        return csIllegalNoticeNo;
    }

    public void setCsIllegalNoticeNo(String csIllegalNoticeNo) {
        this.csIllegalNoticeNo = csIllegalNoticeNo;
    }

    public Date getCsIllegalNoticeDate() {
        return csIllegalNoticeDate;
    }

    public void setCsIllegalNoticeDate(Date csIllegalNoticeDate) {
        this.csIllegalNoticeDate = csIllegalNoticeDate;
    }

    public String getPropertyUsageType() {
        return propertyUsageType;
    }

    public void setPropertyUsageType(String propertyUsageType) {
        this.propertyUsageType = propertyUsageType;
    }

    public Double getArv() {
        return arv;
    }

    public void setArv(Double arv) {
        this.arv = arv;
    }

    public String getPtin() {
        return ptin;
    }

    public void setPtin(String ptin) {
        this.ptin = ptin;
    }

    public String getConActive() {
        return conActive;
    }

    public void setConActive(String conActive) {
        this.conActive = conActive;
    }

    public String getMobileNoOTP() {
        return mobileNoOTP;
    }

    public void setMobileNoOTP(String mobileNoOTP) {
        this.mobileNoOTP = mobileNoOTP;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCsSourceLine() {
		return csSourceLine;
	}

	public void setCsSourceLine(String csSourceLine) {
		this.csSourceLine = csSourceLine;
	}

	public String getHoleMan() {
		return holeMan;
	}

	public void setHoleMan(String holeMan) {
		this.holeMan = holeMan;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getHouseNumberBilling() {
		return houseNumberBilling;
	}

	public void setHouseNumberBilling(String houseNumberBilling) {
		this.houseNumberBilling = houseNumberBilling;
	}

	public String getFatherNameBilling() {
		return fatherNameBilling;
	}

	public void setFatherNameBilling(String fatherNameBilling) {
		this.fatherNameBilling = fatherNameBilling;
	}

	public String getLandmarkBilling() {
		return landmarkBilling;
	}

	public void setLandmarkBilling(String landmarkBilling) {
		this.landmarkBilling = landmarkBilling;
	}

	public String getContactNoBilling() {
		return contactNoBilling;
	}

	public void setContactNoBilling(String contactNoBilling) {
		this.contactNoBilling = contactNoBilling;
	}

	public String getEmailBilling() {
		return emailBilling;
	}

	public void setEmailBilling(String emailBilling) {
		this.emailBilling = emailBilling;
	}

	public String getAadharBilling() {
		return aadharBilling;
	}

	public void setAadharBilling(String aadharBilling) {
		this.aadharBilling = aadharBilling;
	}

	public Long getCsDistrict() {
		return csDistrict;
	}

	public void setCsDistrict(Long csDistrict) {
		this.csDistrict = csDistrict;
	}

	public Long getDistrictBilling() {
		return districtBilling;
	}

	public void setDistrictBilling(Long districtBilling) {
		this.districtBilling = districtBilling;
	}

	public Long getCoBDwzid1() {
		return coBDwzid1;
	}

	public void setCoBDwzid1(Long coBDwzid1) {
		this.coBDwzid1 = coBDwzid1;
	}

	public Long getCoBDwzid2() {
		return coBDwzid2;
	}

	public void setCoBDwzid2(Long coBDwzid2) {
		this.coBDwzid2 = coBDwzid2;
	}

	public Long getCoBDwzid3() {
		return coBDwzid3;
	}

	public void setCoBDwzid3(Long coBDwzid3) {
		this.coBDwzid3 = coBDwzid3;
	}

	public Long getCoBDwzid4() {
		return coBDwzid4;
	}

	public void setCoBDwzid4(Long coBDwzid4) {
		this.coBDwzid4 = coBDwzid4;
	}

	public Long getCoBDwzid5() {
		return coBDwzid5;
	}

	public void setCoBDwzid5(Long coBDwzid5) {
		this.coBDwzid5 = coBDwzid5;
	}

	public Long getConRelationId() {
		return conRelationId;
	}

	public void setConRelationId(Long conRelationId) {
		this.conRelationId = conRelationId;
	}

	public Long getNoOfToiletSeats() {
		return noOfToiletSeats;
	}

	public void setNoOfToiletSeats(Long noOfToiletSeats) {
		this.noOfToiletSeats = noOfToiletSeats;
	}

	public Long getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(Long floorNo) {
		this.floorNo = floorNo;
	}

	public Double getBuiltUpArea() {
		return builtUpArea;
	}

	public void setBuiltUpArea(Double builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public String getNocAppl() {
		return nocAppl;
	}

	public void setNocAppl(String nocAppl) {
		this.nocAppl = nocAppl;
	}

	public Double getEstimateCharge() {
		return estimateCharge;
	}

	public void setEstimateCharge(Double estimateCharge) {
		this.estimateCharge = estimateCharge;
	}

	public String getConRelationName() {
		return conRelationName;
	}

	public void setConRelationName(String conRelationName) {
		this.conRelationName = conRelationName;
	}

	public Long getCsGender() {
		return csGender;
	}

	public void setCsGender(Long csGender) {
		this.csGender = csGender;
	}
	
	
}