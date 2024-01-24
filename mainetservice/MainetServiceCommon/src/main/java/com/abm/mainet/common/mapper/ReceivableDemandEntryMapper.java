package com.abm.mainet.common.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.ReceivableDemandEntry;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class ReceivableDemandEntryMapper extends AbstractServiceMapper {
    
    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public ReceivableDemandEntryMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    
    public ReceivableDemandEntry mapDTOToEntity(ReceivableDemandEntryDTO receivableDemandEntryDTO) {

        if (receivableDemandEntryDTO == null) {
            return null;
        }
        receivableDemandEntryDTO.getRcvblDemandList().forEach(det -> {
            det.setRcvblDemandDets(receivableDemandEntryDTO);
        });

        return map(receivableDemandEntryDTO, ReceivableDemandEntry.class);

    }

    public ReceivableDemandEntryDTO mapEntityToDTO(ReceivableDemandEntry receivableDemandEntry) {

        if (receivableDemandEntry == null) {
            return null;
        }

        return map(receivableDemandEntry, ReceivableDemandEntryDTO.class);

    }

    public List<ReceivableDemandEntryDTO> mapEntityListToDTOList(List<ReceivableDemandEntry> receivableDemandEntryList) {

        if (receivableDemandEntryList == null) {
            return null;
        }

        return map(receivableDemandEntryList, ReceivableDemandEntryDTO.class);

    }

    public List<ReceivableDemandEntry> mapDTOListToEntityList(
            List<ReceivableDemandEntryDTO> receivableDemandEntryDTO) {

        if (receivableDemandEntryDTO == null) {
            return null;
        }

        return map(receivableDemandEntryDTO, ReceivableDemandEntry.class);

    }


}
