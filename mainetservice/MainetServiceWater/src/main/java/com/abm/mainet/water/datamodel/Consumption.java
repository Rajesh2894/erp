package com.abm.mainet.water.datamodel;

import java.io.Serializable;

public class Consumption implements Serializable {

    private static final long serialVersionUID = -858268924242326720L;

    private long orgId;
    private String typeOfConnection;
    private String billingMethod;
    private String typeOfPeriod;
    private String gapCode;
    private long installationReading;
    private long disconnectionReading;
    private long restorationReading;
    private long maxMeterReading;
    private long lastMeterReading;
    private long currentMeterReading;
    private long minimumConsump;
    private long currentReadingDays;
    private long lastReadingDays;
    private long current_2Reading;
    private long current_3Reading;
    private long current_4Reading;
    private long current_5Reading;
    private long current_6Reading;
    private long current_7Reading;
    private long current_8Reading;
    private long current_9Reading;
    private long current_10Reading;
    private long current_11Reading;
    private long current_12Reading;
    private long current_2ReadingDays;
    private long current_3ReadingDays;
    private long current_4ReadingDays;
    private long current_5ReadingDays;
    private long current_6ReadingDays;
    private long current_7ReadingDays;
    private long current_8ReadingDays;
    private long current_9ReadingDays;
    private long current_10ReadingDays;
    private long current_11ReadingDays;
    private long current_12ReadingDays;

    private double consumption;

    private double baseRate;
    private String factor1;
    private String factor2;
    private String factor3;
    private String factor4;
    
    
    private long currentMeterReadingDate;
    private long meterInstallationDate;
    private long physicalConnectionDate;
    
    private double maxMeterReadLast4Mrd;
    private double maxNoOfDaysLast4Mrd;

    /**
     * private constructor , prevent to Object initialization
     * 
     */
    private Consumption() {

    }

    /**
     * @return the factor1
     */
    public String getFactor1() {
        return factor1;
    }

    /**
     * @param factor1 the factor1 to set
     */
    public void setFactor1(final String factor1) {
        this.factor1 = factor1;
    }

    /**
     * @return the factor2
     */
    public String getFactor2() {
        return factor2;
    }

    /**
     * @param factor2 the factor2 to set
     */
    public void setFactor2(final String factor2) {
        this.factor2 = factor2;
    }

    /**
     * @return the factor3
     */
    public String getFactor3() {
        return factor3;
    }

    /**
     * @param factor3 the factor3 to set
     */
    public void setFactor3(final String factor3) {
        this.factor3 = factor3;
    }

    /**
     * @return the factor4
     */
    public String getFactor4() {
        return factor4;
    }

    /**
     * @param factor4 the factor4 to set
     */
    public void setFactor4(final String factor4) {
        this.factor4 = factor4;
    }

    /**
     * @return the baseRate
     */
    public double getBaseRate() {
        return baseRate;
    }

    /**
     * @param baseRate the baseRate to set
     */
    public void setBaseRate(final double baseRate) {
        this.baseRate = baseRate;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(final double consumption) {
        this.consumption = consumption;
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

    public long getInstallationReading() {
        return installationReading;
    }

    public void setInstallationReading(final long installationReading) {
        this.installationReading = installationReading;
    }

    public long getDisconnectionReading() {
        return disconnectionReading;
    }

    public void setDisconnectionReading(final long disconnectionReading) {
        this.disconnectionReading = disconnectionReading;
    }

    public long getRestorationReading() {
        return restorationReading;
    }

    public void setRestorationReading(final long restorationReading) {
        this.restorationReading = restorationReading;
    }

    public long getMaxMeterReading() {
        return maxMeterReading;
    }

    public void setMaxMeterReading(final long maxMeterReading) {
        this.maxMeterReading = maxMeterReading;
    }

    public long getLastMeterReading() {
        return lastMeterReading;
    }

    public void setLastMeterReading(final long lastMeterReading) {
        this.lastMeterReading = lastMeterReading;
    }

    public long getCurrentMeterReading() {
        return currentMeterReading;
    }

    public void setCurrentMeterReading(final long currentMeterReading) {
        this.currentMeterReading = currentMeterReading;
    }

    public long getMinimumConsump() {
        return minimumConsump;
    }

    public void setMinimumConsump(final long minimumConsump) {
        this.minimumConsump = minimumConsump;
    }

    public long getCurrent_2Reading() {
        return current_2Reading;
    }

    public void setCurrent_2Reading(final long current_2Reading) {
        this.current_2Reading = current_2Reading;
    }

    public long getCurrent_3Reading() {
        return current_3Reading;
    }

    public void setCurrent_3Reading(final long current_3Reading) {
        this.current_3Reading = current_3Reading;
    }

    public long getCurrent_4Reading() {
        return current_4Reading;
    }

    public void setCurrent_4Reading(final long current_4Reading) {
        this.current_4Reading = current_4Reading;
    }

    public long getCurrent_5Reading() {
        return current_5Reading;
    }

    public void setCurrent_5Reading(final long current_5Reading) {
        this.current_5Reading = current_5Reading;
    }

    public long getCurrent_6Reading() {
        return current_6Reading;
    }

    public void setCurrent_6Reading(final long current_6Reading) {
        this.current_6Reading = current_6Reading;
    }

    public long getCurrentReadingDays() {
        return currentReadingDays;
    }

    public void setCurrentReadingDays(final long currentReadingDays) {
        this.currentReadingDays = currentReadingDays;
    }

    public long getLastReadingDays() {
        return lastReadingDays;
    }

    public void setLastReadingDays(final long lastReadingDays) {
        this.lastReadingDays = lastReadingDays;
    }

    public long getCurrent_2ReadingDays() {
        return current_2ReadingDays;
    }

    public void setCurrent_2ReadingDays(final long current_2ReadingDays) {
        this.current_2ReadingDays = current_2ReadingDays;
    }

    public long getCurrent_3ReadingDays() {
        return current_3ReadingDays;
    }

    public void setCurrent_3ReadingDays(final long current_3ReadingDays) {
        this.current_3ReadingDays = current_3ReadingDays;
    }

    public long getCurrent_4ReadingDays() {
        return current_4ReadingDays;
    }

    public void setCurrent_4ReadingDays(final long current_4ReadingDays) {
        this.current_4ReadingDays = current_4ReadingDays;
    }

    public long getCurrent_5ReadingDays() {
        return current_5ReadingDays;
    }

    public void setCurrent_5ReadingDays(final long current_5ReadingDays) {
        this.current_5ReadingDays = current_5ReadingDays;
    }

    public long getCurrent_6ReadingDays() {
        return current_6ReadingDays;
    }

    public void setCurrent_6ReadingDays(final long current_6ReadingDays) {
        this.current_6ReadingDays = current_6ReadingDays;
    }

    public String getGapCode() {
        return gapCode;
    }

    public void setGapCode(final String gapCode) {
        this.gapCode = gapCode;
    }

    public long getCurrent_7Reading() {
        return current_7Reading;
    }

    public void setCurrent_7Reading(final long current_7Reading) {
        this.current_7Reading = current_7Reading;
    }

    public long getCurrent_8Reading() {
        return current_8Reading;
    }

    public void setCurrent_8Reading(final long current_8Reading) {
        this.current_8Reading = current_8Reading;
    }

    public long getCurrent_9Reading() {
        return current_9Reading;
    }

    public void setCurrent_9Reading(final long current_9Reading) {
        this.current_9Reading = current_9Reading;
    }

    public long getCurrent_10Reading() {
        return current_10Reading;
    }

    public void setCurrent_10Reading(final long current_10Reading) {
        this.current_10Reading = current_10Reading;
    }

    public long getCurrent_11Reading() {
        return current_11Reading;
    }

    public void setCurrent_11Reading(final long current_11Reading) {
        this.current_11Reading = current_11Reading;
    }

    public long getCurrent_12Reading() {
        return current_12Reading;
    }

    public void setCurrent_12Reading(final long current_12Reading) {
        this.current_12Reading = current_12Reading;
    }

    public long getCurrent_7ReadingDays() {
        return current_7ReadingDays;
    }

    public void setCurrent_7ReadingDays(final long current_7ReadingDays) {
        this.current_7ReadingDays = current_7ReadingDays;
    }

    public long getCurrent_8ReadingDays() {
        return current_8ReadingDays;
    }

    public void setCurrent_8ReadingDays(final long current_8ReadingDays) {
        this.current_8ReadingDays = current_8ReadingDays;
    }

    public long getCurrent_9ReadingDays() {
        return current_9ReadingDays;
    }

    public void setCurrent_9ReadingDays(final long current_9ReadingDays) {
        this.current_9ReadingDays = current_9ReadingDays;
    }

    public long getCurrent_10ReadingDays() {
        return current_10ReadingDays;
    }

    public void setCurrent_10ReadingDays(final long current_10ReadingDays) {
        this.current_10ReadingDays = current_10ReadingDays;
    }

    public long getCurrent_11ReadingDays() {
        return current_11ReadingDays;
    }

    public void setCurrent_11ReadingDays(final long current_11ReadingDays) {
        this.current_11ReadingDays = current_11ReadingDays;
    }

    public long getCurrent_12ReadingDays() {
        return current_12ReadingDays;
    }

    public void setCurrent_12ReadingDays(final long current_12ReadingDays) {
        this.current_12ReadingDays = current_12ReadingDays;
    }

    /**
	 * @return the currentMeterReadingDate
	 */
	public long getCurrentMeterReadingDate() {
		return currentMeterReadingDate;
	}

	/**
	 * @param currentMeterReadingDate the currentMeterReadingDate to set
	 */
	public void setCurrentMeterReadingDate(long currentMeterReadingDate) {
		this.currentMeterReadingDate = currentMeterReadingDate;
	}

	/**
	 * @return the meterInstallationDate
	 */
	public long getMeterInstallationDate() {
		return meterInstallationDate;
	}

	/**
	 * @param meterInstallationDate the meterInstallationDate to set
	 */
	public void setMeterInstallationDate(long meterInstallationDate) {
		this.meterInstallationDate = meterInstallationDate;
	}

	/**
	 * @return the physicalConnectionDate
	 */
	public long getPhysicalConnectionDate() {
		return physicalConnectionDate;
	}

	/**
	 * @param physicalConnectionDate the physicalConnectionDate to set
	 */
	public void setPhysicalConnectionDate(long physicalConnectionDate) {
		this.physicalConnectionDate = physicalConnectionDate;
	}
	
	

	/**
	 * @return the maxMeterReadLast4Mrd
	 */
	public double getMaxMeterReadLast4Mrd() {
		return maxMeterReadLast4Mrd;
	}

	/**
	 * @param maxMeterReadLast4Mrd the maxMeterReadLast4Mrd to set
	 */
	public void setMaxMeterReadLast4Mrd(double maxMeterReadLast4Mrd) {
		this.maxMeterReadLast4Mrd = maxMeterReadLast4Mrd;
	}

	/**
	 * @return the maxNoOfDaysLast4Mrd
	 */
	public double getMaxNoOfDaysLast4Mrd() {
		return maxNoOfDaysLast4Mrd;
	}

	/**
	 * @param maxNoOfDaysLast4Mrd the maxNoOfDaysLast4Mrd to set
	 */
	public void setMaxNoOfDaysLast4Mrd(double maxNoOfDaysLast4Mrd) {
		this.maxNoOfDaysLast4Mrd = maxNoOfDaysLast4Mrd;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Consumption [orgId=" + orgId + ", typeOfConnection=" + typeOfConnection + ", billingMethod="
				+ billingMethod + ", typeOfPeriod=" + typeOfPeriod + ", gapCode=" + gapCode + ", installationReading="
				+ installationReading + ", disconnectionReading=" + disconnectionReading + ", restorationReading="
				+ restorationReading + ", maxMeterReading=" + maxMeterReading + ", lastMeterReading=" + lastMeterReading
				+ ", currentMeterReading=" + currentMeterReading + ", minimumConsump=" + minimumConsump
				+ ", currentReadingDays=" + currentReadingDays + ", lastReadingDays=" + lastReadingDays
				+ ", current_2Reading=" + current_2Reading + ", current_3Reading=" + current_3Reading
				+ ", current_4Reading=" + current_4Reading + ", current_5Reading=" + current_5Reading
				+ ", current_6Reading=" + current_6Reading + ", current_7Reading=" + current_7Reading
				+ ", current_8Reading=" + current_8Reading + ", current_9Reading=" + current_9Reading
				+ ", current_10Reading=" + current_10Reading + ", current_11Reading=" + current_11Reading
				+ ", current_12Reading=" + current_12Reading + ", current_2ReadingDays=" + current_2ReadingDays
				+ ", current_3ReadingDays=" + current_3ReadingDays + ", current_4ReadingDays=" + current_4ReadingDays
				+ ", current_5ReadingDays=" + current_5ReadingDays + ", current_6ReadingDays=" + current_6ReadingDays
				+ ", current_7ReadingDays=" + current_7ReadingDays + ", current_8ReadingDays=" + current_8ReadingDays
				+ ", current_9ReadingDays=" + current_9ReadingDays + ", current_10ReadingDays=" + current_10ReadingDays
				+ ", current_11ReadingDays=" + current_11ReadingDays + ", current_12ReadingDays="
				+ current_12ReadingDays + ", consumption=" + consumption + ", baseRate=" + baseRate + ", factor1="
				+ factor1 + ", factor2=" + factor2 + ", factor3=" + factor3 + ", factor4=" + factor4
				+ ", currentMeterReadingDate=" + currentMeterReadingDate + ", meterInstallationDate="
				+ meterInstallationDate + ", physicalConnectionDate=" + physicalConnectionDate
				+ ", maxMeterReadLast4Mrd=" + maxMeterReadLast4Mrd + ", maxNoOfDaysLast4Mrd=" + maxNoOfDaysLast4Mrd
				 + "]";
	}

}
