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
@Table(name = "TB_AC_PROJECTED_EXPENDI_HIST")

public class AccountBudgetProjectedExpenditureHistEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PR_EXPENDITUREID_HIST_ID", nullable = false)
    private Long prExpenditureHistId;

    @Column(name = "PR_EXPENDITUREID", nullable = false)
    private Long prExpenditureid;

    @Column(name = "PR_EXP_BUDGETCODE", nullable = false)
    private String prExpBudgetCode;

    @Column(name = "ORGINAL_ESTAMT")
    private BigDecimal orginalEstamt;

    @Column(name = "EXPENDITURE_AMT", nullable = false)
    private BigDecimal expenditureAmt;

    @Column(name = "REVISED_ESTAMT")
    private String revisedEstamt;

    @Column(name = "PR_BALANCE_AMT")
    private BigDecimal prBalanceAmt;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FIELD_ID",nullable = false)
    private Long fieldId;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    /*
     * @ManyToOne
     * @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID") private AccountBudgetCodeEntity
     * tbAcBudgetCodeMaster;
     */

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    @Column(name = "BUDGETCODE_ID", nullable = false)
    private Long tbAcBudgetCodeMaster;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long tbDepartment;

    public AccountBudgetProjectedExpenditureHistEntity() {
        super();
    }

    @Column(name = "FA_YEARID")
    private Long faYearid;

    /*
     * @ManyToOne
     * @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID") private Department tbDepartment;
     */

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PROJECTED_EXPENDI_HIST", "PR_EXPENDITUREID_HIST_ID" };
    }

    public Long getPrExpenditureHistId() {
        return prExpenditureHistId;
    }

    public void setPrExpenditureHistId(Long prExpenditureHistId) {
        this.prExpenditureHistId = prExpenditureHistId;
    }

    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    public void setPrExpenditureid(Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    public String getPrExpBudgetCode() {
        return prExpBudgetCode;
    }

    public void setPrExpBudgetCode(String prExpBudgetCode) {
        this.prExpBudgetCode = prExpBudgetCode;
    }

    public BigDecimal getOrginalEstamt() {
        return orginalEstamt;
    }

    public void setOrginalEstamt(BigDecimal orginalEstamt) {
        this.orginalEstamt = orginalEstamt;
    }

    public BigDecimal getExpenditureAmt() {
        return expenditureAmt;
    }

    public void setExpenditureAmt(BigDecimal expenditureAmt) {
        this.expenditureAmt = expenditureAmt;
    }

    public String getRevisedEstamt() {
        return revisedEstamt;
    }

    public void setRevisedEstamt(String revisedEstamt) {
        this.revisedEstamt = revisedEstamt;
    }

    public BigDecimal getPrBalanceAmt() {
        return prBalanceAmt;
    }

    public void setPrBalanceAmt(BigDecimal prBalanceAmt) {
        this.prBalanceAmt = prBalanceAmt;
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

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
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

    
	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    public void setCpdBugsubtypeId(Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(Long faYearid) {
        this.faYearid = faYearid;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    public Long getTbAcBudgetCodeMaster() {
        return tbAcBudgetCodeMaster;
    }

    public void setTbAcBudgetCodeMaster(Long tbAcBudgetCodeMaster) {
        this.tbAcBudgetCodeMaster = tbAcBudgetCodeMaster;
    }

    public Long getTbDepartment() {
        return tbDepartment;
    }

    public void setTbDepartment(Long tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    @Override
    public String toString() {
        return "AccountBudgetProjectedExpenditureHistEntity [prExpenditureHistId=" + prExpenditureHistId
                + ", prExpenditureid=" + prExpenditureid + ", prExpBudgetCode=" + prExpBudgetCode + ", orginalEstamt="
                + orginalEstamt + ", expenditureAmt=" + expenditureAmt + ", revisedEstamt=" + revisedEstamt
                + ", prBalanceAmt=" + prBalanceAmt + ", orgid=" + orgid + ", userId=" + userId + ", langId=" + langId
                + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fieldId + ", fi04V1=" + fi04V1 + ", fi04D1="
                + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", cpdBugsubtypeId=" + cpdBugsubtypeId + ", tbAcBudgetCodeMaster="
                + tbAcBudgetCodeMaster + ", tbDepartment=" + tbDepartment + ", faYearid=" + faYearid + ", hStatus="
                + hStatus + "]";
    }

}
