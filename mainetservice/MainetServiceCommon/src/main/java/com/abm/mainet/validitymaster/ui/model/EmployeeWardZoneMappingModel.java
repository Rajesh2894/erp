package com.abm.mainet.validitymaster.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDetailDto;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDto;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;

/**
 * @author cherupelli.srikanth
 * @since 29 nov 2021
 */

@Component
@Scope("session")
public class EmployeeWardZoneMappingModel extends AbstractFormModel{
	
	@Autowired
	private IEmployeeWardZoneMappingService employeeWardZoneMappingService;
	
	private static final long serialVersionUID = 1L;

	private List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();
	
	private EmployeeWardZoneMappingDto employeeWardZoneMapDto = new EmployeeWardZoneMappingDto();
	
	private List<EmployeeWardZoneMappingDto> empWardZoneMstList = new ArrayList<EmployeeWardZoneMappingDto>();
	
	private String saveMode;
	
	private String  formFlag;
	
	
	@Override
    public boolean saveForm() {
		boolean successFlag = false;
		EmployeeWardZoneMappingDto employeeWardZoneMap = employeeWardZoneMapDto;
		if(employeeWardZoneMap.getEmpLocId() == null || employeeWardZoneMap.getEmpLocId() <= 0) {
			 employeeWardZoneMap.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			 employeeWardZoneMap.setLgIpMac(getClientIpAddress());
			 employeeWardZoneMap.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			 employeeWardZoneMap.setCreatedDate(new Date());
			 
			 employeeWardZoneMap.getWardZoneDetalList().forEach(wardZone ->{
				 wardZone.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				 wardZone.setLgIpMac(getClientIpAddress());
				 wardZone.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				 wardZone.setEmpLocId(employeeWardZoneMap.getEmpId());
				 wardZone.setCreatedDate(new Date());
			 });
		 }
		AtomicBoolean existFlag = new AtomicBoolean(false);
		 employeeWardZoneMap.getWardZoneDetalList().forEach(wardZoneDetail ->{
			empWardZoneMstList.forEach(mast -> {
				List<EmployeeWardZoneMappingDetailDto> existList = mast.getWardZoneDetalList().stream()
						.filter(detail -> detail.getEmpLocId().equals(wardZoneDetail.getEmpLocId())
								&& detail.getWard1().equals(wardZoneDetail.getWard1())
								&& ((detail.getWard2() == null && wardZoneDetail.getWard2() == null) || (detail.getWard2().equals(wardZoneDetail.getWard2())))
								&& ((detail.getWard3() == null && wardZoneDetail.getWard3() == null) || (detail.getWard3().equals(wardZoneDetail.getWard3()))))
						.collect(Collectors.toList());
				if(CollectionUtils.isNotEmpty(existList)) {
					existFlag.getAndSet(true);
				}
			});
		 });
		 
		 if(StringUtils.equals(saveMode, "A") && existFlag.get()) {
			 addValidationError("This combination exists");
		 }
		 
		 if (hasValidationErrors()) {
	            return successFlag;
	        }
		 
		employeeWardZoneMappingService.saveEmployeeWardZoneapping(employeeWardZoneMap);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("Mapping Done"));
		successFlag = true;
		return successFlag;
	}

	public List<EmployeeBean> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeBean> employeeList) {
		this.employeeList = employeeList;
	}

	public EmployeeWardZoneMappingDto getEmployeeWardZoneMapDto() {
		return employeeWardZoneMapDto;
	}

	public void setEmployeeWardZoneMapDto(EmployeeWardZoneMappingDto employeeWardZoneMapDto) {
		this.employeeWardZoneMapDto = employeeWardZoneMapDto;
	}

	public List<EmployeeWardZoneMappingDto> getEmpWardZoneMstList() {
		return empWardZoneMstList;
	}

	public void setEmpWardZoneMstList(List<EmployeeWardZoneMappingDto> empWardZoneMstList) {
		this.empWardZoneMstList = empWardZoneMstList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getFormFlag() {
		return formFlag;
	}

	public void setFormFlag(String formFlag) {
		this.formFlag = formFlag;
	}
	
}
