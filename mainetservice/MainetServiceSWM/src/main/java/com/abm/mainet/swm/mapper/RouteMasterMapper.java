package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.BeatMaster;
import com.abm.mainet.swm.dto.BeatMasterDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
@Component
public class RouteMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RouteMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public BeatMaster mapRouteMasterDTOToRouteMaster(final BeatMasterDTO routeMasterDTO) {

	if (routeMasterDTO == null) {
	    return null;
	}
 
	return map(routeMasterDTO, BeatMaster.class);

    }

    public BeatMasterDTO mapRouteMasterToRouteMasterDTO(BeatMaster routeMaster) {

        if (routeMaster == null) {
            return null;
        }

        return map(routeMaster, BeatMasterDTO.class);

    }

    public List<BeatMasterDTO> mapRouteMasterListToRouteMasterDTOList(List<BeatMaster> routeMasterList) {

        if (routeMasterList == null) {
            return null;
        }

        return map(routeMasterList, BeatMasterDTO.class);

    }

    public List<BeatMaster> mapRouteMasterDTOListToRouteMasterList(List<BeatMasterDTO> routeMasterDTOList) {

        if (routeMasterDTOList == null) {
            return null;
        }

        return map(routeMasterDTOList, BeatMaster.class);

    }

}
