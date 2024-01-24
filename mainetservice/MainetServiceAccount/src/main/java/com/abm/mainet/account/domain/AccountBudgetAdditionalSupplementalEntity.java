
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

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_PROJECTEDPROVISIONADJ")

public class AccountBudgetAdditionalSupplementalEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PA_ADJID", nullable = false)
    private Long paAdjid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    @Column(name = "CPD_BUGTYPE_ID", nullable = false)
    private Long cpdBugtypeId;

    @Column(name = "CPD_ID_BUDGETSUBTYPE", nullable = false)
    private Long cpdBugSubTypeId;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long dpDeptid;

    @Column(name = "CPD_PROVTYPE_ID", nullable = false)
    private BigDecimal cpdProvtypeId;

    @Column(name = "FA_YEARID")
    private Long faYearid;

    @Column(name = "PR_PROJECTIONID")
    private Long prProjectionid;

    @Column(name = "PR_EXPENDITUREID")
    private Long prExpenditureid;

    @Column(name = "PROVISION_OLDAMT")
    private BigDecimal provisionOldamt;

    @Column(name = "ORG_REV_BALAMT")
    private BigDecimal orgRevBalamt;

    @Column(name = "TRANSFER_AMOUNT")
    private BigDecimal transferAmount;

    @Column(name = "NEW_ORG_REV_AMOUNT")
    private BigDecimal newOrgRevAmount;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "BUDGET_IDENTIFY_FLAG")
    private String budgetIdentifyFlag;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

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

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "RP_TRNNO", length = 40)
    private String budgetTranRefNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "AUTH_FLG", length = 1, nullable = false)
    private String authFlag;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "APPROVED_BY", referencedColumnName = "EMPID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBudgetAdditionalSupplementalEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPaAdjid(final Long paAdjid) {
        this.paAdjid = paAdjid;
    }

    public Long getPaAdjid() {
        return paAdjid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : PA_ENTRYDATE ( DATE )

    // --- DATABASE MAPPING : CPD_BUGTYPE_ID ( NUMBER )
    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
    }

    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    // --- DATABASE MAPPING : CPD_PROVTYPE_ID ( NUMBER )
    public void setCpdProvtypeId(final BigDecimal cpdProvtypeId) {
        this.cpdProvtypeId = cpdProvtypeId;
    }

    public BigDecimal getCpdProvtypeId() {
        return cpdProvtypeId;
    }

    // --- DATABASE MAPPING : FA_YEARID ( NUMBER )
    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    // --- DATABASE MAPPING : PR_PROJECTIONID ( NUMBER )
    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
    }

    public Long getPrProjectionid() {
        return prProjectionid;
    }

    // --- DATABASE MAPPING : PR_EXPENDITUREID ( NUMBER )
    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    // --- DATABASE MAPPING : PROVISION_OLDAMT ( NUMBER )
    public void setProvisionOldamt(final BigDecimal provisionOldamt) {
        this.provisionOldamt = provisionOldamt;
    }

    public BigDecimal getProvisionOldamt() {
        return provisionOldamt;
    }

    // --- DATABASE MAPPING : ORG_REV_BALAMT ( NUMBER )
    public void setOrgRevBalamt(final BigDecimal orgRevBalamt) {
        this.orgRevBalamt = orgRevBalamt;
    }

    public BigDecimal getOrgRevBalamt() {
        return orgRevBalamt;
    }

    // --- DATABASE MAPPING : TRANSFER_AMOUNT ( NUMBER )
    public void setTransferAmount(final BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    // --- DATABASE MAPPING : NEW_ORG_REV_AMOUNT ( NUMBER )
    public void setNewOrgRevAmount(final BigDecimal newOrgRevAmount) {
        this.newOrgRevAmount = newOrgRevAmount;
    }

    public BigDecimal getNewOrgRevAmount() {
        return newOrgRevAmount;
    }

    // --- DATABASE MAPPING : REMARK ( NVARCHAR2 )
    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : FI04_N1 ( NUMBER )
    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    // --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public String getBudgetTranRefNo() {
        return budgetTranRefNo;
    }

    public void setBudgetTranRefNo(String budgetTranRefNo) {
        this.budgetTranRefNo = budgetTranRefNo;
    }

    // --- DATABASE MAPPING : FI04_D1 ( DATE )
    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    // --- DATABASE MAPPING : FI04_LO1 ( CHAR )
    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------

    /**
     * @return the cpdBugSubTypeId
     */
    public Long getCpdBugSubTypeId() {
        return cpdBugSubTypeId;
    }

    /**
     * @param cpdBugSubTypeId the cpdBugSubTypeId to set
     */
    public void setCpdBugSubTypeId(final Long cpdBugSubTypeId) {
        this.cpdBugSubTypeId = cpdBugSubTypeId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(final Employee employee) {
        this.employee = employee;
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

    /**
     * @return the budgetIdentifyFlag
     */
    public String getBudgetIdentifyFlag() {
        return budgetIdentifyFlag;
    }

    /**
     * @param budgetIdentifyFlag the budgetIdentifyFlag to set
     */
    public void setBudgetIdentifyFlag(final String budgetIdentifyFlag) {
        this.budgetIdentifyFlag = budgetIdentifyFlag;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(paAdjid);
        /*
         * sb.append("]:"); sb.append(paEntrydate);
         */
        sb.append("|");
        sb.append(cpdBugtypeId);
        sb.append("|");
        sb.append(dpDeptid);
        sb.append("|");
        sb.append(cpdBugSubTypeId);
        sb.append("|");
        sb.append(cpdProvtypeId);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(prProjectionid);
        sb.append("|");
        sb.append(prExpenditureid);
        sb.append("|");
        sb.append(provisionOldamt);
        sb.append("|");
        sb.append(orgRevBalamt);
        sb.append("|");
        sb.append(transferAmount);
        sb.append("|");
        sb.append(newOrgRevAmount);
        sb.append("|");
        sb.append(remark);
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
        sb.append(fi04N1);
        sb.append("|");
        sb.append(budgetTranRefNo);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(authFlag);
        return sb.toString();
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_PROJECTEDPROVISIONADJ", "PA_ADJID" };
    }

}
