package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.FineChargeCollection;
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;

@Component
public class FineChargeCollectionMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FineChargeCollectionMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FineChargeCollection mapFineChargeCollectionDTOToFineChargeCollection(
            FineChargeCollectionDTO fineChargeCollectionDTO) {

        if (fineChargeCollectionDTO == null) {
            return null;
        }

        return map(fineChargeCollectionDTO, FineChargeCollection.class);

    }

    public FineChargeCollectionDTO mapFineChargeCollectionToFineChargeCollectionDTO(FineChargeCollection fineChargeCollection) {

        if (fineChargeCollection == null) {
            return null;
        }

        return map(fineChargeCollection, FineChargeCollectionDTO.class);

    }

    public List<FineChargeCollectionDTO> mapFineChargeCollectionListToFineChargeCollectionDTOList(
            List<FineChargeCollection> FineChargeCollectionList) {

        if (FineChargeCollectionList == null) {
            return null;
        }

        return map(FineChargeCollectionList, FineChargeCollectionDTO.class);

    }

    public List<FineChargeCollection> mapFineChargeCollectionDTOListToFineChargeCollectionList(
            List<FineChargeCollectionDTO> fineChargeCollectionDTOList) {

        if (fineChargeCollectionDTOList == null) {
            return null;
        }

        return map(fineChargeCollectionDTOList, FineChargeCollection.class);

    }

}
