
package com.abm.mainet.cms.domain;

import java.io.Serializable;
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
@Table(name = "TB_PORTAL_SKILLED_YOUTH_DASH")
public class SkilledYouthDashboardDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4966461796784523140L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "SKYUPLD_ID", nullable = false)
    private Long id;

    @Column(name = "DISTRICT_ENG", nullable = true)
    private String districtEng;

    @Column(name = "DISTRICT", nullable = true)
    private String district;

    @Column(name = "TOT_APP", nullable = true)
    private Long totAppl;

    @Column(name = "TOT_TRAN_LBR_DP", nullable = true)
    private Long totTranDp;

    @Column(name = "TOT_APPLICANTS_CALLED", nullable = true)
    private Long totAppCalled;

    @Column(name = "TOT_CUR_INTERNSHIP", nullable = true)
    private Long totCurInternship;

    @Column(name = "TOT_TRAINED_APPLICANTS", nullable = true)
    private Long totTrainedApplicants;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPLD_DT", nullable = true)
    private Date upldDt;

    @Column(name = "CREATED_BY", nullable = true)
    private Long upldBy;

    @Transient
    public String[] getPkValues() {

        return new String[] { "CMS", "TB_PORTAL_SKILLED_YOUTH_DASH", "SKYUPLD_ID" };
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

    public Long getTotTranDp() {
        return totTranDp;
    }

    public void setTotTranDp(Long totTranDp) {
        this.totTranDp = totTranDp;
    }

    public Long getTotAppCalled() {
        return totAppCalled;
    }

    public void setTotAppCalled(Long totAppCalled) {
        this.totAppCalled = totAppCalled;
    }

    public Long getTotCurInternship() {
        return totCurInternship;
    }

    public void setTotCurInternship(Long totCurInternship) {
        this.totCurInternship = totCurInternship;
    }

    public Long getTotTrainedApplicants() {
        return totTrainedApplicants;
    }

    public void setTotTrainedApplicants(Long totTrainedApplicants) {
        this.totTrainedApplicants = totTrainedApplicants;
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