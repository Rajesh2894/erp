package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
public class TripSheetDTO implements Serializable {

    private static final long serialVersionUID = 7583893349934401930L;

    private Long tripId;

    private Long createdBy;

    private Date createdDate;

    private Long orgid;

    private String tripDrivername;

    private BigDecimal tripEntweight;

    private BigDecimal tripExitweight;

    private Date tripIntime;

    private Date tripOuttime;

    private BigDecimal tripTotalgarbage;

    private String tripWeslipno;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long veId;

    private Long deId;

    private Long empId;

    private Date tripDate;

    private String tripData;

    private String tripIntimeDesc;

    private String tripOuttimeDesc;

    private String veNo;

    private String deName;

    private String uploadFileName;

    private String veType;

    private String empName;

    private String noOfTrips;

    private BigDecimal Dry;

    private BigDecimal Wate;

    private BigDecimal hazardous;

    private BigDecimal sumOfGarbage;

    private BigDecimal totalGarbage;

    private String roidId;

    private String roName;

    private BigDecimal totalDry;

    private BigDecimal totalWate;

    private BigDecimal totalHazardous;

    private String wasteSeg;

    private List<TripSheetDTO> TripSheetDTO;

    private Date fromDate;

    private Date toDate;

    private Long no;

    private Long vetypValue;

    private String inTime;

    private String outTime;

    private Long beatNo;

    private List<TripSheetGarbageDetDTO> tbSwTripsheetGdets;

    private List<DocumentDetailsVO> documents = new ArrayList<>();

    public TripSheetDTO() {
    }

    public String getVeType() {
        return veType;
    }

    public void setVeType(String veType) {
        this.veType = veType;
    }

    public Long getTripId() {
        return this.tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getTripDrivername() {
        return this.tripDrivername;
    }

    public void setTripDrivername(String tripDrivername) {
        this.tripDrivername = tripDrivername;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public BigDecimal getTripEntweight() {
        return tripEntweight;
    }

    public void setTripEntweight(BigDecimal tripEntweight) {
        this.tripEntweight = tripEntweight;
    }

    public BigDecimal getTripExitweight() {
        return tripExitweight;
    }

    public void setTripExitweight(BigDecimal tripExitweight) {
        this.tripExitweight = tripExitweight;
    }

    public BigDecimal getTripTotalgarbage() {
        return tripTotalgarbage;
    }

    public void setTripTotalgarbage(BigDecimal tripTotalgarbage) {
        this.tripTotalgarbage = tripTotalgarbage;
    }

    public Date getTripIntime() {
        return this.tripIntime;
    }

    public void setTripIntime(Date tripIntime) {
        this.tripIntime = tripIntime;
    }

    public Date getTripOuttime() {
        return this.tripOuttime;
    }

    public void setTripOuttime(Date tripOuttime) {
        this.tripOuttime = tripOuttime;
    }

    public String getTripWeslipno() {
        return this.tripWeslipno;
    }

    public void setTripWeslipno(String tripWeslipno) {
        this.tripWeslipno = tripWeslipno;
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

    public List<TripSheetGarbageDetDTO> getTbSwTripsheetGdets() {
        return this.tbSwTripsheetGdets;
    }

    public void setTbSwTripsheetGdets(
            List<TripSheetGarbageDetDTO> tbSwTripsheetGdets) {
        this.tbSwTripsheetGdets = tbSwTripsheetGdets;
    }

    public TripSheetGarbageDetDTO addTbSwTripsheetGdet(
            TripSheetGarbageDetDTO tbSwTripsheetGdet) {
        getTbSwTripsheetGdets().add(tbSwTripsheetGdet);
        tbSwTripsheetGdet.setTbSwTripsheet(this);

        return tbSwTripsheetGdet;
    }

    public TripSheetGarbageDetDTO removeTbSwTripsheetGdet(
            TripSheetGarbageDetDTO tbSwTripsheetGdet) {
        getTbSwTripsheetGdets().remove(tbSwTripsheetGdet);
        tbSwTripsheetGdet.setTbSwTripsheet(null);

        return tbSwTripsheetGdet;
    }

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripData() {
        return tripData;
    }

    public void setTripData(String tripData) {
        this.tripData = tripData;
    }

    public String getTripIntimeDesc() {
        return tripIntimeDesc;
    }

    public void setTripIntimeDesc(String tripIntimeDesc) {
        this.tripIntimeDesc = tripIntimeDesc;
    }

    public String getTripOuttimeDesc() {
        return tripOuttimeDesc;
    }

    public void setTripOuttimeDesc(String tripOuttimeDesc) {
        this.tripOuttimeDesc = tripOuttimeDesc;
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

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public String getDeName() {
        return deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getNoOfTrips() {
        return noOfTrips;
    }

    public void setNoOfTrips(String noOfTrips) {
        this.noOfTrips = noOfTrips;
    }

    public BigDecimal getDry() {
        return Dry;
    }

    public void setDry(BigDecimal dry) {
        Dry = dry;
    }

    public BigDecimal getWate() {
        return Wate;
    }

    public void setWate(BigDecimal wate) {
        Wate = wate;
    }

    public BigDecimal getSumOfGarbage() {
        return sumOfGarbage;
    }

    public void setSumOfGarbage(BigDecimal sumOfGarbage) {
        this.sumOfGarbage = sumOfGarbage;
    }

    public List<TripSheetDTO> getTripSheetDTO() {
        return TripSheetDTO;
    }

    public void setTripSheetDTO(List<TripSheetDTO> tripSheetDTO) {
        TripSheetDTO = tripSheetDTO;
    }

    public BigDecimal getTotalGarbage() {
        return totalGarbage;
    }

    public void setTotalGarbage(BigDecimal totalGarbage) {
        this.totalGarbage = totalGarbage;
    }

    public String getRoidId() {
        return roidId;
    }

    public void setRoidId(String roidId) {
        this.roidId = roidId;
    }

    public String getRoName() {
        return roName;
    }

    public void setRoName(String roName) {
        this.roName = roName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Long getVetypValue() {
        return vetypValue;
    }

    public void setVetypValue(Long vetypValue) {
        this.vetypValue = vetypValue;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public Long getBeatNo() {
        return beatNo;
    }

    public void setBeatNo(Long beatNo) {
        this.beatNo = beatNo;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public List<DocumentDetailsVO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDetailsVO> documents) {
        this.documents = documents;
    }

    public BigDecimal getTotalDry() {
        return totalDry;
    }

    public void setTotalDry(BigDecimal totalDry) {
        this.totalDry = totalDry;
    }

    public BigDecimal getTotalWate() {
        return totalWate;
    }

    public void setTotalWate(BigDecimal totalWate) {
        this.totalWate = totalWate;
    }

    public BigDecimal getTotalHazardous() {
        return totalHazardous;
    }

    public void setTotalHazardous(BigDecimal totalHazardous) {
        this.totalHazardous = totalHazardous;
    }

    public BigDecimal getHazardous() {
        return hazardous;
    }

    public void setHazardous(BigDecimal hazardous) {
        this.hazardous = hazardous;
    }

    public String getWasteSeg() {
        return wasteSeg;
    }

    public void setWasteSeg(String wasteSeg) {
        this.wasteSeg = wasteSeg;
    }

}