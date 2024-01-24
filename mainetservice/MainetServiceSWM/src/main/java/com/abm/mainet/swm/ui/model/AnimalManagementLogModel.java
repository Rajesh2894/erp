package com.abm.mainet.swm.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

@Component
@Scope("session")
public class AnimalManagementLogModel extends AbstractFormModel {

    private static final long serialVersionUID = 1774596289524198958L;

    private VehicleScheduleDTO vehicleScheduleDTO = new VehicleScheduleDTO();

    public VehicleScheduleDTO getVehicleScheduleDTO() {
        return vehicleScheduleDTO;
    }

    public void setVehicleScheduleDTO(VehicleScheduleDTO vehicleScheduleDTO) {
        this.vehicleScheduleDTO = vehicleScheduleDTO;
    }

}
