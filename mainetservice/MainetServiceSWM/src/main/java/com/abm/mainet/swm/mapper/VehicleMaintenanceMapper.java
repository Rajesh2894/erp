package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleMaintenance;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;

@Component
public class VehicleMaintenanceMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleMaintenanceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleMaintenance mapVehicleMaintenanceDTOToVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO) {

        if (vehicleMaintenanceDTO == null) {
            return null;
        }

        return map(vehicleMaintenanceDTO, VehicleMaintenance.class);

    }

    public VehicleMaintenanceDTO mapVehicleMaintenanceToVehicleMaintenanceDTO(VehicleMaintenance vehicleMaintenance) {

        if (vehicleMaintenance == null) {
            return null;
        }

        return map(vehicleMaintenance, VehicleMaintenanceDTO.class);

    }

    public List<VehicleMaintenanceDTO> mapVehicleMaintenanceListToVehicleMaintenanceDTOList(
            List<VehicleMaintenance> vehicleMaintenanceList) {

        if (vehicleMaintenanceList == null) {
            return null;
        }

        return map(vehicleMaintenanceList, VehicleMaintenanceDTO.class);

    }

    public List<VehicleMaintenance> mapVehicleMaintenanceDTOListToVehicleMaintenanceList(
            List<VehicleMaintenanceDTO> vehicleMaintenanceDTOList) {

        if (vehicleMaintenanceDTOList == null) {
            return null;
        }

        return map(vehicleMaintenanceDTOList, VehicleMaintenance.class);

    }

}
