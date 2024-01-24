package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.EmployeeSchedule;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;

@Component
public class EmployeeScheduleMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployeeScheduleMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public EmployeeSchedule mapEmployeeScheduleDTOToEmployeeSchedule(EmployeeScheduleDTO employeeScheduleDTO) {

        if (employeeScheduleDTO == null) {
            return null;
        }

        return map(employeeScheduleDTO, EmployeeSchedule.class);

    }

    public EmployeeScheduleDTO mapEmployeeScheduleToEmployeeScheduleDTO(EmployeeSchedule employeeSchedule) {

        if (employeeSchedule == null) {
            return null;
        }

        return map(employeeSchedule, EmployeeScheduleDTO.class);

    }

    public List<EmployeeScheduleDTO> mapEmployeeScheduleListToEmployeeScheduleDTOList(
            List<EmployeeSchedule> employeeScheduleList) {

        if (employeeScheduleList == null) {
            return null;
        }

        return map(employeeScheduleList, EmployeeScheduleDTO.class);

    }

    public List<EmployeeSchedule> mapEmployeeScheduleDTOListToEmployeeScheduleList(
            List<EmployeeScheduleDTO> employeeScheduleDTOList) {

        if (employeeScheduleDTOList == null) {
            return null;
        }

        return map(employeeScheduleDTOList, EmployeeSchedule.class);

    }

}
