
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountVoucherPostDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long vpDetId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long vpId;

    private Long refId;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long langId;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    @Size(max = 200)
    private String fi04V1;

    private Date fi04D1;

    @Size(max = 1)
    private String fi04Lo1;

    private Long rmRcptid;
    private Long rmRcptno;
    private String rmDateTemp;
    private Date rmDate;
    private String Department;
    private String PayeeName;
    private String Mode;
    private Double formattedCurrency;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setVpDetId(final Long vpDetId) {
        this.vpDetId = vpDetId;
    }

    public Long getVpDetId() {
        return vpDetId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setVpId(final Long vpId) {
        this.vpId = vpId;
    }

    public Long getVpId() {
        return vpId;
    }

    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    public Long getRefId() {
        return refId;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
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

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(vpDetId);
        sb.append("|");
        sb.append(vpId);
        sb.append("|");
        sb.append(refId);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        return sb.toString();
    }

    /**
     * @return the rmRcptid
     */
    public Long getRmRcptid() {
        return rmRcptid;
    }

    /**
     * @param rmRcptid the rmRcptid to set
     */
    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    /**
     * @return the rmRcptno
     */
    public Long getRmRcptno() {
        return rmRcptno;
    }

    /**
     * @param rmRcptno the rmRcptno to set
     */
    public void setRmRcptno(final Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return Department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final String department) {
        Department = department;
    }

    /**
     * @return the payeeName
     */
    public String getPayeeName() {
        return PayeeName;
    }

    /**
     * @param payeeName the payeeName to set
     */
    public void setPayeeName(final String payeeName) {
        PayeeName = payeeName;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return Mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        Mode = mode;
    }

    /**
     * @return the rmDate
     */
    public Date getRmDate() {
        return rmDate;
    }

    /**
     * @param rmDate the rmDate to set
     */
    public void setRmDate(final Date rmDate) {
        this.rmDate = rmDate;
    }

    /**
     * @return the rmDateTemp
     */
    public String getRmDateTemp() {
        return rmDateTemp;
    }

    /**
     * @param rmDateTemp the rmDateTemp to set
     */
    public void setRmDateTemp(final String rmDateTemp) {
        this.rmDateTemp = rmDateTemp;
    }

    /**
     * @return the formattedCurrency
     */
    public Double getFormattedCurrency() {
        return formattedCurrency;
    }

    /**
     * @param formattedCurrency the formattedCurrency to set
     */
    public void setFormattedCurrency(final Double formattedCurrency) {
        this.formattedCurrency = formattedCurrency;
    }

}
