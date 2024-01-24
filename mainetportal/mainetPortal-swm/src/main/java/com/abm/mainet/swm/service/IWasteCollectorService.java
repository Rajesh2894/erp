/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IWasteCollectorService {

	CollectorResDTO saveWasteCollector(CollectorReqDTO bookingReqDTO);

	String getComParamDetById(Long cpdId, Long orgId);

	WSResponseDTO getChecklist(WSRequestDTO requestDTO, Long orgId);

	List<ChargeDetailDTO> getApplicableCharges(WSRequestDTO requestDTO, Long vehicleId, Long orgId);
}
