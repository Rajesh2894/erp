
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
@Table(name = "TB_PORTAL_WOMEN_DASH_DET")
public class WomenDashboardDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8327332803582747803L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "WDUPLD_ID", nullable = false)
    private Long id;

    @Column(name = "DISTRICT_ENG", nullable = true)
    private String districtEng;

    @Column(name = "DISTRICT", nullable = true)
    private String district;

    @Column(name = "TOT_PERMANENT", nullable = true)
    private Long totPermanent;

    @Column(name = "TOT_STANDING", nullable = true)
    private Long totStanding;

    @Column(name = "TOT_APPOINTED", nullable = true)
    private Long totAppointed;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPLD_DT", nullable = true)
    private Date upldDt;

    @Column(name = "CREATED_BY", nullable = true)
    private Long upldBy;

    @Transient
    public String[] getPkValues() {

        return new String[] { "CMS", "TB_PORTAL_WOMEN_DASH_DET", "WDUPLD_ID" };
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

    public Long getTotPermanent() {
        return totPermanent;
    }

    public void setTotPermanent(Long totPermanent) {
        this.totPermanent = totPermanent;
    }

    public Long getTotStanding() {
        return totStanding;
    }

    public void setTotStanding(Long totStanding) {
        this.totStanding = totStanding;
    }

    public Long getTotAppointed() {
        return totAppointed;
    }

    public void setTotAppointed(Long totAppointed) {
        this.totAppointed = totAppointed;
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