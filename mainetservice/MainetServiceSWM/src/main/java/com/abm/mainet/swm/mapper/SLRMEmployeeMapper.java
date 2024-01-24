package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.SLRMEmployeeMaster;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;

@Component
public class SLRMEmployeeMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public SLRMEmployeeMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    
    public List<SLRMEmployeeMasterDTO> mapSLRMEmployeeMasterListToSLRMEmployeeMasterDTOList(List<SLRMEmployeeMaster> sLRMEmployeeMasterList) {

        if (sLRMEmployeeMasterList == null) {
            return null;
        }

        return map(sLRMEmployeeMasterList, SLRMEmployeeMasterDTO.class);

    }
    
    public List<SLRMEmployeeMaster> mapSLRMEmployeeMasterDTOListToSLRMEmployeeMasterList(
            List<SLRMEmployeeMasterDTO> sLRMEmployeeMasterDTOList) {

        if (sLRMEmployeeMasterDTOList == null) {
            return null;
        }

        return map(sLRMEmployeeMasterDTOList, SLRMEmployeeMaster.class);

    }


}
