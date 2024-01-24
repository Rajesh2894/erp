package com.abm.mainet.swm.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.PumpMasterDTO;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class ExpenditureIncurredOnTransportationModel extends AbstractFormModel {

    private static final long serialVersionUID = -1666863305208518739L;

    VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    VehicleMaintenanceDTO vehicleMaintenanceDTO = new VehicleMaintenanceDTO();

    List<VehicleMasterDTO> vehicleMasterList;

    List<PumpMasterDTO> pumpMasterList;

    VehicleScheduleDTO vehicleScheduledto;

    VehicleFuellingDTO vehicleFuellingdto;

    private String isPSCLEnv;    
      
    public VehicleFuellingDTO getVehicleFuellingdto() {
        return vehicleFuellingdto;
    }

    public void setVehicleFuellingdto(VehicleFuellingDTO vehicleFuellingdto) {
        this.vehicleFuellingdto = vehicleFuellingdto;
    }

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public VehicleScheduleDTO getVehicleScheduledto() {
        return vehicleScheduledto;
    }

    public void setVehicleScheduledto(VehicleScheduleDTO vehicleScheduledto) {
        this.vehicleScheduledto = vehicleScheduledto;
    }

    public VehicleMaintenanceDTO getVehicleMaintenanceDTO() {
        return vehicleMaintenanceDTO;
    }

    public void setVehicleMaintenanceDTO(VehicleMaintenanceDTO vehicleMaintenanceDTO) {
        this.vehicleMaintenanceDTO = vehicleMaintenanceDTO;
    }

    public List<PumpMasterDTO> getPumpMasterList() {
        return pumpMasterList;
    }

    public void setPumpMasterList(List<PumpMasterDTO> pumpMasterList) {
        this.pumpMasterList = pumpMasterList;
    }

	public String getIsPSCLEnv() {
		return isPSCLEnv;
	}

	public void setIsPSCLEnv(String isPSCLEnv) {
		this.isPSCLEnv = isPSCLEnv;
	}

}
