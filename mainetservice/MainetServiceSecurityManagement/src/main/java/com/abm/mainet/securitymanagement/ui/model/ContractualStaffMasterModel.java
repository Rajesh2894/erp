package com.abm.mainet.securitymanagement.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ContractualStaffMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = 2757800964345224412L;

	private ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();

	private String saveMode;
	
	private List<Object[]> employees;

	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;

	public ContractualStaffMasterDTO getDto() {
		return dto;
	}

	public void setDto(ContractualStaffMasterDTO dto) {
		this.dto = dto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	@Override
	public boolean saveForm() {

		validateBean(dto, ContractualStaffMasterValidator.class);
		if (hasValidationErrors())
			return false;

		Long orgId =UserSession.getCurrent().getOrganisation().getOrgid();
		//dto.setOrgId(orgId);

		Employee employee = UserSession.getCurrent().getEmployee();
		if (getSaveMode().equals(MainetConstants.Legal.SaveMode.EDIT)) {
			dto.setUpdatedBy(employee.getEmpId());
			dto.setUpdatedDate(new Date());
			dto.setLgIpMacUpd(employee.getEmppiservername());
			
			contractualStaffMasterService.saveOrUpdate(dto);

			setSuccessMessage(ApplicationSession.getInstance().getMessage("ContractualStaffMasterDTO.edit.message"));
		} else {
			
			dto.setOrgid(orgId);
			dto.setCreatedBy(employee.getEmpId());
			dto.setCreatedDate(new Date());
			dto.setLgIpMac(employee.getEmppiservername());
			//RM-38980
			dto.setStatus("A");
			//RM-38980
			contractualStaffMasterService.saveOrUpdate(dto);

			setSuccessMessage(ApplicationSession.getInstance().getMessage("ContractualStaffMasterDTO.form.save"
					+ "", new Object[] { dto.getContStaffIdNo() }));
		}

		return true;
	}

	public List<Object[]> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Object[]> employees) {
		this.employees = employees;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
