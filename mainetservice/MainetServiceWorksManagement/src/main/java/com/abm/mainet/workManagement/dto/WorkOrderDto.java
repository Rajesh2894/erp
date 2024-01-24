package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.master.dto.ContractMastDTO;

/**
 * 
 * @author Jeetendra.Pal
 * @Since 14-May-2018
 *
 */
public class WorkOrderDto implements Serializable {

    private static final long serialVersionUID = 7347606808100578598L;

    private Long workId;
    private Date startDate;
    private String workOrderNo;
    private Long liabilityPeriod;
    private Date orderDate;
    private Date contractFromDate;
    private Date contractToDate;
    private TenderMasterDto tenderMasterDto;
    private ContractMastDTO contractMastDTO;
    private List<WorkOrderTermsDto> workOrderTermsDtoList = new ArrayList<>();
    private String tenderNo;
    private String workOrderDate;
    private Date completionDate;
    private Date completeDate;
	private String workOrderStartDate;
    private String agreeFromDate;
    private String agreeToDate;
    private String mbStatus;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String vendorName;
    private String orderDateDesc;
    private String agrFromDateDesc;
    private String agrToDateDesc;
    private String contractNo;
    private String workName;
    private String contractFromDateDesc;
    private String contractToDateDesc;
    private String actualStartDateDesc;
    private String contractorName;
    private BigDecimal contractAmt;
    private String workOrderStatus;
    private String contractDate;
    private String contNo;
    private Double contractAmount;
    private BigDecimal contractValue;
    private BigDecimal mbTotalAmt;
    private Long mbId;
    private String mbNo;
    private String workAssigneeName;
    private Long contId;
    private String workAssignee;
    private List<String> multiSelect = new ArrayList<>();
    private Date workAssigneeDate;
    private String workStatus;
    private String projName;
    private String workMbTakenDate;
    private Long deptId;
    private Long dpDeptId;
	private Long codId1;
    private Long codId2;
    private Long codId3;
    private String remark;
	private WorkDefinationWardZoneDetailsDto wardZoneDto;
	
	 public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
    
    public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

    
    public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public Long getLiabilityPeriod() {
        return liabilityPeriod;
    }

    public void setLiabilityPeriod(Long liabilityPeriod) {
        this.liabilityPeriod = liabilityPeriod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getContractFromDate() {
        return contractFromDate;
    }

    public void setContractFromDate(Date contractFromDate) {
        this.contractFromDate = contractFromDate;
    }

    public Date getContractToDate() {
        return contractToDate;
    }

    public void setContractToDate(Date contractToDate) {
        this.contractToDate = contractToDate;
    }

    public TenderMasterDto getTenderMasterDto() {
        return tenderMasterDto;
    }

    public void setTenderMasterDto(TenderMasterDto tenderMasterDto) {
        this.tenderMasterDto = tenderMasterDto;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public List<WorkOrderTermsDto> getWorkOrderTermsDtoList() {
        return workOrderTermsDtoList;
    }

    public void setWorkOrderTermsDtoList(List<WorkOrderTermsDto> workOrderTermsDtoList) {
        this.workOrderTermsDtoList = workOrderTermsDtoList;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public String getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(String workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public String getWorkOrderStartDate() {
        return workOrderStartDate;
    }

    public void setWorkOrderStartDate(String workOrderStartDate) {
        this.workOrderStartDate = workOrderStartDate;
    }

    public String getAgreeFromDate() {
        return agreeFromDate;
    }

    public void setAgreeFromDate(String agreeFromDate) {
        this.agreeFromDate = agreeFromDate;
    }

    public String getAgreeToDate() {
        return agreeToDate;
    }

    public void setAgreeToDate(String agreeToDate) {
        this.agreeToDate = agreeToDate;
    }

    public String getMbStatus() {
        return mbStatus;
    }

    public void setMbStatus(String mbStatus) {
        this.mbStatus = mbStatus;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getOrderDateDesc() {
        return orderDateDesc;
    }

    public void setOrderDateDesc(String orderDateDesc) {
        this.orderDateDesc = orderDateDesc;
    }

    public String getAgrFromDateDesc() {
        return agrFromDateDesc;
    }

    public void setAgrFromDateDesc(String agrFromDateDesc) {
        this.agrFromDateDesc = agrFromDateDesc;
    }

    public String getAgrToDateDesc() {
        return agrToDateDesc;
    }

    public void setAgrToDateDesc(String agrToDateDesc) {
        this.agrToDateDesc = agrToDateDesc;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public ContractMastDTO getContractMastDTO() {
        return contractMastDTO;
    }

    public void setContractMastDTO(ContractMastDTO contractMastDTO) {
        this.contractMastDTO = contractMastDTO;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getContractFromDateDesc() {
        return contractFromDateDesc;
    }

    public void setContractFromDateDesc(String contractFromDateDesc) {
        this.contractFromDateDesc = contractFromDateDesc;
    }

    public String getContractToDateDesc() {
        return contractToDateDesc;
    }

    public void setContractToDateDesc(String contractToDateDesc) {
        this.contractToDateDesc = contractToDateDesc;
    }

    public String getActualStartDateDesc() {
        return actualStartDateDesc;
    }

    public void setActualStartDateDesc(String actualStartDateDesc) {
        this.actualStartDateDesc = actualStartDateDesc;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public Long getMbId() {
        return mbId;
    }

    public void setMbId(Long mbId) {
        this.mbId = mbId;
    }

    public String getMbNo() {
        return mbNo;
    }

    public void setMbNo(String mbNo) {
        this.mbNo = mbNo;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public BigDecimal getMbTotalAmt() {
        return mbTotalAmt;
    }

    public void setMbTotalAmt(BigDecimal mbTotalAmt) {
        this.mbTotalAmt = mbTotalAmt;
    }

    public String getWorkAssigneeName() {
        return workAssigneeName;
    }

    public void setWorkAssigneeName(String workAssigneeName) {
        this.workAssigneeName = workAssigneeName;
    }

    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public String getWorkAssignee() {
        return workAssignee;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public void setWorkAssignee(String workAssignee) {
        this.workAssignee = workAssignee;
    }

    public Date getWorkAssigneeDate() {
        return workAssigneeDate;
    }

    public void setWorkAssigneeDate(Date workAssigneeDate) {
        this.workAssigneeDate = workAssigneeDate;
    }

    public List<String> getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(List<String> multiSelect) {
        this.multiSelect = multiSelect;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public BigDecimal getContractValue() {
        return contractValue;
    }

    public void setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
    }

	public String getWorkMbTakenDate() {
		return workMbTakenDate;
	}

	public void setWorkMbTakenDate(String workMbTakenDate) {
		this.workMbTakenDate = workMbTakenDate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public Long getCodId1() {
		return codId1;
	}

	public void setCodId1(Long codId1) {
		this.codId1 = codId1;
	}

	public Long getCodId2() {
		return codId2;
	}

	public void setCodId2(Long codId2) {
		this.codId2 = codId2;
	}

	public Long getCodId3() {
		return codId3;
	}

	public void setCodId3(Long codId3) {
		this.codId3 = codId3;
	}

	public WorkDefinationWardZoneDetailsDto getWardZoneDto() {
		return wardZoneDto;
	}

	public void setWardZoneDto(WorkDefinationWardZoneDetailsDto wardZoneDto) {
		this.wardZoneDto = wardZoneDto;
	}
	
}
