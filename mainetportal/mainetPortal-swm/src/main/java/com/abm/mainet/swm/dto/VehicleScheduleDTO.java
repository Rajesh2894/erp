package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
public class VehicleScheduleDTO implements Serializable {

    private static final long serialVersionUID = 8949778885532182788L;

    private Long vesId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private Long veId;

    private String veDesc;

    private String veRegnNo;

    private Long veVetype;

    private Date vesFromdt;

    private String vesReocc;

    private Date vesTodt;

    private String vehicleTypeMar;

    private String roadId;

    private String roadName;

    private String roadNameReg;

    private String vehicleTypeMar1;

    private String vehStartTym;

    private String vehEndTym;

    private Date vehStartTime;

    private String vesWeekday;

    private Date veScheduledate;

    private String latitude;

    private String longitude;

    private List<Date> sheduleDate;

    @JsonIgnore
    private List<VehicleScheduleDetDTO> tbSwVehicleScheddets;

    private List<VehicleScheduleDTO> vehicleScheduleList;

    public VehicleScheduleDTO() {
    }

    public Long getVesId() {
        return this.vesId;
    }

    public void setVesId(Long vesId) {
        this.vesId = vesId;
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

    public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getVeVetype() {
        return this.veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Date getVesFromdt() {
        return this.vesFromdt;
    }

    public void setVesFromdt(Date vesFromdt) {
        this.vesFromdt = vesFromdt;
    }

    public String getVesReocc() {
        return this.vesReocc;
    }

    public void setVesReocc(String vesReocc) {
        this.vesReocc = vesReocc;
    }

    public Date getVesTodt() {
        return this.vesTodt;
    }

    public void setVesTodt(Date vesTodt) {
        this.vesTodt = vesTodt;
    }

    public List<VehicleScheduleDetDTO> getTbSwVehicleScheddets() {
        return this.tbSwVehicleScheddets;
    }

    public void setTbSwVehicleScheddets(List<VehicleScheduleDetDTO> tbSwVehicleScheddets) {
        this.tbSwVehicleScheddets = tbSwVehicleScheddets;
    }

    public VehicleScheduleDetDTO addTbSwVehicleScheddet(VehicleScheduleDetDTO tbSwVehicleScheddet) {
        getTbSwVehicleScheddets().add(tbSwVehicleScheddet);
        tbSwVehicleScheddet.setTbSwVehicleScheduling(this);

        return tbSwVehicleScheddet;
    }

    public VehicleScheduleDetDTO removeTbSwVehicleScheddet(VehicleScheduleDetDTO tbSwVehicleScheddet) {
        getTbSwVehicleScheddets().remove(tbSwVehicleScheddet);
        tbSwVehicleScheddet.setTbSwVehicleScheduling(null);

        return tbSwVehicleScheddet;
    }

    public String getVeDesc() {
        return veDesc;
    }

    public void setVeDesc(String veDesc) {
        this.veDesc = veDesc;
    }

    public String getVeRegnNo() {
        return veRegnNo;
    }

    public void setVeRegnNo(String veRegnNo) {
        this.veRegnNo = veRegnNo;
    }

      public String getVehicleTypeMar() {
        return vehicleTypeMar;
    }

    public void setVehicleTypeMar(String vehicleTypeMar) {
        this.vehicleTypeMar = vehicleTypeMar;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadNameReg() {
        return roadNameReg;
    }

    public void setRoadNameReg(String roadNameReg) {
        this.roadNameReg = roadNameReg;
    }

    public String getVehicleTypeMar1() {
        return vehicleTypeMar1;
    }

    public void setVehicleTypeMar1(String vehicleTypeMar1) {
        this.vehicleTypeMar1 = vehicleTypeMar1;
    }

    public String getVehStartTym() {
        return vehStartTym;
    }

    public void setVehStartTym(String vehStartTym) {
        this.vehStartTym = vehStartTym;
    }

    public String getVehEndTym() {
        return vehEndTym;
    }

    public void setVehEndTym(String vehEndTym) {
        this.vehEndTym = vehEndTym;
    }

    public List<VehicleScheduleDTO> getVehicleScheduleList() {
        return vehicleScheduleList;
    }

    public void setVehicleScheduleList(List<VehicleScheduleDTO> vehicleScheduleList) {
        this.vehicleScheduleList = vehicleScheduleList;
    }

    public Date getVehStartTime() {
        return vehStartTime;
    }

    public void setVehStartTime(Date vehStartTime) {
        this.vehStartTime = vehStartTime;
    }

    public String getVesWeekday() {
        return vesWeekday;
    }

    public void setVesWeekday(String vesWeekday) {
        this.vesWeekday = vesWeekday;
    }

    public List<Date> getSheduleDate() {
        return sheduleDate;
    }

    public void setSheduleDate(List<Date> sheduleDate) {
        this.sheduleDate = sheduleDate;
    }

    public Date getVeScheduledate() {
        return veScheduledate;
    }

    public void setVeScheduledate(Date veScheduledate) {
        this.veScheduledate = veScheduledate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}