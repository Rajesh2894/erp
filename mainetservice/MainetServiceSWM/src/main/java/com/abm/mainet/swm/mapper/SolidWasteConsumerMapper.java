package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;
import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 19-Jun-2018
 */
@Component
public class SolidWasteConsumerMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SolidWasteConsumerMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SolidWasteConsumerMaster mapUserRegistrationDTOToUserRegistration(SolidWasteConsumerMasterDTO userRegistrationDTO) {

        if (userRegistrationDTO == null) {
            return null;
        }

        SolidWasteConsumerMaster master = map(userRegistrationDTO, SolidWasteConsumerMaster.class);

        return master;
    }

    public SolidWasteConsumerMasterDTO mapUserRegistrationToUserRegistrationDTO(SolidWasteConsumerMaster userRegistration) {

        if (userRegistration == null) {
            return null;
        }

        return map(userRegistration, SolidWasteConsumerMasterDTO.class);

    }

    public List<SolidWasteConsumerMasterDTO> mapUserRegistrationListToUserRegistrationDTOList(
            List<SolidWasteConsumerMaster> userRegistrationList) {

        if (userRegistrationList == null) {
            return null;
        }

        return map(userRegistrationList, SolidWasteConsumerMasterDTO.class);

    }

    public List<SolidWasteConsumerMaster> mapUserRegistrationDTOListToUserRegistrationList(
            List<SolidWasteConsumerMasterDTO> userRegistrationDTOList) {

        if (userRegistrationDTOList == null) {
            return null;
        }

        return map(userRegistrationDTOList, SolidWasteConsumerMaster.class);

    }

}
