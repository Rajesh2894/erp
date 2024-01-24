/**
 *
 */
package com.abm.mainet.common.master.dto;

import java.io.Serializable;

/**
 * @author Harsha.Ramachandran
 *
 */
public class SysModVariables implements Serializable {

    private static final long serialVersionUID = 1L;

    String smfaction;
    String smfname;
    String groupGrCode;
    String groupGrDescEng;
    String groupGrDescReg;
    String groupStatus;
    Long orgId;
    Long sessionEmp;
    String isActive;
    String employeeLoginname;
    String employeePassword;

    /*
     * Getters and Setters
     */
    public String getSmfaction() {
        return smfaction;
    }

    public void setSmfaction(final String smfaction) {
        this.smfaction = smfaction;
    }

    public String getSmfname() {
        return smfname;
    }

    public void setSmfname(final String smfname) {
        this.smfname = smfname;
    }

    public String getGroupGrCode() {
        return groupGrCode;
    }

    public void setGroupGrCode(final String groupGrCode) {
        this.groupGrCode = groupGrCode;
    }

    public String getGroupGrDescEng() {
        return groupGrDescEng;
    }

    public void setGroupGrDescEng(final String groupGrDescEng) {
        this.groupGrDescEng = groupGrDescEng;
    }

    public String getGroupGrDescReg() {
        return groupGrDescReg;
    }

    public void setGroupGrDescReg(final String groupGrDescReg) {
        this.groupGrDescReg = groupGrDescReg;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(final String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getSessionEmp() {
        return sessionEmp;
    }

    public void setSessionEmp(final Long sessionEmp) {
        this.sessionEmp = sessionEmp;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(final String isActive) {
        this.isActive = isActive;
    }

    public String getEmployeeLoginname() {
        return employeeLoginname;
    }

    public void setEmployeeLoginname(final String employeeLoginname) {
        this.employeeLoginname = employeeLoginname;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(final String employeePassword) {
        this.employeePassword = employeePassword;
    }

}
