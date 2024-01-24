package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VehicleFuelling;
import com.abm.mainet.swm.dto.VehicleFuellingDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Component
public class VehicleFuellingMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleFuellingMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleFuelling mapVehicleFuelingDTOToVehicleFueling(VehicleFuellingDTO vehicleFuelingDTO) {

        if (vehicleFuelingDTO == null) {
            return null;
        }

        return map(vehicleFuelingDTO, VehicleFuelling.class);

    }

    public VehicleFuellingDTO mapVehicleFuelingToVehicleFuelingDTO(VehicleFuelling vehicleFueling) {

        if (vehicleFueling == null) {
            return null;
        }

        return map(vehicleFueling, VehicleFuellingDTO.class);

    }

    public List<VehicleFuellingDTO> mapVehicleFuelingListToVehicleFuelingDTOList(
            List<VehicleFuelling> vehicleFuelingList) {

        if (vehicleFuelingList == null) {
            return null;
        }

        return map(vehicleFuelingList, VehicleFuellingDTO.class);

    }

    public List<VehicleFuelling> mapVehicleFuelingDTOListToVehicleFuelingList(
            List<VehicleFuellingDTO> vehicleFuelingDTOList) {

        if (vehicleFuelingDTOList == null) {
            return null;
        }

        return map(vehicleFuelingDTOList, VehicleFuelling.class);

    }

}
