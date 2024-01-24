package com.abm.mainet.rnl.dto;

import java.io.Serializable;

public class PropFreezeDTO implements Serializable {

    private static final long serialVersionUID = -7104684510414667320L;
    private Long id;
    private String location;
    private String estate;
    private String property;
    private String fromDate;
    private String toDate;
    private String shift;
    private String purpose;
    private String reasonOfFreezing;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(final String estate) {
        this.estate = estate;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(final String property) {
        this.property = property;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(final String shift) {
        this.shift = shift;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(final String purpose) {
        this.purpose = purpose;
    }

    public String getReasonOfFreezing() {
        return reasonOfFreezing;
    }

    public void setReasonOfFreezing(String reasonOfFreezing) {
        this.reasonOfFreezing = reasonOfFreezing;
    }

}
