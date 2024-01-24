package com.abm.mainet.swm.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.VehicleMasterDTO;

@Component
@Scope("session")
public class StreetAnimalManagementModel extends AbstractFormModel {
    private static final long serialVersionUID = -5082624710983774668L;

    private VehicleMasterDTO vehicleMasterDTO = new VehicleMasterDTO();

    /**
     * @return the vehicleMasterDTO
     */
    public VehicleMasterDTO getVehicleMasterDTO() {
        return vehicleMasterDTO;
    }

    /**
     * @param vehicleMasterDTO the vehicleMasterDTO to set
     */
    public void setVehicleMasterDTO(VehicleMasterDTO vehicleMasterDTO) {
        this.vehicleMasterDTO = vehicleMasterDTO;
    }

}
