package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.domain.WorkDelayReasonHistEntity;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDelayReasonDto;
import com.abm.mainet.workManagement.service.IWorkDelayReasonService;

@Component
@Scope("session")
public class WorkDelayReasonModel extends AbstractFormModel {

	/**
	 * 
	 */
	@Autowired
	private IWorkDelayReasonService delayReasonService;

	@Resource
	private AuditService auditService;

	private static Logger LOGGER = Logger.getLogger(WorkDelayReasonModel.class);

	private static final long serialVersionUID = -8583137368040115103L;

	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
	
	private List<WorkDefinitionDto>  workDefinitionDtoList = new ArrayList<>();

	private WorkDelayReasonDto delayReasonDto;

	private List<Employee> employees;

	private String saveMode;

	private List<WorkDelayReasonDto> delayReasonDtos;

	public List<WorkDelayReasonDto> getDelayReasonDtos() {
		return delayReasonDtos;
	}

	public void setDelayReasonDtos(List<WorkDelayReasonDto> delayReasonDtos) {
		this.delayReasonDtos = delayReasonDtos;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public WorkDelayReasonDto getDelayReasonDto() {
		return delayReasonDto;
	}

	public void setDelayReasonDto(WorkDelayReasonDto delayReasonDto) {
		this.delayReasonDto = delayReasonDto;
	}

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public List<WorkDefinitionDto> getWorkDefinitionDtoList() {
		return workDefinitionDtoList;
	}

	public void setWorkDefinitionDtoList(List<WorkDefinitionDto> workDefinitionDtoList) {
		this.workDefinitionDtoList = workDefinitionDtoList;
	}

	@Override
	public boolean saveForm() {

		Long delResId = this.getDelayReasonDto().getDelResId();

		if (delResId == null) {
			delayReasonDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			delayReasonDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			delayReasonDto.setCreationDate(new Date());
			delayReasonDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			delayReasonDto.setStatus(MainetConstants.STATUS.ACTIVE);
			delayReasonDto.setFlagForHist(MainetConstants.FlagC);
			delayReasonService.saveWorkDelayReason(delayReasonDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Work Delay Reason Added successfully"));

		} else {
			delayReasonDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

			delayReasonDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			delayReasonDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			delayReasonDto.setUpdatedDate(new Date());
			delayReasonDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			delayReasonDto.setFlagForHist(MainetConstants.FlagE);
			delayReasonService.saveWorkDelayReason(delayReasonDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Work Delay Reason Updated successfully"));

		}

		return true;
	}

}
