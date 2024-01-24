
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
@Table(name = "TB_AC_PROJECTEDREVENUE")

public class AccountBudgetProjectedRevenueEntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PR_PROJECTIONID", nullable = false)
    private Long prProjectionid;

    @Column(name = "PR_REV_BUDGETCODE", nullable = false)
    private String prRevBudgetCode;

    @Column(name = "ORGINAL_ESTAMT")
    private BigDecimal orginalEstamt;

    @Column(name = "PR_PROJECTED", nullable = false)
    private BigDecimal prProjected;

    @Column(name = "REVISED_ESTAMT")
    private String revisedEstamt;
    
    @Column(name = "EXPECTED_ESTAMT")
    private BigDecimal expectedCurrentYear;

    @Column(name = "NXT_YR_OE")
    private String nxtYrOe;

    @Column(name = "PR_COLLECTED")
    private BigDecimal prCollected;

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

   /* @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;   // this filed is rename as below(Find_ID) filed*/
    
    @Column(name = "FUND_ID",nullable=true)
    private Long fundId;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    public AccountBudgetProjectedRevenueEntryEntity() {
        super();
    }

    @Column(name = "FA_YEARID")
    private Long faYearid;
    
    @Column(name = "REMARK",nullable=true)
    private String remark;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PROJECTEDREVENUE", "PR_PROJECTIONID" };
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
    }

    public Long getPrProjectionid() {
        return prProjectionid;
    }

    public void setOrginalEstamt(final BigDecimal orginalEstamt) {
        this.orginalEstamt = orginalEstamt;
    }

    public BigDecimal getOrginalEstamt() {
        return orginalEstamt;
    }

    public void setPrProjected(final BigDecimal prProjected) {
        this.prProjected = prProjected;
    }

    public BigDecimal getPrProjected() {
        return prProjected;
    }

    public void setRevisedEstamt(final String revisedEstamt) {
        this.revisedEstamt = revisedEstamt;
    }

    public String getRevisedEstamt() {
        return revisedEstamt;
    }
    
    

    public BigDecimal getExpectedCurrentYear() {
		return expectedCurrentYear;
	}

	public void setExpectedCurrentYear(BigDecimal expectedCurrentYear) {
		this.expectedCurrentYear = expectedCurrentYear;
	}

	public void setNxtYrOe(final String nxtYrOe) {
        this.nxtYrOe = nxtYrOe;
    }

    public String getNxtYrOe() {
        return nxtYrOe;
    }

    public void setPrCollected(final BigDecimal prCollected) {
        this.prCollected = prCollected;
    }

    public BigDecimal getPrCollected() {
        return prCollected;
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
	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

   /* public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }*/

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
     * @return the prRevBudgetCode
     */
    public String getPrRevBudgetCode() {
        return prRevBudgetCode;
    }

    /**
     * @param prRevBudgetCode the prRevBudgetCode to set
     */
    public void setPrRevBudgetCode(final String prRevBudgetCode) {
        this.prRevBudgetCode = prRevBudgetCode;
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

    public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(prProjectionid);
        sb.append("[");
        sb.append(prRevBudgetCode);
        sb.append("]:");
        sb.append(orginalEstamt);
        sb.append("|");
        sb.append(prProjected);
        sb.append("|");
        sb.append(revisedEstamt);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
        sb.append("|");
        sb.append(nxtYrOe);
        sb.append("|");
        sb.append(prCollected);
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
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append("fundId");
        return sb.toString();
    }

}
