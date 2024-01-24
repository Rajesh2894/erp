package com.abm.mainet.cfc.challan.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({

        @NamedQuery(name = "updateChallanVerification",

                query = "UPDATE ChallanMaster SET challanRcvdDate= :challanRcvdDate,challanRcvdBy=:challanRcvdBy,challanRcvdFlag=:challanRcvdFlag,bankTransId=:bankTransId,updatedBy =:updatedBy, updatedDate=:updatedDate, lgIpMacUpd=:lgIpMacUpd WHERE challanNo = :challanNo AND challanId=:challanId AND organisationId= :organisationId") })
@Entity
@Table(name = "TB_CHALLAN_MASTER")
public class ChallanMaster extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CHALLAN_ID", precision = 12, scale = 0, nullable = false)
    private long challanId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long organisationId;

    @Column(name = "CHALLAN_NO", length = 30, nullable = true)
    private String challanNo;

    @Column(name = "CHALLAN_AMOUNT", precision = 15, scale = 2, nullable = true)
    private Double challanAmount;

    @Column(name = "CHALLAN_DATE", nullable = true)
    private Date challanDate;

    @Column(name = "CHALLAN_VALI_DATE", nullable = true)
    private Date challanValiDate;

    @Column(name = "BM_BANKID", precision = 12, scale = 0, nullable = true)
    private Long bmBankid;

    @Column(name = "BA_ACCOUNTID", precision = 12, scale = 0, nullable = true)
    private Long baAccountid;

    @Column(name = "BANK_TRANS_ID", length = 30, nullable = true)
    private String bankTransId;

    @Column(name = "FA_YEARID", precision = 12, scale = 0, nullable = true)
    private Long faYearid;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptid;

    @Column(name = "CHALLAN_RCVD_FLAG", length = 1, nullable = true)
    private String challanRcvdFlag;

    @Column(name = "CHALLAN_RCVD_DATE", nullable = true)
    private Date challanRcvdDate;

    @Column(name = "CHALLAN_RCVD_BY", precision = 12, scale = 0, nullable = true)
    private Long challanRcvdBy;

    @Column(name = "STATUS", length = 1, nullable = true)
    private String status;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userempId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CHALLN_GEN_SERVICE_TYPE", length = 1, nullable = true)
    private String challanServiceType;

    @Column(name = "OFL_PAYMENT_MODE", precision = 12, scale = 0, nullable = false)
    // comments : offline payment mode from OFL prefix
    private long oflPaymentMode;

    @Column(name = "LOI_NO", length = 16, nullable = true)
    private String loiNo;

    @Column(name = "REFERENCE_NO")
    private String uniquePrimaryId;

    @Column(name = "PAYMENT_RECEIPT_CATEGORY")
    private String paymentReceiptCategory;

    /**
     * @return the challanId
     */
    public long getChallanId() {
        return challanId;
    }

    /**
     * @param challanId the challanId to set
     */
    public void setChallanId(final long challanId) {
        this.challanId = challanId;
    }

    /**
     * @return the challanNo
     */
    public String getChallanNo() {
        return challanNo;
    }

    /**
     * @param challanNo the challanNo to set
     */
    public void setChallanNo(final String challanNo) {
        this.challanNo = challanNo;
    }

    /**
     * @return the challanAmount
     */
    public Double getChallanAmount() {
        return challanAmount;
    }

    /**
     * @param challanAmount the challanAmount to set
     */
    public void setChallanAmount(final Double challanAmount) {
        this.challanAmount = challanAmount;
    }

    /**
     * @return the challanDate
     */
    public Date getChallanDate() {
        return challanDate;
    }

    /**
     * @param challanDate the challanDate to set
     */
    public void setChallanDate(final Date challanDate) {
        this.challanDate = challanDate;
    }

    /**
     * @return the challanValiDate
     */
    public Date getChallanValiDate() {
        return challanValiDate;
    }

    /**
     * @param challanValiDate the challanValiDate to set
     */
    public void setChallanValiDate(final Date challanValiDate) {
        this.challanValiDate = challanValiDate;
    }

    /**
     * @return the bmBankid
     */
    public Long getBmBankid() {
        return bmBankid;
    }

    /**
     * @param bmBankid the bmBankid to set
     */
    public void setBmBankid(final Long bmBankid) {
        this.bmBankid = bmBankid;
    }

    /**
     * @return the baAccountid
     */
    public Long getBaAccountid() {
        return baAccountid;
    }

    /**
     * @param baAccountid the baAccountid to set
     */
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    /**
     * @return the bankTransId
     */
    public String getBankTransId() {
        return bankTransId;
    }

    /**
     * @param bankTransId the bankTransId to set
     */
    public void setBankTransId(final String bankTransId) {
        this.bankTransId = bankTransId;
    }

    /**
     * @return the faYearid
     */
    public Long getFaYearid() {
        return faYearid;
    }

    /**
     * @param faYearid the faYearid to set
     */
    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
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
     * @return the challanRcvdFlag
     */
    public String getChallanRcvdFlag() {
        return challanRcvdFlag;
    }

    /**
     * @param challanRcvdFlag the challanRcvdFlag to set
     */
    public void setChallanRcvdFlag(final String challanRcvdFlag) {
        this.challanRcvdFlag = challanRcvdFlag;
    }

    /**
     * @return the challanRcvdDate
     */
    public Date getChallanRcvdDate() {
        return challanRcvdDate;
    }

    /**
     * @param challanRcvdDate the challanRcvdDate to set
     */
    public void setChallanRcvdDate(final Date challanRcvdDate) {
        this.challanRcvdDate = challanRcvdDate;
    }

    /**
     * @return the challanRcvdBy
     */
    public Long getChallanRcvdBy() {
        return challanRcvdBy;
    }

    /**
     * @param challanRcvdBy the challanRcvdBy to set
     */
    public void setChallanRcvdBy(final Long challanRcvdBy) {
        this.challanRcvdBy = challanRcvdBy;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
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
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
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

    @Override
    public long getId() {
        return getChallanId();
    }

    @Override
    public String getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    @Column(name = "USER_ID", updatable = false, insertable = false)
    private long applicantId;

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(final long applicantId) {
        this.applicantId = applicantId;
    }

    public String getChallanServiceType() {
        return challanServiceType;
    }

    public void setChallanServiceType(
            final String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "CFC", "TB_CHALLAN_MASTER",
                "CHALLAN_ID" };
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(final Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getUserempId() {
        return userempId;
    }

    public void setUserempId(final Long userempId) {
        this.userempId = userempId;
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
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setOrgId(com.abm. mainetservice.web.common.entity.Organisation)
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
     * @see com.abm.mainetservice.web.common.entity.BaseEntity#setUserId(com.abm. mainetservice.web.common.entity.Employee)
     */
    @Override
    public void setUserId(final Employee userId) {
    }

    public long getOflPaymentMode() {
        return oflPaymentMode;
    }

    public void setOflPaymentMode(final long oflPaymentMode) {
        this.oflPaymentMode = oflPaymentMode;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public String getUniquePrimaryId() {
        return uniquePrimaryId;
    }

    public void setUniquePrimaryId(final String uniquePrimaryId) {
        this.uniquePrimaryId = uniquePrimaryId;
    }

    public String getPaymentReceiptCategory() {
        return paymentReceiptCategory;
    }

    public void setPaymentReceiptCategory(final String paymentReceiptCategory) {
        this.paymentReceiptCategory = paymentReceiptCategory;
    }

}