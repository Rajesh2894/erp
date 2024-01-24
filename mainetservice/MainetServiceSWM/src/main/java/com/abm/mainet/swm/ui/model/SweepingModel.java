package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

@Component
@Scope("session")
public class SweepingModel extends AbstractFormModel {

    private static final long serialVersionUID = 9147961305281938322L;

    private VehicleScheduleDTO vehicleScheduleDTO = new VehicleScheduleDTO();

    private List<BeatMasterDTO> routeMasterList = new ArrayList<>();

    public List<BeatMasterDTO> getRouteMasterList() {
        return routeMasterList;
    }

    public void setRouteMasterList(List<BeatMasterDTO> routeMasterList) {
        this.routeMasterList = routeMasterList;
    }

    public VehicleScheduleDTO getVehicleScheduleDTO() {
        return vehicleScheduleDTO;
    }

    public void setVehicleScheduleDTO(VehicleScheduleDTO vehicleScheduleDTO) {
        this.vehicleScheduleDTO = vehicleScheduleDTO;
    }

}
