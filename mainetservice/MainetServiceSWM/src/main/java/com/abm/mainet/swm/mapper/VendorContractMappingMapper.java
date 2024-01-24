package com.abm.mainet.swm.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.utility.AbstractServiceMapper;
import com.abm.mainet.swm.domain.VendorContractMapping;
import com.abm.mainet.swm.dto.VendorContractMappingDTO;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 30-May-2018
 */
@Component
public class VendorContractMappingMapper extends AbstractServiceMapper {

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VendorContractMappingMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public VendorContractMapping mapVendorContractMappingDTOToVendorContractMapping(
            VendorContractMappingDTO vendorContractMappingDTO) {

        if (vendorContractMappingDTO == null) {
            return null;
        }

        return map(vendorContractMappingDTO, VendorContractMapping.class);

    }

    public VendorContractMappingDTO mapVendorContractMappingToVendorContractMappingDTO(
            VendorContractMapping vendorContractMapping) {

        if (vendorContractMapping == null) {
            return null;
        }

        return map(vendorContractMapping, VendorContractMappingDTO.class);

    }

    public List<VendorContractMappingDTO> mapVendorContractMappingListToVendorContractMappingDTOList(
            List<VendorContractMapping> vendorContractMappingList) {

        if (vendorContractMappingList == null) {
            return null;
        }

        return map(vendorContractMappingList, VendorContractMappingDTO.class);

    }

    public List<VendorContractMapping> mapVendorContractMappingDTOListToVendorContractMappingList(
            List<VendorContractMappingDTO> vendorContractMappingDTOList) {

        if (vendorContractMappingDTOList == null) {
            return null;
        }

        return map(vendorContractMappingDTOList, VendorContractMapping.class);

    }

}
