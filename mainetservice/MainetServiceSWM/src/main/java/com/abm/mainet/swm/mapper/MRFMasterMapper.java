package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.MRFMaster;
import com.abm.mainet.swm.dto.MRFMasterDto;

@Component
public class MRFMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * @param modelMapper
     */
    public MRFMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public MRFMaster mapMRFMasterDtoToMRFMaster(MRFMasterDto mRFMasterDto) {
        if (mRFMasterDto == null) {
            return null;
        }
        MRFMaster master = map(mRFMasterDto, MRFMaster.class);

        return master;
    }

    public MRFMasterDto MRFMasterToMRFMasterDto(MRFMaster mRFMaster) {
        if (mRFMaster == null) {
            return null;
        }
        MRFMasterDto masterdto = map(mRFMaster, MRFMasterDto.class);

        return masterdto;
    }

    public List<MRFMasterDto> mapMRFMasterListToMRFMasterDtoList(
            List<MRFMaster> mRFMasterList) {
        if (mRFMasterList == null) {
            return null;
        }
        return map(mRFMasterList, MRFMasterDto.class);
    }

    public List<MRFMaster> mapMRFMasterDtoListToMRFMasterList(
            List<MRFMasterDto> mRFMasterDtoList) {

        if (mRFMasterDtoList == null) {
            return null;
        }
        return map(mRFMasterDtoList, MRFMaster.class);
    }

}
