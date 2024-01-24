package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VmVehicleFuelReconciation;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDTO;

@Component
public class VehicleFuelReconciliationMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleFuelReconciliationMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VmVehicleFuelReconciation mapVehicleFuelReconciationDTOToVehicleFuelReconciation(
            VehicleFuelReconciationDTO disposalMasterDTO) {

        if (disposalMasterDTO == null) {
            return null;
        }

        VmVehicleFuelReconciation master = map(disposalMasterDTO, VmVehicleFuelReconciation.class);

        return master;
    }

    public VehicleFuelReconciationDTO mapVehicleFuelReconciationToVehicleFuelReconciationDTO(
    		VmVehicleFuelReconciation vehicleFuelReconciation) {

        if (vehicleFuelReconciation == null) {
            return null;
        }

        return map(vehicleFuelReconciation, VehicleFuelReconciationDTO.class);

    }

    public List<VehicleFuelReconciationDTO> mapVehicleFuelReconciationListToVehicleFuelReconciationDTOList(
            List<VmVehicleFuelReconciation> disposalMasterList) {

        if (disposalMasterList == null) {
            return null;
        }

        return map(disposalMasterList, VehicleFuelReconciationDTO.class);

    }

    public List<VmVehicleFuelReconciation> mapVehicleFuelReconciationDTOListToVehicleFuelReconciationList(
            List<VehicleFuelReconciationDTO> disposalMasterDTOList) {

        if (disposalMasterDTOList == null) {
            return null;
        }

        return map(disposalMasterDTOList, VmVehicleFuelReconciation.class);

    }

}
