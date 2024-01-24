package com.abm.mainet.swm.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VehicleScheduleReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 5320179910017869480L;

    VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    List<VehicleMasterDTO> vehicleMasterList;

    VehicleScheduleDTO vehicleScheduledto;

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

}
