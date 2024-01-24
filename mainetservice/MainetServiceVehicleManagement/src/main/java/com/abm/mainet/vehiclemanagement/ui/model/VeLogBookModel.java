package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.repository.VeLogBookRepository;
import com.abm.mainet.vehiclemanagement.service.ILogBookService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VeLogBookModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	/*
	 * @Autowired private DepartmentService departmentservice;
	 */

	//private static final Logger LOGGER = Logger.getLogger(VehicleLogBookModel.class);
    
	@Autowired
	private ILogBookService logBookService;
	
	@Autowired
	private VeLogBookRepository logBookRepository;
	
	@Resource
	IFileUploadService fileUpload;


	private VehicleLogBookDTO vehicleLogBookDTO = new VehicleLogBookDTO();
	
	private  GenVehicleMasterDTO genVehicleMasterDTO=new GenVehicleMasterDTO();
	
	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();

	private String departments;

	private String saveMode;
	
	private String driverEmpID;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
    private List<AttachDocs> attachDocsList = new ArrayList<>();

	@Override
	public boolean saveForm() {


		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		vehicleLogBookDTO.setOrgId(orgId);
		if(logBookService.veLogBookDupCheck(vehicleLogBookDTO) == false) {
			this.addValidationError(ApplicationSession.getInstance().getMessage("vehicle.logbook.duplicate.exists"));
		    return false;
		}
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
		FileUploadDTO requestDTO = new FileUploadDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.LOG_BOOK+MainetConstants.WINDOWS_SLASH+vehicleLogBookDTO.getVeID()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(Constants.SHORT_CODE);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
       
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
	
	
	public VeLogBookRepository getLogBookRepository() {
		return logBookRepository;
	}

	public void setLogBookRepository(VeLogBookRepository logBookRepository) {
		this.logBookRepository = logBookRepository;
	}

	
	
	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	
	
}

