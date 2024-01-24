package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.service.IInsuranceDetailService;


@Component
@Scope("session")
public class InsuranceDetailModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1017639002072624942L;
	
	private InsuranceDetailsDTO insuranceDetailsDto;
	
	
	@Autowired
	private IInsuranceDetailService insuranceDetailService;
	
	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();
	 List<TbAcVendormaster> vendorList;
	private String saveMode;
	private Long DeptId;
	
	
	private String departments;

	
	

	@Override
	public boolean saveForm() {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		if (insuranceDetailsDto.getInsuranceDetId() == null) {
			insuranceDetailsDto.setOrgid(orgId);
			insuranceDetailsDto.setCreatedBy(createdBy);
			insuranceDetailsDto.setCreatedDate(createdDate);
			insuranceDetailsDto.setLgIpMac(lgIpMac);
			insuranceDetailService.save(insuranceDetailsDto);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
		} else {
			insuranceDetailsDto.setUpdatedBy(createdBy);
			insuranceDetailsDto.setUpdatedDate(createdDate);
			insuranceDetailsDto.setLgIpMacUpd(lgIpMac);
			insuranceDetailService.save(insuranceDetailsDto);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
			
		}
		return true;
	}
	
	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}


	public InsuranceDetailsDTO getInsuranceDetailsDto() {
		return insuranceDetailsDto;
	}

	public void setInsuranceDetailsDto(InsuranceDetailsDTO insuranceDetailsDto) {
		this.insuranceDetailsDto = insuranceDetailsDto;
	}

	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public IInsuranceDetailService getInsuranceDetailService() {
		return insuranceDetailService;
	}

	public void setInsuranceDetailService(IInsuranceDetailService insuranceDetailService) {
		this.insuranceDetailService = insuranceDetailService;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}
	
}




