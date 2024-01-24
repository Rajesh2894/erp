package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleFuelReconciation;
import com.abm.mainet.swm.dto.VehicleFuelReconciationDTO;

@Component
public class VehicleFuelReconciationMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleFuelReconciationMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleFuelReconciation mapVehicleFuelReconciationDTOToVehicleFuelReconciation(
            VehicleFuelReconciationDTO disposalMasterDTO) {

        if (disposalMasterDTO == null) {
            return null;
        }

        VehicleFuelReconciation master = map(disposalMasterDTO, VehicleFuelReconciation.class);

        return master;
    }

    public VehicleFuelReconciationDTO mapVehicleFuelReconciationToVehicleFuelReconciationDTO(
            VehicleFuelReconciation vehicleFuelReconciation) {

        if (vehicleFuelReconciation == null) {
            return null;
        }

        return map(vehicleFuelReconciation, VehicleFuelReconciationDTO.class);

    }

    public List<VehicleFuelReconciationDTO> mapVehicleFuelReconciationListToVehicleFuelReconciationDTOList(
            List<VehicleFuelReconciation> disposalMasterList) {

        if (disposalMasterList == null) {
            return null;
        }

        return map(disposalMasterList, VehicleFuelReconciationDTO.class);

    }

    public List<VehicleFuelReconciation> mapVehicleFuelReconciationDTOListToVehicleFuelReconciationList(
            List<VehicleFuelReconciationDTO> disposalMasterDTOList) {

        if (disposalMasterDTOList == null) {
            return null;
        }

        return map(disposalMasterDTOList, VehicleFuelReconciation.class);

    }

}
