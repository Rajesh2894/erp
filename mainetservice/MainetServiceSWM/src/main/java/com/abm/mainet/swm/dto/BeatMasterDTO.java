package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
public class BeatMasterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long beatId;

    private BigDecimal beatDistance;

    private Long roDistanceUnit;

    private Long beatEndPoint;

    private String beatEndPointName;

    private String beatName;

    private String beatNameReg;

    private String beatNo;

    private Long beatStartPoint;

    private String beatStartPointName;
    
    private String beatEndPointNameReg;
    
    private String beatStartPointNameReg;

    private Long beatVeType;

    private BigDecimal beatDistDes;

    private Long beatDistDesUnit;

    private Long mrfId;

    private String deName;

    private String startLattitude;

    private String endLattitude;

    private String startLongitude;

    private String endLongitude;

    private String beatActive;
    
    private Long beatPopulation;

    private Long beatResCount;

    private Long beatComCount;

    private Long beatIndCount;    
   
    private Long animalCount;    
   
    private Long decompHouse;

    private Long areaType;

    private Long wetAssQty;

    private Long dryAssQty;

    private Long hazAssQty;

    private Long collCount;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private List<RouteDetailsDTO> tbSwRootDets;
    
    private List<BeatDetailDto> tbSwBeatDetail = new ArrayList<>(0);

    public List<BeatDetailDto> getTbSwBeatDetail() {
        return tbSwBeatDetail;
    }

    public void setTbSwBeatDetail(List<BeatDetailDto> tbSwBeatDetail) {
        this.tbSwBeatDetail = tbSwBeatDetail;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getBeatDistance() {
        return beatDistance;
    }

    public void setBeatDistance(BigDecimal beatDistance) {
        this.beatDistance = beatDistance;
    }

    public Long getRoDistanceUnit() {
        return roDistanceUnit;
    }

    public void setRoDistanceUnit(Long roDistanceUnit) {
        this.roDistanceUnit = roDistanceUnit;
    }

    public Long getBeatEndPoint() {
        return beatEndPoint;
    }

    public void setBeatEndPoint(Long beatEndPoint) {
        this.beatEndPoint = beatEndPoint;
    }

    public String getBeatEndPointName() {
        return beatEndPointName;
    }

    public void setBeatEndPointName(String beatEndPointName) {
        this.beatEndPointName = beatEndPointName;
    }

    public String getBeatName() {
        return beatName;
    }

    public void setBeatName(String beatName) {
        this.beatName = beatName;
    }

    public String getBeatNameReg() {
        return beatNameReg;
    }

    public void setBeatNameReg(String beatNameReg) {
        this.beatNameReg = beatNameReg;
    }

    public String getBeatNo() {
        return beatNo;
    }

    public void setBeatNo(String beatNo) {
        this.beatNo = beatNo;
    }

    public Long getBeatStartPoint() {
        return beatStartPoint;
    }

    public void setBeatStartPoint(Long beatStartPoint) {
        this.beatStartPoint = beatStartPoint;
    }

    public String getBeatStartPointName() {
        return beatStartPointName;
    }

    public void setBeatStartPointName(String beatStartPointName) {
        this.beatStartPointName = beatStartPointName;
    }

    public Long getBeatVeType() {
        return beatVeType;
    }

    public void setBeatVeType(Long beatVeType) {
        this.beatVeType = beatVeType;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigDecimal getBeatDistDes() {
        return beatDistDes;
    }

    public void setBeatDistDes(BigDecimal beatDistDes) {
        this.beatDistDes = beatDistDes;
    }

    public Long getBeatDistDesUnit() {
        return beatDistDesUnit;
    }

    public void setBeatDistDesUnit(Long beatDistDesUnit) {
        this.beatDistDesUnit = beatDistDesUnit;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public String getDeName() {
        return deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    public String getStartLattitude() {
        return startLattitude;
    }

    public void setStartLattitude(String startLattitude) {
        this.startLattitude = startLattitude;
    }

    public String getEndLattitude() {
        return endLattitude;
    }

    public void setEndLattitude(String endLattitude) {
        this.endLattitude = endLattitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getBeatActive() {
        return beatActive;
    }

    public void setBeatActive(String beatActive) {
        this.beatActive = beatActive;
    }

    public Long getAreaType() {
        return areaType;
    }

    public void setAreaType(Long areaType) {
        this.areaType = areaType;
    }

    public Long getWetAssQty() {
        return wetAssQty;
    }

    public void setWetAssQty(Long wetAssQty) {
        this.wetAssQty = wetAssQty;
    }

    public Long getDryAssQty() {
        return dryAssQty;
    }

    public void setDryAssQty(Long dryAssQty) {
        this.dryAssQty = dryAssQty;
    }

    public Long getHazAssQty() {
        return hazAssQty;
    }

    public void setHazAssQty(Long hazAssQty) {
        this.hazAssQty = hazAssQty;
    }

    public Long getCollCount() {
        return collCount;
    }

    public void setCollCount(Long collCount) {
        this.collCount = collCount;
    }   

    public Long getBeatPopulation() {
        return beatPopulation;
    }

    public void setBeatPopulation(Long beatPopulation) {
        this.beatPopulation = beatPopulation;
    }

    public Long getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(Long animalCount) {
        this.animalCount = animalCount;
    }

    public Long getDecompHouse() {
        return decompHouse;
    }

    public void setDecompHouse(Long decompHouse) {
        this.decompHouse = decompHouse;
    }

    public Long getBeatResCount() {
        return beatResCount;
    }

    public void setBeatResCount(Long beatResCount) {
        this.beatResCount = beatResCount;
    }

    public Long getBeatComCount() {
        return beatComCount;
    }

    public void setBeatComCount(Long beatComCount) {
        this.beatComCount = beatComCount;
    }

    public Long getBeatIndCount() {
        return beatIndCount;
    }

    public void setBeatIndCount(Long beatIndCount) {
        this.beatIndCount = beatIndCount;
    }

    public List<RouteDetailsDTO> getTbSwRootDets() {
        return tbSwRootDets;
    }

    public void setTbSwRootDets(List<RouteDetailsDTO> tbSwRootDets) {
        this.tbSwRootDets = tbSwRootDets;
    }

    public String getBeatEndPointNameReg() {
        return beatEndPointNameReg;
    }

    public void setBeatEndPointNameReg(String beatEndPointNameReg) {
        this.beatEndPointNameReg = beatEndPointNameReg;
    }

    public String getBeatStartPointNameReg() {
        return beatStartPointNameReg;
    }

    public void setBeatStartPointNameReg(String beatStartPointNameReg) {
        this.beatStartPointNameReg = beatStartPointNameReg;
    }

    public String getRoNoandRoName() {

        final String fullName = replaceNull(getBeatNo()) + MainetConstants.WHITE_SPACE + replaceNull(getBeatName());

        return fullName.trim();
    }

    private String replaceNull(String name) {
        if (name == null) {

            name = MainetConstants.BLANK;
        }
        return name;
    }

}