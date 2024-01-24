/*package com.abm.mainet.vehicle.management.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehicle.management.domain.FmVendorContractMapping;
import com.abm.mainet.vehicle.management.dto.VendorContractMappingDTO;

*//**
 * @author Lalit.Prusti
 *
 * Created Date : 30-May-2018
 *//*
@Component
public class GenVendorContractMappingMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GenVendorContractMappingMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public FmVendorContractMapping mapVendorContractMappingDTOToVendorContractMapping(
            VendorContractMappingDTO vendorContractMappingDTO) {

        if (vendorContractMappingDTO == null) {
            return null;
        }

        return map(vendorContractMappingDTO, FmVendorContractMapping.class);

    }

    public VendorContractMappingDTO mapVendorContractMappingToVendorContractMappingDTO(
            FmVendorContractMapping vendorContractMapping) {

        if (vendorContractMapping == null) {
            return null;
        }

        return map(vendorContractMapping, VendorContractMappingDTO.class);

    }

    public List<VendorContractMappingDTO> mapVendorContractMappingListToVendorContractMappingDTOList(
            List<FmVendorContractMapping> vendorContractMappingList) {

        if (vendorContractMappingList == null) {
            return null;
        }

        return map(vendorContractMappingList, VendorContractMappingDTO.class);

    }

    public List<FmVendorContractMapping> mapVendorContractMappingDTOListToVendorContractMappingList(
            List<VendorContractMappingDTO> vendorContractMappingDTOList) {

        if (vendorContractMappingDTOList == null) {
            return null;
        }

        return map(vendorContractMappingDTOList, FmVendorContractMapping.class);

    }

}
*/