package com.abm.mainet.swm.ui.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.VehicleLogBookMainPageDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

@Component
@Scope("session")
public class VehicleLogBookMainPageModel extends AbstractFormModel {
    private static final long serialVersionUID = -7884146342898898135L;
    private VehicleScheduleDTO vehicleScheduleDTO = new VehicleScheduleDTO();

    private VehicleLogBookMainPageDTO vehicleLogBookMainPageList = new VehicleLogBookMainPageDTO();

    private List<VehicleMasterDTO> vehicleMasterList;

    public VehicleLogBookMainPageDTO getVehicleLogBookMainPageList() {
        return vehicleLogBookMainPageList;
    }

    public void setVehicleLogBookMainPageList(VehicleLogBookMainPageDTO vehicleLogBookMainPageList) {
        this.vehicleLogBookMainPageList = vehicleLogBookMainPageList;
    }

    public VehicleScheduleDTO getVehicleScheduleDTO() {
        return vehicleScheduleDTO;
    }

    public void setVehicleScheduleDTO(VehicleScheduleDTO vehicleScheduleDTO) {
        this.vehicleScheduleDTO = vehicleScheduleDTO;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

}
