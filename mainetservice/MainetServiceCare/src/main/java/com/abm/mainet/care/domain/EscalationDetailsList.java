package com.abm.mainet.care.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class EscalationDetailsList {

    private Integer level;
    private String sla;
    private String empName;
    private String designation;
    private String department;
    private String email;
    private String decision;
    private String remarks;
    private String mobile;
    private String status;
    private String assignmentDate;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(final Integer level) {
        this.level = level;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(final String sla) {
        this.sla = sla;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(final String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(final String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(final String decision) {
        this.decision = decision;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

}
