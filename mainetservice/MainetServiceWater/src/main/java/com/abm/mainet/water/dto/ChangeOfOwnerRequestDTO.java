package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ChangeOfOwnerRequestDTO implements Serializable {

    private static final long serialVersionUID = 1900116677228883696L;

    private long orgnId;

    private Long csIdn;

    private Long apmApplicationId;

    private Date cooApldate;

    private Long cooNotitle;

    private String cooNoname;

    private String cooNomname;

    private String cooNolname;

    private Long cooOtitle;

    private String cooOname;

    private String cooOomname;

    private String cooOolname;

    private String cooRemark;

    private String cooGranted;

    private Long userEmpId;

    private int langId;

    private String cooNotitleCopy;

    private String lgIpMac;

    private Long cooUidNo;

    private Long conUidNo;

    private long serviceId;

    private double amount;

    private int documentSize;

    private String onlineOfflineCheck;

    private List<ChangeOfOwnerResponseDTO> responseDto = new ArrayList<>(0);

    private ApplicantDetailDTO applicant;

    private List<String> fileList;

    private List<DocumentDetailsVO> uploadedDocList;

    private long departmenttId;

    private String connectionNo;
    private Long gender;
    private List<AdditionalOwnerInfoDTO> additionalOwners;
    private ChangeOfOwnerResponseDTO oldOwnerInfo;

    private Long ownerTransferMode;

    public long getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(final long orgnId) {
        this.orgnId = orgnId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getCooApldate() {
        return cooApldate;
    }

    public void setCooApldate(final Date cooApldate) {
        this.cooApldate = cooApldate;
    }

    public Long getCooNotitle() {
        return cooNotitle;
    }

    public void setCooNotitle(final Long cooNotitle) {
        this.cooNotitle = cooNotitle;
    }

    public String getCooNoname() {
        return cooNoname;
    }

    public void setCooNoname(final String cooNoname) {
        this.cooNoname = cooNoname;
    }

    public String getCooNomname() {
        return cooNomname;
    }

    public void setCooNomname(final String cooNomname) {
        this.cooNomname = cooNomname;
    }

    public String getCooNolname() {
        return cooNolname;
    }

    public void setCooNolname(final String cooNolname) {
        this.cooNolname = cooNolname;
    }

    public Long getCooOtitle() {
        return cooOtitle;
    }

    public void setCooOtitle(final Long cooOtitle) {
        this.cooOtitle = cooOtitle;
    }

    public String getCooOname() {
        return cooOname;
    }

    public void setCooOname(final String cooOname) {
        this.cooOname = cooOname;
    }

    public String getCooOomname() {
        return cooOomname;
    }

    public void setCooOomname(final String cooOomname) {
        this.cooOomname = cooOomname;
    }

    public String getCooOolname() {
        return cooOolname;
    }

    public void setCooOolname(final String cooOolname) {
        this.cooOolname = cooOolname;
    }

    public String getCooRemark() {
        return cooRemark;
    }

    public void setCooRemark(final String cooRemark) {
        this.cooRemark = cooRemark;
    }

    public String getCooGranted() {
        return cooGranted;
    }

    public void setCooGranted(final String cooGranted) {
        this.cooGranted = cooGranted;
    }

    public Long getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(final Long userEmpId) {
        this.userEmpId = userEmpId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public String getCooNotitleCopy() {
        return cooNotitleCopy;
    }

    public void setCooNotitleCopy(final String cooNotitleCopy) {
        this.cooNotitleCopy = cooNotitleCopy;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final long serviceId) {
        this.serviceId = serviceId;
    }

    public List<ChangeOfOwnerResponseDTO> getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(final List<ChangeOfOwnerResponseDTO> responseDto) {
        this.responseDto = responseDto;
    }

    public Long getCooUidNo() {
        return cooUidNo;
    }

    public void setCooUidNo(final Long cooUidNo) {
        this.cooUidNo = cooUidNo;
    }

    public Long getConUidNo() {
        return conUidNo;
    }

    public void setConUidNo(final Long conUidNo) {
        this.conUidNo = conUidNo;
    }

    public ApplicantDetailDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(final ApplicantDetailDTO applicant) {
        this.applicant = applicant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public int getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(final int documentSize) {
        this.documentSize = documentSize;
    }

    public String getOnlineOfflineCheck() {
        return onlineOfflineCheck;
    }

    public void setOnlineOfflineCheck(final String onlineOfflineCheck) {
        this.onlineOfflineCheck = onlineOfflineCheck;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(final List<String> fileList) {
        this.fileList = fileList;
    }

    public List<DocumentDetailsVO> getUploadedDocList() {
        return uploadedDocList;
    }

    public void setUploadedDocList(final List<DocumentDetailsVO> uploadedDocList) {
        this.uploadedDocList = uploadedDocList;
    }

    public long getDepartmenttId() {
        return departmenttId;
    }

    public void setDepartmenttId(final long departmenttId) {
        this.departmenttId = departmenttId;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(final Long gender) {
        this.gender = gender;
    }

    public List<AdditionalOwnerInfoDTO> getAdditionalOwners() {
        return additionalOwners;
    }

    public void setAdditionalOwners(final List<AdditionalOwnerInfoDTO> additionalOwners) {
        this.additionalOwners = additionalOwners;
    }

    public ChangeOfOwnerResponseDTO getOldOwnerInfo() {
        return oldOwnerInfo;
    }

    public void setOldOwnerInfo(final ChangeOfOwnerResponseDTO oldOwnerInfo) {
        this.oldOwnerInfo = oldOwnerInfo;
    }

    public Long getOwnerTransferMode() {
        return ownerTransferMode;
    }

    public void setOwnerTransferMode(final Long ownerTransferMode) {
        this.ownerTransferMode = ownerTransferMode;
    }
}
