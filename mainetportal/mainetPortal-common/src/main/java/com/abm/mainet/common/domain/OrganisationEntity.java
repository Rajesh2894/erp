package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_ORGANISATION")
public class OrganisationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ORGID", nullable = false, scale = 0, precision = 12)
    private Long orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LANG_ID")
    private short langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Lob
    @Column(name = "O_LOGO")
    private byte[] OLogo;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRAN_START_DATE")
    private Date appStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ESDT_DATE")
    private Date esdtDate;

    @Column(name = "ORG_CPD_ID")
    private Long orgCpdId;

    @Column(name = "ORG_CPD_ID_DIV")
    private Long orgCpdIdDiv;

    @Column(name = "ORG_CPD_ID_OST")
    private Long orgCpdIdOst;

    @Column(name = "ORG_CPD_ID_DIS")
    private Long orgCpdIdDis;

    @Column(name = "ULB_ORG_ID")
    private Long ulbOrgID;

    @Column(name = "ORG_CPD_ID_STATE")
    private Long orgCpdIdState;

    @Column(name = "DEFAULT_STATUS", length = 1)
    private String defaultStatus;

    @Column(name = "O_NLS_ORGNAME")
    private String ONlsOrgname;

    @Column(name = "TDS_ACCOUNTNO")
    private String tdsAccountno;

    @Column(name = "TDS_CIRCLE")
    private String tdsCircle;
	
	@Column(name="ORG_GST_NO")
	private String    orgGstNo   ;

	@Column(name="ORG_LATITUDE")
	private String orgLatitude;

    @Column(name = "TDS_PAN_GIR_NO")
    private String tdsPanGirNo;

	@Column(name="ORG_LONGITUDE")
	private String orgLongitude;

    @Column(name = "ORG_TAX_DED_NAME")
    private String orgTAxDedName;

    @Column(name = "ORG_TAX_DED_ADDR")
    private String orgTaxDedAddr;

    @Column(name = "ORG_SHORT_NM")
    private String orgShortNm;

    @Column(name = "ORG_ADDRESS")
    private String orgAddress;

    @Column(name = "O_NLS_ORGNAME_MAR")
    private String ONlsOrgnameMar;

    @Column(name = "ORG_ADDRESS_MAR")
    private String orgAddressMar;

    @Column(name = "ORG_STATUS")
    private String orgStatus;

    @Column(name = "ORG_EMAIL_ID")
    private String orgEmailId;

    @Column(name = "VAT_CIRCLE")
    private String vatCircle;

    @Column(name = "VAT_DED_NAME")
    private String vatDedName;

    public OrganisationEntity(Long orgid) {

    }

  //----------------------------------------------------------------------
  	// CONSTRUCTOR(S)
  	//----------------------------------------------------------------------
  	public OrganisationEntity() {
  		super();
  	}
  	
    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : O_NLS_ORGNAME ( NVARCHAR2 )

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public short getLangId() {
        return langId;
    }

    public void setLangId(short langId) {
        this.langId = langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return this.lmoddate;
    }

    // --- DATABASE MAPPING : TDS_ACCOUNTNO ( NVARCHAR2 )
    public void setTdsAccountno(String tdsAccountno) {
        this.tdsAccountno = tdsAccountno;
    }

    public String getTdsAccountno() {
        return this.tdsAccountno;
    }

    // --- DATABASE MAPPING : TDS_CIRCLE ( NVARCHAR2 )
    public void setTdsCircle(String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public String getTdsCircle() {
        return this.tdsCircle;
    }

    // --- DATABASE MAPPING : TDS_PAN_GIR_NO ( NVARCHAR2 )
    public void setTdsPanGirNo(String tdsPanGirNo) {
        this.tdsPanGirNo = tdsPanGirNo;
    }

    public String getTdsPanGirNo() {
        return this.tdsPanGirNo;
    }

    // --- DATABASE MAPPING : ORG_TAX_DED_ADDR ( NVARCHAR2 )
    public void setOrgTaxDedAddr(String orgTaxDedAddr) {
        this.orgTaxDedAddr = orgTaxDedAddr;
    }

    public String getOrgTaxDedAddr() {
        return this.orgTaxDedAddr;
    }

    // --- DATABASE MAPPING : ORG_SHORT_NM ( NVARCHAR2 )
    public void setOrgShortNm(String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getOrgShortNm() {
        return this.orgShortNm;
    }

    public byte[] getOLogo() {
        return OLogo;
    }

    public void setOLogo(byte[] oLogo) {
        OLogo = oLogo;
    }

    // --- DATABASE MAPPING : ORG_ADDRESS ( NVARCHAR2 )
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgAddress() {
        return this.orgAddress;
    }

    // --- DATABASE MAPPING : ORG_ADDRESS_MAR ( NVARCHAR2 )
    public void setOrgAddressMar(String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
    }

    public String getOrgAddressMar() {
        return this.orgAddressMar;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    // --- DATABASE MAPPING : APP_START_DATE ( DATE )
    public void setAppStartDate(Date appStartDate) {
        this.appStartDate = appStartDate;
    }

    public Date getAppStartDate() {
        return this.appStartDate;
    }

    // --- DATABASE MAPPING : ESDT_DATE ( DATE )
    public void setEsdtDate(Date esdtDate) {
        this.esdtDate = esdtDate;
    }

    public Date getEsdtDate() {
        return this.esdtDate;
    }

    // --- DATABASE MAPPING : ORG_STATUS ( NVARCHAR2 )
    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getOrgStatus() {
        return this.orgStatus;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID ( NUMBER )
    public void setOrgCpdId(Long orgCpdId) {
        this.orgCpdId = orgCpdId;
    }

    public Long getOrgCpdId() {
        return this.orgCpdId;
    }

    // --- DATABASE MAPPING : DEFAULT_STATUS ( CHAR )
    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getDefaultStatus() {
        return this.defaultStatus;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_DIV ( NUMBER )
    public void setOrgCpdIdDiv(Long orgCpdIdDiv) {
        this.orgCpdIdDiv = orgCpdIdDiv;
    }

    public Long getOrgCpdIdDiv() {
        return this.orgCpdIdDiv;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_OST ( NUMBER )
    public void setOrgCpdIdOst(Long orgCpdIdOst) {
        this.orgCpdIdOst = orgCpdIdOst;
    }

    public Long getOrgCpdIdOst() {
        return this.orgCpdIdOst;
    }

    // --- DATABASE MAPPING : ORG_CPD_ID_DIS ( NUMBER )
    public void setOrgCpdIdDis(Long orgCpdIdDis) {
        this.orgCpdIdDis = orgCpdIdDis;
    }

    public Long getOrgCpdIdDis() {
        return this.orgCpdIdDis;
    }

    // --- DATABASE MAPPING : ORG_EMAIL_ID ( NVARCHAR2 )
    public void setOrgEmailId(String orgEmailId) {
        this.orgEmailId = orgEmailId;
    }

    public String getOrgEmailId() {
        return this.orgEmailId;
    }

    // --- DATABASE MAPPING : VAT_CIRCLE ( NVARCHAR2 )
    public void setVatCircle(String vatCircle) {
        this.vatCircle = vatCircle;
    }

    public String getVatCircle() {
        return this.vatCircle;
    }

    // --- DATABASE MAPPING : VAT_DED_NAME ( NVARCHAR2 )
    public void setVatDedName(String vatDedName) {
        this.vatDedName = vatDedName;
    }

    public String getVatDedName() {
        return this.vatDedName;
    }

    // --- DATABASE MAPPING : ULB_ORG_ID
    public Long getUlbOrgID() {
        return ulbOrgID;
    }

    public void setUlbOrgID(Long ulbOrgID) {
        this.ulbOrgID = ulbOrgID;
    }

    public Long getOrgCpdIdState() {
        return orgCpdIdState;
    }

    public void setOrgCpdIdState(Long orgCpdIdState) {
        this.orgCpdIdState = orgCpdIdState;
    }

    public String getOrgTAxDedName() {
        return orgTAxDedName;
    }

    public void setOrgTAxDedName(String orgTAxDedName) {
        this.orgTAxDedName = orgTAxDedName;
    }

    public String getONlsOrgnameMar() {
        return ONlsOrgnameMar;
    }

    public void setONlsOrgnameMar(String oNlsOrgnameMar) {
        ONlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    public void setONlsOrgname(String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(tdsAccountno);
        sb.append("|");
        sb.append(tdsCircle);
        sb.append("|");
        sb.append(tdsPanGirNo);
        sb.append("|");
        sb.append(orgTaxDedAddr);
        sb.append("|");
        sb.append(orgShortNm);
        // attribute 'oLogo' not usable (type = byte[])
        sb.append("|");
        sb.append(orgAddress);
        sb.append("|");
        sb.append(orgAddressMar);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(appStartDate);
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
        return sb.toString();
    }

    /*
     * public String[] getPkValues() { return new String[] { "AUT", "TB_ORGANISATION", "ORGID"}; }
     */
}
