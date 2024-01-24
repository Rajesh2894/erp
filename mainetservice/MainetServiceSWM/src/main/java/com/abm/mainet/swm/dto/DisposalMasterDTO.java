package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DisposalMasterDTO implements Serializable {

    private static final long serialVersionUID = 6655644630084404451L;

    private Long deId;

    private Long createdBy;

    private Date createdDate;

    private String deActive;

    private String deAddress;

    private BigDecimal deArea;

    private Long deAreaUnit;

    private BigDecimal deCapacity;

    private Long deCapacityUnit;

    private Long deCategory;

    private String deGisId;

    private String deName;

    private String deNameReg;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long deLocId;

    private Long updatedBy;

    private Date updatedDate;

    private String fromDate;

    private String toDate;

    private String tripDate;

    private BigDecimal dry;

    private BigDecimal wate;

    private BigDecimal sumOfDryWate;

    private BigDecimal totalVolume;

    private String addLatitude;

    private String addLongitude;

    private String siteImage;

    private Long veId;

    private Long vechAvlCountId;

    private Long vechReqCountId;

    private String projectCode;

    private Long empId;

    private Long desgId;

    private Long empAvlCountId;

    private Long empReqCountId;

    private List<DisposalMasterDTO> disposalMasterList;

    @JsonIgnore
    private List<DesposalDetailDTO> tbSwDesposalDets = new ArrayList<>();

    public DisposalMasterDTO() {
    }

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
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

    public String getDeActive() {
        return this.deActive;
    }

    public void setDeActive(String deActive) {
        this.deActive = deActive;
    }

    public String getDeAddress() {
        return this.deAddress;
    }

    public Long getDeLocId() {
        return deLocId;
    }

    public void setDeLocId(Long deLocId) {
        this.deLocId = deLocId;
    }

    public void setDeAddress(String deAddress) {
        this.deAddress = deAddress;
    }

    public BigDecimal getDeArea() {
        return this.deArea;
    }

    public void setDeArea(BigDecimal deArea) {
        this.deArea = deArea;
    }

    public Long getDeAreaUnit() {
        return deAreaUnit;
    }

    public void setDeAreaUnit(Long deAreaUnit) {
        this.deAreaUnit = deAreaUnit;
    }

    public BigDecimal getDeCapacity() {
        return this.deCapacity;
    }

    public void setDeCapacity(BigDecimal deCapacity) {
        this.deCapacity = deCapacity;
    }

    public Long getDeCapacityUnit() {
        return this.deCapacityUnit;
    }

    public void setDeCapacityUnit(Long deCapacityUnit) {
        this.deCapacityUnit = deCapacityUnit;
    }

    public Long getDeCategory() {
        return this.deCategory;
    }

    public void setDeCategory(Long deCategory) {
        this.deCategory = deCategory;
    }

    public String getDeGisId() {
        return this.deGisId;
    }

    public void setDeGisId(String deGisId) {
        this.deGisId = deGisId;
    }

    public String getDeName() {
        return this.deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    public String getDeNameReg() {
        return this.deNameReg;
    }

    public void setDeNameReg(String deNameReg) {
        this.deNameReg = deNameReg;
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

    public List<DesposalDetailDTO> getTbSwDesposalDets() {
        return this.tbSwDesposalDets;
    }

    public void setTbSwDesposalDets(List<DesposalDetailDTO> tbSwDesposalDets) {
        this.tbSwDesposalDets = tbSwDesposalDets;
    }

    public DesposalDetailDTO addTbSwDesposalDet(
            DesposalDetailDTO tbSwDesposalDet) {
        getTbSwDesposalDets().add(tbSwDesposalDet);
        tbSwDesposalDet.setTbSwDesposalMast(this);

        return tbSwDesposalDet;
    }

    public DesposalDetailDTO removeTbSwDesposalDet(
            DesposalDetailDTO tbSwDesposalDet) {
        getTbSwDesposalDets().remove(tbSwDesposalDet);
        tbSwDesposalDet.setTbSwDesposalMast(null);

        return tbSwDesposalDet;
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

    public List<DisposalMasterDTO> getDisposalMasterList() {
        return disposalMasterList;
    }

    public void setDisposalMasterList(
            List<DisposalMasterDTO> disposalMasterList) {
        this.disposalMasterList = disposalMasterList;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public BigDecimal getDry() {
        return dry;
    }

    public void setDry(BigDecimal dry) {
        this.dry = dry;
    }

    public BigDecimal getWate() {
        return wate;
    }

    public void setWate(BigDecimal wate) {
        this.wate = wate;
    }

    public BigDecimal getSumOfDryWate() {
        return sumOfDryWate;
    }

    public void setSumOfDryWate(BigDecimal sumOfDryWate) {
        this.sumOfDryWate = sumOfDryWate;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getAddLatitude() {
        return addLatitude;
    }

    public void setAddLatitude(String addLatitude) {
        this.addLatitude = addLatitude;
    }

    public String getAddLongitude() {
        return addLongitude;
    }

    public void setAddLongitude(String addLongitude) {
        this.addLongitude = addLongitude;
    }

    public String getSiteImage() {
        return siteImage;
    }

    public void setSiteImage(String siteImage) {
        this.siteImage = siteImage;
    }

    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getEmpAvlCountId() {
        return empAvlCountId;
    }

    public void setEmpAvlCountId(Long empAvlCountId) {
        this.empAvlCountId = empAvlCountId;
    }

    public Long getEmpReqCountId() {
        return empReqCountId;
    }

    public void setEmpReqCountId(Long empReqCountId) {
        this.empReqCountId = empReqCountId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Long getVechAvlCountId() {
        return vechAvlCountId;
    }

    public void setVechAvlCountId(Long vechAvlCountId) {
        this.vechAvlCountId = vechAvlCountId;
    }

    public Long getVechReqCountId() {
        return vechReqCountId;
    }

    public void setVechReqCountId(Long vechReqCountId) {
        this.vechReqCountId = vechReqCountId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getDesgId() {
        return desgId;
    }

    public void setDesgId(Long desgId) {
        this.desgId = desgId;
    }

}