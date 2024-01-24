
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 *
 */

public class AccountJournalVoucherEntryBean extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3632220964824617803L;

    private long vouId;

    private String vouNo;

    private Date vouDate = new Date();

    private Date vouPostingDate;

    private Long vouTypeCpdId;

    private Long vouSubtypeCpdId;

    private Long dpDeptid;

    private String vouReferenceNo;

    private Date vouReferenceNoDate;

    private String narration;

    private String payerPayee;

    private Long fieldId;

    private Long org;

    private Long createdBy;

    private Date lmodDate;

    private Long updatedby;

    private Date updatedDate;

    private int langId;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long authoId;

    private Date authoDate;

    private String authoFlg;

    private Long entryType;

    private String authorityName;
    private String voucherType;
    private String vouchersubType;

    private String VocherDate;
    private String voucherDesc;
    private String departmentDesc;
    private String amountDebit;
    private String amountCredit;
    private String DrtotalAmount;
    private String CrtotalAmount;
    private String AccountHead;
    private String organizationName;
    private String AccountHeadDesc;

    private BigDecimal balAmount;
    private BigDecimal depBalAmount;
    private Long depId;
    private String depositFlag;

    private String vouDateDup;
    private String authoDateDup;

    private String authRemark;
    private String transactionDate;
    private String transactionAuthDate;
    private Long voucherSubType;

    private Long depSacHeadId;
    private char depSubTypeFlag;

    private String urlIdentifyFlag;

    private String preparedBy;
    private String preparedDate;
    private String verifiedby;
    private String verifiedDate;
    private String approvedBy;
    private String approvedDate;
    private String postedby;
    private String postedDate;
    private String entryFrom;;

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    private List<AccountJournalVoucherEntryDetailsBean> details = new ArrayList<>();

    List<AccountJournalVoucherEntryBean> listdto = new ArrayList<>();
    private List<AccountJournalVoucherEntryBean> accountHeadList = new ArrayList<>();

    private AccountVoucherReportDto oAccountVoucherReport;

    @Override
    public long getId() {

        return getVouId();
    }

    /**
     * @return the vouId
     */
    public long getVouId() {
        return vouId;
    }

    /**
     * @param vouId the vouId to set
     */
    public void setVouId(final long vouId) {
        this.vouId = vouId;
    }

    /**
     * @return the vouNo
     */
    public String getVouNo() {
        return vouNo;
    }

    /**
     * @param vouNo the vouNo to set
     */
    public void setVouNo(final String vouNo) {
        this.vouNo = vouNo;
    }

    /**
     * @return the vouDate
     */
    public Date getVouDate() {
        return vouDate;
    }

    /**
     * @param vouDate the vouDate to set
     */
    public void setVouDate(final Date vouDate) {
        this.vouDate = vouDate;
    }

    /**
     * @return the vouPostingDate
     */
    public Date getVouPostingDate() {
        return vouPostingDate;
    }

    /**
     * @param vouPostingDate the vouPostingDate to set
     */
    public void setVouPostingDate(final Date vouPostingDate) {
        this.vouPostingDate = vouPostingDate;
    }

    /**
     * @return the vouTypeCpdId
     */
    public Long getVouTypeCpdId() {
        return vouTypeCpdId;
    }

    /**
     * @param vouTypeCpdId the vouTypeCpdId to set
     */
    public void setVouTypeCpdId(final Long vouTypeCpdId) {
        this.vouTypeCpdId = vouTypeCpdId;
    }

    /**
     * @return the vouSubtypeCpdId
     */
    public Long getVouSubtypeCpdId() {
        return vouSubtypeCpdId;
    }

    /**
     * @param vouSubtypeCpdId the vouSubtypeCpdId to set
     */
    public void setVouSubtypeCpdId(final Long vouSubtypeCpdId) {
        this.vouSubtypeCpdId = vouSubtypeCpdId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the vouReferenceNo
     */
    public String getVouReferenceNo() {
        return vouReferenceNo;
    }

    /**
     * @param vouReferenceNo the vouReferenceNo to set
     */
    public void setVouReferenceNo(final String vouReferenceNo) {
        this.vouReferenceNo = vouReferenceNo;
    }

    /**
     * @return the vouReferenceNoDate
     */
    public Date getVouReferenceNoDate() {
        return vouReferenceNoDate;
    }

    /**
     * @param vouReferenceNoDate the vouReferenceNoDate to set
     */
    public void setVouReferenceNoDate(final Date vouReferenceNoDate) {
        this.vouReferenceNoDate = vouReferenceNoDate;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(final String narration) {
        this.narration = narration;
    }

    /**
     * @return the payerPayee
     */
    public String getPayerPayee() {
        return payerPayee;
    }

    /**
     * @param payerPayee the payerPayee to set
     */
    public void setPayerPayee(final String payerPayee) {
        this.payerPayee = payerPayee;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the authoId
     */
    public Long getAuthoId() {
        return authoId;
    }

    /**
     * @param authoId the authoId to set
     */
    public void setAuthoId(final Long authoId) {
        this.authoId = authoId;
    }

    /**
     * @return the authoDate
     */
    public Date getAuthoDate() {
        return authoDate;
    }

    /**
     * @param authoDate the authoDate to set
     */
    public void setAuthoDate(final Date authoDate) {
        this.authoDate = authoDate;
    }

    /**
     * @return the authoFlg
     */
    public String getAuthoFlg() {
        return authoFlg;
    }

    /**
     * @param authoFlg the authoFlg to set
     */
    public void setAuthoFlg(final String authoFlg) {
        this.authoFlg = authoFlg;
    }

    /**
     * @return the entryType
     */
    public Long getEntryType() {
        return entryType;
    }

    /**
     * @param entryType the entryType to set
     */
    public void setEntryType(final Long entryType) {
        this.entryType = entryType;
    }

    /**
     * @return the details
     */
    public List<AccountJournalVoucherEntryDetailsBean> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(final List<AccountJournalVoucherEntryDetailsBean> details) {
        this.details = details;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setOrgId(com.abm.mainet.common.domain.Organisation)
     */
    @Override
    public void setOrgId(final Organisation orgId) {

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#getUserId()
     */
    @Override
    public Employee getUserId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setUserId(com.abm.mainetservice.web.common.entity.Employee)
     */
    @Override
    public void setUserId(final Employee userId) {

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setUpdatedBy(com.abm.mainetservice.web.common.entity.Employee)
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#getIsDeleted()
     */
    @Override
    public String getIsDeleted() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setIsDeleted(java.lang.String)
     */
    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#getOrgId()
     */
    @Override
    public Organisation getOrgId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#getUpdatedBy()
     */
    @Override
    public Employee getUpdatedBy() {
        return null;
    }

    /**
     * @return the org
     */
    public Long getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(final Long org) {
        this.org = org;
    }

    /**
     * @return the updatedby
     */
    public Long getUpdatedby() {
        return updatedby;
    }

    /**
     * @param updatedby the updatedby to set
     */
    public void setUpdatedby(final Long updatedby) {
        this.updatedby = updatedby;
    }

    /**
     * @return the authorityName
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * @param authorityName the authorityName to set
     */
    public void setAuthorityName(final String authorityName) {
        this.authorityName = authorityName;
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

    public AccountVoucherReportDto getoAccountVoucherReport() {
        return oAccountVoucherReport;
    }

    public void setoAccountVoucherReport(final AccountVoucherReportDto oAccountVoucherReport) {
        this.oAccountVoucherReport = oAccountVoucherReport;
    }

    public String getVouchersubType() {
        return vouchersubType;
    }

    public void setVouchersubType(final String vouchersubType) {
        this.vouchersubType = vouchersubType;
    }

    public String getVocherDate() {
        return VocherDate;
    }

    public void setVocherDate(final String vocherDate) {
        VocherDate = vocherDate;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public String getVoucherDesc() {
        return voucherDesc;
    }

    public void setVoucherDesc(final String voucherDesc) {
        this.voucherDesc = voucherDesc;
    }

    public String getAccountHead() {
        return AccountHead;
    }

    public void setAccountHead(final String accountHead) {
        AccountHead = accountHead;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public List<AccountJournalVoucherEntryBean> getListdto() {
        return listdto;
    }

    public void setListdto(final List<AccountJournalVoucherEntryBean> listdto) {
        this.listdto = listdto;
    }

    public List<AccountJournalVoucherEntryBean> getAccountHeadList() {
        return accountHeadList;
    }

    public void setAccountHeadList(final List<AccountJournalVoucherEntryBean> accountHeadList) {
        this.accountHeadList = accountHeadList;
    }

    public String getAccountHeadDesc() {
        return AccountHeadDesc;
    }

    public void setAccountHeadDesc(final String accountHeadDesc) {
        AccountHeadDesc = accountHeadDesc;
    }

    public String getCrtotalAmount() {
        return CrtotalAmount;
    }

    public void setCrtotalAmount(String crtotalAmount) {
        CrtotalAmount = crtotalAmount;
    }

    public BigDecimal getBalAmount() {
        return balAmount;
    }

    public void setBalAmount(final BigDecimal balAmount) {
        this.balAmount = balAmount;
    }

    public Long getDepId() {
        return depId;
    }

    public String getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(final String amountCredit) {
        this.amountCredit = amountCredit;
    }

    public void setDepId(final Long depId) {
        this.depId = depId;
    }

    public String getDepositFlag() {
        return depositFlag;
    }

    public void setDepositFlag(final String depositFlag) {
        this.depositFlag = depositFlag;
    }

    public String getAmountDebit() {
        return amountDebit;
    }

    public void setAmountDebit(final String amountDebit) {
        this.amountDebit = amountDebit;
    }

    public String getDrtotalAmount() {
        return DrtotalAmount;
    }

    public void setDrtotalAmount(final String drtotalAmount) {
        DrtotalAmount = drtotalAmount;
    }

    public String getVouDateDup() {
        return vouDateDup;
    }

    public void setVouDateDup(final String vouDateDup) {
        this.vouDateDup = vouDateDup;
    }

    public String getAuthoDateDup() {
        return authoDateDup;
    }

    public void setAuthoDateDup(final String authoDateDup) {
        this.authoDateDup = authoDateDup;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(final String authRemark) {
        this.authRemark = authRemark;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getVoucherSubType() {
        return voucherSubType;
    }

    public void setVoucherSubType(final Long voucherSubType) {
        this.voucherSubType = voucherSubType;
    }

    public String getTransactionAuthDate() {
        return transactionAuthDate;
    }

    public void setTransactionAuthDate(final String transactionAuthDate) {
        this.transactionAuthDate = transactionAuthDate;
    }

    public BigDecimal getDepBalAmount() {
        return depBalAmount;
    }

    public void setDepBalAmount(BigDecimal depBalAmount) {
        this.depBalAmount = depBalAmount;
    }

    public Long getDepSacHeadId() {
        return depSacHeadId;
    }

    public void setDepSacHeadId(Long depSacHeadId) {
        this.depSacHeadId = depSacHeadId;
    }

    public char getDepSubTypeFlag() {
        return depSubTypeFlag;
    }

    public void setDepSubTypeFlag(char depSubTypeFlag) {
        this.depSubTypeFlag = depSubTypeFlag;
    }

    public String getUrlIdentifyFlag() {
        return urlIdentifyFlag;
    }

    public void setUrlIdentifyFlag(String urlIdentifyFlag) {
        this.urlIdentifyFlag = urlIdentifyFlag;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public String getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    @Override
    public String toString() {
        return "AccountJournalVoucherEntryBean [vouId=" + vouId + ", vouNo=" + vouNo + ", vouDate=" + vouDate
                + ", vouPostingDate=" + vouPostingDate + ", vouTypeCpdId=" + vouTypeCpdId + ", vouSubtypeCpdId="
                + vouSubtypeCpdId + ", dpDeptid=" + dpDeptid + ", vouReferenceNo=" + vouReferenceNo
                + ", vouReferenceNoDate=" + vouReferenceNoDate + ", narration=" + narration + ", payerPayee="
                + payerPayee + ", fieldId=" + fieldId + ", org=" + org + ", createdBy=" + createdBy + ", lmodDate="
                + lmodDate + ", updatedby=" + updatedby + ", updatedDate=" + updatedDate + ", langId=" + langId
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", authoId=" + authoId + ", authoDate="
                + authoDate + ", authoFlg=" + authoFlg + ", entryType=" + entryType + ", authorityName=" + authorityName
                + ", voucherType=" + voucherType + ", vouchersubType=" + vouchersubType + ", VocherDate=" + VocherDate
                + ", voucherDesc=" + voucherDesc + ", departmentDesc=" + departmentDesc + ", amountDebit=" + amountDebit
                + ", amountCredit=" + amountCredit + ", DrtotalAmount=" + DrtotalAmount + ", CrtotalAmount="
                + CrtotalAmount + ", AccountHead=" + AccountHead + ", organizationName=" + organizationName
                + ", AccountHeadDesc=" + AccountHeadDesc + ", balAmount=" + balAmount + ", depBalAmount=" + depBalAmount
                + ", depId=" + depId + ", depositFlag=" + depositFlag + ", vouDateDup=" + vouDateDup + ", authoDateDup="
                + authoDateDup + ", authRemark=" + authRemark + ", transactionDate=" + transactionDate
                + ", transactionAuthDate=" + transactionAuthDate + ", voucherSubType=" + voucherSubType
                + ", depSacHeadId=" + depSacHeadId + ", depSubTypeFlag=" + depSubTypeFlag + ", urlIdentifyFlag="
                + urlIdentifyFlag + ", preparedBy=" + preparedBy + ", preparedDate=" + preparedDate + ", verifiedby="
                + verifiedby + ", verifiedDate=" + verifiedDate + ", approvedBy=" + approvedBy + ", approvedDate="
                + approvedDate + ", postedby=" + postedby + ", postedDate=" + postedDate + ", details=" + details
                + ", listdto=" + listdto + ", accountHeadList=" + accountHeadList + ", oAccountVoucherReport="
                + oAccountVoucherReport + "]";
    }

	public String getEntryFrom() {
		return entryFrom;
	}

	public void setEntryFrom(String entryFrom) {
		this.entryFrom = entryFrom;
	}

}
