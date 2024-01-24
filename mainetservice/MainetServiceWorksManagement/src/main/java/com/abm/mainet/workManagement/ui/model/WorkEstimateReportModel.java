package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.DepartmentDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;

/**
 * @author vishwajeet.kumar
 * @since 1 March 2018
 */
@Component
@Scope("session")
public class WorkEstimateReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private WorkEstimateMasterDto workEstimateMasterDto;
	private WmsProjectMasterDto projectMasterDto;

	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();

	private List<WorkDefinitionDto> workDefinationList = new ArrayList<>();

	private WorkDefinitionDto definitionDto = new WorkDefinitionDto();
	private List<LookUp> reportTypeLookUp = new ArrayList<>();

	 private WorkDefinitionDto wmsDto; 
	private Long projId;

	private Long orgId;

	private String reportType;

	
	private DepartmentDTO departmentdto;
	
	private String departName;
	private String saveMode;
	private String departNameReg;

	private BigDecimal amount;
	List<LookUp> lookups = getLevelData(MainetConstants.WorksManagement.WRT);
	
	private List<TbDepartment> departmentsList; 
	
	private List<WorkEstimateMasterDto> workMasterDtosList = new ArrayList<>();

	public WorkEstimateMasterDto getWorkEstimateMasterDto() {
		return workEstimateMasterDto;
	}

	public void setWorkEstimateMasterDto(WorkEstimateMasterDto workEstimateMasterDto) {
		this.workEstimateMasterDto = workEstimateMasterDto;
	}

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public List<WorkDefinitionDto> getWorkDefinationList() {
		return workDefinationList;
	}

	public void setWorkDefinationList(List<WorkDefinitionDto> workDefinationList) {
		this.workDefinationList = workDefinationList;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<LookUp> getReportTypeLookUp() {
		return reportTypeLookUp;
	}

	public void setReportTypeLookUp(List<LookUp> reportTypeLookUp) {
		this.reportTypeLookUp = reportTypeLookUp;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<WorkEstimateMasterDto> getWorkMasterDtosList() {
		return workMasterDtosList;
	}

	public void setWorkMasterDtosList(List<WorkEstimateMasterDto> workMasterDtosList) {
		this.workMasterDtosList = workMasterDtosList;
	}

	public WmsProjectMasterDto getProjectMasterDto() {
		return projectMasterDto;
	}

	public void setProjectMasterDto(WmsProjectMasterDto projectMasterDto) {
		this.projectMasterDto = projectMasterDto;
	}

	public WorkDefinitionDto getDefinitionDto() {
		return definitionDto;
	}

	public void setDefinitionDto(WorkDefinitionDto definitionDto) {
		this.definitionDto = definitionDto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public WorkDefinitionDto getWmsDto() {
		return wmsDto;
	}

	public void setWmsDto(WorkDefinitionDto wmsDto) {
		this.wmsDto = wmsDto;
	}

	public List<LookUp> getLookups() {
		return lookups;
	}

	public void setLookups(List<LookUp> lookups) {
		this.lookups = lookups;
	}

	public DepartmentDTO getDepartmentdto() {
		return departmentdto;
	}

	public void setDepartmentdto(DepartmentDTO departmentdto) {
		this.departmentdto = departmentdto;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDepartNameReg() {
		return departNameReg;
	}

	public void setDepartNameReg(String departNameReg) {
		this.departNameReg = departNameReg;
	}



	

}
