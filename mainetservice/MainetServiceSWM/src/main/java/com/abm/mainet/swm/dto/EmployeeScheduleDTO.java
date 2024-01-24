package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
public class EmployeeScheduleDTO implements Serializable {

    private static final long serialVersionUID = -8705276585872728266L;

    private Long emsId;

    private Long createdBy;

    private Date createdDate;

    private Date emsFromdate;

    private String emsReocc;

    private Date emsTodate;

    private String emsType;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private Long empid;

    private String isDeleted;

    private String empName;

    private String fromDate;

    private String toDate;

    private String estfromdate;

    private String estTodate;

    private String weekdays;

    private String flagMsg;

    private Date esdScheduledate;

    private List<Date> sheduleDate;

    private String empScheduledate;

    private Long monthNo;

    private Long shiftId;

    private String vehicleNo;

    private String wasteType;

    private List<EmployeeScheduleDTO> employeeScheduleList;

    private List<EmployeeScheduleDetailDTO> tbSwEmployeeScheddets;

    public EmployeeScheduleDTO() {
    }

    public Long getEmsId() {
        return this.emsId;
    }

    public void setEmsId(Long emsId) {
        this.emsId = emsId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getEmsFromdate() {
        return this.emsFromdate;
    }

    public void setEmsFromdate(Date emsFromdate) {
        this.emsFromdate = emsFromdate;
    }

    public String getEmsReocc() {
        return this.emsReocc;
    }

    public void setEmsReocc(String emsReocc) {
        this.emsReocc = emsReocc;
    }

    public Date getEmsTodate() {
        return this.emsTodate;
    }

    public void setEmsTodate(Date emsTodate) {
        this.emsTodate = emsTodate;
    }

    public String getEmsType() {
        return emsType;
    }

    public void setEmsType(String emsType) {
        this.emsType = emsType;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<EmployeeScheduleDetailDTO> getTbSwEmployeeScheddets() {
        return this.tbSwEmployeeScheddets;
    }

    public void setTbSwEmployeeScheddets(List<EmployeeScheduleDetailDTO> tbSwEmployeeScheddets) {
        this.tbSwEmployeeScheddets = tbSwEmployeeScheddets;
    }

    public EmployeeScheduleDetailDTO addTbSwEmployeeScheddet(EmployeeScheduleDetailDTO tbSwEmployeeScheddet) {
        getTbSwEmployeeScheddets().add(tbSwEmployeeScheddet);
        tbSwEmployeeScheddet.setTbSwEmployeeScheduling(this);

        return tbSwEmployeeScheddet;
    }

    public EmployeeScheduleDetailDTO removeTbSwEmployeeScheddet(EmployeeScheduleDetailDTO tbSwEmployeeScheddet) {
        getTbSwEmployeeScheddets().remove(tbSwEmployeeScheddet);
        tbSwEmployeeScheddet.setTbSwEmployeeScheduling(null);

        return tbSwEmployeeScheddet;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public List<EmployeeScheduleDTO> getEmployeeScheduleList() {
        return employeeScheduleList;
    }

    public void setEmployeeScheduleList(List<EmployeeScheduleDTO> employeeScheduleList) {
        this.employeeScheduleList = employeeScheduleList;
    }

    public String getEstfromdate() {
        return estfromdate;
    }

    public void setEstfromdate(String estfromdate) {
        this.estfromdate = estfromdate;
    }

    public String getEstTodate() {
        return estTodate;
    }

    public void setEstTodate(String estTodate) {
        this.estTodate = estTodate;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

    public Date getEsdScheduledate() {
        return esdScheduledate;
    }

    public void setEsdScheduledate(Date esdScheduledate) {
        this.esdScheduledate = esdScheduledate;
    }

    public List<Date> getSheduleDate() {
        return sheduleDate;
    }

    public void setSheduleDate(List<Date> sheduleDate) {
        this.sheduleDate = sheduleDate;
    }

    public String getEmpScheduledate() {
        return empScheduledate;
    }

    public void setEmpScheduledate(String empScheduledate) {
        this.empScheduledate = empScheduledate;
    }

    public Long getMonthNo() {
        return monthNo;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setMonthNo(Long monthNo) {
        this.monthNo = monthNo;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

}
