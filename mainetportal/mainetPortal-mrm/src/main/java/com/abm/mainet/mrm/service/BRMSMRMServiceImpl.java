package com.abm.mainet.mrm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
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
import com.abm.mainet.mrm.dto.MRMRateMaster;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BRMSMRMServiceImpl implements IBRMSMRMService {


    

    public WSResponseDTO getApplicableTaxes(MRMRateMaster mrmRateMaster) {
    	final WSRequestDTO dto = new WSRequestDTO();
    	mrmRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL, NewWaterServiceConstants.CAA).getLookUpId()));
        dto.setDataModel(mrmRateMaster);
        return JersyCall.callServiceBRMSRestClient(dto, ServiceEndpoints.BRMS_MRM_URL.MRM_DEPENDENT_PARAM_URL);
    }
    

    public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {
        Object dataModel = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for (final Object object : list) {
                    MRMRateMaster responseMap = (MRMRateMaster) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
            }
        } catch (final IOException e) {
        }
        return dataModelList;

    }

    @SuppressWarnings("unchecked")
	@Override
    public List<ChargeDetailDTO> getApplicableCharges(List<MRMRateMaster> requiredCHarges) {
        final WSRequestDTO request = new WSRequestDTO();
        request.setDataModel(requiredCHarges);
        WSResponseDTO response = null;
        try {
            response = JersyCall.callServiceBRMSRestClient(request,
                    ServiceEndpoints.BRMS_MRM_URL.MRM_SERVICE_CHARGE_URL);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> charges = JersyCall.castResponse(response, ChargeDetailDTO.class);

                return (List<ChargeDetailDTO>) charges;
            } else {
                throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
            }
        } catch (Exception exception) {
            throw new FrameworkException("Exception while fatching service charges" + response.getErrorMessage());
        }
    }

    @Override
    public WSResponseDTO getApplicableTaxes(WSRequestDTO taxReqDTO) {
        WSResponseDTO applicableTaxes = null;
        try {
            applicableTaxes = JersyCall.callServiceBRMSRestClient(taxReqDTO,
                    ServiceEndpoints.BRMS_MRM_URL.MRM_DEPENDENT_PARAM_URL);
        } catch (Exception exception) {
            throw new FrameworkException("Exception while fetching getApplicableTaxes from service call:", exception);
        }

        return applicableTaxes;
    }

	

}
