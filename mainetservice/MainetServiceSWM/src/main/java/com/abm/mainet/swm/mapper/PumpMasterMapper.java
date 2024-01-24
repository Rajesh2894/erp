package com.abm.mainet.swm.mapper;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.PumpMaster;
import com.abm.mainet.swm.dto.PumpMasterDTO;

@Component
public class PumpMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PumpMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PumpMaster mapPumpMasterDTOToPumpMaster(PumpMasterDTO pumpMasterDTO) {

        if (pumpMasterDTO == null) {
            return null;
        }

        PumpMaster master = map(pumpMasterDTO, PumpMaster.class);
        master.getTbSwPumpFuldets().forEach(item -> {
            if (item.getPfuId() == null) {
                item.setOrgid(master.getOrgid());
                item.setCreatedBy(master.getCreatedBy());
                item.setCreatedDate(new Date());
                item.setLgIpMac(master.getLgIpMac());
                item.setTbSwPumpMast(master);
            } else {
                item.setUpdatedBy(master.getUpdatedBy());
                item.setUpdatedDate(master.getUpdatedDate());
                item.setLgIpMacUpd(master.getLgIpMacUpd());
            }
        });

        return master;

    }

    public PumpMasterDTO mapPumpMasterToPumpMasterDTO(PumpMaster pumpMaster) {

        if (pumpMaster == null) {
            return null;
        }

        return map(pumpMaster, PumpMasterDTO.class);

    }

    public List<PumpMasterDTO> mapPumpMasterListToPumpMasterDTOList(List<PumpMaster> pumpMasterList) {

        if (pumpMasterList == null) {
            return null;
        }

        return map(pumpMasterList, PumpMasterDTO.class);

    }

    public List<PumpMaster> mapPumpMasterDTOListToPumpMasterList(List<PumpMasterDTO> pumpMasterDTOList) {

        if (pumpMasterDTOList == null) {
            return null;
        }

        return map(pumpMasterDTOList, PumpMaster.class);

    }

}
