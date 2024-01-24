/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_ERROR")
public class AssetRegisterUploadError implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2173823241213152465L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ERR_ID")
    private Long errId;

    @Column(name = "ERR_DATA", nullable = true, length = 2000)
    private String errData;

    @Column(name = "ERR_DESCRIPTION", nullable = true, length = 2000)
    private String errDescription;

    @Column(name = "FILE_NAME ", nullable = true, length = 500)
    private String fileName;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "AST_TYPE", nullable = true)
    private String astType;

    /**
     * @return the errId
     */
    public Long getErrId() {
        return errId;
    }

    /**
     * @param errId the errId to set
     */
    public void setErrId(Long errId) {
        this.errId = errId;
    }

    /**
     * @return the errData
     */
    public String getErrData() {
        return errData;
    }

    /**
     * @param errData the errData to set
     */
    public void setErrData(String errData) {
        this.errData = errData;
    }

    /**
     * @return the errDescription
     */
    public String getErrDescription() {
        return errDescription;
    }

    /**
     * @param errDescription the errDescription to set
     */
    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the astType
     */
    public String getAstType() {
        return astType;
    }

    /**
     * @param astType the astType to set
     */
    public void setAstType(String astType) {
        this.astType = astType;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_ERROR",
                "ERR_ID" };
    }

}
