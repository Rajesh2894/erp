package com.abm.mainet.water.service;

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
import com.abm.mainet.water.dto.WaterRateMaster;

@Service
public class WaterBRMSServiceImpl implements IWaterBRMSService {

    private static final long serialVersionUID = -7846188446943642160L;

    @Override
    public WSResponseDTO getApplicableTaxes(final WaterRateMaster rate, final long orgid, final String serviceShortCode) {
        final WSRequestDTO dto = new WSRequestDTO();
        rate.setOrgId(orgid);
        rate.setServiceCode(serviceShortCode);
        rate.setChargeApplicableAt(Long.toString(CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL, NewWaterServiceConstants.CAA)
                .getLookUpId()));
        dto.setDataModel(rate);
        // return JersyCall.callServiceBRMSRestClient(dto, BRMS_WATER_URL.DEPENDENT_PARAM_URI);
        return JersyCall.callServiceBRMSRestClient(dto, ServiceEndpoints.BRMS_WATER_URL.WATER_DEPENDENT_PARAM_URL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChargeDetailDTO> getApplicableCharges(final List<WaterRateMaster> requiredCHarges) {
        final WSRequestDTO request = new WSRequestDTO();
        request.setDataModel(requiredCHarges);
        final WSResponseDTO response = JersyCall.callServiceBRMSRestClient(request,
                ServiceEndpoints.BRMS_WATER_URL.WATER_SERVICE_CHARGE_URL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<?> charges = JersyCall.castResponse(response, ChargeDetailDTO.class);
            // List<ChargeDetailDTO> detailDTOs =
            /*
             * final List<WaterRateMaster> finalRateMaster = new ArrayList<>(); for (final Object rate : charges) { final
             * WaterRateMaster masterRate = (WaterRateMaster) rate; finalRateMaster.add(masterRate); } final ChargeDetailDTO
             * chargedto = new ChargeDetailDTO();
             */
            // final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
            /*
             * for (final WaterRateMaster rateCharge : finalRateMaster) { chargedto.setChargeCode(rateCharge.getTaxId());
             * chargedto.setChargeAmount(rateCharge.getFlatRate()); chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
             * chargedto.setChargeDescReg(rateCharge.getChargeDescReg()); detailDTOs.add(chargedto); }
             */

            return (List<ChargeDetailDTO>) charges;
        } else {
            throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
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
