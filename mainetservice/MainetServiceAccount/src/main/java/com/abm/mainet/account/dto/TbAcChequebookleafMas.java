package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbAcChequebookleafMas implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @NotNull
    private Long chequebookId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long bmBankid;

    private Long bmBankidTemp;

    private Long baAccountid;

    private Long baAccountidTemp;

    @NotNull
    @Size(min = 1, max = 12)
    private String fromChequeNo;

    @NotNull
    @Size(min = 1, max = 12)
    private String toChequeNo;

    private Long empid;

    @NotNull
    private Long checkbookLeave;

    private Date rcptChqbookDate;

    @Null
    private String rcptChqbookDatetemp;

    private Long issuerEmpid;

    private Date chkbookRtnDate;

    @Null
    private String chkbookRtnDatetemp;

    @Size(max = 1)
    private String checkBookReturn;

    private Long orgid;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

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

    private String returnRemark;

    private String chequeIssueDateTemp;

    private Date chequeIssueDate;

    private String hasError;

    private String alreadyExists = "N";

    public String getChkbookRtnDatetemp() {
        return chkbookRtnDatetemp;
    }

    public void setChkbookRtnDatetemp(final String chkbookRtnDatetemp) {
        this.chkbookRtnDatetemp = chkbookRtnDatetemp;
    }

    public String getRcptChqbookDatetemp() {
        return rcptChqbookDatetemp;
    }

    public void setRcptChqbookDatetemp(final String rcptChqbookDatetemp) {
        this.rcptChqbookDatetemp = rcptChqbookDatetemp;
    }

    public Long getBmBankidTemp() {
        return bmBankidTemp;
    }

    public void setBmBankidTemp(final Long bmBankidTemp) {
        this.bmBankidTemp = bmBankidTemp;
    }

    public Long getBaAccountidTemp() {
        return baAccountidTemp;
    }

    public void setBaAccountidTemp(final Long baAccountidTemp) {
        this.baAccountidTemp = baAccountidTemp;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setChequebookId(final Long chequebookId) {
        this.chequebookId = chequebookId;
    }

    public Long getChequebookId() {
        return chequebookId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setBmBankid(final Long bmBankid) {
        this.bmBankid = bmBankid;
    }

    public Long getBmBankid() {
        return bmBankid;
    }

    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    public Long getBaAccountid() {
        return baAccountid;
    }

    public void setFromChequeNo(final String fromChequeNo) {
        this.fromChequeNo = fromChequeNo;
    }

    public String getFromChequeNo() {
        return fromChequeNo;
    }

    public void setToChequeNo(final String toChequeNo) {
        this.toChequeNo = toChequeNo;
    }

    public String getToChequeNo() {
        return toChequeNo;
    }

    public void setEmpid(final Long empid) {
        this.empid = empid;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setCheckbookLeave(final Long checkbookLeave) {
        this.checkbookLeave = checkbookLeave;
    }

    public Long getCheckbookLeave() {
        return checkbookLeave;
    }

    public void setRcptChqbookDate(final Date rcptChqbookDate) {
        this.rcptChqbookDate = rcptChqbookDate;
    }

    public Date getRcptChqbookDate() {
        return rcptChqbookDate;
    }

    public void setIssuerEmpid(final Long issuerEmpid) {
        this.issuerEmpid = issuerEmpid;
    }

    public Long getIssuerEmpid() {
        return issuerEmpid;
    }

    public void setChkbookRtnDate(final Date chkbookRtnDate) {
        this.chkbookRtnDate = chkbookRtnDate;
    }

    public Date getChkbookRtnDate() {
        return chkbookRtnDate;
    }

    public void setCheckBookReturn(final String checkBookReturn) {
        this.checkBookReturn = checkBookReturn;
    }

    public String getCheckBookReturn() {
        return checkBookReturn;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
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

    /**
     * @return the returnRemark
     */
    public String getReturnRemark() {
        return returnRemark;
    }

    /**
     * @param returnRemark the returnRemark to set
     */
    public void setReturnRemark(final String returnRemark) {
        this.returnRemark = returnRemark;
    }

    /**
     * @return the chequeIssueDateTemp
     */
    public String getChequeIssueDateTemp() {
        return chequeIssueDateTemp;
    }

    /**
     * @param chequeIssueDateTemp the chequeIssueDateTemp to set
     */
    public void setChequeIssueDateTemp(final String chequeIssueDateTemp) {
        this.chequeIssueDateTemp = chequeIssueDateTemp;
    }

    /**
     * @return the chequeIssueDate
     */
    public Date getChequeIssueDate() {
        return chequeIssueDate;
    }

    /**
     * @param chequeIssueDate the chequeIssueDate to set
     */
    public void setChequeIssueDate(final Date chequeIssueDate) {
        this.chequeIssueDate = chequeIssueDate;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TbAcChequebookleafMas [chequebookId=" + chequebookId
                + ", bmBankid=" + bmBankid + ", bmBankidTemp=" + bmBankidTemp
                + ", baAccountid=" + baAccountid + ", baAccountidTemp="
                + baAccountidTemp + ", fromChequeNo=" + fromChequeNo
                + ", toChequeNo=" + toChequeNo + ", empid=" + empid
                + ", checkbookLeave=" + checkbookLeave + ", rcptChqbookDate="
                + rcptChqbookDate + ", rcptChqbookDatetemp="
                + rcptChqbookDatetemp + ", issuerEmpid=" + issuerEmpid
                + ", chkbookRtnDate=" + chkbookRtnDate
                + ", chkbookRtnDatetemp=" + chkbookRtnDatetemp
                + ", checkBookReturn=" + checkBookReturn + ", orgid=" + orgid
                + ", userId=" + userId + ", langId=" + langId + ", lmoddate="
                + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1
                + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1
                + ", returnRemark=" + returnRemark + ", chequeIssueDateTemp="
                + chequeIssueDateTemp + ", chequeIssueDate=" + chequeIssueDate
                + "]";
    }

}
