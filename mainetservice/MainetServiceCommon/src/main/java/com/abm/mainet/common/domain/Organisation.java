package com.abm.mainet.common.domain;

// Generated Oct 29, 2012 2:31:43 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ORGANISATION")
public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ORGID", nullable = false, scale = 0, precision = 12)
    private long orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "O_NLS_ORGNAME")
    private String ONlsOrgname;

    @Column(name = "CREATED_BY")
    private Long userId;

    /*
     * @Column(name = "LANG_ID") private Long langId;
     */

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date lmoddate;

    @Column(name = "TDS_ACCOUNTNO")
    private String tdsAccountno;

    @Column(name = "TDS_CIRCLE")
    private String tdsCircle;

    @Column(name = "TDS_PAN_GIR_NO")
    private String tdsPanGirNo;

    @Column(name = "ORG_TAX_DED_NAME")
    private String orgTaxDedName;

    @Column(name = "ORG_TAX_DED_ADDR")
    private String orgTaxDedAddr;

    @Column(name = "ORG_SHORT_NM")
    private String orgShortNm;

    @Column(name = "O_LOGO")
    private String oLogo;

    @Column(name = "ORG_ADDRESS")
    private String orgAddress;

    @Column(name = "O_NLS_ORGNAME_MAR")
    private String oNlsOrgnameMar;

    @Column(name = "ORG_ADDRESS_MAR")
    private String orgAddressMar;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRAN_START_DATE")
    private Date tranStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ESDT_DATE")
    private Date esdtDate;

    @Column(name = "ORG_STATUS")
    private String orgStatus;

    @Column(name = "ORG_CPD_ID")
    private Long orgCpdId;

    @Column(name = "DEFAULT_STATUS", length = 1)
    private String defaultStatus;

    @Column(name = "ORG_CPD_ID_DIV")
    private Long orgCpdIdDiv;

    @Column(name = "ORG_CPD_ID_OST")
    private Long orgCpdIdOst;

    @Column(name = "ORG_CPD_ID_DIS")
    private Long orgCpdIdDis;

    @Column(name = "ORG_EMAIL_ID")
    private String orgEmailId;

    @Column(name = "VAT_CIRCLE")
    private String vatCircle;

    @Column(name = "VAT_DED_NAME")
    private String vatDedName;

    @Column(name = "ULB_ORG_ID")
    private Long ulbOrgID;

    @Column(name = "ORG_CPD_ID_STATE")
    private Long orgCpdIdState;

    @Temporal(TemporalType.DATE)
    @Column(name = "AC_GO_LIVE_DATE")
    private Date acGoLiveDate;

    @Column(name = "ORG_MULTI_CURRENCY_FLAG")
    private String orgMultiCurrencyFlag;

    @Column(name = "CU_ID")
    private Long cuId;

    @Column(name = "ORG_GST_NO")
    private String orgGstNo;

    @Column(name = "ORG_LATITUDE")
    private String orgLatitude;

    @Column(name = "ORG_LONGITUDE")
    private String orgLongitude;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORG_REGNO", length = 100)
    private String orgRegNo;
    
    @Column(name = "SDB_ID1")
    private Long sdbId1;
    @Column(name = "SDB_ID2")
    private Long sdbId2;
    @Column(name = "SDB_ID3")
    private Long sdbId3;
    @Column(name = "SDB_ID4")
    private Long sdbId4;
    @Column(name = "SDB_ID5")
    private Long sdbId5;

    /*
     * Getters and setters
     */

    public String getOrgMultiCurrencyFlag() {
        return orgMultiCurrencyFlag;
    }

    public void setOrgMultiCurrencyFlag(final String orgMultiCurrencyFlag) {
        this.orgMultiCurrencyFlag = orgMultiCurrencyFlag;
    }

    public Long getCuId() {
        return cuId;
    }

    public void setCuId(final Long cuId) {
        this.cuId = cuId;
    }

    public String getoNlsOrgnameMar() {
        return oNlsOrgnameMar;
    }

    public void setoNlsOrgnameMar(final String oNlsOrgnameMar) {
        this.oNlsOrgnameMar = oNlsOrgnameMar;
    }

    public Date getAcGoLiveDate() {
        return acGoLiveDate;
    }

    public void setAcGoLiveDate(final Date acGoLiveDate) {
        this.acGoLiveDate = acGoLiveDate;
    }

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public Organisation() {
        super();
    }

    public Organisation(final long orgid) {
        this.orgid = orgid;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setOrgid(final long orgid) {
        this.orgid = orgid;
    }

    public long getOrgid() {
        return orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : O_NLS_ORGNAME ( NVARCHAR2 )
    /**
     * @return the oNlsOrgname
     */
    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    /**
     * @param oNlsOrgname the oNlsOrgname to set
     */
    public void setONlsOrgname(final String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    /*
     * public void setLangId(final Long langId) { this.langId = langId; } public Long getLangId() { return langId; }
     */

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : TDS_ACCOUNTNO ( NVARCHAR2 )
    public void setTdsAccountno(final String tdsAccountno) {
        this.tdsAccountno = tdsAccountno;
    }

    public String getTdsAccountno() {
        return tdsAccountno;
    }

    // --- DATABASE MAPPING : TDS_CIRCLE ( NVARCHAR2 )
    public void setTdsCircle(final String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public String getTdsCircle() {
        return tdsCircle;
    }

    // --- DATABASE MAPPING : TDS_PAN_GIR_NO ( NVARCHAR2 )
    public void setTdsPanGirNo(final String tdsPanGirNo) {
        this.tdsPanGirNo = tdsPanGirNo;
    }

    public String getTdsPanGirNo() {
        return tdsPanGirNo;
    }

    // --- DATABASE MAPPING : ORG_TAX_DED_NAME ( NVARCHAR2 )
    public void setOrgTaxDedName(final String orgTaxDedName) {
        this.orgTaxDedName = orgTaxDedName;
    }

    public String getOrgTaxDedName() {
        return orgTaxDedName;
    }

    // --- DATABASE MAPPING : ORG_TAX_DED_ADDR ( NVARCHAR2 )
    public void setOrgTaxDedAddr(final String orgTaxDedAddr) {
        this.orgTaxDedAddr = orgTaxDedAddr;
    }

    public String getOrgTaxDedAddr() {
        return orgTaxDedAddr;
    }

    // --- DATABASE MAPPING : ORG_SHORT_NM ( NVARCHAR2 )
    public void setOrgShortNm(final String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getOrgShortNm() {
        return orgShortNm;
    }

    // --- DATABASE MAPPING : ORG_ADDRESS ( NVARCHAR2 )
    public void setOrgAddress(final String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    // --- DATABASE MAPPING : O_NLS_ORGNAME_MAR ( NVARCHAR2 )
    public void setONlsOrgnameMar(final String oNlsOrgnameMar) {
        this.oNlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getONlsOrgnameMar() {
        return oNlsOrgnameMar;
    }

    // --- DATABASE MAPPING : ORG_ADDRESS_MAR ( NVARCHAR2 )
    public void setOrgAddressMar(final String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
    }

    public String getOrgAddressMar() {
        return orgAddressMar;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : ESDT_DATE ( DATE )
    public void setEsdtDate(final Date esdtDate) {
        this.esdtDate = esdtDate;
    }

    public Date getEsdtDate() {
        return esdtDate;
    }

    // --- DATABASE MAPPING : ORG_STATUS ( NVARCHAR2 )
    public void setOrgStatus(final String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID ( NUMBER )
    public void setOrgCpdId(final Long orgCpdId) {
        this.orgCpdId = orgCpdId;
    }

    public Long getOrgCpdId() {
        return orgCpdId;
    }

    // --- DATABASE MAPPING : DEFAULT_STATUS ( CHAR )
    public void setDefaultStatus(final String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_DIV ( NUMBER )
    public void setOrgCpdIdDiv(final Long orgCpdIdDiv) {
        this.orgCpdIdDiv = orgCpdIdDiv;
    }

    public Long getOrgCpdIdDiv() {
        return orgCpdIdDiv;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_OST ( NUMBER )
    public void setOrgCpdIdOst(final Long orgCpdIdOst) {
        this.orgCpdIdOst = orgCpdIdOst;
    }

    public Long getOrgCpdIdOst() {
        return orgCpdIdOst;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_DIS ( NUMBER )
    public void setOrgCpdIdDis(final Long orgCpdIdDis) {
        this.orgCpdIdDis = orgCpdIdDis;
    }

    public Long getOrgCpdIdDis() {
        return orgCpdIdDis;
    }

    // --- DATABASE MAPPING : ORG_EMAIL_ID ( NVARCHAR2 )
    public void setOrgEmailId(final String orgEmailId) {
        this.orgEmailId = orgEmailId;
    }

    public String getOrgEmailId() {
        return orgEmailId;
    }

    // --- DATABASE MAPPING : VAT_CIRCLE ( NVARCHAR2 )
    public void setVatCircle(final String vatCircle) {
        this.vatCircle = vatCircle;
    }

    public String getVatCircle() {
        return vatCircle;
    }

    // --- DATABASE MAPPING : VAT_DED_NAME ( NVARCHAR2 )
    public void setVatDedName(final String vatDedName) {
        this.vatDedName = vatDedName;
    }

    public String getVatDedName() {
        return vatDedName;
    }

    // --- DATABASE MAPPING : ULB_ORG_ID
    public Long getUlbOrgID() {
        return ulbOrgID;
    }

    public void setUlbOrgID(final Long ulbOrgID) {
        this.ulbOrgID = ulbOrgID;
    }

    public Long getOrgCpdIdState() {
        return orgCpdIdState;
    }

    public void setOrgCpdIdState(final Long orgCpdIdState) {
        this.orgCpdIdState = orgCpdIdState;
    }

    public String getOrgGstNo() {
        return orgGstNo;
    }

    public void setOrgGstNo(final String orgGstNo) {
        this.orgGstNo = orgGstNo;
    }

    public String getOrgLatitude() {
        return orgLatitude;
    }

    public void setOrgLatitude(final String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

    public String getOrgLongitude() {
        return orgLongitude;
    }

    public void setOrgLongitude(final String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    public String getoLogo() {
        return oLogo;
    }

    public void setoLogo(final String oLogo) {
        this.oLogo = oLogo;
    }

    public Date getTranStartDate() {
        return tranStartDate;
    }

    public void setTranStartDate(final Date tranStartDate) {
        this.tranStartDate = tranStartDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getOrgRegNo() {
        return orgRegNo;
    }

    public void setOrgRegNo(String orgRegNo) {
        this.orgRegNo = orgRegNo;
    }

    
    
    /**
	 * @return the sdbId1
	 */
	public Long getSdbId1() {
		return sdbId1;
	}

	/**
	 * @param sdbId1 the sdbId1 to set
	 */
	public void setSdbId1(Long sdbId1) {
		this.sdbId1 = sdbId1;
	}

	/**
	 * @return the sdbId2
	 */
	public Long getSdbId2() {
		return sdbId2;
	}

	/**
	 * @param sdbId2 the sdbId2 to set
	 */
	public void setSdbId2(Long sdbId2) {
		this.sdbId2 = sdbId2;
	}

	/**
	 * @return the sdbId3
	 */
	public Long getSdbId3() {
		return sdbId3;
	}

	/**
	 * @param sdbId3 the sdbId3 to set
	 */
	public void setSdbId3(Long sdbId3) {
		this.sdbId3 = sdbId3;
	}

	/**
	 * @return the sdbId4
	 */
	public Long getSdbId4() {
		return sdbId4;
	}

	/**
	 * @param sdbId4 the sdbId4 to set
	 */
	public void setSdbId4(Long sdbId4) {
		this.sdbId4 = sdbId4;
	}

	/**
	 * @return the sdbId5
	 */
	public Long getSdbId5() {
		return sdbId5;
	}

	/**
	 * @param sdbId5 the sdbId5 to set
	 */
	public void setSdbId5(Long sdbId5) {
		this.sdbId5 = sdbId5;
	}

	// ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(orgid);
        sb.append("]:");
        sb.append(ONlsOrgname);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        /*
         * sb.append(langId); sb.append("|");
         */
        sb.append(lmoddate);
        sb.append("|");
        sb.append(tdsAccountno);
        sb.append("|");
        sb.append(tdsCircle);
        sb.append("|");
        sb.append(tdsPanGirNo);
        sb.append("|");
        sb.append(orgTaxDedName);
        sb.append("|");
        sb.append(orgTaxDedAddr);
        sb.append("|");
        sb.append(orgShortNm);
        sb.append("|");
        sb.append(orgAddress);
        sb.append("|");
        sb.append(oNlsOrgnameMar);
        sb.append("|");
        sb.append(orgAddressMar);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(esdtDate);
        sb.append("|");
        sb.append(orgStatus);
        sb.append("|");
        sb.append(orgCpdId);
        sb.append("|");
        sb.append(defaultStatus);
        sb.append("|");
        sb.append(orgCpdIdDiv);
        sb.append("|");
        sb.append(orgCpdIdOst);
        sb.append("|");
        sb.append(orgCpdIdDis);
        sb.append("|");
        sb.append(orgEmailId);
        sb.append("|");
        sb.append(vatCircle);
        sb.append("|");
        sb.append(vatDedName);
        sb.append("|");
        sb.append(sdbId1);
        sb.append("|");
        sb.append(sdbId2);
        sb.append("|");
        sb.append(sdbId3);
        sb.append("|");
        sb.append(sdbId4);
        sb.append("|");
        sb.append(sdbId5);
        return sb.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_ORGANISATION", "ORGID" };
    }

}
