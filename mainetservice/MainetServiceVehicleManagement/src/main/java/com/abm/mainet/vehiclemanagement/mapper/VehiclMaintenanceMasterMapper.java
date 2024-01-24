package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMast;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceMasterDTO;

@Component
public class VehiclMaintenanceMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehiclMaintenanceMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleMaintenanceMast mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(
            VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {

        if (vehicleMaintenanceMasterDTO == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterDTO, VehicleMaintenanceMast.class);

    }

    public VehicleMaintenanceMasterDTO mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(
    		VehicleMaintenanceMast vehicleMaintenanceMaster) {

        if (vehicleMaintenanceMaster == null) {
            return null;
        }

        return map(vehicleMaintenanceMaster, VehicleMaintenanceMasterDTO.class);

    }

    public List<VehicleMaintenanceMasterDTO> mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
            List<VehicleMaintenanceMast> vehicleMaintenanceMasterList) {

        if (vehicleMaintenanceMasterList == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterList, VehicleMaintenanceMasterDTO.class);

    }

    public List<VehicleMaintenanceMast> mapVehicleMaintenanceMasterDTOListToVehicleMaintenanceMasterList(
            List<VehicleMaintenanceMasterDTO> vehicleMaintenanceMasterDTOList) {

        if (vehicleMaintenanceMasterDTOList == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterDTOList, VehicleMaintenanceMast.class);

    }

}
