package com.abm.mainet.brms.rest.mrm.service;

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
import com.abm.mainet.rule.mrm.datamodel.MRMRateMaster;

@Service
public class IMRMServiceImpl implements IMRMService {
	
    @Autowired
    private RuleEngineService ruleEngineService;

    @Override
    public WSResponseDTO getServiceCharges(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage("dataModel field within WSRequestDTO dto cannot be null");
            return responseDTO;
        } else {
            responseDTO = findServiceCharge(requestDTO, responseDTO);
        }
        return responseDTO;
    }

    private WSResponseDTO findServiceCharge(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<MRMRateMaster> mrmRateMaster = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, MRMRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
            	MRMRateMaster ruleResult = ruleEngineService.findMRMServiceCharges((MRMRateMaster) model);
                if (ruleResult != null) {
                	mrmRateMaster.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    throw new FrameworkException("No Service Charges found for inserted model" + model);
                }
            }
            responseDTO.setResponseObj(mrmRateMaster);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } else {
            throw new FrameworkException("WsRequestDTO's dataModel field must not contain an empty list");
        }
        return responseDTO;
    }
}
