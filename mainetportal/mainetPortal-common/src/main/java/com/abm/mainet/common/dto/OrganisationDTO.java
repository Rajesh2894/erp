/*
 * Created on 17 Sep 2015 ( Time 19:49:11 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.abm.mainet.common.constant.MainetConstants;

public class OrganisationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @NotNull
    private BigDecimal orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Size(max = 100)
    private String oNlsOrgname;

    public String getoNlsOrgname() {
        return oNlsOrgname;
    }

    public void setoNlsOrgname(String oNlsOrgname) {
        this.oNlsOrgname = oNlsOrgname;
    }

    public byte[] getoLogo() {
        return oLogo;
    }

    public void setoLogo(byte[] oLogo) {
        this.oLogo = oLogo;
    }

    public String getoNlsOrgnameMar() {
        return oNlsOrgnameMar;
    }

    public void setoNlsOrgnameMar(String oNlsOrgnameMar) {
        this.oNlsOrgnameMar = oNlsOrgnameMar;
    }

    private BigDecimal userId;

    private BigDecimal langId;

    private Date lmoddate;

    @Size(max = 20)
    private String tdsAccountno;

    @Size(max = 20)
    private String tdsCircle;

    @Size(max = 20)
    private String tdsPanGirNo;

    @Size(max = 70)
    private String orgTaxDedName;

    @Size(max = 200)
    private String orgTaxDedAddr;

    @Size(max = 10)
    private String orgShortNm;

    private byte[] oLogo;

    @Size(max = 200)
    private String orgAddress;

    @Size(max = 200)
    private String oNlsOrgnameMar;

    @Size(max = 200)
    private String orgAddressMar;

    private BigDecimal updatedBy;

    private Date updatedDate;

    private Date appStartDate;

    private Date esdtDate;

    @Size(max = 1)
    private String orgStatus;

    private BigDecimal orgCpdId;

    @Size(max = 1)
    private String defaultStatus;

    private BigDecimal orgCpdIdDiv;

    private BigDecimal orgCpdIdOst;

    private BigDecimal orgCpdIdDis;

    @Size(max = 100)
    private String orgEmailId;

    @Size(max = 20)
    private String vatCircle;

    @Size(max = 70)
    private String vatDedName;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setOrgid(final BigDecimal orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getOrgid() {
        return orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setONlsOrgname(final String oNlsOrgname) {
        this.oNlsOrgname = oNlsOrgname;
    }

    public String getONlsOrgname() {
        return oNlsOrgname;
    }

    public void setUserId(final BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setLangId(final BigDecimal langId) {
        this.langId = langId;
    }

    public BigDecimal getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setTdsAccountno(final String tdsAccountno) {
        this.tdsAccountno = tdsAccountno;
    }

    public String getTdsAccountno() {
        return tdsAccountno;
    }

    public void setTdsCircle(final String tdsCircle) {
        this.tdsCircle = tdsCircle;
    }

    public String getTdsCircle() {
        return tdsCircle;
    }

    public void setTdsPanGirNo(final String tdsPanGirNo) {
        this.tdsPanGirNo = tdsPanGirNo;
    }

    public String getTdsPanGirNo() {
        return tdsPanGirNo;
    }

    public void setOrgTaxDedName(final String orgTaxDedName) {
        this.orgTaxDedName = orgTaxDedName;
    }

    public String getOrgTaxDedName() {
        return orgTaxDedName;
    }

    public void setOrgTaxDedAddr(final String orgTaxDedAddr) {
        this.orgTaxDedAddr = orgTaxDedAddr;
    }

    public String getOrgTaxDedAddr() {
        return orgTaxDedAddr;
    }

    public void setOrgShortNm(final String orgShortNm) {
        this.orgShortNm = orgShortNm;
    }

    public String getOrgShortNm() {
        return orgShortNm;
    }

    public void setOLogo(final byte[] oLogo) {
        this.oLogo = oLogo;
    }

    public byte[] getOLogo() {
        return oLogo;
    }

    public void setOrgAddress(final String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setONlsOrgnameMar(final String oNlsOrgnameMar) {
        this.oNlsOrgnameMar = oNlsOrgnameMar;
    }

    public String getONlsOrgnameMar() {
        return oNlsOrgnameMar;
    }

    public void setOrgAddressMar(final String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
    }

    public String getOrgAddressMar() {
        return orgAddressMar;
    }

    public void setUpdatedBy(final BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public BigDecimal getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setAppStartDate(final Date appStartDate) {
        this.appStartDate = appStartDate;
    }

    public Date getAppStartDate() {
        return appStartDate;
    }

    public void setEsdtDate(final Date esdtDate) {
        this.esdtDate = esdtDate;
    }

    public Date getEsdtDate() {
        return esdtDate;
    }

    public void setOrgStatus(final String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgCpdId(final BigDecimal orgCpdId) {
        this.orgCpdId = orgCpdId;
    }

    public BigDecimal getOrgCpdId() {
        return orgCpdId;
    }

    public void setDefaultStatus(final String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setOrgCpdIdDiv(final BigDecimal orgCpdIdDiv) {
        this.orgCpdIdDiv = orgCpdIdDiv;
    }

    public BigDecimal getOrgCpdIdDiv() {
        return orgCpdIdDiv;
    }

    public void setOrgCpdIdOst(final BigDecimal orgCpdIdOst) {
        this.orgCpdIdOst = orgCpdIdOst;
    }

    public BigDecimal getOrgCpdIdOst() {
        return orgCpdIdOst;
    }

    public void setOrgCpdIdDis(final BigDecimal orgCpdIdDis) {
        this.orgCpdIdDis = orgCpdIdDis;
    }

    public BigDecimal getOrgCpdIdDis() {
        return orgCpdIdDis;
    }

    public void setOrgEmailId(final String orgEmailId) {
        this.orgEmailId = orgEmailId;
    }

    public String getOrgEmailId() {
        return orgEmailId;
    }

    public void setVatCircle(final String vatCircle) {
        this.vatCircle = vatCircle;
    }

    public String getVatCircle() {
        return vatCircle;
    }

    public void setVatDedName(final String vatDedName) {
        this.vatDedName = vatDedName;
    }

    public String getVatDedName() {
        return vatDedName;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(orgid);
        sb.append(MainetConstants.operator.ORR);
        sb.append(oNlsOrgname);
        sb.append(MainetConstants.operator.ORR);
        sb.append(userId);
        sb.append(MainetConstants.operator.ORR);
        sb.append(langId);
        sb.append(MainetConstants.operator.ORR);
        sb.append(lmoddate);
        sb.append(MainetConstants.operator.ORR);
        sb.append(tdsAccountno);
        sb.append(MainetConstants.operator.ORR);
        sb.append(tdsCircle);
        sb.append(MainetConstants.operator.ORR);
        sb.append(tdsPanGirNo);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgTaxDedName);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgTaxDedAddr);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgShortNm);
        // attribute 'oLogo' not usable (type = byte[])
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgAddress);
        sb.append(MainetConstants.operator.ORR);
        sb.append(oNlsOrgnameMar);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgAddressMar);
        sb.append(MainetConstants.operator.ORR);
        sb.append(updatedBy);
        sb.append(MainetConstants.operator.ORR);
        sb.append(updatedDate);
        sb.append(MainetConstants.operator.ORR);
        sb.append(appStartDate);
        sb.append(MainetConstants.operator.ORR);
        sb.append(esdtDate);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgStatus);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgCpdId);
        sb.append(MainetConstants.operator.ORR);
        sb.append(defaultStatus);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgCpdIdDiv);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgCpdIdOst);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgCpdIdDis);
        sb.append(MainetConstants.operator.ORR);
        sb.append(orgEmailId);
        sb.append(MainetConstants.operator.ORR);
        sb.append(vatCircle);
        sb.append(MainetConstants.operator.ORR);
        sb.append(vatDedName);
        return sb.toString();
    }

}
