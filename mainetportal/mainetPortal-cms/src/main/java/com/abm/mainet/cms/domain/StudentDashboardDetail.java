
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
@Table(name = "TB_PORTAL_STU_DASH_DET")
public class StudentDashboardDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4966461796784523140L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "UPLD_ID", nullable = false)
    private Long id;

    @Column(name = "DISTRICT_ENG", nullable = true)
    private String districtEng;

    @Column(name = "DISTRICT", nullable = true)
    private String district;

    @Column(name = "TOT_APPL", nullable = true)
    private Long totAppl;

    @Column(name = "TOT_BANK_APPL", nullable = true)
    private Long totBankAppl;

    @Column(name = "TOT_STU_CARD", nullable = true)
    private Long totStuCard;

    @Column(name = "TOT_BNK_LOAN", nullable = true)
    private BigDecimal totoBnkLoan;

    @Column(name = "TOT_BNK_DISTR", nullable = true)
    private BigDecimal totBnkDistr;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPLD_DT", nullable = true)
    private Date upldDt;

    @Column(name = "UPLD_BY", nullable = true)
    private Long upldBy;

    @Transient
    public String[] getPkValues() {

        return new String[] { "CMS", "TB_PORTAL_STU_DASH_DET", "UPLD_ID" };
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

    public Long getTotBankAppl() {
        return totBankAppl;
    }

    public void setTotBankAppl(Long totBankAppl) {
        this.totBankAppl = totBankAppl;
    }

    public Long getTotStuCard() {
        return totStuCard;
    }

    public void setTotStuCard(Long totStuCard) {
        this.totStuCard = totStuCard;
    }

    public BigDecimal getTotoBnkLoan() {
        return totoBnkLoan;
    }

    public void setTotoBnkLoan(BigDecimal totoBnkLoan) {
        this.totoBnkLoan = totoBnkLoan;
    }

    public BigDecimal getTotBnkDistr() {
        return totBnkDistr;
    }

    public void setTotBnkDistr(BigDecimal totBnkDistr) {
        this.totBnkDistr = totBnkDistr;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}