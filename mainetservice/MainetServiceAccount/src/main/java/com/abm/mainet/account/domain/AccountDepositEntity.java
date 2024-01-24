package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_DEPOSITS")
public class AccountDepositEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DEP_ID", nullable = false)
    private Long depId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEP_RECEIPTDT")
    private Date depReceiptdt;

    @Column(name = "DEP_NO", nullable = false)
    private String depNo;

    @Column(name = "DEP_REFERENCENO")
    private String depReferenceNo;

    @Column(name = "DEP_AMOUNT", nullable = false)
    private BigDecimal depAmount;

    @Column(name = "DEP_REFUND_BAL")
    private BigDecimal depRefundBal;

    @Column(name = "DEP_RECEIVEDFROM", nullable = false)
    private String depReceivedfrom;

    @Column(name = "DEP_NARRATION", nullable = false)
    private String depNarration;

    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "DEP_DEL_FLAG", length = 1)
    private String dep_del_flag;

    @Column(name = "DEP_DEL_AUTH_BY")
    private Long dep_del_auth_by;

    @Column(name = "DEP_DEL_REMARK")
    private String dep_del_remark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEP_DEL_DATE")
    private Date dep_del_date;

    @Column(name = "DEP_ENTRY_TYPE", length = 1)
    private String depEntryTypeFlag;

    @ManyToOne
    @JoinColumn(name = "CPD_STATUS", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDetEntity3;

    @ManyToOne
    @JoinColumn(name = "VM_VENDORID", referencedColumnName = "VM_VENDORID")
    private TbAcVendormasterEntity tbVendormaster;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;

    @ManyToOne
    @JoinColumn(name = "CPD_DEPOSIT_TYPE", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDetEntity;

    @ManyToOne
    @JoinColumn(name = "BM_ID", referencedColumnName = "BM_ID")
    private AccountBillEntryMasterEnitity accountBillEntryMaster;

    @ManyToOne
    @JoinColumn(name = "VOU_ID", referencedColumnName = "VOU_ID")
    private AccountVoucherEntryEntity accountVoucherEntry;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "PAYMENT_ID")
    private AccountPaymentMasterEntity accountPaymentMaster;

    @ManyToOne
    @JoinColumn(name = "TR_TENDER_ID", referencedColumnName = "TR_TENDER_ID")
    private AccountTenderEntryEntity accountTenderEntry;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RM_RCPTID", referencedColumnName = "RM_RCPTID")
    private TbServiceReceiptMasEntity tbServiceReceiptMas;
    
    @Column(name = "DEFECT_LIABLITY_DATE")
    private Date defectLiabilityDate ;

    public AccountDepositEntity() {
        super();
    }

    public void setDepId(final Long depId) {
        this.depId = depId;
    }

    public Long getDepId() {
        return depId;
    }

    /*
     * public void setDepReceiptdt(final Date depReceiptdt) { this.depReceiptdt = depReceiptdt; } public Date getDepReceiptdt() {
     * return depReceiptdt; }
     */

    public String getDepNo() {
		return depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}
	
	public String getDepReferenceNo() {
	        return depReferenceNo;
	    }

	public void setDepReferenceNo(String depReferenceNo) {
        this.depReferenceNo = depReferenceNo;
    }

    public void setDepAmount(final BigDecimal depAmount) {
        this.depAmount = depAmount;
    }

    public BigDecimal getDepAmount() {
        return depAmount;
    }

    public void setDepRefundBal(final BigDecimal depRefundBal) {
        this.depRefundBal = depRefundBal;
    }

    public BigDecimal getDepRefundBal() {
        return depRefundBal;
    }

    public void setDepReceivedfrom(final String depReceivedfrom) {
        this.depReceivedfrom = depReceivedfrom;
    }

    public String getDepReceivedfrom() {
        return depReceivedfrom;
    }

    public void setDepNarration(final String depNarration) {
        this.depNarration = depNarration;
    }

    public String getDepNarration() {
        return depNarration;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
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

    public void setTbComparamDet3(final TbComparamDetEntity tbComparamDetEntity3) {
        this.tbComparamDetEntity3 = tbComparamDetEntity3;
    }

    public TbComparamDetEntity getTbComparamDet3() {
        return tbComparamDetEntity3;
    }

    public void setTbVendormaster(final TbAcVendormasterEntity tbVendormaster) {
        this.tbVendormaster = tbVendormaster;
    }

    public TbAcVendormasterEntity getTbVendormaster() {
        return tbVendormaster;
    }

    public void setTbDepartment(final Department tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    public Department getTbDepartment() {
        return tbDepartment;
    }

    public void setTbComparamDet(final TbComparamDetEntity tbComparamDetEntity) {
        this.tbComparamDetEntity = tbComparamDetEntity;
    }

    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDetEntity;
    }

    public Long getDep_del_auth_by() {
        return dep_del_auth_by;
    }

    public void setDep_del_auth_by(final Long dep_del_auth_by) {
        this.dep_del_auth_by = dep_del_auth_by;
    }

    public String getDep_del_remark() {
        return dep_del_remark;
    }

    public void setDep_del_remark(final String dep_del_remark) {
        this.dep_del_remark = dep_del_remark;
    }

    public Date getDep_del_date() {
        return dep_del_date;
    }

    public void setDep_del_date(final Date dep_del_date) {
        this.dep_del_date = dep_del_date;
    }

    public String getDepEntryTypeFlag() {
        return depEntryTypeFlag;
    }

    public void setDepEntryTypeFlag(String depEntryTypeFlag) {
        this.depEntryTypeFlag = depEntryTypeFlag;
    }

    public String getDep_del_flag() {
        return dep_del_flag;
    }

    public void setDep_del_flag(final String dep_del_flag) {
        this.dep_del_flag = dep_del_flag;
    }

    public AccountBillEntryMasterEnitity getAccountBillEntryMaster() {
        return accountBillEntryMaster;
    }

    public void setAccountBillEntryMaster(final AccountBillEntryMasterEnitity accountBillEntryMaster) {
        this.accountBillEntryMaster = accountBillEntryMaster;
    }

    public AccountVoucherEntryEntity getAccountVoucherEntry() {
        return accountVoucherEntry;
    }

    public void setAccountVoucherEntry(final AccountVoucherEntryEntity accountVoucherEntry) {
        this.accountVoucherEntry = accountVoucherEntry;
    }

    public AccountPaymentMasterEntity getAccountPaymentMaster() {
        return accountPaymentMaster;
    }

    public void setAccountPaymentMaster(final AccountPaymentMasterEntity accountPaymentMaster) {
        this.accountPaymentMaster = accountPaymentMaster;
    }

    public AccountTenderEntryEntity getAccountTenderEntry() {
        return accountTenderEntry;
    }

    public void setAccountTenderEntry(final AccountTenderEntryEntity accountTenderEntry) {
        this.accountTenderEntry = accountTenderEntry;
    }

    public TbServiceReceiptMasEntity getTbServiceReceiptMas() {
        return tbServiceReceiptMas;
    }

    public void setTbServiceReceiptMas(final TbServiceReceiptMasEntity tbServiceReceiptMas) {
        this.tbServiceReceiptMas = tbServiceReceiptMas;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_DEPOSITS", "DEP_ID" };
    }

    public Date getDepReceiptdt() {
        return depReceiptdt;
    }

    public void setDepReceiptdt(Date depReceiptdt) {
        this.depReceiptdt = depReceiptdt;
    }

    public Date getDefectLiabilityDate() {
		return defectLiabilityDate;
	}
	public void setDefectLiabilityDate(Date defectLiabilityDate) {
		this.defectLiabilityDate = defectLiabilityDate;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccountDepositEntity [depId=");
        builder.append(depId);
        builder.append(", depReceiptdt=");
        builder.append(depReceiptdt);
        builder.append(", depNo=");
        builder.append(depNo);
        builder.append(", depReferenceNo=");
        builder.append(depReferenceNo);
        builder.append(", depAmount=");
        builder.append(depAmount);
        builder.append(", depRefundBal=");
        builder.append(depRefundBal);
        builder.append(", depReceivedfrom=");
        builder.append(depReceivedfrom);
        builder.append(", depNarration=");
        builder.append(depNarration);
        builder.append(", sacHeadId=");
        builder.append(sacHeadId);
        builder.append(", orgid=");
        builder.append(orgid);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", dep_del_flag=");
        builder.append(dep_del_flag);
        builder.append(", dep_del_auth_by=");
        builder.append(dep_del_auth_by);
        builder.append(", dep_del_remark=");
        builder.append(dep_del_remark);
        builder.append(", dep_del_date=");
        builder.append(dep_del_date);
        builder.append(", depEntryTypeFlag=");
        builder.append(depEntryTypeFlag);
        builder.append(", tbComparamDetEntity3=");
        builder.append(tbComparamDetEntity3);
        builder.append(", tbVendormaster=");
        builder.append(tbVendormaster);
        builder.append(", tbDepartment=");
        builder.append(tbDepartment);
        builder.append(", tbComparamDetEntity=");
        builder.append(tbComparamDetEntity);
        builder.append(", accountBillEntryMaster=");
        builder.append(accountBillEntryMaster);
        builder.append(", accountVoucherEntry=");
        builder.append(accountVoucherEntry);
        builder.append(", accountPaymentMaster=");
        builder.append(accountPaymentMaster);
        builder.append(", accountTenderEntry=");
        builder.append(accountTenderEntry);
        builder.append(", tbServiceReceiptMas=");
        builder.append(tbServiceReceiptMas);
        builder.append("]");
        return builder.toString();
    }

}
