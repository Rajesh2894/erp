
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDepositDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long depId;

    private String depEntryDate;

    private Date depReceiptdt;

    private Long depNo;

    private Long depReceiptno;

    private Long depAmount;

    private Long depRefundBal;

    private String depReceivedfrom;

    private String depNarration;

    private Long sacHeadId;

    private String dmFlag;

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

    private String fi04V1;

    private Date fi04D1;

    private String fi04Lo1;

    private TbComparamDetEntity cpd_STATUS;

    private TbAcVendormasterEntity tbVendormaster;

    private Department tbDepartment;

    private TbComparamDetEntity cpd_SOURCE_TYPE;

    private TbComparamDetEntity cpd_DEPOSIT_TYPE;

    private String hasError;

    /**
     * @return the depId
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * @param depId the depId to set
     */
    public void setDepId(final Long depId) {
        this.depId = depId;
    }

    /**
     * @return the depEntryDate
     */
    public String getDepEntryDate() {
        return depEntryDate;
    }

    /**
     * @param depEntryDate the depEntryDate to set
     */
    public void setDepEntryDate(final String depEntryDate) {
        this.depEntryDate = depEntryDate;
    }

    /**
     * @return the depReceiptdt
     */
    public Date getDepReceiptdt() {
        return depReceiptdt;
    }

    /**
     * @param depReceiptdt the depReceiptdt to set
     */
    public void setDepReceiptdt(final Date depReceiptdt) {
        this.depReceiptdt = depReceiptdt;
    }

    public Long getDepNo() {
        return depNo;
    }

    public void setDepNo(final Long depNo) {
        this.depNo = depNo;
    }

    /**
     * @return the depReceiptno
     */
    public Long getDepReceiptno() {
        return depReceiptno;
    }

    /**
     * @param depReceiptno the depReceiptno to set
     */
    public void setDepReceiptno(final Long depReceiptno) {
        this.depReceiptno = depReceiptno;
    }

    /**
     * @return the depAmount
     */
    public Long getDepAmount() {
        return depAmount;
    }

    /**
     * @param depAmount the depAmount to set
     */
    public void setDepAmount(final Long depAmount) {
        this.depAmount = depAmount;
    }

    /**
     * @return the depRefundBal
     */
    public Long getDepRefundBal() {
        return depRefundBal;
    }

    /**
     * @param depRefundBal the depRefundBal to set
     */
    public void setDepRefundBal(final Long depRefundBal) {
        this.depRefundBal = depRefundBal;
    }

    /**
     * @return the depReceivedfrom
     */
    public String getDepReceivedfrom() {
        return depReceivedfrom;
    }

    /**
     * @param depReceivedfrom the depReceivedfrom to set
     */
    public void setDepReceivedfrom(final String depReceivedfrom) {
        this.depReceivedfrom = depReceivedfrom;
    }

    /**
     * @return the depNarration
     */
    public String getDepNarration() {
        return depNarration;
    }

    /**
     * @param depNarration the depNarration to set
     */
    public void setDepNarration(final String depNarration) {
        this.depNarration = depNarration;
    }

    /**
     * @return the sacHeadId
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * @param sacHeadId the sacHeadId to set
     */
    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the dmFlag
     */
    public String getDmFlag() {
        return dmFlag;
    }

    /**
     * @param dmFlag the dmFlag to set
     */
    public void setDmFlag(final String dmFlag) {
        this.dmFlag = dmFlag;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
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
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /**
     * @return the cpd_STATUS
     */
    public TbComparamDetEntity getCpd_STATUS() {
        return cpd_STATUS;
    }

    /**
     * @param cpd_STATUS the cpd_STATUS to set
     */
    public void setCpd_STATUS(final TbComparamDetEntity cpd_STATUS) {
        this.cpd_STATUS = cpd_STATUS;
    }

    /**
     * @return the tbVendormaster
     */
    public TbAcVendormasterEntity getTbVendormaster() {
        return tbVendormaster;
    }

    /**
     * @param tbVendormaster the tbVendormaster to set
     */
    public void setTbVendormaster(final TbAcVendormasterEntity tbVendormaster) {
        this.tbVendormaster = tbVendormaster;
    }

    /**
     * @return the tbDepartment
     */
    public Department getTbDepartment() {
        return tbDepartment;
    }

    /**
     * @param tbDepartment the tbDepartment to set
     */
    public void setTbDepartment(final Department tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    /**
     * @return the cpd_SOURCE_TYPE
     */
    public TbComparamDetEntity getCpd_SOURCE_TYPE() {
        return cpd_SOURCE_TYPE;
    }

    /**
     * @param cpd_SOURCE_TYPE the cpd_SOURCE_TYPE to set
     */
    public void setCpd_SOURCE_TYPE(final TbComparamDetEntity cpd_SOURCE_TYPE) {
        this.cpd_SOURCE_TYPE = cpd_SOURCE_TYPE;
    }

    /**
     * @return the cpd_DEPOSIT_TYPE
     */
    public TbComparamDetEntity getCpd_DEPOSIT_TYPE() {
        return cpd_DEPOSIT_TYPE;
    }

    /**
     * @param cpd_DEPOSIT_TYPE the cpd_DEPOSIT_TYPE to set
     */
    public void setCpd_DEPOSIT_TYPE(final TbComparamDetEntity cpd_DEPOSIT_TYPE) {
        this.cpd_DEPOSIT_TYPE = cpd_DEPOSIT_TYPE;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountDepositDto [depId=" + depId + ", depEntryDate=" + depEntryDate + ", depReceiptdt="
                + depReceiptdt + ", depNo=" + depNo + ", depReceiptno=" + depReceiptno + ", depAmount=" + depAmount
                + ", depRefundBal=" + depRefundBal + ", depReceivedfrom=" + depReceivedfrom + ", depNarration="
                + depNarration + ", sacHeadId=" + sacHeadId + ", dmFlag=" + dmFlag + ", orgid=" + orgid
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + ", langId=" + langId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1="
                + fi04Lo1 + ", cpd_STATUS=" + cpd_STATUS + ", tbVendormaster=" + tbVendormaster + ", tbDepartment="
                + tbDepartment + ", cpd_SOURCE_TYPE=" + cpd_SOURCE_TYPE + ", cpd_DEPOSIT_TYPE=" + cpd_DEPOSIT_TYPE
                + "]";
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

}
