package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleMaintenanceMaster;
import com.abm.mainet.swm.dto.VehicleMaintenanceMasterDTO;

@Component
public class VehicleMaintenanceMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleMaintenanceMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleMaintenanceMaster mapVehicleMaintenanceMasterDTOToVehicleMaintenanceMaster(
            VehicleMaintenanceMasterDTO vehicleMaintenanceMasterDTO) {

        if (vehicleMaintenanceMasterDTO == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterDTO, VehicleMaintenanceMaster.class);

    }

    public VehicleMaintenanceMasterDTO mapVehicleMaintenanceMasterToVehicleMaintenanceMasterDTO(
            VehicleMaintenanceMaster vehicleMaintenanceMaster) {

        if (vehicleMaintenanceMaster == null) {
            return null;
        }

        return map(vehicleMaintenanceMaster, VehicleMaintenanceMasterDTO.class);

    }

    public List<VehicleMaintenanceMasterDTO> mapVehicleMaintenanceMasterListToVehicleMaintenanceMasterDTOList(
            List<VehicleMaintenanceMaster> vehicleMaintenanceMasterList) {

        if (vehicleMaintenanceMasterList == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterList, VehicleMaintenanceMasterDTO.class);

    }

    public List<VehicleMaintenanceMaster> mapVehicleMaintenanceMasterDTOListToVehicleMaintenanceMasterList(
            List<VehicleMaintenanceMasterDTO> vehicleMaintenanceMasterDTOList) {

        if (vehicleMaintenanceMasterDTOList == null) {
            return null;
        }

        return map(vehicleMaintenanceMasterDTOList, VehicleMaintenanceMaster.class);

    }

}
