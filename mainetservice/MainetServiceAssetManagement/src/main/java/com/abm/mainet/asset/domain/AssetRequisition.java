package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

@Entity
@Table(name = "TB_AST_REQUISITION")
public class AssetRequisition implements Serializable {

    private static final long serialVersionUID = 755168356996091112L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_REQUISITION_ID", nullable = false)
    private Long assetRequisitionId;

    @Column(name = "AST_CATEGORY", nullable = false)
    private Long astCategory;

    @Column(name = "AST_NAME", length = 100, nullable = false)
    private String astName;

    @Column(name = "AST_DESC", length = 500, nullable = false)
    private String astDesc;

    @Column(name = "AST_QTY",nullable = false)
    private BigDecimal astQty;

    @Column(name = "AST_LOC", nullable = false)
    private Long astLoc;

    @Column(name = "AST_DEPT", nullable = false)
    private Long astDept;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;
    
    

    @Column(name = "ASSET_CODE",  nullable = false)
    private String assetCode;
    

    @Column(name = "SERIAL_NO", nullable = false)
    private String serialNo;
    
    @Column(name = "TOTAL_ASSET_QUANTITY", nullable = false)
    private BigDecimal astTotalQty;
    
    @Column(name = "REMAINING_QUANTITY", nullable = false)
    private BigDecimal astRemainingQty;
    
    
    @Column(name = "EMPLOYEE", nullable = false)
    private Long empId;
    
    @Column(name = "DISPATCHED_DATE", nullable = true)
    private Date dispatchedDate;
    
    @Column(name = "REQUISITION_NUMBER", nullable = false)
    private String requisitionNumber;
    
    @Column(name = "DISPATCH_QTY", nullable = false)
    private BigDecimal dispatchQuantity;
    
    @Column(name = "REJECTED_QTY", nullable = false)
    private Integer rejectedQuantity;
    
    @Column(name = "ASSET_ID", nullable =true )
    private String assetId;

    
    
    
    public Long getAssetRequisitionId() {
        return assetRequisitionId;
    }

    public void setAssetRequisitionId(Long assetRequisitionId) {
        this.assetRequisitionId = assetRequisitionId;
    }

    public Long getAstCategory() {
        return astCategory;
    }

    public void setAstCategory(Long astCategory) {
        this.astCategory = astCategory;
    }

    public String getAstName() {
        return astName;
    }

    public void setAstName(String astName) {
        this.astName = astName;
    }

    public String getAstDesc() {
        return astDesc;
    }

    public void setAstDesc(String astDesc) {
        this.astDesc = astDesc;
    }

    public BigDecimal getAstQty() {
        return astQty;
    }

    public void setAstQty(BigDecimal astQty) {
        this.astQty = astQty;
    }

    public Long getAstLoc() {
        return astLoc;
    }

    public void setAstLoc(Long astLoc) {
        this.astLoc = astLoc;
    }

    public Long getAstDept() {
        return astDept;
    }

    public void setAstDept(Long astDept) {
        this.astDept = astDept;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    

    public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	

	public BigDecimal getAstTotalQty() {
		return astTotalQty;
	}

	public void setAstTotalQty(BigDecimal astTotalQty) {
		this.astTotalQty = astTotalQty;
	}

	public BigDecimal getAstRemainingQty() {
		return astRemainingQty;
	}

	public void setAstRemainingQty(BigDecimal astRemainingQty) {
		this.astRemainingQty = astRemainingQty;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getDispatchedDate() {
		return dispatchedDate;
	}

	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	
	public String getRequisitionNumber() {
		return requisitionNumber;
	}

	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	
	public BigDecimal getDispatchQuantity() {
		return dispatchQuantity;
	}

	public void setDispatchQuantity(BigDecimal dispatchQuantity) {
		this.dispatchQuantity = dispatchQuantity;
	}
	
	public Integer getRejectedQuantity() {
		return rejectedQuantity; 
	}

	public void setRejectedQuantity(Integer rejectedQuantity) {
		this.rejectedQuantity = rejectedQuantity;
	}
	
	

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_REQUISITION", "ASSET_REQUISITION_ID" };
    }
}
