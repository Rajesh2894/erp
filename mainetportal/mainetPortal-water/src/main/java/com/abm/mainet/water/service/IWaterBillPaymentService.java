package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.List;

import com.abm.mainet.water.dto.WaterBillRequestDTO;
import com.abm.mainet.water.dto.WaterBillResponseDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IWaterBillPaymentService {

    WaterBillResponseDTO fetchBillingData(WaterBillRequestDTO requestDto)
            throws JsonParseException, JsonMappingException, IOException;

    WaterBillResponseDTO saveOrUpdateBillPaid(WaterBillRequestDTO inputData)
            throws JsonParseException, JsonMappingException, IOException;

    WaterBillResponseDTO saveOrUpdateAdvancePayment(WaterBillRequestDTO inputData)
            throws JsonParseException, JsonMappingException, IOException;
    
    List<WaterDataEntrySearchDTO> getBillPaymentDetailData(WaterDataEntrySearchDTO searchDto);

}
