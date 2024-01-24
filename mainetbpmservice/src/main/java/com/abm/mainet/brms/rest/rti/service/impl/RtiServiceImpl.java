package com.abm.mainet.brms.rest.rti.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.rti.service.IRtiService;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.rti.datamodel.RtiRateMaster;

@Service
public class RtiServiceImpl implements IRtiService {
    @Autowired
    private RuleEngineService ruleEngineService;

    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No charge Found";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "DataModel Must not be empty";

    @Override
    public WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        return findApplicableCharges(requestDTO, responseDTO);
    }

    private WSResponseDTO findApplicableCharges(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<RtiRateMaster> rtiRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, RtiRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
                RtiRateMaster ruleResult = ruleEngineService.findRtiRateMaster((RtiRateMaster) model);
                if (ruleResult != null) {
                    rtiRates.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    throw new FrameworkException(NO_CHARGE_FOUND);
                }
            }
            responseDTO.setResponseObj(rtiRates);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } else {
            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
        }

        return responseDTO;
    }
}
