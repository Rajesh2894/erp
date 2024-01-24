package com.abm.mainet.care.dto.report;

import java.io.Serializable;

public class ComplaintGradeSummary implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String department;
    private String complaintType;
    private String orgName;
    private long received;
    private long pendingWithinSla;
    private long pendingBeyondSla;
    private long closedWithinSla;
    private long closedBeyondSla;
    private double redressalWithinSla;
    private double redressalBeyondSla;
    private double redressal;
    private String grade;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    public long getPendingWithinSla() {
        return pendingWithinSla;
    }

    public void setPendingWithinSla(long pendingWithinSla) {
        this.pendingWithinSla = pendingWithinSla;
    }

    public long getPendingBeyondSla() {
        return pendingBeyondSla;
    }

    public void setPendingBeyondSla(long pendingBeyondSla) {
        this.pendingBeyondSla = pendingBeyondSla;
    }

    public long getClosedWithinSla() {
        return closedWithinSla;
    }

    public void setClosedWithinSla(long closedWithinSla) {
        this.closedWithinSla = closedWithinSla;
    }

    public long getClosedBeyondSla() {
        return closedBeyondSla;
    }

    public void setClosedBeyondSla(long closedBeyondSla) {
        this.closedBeyondSla = closedBeyondSla;
    }

    public double getRedressalWithinSla() {
        return redressalWithinSla;
    }

    public void setRedressalWithinSla(double redressalWithinSla) {
        this.redressalWithinSla = redressalWithinSla;
    }

    public double getRedressalBeyondSla() {
        return redressalBeyondSla;
    }

    public void setRedressalBeyondSla(double redressalBeyondSla) {
        this.redressalBeyondSla = redressalBeyondSla;
    }

    public double getRedressal() {
        return redressal;
    }

    public void setRedressal(double d) {
        this.redressal = d;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
