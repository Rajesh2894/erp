package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

public class AnimalManagementLogDto implements Serializable {
    private static final long serialVersionUID = 1125688680660005952L;

    private Long orgid;

    private Long distName;

    private Date date;

    private String vehicleNo;

    private Long noOfAnimal;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getDistName() {
        return distName;
    }

    public void setDistName(Long distName) {
        this.distName = distName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getNoOfAnimal() {
        return noOfAnimal;
    }

    public void setNoOfAnimal(Long noOfAnimal) {
        this.noOfAnimal = noOfAnimal;
    }

}
