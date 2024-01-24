package com.abm.mainet.water.dto;

import java.io.Serializable;

import com.abm.mainet.common.integration.dto.ResponseDTO;

public class ChangeOfUsageResponseDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private CustomerInfoDTO customerInfoDTO;

    public CustomerInfoDTO getCustomerInfoDTO() {
        return customerInfoDTO;
    }

    public void setCustomerInfoDTO(final CustomerInfoDTO customerInfoDTO) {
        this.customerInfoDTO = customerInfoDTO;
    }
}
