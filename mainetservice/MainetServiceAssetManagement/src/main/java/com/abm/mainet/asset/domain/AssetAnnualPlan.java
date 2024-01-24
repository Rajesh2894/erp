package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AST_ANNUAL_PLAN_MST")
public class AssetAnnualPlan implements Serializable {

    private static final long serialVersionUID = 3371100670051461547L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "AST_ANN_PLAN_ID", nullable = false)
    private Long astAnnualPlanId;

    @Column(name = "FA_YEARID", precision = 12, scale = 0, nullable = true)
    private Long financialYear;

    /**
     * Mapped to TB_LOCATION_MAS table with LocationMasEntity entity
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_ID", nullable = false)
    private LocationMasEntity locationMas;

    /**
     * Mapped to TB_DEPARTMENT table with LocationMasEntity entity
     */

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false)
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetAnnualPlan", cascade = CascadeType.ALL)
    private List<AssetAnnualPlanDetails> annualPlanDetails = new ArrayList<>();

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    public Long getAstAnnualPlanId() {
        return astAnnualPlanId;
    }

    public void setAstAnnualPlanId(Long astAnnualPlanId) {
        this.astAnnualPlanId = astAnnualPlanId;
    }

    public Long getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Long financialYear) {
        this.financialYear = financialYear;
    }

    public LocationMasEntity getLocationMas() {
        return locationMas;
    }

    public void setLocationMas(LocationMasEntity locationMas) {
        this.locationMas = locationMas;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<AssetAnnualPlanDetails> getAnnualPlanDetails() {
        return annualPlanDetails;
    }

    public void setAnnualPlanDetails(List<AssetAnnualPlanDetails> annualPlanDetails) {
        this.annualPlanDetails = annualPlanDetails;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_ANNUAL_PLAN_MST", "AST_ANN_PLAN_ID" };

    }

}
