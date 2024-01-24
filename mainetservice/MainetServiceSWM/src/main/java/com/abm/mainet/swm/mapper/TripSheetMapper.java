package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.TripSheet;
import com.abm.mainet.swm.dto.TripSheetDTO;

@Component
public class TripSheetMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TripSheetMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TripSheet mapTripSheetDTOToTripSheet(TripSheetDTO tripsheetDTO) {

        if (tripsheetDTO == null) {
            return null;
        }
        tripsheetDTO.getTbSwTripsheetGdets().forEach(det -> {
            det.setTbSwTripsheet(tripsheetDTO);
        });

        return map(tripsheetDTO, TripSheet.class);

    }

    public TripSheetDTO mapTripSheetToTripSheetDTO(TripSheet tripsheet) {

        if (tripsheet == null) {
            return null;
        }

        return map(tripsheet, TripSheetDTO.class);

    }

    public List<TripSheetDTO> mapTripSheetListToTripSheetDTOList(List<TripSheet> tripsheetList) {

        if (tripsheetList == null) {
            return null;
        }

        return map(tripsheetList, TripSheetDTO.class);

    }

    public List<TripSheet> mapTripSheetDTOListToTripSheetList(
            List<TripSheetDTO> tripsheetDTOList) {

        if (tripsheetDTOList == null) {
            return null;
        }

        return map(tripsheetDTOList, TripSheet.class);

    }

}
