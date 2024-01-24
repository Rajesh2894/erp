package com.abm.mainet.common.master.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.CustomerMasterEntity;
import com.abm.mainet.common.master.dto.CustomerMasterDTO;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class CustomerMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    public CustomerMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public List<CustomerMasterDTO> mapEntityListToDTOList(List<CustomerMasterEntity> customerMasterEntityList) {

        if (customerMasterEntityList == null) {
            return null;
        }

        return map(customerMasterEntityList, CustomerMasterDTO.class);

    }

    public List<CustomerMasterEntity> mapDTOListToEntityList(
            List<CustomerMasterDTO> customerMasterDTOList) {

        if (customerMasterDTOList == null) {
            return null;
        }

        return map(customerMasterDTOList, CustomerMasterEntity.class);

    }

}
