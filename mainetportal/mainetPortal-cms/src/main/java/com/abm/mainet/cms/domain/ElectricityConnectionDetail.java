
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
@Table(name = "TB_PORTAL_ELECTRICITY_CONN")
public class ElectricityConnectionDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4966461796784523140L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")

    @Column(name = "EUPLD_ID", nullable = false)
    private Long id;

    @Column(name = "DISTRICT_ENG", nullable = true)
    private String districtEng;

    @Column(name = "DISTRICT", nullable = true)
    private String district;

    @Column(name = "TOT_VILLAGE", nullable = true)
    private Long totVillage;

    @Column(name = "TOT_HOUSEHOLD", nullable = true)
    private Long totHouseholds;

    @Column(name = "TOT_VILLAGE_ELECTRIFY", nullable = true)
    private Long totVillageElec;

    @Column(name = "TOT_HOUSEHOLD_ELECTRIFY", nullable = true)
    private Long totHouseElec;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPLD_DT", nullable = true)
    private Date upldDt;

    @Column(name = "UPLD_BY", nullable = true)
    private Long upldBy;

    @Transient
    public String[] getPkValues() {

        return new String[] { "CMS", "TB_PORTAL_ELECTRICITY_CONN", "EUPLD_ID" };
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

    public Long getTotVillage() {
        return totVillage;
    }

    public void setTotVillage(Long totVillage) {
        this.totVillage = totVillage;
    }

    public Long getTotHouseholds() {
        return totHouseholds;
    }

    public void setTotHouseholds(Long totHouseholds) {
        this.totHouseholds = totHouseholds;
    }

    public Long getTotVillageElec() {
        return totVillageElec;
    }

    public void setTotVillageElec(Long totVillageElec) {
        this.totVillageElec = totVillageElec;
    }

    public Long getTotHouseElec() {
        return totHouseElec;
    }

    public void setTotHouseElec(Long totHouseElec) {
        this.totHouseElec = totHouseElec;
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