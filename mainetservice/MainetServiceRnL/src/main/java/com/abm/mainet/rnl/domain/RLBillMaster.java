package com.abm.mainet.rnl.domain;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author ritesh.patil
 *
 * Bill Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_BILL_MAST")
public class RLBillMaster implements Cloneable {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BM_BMNO", nullable = false)
    private Long billId;

    @Column(name = "CONT_ID", nullable = false)
    private Long contId;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_BILLDATE")
    private Date billDate;

    @Column(name = "BM_AMOUNT", precision = 15, scale = 2)
    private Double amount;

    @Column(name = "BM_PAID_AMT", precision = 15, scale = 2)
    private Double paidAmount;

    @Column(name = "BM_BALANCE_AMT", precision = 15, scale = 2)
    private Double balanceAmount;

    @Column(name = "BM_PAID_FLAG")
    private String paidFlag;

    @Column(name = "BM_ACTIVE")
    private String active;

    @Column(name = "CONIT_ID")
    private Long conitId;

    @Column(name = "BM_BILLNO")
    private Long bmBillNo;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_DUE_DATE")
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_PAYMNET_DATE")
    private Date paymentDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "BM_START_DATE")
    private Date startDate;

    @Column(name = "TAX_ID", nullable = false)
    private Long taxId;

    @Column(name = "BM_TYPE")
    private String bmType;

    @Column(name = "BM_REMARK")
    private String remarks;
   
    
    @Column(name = "partial_paid_flag")
    private String partialPaidFlag;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(final Long billId) {
        this.billId = billId;
    }

    public Long getContId() {
        return contId;
    }

    public void setContId(final Long contId) {
        this.contId = contId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(final Date billDate) {
        this.billDate = billDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(final Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPaidFlag() {
        return paidFlag;
    }

    public void setPaidFlag(final String paidFlag) {
        this.paidFlag = paidFlag;
    }

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

    public Long getConitId() {
        return conitId;
    }

    public void setConitId(final Long conitId) {
        this.conitId = conitId;
    }

    public Long getBmBillNo() {
        return bmBillNo;
    }

    public void setBmBillNo(final Long bmBillNo) {
        this.bmBillNo = bmBillNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(final Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(final Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.RnLDetailEntity.TB_RL_BILL_MAST,
                MainetConstants.RnLDetailEntity.BM_BMNO };
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public String getBmType() {
        return bmType;
    }

    public void setBmType(final String bmType) {
        this.bmType = bmType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

   

	public String getPartialPaidFlag() {
		return partialPaidFlag;
	}

	public void setPartialPaidFlag(String partialPaidFlag) {
		this.partialPaidFlag = partialPaidFlag;
	}

	@Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    public static Comparator<RLBillMaster> sortByIntrest = (o1, o2) -> o2.getBmType().compareTo(o1.getBmType());
}
