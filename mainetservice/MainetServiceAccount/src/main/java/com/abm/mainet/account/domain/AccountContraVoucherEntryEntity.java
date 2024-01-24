package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 * @since 08 Sep 2016
 */
@Entity
@Table(name = "TB_AC_CONTRADET")
public class AccountContraVoucherEntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_CONTRADET", "CO_TRAN_ID" };
    }

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CO_TRAN_ID", nullable = false)
    private Long coTranId;

    @Column(name = "CO_ENTRY_DATE", nullable = true)
    private Date coEntryDate;

    @Column(name = "CO_DATE", nullable = true)
    private Date coDate;

    @Column(name = "CO_VOUCHERNUMBER", nullable = true)
    private String coVouchernumber;

    @Column(name = "CO_TYPE_FLAG", nullable = true)
    private String coTypeFlag;

    @ManyToOne
    @JoinColumn(name = "BA_ACCOUNTID_PAY", referencedColumnName = "BA_ACCOUNTID")
    private BankAccountMasterEntity baAccountidPay;

    @ManyToOne
    @JoinColumn(name = "FUND_ID_PAY", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity fundIdPay;

    @ManyToOne
    @JoinColumn(name = "FUNCTION_ID_PAY", referencedColumnName = "FUNCTION_ID")
    private AccountFunctionMasterEntity functionIdPay;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID_PAY", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity fieldIdPay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "PAC_HEAD_ID_PAY", referencedColumnName = "PAC_HEAD_ID")
    private AccountHeadPrimaryAccountCodeMasterEntity pacHeadIdPay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "SAC_HEAD_ID_PAY", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity sacHeadIdPay;

    @Column(name = "CO_AMOUNT_PAY", nullable = true)
    private BigDecimal coAmountPay;

    @ManyToOne
    @JoinColumn(name = "CPD_MODE_PAY", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity cpdModePay;

    @ManyToOne
    @JoinColumn(name = "CHEQUEBOOK_DETID", referencedColumnName = "CHEQUEBOOK_DETID")
    private TbAcChequebookleafDetEntity chequebookDetid;

    @Column(name = "CO_CHEQUEDATE", nullable = true)
    private Date coChequedate;

    @Column(name = "CO_REMARK_PAY", nullable = true)
    private String coRemarkPay;

    @ManyToOne
    @JoinColumn(name = "BA_ACCOUNTID_REC", referencedColumnName = "BA_ACCOUNTID")
    private BankAccountMasterEntity baAccountidRec;

    @ManyToOne
    @JoinColumn(name = "FUND_ID_REC", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity fundIdRec;

    @ManyToOne
    @JoinColumn(name = "FUNCTION_ID_REC", referencedColumnName = "FUNCTION_ID")
    private AccountFunctionMasterEntity functionIdRec;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID_REC", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity fieldIdRec;

    @ManyToOne
    @JoinColumn(name = "PAC_HEAD_ID_REC", referencedColumnName = "PAC_HEAD_ID")
    private AccountHeadPrimaryAccountCodeMasterEntity pacHeadIdRec;

    @ManyToOne
    @JoinColumn(name = "SAC_HEAD_ID_REC", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity sacHeadIdRec;

    @Column(name = "CO_AMOUNT_REC", nullable = true)
    private BigDecimal coAmountRec;

    @ManyToOne
    @JoinColumn(name = "CPD_MODE_REC", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity cpdModeRec;

    @Column(name = "CO_REMARK_REC", nullable = true)
    private String coRemarkRec;

    @Column(name = "AMT_IN_COINS", nullable = true)
    private Double amtInCoins;

    @Column(name = "AUTH_FLAG", nullable = true)
    private String authFlag;

    @Column(name = "AUTH_BY", nullable = true)
    private Long authBy;

    @Column(name = "AUTH_DATE", nullable = true)
    private Date authDate;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "LANG_ID", nullable = true)
    private Long langId;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", nullable = true)
    private Long fi04N1;

    @Column(name = "FI04_V1", nullable = true)
    private String fi04V1;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "FI04_LO1", nullable = true)
    private String fi04Lo1;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "coTranId", cascade = CascadeType.ALL)
    private List<AccountContraVoucherEntryEntity> contraVoucher = new ArrayList<>();

    @OneToMany(mappedBy = "tbAcContradet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AccountContraVouherEntryCashDepEntity> cashDeposit = new ArrayList<>(0);

    /** @return the coTranId */
    public Long getCoTranId() {
        return coTranId;
    }

    /**
     * @param coTranId the coTranId to set
     */
    public void setCoTranId(final Long coTranId) {
        this.coTranId = coTranId;
    }

    /** @return the coEntryDate */
    public Date getCoEntryDate() {
        return coEntryDate;
    }

    /**
     * @param coEntryDate the coEntryDate to set
     */
    public void setCoEntryDate(final Date coEntryDate) {
        this.coEntryDate = coEntryDate;
    }

    /** @return the coDate */
    public Date getCoDate() {
        return coDate;
    }

    /**
     * @param coDate the coDate to set
     */
    public void setCoDate(final Date coDate) {
        this.coDate = coDate;
    }

    /** @return the coVouchernumber */
    public String getCoVouchernumber() {
        return coVouchernumber;
    }

    /**
     * @param coVouchernumber the coVouchernumber to set
     */
    public void setCoVouchernumber(final String coVouchernumber) {
        this.coVouchernumber = coVouchernumber;
    }

    /** @return the coTypeFlag */
    public String getCoTypeFlag() {
        return coTypeFlag;
    }

    /**
     * @param coTypeFlag the coTypeFlag to set
     */
    public void setCoTypeFlag(final String coTypeFlag) {
        this.coTypeFlag = coTypeFlag;
    }

    /** @return the baAccountidPay */
    public BankAccountMasterEntity getBaAccountidPay() {
        return baAccountidPay;
    }

    /**
     * @param baAccountidPay the baAccountidPay to set
     */
    public void setBaAccountidPay(final BankAccountMasterEntity baAccountidPay) {
        this.baAccountidPay = baAccountidPay;
    }

    /** @return the fundIdPay */
    public AccountFundMasterEntity getFundIdPay() {
        return fundIdPay;
    }

    /**
     * @param fundIdPay the fundIdPay to set
     */
    public void setFundIdPay(final AccountFundMasterEntity fundIdPay) {
        this.fundIdPay = fundIdPay;
    }

    /** @return the functionIdPay */
    public AccountFunctionMasterEntity getFunctionIdPay() {
        return functionIdPay;
    }

    /**
     * @param functionIdPay the functionIdPay to set
     */
    public void setFunctionIdPay(final AccountFunctionMasterEntity functionIdPay) {
        this.functionIdPay = functionIdPay;
    }

    /** @return the fieldIdPay */
    public AccountFieldMasterEntity getFieldIdPay() {
        return fieldIdPay;
    }

    /**
     * @param fieldIdPay the fieldIdPay to set
     */
    public void setFieldIdPay(final AccountFieldMasterEntity fieldIdPay) {
        this.fieldIdPay = fieldIdPay;
    }

    /** @return the pacHeadIdPay */
    public AccountHeadPrimaryAccountCodeMasterEntity getPacHeadIdPay() {
        return pacHeadIdPay;
    }

    /**
     * @param pacHeadIdPay the pacHeadIdPay to set
     */
    public void setPacHeadIdPay(final AccountHeadPrimaryAccountCodeMasterEntity pacHeadIdPay) {
        this.pacHeadIdPay = pacHeadIdPay;
    }

    /** @return the sacHeadIdPay */
    public AccountHeadSecondaryAccountCodeMasterEntity getSacHeadIdPay() {
        return sacHeadIdPay;
    }

    /**
     * @param sacHeadIdPay the sacHeadIdPay to set
     */
    public void setSacHeadIdPay(final AccountHeadSecondaryAccountCodeMasterEntity sacHeadIdPay) {
        this.sacHeadIdPay = sacHeadIdPay;
    }

    /** @return the coAmountPay */
    public BigDecimal getCoAmountPay() {
        return coAmountPay;
    }

    /**
     * @param coAmountPay the coAmountPay to set
     */
    public void setCoAmountPay(final BigDecimal coAmountPay) {
        this.coAmountPay = coAmountPay;
    }

    /** @return the cpdModePay */
    public TbComparamDetEntity getCpdModePay() {
        return cpdModePay;
    }

    /**
     * @param cpdModePay the cpdModePay to set
     */
    public void setCpdModePay(final TbComparamDetEntity cpdModePay) {
        this.cpdModePay = cpdModePay;
    }

    /** @return the chequebookDetid */
    public TbAcChequebookleafDetEntity getChequebookDetid() {
        return chequebookDetid;
    }

    /**
     * @param chequebookDetid the chequebookDetid to set
     */
    public void setChequebookDetid(final TbAcChequebookleafDetEntity chequebookDetid) {
        this.chequebookDetid = chequebookDetid;
    }

    /** @return the coChequedate */
    public Date getCoChequedate() {
        return coChequedate;
    }

    /**
     * @param coChequedate the coChequedate to set
     */
    public void setCoChequedate(final Date coChequedate) {
        this.coChequedate = coChequedate;
    }

    /** @return the coRemarkPay */
    public String getCoRemarkPay() {
        return coRemarkPay;
    }

    /**
     * @param coRemarkPay the coRemarkPay to set
     */
    public void setCoRemarkPay(final String coRemarkPay) {
        this.coRemarkPay = coRemarkPay;
    }

    /** @return the baAccountidRec */
    public BankAccountMasterEntity getBaAccountidRec() {
        return baAccountidRec;
    }

    /**
     * @param baAccountidRec the baAccountidRec to set
     */
    public void setBaAccountidRec(final BankAccountMasterEntity baAccountidRec) {
        this.baAccountidRec = baAccountidRec;
    }

    /** @return the fundIdRec */
    public AccountFundMasterEntity getFundIdRec() {
        return fundIdRec;
    }

    /**
     * @param fundIdRec the fundIdRec to set
     */
    public void setFundIdRec(final AccountFundMasterEntity fundIdRec) {
        this.fundIdRec = fundIdRec;
    }

    /** @return the functionIdRec */
    public AccountFunctionMasterEntity getFunctionIdRec() {
        return functionIdRec;
    }

    /**
     * @param functionIdRec the functionIdRec to set
     */
    public void setFunctionIdRec(final AccountFunctionMasterEntity functionIdRec) {
        this.functionIdRec = functionIdRec;
    }

    /** @return the fieldIdRec */
    public AccountFieldMasterEntity getFieldIdRec() {
        return fieldIdRec;
    }

    /**
     * @param fieldIdRec the fieldIdRec to set
     */
    public void setFieldIdRec(final AccountFieldMasterEntity fieldIdRec) {
        this.fieldIdRec = fieldIdRec;
    }

    /** @return the pacHeadIdRec */
    public AccountHeadPrimaryAccountCodeMasterEntity getPacHeadIdRec() {
        return pacHeadIdRec;
    }

    /**
     * @param pacHeadIdRec the pacHeadIdRec to set
     */
    public void setPacHeadIdRec(final AccountHeadPrimaryAccountCodeMasterEntity pacHeadIdRec) {
        this.pacHeadIdRec = pacHeadIdRec;
    }

    /** @return the sacHeadIdRec */
    public AccountHeadSecondaryAccountCodeMasterEntity getSacHeadIdRec() {
        return sacHeadIdRec;
    }

    /**
     * @param sacHeadIdRec the sacHeadIdRec to set
     */
    public void setSacHeadIdRec(final AccountHeadSecondaryAccountCodeMasterEntity sacHeadIdRec) {
        this.sacHeadIdRec = sacHeadIdRec;
    }

    /** @return the coAmountRec */
    public BigDecimal getCoAmountRec() {
        return coAmountRec;
    }

    /**
     * @param coAmountRec the coAmountRec to set
     */
    public void setCoAmountRec(final BigDecimal coAmountRec) {
        this.coAmountRec = coAmountRec;
    }

    /** @return the cpdModeRec */
    public TbComparamDetEntity getCpdModeRec() {
        return cpdModeRec;
    }

    /**
     * @param cpdModeRec the cpdModeRec to set
     */
    public void setCpdModeRec(final TbComparamDetEntity cpdModeRec) {
        this.cpdModeRec = cpdModeRec;
    }

    /** @return the coRemarkRec */
    public String getCoRemarkRec() {
        return coRemarkRec;
    }

    /**
     * @param coRemarkRec the coRemarkRec to set
     */
    public void setCoRemarkRec(final String coRemarkRec) {
        this.coRemarkRec = coRemarkRec;
    }

    /** @return the amtInCoins */
    public Double getAmtInCoins() {
        return amtInCoins;
    }

    /**
     * @param amtInCoins the amtInCoins to set
     */
    public void setAmtInCoins(final Double amtInCoins) {
        this.amtInCoins = amtInCoins;
    }

    /** @return the authFlag */
    public String getAuthFlag() {
        return authFlag;
    }

    /**
     * @param authFlag the authFlag to set
     */
    public void setAuthFlag(final String authFlag) {
        this.authFlag = authFlag;
    }

    /** @return the authBy */
    public Long getAuthBy() {
        return authBy;
    }

    /**
     * @param authBy the authBy to set
     */
    public void setAuthBy(final Long authBy) {
        this.authBy = authBy;
    }

    /** @return the authDate */
    public Date getAuthDate() {
        return authDate;
    }

    /**
     * @param authDate the authDate to set
     */
    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
    }

    /** @return the orgId */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /** @return the createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /** @return the createdDate */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /** @return the langId */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /** @return the updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** @return the updatedDate */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /** @return the lgIpMac */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /** @return the lgIpMacUpd */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /** @return the fi04N1 */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /** @return the fi04V1 */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /** @return the fi04D1 */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /** @return the fi04Lo1 */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /** @return the contraVoucher */
    public List<AccountContraVoucherEntryEntity> getContraVoucher() {
        return contraVoucher;
    }

    /**
     * @param contraVoucher the contraVoucher to set
     */
    public void setContraVoucher(final List<AccountContraVoucherEntryEntity> contraVoucher) {
        this.contraVoucher = contraVoucher;
    }

    /** @return the cashDeposit */
    public List<AccountContraVouherEntryCashDepEntity> getCashDeposit() {
        return cashDeposit;
    }

    /**
     * @param cashDeposit the cashDeposit to set
     */
    public void setCashDeposit(final List<AccountContraVouherEntryCashDepEntity> cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

}
