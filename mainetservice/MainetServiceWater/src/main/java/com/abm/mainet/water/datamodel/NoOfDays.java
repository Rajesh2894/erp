package com.abm.mainet.water.datamodel;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class NoOfDays implements Serializable, Cloneable {

    private static final long serialVersionUID = -5438690382833213682L;

    private long orgId;
    private String typeOfConnection; // csr info table
    private String billingMethod;
    private String typeOfPeriod;
    private java.lang.String factor1;
    private java.lang.String factor2;
    private java.lang.String factor3;
    private java.lang.String factor4;

    private long physicalConnectionDate; // tb_mrdata Table
    private long meterInstallationDate;	 // tb_mrdata Table
    private long disconnectionDate;	 // tb_mrdata Table
    private long restorationDate;	// tb_mrdata Table
    private long lastMeterReadingDate;  // tb_mrdata Table
    private long currentMeterReadingDate;	// tb_mrdata Table
    private long startBillingDate;	// exception gap master
    private long stopBillingDate;	// exception gap master
    private long billingCycleStartDate;// exception gap master
    private long billingCycleEndDate;// exception gap master
    private long noOfDays;
    private long exceptionPeriod;// exception period from exception gap master in days

    /**
     * private constructor , prevent to Object initialization
     * 
     */
    private NoOfDays() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    public long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(final long noOfDays) {

        final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        this.noOfDays = timeUnit.toDays(noOfDays);
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    public void setTypeOfConnection(final String typeOfConnection) {
        this.typeOfConnection = typeOfConnection;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(final String billingMethod) {
        this.billingMethod = billingMethod;
    }

    public String getTypeOfPeriod() {
        return typeOfPeriod;
    }

    public void setTypeOfPeriod(final String typeOfPeriod) {
        this.typeOfPeriod = typeOfPeriod;
    }

    public java.lang.String getFactor1() {
        return factor1;
    }

    public void setFactor1(final java.lang.String factor1) {
        this.factor1 = factor1;
    }

    public java.lang.String getFactor2() {
        return factor2;
    }

    public void setFactor2(final java.lang.String factor2) {
        this.factor2 = factor2;
    }

    public java.lang.String getFactor3() {
        return factor3;
    }

    public void setFactor3(final java.lang.String factor3) {
        this.factor3 = factor3;
    }

    public java.lang.String getFactor4() {
        return factor4;
    }

    public void setFactor4(final java.lang.String factor4) {
        this.factor4 = factor4;
    }

    public long getPhysicalConnectionDate() {
        return physicalConnectionDate;
    }

    public long getMeterInstallationDate() {
        return meterInstallationDate;
    }

    public void setMeterInstallationDate(final long meterInstallationDate) {
        this.meterInstallationDate = meterInstallationDate;
    }

    public long getDisconnectionDate() {
        return disconnectionDate;
    }

    public void setDisconnectionDate(final long disconnectionDate) {
        this.disconnectionDate = disconnectionDate;
    }

    public long getRestorationDate() {
        return restorationDate;
    }

    public void setRestorationDate(final long restorationDate) {
        this.restorationDate = restorationDate;
    }

    public long getLastMeterReadingDate() {
        return lastMeterReadingDate;
    }

    public void setLastMeterReadingDate(final long lastMeterReadingDate) {
        this.lastMeterReadingDate = lastMeterReadingDate;
    }

    public long getStartBillingDate() {
        return startBillingDate;
    }

    public void setStartBillingDate(final long startBillingDate) {
        this.startBillingDate = startBillingDate;
    }

    public long getStopBillingDate() {
        return stopBillingDate;
    }

    public void setStopBillingDate(final long stopBillingDate) {
        this.stopBillingDate = stopBillingDate;
    }

    public long getBillingCycleStartDate() {
        return billingCycleStartDate;
    }

    public void setBillingCycleStartDate(final long billingCycleStartDate) {
        this.billingCycleStartDate = billingCycleStartDate;
    }

    public long getBillingCycleEndDate() {
        return billingCycleEndDate;
    }

    public void setBillingCycleEndDate(final long billingCycleEndDate) {
        this.billingCycleEndDate = billingCycleEndDate;
    }

    public long getCurrentMeterReadingDate() {
        return currentMeterReadingDate;
    }

    public void setCurrentMeterReadingDate(final long currentMeterReadingDate) {
        this.currentMeterReadingDate = currentMeterReadingDate;
    }

    public void setPhysicalConnectionDate(final long physicalConnectionDate) {
        this.physicalConnectionDate = physicalConnectionDate;
    }

    public long getExceptionPeriod() {
        return exceptionPeriod;
    }

    public void setExceptionPeriod(final long exceptionPeriod) {
        this.exceptionPeriod = exceptionPeriod;
    }

    @Override
    public String toString() {
        return "NoOfDays [orgId=" + orgId + ", typeOfConnection=" + typeOfConnection + ", billingMethod=" + billingMethod
                + ", typeOfPeriod=" + typeOfPeriod + ", factor1=" + factor1 + ", factor2=" + factor2 + ", factor3=" + factor3
                + ", factor4=" + factor4 + ", physicalConnectionDate=" + physicalConnectionDate + ", meterInstallationDate="
                + meterInstallationDate + ", disconnectionDate=" + disconnectionDate + ", restorationDate=" + restorationDate
                + ", lastMeterReadingDate=" + lastMeterReadingDate + ", currentMeterReadingDate=" + currentMeterReadingDate
                + ", startBillingDate=" + startBillingDate + ", stopBillingDate=" + stopBillingDate + ", billingCycleStartDate="
                + billingCycleStartDate + ", billingCycleEndDate=" + billingCycleEndDate + ", noOfDays=" + noOfDays
                + ", exceptionPeriod=" + exceptionPeriod + "]";
    }

}
