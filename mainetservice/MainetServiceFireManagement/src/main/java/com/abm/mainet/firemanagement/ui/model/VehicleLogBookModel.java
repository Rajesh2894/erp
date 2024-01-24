package com.abm.mainet.firemanagement.ui.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.firemanagement.repository.LogBookRepository;
import com.abm.mainet.firemanagement.service.ILogBookService;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VehicleLogBookModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	/*
	 * @Autowired private DepartmentService departmentservice;
	 */

	//private static final Logger LOGGER = Logger.getLogger(VehicleLogBookModel.class);
    
	@Autowired
	private ILogBookService logBookService;
	
	@Autowired
	private LogBookRepository logBookRepository;


	private VehicleLogBookDTO vehicleLogBookDTO = new VehicleLogBookDTO();
	
	private  GenVehicleMasterDTO genVehicleMasterDTO=new GenVehicleMasterDTO();
	
	private String departments;

	private String saveMode;
	
	private String driverEmpID;

	@Override
	public boolean saveForm() {


		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		if (vehicleLogBookDTO.getVeID() == null) {

			vehicleLogBookDTO.setOrgId(orgId);
			vehicleLogBookDTO.setCreatedBy(createdBy);
			vehicleLogBookDTO.setCreatedDate(createdDate);
			vehicleLogBookDTO.setLgIpMac(lgIpMac);
		    //vehicleLogBookDTO.setDriverName(UserSession.getCurrent().getOrganisation().getOrgid());
			//vehicleLogBookDTO.setDriverDesc(genVehicleMasterDTO.getDriverName());
		//	vehicleLogBookDTO.setDriverId(driverId);
			
			logBookService.saveVehicleDetails(vehicleLogBookDTO);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
		} else {
			vehicleLogBookDTO.setUpdatedBy(createdBy);
			vehicleLogBookDTO.setUpdatedDate(createdDate);
			vehicleLogBookDTO.setLgIpMacUpd(lgIpMac);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
			logBookService.saveVehicleDetails(vehicleLogBookDTO);
		}
		return true;
	}
	
	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public ILogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(ILogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public VehicleLogBookDTO getVehicleLogBookDTO() {
		return vehicleLogBookDTO;
	}

	public void setVehicleLogBookDTO(VehicleLogBookDTO vehicleLogBookDTO) {
		this.vehicleLogBookDTO = vehicleLogBookDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GenVehicleMasterDTO getGenVehicleMasterDTO() {
		return genVehicleMasterDTO;
	}

	public void setGenVehicleMasterDTO(GenVehicleMasterDTO genVehicleMasterDTO) {
		this.genVehicleMasterDTO = genVehicleMasterDTO;
	}

	public String getDriverEmpID() {
		return driverEmpID;
	}

	public void setDriverEmpID(String driverEmpID) {
		this.driverEmpID = driverEmpID;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}
	
	
	public LogBookRepository getLogBookRepository() {
		return logBookRepository;
	}

	public void setLogBookRepository(LogBookRepository logBookRepository) {
		this.logBookRepository = logBookRepository;
	}

	
	
	
	
	
}
