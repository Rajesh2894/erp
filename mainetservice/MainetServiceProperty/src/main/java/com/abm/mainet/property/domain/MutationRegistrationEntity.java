package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_mut_registration")
public class MutationRegistrationEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3609910442654942377L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MUT_ID")
    private long mutId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "DP_DEPTID")
    private Long dpDeptid;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MN_PROPERTYNO")
    private String propertyno;

    @Column(name = "MUT_DOCEXECUTION_DATE")
    private Date docExecutionDate;

    @Column(name = "MUT_KHASPL_NO")
    private String khasraOrPlotNo;

    @Column(name = "MUT_LAND_ID")
    private Long landTypeId;

    @Column(name = "MUT_PROPTYPE")
    private Long propertyType;

    @Column(name = "MUT_REGISTRATIONDATE")
    private Date registrationDate;

    @Column(name = "MUT_REGISTRATIONNO")
    private String registrationNo;

    @Column(name = "MUT_TOTALAREA")
    private Long totalarea;

    @Column(name = "MUT_VGRC_CODE")
    private String villageCode;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "REGIS_DOC")
    private String registrationDocument;

    @Column(name = "MUTATATION_DOC")
    private String mutationDocument;

    @Column(name = "MUTATION_TYPE")
    private Long mutationType;

    @Column(name = "MUTATION_ORDER_NO")
    private String mutationOrderNo;

    @Column(name = "MUTATION_DATE")
    private Date mutationDate;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mutId", cascade = CascadeType.ALL)
    private List<MutRegdetEntity> mutRegdetEntityList;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_mut_registration", "MUT_ID" };
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

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(Long dpDeptid) {
        this.dpDeptid = dpDeptid;
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

    public String getKhasraOrPlotNo() {
        return khasraOrPlotNo;
    }

    public void setKhasraOrPlotNo(String khasraOrPlotNo) {
        this.khasraOrPlotNo = khasraOrPlotNo;
    }

    public Long getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Long propertyType) {
        this.propertyType = propertyType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Long getTotalarea() {
        return totalarea;
    }

    public void setTotalarea(Long totalarea) {
        this.totalarea = totalarea;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public List<MutRegdetEntity> getMutRegdetEntityList() {
        return mutRegdetEntityList;
    }

    public void setMutRegdetEntityList(List<MutRegdetEntity> mutRegdetEntityList) {
        this.mutRegdetEntityList = mutRegdetEntityList;
    }

    public long getMutId() {
        return mutId;
    }

    public void setMutId(long mutId) {
        this.mutId = mutId;
    }

    public Long getLandTypeId() {
        return landTypeId;
    }

    public void setLandTypeId(Long landTypeId) {
        this.landTypeId = landTypeId;
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

    public Long getMutationType() {
        return mutationType;
    }

    public void setMutationType(Long mutationType) {
        this.mutationType = mutationType;
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

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

}
