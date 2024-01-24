package com.abm.mainet.property.datamodel;

import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

public class PropertyTaxDataModel extends CommonModel {

    private static final long serialVersionUID = 6542359228565140911L;
    private String wardZoneLevel1;
    private String wardZoneLevel2;
    private String wardZoneLevel3;
    private String wardZoneLevel4;
    private String wardZoneLevel5;
    private long rateStartDate;
    private double sddrRate;

    private PropertyTaxDataModel() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getWardZoneLevel1() {
        return wardZoneLevel1;
    }

    public void setWardZoneLevel1(String wardZoneLevel1) {
        this.wardZoneLevel1 = wardZoneLevel1;
    }

    public String getWardZoneLevel2() {
        return wardZoneLevel2;
    }

    public void setWardZoneLevel2(String wardZoneLevel2) {
        this.wardZoneLevel2 = wardZoneLevel2;
    }

    public String getWardZoneLevel3() {
        return wardZoneLevel3;
    }

    public void setWardZoneLevel3(String wardZoneLevel3) {
        this.wardZoneLevel3 = wardZoneLevel3;
    }

    public String getWardZoneLevel4() {
        return wardZoneLevel4;
    }

    public void setWardZoneLevel4(String wardZoneLevel4) {
        this.wardZoneLevel4 = wardZoneLevel4;
    }

    public String getWardZoneLevel5() {
        return wardZoneLevel5;
    }

    public void setWardZoneLevel5(String wardZoneLevel5) {
        this.wardZoneLevel5 = wardZoneLevel5;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public double getSddrRate() {
        return sddrRate;
    }

    public void setSddrRate(double sddrRate) {
        this.sddrRate = sddrRate;
    }

}
