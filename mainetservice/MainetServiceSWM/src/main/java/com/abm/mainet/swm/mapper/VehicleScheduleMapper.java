package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleSchedule;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;

@Component
public class VehicleScheduleMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleScheduleMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleSchedule mapVehicleScheduleDTOToVehicleSchedule(VehicleScheduleDTO vehicleScheduleDTO) {

        if (vehicleScheduleDTO == null) {
            return null;
        }

        return map(vehicleScheduleDTO, VehicleSchedule.class);

    }

    public VehicleScheduleDTO mapVehicleScheduleToVehicleScheduleDTO(VehicleSchedule vehicleSchedule) {

        if (vehicleSchedule == null) {
            return null;
        }

        return map(vehicleSchedule, VehicleScheduleDTO.class);

    }

    public List<VehicleScheduleDTO> mapVehicleScheduleListToVehicleScheduleDTOList(List<VehicleSchedule> vehicleScheduleList) {

        if (vehicleScheduleList == null) {
            return null;
        }

        return map(vehicleScheduleList, VehicleScheduleDTO.class);

    }

    public List<VehicleSchedule> mapVehicleScheduleDTOListToVehicleScheduleList(
            List<VehicleScheduleDTO> vehicleScheduleDTOList) {

        if (vehicleScheduleDTOList == null) {
            return null;
        }

        return map(vehicleScheduleDTOList, VehicleSchedule.class);

    }

}
