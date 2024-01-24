package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WmsProjectMasterDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long projId;

	private Long projType;

	private String projCode;

	private Long dpDeptId;

	private String projNameEng;

	private String projNameReg;

	private String projDescription;

	private Date projStartDate;

	private Date projEndDate;

	private Long schId;

	private String schemeName;

	private BigDecimal projEstimateCost;

	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String departmentName;

	private String departmentCode;

	private String projTypeName;

	private String projActive;

	private BigDecimal projActualCost;

	private Long projRisk;

	private Long projComplexity;

	private String workName;

	private Long workId;

	private String startDate;

	private String endDate;

	private Long mileId;

	List<WorkDefinitionDto> workDefinitionDto = new ArrayList<>();

	private String mileStoneDesc;

	private String mileStoneType;

	private List<Long> workIdList = new ArrayList<>();

	private String startDateDesc;

	private String endDateDesc;

	private String rsoNumber;

	private Date rsoDate;

	private String orderDateDesc;

	private String rsoDateDesc;

	private Long projStatus;

	private Long schmSourceCode;

	private Long projPrd;

	private Long projPrdUnit;
	
	private List<WorkDefinationYearDetDto> yearDtos = new ArrayList<>();

	public Long getProjPrd() {
		return projPrd;
	}

	public void setProjPrd(Long projPrd) {
		this.projPrd = projPrd;
	}

	public Long getProjPrdUnit() {
		return projPrdUnit;
	}

	public void setProjPrdUnit(Long projPrdUnit) {
		this.projPrdUnit = projPrdUnit;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getProjType() {
		return projType;
	}

	public void setProjType(Long projType) {
		this.projType = projType;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}

	public String getProjNameEng() {
		return projNameEng;
	}

	public void setProjNameEng(String projNameEng) {
		this.projNameEng = projNameEng;
	}

	public String getProjNameReg() {
		return projNameReg;
	}

	public void setProjNameReg(String projNameReg) {
		this.projNameReg = projNameReg;
	}

	public String getProjDescription() {
		return projDescription;
	}

	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}

	public Date getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(Date projStartDate) {
		this.projStartDate = projStartDate;
	}

	public Date getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(Date projEndDate) {
		this.projEndDate = projEndDate;
	}

	public Long getSchId() {
		return schId;
	}

	public void setSchId(Long schId) {
		this.schId = schId;
	}

	public BigDecimal getProjEstimateCost() {
		return projEstimateCost;
	}

	public void setProjEstimateCost(BigDecimal projEstimateCost) {
		this.projEstimateCost = projEstimateCost;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjTypeName() {
		return projTypeName;
	}

	public void setProjTypeName(String projTypeName) {
		this.projTypeName = projTypeName;
	}

	public String getProjActive() {
		return projActive;
	}

	public void setProjActive(String projActive) {
		this.projActive = projActive;
	}

	public BigDecimal getProjActualCost() {
		return projActualCost;
	}

	public void setProjActualCost(BigDecimal projActualCost) {
		this.projActualCost = projActualCost;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Long getProjRisk() {
		return projRisk;
	}

	public void setProjRisk(Long projRisk) {
		this.projRisk = projRisk;
	}

	public Long getProjComplexity() {
		return projComplexity;
	}

	public void setProjComplexity(Long projComplexity) {
		this.projComplexity = projComplexity;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getMileId() {
		return mileId;
	}

	public void setMileId(Long mileId) {
		this.mileId = mileId;
	}

	public String getMileStoneDesc() {
		return mileStoneDesc;
	}

	public void setMileStoneDesc(String mileStoneDesc) {
		this.mileStoneDesc = mileStoneDesc;
	}

	public String getMileStoneType() {
		return mileStoneType;
	}

	public void setMileStoneType(String mileStoneType) {
		this.mileStoneType = mileStoneType;
	}

	public List<Long> getWorkIdList() {
		return workIdList;
	}

	public void setWorkIdList(List<Long> workIdList) {
		this.workIdList = workIdList;
	}

	public String getStartDateDesc() {
		return startDateDesc;
	}

	public void setStartDateDesc(String startDateDesc) {
		this.startDateDesc = startDateDesc;
	}

	public String getEndDateDesc() {
		return endDateDesc;
	}

	public void setEndDateDesc(String endDateDesc) {
		this.endDateDesc = endDateDesc;
	}

	public List<WorkDefinitionDto> getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void addDefinitionDto(WorkDefinitionDto definitionDto) {
		if (null != definitionDto) {
			workDefinitionDto.add(definitionDto);
		}

	}

	/**
	 * Please do not remove hashcode() and equals() method, which is used to
	 * milestone to check duplication
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projId == null) ? 0 : projId.hashCode());
		result = prime * result + ((workName == null) ? 0 : workName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WmsProjectMasterDto))
			return false;
		WmsProjectMasterDto other = (WmsProjectMasterDto) obj;
		if (projId == null) {
			if (other.projId != null)
				return false;
		} else if (!projId.equals(other.projId))
			return false;
		if (workName == null) {
			if (other.workName != null)
				return false;
		} else if (!workName.equals(other.workName))
			return false;
		return true;
	}

	public String getRsoNumber() {
		return rsoNumber;
	}

	public void setRsoNumber(String rsoNumber) {
		this.rsoNumber = rsoNumber;
	}

	public Date getRsoDate() {
		return rsoDate;
	}

	public void setRsoDate(Date rsoDate) {
		this.rsoDate = rsoDate;
	}

	public String getOrderDateDesc() {
		return orderDateDesc;
	}

	public void setOrderDateDesc(String orderDateDesc) {
		this.orderDateDesc = orderDateDesc;
	}

	public String getRsoDateDesc() {
		return rsoDateDesc;
	}

	public void setRsoDateDesc(String rsoDateDesc) {
		this.rsoDateDesc = rsoDateDesc;
	}

	public Long getProjStatus() {
		return projStatus;
	}

	public void setProjStatus(Long projStatus) {
		this.projStatus = projStatus;
	}

	public Long getSchmSourceCode() {
		return schmSourceCode;
	}

	public void setSchmSourceCode(Long schmSourceCode) {
		this.schmSourceCode = schmSourceCode;
	}

	public List<WorkDefinationYearDetDto> getYearDtos() {
		return yearDtos;
	}

	public void setYearDtos(List<WorkDefinationYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
	}

}
