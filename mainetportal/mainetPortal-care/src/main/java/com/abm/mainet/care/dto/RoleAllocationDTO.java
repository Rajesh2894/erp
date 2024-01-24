package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleAllocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer organizationId;
    private List<String> employeeIds;
    private List<String> emailIds;
    private String actionSLA;
    private String employeeWithinDepartmentEmpId;
    private String employeeWithinDepartmentEmailId;
    private List<String> employeeByLocationEmpIds;
    private List<String> employeeByLocationEmailId;
    private List<String> employeeByDepartmentEmpIds;
    private List<String> employeeByDepartmentEmailId;

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(final List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<String> getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(final List<String> emailIds) {
        this.emailIds = emailIds;
    }

    public String getActionSLA() {
        return actionSLA;
    }

    public void setActionSLA(final String actionSLA) {
        this.actionSLA = actionSLA;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(final Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getEmployeeWithinDepartmentEmpId() {
        return employeeWithinDepartmentEmpId;
    }

    public void setEmployeeWithinDepartmentEmpId(
            final String employeeWithinDepartmentEmpId) {
        this.employeeWithinDepartmentEmpId = employeeWithinDepartmentEmpId;
    }

    public String getEmployeeWithinDepartmentEmailId() {
        return employeeWithinDepartmentEmailId;
    }

    public void setEmployeeWithinDepartmentEmailId(
            final String employeeWithinDepartmentEmailId) {
        this.employeeWithinDepartmentEmailId = employeeWithinDepartmentEmailId;
    }

    public List<String> getEmployeeByLocationEmpIds() {
        return employeeByLocationEmpIds;
    }

    public void setEmployeeByLocationEmpIds(final List<String> employeeByLocationEmpIds) {
        this.employeeByLocationEmpIds = employeeByLocationEmpIds;
    }

    public List<String> getEmployeeByLocationEmailId() {
        return employeeByLocationEmailId;
    }

    public void setEmployeeByLocationEmailId(final List<String> employeeByLocationEmailId) {
        this.employeeByLocationEmailId = employeeByLocationEmailId;
    }

    public List<String> getEmployeeByDepartmentEmpIds() {
        return employeeByDepartmentEmpIds;
    }

    public void setEmployeeByDepartmentEmpIds(
            final List<String> employeeByDepartmentEmpIds) {
        this.employeeByDepartmentEmpIds = employeeByDepartmentEmpIds;
    }

    public List<String> getEmployeeByDepartmentEmailId() {
        return employeeByDepartmentEmailId;
    }

    public void setEmployeeByDepartmentEmailId(
            final List<String> employeeByDepartmentEmailId) {
        this.employeeByDepartmentEmailId = employeeByDepartmentEmailId;
    }

}
