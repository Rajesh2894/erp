package com.abm.mainet.vehiclemanagement.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.vehiclemanagement.domain.VmVendorContractMapping;
import com.abm.mainet.vehiclemanagement.dto.VendorContractMappingDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 30-May-2018
 */
@Component
public class GeVendorContractMappingMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GeVendorContractMappingMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VmVendorContractMapping mapVendorContractMappingDTOToVendorContractMapping(
            VendorContractMappingDTO vendorContractMappingDTO) {

        if (vendorContractMappingDTO == null) {
            return null;
        }

        return map(vendorContractMappingDTO, VmVendorContractMapping.class);

    }

    public VendorContractMappingDTO mapVendorContractMappingToVendorContractMappingDTO(
            VmVendorContractMapping vendorContractMapping) {

        if (vendorContractMapping == null) {
            return null;
        }

        return map(vendorContractMapping, VendorContractMappingDTO.class);

    }

    public List<VendorContractMappingDTO> mapVendorContractMappingListToVendorContractMappingDTOList(
            List<VmVendorContractMapping> vendorContractMappingList) {

        if (vendorContractMappingList == null) {
            return null;
        }

        return map(vendorContractMappingList, VendorContractMappingDTO.class);

    }

    public List<VmVendorContractMapping> mapVendorContractMappingDTOListToVendorContractMappingList(
            List<VendorContractMappingDTO> vendorContractMappingDTOList) {

        if (vendorContractMappingDTOList == null) {
            return null;
        }

        return map(vendorContractMappingDTOList, VmVendorContractMapping.class);

    }

}
