package com.abm.mainet.mobile.dto;

import java.io.Serializable;

/**
 * @author umashanker.kanaujiya
 *
 */
public class MobileOnlinePaymentDTO implements Serializable {

    private static final long serialVersionUID = 3582211214843068159L;

    private long serviceId;
    private String serviceName;
    private String serviceFlag;
    private String ownerName;
    private Double dueAmt;
    private String mobNo;
    private String email;
    private String flatNo;
    private String propertyNo;
    private String waterNo;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(final String serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(final Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(final String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(final String flatNo) {
        this.flatNo = flatNo;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(final String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(final String waterNo) {
        this.waterNo = waterNo;
    }

}
