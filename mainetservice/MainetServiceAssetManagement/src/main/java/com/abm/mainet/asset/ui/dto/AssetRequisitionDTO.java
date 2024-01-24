package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssetRequisitionDTO implements Serializable {

    private static final long serialVersionUID = -6342061412114362684L;

    private Long assetRequisitionId;
    private Long astCategory;
    private String astCategoryDesc;
    private String astName;
    private String astDesc;
    private BigDecimal astQty;
    private Long astLoc;
    private String astLocDesc;
    private Long astDept;
    private String astDeptDesc;
    private Long orgId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String deptCode;
    private String status;
    private BigDecimal astTotalQty;
    private BigDecimal astRemainingQty;
    private Long empId;
    private Date dispatchedDate ;
    private String serialNo;
    private String assetCode;
    private String requisitionNumber;
    private BigDecimal dispatchQuantity;
    private Integer rejectedQuantity;
    private String assetId;
    private Long ward1;
    private Long ward2;
    private Long ward3;
    private Long ward4;
    private Long ward5;
    
    
    
    
    
   
    
    List<SummaryDTO> dto= new ArrayList<>();

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

    public String getAstCategoryDesc() {
        return astCategoryDesc;
    }

    public void setAstCategoryDesc(String astCategoryDesc) {
        this.astCategoryDesc = astCategoryDesc;
    }

    public String getAstLocDesc() {
        return astLocDesc;
    }

    public void setAstLocDesc(String astLocDesc) {
        this.astLocDesc = astLocDesc;
    }

    public String getAstDeptDesc() {
        return astDeptDesc;
    }

    public void setAstDeptDesc(String astDeptDesc) {
        this.astDeptDesc = astDeptDesc;
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

	public List<SummaryDTO> getDto() {
		return dto;
	}

	public void setDto(List<SummaryDTO> dto) {
		this.dto = dto;
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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
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
	
	
	
	
	

}
