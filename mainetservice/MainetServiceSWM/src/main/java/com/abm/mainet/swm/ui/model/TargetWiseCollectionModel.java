package com.abm.mainet.swm.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class TargetWiseCollectionModel extends AbstractFormModel {
    private static final long serialVersionUID = -7384424797094958340L;

    private VehicleScheduleDTO vehicleScheduledto = new VehicleScheduleDTO();

    private List<VehicleMasterDTO> vehicleMasterList;

    private SanitationStaffTargetDTO vehicleTargetDTO = new SanitationStaffTargetDTO();

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public SanitationStaffTargetDTO getVehicleTargetDTO() {
        return vehicleTargetDTO;
    }

    public void setVehicleTargetDTO(SanitationStaffTargetDTO vehicleTargetDTO) {
        this.vehicleTargetDTO = vehicleTargetDTO;
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
