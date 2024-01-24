package com.abm.mainet.dashboard.citizen.dto;

import java.io.Serializable;

public class WaterDashboardDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2327247799762266211L;
    private String connectionNo;
    private String applDate;
    private String deptName;
    private String deptShortName;

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptShortName() {
        return deptShortName;
    }

    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

}
