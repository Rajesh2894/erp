package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleData;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;

@Component
public class VehicleSchedullingMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleSchedullingMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleScheduleData mapVehicleScheduleDTOToVehicleSchedule(VehicleScheduleDTO vehicleScheduleDTO) {

        if (vehicleScheduleDTO == null) {
            return null;
        }

        return map(vehicleScheduleDTO, VehicleScheduleData.class);

    }

    public VehicleScheduleDTO mapVehicleScheduleToVehicleScheduleDTO(VehicleScheduleData vehicleSchedule) {

        if (vehicleSchedule == null) {
            return null;
        }

        return map(vehicleSchedule, VehicleScheduleDTO.class);

    }

    public List<VehicleScheduleDTO> mapVehicleScheduleListToVehicleScheduleDTOList(List<VehicleScheduleData> vehicleScheduleList) {

        if (vehicleScheduleList == null) {
            return null;
        }

        return map(vehicleScheduleList, VehicleScheduleDTO.class);

    }

    public List<VehicleScheduleData> mapVehicleScheduleDTOListToVehicleScheduleList(List<VehicleScheduleDTO> vehicleScheduleDTOList) {

        if (vehicleScheduleDTOList == null) {
            return null;
        }

        return map(vehicleScheduleDTOList, VehicleScheduleData.class);

    }

}
