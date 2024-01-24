package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleMaster;
import com.abm.mainet.swm.dto.VehicleMasterDTO;

@Component
public class VehicleMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleMaster mapVehicleMasterDTOToVehicleMaster(VehicleMasterDTO vehicleMasterDTO) {

        if (vehicleMasterDTO == null) {
            return null;
        }

        vehicleMasterDTO.getTbSwVehicleMasterdets().forEach(det -> {
            det.setTbSwVehicleMaster(vehicleMasterDTO);
        });

        return map(vehicleMasterDTO, VehicleMaster.class);

    }

    public VehicleMasterDTO mapVehicleMasterToVehicleMasterDTO(VehicleMaster vehicleMaster) {

        if (vehicleMaster == null) {
            return null;
        }

        return map(vehicleMaster, VehicleMasterDTO.class);

    }

    public List<VehicleMasterDTO> mapVehicleMasterListToVehicleMasterDTOList(List<VehicleMaster> vehicleMasterList) {

        if (vehicleMasterList == null) {
            return null;
        }

        return map(vehicleMasterList, VehicleMasterDTO.class);

    }

    public List<VehicleMaster> mapVehicleMasterDTOListToVehicleMasterList(List<VehicleMasterDTO> vehicleMasterDTOList) {

        if (vehicleMasterDTOList == null) {
            return null;
        }

        return map(vehicleMasterDTOList, VehicleMaster.class);

    }

}
