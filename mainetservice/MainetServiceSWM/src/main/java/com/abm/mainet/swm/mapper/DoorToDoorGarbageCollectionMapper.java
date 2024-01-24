package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.DoorToDoorGarbageCollection;
import com.abm.mainet.swm.dto.DoorToDoorGarbageCollectionDTO;

@Component
public class DoorToDoorGarbageCollectionMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DoorToDoorGarbageCollectionMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public DoorToDoorGarbageCollection mapDoorToDoorGarbageCollectionDTOToDoorToDoorGarbageCollection(
            DoorToDoorGarbageCollectionDTO doorToDoorGarbageCollectionDTO) {

        if (doorToDoorGarbageCollectionDTO == null) {
            return null;
        }

        return map(doorToDoorGarbageCollectionDTO, DoorToDoorGarbageCollection.class);

    }

    public DoorToDoorGarbageCollectionDTO mapDoorToDoorGarbageCollectionToDoorToDoorGarbageCollectionDTO(
            DoorToDoorGarbageCollection doorToDoorGarbageCollection) {

        if (doorToDoorGarbageCollection == null) {
            return null;
        }

        return map(doorToDoorGarbageCollection, DoorToDoorGarbageCollectionDTO.class);

    }

    public List<DoorToDoorGarbageCollectionDTO> mapDoorToDoorGarbageCollectionListToDoorToDoorGarbageCollectionDTOList(
            List<DoorToDoorGarbageCollection> doorToDoorGarbageCollectionList) {

        if (doorToDoorGarbageCollectionList == null) {
            return null;
        }

        return map(doorToDoorGarbageCollectionList, DoorToDoorGarbageCollectionDTO.class);

    }

    public List<DoorToDoorGarbageCollection> mapDoorToDoorGarbageCollectionDTOListToDoorToDoorGarbageCollectionList(
            List<DoorToDoorGarbageCollectionDTO> doorToDoorGarbageCollectionDTOList) {

        if (doorToDoorGarbageCollectionDTOList == null) {
            return null;
        }

        return map(doorToDoorGarbageCollectionDTOList, DoorToDoorGarbageCollection.class);

    }

}
