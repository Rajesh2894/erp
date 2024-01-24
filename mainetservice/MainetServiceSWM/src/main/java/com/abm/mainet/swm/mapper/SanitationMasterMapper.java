package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.SanitationMaster;
import com.abm.mainet.swm.dto.SanitationMasterDTO;

@Component
public class SanitationMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SanitationMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SanitationMaster mapSanitationMasterDTOToSanitationMaster(SanitationMasterDTO sanitationMasterDTO) {

        if (sanitationMasterDTO == null) {
            return null;
        }

        return map(sanitationMasterDTO, SanitationMaster.class);

    }

    public SanitationMasterDTO mapSanitationMasterToSanitationMasterDTO(SanitationMaster sanitationMaster) {

        if (sanitationMaster == null) {
            return null;
        }

        return map(sanitationMaster, SanitationMasterDTO.class);

    }

    public List<SanitationMasterDTO> mapSanitationMasterListToSanitationMasterDTOList(
            List<SanitationMaster> sanitationMasterList) {

        if (sanitationMasterList == null) {
            return null;
        }

        return map(sanitationMasterList, SanitationMasterDTO.class);

    }

    public List<SanitationMaster> mapSanitationMasterDTOListToSanitationMasterList(
            List<SanitationMasterDTO> sanitationMasterDTOList) {

        if (sanitationMasterDTOList == null) {
            return null;
        }

        return map(sanitationMasterDTOList, SanitationMaster.class);

    }

}
