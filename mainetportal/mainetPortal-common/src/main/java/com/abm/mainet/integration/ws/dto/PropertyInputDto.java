/**
 * 
 */
package com.abm.mainet.integration.ws.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saiprasad.Vengurekar
 *
 */
public class PropertyInputDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2457972750441185797L;

    private String propertyNo;
    
    private String flatNo;

    private Long scheduleId;

    private Date acquisitionDate;

    private Long orgId;

    private Long empId;

    private Long deptId;

    private Long finYearId;

    private int langId;

    private String oldPropertyNo;
    
    private Long applicationId;

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Long getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(Long finYearId) {
        this.finYearId = finYearId;
    }

    public String getOldPropertyNo() {
        return oldPropertyNo;
    }

    public void setOldPropertyNo(String oldPropertyNo) {
        this.oldPropertyNo = oldPropertyNo;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
    
    
}
