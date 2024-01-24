package com.abm.mainet.water.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.model.mapping.AbstractModelMapper;
import com.abm.mainet.water.domain.ChangeOfUsage;
import com.abm.mainet.water.domain.TBWaterDisconnection;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.RoadTypeDTO;
import com.abm.mainet.water.dto.TBWaterDisconnectionDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;

@Component
public class WaterServiceMapper extends AbstractModelMapper {

    private final ModelMapper modelMapper;

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    public WaterServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TbKCsmrInfoMH mapTbKCsmrInfoDTOToTbKCsmrInfoEntity(final TbCsmrInfoDTO tbCsmrInfoDTO) {
        if (tbCsmrInfoDTO == null) {
            return null;
        }

        final TbKCsmrInfoMH tbKCsmrInfoEntity = map(tbCsmrInfoDTO, TbKCsmrInfoMH.class);

        return tbKCsmrInfoEntity;
    }

    public TbCsmrInfoDTO maptbKCsmrInfoMHToTbKCsmrInfoDTO(final TbKCsmrInfoMH tbKCsmrInfoMH) {
        if (tbKCsmrInfoMH == null) {
            return null;
        }
        tbKCsmrInfoMH.getRoadList();
        tbKCsmrInfoMH.getLinkDetails();
        tbKCsmrInfoMH.getOwnerList();

        final TbCsmrInfoDTO tbKCsmrInfoDTO = map(tbKCsmrInfoMH, TbCsmrInfoDTO.class);

        return tbKCsmrInfoDTO;
    }

    public TBWaterDisconnection mapWaterDisconnectionDtoTOEntity(final TBWaterDisconnectionDTO dto) {
        if (dto == null) {
            return null;
        }

        final TBWaterDisconnection entity = map(dto, TBWaterDisconnection.class);

        return entity;
    }

    public List<RoadTypeDTO> mapTbWtCsmrRoadTypesEntityToRoadTypeDTO(final List<TbWtCsmrRoadTypes> list) {
        if (list == null) {
            return null;
        }

        final List<RoadTypeDTO> RoadTypeDTO = map(list, RoadTypeDTO.class);

        return RoadTypeDTO;
    }

    public List<TbWtCsmrRoadTypes> mapRoadTypeDTOToTbWtCsmrRoadTypesEntity(final List<RoadTypeDTO> roadTypeDTOs) {
        if (roadTypeDTOs == null) {
            return null;
        }
        final List<TbWtCsmrRoadTypes> roadTypesEntity = map(roadTypeDTOs, TbWtCsmrRoadTypes.class);

        return roadTypesEntity;
    }

    public List<CustomerInfoDTO> mapTbKCsmrInfoMHToCustomerInfoDTO(final List<TbKCsmrInfoMH> tbCsmrInfoMHList) {
        if (tbCsmrInfoMHList == null) {
            return null;
        }

        final List<CustomerInfoDTO> tbCsmrInfoDTOList = map(tbCsmrInfoMHList, CustomerInfoDTO.class);

        return tbCsmrInfoDTOList;
    }

    public CustomerInfoDTO mapTbKCsmrInfoToCustomerInfoDTO(final TbKCsmrInfoMH tbCsmrInfoMH) {
        if (tbCsmrInfoMH == null) {
            return null;
        }

        final CustomerInfoDTO tbCsmrInfoDTO = map(tbCsmrInfoMH, CustomerInfoDTO.class);

        return tbCsmrInfoDTO;
    }

    /**
     * Map tb water disconnection to tb water disconnection dto.
     *
     * @param disconnet the disconnet
     * @return the TB water disconnection dto
     */
    public TBWaterDisconnectionDTO mapTBWaterDisconnectionToTBWaterDisconnectionDTO(final TBWaterDisconnection disconnet) {
        if (disconnet == null) {
            throw new FrameworkException("TBWaterDisconnection entity found null, can't be map to TBWaterDisconnection DTO ");
        }
        final TBWaterDisconnectionDTO disconnectionDTO = map(disconnet, TBWaterDisconnectionDTO.class);

        return disconnectionDTO;
    }
    // Change Of Usases ...

    public ChangeOfUsage mapChangeOfUsageDtoToChangeOfUsage(final ChangeOfUsageDTO usesDTO) {
        if (null == usesDTO) {
            throw new RuntimeException("ChangeOfUsesDTO is null");
        }
        final ChangeOfUsage changeOfUses = map(usesDTO, ChangeOfUsage.class);

        return changeOfUses;
    }

    public ChangeOfUsageDTO mapChangeOfUsageToChangeOfUsageDto(final ChangeOfUsage changeOfUses) {
        if (null == changeOfUses) {
            throw new RuntimeException("ChangeOfUses is null");
        }
        final ChangeOfUsageDTO usesDTO = map(changeOfUses, ChangeOfUsageDTO.class);

        return usesDTO;
    }

}
