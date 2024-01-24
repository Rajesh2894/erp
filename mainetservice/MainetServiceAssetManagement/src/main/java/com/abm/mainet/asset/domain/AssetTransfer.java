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
@Table(name = "TB_AST_TRANSFER")
public class AssetTransfer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2289391718500365757L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRANSFER_ID", nullable = false)
    private Long transferId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

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

    @Column(name = "AUTH_STATUS", nullable = true)
    private String authStatus;
    
    @Column(name = "TRANSFER_DEPART", nullable = true)
    private Long transferDepartment;

    @Column(name = "AUTH_BY", nullable = true)
    private Long authBy;
    @Column(name = "AUTH_DATE", nullable = true)
    private Date authDate;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getTransferCostCenter() {
        return transferCostCenter;
    }

    public void setTransferCostCenter(Long transferCostCenter) {
        this.transferCostCenter = transferCostCenter;
    }

    public Long getTransferEmployee() {
        return transferEmployee;
    }

    public void setTransferEmployee(Long transferEmployee) {
        this.transferEmployee = transferEmployee;
    }

    public Long getTransferLocation() {
        return transferLocation;
    }

    public void setTransferLocation(Long transferLocation) {
        this.transferLocation = transferLocation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_TRANSFER", "TRANSFER_ID" };
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Long getAuthBy() {
        return authBy;
    }

    public void setAuthBy(Long authBy) {
        this.authBy = authBy;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
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
    
    

}
