package com.abm.mainet.cms.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.dto.DepartmentUserLogReportDTO;
import com.abm.mainet.cms.service.IDepartmentUserLogReportService;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class DepartmentUserLogReportModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String section;
	private Date fromDate;
	private Date toDate;
	private Long organisation;
	
	private List<DepartmentUserLogReportDTO> departmentUserLogReportDTO=new ArrayList<>();
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public List<DepartmentUserLogReportDTO> getDepartmentUserLogReportDTO() {
		return departmentUserLogReportDTO;
	}
	public void setDepartmentUserLogReportDTO(List<DepartmentUserLogReportDTO> departmentUserLogReportDTO) {
		this.departmentUserLogReportDTO = departmentUserLogReportDTO;
	}
	public Long getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Long organisation) {
		this.organisation = organisation;
	}

}
