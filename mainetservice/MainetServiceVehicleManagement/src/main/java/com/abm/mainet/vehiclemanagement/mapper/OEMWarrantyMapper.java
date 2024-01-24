package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;


@Component
public class OEMWarrantyMapper extends AbstractServiceMapper{
	
	 private ModelMapper modelMapper;

	    public ModelMapper getModelMapper() {
	        return modelMapper;
	    }

	    public void setModelMapper(ModelMapper modelMapper) {
	        this.modelMapper = modelMapper;
	    }

	    public OEMWarrantyMapper() {
	        modelMapper = new ModelMapper();
	        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	    }

	    public OEMWarranty mapVehicleScheduleDTOToVehicleSchedule(OEMWarrantyDTO oemWarrantyDTO) {

	        if (oemWarrantyDTO == null) {
	            return null;
	        }

	        return map(oemWarrantyDTO, OEMWarranty.class);

	    }

	    public OEMWarrantyDTO mapVehicleScheduleToVehicleScheduleDTO(OEMWarranty oemWarranty) {

	        if (oemWarranty == null) {
	            return null;
	        }

	        return map(oemWarranty, OEMWarrantyDTO.class);

	    }

	    public List<OEMWarrantyDTO> mapVehicleScheduleListToVehicleScheduleDTOList(List<OEMWarranty> oemWarrantyList) {

	        if (oemWarrantyList == null) {
	            return null;
	        }

	        return map(oemWarrantyList, OEMWarrantyDTO.class);

	    }

	    public List<OEMWarranty> mapVehicleScheduleDTOListToVehicleScheduleList(List<OEMWarrantyDTO> oemWarrantyDTOList) {

	        if (oemWarrantyDTOList == null) {
	            return null;
	        }

	        return map(oemWarrantyDTOList, OEMWarranty.class);

	    }

	}



