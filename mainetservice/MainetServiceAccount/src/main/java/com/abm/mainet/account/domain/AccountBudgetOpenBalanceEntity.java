package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BUGOPEN_BALANCE")

public class AccountBudgetOpenBalanceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OPN_ID", nullable = false)
    private Long opnId;

    /*
     * @Temporal(TemporalType.DATE)
     * @Column(name = "OPN_ENTRYDATE", nullable = false) private Date opnEntrydate;
     */

    @Column(name = "OPENBAL_AMT")
    private String openbalAmt;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "CLO_BAL_AMT")
    private BigDecimal cloBalAmt;

    @Column(name = "SUR_DEF_AMT")
    private BigDecimal surDefAmt;

    @Column(name = "CLO_BAL_TYPE")
    private Long cloBalType;

    @Column(name = "OPNBAL_TYPE", length = 1)
    private String opnBalType;

    @ManyToOne
    @JoinColumn(name = "SAC_HEAD_ID", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster;

    @ManyToOne
    @JoinColumn(name = "FUNCTION_ID", referencedColumnName = "FUNCTION_ID")
    private AccountFunctionMasterEntity tbAcFunctionMaster;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity tbAcFieldMaster;

    @ManyToOne
    @JoinColumn(name = "FUND_ID", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity tbAcFundMaster;

    @ManyToOne
    @JoinColumn(name = "PAC_HEAD_ID", referencedColumnName = "PAC_HEAD_ID")
    private AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster;

    @Column(name = "FA_YEARID")
    private Long faYearid;

    @Column(name = "FINALIZED_FLAG")
    private String flagFlzd;

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    /**
     * @return the flagFlzd
     */
    public String getFlagFlzd() {
        return flagFlzd;
    }

    /**
     * @param flagFlzd the flagFlzd to set
     */
    public void setFlagFlzd(final String flagFlzd) {
        this.flagFlzd = flagFlzd;
    }

    @ManyToOne
    @JoinColumn(name = "CPD_ID_DRCR", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet;

    public AccountBudgetOpenBalanceEntity() {
        super();
    }

    public void setOpnId(final Long opnId) {
        this.opnId = opnId;
    }

    public Long getOpnId() {
        return opnId;
    }

    public void setOpenbalAmt(final String openbalAmt) {
        this.openbalAmt = openbalAmt;
    }

    public String getOpenbalAmt() {
        return openbalAmt;
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

    public void setOpnBalType(final String opnBalType) {
        this.opnBalType = opnBalType;
    }

    public String getOpnBalType() {
        return opnBalType;
    }

    public void setTbAcSecondaryheadMaster(final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMaster) {
        this.tbAcSecondaryheadMaster = tbAcSecondaryheadMaster;
    }

    public AccountHeadSecondaryAccountCodeMasterEntity getTbAcSecondaryheadMaster() {
        return tbAcSecondaryheadMaster;
    }

    public void setTbAcFunctionMaster(final AccountFunctionMasterEntity tbAcFunctionMaster) {
        this.tbAcFunctionMaster = tbAcFunctionMaster;
    }

    public AccountFunctionMasterEntity getTbAcFunctionMaster() {
        return tbAcFunctionMaster;
    }

    public void setTbAcFieldMaster(final AccountFieldMasterEntity tbAcFieldMaster) {
        this.tbAcFieldMaster = tbAcFieldMaster;
    }

    public AccountFieldMasterEntity getTbAcFieldMaster() {
        return tbAcFieldMaster;
    }

    public void setTbAcFundMaster(final AccountFundMasterEntity tbAcFundMaster) {
        this.tbAcFundMaster = tbAcFundMaster;
    }

    public AccountFundMasterEntity getTbAcFundMaster() {
        return tbAcFundMaster;
    }

    public void setTbAcPrimaryheadMaster(final AccountHeadPrimaryAccountCodeMasterEntity tbAcPrimaryheadMaster) {
        this.tbAcPrimaryheadMaster = tbAcPrimaryheadMaster;
    }

    public AccountHeadPrimaryAccountCodeMasterEntity getTbAcPrimaryheadMaster() {
        return tbAcPrimaryheadMaster;
    }

    public void setTbComparamDet(final TbComparamDetEntity tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDet;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BUGOPEN_BALANCE", "OPN_ID" };
    }

    public BigDecimal getCloBalAmt() {
        return cloBalAmt;
    }

    public void setCloBalAmt(BigDecimal cloBalAmt) {
        this.cloBalAmt = cloBalAmt;
    }

    public BigDecimal getSurDefAmt() {
        return surDefAmt;
    }

    public void setSurDefAmt(BigDecimal surDefAmt) {
        this.surDefAmt = surDefAmt;
    }

    public Long getCloBalType() {
        return cloBalType;
    }

    public void setCloBalType(Long cloBalType) {
        this.cloBalType = cloBalType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(opnId);
        sb.append("]:");
        sb.append(faYearid);
        sb.append("]:");
        sb.append(flagFlzd);
        sb.append("|");
        sb.append(openbalAmt);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(cloBalAmt);
        sb.append("|");
        sb.append(surDefAmt);
        sb.append("|");
        sb.append(cloBalType);
        sb.append("|");
        sb.append(opnBalType);
        return sb.toString();
    }

}
