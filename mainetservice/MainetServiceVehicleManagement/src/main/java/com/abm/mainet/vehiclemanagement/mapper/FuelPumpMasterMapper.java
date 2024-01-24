package com.abm.mainet.vehiclemanagement.mapper;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.FuelPumpMaster;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;

@Component
public class FuelPumpMasterMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FuelPumpMasterMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FuelPumpMaster mapPumpMasterDTOToPumpMaster(PumpMasterDTO pumpMasterDTO) {

        if (pumpMasterDTO == null) {
            return null;
        }

        FuelPumpMaster master = map(pumpMasterDTO, FuelPumpMaster.class);
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

    public PumpMasterDTO mapPumpMasterToPumpMasterDTO(FuelPumpMaster pumpMaster) {

        if (pumpMaster == null) {
            return null;
        }

        return map(pumpMaster, PumpMasterDTO.class);

    }

    public List<PumpMasterDTO> mapPumpMasterListToPumpMasterDTOList(List<FuelPumpMaster> pumpMasterList) {

        if (pumpMasterList == null) {
            return null;
        }

        return map(pumpMasterList, PumpMasterDTO.class);

    }

    public List<FuelPumpMaster> mapPumpMasterDTOListToPumpMasterList(List<PumpMasterDTO> pumpMasterDTOList) {

        if (pumpMasterDTOList == null) {
            return null;
        }

        return map(pumpMasterDTOList, FuelPumpMaster.class);

    }

}
