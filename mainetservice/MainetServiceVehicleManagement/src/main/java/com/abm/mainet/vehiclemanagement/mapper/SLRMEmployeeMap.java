package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMaster;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;

@Component
public class SLRMEmployeeMap extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public SLRMEmployeeMap() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    
    public List<SLRMEmployeeMasterDTO> mapVehicleEmployeeMasterListToSLRMEmployeeMasterDTOList(List<VehicleEmployeeMaster> VehicleEmployeeMasterList) {

        if (VehicleEmployeeMasterList == null) {
            return null;
        }

        return map(VehicleEmployeeMasterList, SLRMEmployeeMasterDTO.class);

    }
    
    public List<VehicleEmployeeMaster> mapSLRMEmployeeMasterDTOListToVehicleEmployeeMasterList(
            List<SLRMEmployeeMasterDTO> SLRMEmployeeMasterDTOList) {

        if (SLRMEmployeeMasterDTOList == null) {
            return null;
        }

        return map(SLRMEmployeeMasterDTOList, VehicleEmployeeMaster.class);

    }


}
