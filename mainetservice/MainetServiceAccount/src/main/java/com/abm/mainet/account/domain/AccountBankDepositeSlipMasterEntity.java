
package com.abm.mainet.account.domain;

/**
 * @author prasant.sahu
 *
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BANK_DEPOSITSLIP_MASTER")
public class AccountBankDepositeSlipMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DPS_SLIPID")
    private Long depositeSlipId;

    @OneToMany(mappedBy = "depositeSlipId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountBankDepositeSlipDenomEntity> denominationEntityList;

    @Column(name = "DPS_SLIPNO")
    private String depositeSlipNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "DPS_DEPOSIT_DATE")
    private Date dpsDepositDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "DPS_SLIPDATE")
    private Date depositeSlipDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "DPS_FROMDATE")
    private Date depositeFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "DPS_TODATE")
    private Date depositeToDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department deptId;

    @Column(name = "DPS_TYPE")
    private String depositeTypeFlag;

    @Column(name = "BA_ACCOUNTID")
    private Long depositeBAAccountId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "FUND_ID", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity fundId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "FIELD_ID", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity fieldId;

    @Column(name = "DPS_REMARK")
    private String depositeRemark;

    @Column(name = "DPS_AMOUNT")
    private BigDecimal depositeAmount;

    @Column(name = "AUTH_FLAG")
    private String depositeAuthFlag;

    @Column(name = "AUTH_BY")
    private Long depositeAuthBy;

    @Column(name = "AUTH_DATE")
    private Date depositeAuthDate;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "DEPOSITSLIP_TYPE_FLAG", length = 1)
    private Character depositTypeFlag;

    @Column(name = "CO_TYPE_FLAG", length = 1)
    private Character coTypeFlag;

    @Column(name = "DPS_DEL_AUTH_BY")
    private Long dps_del_auth_by;

    @Column(name = "DPS_DEL_FLAG", length = 1)
    private String dps_del_flag;

    @Temporal(TemporalType.DATE)
    @Column(name = "DPS_DEL_DATE")
    private Date dps_del_date;

    @Column(name = "DPS_DEL_ORDER_NO")
    private String dps_del_order_no;

    @Column(name = "DPS_DEL_REMARK")
    private String dps_del_remark;

    /**
     * @return the depositeSlipId
     */
    public Long getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @return the denominationEntityList
     */
    public List<AccountBankDepositeSlipDenomEntity> getDenominationEntityList() {
        return denominationEntityList;
    }

    /**
     * @param denominationEntityList the denominationEntityList to set
     */
    public void setDenominationEntityList(
            final List<AccountBankDepositeSlipDenomEntity> denominationEntityList) {
        this.denominationEntityList = denominationEntityList;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final Long depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

   

    public String getDepositeSlipNumber() {
		return depositeSlipNumber;
	}

	public void setDepositeSlipNumber(String depositeSlipNumber) {
		this.depositeSlipNumber = depositeSlipNumber;
	}

	/**
     * @return the depositeSlipDate
     */
    public Date getDepositeSlipDate() {
        return depositeSlipDate;
    }

    /**
     * @param depositeSlipDate the depositeSlipDate to set
     */
    public void setDepositeSlipDate(final Date depositeSlipDate) {
        this.depositeSlipDate = depositeSlipDate;
    }

    /**
     * @return the depositeAmount
     */
    public BigDecimal getDepositeAmount() {
        return depositeAmount;
    }

    /**
     * @param depositeAmount the depositeAmount to set
     */
    public void setDepositeAmount(final BigDecimal depositeAmount) {
        this.depositeAmount = depositeAmount;
    }

    /**
     * @return the depositeTypeFlag
     */
    public String getDepositeTypeFlag() {
        return depositeTypeFlag;
    }

    /**
     * @param depositeTypeFlag the depositeTypeFlag to set
     */
    public void setDepositeTypeFlag(final String depositeTypeFlag) {
        this.depositeTypeFlag = depositeTypeFlag;
    }

    /**
     * @return the depositeFromDate
     */
    public Date getDepositeFromDate() {
        return depositeFromDate;
    }

    /**
     * @param depositeFromDate the depositeFromDate to set
     */
    public void setDepositeFromDate(final Date depositeFromDate) {
        this.depositeFromDate = depositeFromDate;
    }

    /**
     * @return the depositeToDate
     */
    public Date getDepositeToDate() {
        return depositeToDate;
    }

    /**
     * @param depositeToDate the depositeToDate to set
     */
    public void setDepositeToDate(final Date depositeToDate) {
        this.depositeToDate = depositeToDate;
    }

    /**
     * @return the deptId
     */
    public Department getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Department deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the depositeBAAccountId
     */
    public Long getDepositeBAAccountId() {
        return depositeBAAccountId;
    }

    /**
     * @param depositeBAAccountId the depositeBAAccountId to set
     */
    public void setDepositeBAAccountId(final Long depositeBAAccountId) {
        this.depositeBAAccountId = depositeBAAccountId;
    }

    /**
     * @return the fundId
     */
    public AccountFundMasterEntity getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final AccountFundMasterEntity fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the fieldId
     */
    public AccountFieldMasterEntity getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final AccountFieldMasterEntity fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the depositeAuthDate
     */
    public Date getDepositeAuthDate() {
        return depositeAuthDate;
    }

    /**
     * @param depositeAuthDate the depositeAuthDate to set
     */
    public void setDepositeAuthDate(final Date depositeAuthDate) {
        this.depositeAuthDate = depositeAuthDate;
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
     * @return the depositeAuthFlag
     */
    public String getDepositeAuthFlag() {
        return depositeAuthFlag;
    }

    /**
     * @param depositeAuthFlag the depositeAuthFlag to set
     */
    public void setDepositeAuthFlag(final String depositeAuthFlag) {
        this.depositeAuthFlag = depositeAuthFlag;
    }

    /**
     * @return the depositeRemark
     */
    public String getDepositeRemark() {
        return depositeRemark;
    }

    /**
     * @param depositeRemark the depositeRemark to set
     */
    public void setDepositeRemark(final String depositeRemark) {
        this.depositeRemark = depositeRemark;
    }

    /**
     * @return the depositeAuthBy
     */
    public Long getDepositeAuthBy() {
        return depositeAuthBy;
    }

    /**
     * @param depositeAuthBy the depositeAuthBy to set
     */
    public void setDepositeAuthBy(final Long depositeAuthBy) {
        this.depositeAuthBy = depositeAuthBy;
    }

    /**
     * @return the depositTypeFlag
     */
    public Character getDepositTypeFlag() {
        return depositTypeFlag;
    }

    /**
     * @param depositTypeFlag the depositTypeFlag to set
     */
    public void setDepositTypeFlag(final Character depositTypeFlag) {
        this.depositTypeFlag = depositTypeFlag;
    }

    /**
     * @return the coTypeFlag
     */
    public Character getCoTypeFlag() {
        return coTypeFlag;
    }

    /**
     * @param coTypeFlag the coTypeFlag to set
     */
    public void setCoTypeFlag(final Character coTypeFlag) {
        this.coTypeFlag = coTypeFlag;
    }

    public Long getDps_del_auth_by() {
        return dps_del_auth_by;
    }

    public void setDps_del_auth_by(final Long dps_del_auth_by) {
        this.dps_del_auth_by = dps_del_auth_by;
    }

    public String getDps_del_flag() {
        return dps_del_flag;
    }

    public void setDps_del_flag(final String dps_del_flag) {
        this.dps_del_flag = dps_del_flag;
    }

    public Date getDps_del_date() {
        return dps_del_date;
    }

    public void setDps_del_date(final Date dps_del_date) {
        this.dps_del_date = dps_del_date;
    }

    public String getDps_del_order_no() {
        return dps_del_order_no;
    }

    public void setDps_del_order_no(final String dps_del_order_no) {
        this.dps_del_order_no = dps_del_order_no;
    }

    public String getDps_del_remark() {
        return dps_del_remark;
    }

    public void setDps_del_remark(final String dps_del_remark) {
        this.dps_del_remark = dps_del_remark;
    }

    public Date getDpsDepositDate() {
		return dpsDepositDate;
	}

	public void setDpsDepositDate(Date dpsDepositDate) {
		this.dpsDepositDate = dpsDepositDate;
	}

	public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BANK_DEPOSITSLIP_MASTER", "DPS_SLIPID" };
    }

}
