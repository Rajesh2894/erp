package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.List;

public class AreaWiseDto implements Serializable {
    private static final long serialVersionUID = -4735259211874660103L;

    private String reportType;

    private String fromDate;

    private String toDate;

    private String locName;

    private String locAdress;

    private String wardName;

    private String zoneName;

    private String mobNo;

    private String remarks;

    private String flagMsg;

    private String chiefName;

    private List<AreaWiseDto> areaWiseDtoList;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<AreaWiseDto> getAreaWiseDtoList() {
        return areaWiseDtoList;
    }

    public void setAreaWiseDtoList(List<AreaWiseDto> areaWiseDtoList) {
        this.areaWiseDtoList = areaWiseDtoList;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocAdress() {
        return locAdress;
    }

    public void setLocAdress(String locAdress) {
        this.locAdress = locAdress;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

    public String getChiefName() {
        return chiefName;
    }

    public void setChiefName(String chiefName) {
        this.chiefName = chiefName;
    }

}
