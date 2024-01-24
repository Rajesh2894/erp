package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VeVehicleMaster;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;

@Component
public class GeVehicleMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GeVehicleMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VeVehicleMaster mapVehicleMasterDTOToVehicleMaster(GenVehicleMasterDTO vehicleMasterDTO) {

        if (vehicleMasterDTO == null) {
            return null;
        }

		/*
		 * vehicleMasterDTO.getTbSwVehicleMasterdets().forEach(det -> {
		 * det.setTbSwVehicleMaster(vehicleMasterDTO); });
		 */

        return map(vehicleMasterDTO, VeVehicleMaster.class);

    }

    public GenVehicleMasterDTO mapVehicleMasterToVehicleMasterDTO(VeVehicleMaster vehicleMaster) {

        if (vehicleMaster == null) {
            return null;
        }

        return map(vehicleMaster, GenVehicleMasterDTO.class);

    }

    public List<GenVehicleMasterDTO> mapVehicleMasterListToVehicleMasterDTOList(List<VeVehicleMaster> vehicleMasterList) {

        if (vehicleMasterList == null) {
            return null;
        }

        return map(vehicleMasterList, GenVehicleMasterDTO.class);

    }

    public List<VeVehicleMaster> mapVehicleMasterDTOListToVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterDTOList) {

        if (vehicleMasterDTOList == null) {
            return null;
        }

        return map(vehicleMasterDTOList, VeVehicleMaster.class);

    }

}
