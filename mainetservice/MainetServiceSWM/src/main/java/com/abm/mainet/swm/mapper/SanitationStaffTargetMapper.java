package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.SanitationStaffTarget;
import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Component
public class SanitationStaffTargetMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SanitationStaffTargetMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SanitationStaffTarget mapSanitationStaffTargetDTOToSanitationStaffTarget(
            SanitationStaffTargetDTO sanitationStaffTargetDTO) {

        if (sanitationStaffTargetDTO == null) {
            return null;
        }

        SanitationStaffTarget master = map(sanitationStaffTargetDTO, SanitationStaffTarget.class);

        return master;
    }

    public SanitationStaffTargetDTO mapSanitationStaffTargetToSanitationStaffTargetDTO(
            SanitationStaffTarget sanitationStaffTarget) {

        if (sanitationStaffTarget == null) {
            return null;
        }

        return map(sanitationStaffTarget, SanitationStaffTargetDTO.class);

    }

    public List<SanitationStaffTargetDTO> mapSanitationStaffTargetListToSanitationStaffTargetDTOList(
            List<SanitationStaffTarget> sanitationStaffTargetList) {

        if (sanitationStaffTargetList == null) {
            return null;
        }

        return map(sanitationStaffTargetList, SanitationStaffTargetDTO.class);

    }

    public List<SanitationStaffTarget> mapSanitationStaffTargetDTOListToSanitationStaffTargetList(
            List<SanitationStaffTargetDTO> sanitationStaffTargetDTOList) {

        if (sanitationStaffTargetDTOList == null) {
            return null;
        }

        return map(sanitationStaffTargetDTOList, SanitationStaffTarget.class);

    }

}
