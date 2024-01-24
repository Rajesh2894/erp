
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

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_PROJECTED_EXPENDITURE")

public class AccountBudgetProjectedExpenditureEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
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
    
    @Column(name = "EXPECTED_ESTAMT")
    private BigDecimal expectedCurrentYearO;
    


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

    // @Column(name = "FI04_V1")
    // private String fi04V1;

    @Column(name = "NXT_YR_OE")
    private BigDecimal nxtYrOe;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    /*@Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;  // this filed is rename as below(Find_ID) filed*/
    
    @Column(name = "FUND_ID",nullable=true)
    private Long fundId;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    public AccountBudgetProjectedExpenditureEntity() {
        super();
    }

    @Column(name = "FA_YEARID")
    private Long faYearid;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;
    
    @Column(name = "REMARK",nullable=true)
    private String remark;
    
    @Column(name = "CUR_YR_SPAMT")
    private BigDecimal curYrSpamt;
    
    @Column(name = "NXT_YR_SPAMT")
    private BigDecimal nxtYrSpamt;
    

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public void setOrginalEstamt(final BigDecimal orginalEstamt) {
        this.orginalEstamt = orginalEstamt;
    }

    public BigDecimal getOrginalEstamt() {
        return orginalEstamt;
    }

    public void setRevisedEstamt(final String revisedEstamt) {
        this.revisedEstamt = revisedEstamt;
    }

    public String getRevisedEstamt() {
        return revisedEstamt;
    }
    
    

    public BigDecimal getExpectedCurrentYearO() {
		return expectedCurrentYearO;
	}

	public void setExpectedCurrentYearO(BigDecimal expectedCurrentYearO) {
		this.expectedCurrentYearO = expectedCurrentYearO;
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

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
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

    /*
     * public void setFi04V1(final String fi04V1) { this.fi04V1 = fi04V1; } public String getFi04V1() { return fi04V1; }
     */
   
	public BigDecimal getNxtYrOe() {
        return nxtYrOe;
    }

    
	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public void setNxtYrOe(BigDecimal nxtYrOe) {
        this.nxtYrOe = nxtYrOe;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    /*public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }*/
    
    

    /**
     * @return the prExpenditureid
     */
    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	/**
     * @param prExpenditureid the prExpenditureid to set
     */
    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    /**
     * @return the expenditureAmt
     */
    public BigDecimal getExpenditureAmt() {
        return expenditureAmt;
    }

    /**
     * @param expenditureAmt the expenditureAmt to set
     */
    public void setExpenditureAmt(final BigDecimal expenditureAmt) {
        this.expenditureAmt = expenditureAmt;
    }

    /**
     * @return the prBalanceAmt
     */
    public BigDecimal getPrBalanceAmt() {
        return prBalanceAmt;
    }

    /**
     * @param prBalanceAmt the prBalanceAmt to set
     */
    public void setPrBalanceAmt(final BigDecimal prBalanceAmt) {
        this.prBalanceAmt = prBalanceAmt;
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
     * @return the prExpBudgetCode
     */
    public String getPrExpBudgetCode() {
        return prExpBudgetCode;
    }

    /**
     * @param prExpBudgetCode the prExpBudgetCode to set
     */
    public void setPrExpBudgetCode(final String prExpBudgetCode) {
        this.prExpBudgetCode = prExpBudgetCode;
    }

    /**
     * @return the cpdBugsubtypeId
     */
    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    /**
     * @param cpdBugsubtypeId the cpdBugsubtypeId to set
     */
    public void setCpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    /**
     * @return the tbAcBudgetCodeMaster
     */
    public AccountBudgetCodeEntity getTbAcBudgetCodeMaster() {
        return tbAcBudgetCodeMaster;
    }

    /**
     * @param tbAcBudgetCodeMaster the tbAcBudgetCodeMaster to set
     */
    public void setTbAcBudgetCodeMaster(
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster) {
        this.tbAcBudgetCodeMaster = tbAcBudgetCodeMaster;
    }
    
    public BigDecimal getCurYrSpamt() {
		return curYrSpamt;
	}

	public void setCurYrSpamt(BigDecimal curYrSpamt) {
		this.curYrSpamt = curYrSpamt;
	}

	public BigDecimal getNxtYrSpamt() {
		return nxtYrSpamt;
	}

	public void setNxtYrSpamt(BigDecimal nxtYrSpamt) {
		this.nxtYrSpamt = nxtYrSpamt;
	}


    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    
	public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PROJECTED_EXPENDITURE", "PR_EXPENDITUREID" };
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(prExpenditureid);
        sb.append("]:");
        sb.append(prExpBudgetCode);
        sb.append("]:");
        sb.append(orginalEstamt);
        sb.append("|");
        sb.append(expenditureAmt);
        sb.append("|");
        sb.append(revisedEstamt);
        sb.append("|");
        sb.append(prBalanceAmt);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
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
        sb.append(fieldId);
        sb.append("|");
        sb.append(nxtYrOe);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append("fundId");
        return sb.toString();
    }

}
