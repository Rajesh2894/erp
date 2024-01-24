/**
 * 
 */
package com.abm.mainet.swm.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class WasteCollectorServiceImpl implements IWasteCollectorService {

	private static final Logger logger = Logger.getLogger(WasteCollectorServiceImpl.class);
	private static final String ERROR = "Error Occurred during cast responseEntity received while BRMS call is success!";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IWasteCollectorService#saveWasteCollector(com.abm.
	 * mainet.swm.dto.CollectorReqDTO)
	 */
	@Override
	public CollectorResDTO saveWasteCollector(CollectorReqDTO bookingReqDTO) {
		final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(bookingReqDTO,
				ServiceEndpoints.SWMWebServiceURL.SAVE_WASTE_COLLECTOR);
		CollectorResDTO responseDTO = null;
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			try {
				responseDTO = JersyCall.castResponse(responseEntity, CollectorResDTO.class);
			} catch (IOException exp) {
				logger.error(ERROR, exp);
			}
		}
		return responseDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IWasteCollectorService#getComParamDetById(java.
	 * lang.Long, java.lang.Long)
	 */
	@Override
	public String getComParamDetById(Long cpdId, Long orgId) {
		String uri = ServiceEndpoints.SWMWebServiceURL.GET_COMPARAMDET_BY_ID + MainetConstants.operator.FORWARD_SLACE
				+ orgId + MainetConstants.operator.FORWARD_SLACE + cpdId;
		String orgName = null;
		final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(null, uri);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			try {
				orgName = JersyCall.castResponse(responseEntity, String.class);
			} catch (IOException exp) {
				logger.error(ERROR, exp);
			}
		}
		return orgName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IWasteCollectorService#getChecklist(com.abm.mainet
	 * .integration.ws.dto.WSRequestDTO, java.lang.Long)
	 */
	@Override
	public WSResponseDTO getChecklist(WSRequestDTO requestDTO, Long orgId) {
		String uri = ServiceEndpoints.SWMWebServiceURL.GET_CHECKLIST + MainetConstants.operator.FORWARD_SLACE + orgId;
		final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestDTO, uri);
		logger.info("responseEntity after getChecklist call is " + responseEntity.getStatusCode() + " : "
				+ responseEntity.getBody());
		WSResponseDTO responseDTO = null;
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			try {
				responseDTO = JersyCall.castResponse(responseEntity, WSResponseDTO.class);
			} catch (IOException exp) {
				logger.error(ERROR, exp);
			}
		}
		return responseDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.swm.service.IWasteCollectorService#getApplicableCharges(com.
	 * abm.mainet.integration.ws.dto.WSRequestDTO, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ChargeDetailDTO> getApplicableCharges(WSRequestDTO requestDTO, Long vehicleId, Long orgId) {
		String uri = ServiceEndpoints.SWMWebServiceURL.GET_APPLICABLE_CHARGES + MainetConstants.operator.FORWARD_SLACE
				+ orgId + MainetConstants.operator.FORWARD_SLACE + vehicleId;
		List<ChargeDetailDTO> chargeList = null;
		final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestDTO, uri);
		logger.info("responseEntity after getApplicableCharges call is " + responseEntity.getStatusCode() + " : "
				+ responseEntity.getBody());
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			try {
				WSResponseDTO responseDTO = JersyCall.castResponse(responseEntity, WSResponseDTO.class);
				chargeList = responseDTO.getCharges();
			} catch (IOException exp) {
				logger.error(ERROR, exp);
			}
		}
		return chargeList;
	}

}
