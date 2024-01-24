package com.abm.mainet.common.master.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.ActivityManagement;
import com.abm.mainet.common.dto.ActivityManagementDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class ActivityManagementMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ActivityManagementMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActivityManagement mapDtoToEntity(ActivityManagementDTO dto) {
        if (dto == null) {
            return null;
        }
        return map(dto, ActivityManagement.class);
    }

    public ActivityManagementDTO mapEntitytoDto(ActivityManagement entity) {
        if (entity == null) {
            return null;
        }
        return map(entity, ActivityManagementDTO.class);
    }

    public List<ActivityManagementDTO> mapEntityListtoDtoList(List<ActivityManagement> entityList) {
        if (entityList == null) {
            return null;
        }
        return map(entityList, ActivityManagementDTO.class);
    }

}
