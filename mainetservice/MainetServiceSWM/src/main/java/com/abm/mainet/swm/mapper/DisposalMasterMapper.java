package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.DisposalMaster;
import com.abm.mainet.swm.dto.DisposalMasterDTO;

@Component
public class DisposalMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DisposalMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public DisposalMaster mapDisposalMasterDTOToDisposalMaster(DisposalMasterDTO disposalMasterDTO) {

        if (disposalMasterDTO == null) {
            return null;
        }

        DisposalMaster master = map(disposalMasterDTO, DisposalMaster.class);

        return master;
    }

    public DisposalMasterDTO mapDisposalMasterToDisposalMasterDTO(DisposalMaster disposalMaster) {

        if (disposalMaster == null) {
            return null;
        }

        return map(disposalMaster, DisposalMasterDTO.class);

    }

    public List<DisposalMasterDTO> mapDisposalMasterListToDisposalMasterDTOList(
            List<DisposalMaster> disposalMasterList) {

        if (disposalMasterList == null) {
            return null;
        }

        return map(disposalMasterList, DisposalMasterDTO.class);

    }

    public List<DisposalMaster> mapDisposalMasterDTOListToDisposalMasterList(
            List<DisposalMasterDTO> disposalMasterDTOList) {

        if (disposalMasterDTOList == null) {
            return null;
        }

        return map(disposalMasterDTOList, DisposalMaster.class);

    }

}
