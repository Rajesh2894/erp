package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceDetails;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;

@Component
public class VehicleMaintenanceMap extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleMaintenanceMap() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleMaintenanceDetails mapVehicleMaintenanceDTOToVehicleMaintenance(VehicleMaintenanceDTO vehicleMaintenanceDTO) {

        if (vehicleMaintenanceDTO == null) {
            return null;
        }

        return map(vehicleMaintenanceDTO, VehicleMaintenanceDetails.class);

    }

    public VehicleMaintenanceDTO mapVehicleMaintenanceToVehicleMaintenanceDTO(VehicleMaintenanceDetails vehicleMaintenance) {

        if (vehicleMaintenance == null) {
            return null;
        }

        return map(vehicleMaintenance, VehicleMaintenanceDTO.class);

    }

    public List<VehicleMaintenanceDTO> mapVehicleMaintenanceListToVehicleMaintenanceDTOList(
            List<VehicleMaintenanceDetails> vehicleMaintenanceList) {

        if (vehicleMaintenanceList == null) {
            return null;
        }

        return map(vehicleMaintenanceList, VehicleMaintenanceDTO.class);

    }

    public List<VehicleMaintenanceDetails> mapVehicleMaintenanceDTOListToVehicleMaintenanceList(
            List<VehicleMaintenanceDTO> vehicleMaintenanceDTOList) {

        if (vehicleMaintenanceDTOList == null) {
            return null;
        }

        return map(vehicleMaintenanceDTOList, VehicleMaintenanceDetails.class);

    }

}
