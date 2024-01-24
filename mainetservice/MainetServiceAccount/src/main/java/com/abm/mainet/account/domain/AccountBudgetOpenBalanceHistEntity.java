package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BUGOPEN_BALANCE_HIST")
public class AccountBudgetOpenBalanceHistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OPN_ID_H", nullable = false)
    private Long opnHId;

    @Column(name = "OPN_ID")
    private Long opnId;

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

    @Column(name = "SAC_HEAD_ID")
    private Long tbAcSecondaryheadMaster;

    @Column(name = "FUNCTION_ID")
    private Long tbAcFunctionMaster;

    @Column(name = "FIELD_ID")
    private Long tbAcFieldMaster;

    @Column(name = "FUND_ID")
    private Long tbAcFundMaster;

    @Column(name = "PAC_HEAD_ID")
    private Long tbAcPrimaryheadMaster;

    @Column(name = "FA_YEARID")
    private Long faYearid;

    @Column(name = "FINALIZED_FLAG")
    private String flagFlzd;

    @Column(name = "CPD_ID_DRCR")
    private Long tbComparamDet;

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    public AccountBudgetOpenBalanceHistEntity() {
        super();
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BUGOPEN_BALANCE_HIST", "OPN_ID_H" };
    }

    public Long getOpnHId() {
        return opnHId;
    }

    public void setOpnHId(Long opnHId) {
        this.opnHId = opnHId;
    }

    public Long getOpnId() {
        return opnId;
    }

    public void setOpnId(Long opnId) {
        this.opnId = opnId;
    }

    public String getOpenbalAmt() {
        return openbalAmt;
    }

    public void setOpenbalAmt(String openbalAmt) {
        this.openbalAmt = openbalAmt;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public String getOpnBalType() {
        return opnBalType;
    }

    public void setOpnBalType(String opnBalType) {
        this.opnBalType = opnBalType;
    }

    public Long getTbAcSecondaryheadMaster() {
        return tbAcSecondaryheadMaster;
    }

    public void setTbAcSecondaryheadMaster(Long tbAcSecondaryheadMaster) {
        this.tbAcSecondaryheadMaster = tbAcSecondaryheadMaster;
    }

    public Long getTbAcFunctionMaster() {
        return tbAcFunctionMaster;
    }

    public void setTbAcFunctionMaster(Long tbAcFunctionMaster) {
        this.tbAcFunctionMaster = tbAcFunctionMaster;
    }

    public Long getTbAcFieldMaster() {
        return tbAcFieldMaster;
    }

    public void setTbAcFieldMaster(Long tbAcFieldMaster) {
        this.tbAcFieldMaster = tbAcFieldMaster;
    }

    public Long getTbAcFundMaster() {
        return tbAcFundMaster;
    }

    public void setTbAcFundMaster(Long tbAcFundMaster) {
        this.tbAcFundMaster = tbAcFundMaster;
    }

    public Long getTbAcPrimaryheadMaster() {
        return tbAcPrimaryheadMaster;
    }

    public void setTbAcPrimaryheadMaster(Long tbAcPrimaryheadMaster) {
        this.tbAcPrimaryheadMaster = tbAcPrimaryheadMaster;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(Long faYearid) {
        this.faYearid = faYearid;
    }

    public String getFlagFlzd() {
        return flagFlzd;
    }

    public void setFlagFlzd(String flagFlzd) {
        this.flagFlzd = flagFlzd;
    }

    public Long getTbComparamDet() {
        return tbComparamDet;
    }

    public void setTbComparamDet(Long tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    @Override
    public String toString() {
        return "AccountBudgetOpenBalanceHistEntity [opnHId=" + opnHId + ", opnId=" + opnId + ", openbalAmt="
                + openbalAmt + ", orgid=" + orgid + ", userId=" + userId + ", lmoddate=" + lmoddate + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
                + ", cloBalAmt=" + cloBalAmt + ", surDefAmt=" + surDefAmt + ", cloBalType=" + cloBalType
                + ", opnBalType=" + opnBalType + ", tbAcSecondaryheadMaster=" + tbAcSecondaryheadMaster
                + ", tbAcFunctionMaster=" + tbAcFunctionMaster + ", tbAcFieldMaster=" + tbAcFieldMaster
                + ", tbAcFundMaster=" + tbAcFundMaster + ", tbAcPrimaryheadMaster=" + tbAcPrimaryheadMaster
                + ", faYearid=" + faYearid + ", flagFlzd=" + flagFlzd + ", tbComparamDet=" + tbComparamDet
                + ", hStatus=" + hStatus + "]";
    }

}
