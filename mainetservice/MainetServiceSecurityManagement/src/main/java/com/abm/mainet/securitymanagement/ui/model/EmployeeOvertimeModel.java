package com.abm.mainet.securitymanagement.ui.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.service.IEmployeeSchedulingService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)

public class EmployeeOvertimeModel extends AbstractFormModel{

	
	private static final long serialVersionUID = 2757800964345224412L;

	private List<TbAcVendormaster> vendorList=new ArrayList<TbAcVendormaster>();
	private List<TbLocationMas> location=new ArrayList<TbLocationMas>();
	EmployeeSchedulingDTO employeeSchedulingDTO = new EmployeeSchedulingDTO();
	List<EmployeeSchedulingDTO> employeeSchedulingDTOList = new ArrayList<>();
	List<EmployeeSchedulingDetDTO> detList = new ArrayList<EmployeeSchedulingDetDTO>();
	
	@Autowired
	IEmployeeSchedulingService employeeSchedulingService;
	
	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();

		for (int i = 0; i < detList.size(); i++) {
			detList.get(i).setOrgid(orgId);
			detList.get(i).setUpdatedBy(createdBy);
			detList.get(i).setUpdatedDate(createdDate);
			detList.get(i).setLgIpMac(lgIpMac);
			detList.get(i).setCreatedBy(createdBy);
			detList.get(i).setEmplScdlId(employeeSchedulingDTOList.get(0).getEmplScdlId());
			
		}
		employeeSchedulingDTO.setEmplDetDto(detList);
		employeeSchedulingService.saveoverTimeData(employeeSchedulingDTO);
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("Saved Data Successfully"));
		return true;
			
		}
		
	


	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public List<TbLocationMas> getLocation() {
		return location;
	}

	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}

	public EmployeeSchedulingDTO getEmployeeSchedulingDTO() {
		return employeeSchedulingDTO;
	}

	public void setEmployeeSchedulingDTO(EmployeeSchedulingDTO employeeSchedulingDTO) {
		this.employeeSchedulingDTO = employeeSchedulingDTO;
	}

	public List<EmployeeSchedulingDTO> getEmployeeSchedulingDTOList() {
		return employeeSchedulingDTOList;
	}

	public void setEmployeeSchedulingDTOList(List<EmployeeSchedulingDTO> employeeSchedulingDTOList) {
		this.employeeSchedulingDTOList = employeeSchedulingDTOList;
	}

	public List<EmployeeSchedulingDetDTO> getDetList() {
		return detList;
	}

	public void setDetList(List<EmployeeSchedulingDetDTO> detList) {
		this.detList = detList;
	}

	
}