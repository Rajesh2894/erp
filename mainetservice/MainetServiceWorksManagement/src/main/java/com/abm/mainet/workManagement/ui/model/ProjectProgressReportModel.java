/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.ProjectProgressDto;

/**
 * @author Saiprasad.Vengurekar
 *
 */
@Component
@Scope("session")
public class ProjectProgressReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 8423679578270787328L;
    
	private List<ProjectProgressDto> projectProgressDto = new ArrayList<>();
	private String fromDate;

	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<ProjectProgressDto> getProjectProgressDto() {
		return projectProgressDto;
	}

	public void setProjectProgressDto(List<ProjectProgressDto> projectProgressDto) {
		this.projectProgressDto = projectProgressDto;
	}

}
