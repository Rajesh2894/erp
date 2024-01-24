package com.abm.mainet.rnl.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author ritesh.patil
 *
 */
public interface IRNLChecklistAndChargeService {

    List<DocumentDetailsVO> getFileUploadList(List<DocumentDetailsVO> checkList, Map<Long, Set<File>> fileMap);

    /**
     * @return
     */
    // WSResponseDTO initializeModel();

    /**
     * @param dto
     * @return
     */
    // List<DocumentDetailsVO> getChecklist(CheckListModel checklistModel);

    /**
     * @param rate
     * @param orgid
     * @param serviceId
     * @return
     */
    WSResponseDTO getApplicableTaxes(RNLRateMaster rate, long orgid, String serviceShortCode);

    /**
     * @param requiredCHarges
     * @return
     */
    List<ChargeDetailDTO> getApplicableCharges(List<RNLRateMaster> requiredCHarges);

    /**
     * @param charges
     * @return
     */
    double chargesToPay(List<ChargeDetailDTO> charges);

    PropertyResDTO getFilteredRentedProperties(Integer categoryId, Long eventId, long capacityFrom, long capacityTo,
            double rentFrom, double rentTo, Organisation org);
    
    
    
    public BookingResDTO saveOrUpdateChangeUsage(final BookingReqDTO bookingResDTO) throws JsonParseException, JsonMappingException, IOException;

	PropertyResDTO getFilteredWaterTanker(Integer categoryId, Long eventId, Organisation org);

	BookingResDTO saveOrUpdateWaterTanker(BookingReqDTO bookingReqDTO) throws JsonParseException, JsonMappingException, IOException;

	BookingReqDTO getApplicationData(BookingReqDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException;   
    
}
