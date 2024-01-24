package com.abm.mainet.rts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.NewWaterServiceConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.ui.model.WaterRateMaster;

@Service
public class RtsBrmsServiceImpl implements RtsBrmsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799670674951538979L;

	@Override
	public WSResponseDTO getApplicableTaxes(final WaterRateMaster rate, final long orgid,
			final String serviceShortCode) {
		final WSRequestDTO dto = new WSRequestDTO();
		rate.setOrgId(orgid);
		rate.setServiceCode(serviceShortCode);
		rate.setChargeApplicableAt(Long.toString(CommonMasterUtility
				.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL, NewWaterServiceConstants.CAA)
				.getLookUpId()));
		dto.setDataModel(rate);
		// return JersyCall.callServiceBRMSRestClient(dto,
		// BRMS_WATER_URL.DEPENDENT_PARAM_URI);
		return JersyCall.callServiceBRMSRestClient(dto, ServiceEndpoints.RTS.DEPENDENT_PARAMS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MediaChargeAmountDTO> getApplicableCharges(final List<WaterRateMaster> requiredCHarges) {
		final WSRequestDTO request = new WSRequestDTO();
		request.setDataModel(requiredCHarges);
		final WSResponseDTO response = JersyCall.callServiceBRMSRestClient(request,
				ServiceEndpoints.RTS.SERVICE_CHARGE);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<?> charges = JersyCall.castResponse(response, MediaChargeAmountDTO.class);

			return (List<MediaChargeAmountDTO>) charges;
		} else {
			final List<?> charges = JersyCall.castResponse(response, MediaChargeAmountDTO.class);

			return (List<MediaChargeAmountDTO>) charges;
		}
	}

	@Override
	public double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

}
