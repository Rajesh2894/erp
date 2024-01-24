package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceMasterService;
import com.abm.mainet.vehiclemanagement.ui.validator.VehiclMaintenanceMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehiclMaintenanceMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1340684254502225688L;

    @Autowired
    private IVehicleMaintenanceMasterService vehicleMaintenanceMasterService;

    private VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO = new VehicleMaintenanceMasterDTO();

    private List<VehicleMaintenanceMasterDTO> vehicleMaintenanceList;

    private String saveMode;
    
    List<GenVehicleMasterDTO> vehicles;
    
    private List<Object[]> vehicleObjectList;


    @Override
    public boolean saveForm() {
        boolean status = false;
    	Organisation organisation = UserSession.getCurrent().getOrganisation();
    	boolean envIsPSCL = Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL);
    	if(!envIsPSCL)
    		validateBean(vehicleMaintenanceMasterDTO, VehiclMaintenanceMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
    		Long empID = UserSession.getCurrent().getEmployee().getEmpId();
    		Date sysDate = new Date();
    		String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();
            if (vehicleMaintenanceMasterDTO.getVeMeId() == null) {
                vehicleMaintenanceMasterDTO.setOrgid(organisation.getOrgid());
                vehicleMaintenanceMasterDTO.setCreatedBy(empID);
                vehicleMaintenanceMasterDTO.setCreatedDate(sysDate);
                vehicleMaintenanceMasterDTO.setLgIpMac(macAddr);
                vehicleMaintenanceMasterDTO.setVeMeActive(MainetConstants.FlagY);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.add.success"));
                if(envIsPSCL) {
                	vehicleMaintenanceMasterService.savePSCLVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
                	status = true;
                }
            } else {
                vehicleMaintenanceMasterDTO.setUpdatedBy(empID);
                vehicleMaintenanceMasterDTO.setUpdatedDate(sysDate);
                vehicleMaintenanceMasterDTO.setLgIpMacUpd(macAddr);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.edit.success"));
                if(envIsPSCL) {
                	vehicleMaintenanceMasterService.saveVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
                	status = true;
                }
            }
            if(!envIsPSCL) {
    			status = vehicleMaintenanceMasterService.validateVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
    			if (status) {
    				vehicleMaintenanceMasterService.saveVehicleMaintenanceMaster(vehicleMaintenanceMasterDTO);
    				status = true;
    			} else {
    				addValidationError(ApplicationSession.getInstance().getMessage("VehicleMaintenanceMasterDTO.exists"));
    				status = false;
    			}            	
            }
        }
        return status;
    }

    public List<VehicleMaintenanceMasterDTO> getVehicleMaintenanceList() {
        return vehicleMaintenanceList;
    }

    public void setVehicleMaintenanceList(List<VehicleMaintenanceMasterDTO> vehicleMaintenanceList) {
        this.vehicleMaintenanceList = vehicleMaintenanceList;
    }

    public IVehicleMaintenanceMasterService getVehicleMaintenanceMasterService() {
        return vehicleMaintenanceMasterService;
    }

    public void setVehicleMaintenanceMasterService(IVehicleMaintenanceMasterService vehicleMaintenanceMasterService) {
        this.vehicleMaintenanceMasterService = vehicleMaintenanceMasterService;
    }

    public VehicleMaintenanceMasterDTO getVehicleMaintenanceMasterDTO() {
        return vehicleMaintenanceMasterDTO;
    }

    public void setVehicleMaintenanceMasterDTO(VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {
        this.vehicleMaintenanceMasterDTO = vehicleMaintenanceMasterDTO;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

	public List<GenVehicleMasterDTO> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<GenVehicleMasterDTO> vehicles) {
		this.vehicles = vehicles;
	}

	public List<Object[]> getVehicleObjectList() {
		return vehicleObjectList;
	}

	public void setVehicleObjectList(List<Object[]> vehicleObjectList) {
		this.vehicleObjectList = vehicleObjectList;
	}

}
