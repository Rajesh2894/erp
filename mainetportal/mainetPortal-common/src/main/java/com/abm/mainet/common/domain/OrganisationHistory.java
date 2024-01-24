package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ORGANISATION_HIST")
public class OrganisationHistory implements Serializable {

    private static final long serialVersionUID = 2673143643755004332L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ORGID_H", nullable = false, scale = 0, precision = 12)
    private Long orgHistId;

    @Column(name = "ORGID", nullable = false, scale = 0, precision = 12)
    private Long orgid;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LANG_ID")
    private short langId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "O_NLS_ORGNAME")
    private String ONlsOrgname;

    @Column(name = "TDS_ACCOUNTNO")
    private String tdsAccountno;

    @Column(name = "TDS_CIRCLE")
    private String tdsCircle;

    @Column(name = "TDS_PAN_GIR_NO")
    private String tdsPanGirNo;

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

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
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

    @Column(name = "ORG_GST_NO")
    private String orgGstNo;
    @Column(name = "ORG_LATITUDE")
    private String orgLatitude;

    @Column(name = "ORG_LONGITUDE")
    private String orgLongitude;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Lob
    @Column(name = "O_LOGO")
    private String OLogo;

    public OrganisationHistory() {
        super();
    }

    public OrganisationHistory(Long orgid) {

    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public short getLangId() {
        return langId;
    }

    public void setLangId(short langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    public void setONlsOrgname(String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
    }

    public String getTdsAccountno() {
        return tdsAccountno;
    }

    public void setTdsAccountno(String tdsAccountno) {
        this.tdsAccountno = tdsAccountno;
    }

    public String getTdsCircle() {
        return tdsCircle;
    }

    public void setTdsCircle(String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public String getTdsPanGirNo() {
        return tdsPanGirNo;
    }

    public void setTdsPanGirNo(String tdsPanGirNo) {
        this.tdsPanGirNo = tdsPanGirNo;
    }

    public String getOrgTAxDedName() {
        return orgTAxDedName;
    }

    public void setOrgTAxDedName(String orgTAxDedName) {
        this.orgTAxDedName = orgTAxDedName;
    }

    public String getOrgTaxDedAddr() {
        return orgTaxDedAddr;
    }

    public void setOrgTaxDedAddr(String orgTaxDedAddr) {
        this.orgTaxDedAddr = orgTaxDedAddr;
    }

    public String getOrgShortNm() {
        return orgShortNm;
    }

    public void setOrgShortNm(String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getONlsOrgnameMar() {
        return ONlsOrgnameMar;
    }

    public void setONlsOrgnameMar(String oNlsOrgnameMar) {
        ONlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getOrgAddressMar() {
        return orgAddressMar;
    }

    public void setOrgAddressMar(String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getTranStartDate() {
        return tranStartDate;
    }

    public void setTranStartDate(Date tranStartDate) {
        this.tranStartDate = tranStartDate;
    }

    public Date getEsdtDate() {
        return esdtDate;
    }

    public void setEsdtDate(Date esdtDate) {
        this.esdtDate = esdtDate;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Long getOrgCpdId() {
        return orgCpdId;
    }

    public void setOrgCpdId(Long orgCpdId) {
        this.orgCpdId = orgCpdId;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Long getOrgCpdIdDiv() {
        return orgCpdIdDiv;
    }

    public void setOrgCpdIdDiv(Long orgCpdIdDiv) {
        this.orgCpdIdDiv = orgCpdIdDiv;
    }

    public Long getOrgCpdIdOst() {
        return orgCpdIdOst;
    }

    public void setOrgCpdIdOst(Long orgCpdIdOst) {
        this.orgCpdIdOst = orgCpdIdOst;
    }

    public Long getOrgCpdIdDis() {
        return orgCpdIdDis;
    }

    public void setOrgCpdIdDis(Long orgCpdIdDis) {
        this.orgCpdIdDis = orgCpdIdDis;
    }

    public String getOrgEmailId() {
        return orgEmailId;
    }

    public void setOrgEmailId(String orgEmailId) {
        this.orgEmailId = orgEmailId;
    }

    public String getVatCircle() {
        return vatCircle;
    }

    public void setVatCircle(String vatCircle) {
        this.vatCircle = vatCircle;
    }

    public String getVatDedName() {
        return vatDedName;
    }

    public void setVatDedName(String vatDedName) {
        this.vatDedName = vatDedName;
    }

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

    public String getOrgGstNo() {
        return orgGstNo;
    }

    public void setOrgGstNo(String orgGstNo) {
        this.orgGstNo = orgGstNo;
    }

    public String getOrgLatitude() {
        return orgLatitude;
    }

    public void setOrgLatitude(String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

    public String getOrgLongitude() {
        return orgLongitude;
    }

    public void setOrgLongitude(String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    public String getOLogo() {
        return OLogo;
    }

    public void setOLogo(String oLogo) {
        OLogo = oLogo;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrgHistId() {
        return orgHistId;
    }

    public void setOrgHistId(Long orgHistId) {
        this.orgHistId = orgHistId;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_ORGANISATION_HIST", "ORGID_H" };
    }

}
