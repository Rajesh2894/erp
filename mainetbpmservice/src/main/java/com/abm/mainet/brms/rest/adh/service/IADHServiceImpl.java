package com.abm.mainet.brms.rest.adh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.adh.datamodel.ADHRateMaster;

/**
 * @author vishwajeet.kumar
 * @since 22 October 2019
 */

@Service
public class IADHServiceImpl implements IADHService {

    @Autowired
    private RuleEngineService ruleEngineService;

    @Override
    public WSResponseDTO getNewADHApplicationCharges(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage("dataModel field within WSRequestDTO dto cannot be null");
            return responseDTO;
        } else {
            responseDTO = findNewADHApplicationCharge(requestDTO, responseDTO);
        }
        return responseDTO;
    }

    private WSResponseDTO findNewADHApplicationCharge(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<ADHRateMaster> adhRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, ADHRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
                ADHRateMaster ruleResult = ruleEngineService.findNewADHRateMaster((ADHRateMaster) model);
                if (ruleResult != null) {
                    adhRates.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    throw new FrameworkException("No Application Charges found for inserted model" + model);
                }
            }
            responseDTO.setResponseObj(adhRates);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } else {
            throw new FrameworkException("WsRequestDTO's dataModel field must not contain an empty list");
        }
        return responseDTO;
    }

    @Override
    public WSResponseDTO calculateRateForMultipleADHApplicationCharges(WSRequestDTO requestDTO) {

        return null;
    }
}
