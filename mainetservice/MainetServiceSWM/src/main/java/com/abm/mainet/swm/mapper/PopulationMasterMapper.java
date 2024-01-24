package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.PopulationMaster;
import com.abm.mainet.swm.dto.PopulationMasterDTO;

@Component
public class PopulationMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PopulationMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PopulationMaster mapPopulationMasterDTOToPopulationMaster(PopulationMasterDTO populationMasterDTO) {

        if (populationMasterDTO == null) {
            return null;
        }

        return map(populationMasterDTO, PopulationMaster.class);

    }

    public PopulationMasterDTO mapPopulationMasterToPopulationMasterDTO(PopulationMaster populationMaster) {

        if (populationMaster == null) {
            return null;
        }

        return map(populationMaster, PopulationMasterDTO.class);

    }

    public List<PopulationMasterDTO> mapPopulationMasterListToPopulationMasterDTOList(
            List<PopulationMaster> populationMasterList) {

        if (populationMasterList == null) {
            return null;
        }

        return map(populationMasterList, PopulationMasterDTO.class);

    }

    public List<PopulationMaster> mapPopulationMasterDTOListToPopulationMasterList(
            List<PopulationMasterDTO> populationMasterDTOList) {

        if (populationMasterDTOList == null) {
            return null;
        }

        return map(populationMasterDTOList, PopulationMaster.class);

    }

}
