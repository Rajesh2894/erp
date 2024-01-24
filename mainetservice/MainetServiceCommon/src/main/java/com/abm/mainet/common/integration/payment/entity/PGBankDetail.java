/**
 *
 */
package com.abm.mainet.common.integration.payment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;

/**
 * @author pabitra.raulo
 *
 */
@Entity
@Table(name = "TB_PG_BANK")
public class PGBankDetail implements Serializable {

    private static final long serialVersionUID = -2622245677852886225L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "PG_ID", precision = 15, scale = 0, nullable = false)
    private long pgId;

    @Column(name = "MERCHANT_ID", length = 20, nullable = true)
    private String merchantId;

    @Column(name = "PG_NAME", length = 200, nullable = true)
    private String pgName;

    @Column(name = "PG_URL", length = 300, nullable = true)
    private String pgUrl;

    @Column(name = "PG_STATUS", length = 1, nullable = true)
    private String pgStatus;

    @Column(name = "BANKID", precision = 12, scale = 0, nullable = true)
    private Long bankid;

    @Column(name = "BA_ACCOUNTID", precision = 12, scale = 0, nullable = true)
    private Long baAccountid;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "COMM_N1", precision = 15, scale = 0, nullable = true)
    private Long commN1;

    @Column(name = "COMM_V1", length = 100, nullable = true)
    private String commV1;

    @Column(name = "COMM_D1", nullable = true)
    private Date commD1;

    @Column(name = "COMM_LO1", length = 1, nullable = true)
    private String commLo1;
    
    @Column(name = "DEPTCODE", length = 20, nullable = true)
    private String deptCode;

    /**
     * @return the pgId
     */
    public long getPgId() {
        return pgId;
    }

    /**
     * @param pgId the pgId to set
     */
    public void setPgId(final long pgId) {
        this.pgId = pgId;
    }

    /**
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId the merchantId to set
     */
    public void setMerchantId(final String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the pgName
     */
    public String getPgName() {
        return pgName;
    }

    /**
     * @param pgName the pgName to set
     */
    public void setPgName(final String pgName) {
        this.pgName = pgName;
    }

    /**
     * @return the pgUrl
     */
    public String getPgUrl() {
        return pgUrl;
    }

    /**
     * @param pgUrl the pgUrl to set
     */
    public void setPgUrl(final String pgUrl) {
        this.pgUrl = pgUrl;
    }

    /**
     * @return the pgStatus
     */
    public String getPgStatus() {
        return pgStatus;
    }

    /**
     * @param pgStatus the pgStatus to set
     */
    public void setPgStatus(final String pgStatus) {
        this.pgStatus = pgStatus;
    }

    /**
     * @return the baAccountid
     */
    public Long getBaAccountid() {
        return baAccountid;
    }

    /**
     * @param baAccountid the baAccountid to set
     */
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
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
     * @return the updatedBy
     */
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
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
     * @return the commN1
     */
    public Long getCommN1() {
        return commN1;
    }

    /**
     * @param commN1 the commN1 to set
     */
    public void setCommN1(final Long commN1) {
        this.commN1 = commN1;
    }

    /**
     * @return the commV1
     */
    public String getCommV1() {
        return commV1;
    }

    /**
     * @param commV1 the commV1 to set
     */
    public void setCommV1(final String commV1) {
        this.commV1 = commV1;
    }

    /**
     * @return the commD1
     */
    public Date getCommD1() {
        return commD1;
    }

    /**
     * @param commD1 the commD1 to set
     */
    public void setCommD1(final Date commD1) {
        this.commD1 = commD1;
    }

    /**
     * @return the commLo1
     */
    public String getCommLo1() {
        return commLo1;
    }

    /**
     * @param commLo1 the commLo1 to set
     */
    public void setCommLo1(final String commLo1) {
        this.commLo1 = commLo1;
    }

    public Long getBankid() {
        return bankid;
    }

    public void setBankid(final Long bankid) {
        this.bankid = bankid;
    }

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

    
}
