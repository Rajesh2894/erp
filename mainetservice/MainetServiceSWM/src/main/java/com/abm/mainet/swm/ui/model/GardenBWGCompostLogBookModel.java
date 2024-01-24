package com.abm.mainet.swm.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.VehicleMasterDTO;

@Component
@Scope("session")
public class GardenBWGCompostLogBookModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7800363384614027415L;

    private VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

}
