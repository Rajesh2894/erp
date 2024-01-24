package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */

@Entity
@Table(name = "TB_ADH_BILL_MAST")
public class ADHBillMasEntity implements Serializable {

    private static final long serialVersionUID = -7220184218438919206L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "bm_bmno")
    private Long billMasNo;

    @Column(name = "cont_id")
    private Long contractId;

    @Column(name = "bm_billdate")
    private Date billDate;

    @Column(name = "bm_amount")
    private Double billAmount;

    @Column(name = "bm_paid_amt")
    private Double paidAmount;

    @Column(name = "bm_balance_amt")
    private Double balanceAmount;

    @Column(name = "bm_paid_flag")
    private String paidFlag;

    @Column(name = "bm_active")
    private String active;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "conit_id")
    private Long conitId;

    @Column(name = "bm_billno")
    private Long billNo;

    @Column(name = "bm_due_date")
    private Date dueDate;

    @Column(name = "bm_paymnet_date")
    private Date paymentDate;

    @Column(name = "Created_date")
    private Date createdDate;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "bm_start_date")
    private Date startDate;

    @Column(name = "bm_type")
    private String billType;

    @Column(name = "bm_remark")
    private String remarks;

    public Long getBillMasNo() {
	return billMasNo;
    }

    public void setBillMasNo(Long billMasNo) {
	this.billMasNo = billMasNo;
    }

    public Long getContractId() {
	return contractId;
    }

    public void setContractId(Long contractId) {
	this.contractId = contractId;
    }

    public Date getBillDate() {
	return billDate;
    }

    public void setBillDate(Date billDate) {
	this.billDate = billDate;
    }

    public Double getBillAmount() {
	return billAmount;
    }

    public void setBillAmount(Double billAmount) {
	this.billAmount = billAmount;
    }

    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	this.paidAmount = paidAmount;
    }

    public Double getBalanceAmount() {
	return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
	this.balanceAmount = balanceAmount;
    }

    public String getPaidFlag() {
	return paidFlag;
    }

    public void setPaidFlag(String paidFlag) {
	this.paidFlag = paidFlag;
    }

    public String getActive() {
	return active;
    }

    public void setActive(String active) {
	this.active = active;
    }

    public Long getOrgId() {
	return orgId;
    }

    public void setOrgId(Long orgId) {
	this.orgId = orgId;
    }

    public Long getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
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

    public String getLgIpMac() {
	return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
	this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
	return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getConitId() {
	return conitId;
    }

    public void setConitId(Long conitId) {
	this.conitId = conitId;
    }

    public Long getBillNo() {
	return billNo;
    }

    public void setBillNo(Long billNo) {
	this.billNo = billNo;
    }

    public Date getDueDate() {
	return dueDate;
    }

    public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
	return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Long getTaxId() {
	return taxId;
    }

    public void setTaxId(Long taxId) {
	this.taxId = taxId;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public String getBillType() {
	return billType;
    }

    public void setBillType(String billType) {
	this.billType = billType;
    }

    public String getRemarks() {
	return remarks;
    }

    public void setRemarks(String remarks) {
	this.remarks = remarks;
    }

    public String[] getPkValues() {
	return new String[] { "ADH", "TB_ADH_BILL_MAST", "bm_bmno" };
    }
}
