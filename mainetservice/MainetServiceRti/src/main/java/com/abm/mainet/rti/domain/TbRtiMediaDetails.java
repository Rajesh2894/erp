/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Entity class for Media Details for Respective RTI application filed.
 */
package com.abm.mainet.rti.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_RTI_MEDIA_DETAILS")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class TbRtiMediaDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3011378454321662333L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RTI_MED_ID", nullable = false)
    private Long rtiMediaId;

    @Column(name = "RTI_ID", nullable = false)
    private Long rtiMedId;

    /*
     * @Column(name = "RTI_ID") private Long rtiMedId;
     */

    @Column(name = "LOI_ID")
    private Long loiId;

    /*
     * @Column(name = "CARE_REQUEST_ID") private Long careRequestId;
     */

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "MEDIA_TYPE")
    private Long mediaType;

    @Column(name = "MEDIA_QUANTITY")
    private Long mediaQuantity;

    @Column(name = "MEDIA_AMOUNT")
    private Double mediaAmount;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lModDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ISDELETED")
    private int isDeleted;

    @Column(name = "UPL_FILE_NAME")
    private String uploadFileName;

    @Column(name = "UPL_FILE_PATH")
    private String uploadFilePath;

    @Column(name = "MEDIA_DESC", length = 200)
    private String mediaDesc;

    @Column(name = "media_status")
    private String mediastatus;

    public Long getRtiMediaId() {
        return rtiMediaId;
    }

    public void setRtiMediaId(Long rtiMediaId) {
        this.rtiMediaId = rtiMediaId;
    }

    public Long getLoiId() {
        return loiId;
    }

    public void setLoiId(Long loiId) {
        this.loiId = loiId;
    }

    /*
     * public Long getCareRequestId() { return careRequestId; } public void setCareRequestId(Long careRequestId) {
     * this.careRequestId = careRequestId; }
     */
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getMediaType() {
        return mediaType;
    }

    public void setMediaType(Long mediaType) {
        this.mediaType = mediaType;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getlModDate() {
        return lModDate;
    }

    public void setlModDate(Date lModDate) {
        this.lModDate = lModDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    /*
     * public Long getRtiMedId() { return rtiMedId; } public void setRtiMedId(Long rtiMedId) { this.rtiMedId = rtiMedId; }
     */

    public Long getMediaQuantity() {
        return mediaQuantity;
    }

    public void setMediaQuantity(Long mediaQuantity) {
        this.mediaQuantity = mediaQuantity;
    }

    public Double getMediaAmount() {
        return mediaAmount;
    }

    public void setMediaAmount(Double mediaAmount) {
        this.mediaAmount = mediaAmount;
    }

    public Long getRtiMedId() {
        return rtiMedId;
    }

    public void setRtiMedId(Long rtiMedId) {
        this.rtiMedId = rtiMedId;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String[] getPkValues() {
        return new String[] { "RTI", "TB_RTI_MEDIA_DETAILS", "RTI_MED_ID" };
    }

    public String getMediaDesc() {
        return mediaDesc;
    }

    public void setMediaDesc(String mediaDesc) {
        this.mediaDesc = mediaDesc;
    }

    public String getMediastatus() {
        return mediastatus;
    }

    public void setMediastatus(String mediastatus) {
        this.mediastatus = mediastatus;
    }

}
