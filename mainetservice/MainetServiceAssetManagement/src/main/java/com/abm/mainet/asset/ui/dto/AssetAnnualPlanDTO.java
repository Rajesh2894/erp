package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.LocationMasEntity;

public class AssetAnnualPlanDTO implements Serializable {

    private static final long serialVersionUID = 7304308999386437507L;

    private Long astAnnualPlanId;

    private Long financialYear;

    private LocationMasEntity locationMas;

    private String locationDesc;

    private Department department;

    private String departDesc;

    private List<AssetAnnualPlanDetailsDTO> astAnnualPlanDetailsDTO;

    private Long orgId;

    private Date createdDate;

    private Long createdBy;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String deptCode;

    private String status;

    private String dateDesc;

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

    public List<AssetAnnualPlanDetailsDTO> getAstAnnualPlanDetailsDTO() {
        return astAnnualPlanDetailsDTO;
    }

    public void setAstAnnualPlanDetailsDTO(List<AssetAnnualPlanDetailsDTO> astAnnualPlanDetailsDTO) {
        this.astAnnualPlanDetailsDTO = astAnnualPlanDetailsDTO;
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

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getDepartDesc() {
        return departDesc;
    }

    public void setDepartDesc(String departDesc) {
        this.departDesc = departDesc;
    }

    @Override
    public String toString() {
        return "AssetAnnualPlanDTO [astAnnualPlanId=" + astAnnualPlanId + ", financialYear=" + financialYear + ", locationMas="
                + locationMas + ", locationDesc=" + locationDesc + ", department=" + department + ", departDesc=" + departDesc
                + ", astAnnualPlanDetailsDTO=" + astAnnualPlanDetailsDTO + ", orgId=" + orgId + ", createdDate=" + createdDate
                + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", deptCode=" + deptCode + ", status=" + status + ", dateDesc="
                + dateDesc + "]";
    }

}
