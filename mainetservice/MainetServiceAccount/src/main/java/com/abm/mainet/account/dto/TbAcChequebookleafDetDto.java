/**
 * 
 */
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.account.domain.TbAcChequebookleafMasEntity;

/**
 * @author Anwarul.Hassan
 * @since 18-Dec-2019
 */
public class TbAcChequebookleafDetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chequebookDetid;
    private String chequeNo;
    private Long cpdIdstatus;
    private Long paymentId;
    private String remark;
    private Long stopPayOrderNo;
    private Date stopPayOrderDate;
    private String stopPayFlag;
    private String stopPayRemark;
    private Date stoppayDate;
    private String paymentType;
    private Date issuanceDate;
    private Date cancellationDate;
    private String cancellationReason;
    private Long newIssueCheqeuBookDetId;
    private Long orgid;
    private Long userId;
    private Date lmoddate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long fi04N1;
    private String fi04V1;
    private Date fi04D1;
    private String fi04Lo1;
    private TbAcChequebookleafMasEntity tbAcChequebookleafMas;

    public Long getChequebookDetid() {
        return chequebookDetid;
    }

    public void setChequebookDetid(final Long chequebookDetid) {
        this.chequebookDetid = chequebookDetid;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(final String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Long getCpdIdstatus() {
        return cpdIdstatus;
    }

    public void setCpdIdstatus(final Long cpdIdstatus) {
        this.cpdIdstatus = cpdIdstatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public Long getStopPayOrderNo() {
        return stopPayOrderNo;
    }

    public void setStopPayOrderNo(final Long stopPayOrderNo) {
        this.stopPayOrderNo = stopPayOrderNo;
    }

    public Date getStopPayOrderDate() {
        return stopPayOrderDate;
    }

    public void setStopPayOrderDate(final Date stopPayOrderDate) {
        this.stopPayOrderDate = stopPayOrderDate;
    }

    public String getStopPayFlag() {
        return stopPayFlag;
    }

    public void setStopPayFlag(final String stopPayFlag) {
        this.stopPayFlag = stopPayFlag;
    }

    public String getStopPayRemark() {
        return stopPayRemark;
    }

    public void setStopPayRemark(final String stopPayRemark) {
        this.stopPayRemark = stopPayRemark;
    }

    public Date getStoppayDate() {
        return stoppayDate;
    }

    public void setStoppayDate(final Date stoppayDate) {
        this.stoppayDate = stoppayDate;
    }

    public Date getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(final Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(final Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(final String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Long getNewIssueCheqeuBookDetId() {
        return newIssueCheqeuBookDetId;
    }

    public void setNewIssueCheqeuBookDetId(final Long newIssueCheqeuBookDetId) {
        this.newIssueCheqeuBookDetId = newIssueCheqeuBookDetId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public TbAcChequebookleafMasEntity getTbAcChequebookleafMas() {
        return tbAcChequebookleafMas;
    }

    public void setTbAcChequebookleafMas(final TbAcChequebookleafMasEntity tbAcChequebookleafMas) {
        this.tbAcChequebookleafMas = tbAcChequebookleafMas;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "TbAcChequebookleafDetEntity [chequebookDetid=" + chequebookDetid + ", chequeNo=" + chequeNo
                + ", cpdIdstatus=" + cpdIdstatus + ", paymentId=" + paymentId + ", remark=" + remark
                + ", stopPayOrderNo=" + stopPayOrderNo + ", stopPayOrderDate=" + stopPayOrderDate + ", stopPayFlag="
                + stopPayFlag + ", stopPayRemark=" + stopPayRemark + ", stoppayDate=" + stoppayDate + ", paymentType="
                + paymentType + ", issuanceDate=" + issuanceDate + ", cancellationDate=" + cancellationDate
                + ", cancellationReason=" + cancellationReason + ", newIssueCheqeuBookDetId=" + newIssueCheqeuBookDetId
                + ", orgid=" + orgid + ", userId=" + userId + ", lmoddate=" + lmoddate
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1="
                + fi04Lo1 + ", tbAcChequebookleafMas=" + tbAcChequebookleafMas + "]";
    }
}
