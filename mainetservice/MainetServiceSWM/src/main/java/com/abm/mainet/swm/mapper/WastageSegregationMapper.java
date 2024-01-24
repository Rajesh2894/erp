package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.WastageSegregation;
import com.abm.mainet.swm.dto.WastageSegregationDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Component
public class WastageSegregationMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WastageSegregationMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public WastageSegregation mapWastageSegregationDTOToWastageSegregation(WastageSegregationDTO wastageSegregationDTO) {

        if (wastageSegregationDTO == null) {
            return null;
        }

        wastageSegregationDTO.getTbSwWastesegDets().forEach(det -> {
            det.setTbSwWasteseg(wastageSegregationDTO);
        });

        return map(wastageSegregationDTO, WastageSegregation.class);

    }

    public WastageSegregationDTO mapWastageSegregationToWastageSegregationDTO(WastageSegregation wastageSegregation) {

        if (wastageSegregation == null) {
            return null;
        }

        WastageSegregationDTO masterDto = map(wastageSegregation, WastageSegregationDTO.class);
        return masterDto;
    }

    public List<WastageSegregationDTO> mapWastageSegregationListToWastageSegregationDTOList(
            List<WastageSegregation> wastageSegregationList) {

        if (wastageSegregationList == null) {
            return null;
        }

        return map(wastageSegregationList, WastageSegregationDTO.class);

    }

    public List<WastageSegregation> mapWastageSegregationDTOListToWastageSegregationList(
            List<WastageSegregationDTO> wastageSegregationDTOList) {

        if (wastageSegregationDTOList == null) {
            return null;
        }

        return map(wastageSegregationDTOList, WastageSegregation.class);

    }

}
