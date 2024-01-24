package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.BeatMasterDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

@Component
@Scope("session")
public class VehicleDeployementPlanReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 224219663844335591L;

    private VehicleScheduleDTO vehicleScheduleDTO = new VehicleScheduleDTO();

    private List<BeatMasterDTO> beatMasterList = new ArrayList<>();
    
    private List<VehicleMasterDTO> vehicleMasterList = new ArrayList<>();
    Object[] empUIdList;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public VehicleScheduleDTO getVehicleScheduleDTO() {
        return vehicleScheduleDTO;
    }

    public void setVehicleScheduleDTO(VehicleScheduleDTO vehicleScheduleDTO) {
        this.vehicleScheduleDTO = vehicleScheduleDTO;
    }

    public List<BeatMasterDTO> getBeatMasterList() {
        return beatMasterList;
    }

    public void setBeatMasterList(List<BeatMasterDTO> beatMasterList) {
        this.beatMasterList = beatMasterList;
    }

    public List<VehicleMasterDTO> getVehicleMasterList() {
        return vehicleMasterList;
    }

    public void setVehicleMasterList(List<VehicleMasterDTO> vehicleMasterList) {
        this.vehicleMasterList = vehicleMasterList;
    }

    public Object[] getEmpUIdList() {
        return empUIdList;
    }

    public void setEmpUIdList(Object[] empUIdList) {
        this.empUIdList = empUIdList;
    }

    
}
