package com.abm.mainet.common.master.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.HelpDesk;
import com.abm.mainet.common.dto.HelpDeskDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class HelpDeskMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public HelpDeskMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HelpDesk mapDtoToEntity(HelpDeskDTO dto) {
        if (dto == null) {
            return null;
        }
        return map(dto, HelpDesk.class);
    }

    public HelpDeskDTO mapEntitytoDto(HelpDesk entity) {
        if (entity == null) {
            return null;
        }
        return map(entity, HelpDeskDTO.class);
    }

    public List<HelpDeskDTO> mapEntityListtoDtoList(List<HelpDesk> entityList) {
        if (entityList == null) {
            return null;
        }
        return map(entityList, HelpDeskDTO.class);
    }

}
