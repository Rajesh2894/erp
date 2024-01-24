package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MutationRegistrationDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2446497050885470774L;

    private String mutId;

    private String deptCode;

    private String villageCode;

    private String khasraOrPlotNo;

    private Long totalarea;

    private String registrationNo;

    private Date registrationDate;

    private String propertyno;

    private Date docExecutionDate;

    private String landType;

    private String propertyType;

    private String mutationType;

    private String mutationOrderNo;

    private Date mutationDate;

    private Long orgId;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String registrationDocument;

    private String mutationDocument;

    private List<ExecutantsDto> executantsDetailsList = new ArrayList<>();

    private List<ClaimantDto> claimantDetailsList = new ArrayList<>();

    public String getMutId() {
        return mutId;
    }

    public void setMutId(String mutId) {
        this.mutId = mutId;
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

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getKhasraOrPlotNo() {
        return khasraOrPlotNo;
    }

    public void setKhasraOrPlotNo(String khasraOrPlotNo) {
        this.khasraOrPlotNo = khasraOrPlotNo;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPropertyno() {
        return propertyno;
    }

    public void setPropertyno(String propertyno) {
        this.propertyno = propertyno;
    }

    public Date getDocExecutionDate() {
        return docExecutionDate;
    }

    public void setDocExecutionDate(Date docExecutionDate) {
        this.docExecutionDate = docExecutionDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public List<ExecutantsDto> getExecutantsDetailsList() {
        return executantsDetailsList;
    }

    public void setExecutantsDetailsList(List<ExecutantsDto> executantsDetailsList) {
        this.executantsDetailsList = executantsDetailsList;
    }

    public List<ClaimantDto> getClaimantDetailsList() {
        return claimantDetailsList;
    }

    public void setClaimantDetailsList(List<ClaimantDto> claimantDetailsList) {
        this.claimantDetailsList = claimantDetailsList;
    }

    public Long getTotalarea() {
        return totalarea;
    }

    public void setTotalarea(Long totalarea) {
        this.totalarea = totalarea;
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

    public Date getMutationDate() {
        return mutationDate;
    }

    public void setMutationDate(Date mutationDate) {
        this.mutationDate = mutationDate;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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

    @Override
    public String toString() {
        return "MutationRegistrationDto [mutId=" + mutId + ", deptCode=" + deptCode + ", villageCode=" + villageCode
                + ", khasraOrPlotNo=" + khasraOrPlotNo + ", totalarea=" + totalarea + ", registrationNo=" + registrationNo
                + ", registrationDate=" + registrationDate + ", propertyno=" + propertyno + ", docExecutionDate="
                + docExecutionDate + ", landType=" + landType + ", propertyType=" + propertyType + ", mutationType="
                + mutationType + ", mutationOrderNo=" + mutationOrderNo + ", mutationDate=" + mutationDate + ", orgId=" + orgId
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", registrationDocument="
                + registrationDocument + ", mutationDocument=" + mutationDocument + ", executantsDetailsList="
                + executantsDetailsList + ", claimantDetailsList=" + claimantDetailsList + "]";
    }

}
