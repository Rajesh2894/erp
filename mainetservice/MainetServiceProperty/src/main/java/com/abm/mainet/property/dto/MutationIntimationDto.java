package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MutationIntimationDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3613071784038283811L;

    private long rowId;
    private String propertyno;
    private String propertyType;
    private String mutationType;
    private Long mutationTypeId;
    private String landType;
    private String district;
    private String tehsil;
    private String totalArea;
    private String excuClaimName;
    private String excuClaimMobileNo;
    private String registrationNo;
    private String mutationOrderNo;
    private String currentOwner;
    private String oldOwner;
    private String village;
    private String khasraPloatNo;
    private String regNoAndDate;
    private Date regFromDate;
    private Date registrationDate;
    private Date mutationDate;
    private Date docExecutaionDate;
    private Date regToDate;
    private Long orgId;
    private String mutationFilePath;
    private String docExecutaionFilePath;
    private String registrationDocument;
    private String mutationDocument;
    private List<ExecutantsDto> executantList = null;
    private List<ClaimantDto> claimantList = null;

    public String getPropertyno() {
        return propertyno;
    }

    public void setPropertyno(String propertyno) {
        this.propertyno = propertyno;
    }

    public String getExcuClaimName() {
        return excuClaimName;
    }

    public void setExcuClaimName(String excuClaimName) {
        this.excuClaimName = excuClaimName;
    }

    public String getExcuClaimMobileNo() {
        return excuClaimMobileNo;
    }

    public void setExcuClaimMobileNo(String excuClaimMobileNo) {
        this.excuClaimMobileNo = excuClaimMobileNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getMutationOrderNo() {
        return mutationOrderNo;
    }

    public void setMutationOrderNo(String mutationOrderNo) {
        this.mutationOrderNo = mutationOrderNo;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getOldOwner() {
        return oldOwner;
    }

    public void setOldOwner(String oldOwner) {
        this.oldOwner = oldOwner;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getKhasraPloatNo() {
        return khasraPloatNo;
    }

    public void setKhasraPloatNo(String khasraPloatNo) {
        this.khasraPloatNo = khasraPloatNo;
    }

    public String getRegNoAndDate() {
        return regNoAndDate;
    }

    public void setRegNoAndDate(String regNoAndDate) {
        this.regNoAndDate = regNoAndDate;
    }

    public Date getRegFromDate() {
        return regFromDate;
    }

    public void setRegFromDate(Date regFromDate) {
        this.regFromDate = regFromDate;
    }

    public Date getRegToDate() {
        return regToDate;
    }

    public void setRegToDate(Date regToDate) {
        this.regToDate = regToDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public long getEditFlag() {
        return getRowId();
    }

    public void setEditFlag(long l) {

    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getMutationType() {
        return mutationType;
    }

    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getMutationDate() {
        return mutationDate;
    }

    public void setMutationDate(Date mutationDate) {
        this.mutationDate = mutationDate;
    }

    public Date getDocExecutaionDate() {
        return docExecutaionDate;
    }

    public void setDocExecutaionDate(Date docExecutaionDate) {
        this.docExecutaionDate = docExecutaionDate;
    }

    public List<ExecutantsDto> getExecutantList() {
        return executantList;
    }

    public void setExecutantList(List<ExecutantsDto> executantList) {
        this.executantList = executantList;
    }

    public List<ClaimantDto> getClaimantList() {
        return claimantList;
    }

    public void setClaimantList(List<ClaimantDto> claimantList) {
        this.claimantList = claimantList;
    }

    public String getMutationFilePath() {
        return mutationFilePath;
    }

    public void setMutationFilePath(String mutationFilePath) {
        this.mutationFilePath = mutationFilePath;
    }

    public String getDocExecutaionFilePath() {
        return docExecutaionFilePath;
    }

    public void setDocExecutaionFilePath(String docExecutaionFilePath) {
        this.docExecutaionFilePath = docExecutaionFilePath;
    }

    public String getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(String registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    public String getMutationDocument() {
        return mutationDocument;
    }

    public void setMutationDocument(String mutationDocument) {
        this.mutationDocument = mutationDocument;
    }

    public Long getMutationTypeId() {
        return mutationTypeId;
    }

    public void setMutationTypeId(Long mutationTypeId) {
        this.mutationTypeId = mutationTypeId;
    }

}
