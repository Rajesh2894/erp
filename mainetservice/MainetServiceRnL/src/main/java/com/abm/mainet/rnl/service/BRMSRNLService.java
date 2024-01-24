package com.abm.mainet.rnl.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;

/**
 * @author hiren.poriya
 * @Since 09-Jun-2018
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface BRMSRNLService {

    /**
     * this service is used for get applicable tax for RNL service
     * @param WSRequestDTO which contain OrgId, service code and charge applicable at field id
     * @return WSResponseDTO which contain RNLRateMaster DTO with applicable tax details
     */
    WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO wsRequestDTO);

    /**
     * this service is used for get Service for RNL Service charges from BRMS.
     * @param requestDTO with contain RNLRATEMaster details
     * @return WSResponseDTO with List<ChargeDetailDTO> for RNL service
     */
    WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO wsRequestDTO);

    /**
     * @param charges
     * @return
     */
    double chargesToPay(List<ChargeDetailDTO> charges);

    PropertyResDTO getFilteredRentedProperties(EstatePropReqestDTO reqDto);

	PropertyResDTO getFilteredWaterTanker(EstatePropReqestDTO reqDto);

	BookingResDTO saveWaterTanker(BookingReqDTO bookingReqDTO);

	BookingReqDTO getWaterTankerDetailByAppId(BookingReqDTO bookingReqDTO);

	WSResponseDTO getPenaltyCharges(WSRequestDTO wsRequestDTO);

}
