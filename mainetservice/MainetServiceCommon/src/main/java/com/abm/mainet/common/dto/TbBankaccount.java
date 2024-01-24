/*
 * Created on 18 Jun 2016 ( Time 16:01:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbBankaccount implements Serializable {
    private static final long serialVersionUID = -8473804382496782393L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long baAccountid;

    private Long orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Size(min = 1, max = 50)
    private String baAccountcode;

    private Long bmBankid;

    private Long cpdAccounttype;

    @Size(min = 1, max = 300)
    private String baAccountname;

    private Date baCurrentdate;

    @Size(max = 2)
    private String baStatus;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    private Long fuObjId;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    private Long fi04N2;

    private Long fi04N3;

    private Long fi04N4;

    private Long fi04N5;

    @Size(max = 200)
    private String fi04V1;

    @Size(max = 200)
    private String fi04V2;

    @Size(max = 200)
    private String fi04V3;

    @Size(max = 200)
    private String fi04V4;

    @Size(max = 200)
    private String fi04V5;

    private Date fi04D1;

    private Date fi04D2;

    private Date fi04D3;

    @Size(max = 1)
    private String fi04Lo1;

    @Size(max = 1)
    private String fi04Lo2;

    @Size(max = 1)
    private String fi04Lo3;

    private Long acCpdId;

    @Size(max = 1)
    private String appChallanFlag;

    private Long tbBankmaster;

    private String tempdate;

    private Long fundId;

    private Long fieldId;

    private Long pacHeadId;

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the pacHeadId
     */
    public Long getPacHeadId() {
        return pacHeadId;
    }

    /**
     * @param pacHeadId the pacHeadId to set
     */
    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    @JsonBackReference
    private List<TbBankaccountBal> listOfTbBankaccountBal;

    @JsonBackReference
    private List<AccountFundMasterBean> funds;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    public Long getBaAccountid() {
        return baAccountid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setBaAccountcode(final String baAccountcode) {
        this.baAccountcode = baAccountcode;
    }

    public String getBaAccountcode() {
        return baAccountcode;
    }

    public void setBmBankid(final Long bmBankid) {
        this.bmBankid = bmBankid;
    }

    public Long getBmBankid() {
        return bmBankid;
    }

    public void setCpdAccounttype(final Long cpdAccounttype) {
        this.cpdAccounttype = cpdAccounttype;
    }

    public Long getCpdAccounttype() {
        return cpdAccounttype;
    }

    public void setBaAccountname(final String baAccountname) {
        this.baAccountname = baAccountname;
    }

    public String getBaAccountname() {
        return baAccountname;
    }

    public void setBaCurrentdate(final Date baCurrentdate) {
        this.baCurrentdate = baCurrentdate;
    }

    public Date getBaCurrentdate() {
        return baCurrentdate;
    }

    public void setBaStatus(final String baStatus) {
        this.baStatus = baStatus;
    }

    public String getBaStatus() {
        return baStatus;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setFuObjId(final Long fuObjId) {
        this.fuObjId = fuObjId;
    }

    public Long getFuObjId() {
        return fuObjId;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N2(final Long fi04N2) {
        this.fi04N2 = fi04N2;
    }

    public Long getFi04N2() {
        return fi04N2;
    }

    public void setFi04N3(final Long fi04N3) {
        this.fi04N3 = fi04N3;
    }

    public Long getFi04N3() {
        return fi04N3;
    }

    public void setFi04N4(final Long fi04N4) {
        this.fi04N4 = fi04N4;
    }

    public Long getFi04N4() {
        return fi04N4;
    }

    public void setFi04N5(final Long fi04N5) {
        this.fi04N5 = fi04N5;
    }

    public Long getFi04N5() {
        return fi04N5;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V2(final String fi04V2) {
        this.fi04V2 = fi04V2;
    }

    public String getFi04V2() {
        return fi04V2;
    }

    public void setFi04V3(final String fi04V3) {
        this.fi04V3 = fi04V3;
    }

    public String getFi04V3() {
        return fi04V3;
    }

    public void setFi04V4(final String fi04V4) {
        this.fi04V4 = fi04V4;
    }

    public String getFi04V4() {
        return fi04V4;
    }

    public void setFi04V5(final String fi04V5) {
        this.fi04V5 = fi04V5;
    }

    public String getFi04V5() {
        return fi04V5;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D2(final Date fi04D2) {
        this.fi04D2 = fi04D2;
    }

    public Date getFi04D2() {
        return fi04D2;
    }

    public void setFi04D3(final Date fi04D3) {
        this.fi04D3 = fi04D3;
    }

    public Date getFi04D3() {
        return fi04D3;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo2(final String fi04Lo2) {
        this.fi04Lo2 = fi04Lo2;
    }

    public String getFi04Lo2() {
        return fi04Lo2;
    }

    public void setFi04Lo3(final String fi04Lo3) {
        this.fi04Lo3 = fi04Lo3;
    }

    public String getFi04Lo3() {
        return fi04Lo3;
    }

    public void setAcCpdId(final Long acCpdId) {
        this.acCpdId = acCpdId;
    }

    public Long getAcCpdId() {
        return acCpdId;
    }

    public void setAppChallanFlag(final String appChallanFlag) {
        this.appChallanFlag = appChallanFlag;
    }

    public String getAppChallanFlag() {
        return appChallanFlag;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(baAccountid);
        sb.append("|");
        sb.append(baAccountcode);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(bmBankid);
        sb.append("|");
        sb.append(cpdAccounttype);
        sb.append("|");
        sb.append(baAccountname);
        sb.append("|");
        sb.append(baCurrentdate);
        sb.append("|");
        sb.append(baStatus);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(fuObjId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04N2);
        sb.append("|");
        sb.append(fi04N3);
        sb.append("|");
        sb.append(fi04N4);
        sb.append("|");
        sb.append(fi04N5);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04V2);
        sb.append("|");
        sb.append(fi04V3);
        sb.append("|");
        sb.append(fi04V4);
        sb.append("|");
        sb.append(fi04V5);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04D2);
        sb.append("|");
        sb.append(fi04D3);
        sb.append("|");
        sb.append(fi04Lo1);
        sb.append("|");
        sb.append(fi04Lo2);
        sb.append("|");
        sb.append(fi04Lo3);
        sb.append("|");
        sb.append(acCpdId);
        sb.append("|");
        sb.append(appChallanFlag);
        return sb.toString();
    }

    /**
     * @return the tbBankmaster
     */
    public Long getTbBankmaster() {
        return tbBankmaster;
    }

    /**
     * @param tbBankmaster the tbBankmaster to set
     */
    public void setTbBankmaster(final Long tbBankmaster) {
        this.tbBankmaster = tbBankmaster;
    }

    /**
     * @return the listOfTbBankaccountBal
     */
    public List<TbBankaccountBal> getListOfTbBankaccountBal() {
        return listOfTbBankaccountBal;
    }

    /**
     * @param listOfTbBankaccountBal the listOfTbBankaccountBal to set
     */
    public void setListOfTbBankaccountBal(
            final List<TbBankaccountBal> listOfTbBankaccountBal) {
        this.listOfTbBankaccountBal = listOfTbBankaccountBal;
    }

    /**
     * @return the tempdate
     */
    public String getTempdate() {
        return tempdate;
    }

    /**
     * @param tempdate the tempdate to set
     */
    public void setTempdate(final String tempdate) {
        this.tempdate = tempdate;
    }

    /**
     * @return the funds
     */
    public List<AccountFundMasterBean> getFunds() {
        return funds;
    }

    /**
     * @param funds the funds to set
     */
    public void setFunds(final List<AccountFundMasterBean> funds) {
        this.funds = funds;
    }

}
