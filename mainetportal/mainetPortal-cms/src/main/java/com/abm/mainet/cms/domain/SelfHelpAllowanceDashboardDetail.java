
package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Jugnu Pandey
 */
@Entity
@Table(name = "TB_PORTAL_SELF_HELPALLOWACE")
public class SelfHelpAllowanceDashboardDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4966461796784523140L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "UALW_ID", nullable = false)
    private Long id;

    @Column(name = "DISTRICT_ENG", nullable = true)
    private String districtEng;

    @Column(name = "DISTRICT", nullable = true)
    private String district;

    @Column(name = "TOT_APPL", nullable = true)
    private Long totAppl;

    @Column(name = "TOT_APL_ACCOUNTS", nullable = true)
    private Long totAplAccounts;

    @Column(name = "TOT_ALLOWANCE_AMT", nullable = true)
    private BigDecimal totAllowanceAmt;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPLD_DT", nullable = true)
    private Date upldDt;

    @Column(name = "UPLD_BY", nullable = true)
    private Long upldBy;

    @Transient
    public String[] getPkValues() {

        return new String[] { "CMS", "TB_PORTAL_SELF_HELPALLOWACE", "UALW_ID" };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictEng() {
        return districtEng;
    }

    public void setDistrictEng(String districtEng) {
        this.districtEng = districtEng;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getTotAppl() {
        return totAppl;
    }

    public void setTotAppl(Long totAppl) {
        this.totAppl = totAppl;
    }

    public Long getTotAplAccounts() {
        return totAplAccounts;
    }

    public void setTotAplAccounts(Long totAplAccounts) {
        this.totAplAccounts = totAplAccounts;
    }

    public BigDecimal getTotAllowanceAmt() {
        return totAllowanceAmt;
    }

    public void setTotAllowanceAmt(BigDecimal totAllowanceAmt) {
        this.totAllowanceAmt = totAllowanceAmt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpldDt() {
        return upldDt;
    }

    public void setUpldDt(Date upldDt) {
        this.upldDt = upldDt;
    }

    public Long getUpldBy() {
        return upldBy;
    }

    public void setUpldBy(Long upldBy) {
        this.upldBy = upldBy;
    }

}