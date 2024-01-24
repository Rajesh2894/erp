/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_AST_TRANSFER_HIST")
public class AssetTransferHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -938539674471603682L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRANSFER_HIST_ID", nullable = false)
    private Long transferHistId;

    @Column(name = "TRANSFER_ID", nullable = false)
    private Long transferId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetCode;

    @Column(name = "DOC_DATE", nullable = false)
    private Date docDate;

    @Column(name = "POST_DATE", nullable = true)
    private Date postDate;

    @Column(name = "TRANSFER_TYPE", nullable = false)
    private String transferType;

    @Column(name = "REMARK", nullable = true)
    private String remark;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "TRANSFER_COST_CENTER", nullable = true)
    private Long transferCostCenter;

    @Column(name = "TRANSFER_EMPLOYEE", nullable = true)
    private Long transferEmployee;

    @Column(name = "TRANSFER_LOCATION", nullable = true)
    private Long transferLocation;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "TRANSFER_FLAG", nullable = true)
    private String transferFlag;

    @Column(name = "H_STATUS", nullable = true)
    private String historyFlag;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;
    
    @Column(name = "TRANSFER_DEPART", nullable = true)
    private Long transferDepartment;
    
    

    /**
     * @return the transferHistId
     */
    public Long getTransferHistId() {
        return transferHistId;
    }

    /**
     * @param transferHistId the transferHistId to set
     */
    public void setTransferHistId(Long transferHistId) {
        this.transferHistId = transferHistId;
    }

    /**
     * @return the transferId
     */
    public Long getTransferId() {
        return transferId;
    }

    /**
     * @param transferId the transferId to set
     */
    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    /**
     * @return the assetCode
     */
    public AssetInformation getAssetCode() {
        return assetCode;
    }

    /**
     * @param assetCode the assetCode to set
     */
    public void setAssetCode(AssetInformation assetCode) {
        this.assetCode = assetCode;
    }

    /**
     * @return the docDate
     */
    public Date getDocDate() {
        return docDate;
    }

    /**
     * @param docDate the docDate to set
     */
    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    /**
     * @return the transferType
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * @param transferType the transferType to set
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * @return the transferCostCenter
     */
    public Long getTransferCostCenter() {
        return transferCostCenter;
    }

    /**
     * @param transferCostCenter the transferCostCenter to set
     */
    public void setTransferCostCenter(Long transferCostCenter) {
        this.transferCostCenter = transferCostCenter;
    }

    /**
     * @return the transferEmployee
     */
    public Long getTransferEmployee() {
        return transferEmployee;
    }

    /**
     * @param transferEmployee the transferEmployee to set
     */
    public void setTransferEmployee(Long transferEmployee) {
        this.transferEmployee = transferEmployee;
    }

    /**
     * @return the transferLocation
     */
    public Long getTransferLocation() {
        return transferLocation;
    }

    /**
     * @param transferLocation the transferLocation to set
     */
    public void setTransferLocation(Long transferLocation) {
        this.transferLocation = transferLocation;
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
     * @return the transferFlag
     */
    public String getTransferFlag() {
        return transferFlag;
    }

    /**
     * @param transferFlag the transferFlag to set
     */
    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    /**
     * @return the historyFlag
     */
    public String getHistoryFlag() {
        return historyFlag;
    }

    /**
     * @param historyFlag the historyFlag to set
     */
    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    
    

    public Long getTransferDepartment() {
		return transferDepartment;
	}

	public void setTransferDepartment(Long transferDepartment) {
		this.transferDepartment = transferDepartment;
	}

	public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_TRANSFER_HIST",
                "TRANSFER_HIST_ID" };
    }
}
