package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WmsProjectStatusReportDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;

@Component
@Scope("session")
public class ProjectRegisterReportModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WorkDefinitionDto workDefinitionDto;
	private SchemeMasterDTO schemeMasterDto;
	private WorkEstimateMasterDto workEstimateMasterDto;
	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
	private List<SchemeMasterDTO> schemeMasterList;
	private List<WmsProjectStatusReportDetDto> projectStatusReportDetDto;

	public WorkDefinitionDto getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(WorkDefinitionDto workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public SchemeMasterDTO getSchemeMasterDto() {
		return schemeMasterDto;
	}

	public void setSchemeMasterDto(SchemeMasterDTO schemeMasterDto) {
		this.schemeMasterDto = schemeMasterDto;
	}

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

	public List<SchemeMasterDTO> getSchemeMasterList() {
		return schemeMasterList;
	}

	public void setSchemeMasterList(List<SchemeMasterDTO> schemeMasterList) {
		this.schemeMasterList = schemeMasterList;
	}

	public List<WmsProjectStatusReportDetDto> getProjectStatusReportDetDto() {
		return projectStatusReportDetDto;
	}

	public void setProjectStatusReportDetDto(List<WmsProjectStatusReportDetDto> projectStatusReportDetDto) {
		this.projectStatusReportDetDto = projectStatusReportDetDto;
	}

}