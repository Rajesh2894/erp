package com.abm.mainet.care.dto.report;

public class SummaryField implements Comparable<SummaryField> {

    private String department;
    private String complaintType;

    public SummaryField() {
    }

    public SummaryField(String department, String complaintType) {
        super();
        this.department = department;
        this.complaintType = complaintType;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((complaintType == null) ? 0 : complaintType.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SummaryField other = (SummaryField) obj;
        if (complaintType == null) {
            if (other.complaintType != null)
                return false;
        } else if (!complaintType.equals(other.complaintType))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SummaryField [department=" + department + ", complaintType=" + complaintType + "]";
    }

    @Override
    public int compareTo(SummaryField o) {
        int result = department.compareToIgnoreCase(o.getDepartment());
        if (result == 0)
            result = complaintType.compareToIgnoreCase(o.getComplaintType());
        return result;
    }

}
