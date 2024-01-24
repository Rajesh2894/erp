/*package com.abm.mainet.vehicle.management.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
//import com.abm.mainet.vehicle.management.domain.FmVehicleMaster;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

@Component
public class GenVehicleMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GenVehicleMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FmVehicleMaster mapVehicleMasterDTOToVehicleMaster(GenVehicleMasterDTO vehicleMasterDTO) {

        if (vehicleMasterDTO == null) {
            return null;
        }

		
		 * vehicleMasterDTO.getTbSwVehicleMasterdets().forEach(det -> {
		 * det.setTbSwVehicleMaster(vehicleMasterDTO); });
		 

        return map(vehicleMasterDTO, FmVehicleMaster.class);

    }

    public GenVehicleMasterDTO mapVehicleMasterToVehicleMasterDTO(FmVehicleMaster fmVehicleMaster) {

        if (fmVehicleMaster == null) {
            return null;
        }

        return map(fmVehicleMaster, GenVehicleMasterDTO.class);

    }

    public List<GenVehicleMasterDTO> mapVehicleMasterListToVehicleMasterDTOList(List<FmVehicleMaster> vehicleMasterList) {

        if (vehicleMasterList == null) {
            return null;
        }

        return map(vehicleMasterList, GenVehicleMasterDTO.class);

    }

    public List<FmVehicleMaster> mapVehicleMasterDTOListToVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterDTOList) {

        if (vehicleMasterDTOList == null) {
            return null;
        }

        return map(vehicleMasterDTOList, FmVehicleMaster.class);

    }

}
*/