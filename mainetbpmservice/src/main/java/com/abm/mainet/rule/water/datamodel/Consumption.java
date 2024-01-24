package com.abm.mainet.rule.water.datamodel;

import java.io.Serializable;

public class Consumption implements Serializable {

    /**
    * 
    */
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
    /*Fields added told by rishikesh*/
    private long currentMeterReadingDate;
    private long meterInstallationDate;
    private long physicalConnectionDate;
    private double maxMeterReadLast4Mrd;
    private double maxNoOfDaysLast4Mrd;
    

    private double consumption;

    private double baseRate;
    private String factor1;
    private String factor2;
    private String factor3;
    private String factor4;

    /**
     * default constructor to initialize state of Object to default
     */
    public Consumption() {
        this.orgId = 0l;
        this.typeOfConnection = "NA";
        this.billingMethod = "NA";
        this.typeOfPeriod = "NA";
        this.gapCode = "NA";
        this.installationReading = 0l;
        this.disconnectionReading = 0l;
        this.restorationReading = 0l;
        this.maxMeterReading = 0l;
        this.lastMeterReading = 0l;
        this.currentMeterReading = 0l;
        this.minimumConsump = 0l;
        this.currentReadingDays = 0l;
        this.lastReadingDays = 0l;
        this.current_2Reading = 0l;
        this.current_3Reading = 0l;
        this.current_4Reading = 0l;
        this.current_5Reading = 0l;
        this.current_6Reading = 0l;
        this.current_7Reading = 0l;
        this.current_8Reading = 0l;
        this.current_9Reading = 0l;
        this.current_10Reading = 0l;
        this.current_11Reading = 0l;
        this.current_12Reading = 0l;
        this.current_2ReadingDays = 0l;
        this.current_3ReadingDays = 0l;
        this.current_4ReadingDays = 0l;
        this.current_5ReadingDays = 0l;
        this.current_6ReadingDays = 0l;
        this.current_7ReadingDays = 0l;
        this.current_8ReadingDays = 0l;
        this.current_9ReadingDays = 0l;
        this.current_10ReadingDays = 0l;
        this.current_11ReadingDays = 0l;
        this.current_12ReadingDays = 0l;
        this.baseRate = 0.0d;
        this.factor1 = "NA";
        this.factor2 = "NA";
        this.factor3 = "NA";
        this.factor4 = "NA";
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
    public void setFactor1(String factor1) {
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
    public void setFactor2(String factor2) {
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
    public void setFactor3(String factor3) {
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
    public void setFactor4(String factor4) {
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
    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    public void setTypeOfConnection(String typeOfConnection) {
        this.typeOfConnection = typeOfConnection;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

    public String getTypeOfPeriod() {
        return typeOfPeriod;
    }

    public void setTypeOfPeriod(String typeOfPeriod) {
        this.typeOfPeriod = typeOfPeriod;
    }

    public long getInstallationReading() {
        return installationReading;
    }

    public void setInstallationReading(long installationReading) {
        this.installationReading = installationReading;
    }

    public long getDisconnectionReading() {
        return disconnectionReading;
    }

    public void setDisconnectionReading(long disconnectionReading) {
        this.disconnectionReading = disconnectionReading;
    }

    public long getRestorationReading() {
        return restorationReading;
    }

    public void setRestorationReading(long restorationReading) {
        this.restorationReading = restorationReading;
    }

    public long getMaxMeterReading() {
        return maxMeterReading;
    }

    public void setMaxMeterReading(long maxMeterReading) {
        this.maxMeterReading = maxMeterReading;
    }

    public long getLastMeterReading() {
        return lastMeterReading;
    }

    public void setLastMeterReading(long lastMeterReading) {
        this.lastMeterReading = lastMeterReading;
    }

    public long getCurrentMeterReading() {
        return currentMeterReading;
    }

    public void setCurrentMeterReading(long currentMeterReading) {
        this.currentMeterReading = currentMeterReading;
    }

    public long getMinimumConsump() {
        return minimumConsump;
    }

    public void setMinimumConsump(long minimumConsump) {
        this.minimumConsump = minimumConsump;
    }

    public long getCurrent_2Reading() {
        return current_2Reading;
    }

    public void setCurrent_2Reading(long current_2Reading) {
        this.current_2Reading = current_2Reading;
    }

    public long getCurrent_3Reading() {
        return current_3Reading;
    }

    public void setCurrent_3Reading(long current_3Reading) {
        this.current_3Reading = current_3Reading;
    }

    public long getCurrent_4Reading() {
        return current_4Reading;
    }

    public void setCurrent_4Reading(long current_4Reading) {
        this.current_4Reading = current_4Reading;
    }

    public long getCurrent_5Reading() {
        return current_5Reading;
    }

    public void setCurrent_5Reading(long current_5Reading) {
        this.current_5Reading = current_5Reading;
    }

    public long getCurrent_6Reading() {
        return current_6Reading;
    }

    public void setCurrent_6Reading(long current_6Reading) {
        this.current_6Reading = current_6Reading;
    }

    public long getCurrentReadingDays() {
        return currentReadingDays;
    }

    public void setCurrentReadingDays(long currentReadingDays) {
        this.currentReadingDays = currentReadingDays;
    }

    public long getLastReadingDays() {
        return lastReadingDays;
    }

    public void setLastReadingDays(long lastReadingDays) {
        this.lastReadingDays = lastReadingDays;
    }

    public long getCurrent_2ReadingDays() {
        return current_2ReadingDays;
    }

    public void setCurrent_2ReadingDays(long current_2ReadingDays) {
        this.current_2ReadingDays = current_2ReadingDays;
    }

    public long getCurrent_3ReadingDays() {
        return current_3ReadingDays;
    }

    public void setCurrent_3ReadingDays(long current_3ReadingDays) {
        this.current_3ReadingDays = current_3ReadingDays;
    }

    public long getCurrent_4ReadingDays() {
        return current_4ReadingDays;
    }

    public void setCurrent_4ReadingDays(long current_4ReadingDays) {
        this.current_4ReadingDays = current_4ReadingDays;
    }

    public long getCurrent_5ReadingDays() {
        return current_5ReadingDays;
    }

    public void setCurrent_5ReadingDays(long current_5ReadingDays) {
        this.current_5ReadingDays = current_5ReadingDays;
    }

    public long getCurrent_6ReadingDays() {
        return current_6ReadingDays;
    }

    public void setCurrent_6ReadingDays(long current_6ReadingDays) {
        this.current_6ReadingDays = current_6ReadingDays;
    }

    public String getGapCode() {
        return gapCode;
    }

    public void setGapCode(String gapCode) {
        this.gapCode = gapCode;
    }

    public long getCurrent_7Reading() {
        return current_7Reading;
    }

    public void setCurrent_7Reading(long current_7Reading) {
        this.current_7Reading = current_7Reading;
    }

    public long getCurrent_8Reading() {
        return current_8Reading;
    }

    public void setCurrent_8Reading(long current_8Reading) {
        this.current_8Reading = current_8Reading;
    }

    public long getCurrent_9Reading() {
        return current_9Reading;
    }

    public void setCurrent_9Reading(long current_9Reading) {
        this.current_9Reading = current_9Reading;
    }

    public long getCurrent_10Reading() {
        return current_10Reading;
    }

    public void setCurrent_10Reading(long current_10Reading) {
        this.current_10Reading = current_10Reading;
    }

    public long getCurrent_11Reading() {
        return current_11Reading;
    }

    public void setCurrent_11Reading(long current_11Reading) {
        this.current_11Reading = current_11Reading;
    }

    public long getCurrent_12Reading() {
        return current_12Reading;
    }

    public void setCurrent_12Reading(long current_12Reading) {
        this.current_12Reading = current_12Reading;
    }

    public long getCurrent_7ReadingDays() {
        return current_7ReadingDays;
    }

    public void setCurrent_7ReadingDays(long current_7ReadingDays) {
        this.current_7ReadingDays = current_7ReadingDays;
    }

    public long getCurrent_8ReadingDays() {
        return current_8ReadingDays;
    }

    public void setCurrent_8ReadingDays(long current_8ReadingDays) {
        this.current_8ReadingDays = current_8ReadingDays;
    }

    public long getCurrent_9ReadingDays() {
        return current_9ReadingDays;
    }

    public void setCurrent_9ReadingDays(long current_9ReadingDays) {
        this.current_9ReadingDays = current_9ReadingDays;
    }

    public long getCurrent_10ReadingDays() {
        return current_10ReadingDays;
    }

    public void setCurrent_10ReadingDays(long current_10ReadingDays) {
        this.current_10ReadingDays = current_10ReadingDays;
    }

    public long getCurrent_11ReadingDays() {
        return current_11ReadingDays;
    }

    public void setCurrent_11ReadingDays(long current_11ReadingDays) {
        this.current_11ReadingDays = current_11ReadingDays;
    }

    public long getCurrent_12ReadingDays() {
        return current_12ReadingDays;
    }

    public void setCurrent_12ReadingDays(long current_12ReadingDays) {
        this.current_12ReadingDays = current_12ReadingDays;
    }

    @Override
    public String toString() {
        return "Consumption [orgId=" + orgId + ", typeOfConnection=" + typeOfConnection + ", billingMethod=" + billingMethod
                + ", typeOfPeriod=" + typeOfPeriod + ", gapCode=" + gapCode + ", installationReading=" + installationReading
                + ", disconnectionReading=" + disconnectionReading + ", restorationReading=" + restorationReading
                + ", maxMeterReading=" + maxMeterReading + ", lastMeterReading=" + lastMeterReading + ", currentMeterReading="
                + currentMeterReading + ", minimumConsump=" + minimumConsump + ", currentReadingDays=" + currentReadingDays
                + ", lastReadingDays=" + lastReadingDays + ", current_2Reading=" + current_2Reading + ", current_3Reading="
                + current_3Reading + ", current_4Reading=" + current_4Reading + ", current_5Reading=" + current_5Reading
                + ", current_6Reading=" + current_6Reading + ", current_7Reading=" + current_7Reading + ", current_8Reading="
                + current_8Reading + ", current_9Reading=" + current_9Reading + ", current_10Reading=" + current_10Reading
                + ", current_11Reading=" + current_11Reading + ", current_12Reading=" + current_12Reading
                + ", current_2ReadingDays=" + current_2ReadingDays + ", current_3ReadingDays=" + current_3ReadingDays
                + ", current_4ReadingDays=" + current_4ReadingDays + ", current_5ReadingDays=" + current_5ReadingDays
                + ", current_6ReadingDays=" + current_6ReadingDays + ", current_7ReadingDays=" + current_7ReadingDays
                + ", current_8ReadingDays=" + current_8ReadingDays + ", current_9ReadingDays=" + current_9ReadingDays
                + ", current_10ReadingDays=" + current_10ReadingDays + ", current_11ReadingDays=" + current_11ReadingDays
                + ", current_12ReadingDays=" + current_12ReadingDays + ", consumption=" + consumption + ", baseRate=" + baseRate
                + ", factor1=" + factor1 + ", factor2=" + factor2 + ", factor3=" + factor3 + ", factor4=" + factor4 + "]";
    }

	public long getCurrentMeterReadingDate() {
		return currentMeterReadingDate;
	}

	public void setCurrentMeterReadingDate(long currentMeterReadingDate) {
		this.currentMeterReadingDate = currentMeterReadingDate;
	}

	public long getMeterInstallationDate() {
		return meterInstallationDate;
	}

	public void setMeterInstallationDate(long meterInstallationDate) {
		this.meterInstallationDate = meterInstallationDate;
	}

	public long getPhysicalConnectionDate() {
		return physicalConnectionDate;
	}

	public void setPhysicalConnectionDate(long physicalConnectionDate) {
		this.physicalConnectionDate = physicalConnectionDate;
	}

	public double getMaxMeterReadLast4Mrd() {
		return maxMeterReadLast4Mrd;
	}

	public void setMaxMeterReadLast4Mrd(double maxMeterReadLast4Mrd) {
		this.maxMeterReadLast4Mrd = maxMeterReadLast4Mrd;
	}

	public double getMaxNoOfDaysLast4Mrd() {
		return maxNoOfDaysLast4Mrd;
	}

	public void setMaxNoOfDaysLast4Mrd(double maxNoOfDaysLast4Mrd) {
		this.maxNoOfDaysLast4Mrd = maxNoOfDaysLast4Mrd;
	}

}
