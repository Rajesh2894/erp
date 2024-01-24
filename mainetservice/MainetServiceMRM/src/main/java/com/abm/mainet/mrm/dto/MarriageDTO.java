package com.abm.mainet.mrm.dto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class MarriageDTO extends RequestDTO implements Serializable {

    private static final long serialVersionUID = 1821229914189806099L;

    private Long marId;

    private Long applicationId;

    private String applicantName;

    private String appTime;

    private Date dueDate;

    private String dueTime;

    private String helpLine;

    private Date marDate;

    private Date appDate;

    private String placeMarE;

    private String placeMarR;

    private Long personalLaw;

    private String priestNameE;

    private String priestNameR;

    private String priestAddress;

    private Long priestReligion;

    private Integer priestAge;

    private String status;

    private String urlParam; // identify last save tab

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String serialNo;// generate using sequence master

    private String volume;// input from marriage certificate

    private Long ward1;
    private Long ward2;
    private Long ward3;
    private Long ward4;
    private Long ward5;

    private String ward1Desc;
    private String ward1RegDesc;
    private String ward2Desc;
    private String ward3Desc;
    private String ward4Desc;
    private String ward5Desc;

    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    @JsonManagedReference
    private HusbandDTO husbandDTO = new HusbandDTO();

    @JsonManagedReference
    private WifeDTO wifeDTO = new WifeDTO();

    @JsonManagedReference
    private List<WitnessDetailsDTO> witnessDetailsDTO = new ArrayList<WitnessDetailsDTO>();

    @JsonManagedReference
    private AppointmentDTO appointmentDTO = new AppointmentDTO();

    private Long noOfCopies;

    private String urlShortCode;
    private Map<String, File> uploadMap;

    private Long noOfWitness;// set using NOW prefix

    private List<String> errorList = new ArrayList<>();

    private String hitFrom;// identify punch from

    private String applnCopyTo;

    private Long smServiceDuration;

    private Date regDate;

    private String husbPhoPath;
    private String wifePhoPath;

    private String actionStatus;

    public Long getMarId() {
        return marId;
    }

    public void setMarId(Long marId) {
        this.marId = marId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getHelpLine() {
        return helpLine;
    }

    public void setHelpLine(String helpLine) {
        this.helpLine = helpLine;
    }

    public Date getMarDate() {
        return marDate;
    }

    public void setMarDate(Date marDate) {
        this.marDate = marDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getPlaceMarE() {
        return placeMarE;
    }

    public void setPlaceMarE(String placeMarE) {
        this.placeMarE = placeMarE;
    }

    public String getPlaceMarR() {
        return placeMarR;
    }

    public void setPlaceMarR(String placeMarR) {
        this.placeMarR = placeMarR;
    }

    public Long getPersonalLaw() {
        return personalLaw;
    }

    public void setPersonalLaw(Long personalLaw) {
        this.personalLaw = personalLaw;
    }

    public String getPriestNameE() {
        return priestNameE;
    }

    public void setPriestNameE(String priestNameE) {
        this.priestNameE = priestNameE;
    }

    public String getPriestNameR() {
        return priestNameR;
    }

    public void setPriestNameR(String priestNameR) {
        this.priestNameR = priestNameR;
    }

    public String getPriestAddress() {
        return priestAddress;
    }

    public void setPriestAddress(String priestAddress) {
        this.priestAddress = priestAddress;
    }

    public Long getPriestReligion() {
        return priestReligion;
    }

    public void setPriestReligion(Long priestReligion) {
        this.priestReligion = priestReligion;
    }

    public Integer getPriestAge() {
        return priestAge;
    }

    public void setPriestAge(Integer priestAge) {
        this.priestAge = priestAge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Long getWard1() {
        return ward1;
    }

    public void setWard1(Long ward1) {
        this.ward1 = ward1;
    }

    public Long getWard2() {
        return ward2;
    }

    public void setWard2(Long ward2) {
        this.ward2 = ward2;
    }

    public Long getWard3() {
        return ward3;
    }

    public void setWard3(Long ward3) {
        this.ward3 = ward3;
    }

    public Long getWard4() {
        return ward4;
    }

    public void setWard4(Long ward4) {
        this.ward4 = ward4;
    }

    public Long getWard5() {
        return ward5;
    }

    public void setWard5(Long ward5) {
        this.ward5 = ward5;
    }

    public String getWard1Desc() {
        return ward1Desc;
    }

    public void setWard1Desc(String ward1Desc) {
        this.ward1Desc = ward1Desc;
    }

    public String getWard1RegDesc() {
        return ward1RegDesc;
    }

    public void setWard1RegDesc(String ward1RegDesc) {
        this.ward1RegDesc = ward1RegDesc;
    }

    public String getWard2Desc() {
        return ward2Desc;
    }

    public void setWard2Desc(String ward2Desc) {
        this.ward2Desc = ward2Desc;
    }

    public String getWard3Desc() {
        return ward3Desc;
    }

    public void setWard3Desc(String ward3Desc) {
        this.ward3Desc = ward3Desc;
    }

    public String getWard4Desc() {
        return ward4Desc;
    }

    public void setWard4Desc(String ward4Desc) {
        this.ward4Desc = ward4Desc;
    }

    public String getWard5Desc() {
        return ward5Desc;
    }

    public void setWard5Desc(String ward5Desc) {
        this.ward5Desc = ward5Desc;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    public HusbandDTO getHusbandDTO() {
        return husbandDTO;
    }

    public void setHusbandDTO(HusbandDTO husbandDTO) {
        this.husbandDTO = husbandDTO;
    }

    public WifeDTO getWifeDTO() {
        return wifeDTO;
    }

    public void setWifeDTO(WifeDTO wifeDTO) {
        this.wifeDTO = wifeDTO;
    }

    public List<WitnessDetailsDTO> getWitnessDetailsDTO() {
        return witnessDetailsDTO;
    }

    public void setWitnessDetailsDTO(List<WitnessDetailsDTO> witnessDetailsDTO) {
        this.witnessDetailsDTO = witnessDetailsDTO;
    }

    public AppointmentDTO getAppointmentDTO() {
        return appointmentDTO;
    }

    public void setAppointmentDTO(AppointmentDTO appointmentDTO) {
        this.appointmentDTO = appointmentDTO;
    }

    public Long getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(Long noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getUrlShortCode() {
        return urlShortCode;
    }

    public void setUrlShortCode(String urlShortCode) {
        this.urlShortCode = urlShortCode;
    }

    public Map<String, File> getUploadMap() {
        return uploadMap;
    }

    public void setUploadMap(Map<String, File> uploadMap) {
        this.uploadMap = uploadMap;
    }

    public Long getNoOfWitness() {
        return noOfWitness;
    }

    public void setNoOfWitness(Long noOfWitness) {
        this.noOfWitness = noOfWitness;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public String getHitFrom() {
        return hitFrom;
    }

    public void setHitFrom(String hitFrom) {
        this.hitFrom = hitFrom;
    }

    public String getApplnCopyTo() {
        return applnCopyTo;
    }

    public void setApplnCopyTo(String applnCopyTo) {
        this.applnCopyTo = applnCopyTo;
    }

    public Long getSmServiceDuration() {
        return smServiceDuration;
    }

    public void setSmServiceDuration(Long smServiceDuration) {
        this.smServiceDuration = smServiceDuration;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getHusbPhoPath() {
        return husbPhoPath;
    }

    public void setHusbPhoPath(String husbPhoPath) {
        this.husbPhoPath = husbPhoPath;
    }

    public String getWifePhoPath() {
        return wifePhoPath;
    }

    public void setWifePhoPath(String wifePhoPath) {
        this.wifePhoPath = wifePhoPath;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    @Override
    public String toString() {
        return "MarriageDTO [marId=" + marId + ", applicationId=" + applicationId + ", applicantName=" + applicantName
                + ", appTime=" + appTime + ", dueDate=" + dueDate + ", dueTime=" + dueTime + ", helpLine=" + helpLine
                + ", marDate=" + marDate + ", appDate=" + appDate + ", placeMarE=" + placeMarE + ", placeMarR=" + placeMarR
                + ", personalLaw=" + personalLaw + ", priestNameE=" + priestNameE + ", priestNameR=" + priestNameR
                + ", priestAddress=" + priestAddress + ", priestReligion=" + priestReligion + ", priestAge=" + priestAge
                + ", status=" + status + ", urlParam=" + urlParam + ", orgId=" + orgId + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", serialNo=" + serialNo + ", volume=" + volume + ", ward1=" + ward1
                + ", ward2=" + ward2 + ", ward3=" + ward3 + ", ward4=" + ward4 + ", ward5=" + ward5 + ", ward1Desc=" + ward1Desc
                + ", ward1RegDesc=" + ward1RegDesc + ", ward2Desc=" + ward2Desc + ", ward3Desc=" + ward3Desc + ", ward4Desc="
                + ward4Desc + ", ward5Desc=" + ward5Desc + ", applicantDetailDto=" + applicantDetailDto + ", husbandDTO="
                + husbandDTO + ", wifeDTO=" + wifeDTO + ", witnessDetailsDTO=" + witnessDetailsDTO + ", appointmentDTO="
                + appointmentDTO + ", noOfCopies=" + noOfCopies + ", urlShortCode=" + urlShortCode + ", uploadMap=" + uploadMap
                + ", noOfWitness=" + noOfWitness + ", errorList=" + errorList + ", hitFrom=" + hitFrom + ", applnCopyTo="
                + applnCopyTo + ", smServiceDuration=" + smServiceDuration + ", regDate=" + regDate + "]";
    }

}
