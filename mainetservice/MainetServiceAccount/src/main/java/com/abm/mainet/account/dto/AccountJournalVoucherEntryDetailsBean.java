
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 *
 */
public class AccountJournalVoucherEntryDetailsBean implements Serializable {

    private static final long serialVersionUID = -6031962707732082129L;

    private long voudetId;

    @JsonBackReference
    private AccountJournalVoucherEntryBean master;

    private Long fundId;

    private Long functionId;

    private Long sacHeadId;

    private BigDecimal voudetAmt;

    private Long drcrCpdId;

    private Long orgId;

    private Long createdBy;

    private int langId;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String deleted = "N";

    private AccountBudgetCodeDto budgetCode;

    private Map<Long, String> accountHeadCodeList = new HashMap<>();
    private List<AccountJournalVoucherEntryDetailsBean> accountHeadList = null;
    private String acHeadCode;

    /**
     * @return the voudetId
     */
    public long getVoudetId() {
        return voudetId;
    }

    /**
     * @param voudetId the voudetId to set
     */
    public void setVoudetId(final long voudetId) {
        this.voudetId = voudetId;
    }

    /**
     * @return the master
     */
    public AccountJournalVoucherEntryBean getMaster() {
        return master;
    }

    /**
     * @param master the master to set
     */
    public void setMaster(final AccountJournalVoucherEntryBean master) {
        this.master = master;
    }

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the functionId
     */
    public Long getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId the functionId to set
     */
    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    /**
     * @return the sacHeadId
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * @param sacHeadId the sacHeadId to set
     */
    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the voudetAmt
     */
    public BigDecimal getVoudetAmt() {
        return voudetAmt;
    }

    /**
     * @param voudetAmt the voudetAmt to set
     */
    public void setVoudetAmt(final BigDecimal voudetAmt) {
        this.voudetAmt = voudetAmt;
    }

    /**
     * @return the drcrCpdId
     */
    public Long getDrcrCpdId() {
        return drcrCpdId;
    }

    /**
     * @param drcrCpdId the drcrCpdId to set
     */
    public void setDrcrCpdId(final Long drcrCpdId) {
        this.drcrCpdId = drcrCpdId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
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
     * @return the langId
     */
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public static Comparator<AccountJournalVoucherEntryDetailsBean> compareOnvoutDetId = (o1, o2) -> Integer
            .valueOf(String.valueOf(o1.getDrcrCpdId() - o2.getDrcrCpdId()));

    @Override
    public boolean equals(final Object object) {
        boolean sameSame = false;

        if ((object != null) && (object instanceof AccountJournalVoucherEntryDetailsBean)) {
            sameSame = getVoudetId() == ((AccountJournalVoucherEntryDetailsBean) object).getVoudetId();
        }

        return sameSame;
    }

    /**
     * @return the deleted
     */
    public String getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(final String deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the budgetCode
     */
    public AccountBudgetCodeDto getBudgetCode() {
        return budgetCode;
    }

    /**
     * @param budgetCode the budgetCode to set
     */
    public void setBudgetCode(final AccountBudgetCodeDto budgetCode) {
        this.budgetCode = budgetCode;
    }

    /**
     * @return the accountHeadCodeList
     */
    public Map<Long, String> getAccountHeadCodeList() {
        return accountHeadCodeList;
    }

    /**
     * @param accountHeadCodeList the accountHeadCodeList to set
     */
    public void setAccountHeadCodeList(final Map<Long, String> accountHeadCodeList) {
        this.accountHeadCodeList = accountHeadCodeList;
    }

    public List<AccountJournalVoucherEntryDetailsBean> getAccountHeadList() {
        return accountHeadList;
    }

    public void setAccountHeadList(final List<AccountJournalVoucherEntryDetailsBean> accountHeadList) {
        this.accountHeadList = accountHeadList;
    }

	public String getAcHeadCode() {
		return acHeadCode;
	}

	public void setAcHeadCode(String acHeadCode) {
		this.acHeadCode = acHeadCode;
	}

}
