package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VehicleFuellingData;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Component
public class VehicleFuelMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VehicleFuelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VehicleFuellingData mapVehicleFuelingDTOToVehicleFueling(VehicleFuellingDTO vehicleFuelingDTO) {

        if (vehicleFuelingDTO == null) {
            return null;
        }

        return map(vehicleFuelingDTO, VehicleFuellingData.class);

    }

    public VehicleFuellingDTO mapVehicleFuelingToVehicleFuelingDTO(VehicleFuellingData vehicleFueling) {

        if (vehicleFueling == null) {
            return null;
        }

        return map(vehicleFueling, VehicleFuellingDTO.class);

    }

    public List<VehicleFuellingDTO> mapVehicleFuelingListToVehicleFuelingDTOList(
            List<VehicleFuellingData> vehicleFuelingList) {

        if (vehicleFuelingList == null) {
            return null;
        }

        return map(vehicleFuelingList, VehicleFuellingDTO.class);

    }

    public List<VehicleFuellingData> mapVehicleFuelingDTOListToVehicleFuelingList(
            List<VehicleFuellingDTO> vehicleFuelingDTOList) {

        if (vehicleFuelingDTOList == null) {
            return null;
        }

        return map(vehicleFuelingDTOList, VehicleFuellingData.class);

    }

}
