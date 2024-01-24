package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.SolidWasteBillMaster;
import com.abm.mainet.swm.dto.SolidWasteBillMasterDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 19-Jun-2018
 */
@Component
public class SolidWasteBillMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SolidWasteBillMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SolidWasteBillMaster mapBillMasterDTOToBillMaster(SolidWasteBillMasterDTO billMasterDTO) {

        if (billMasterDTO == null) {
            return null;
        }

        SolidWasteBillMaster master = map(billMasterDTO, SolidWasteBillMaster.class);

        return master;
    }

    public SolidWasteBillMasterDTO mapBillMasterToBillMasterDTO(SolidWasteBillMaster billMaster) {

        if (billMaster == null) {
            return null;
        }

        return map(billMaster, SolidWasteBillMasterDTO.class);

    }

    public List<SolidWasteBillMasterDTO> mapBillMasterListToBillMasterDTOList(
            List<SolidWasteBillMaster> billMasterList) {

        if (billMasterList == null) {
            return null;
        }

        return map(billMasterList, SolidWasteBillMasterDTO.class);

    }

    public List<SolidWasteBillMaster> mapBillMasterDTOListToBillMasterList(
            List<SolidWasteBillMasterDTO> billMasterDTOList) {

        if (billMasterDTOList == null) {
            return null;
        }

        return map(billMasterDTOList, SolidWasteBillMaster.class);

    }

}
