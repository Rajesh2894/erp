package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class WorkCompletionRegisterModel extends AbstractFormModel{

	private static final long serialVersionUID = 586587165388660667L;
	
	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
	
	private List<WorkDefinitionDto> workDefDtos = new ArrayList<>();

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public List<WorkDefinitionDto> getWorkDefDtos() {
		return workDefDtos;
	}

	public void setWorkDefDtos(List<WorkDefinitionDto> workDefDtos) {
		this.workDefDtos = workDefDtos;
	}

}
