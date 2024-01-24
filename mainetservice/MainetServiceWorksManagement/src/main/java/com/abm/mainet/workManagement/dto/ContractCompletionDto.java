package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.common.domain.Employee;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class ContractCompletionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7681279700957546566L;
	private Long workId;
	private String workName;
	private Long projId;
	private String projName;
	private String workStartDate;
	private String workEndDate;
	private Long workType;
	private String workTypeDesc;
	private String LastMBFileDate;
	private String completionDate;
	private String contractNo;
	private String contractStartDate;
	private String contractEndDate;
	private String contractorName;
	private String completionNo;
	private WorkDefinationAssetInfoDto assetInfoDto = new WorkDefinationAssetInfoDto();
	private String contDate;
	private Long vendorId;
	private Long projDeptId;
	private String workCode;
	private Long locId;
	private Date acquisitionDate;
	private BigDecimal totAmount;
	private Employee emp;
	private Long orgId;
	private Long workCategory;
	private String assetCategory;
	private String orgState;


	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(String workStartDate) {
		this.workStartDate = workStartDate;
	}

	public String getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getLastMBFileDate() {
		return LastMBFileDate;
	}

	public void setLastMBFileDate(String lastMBFileDate) {
		LastMBFileDate = lastMBFileDate;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public String getWorkTypeDesc() {
		return workTypeDesc;
	}

	public void setWorkTypeDesc(String workTypeDesc) {
		this.workTypeDesc = workTypeDesc;
	}

	public String getCompletionNo() {
		return completionNo;
	}

	public void setCompletionNo(String completionNo) {
		this.completionNo = completionNo;
	}

	public WorkDefinationAssetInfoDto getAssetInfoDto() {
		return assetInfoDto;
	}

	public void setAssetInfoDto(WorkDefinationAssetInfoDto assetInfoDto) {
		this.assetInfoDto = assetInfoDto;
	}

	public String getContDate() {
		return contDate;
	}

	public void setContDate(String contDate) {
		this.contDate = contDate;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getProjDeptId() {
		return projDeptId;
	}

	public void setProjDeptId(Long projDeptId) {
		this.projDeptId = projDeptId;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public BigDecimal getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(BigDecimal totAmount) {
		this.totAmount = totAmount;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(Long workCategory) {
		this.workCategory = workCategory;
	}

	public String getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getOrgState() {
		return orgState;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}
	
}
