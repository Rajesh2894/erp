package com.abm.mainet.brms.rest.rnl.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.rnl.service.RNLService;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.rnl.datamodel.RNLRateMaster;

@Service
public class RNLServiceImpl implements RNLService {

    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No Charge found for inserted model";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";

    @Autowired
    private RuleEngineService ruleEngineService;

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

    public WSResponseDTO findApplicableCharges(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<RNLRateMaster> rnlRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, RNLRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
                RNLRateMaster ruleResult = ruleEngineService.findRnLRateMaster((RNLRateMaster) model);
                if (ruleResult != null) {
                    rnlRates.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    throw new FrameworkException(NO_CHARGE_FOUND + model);
                }
            }
            responseDTO.setResponseObj(rnlRates);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } else {
            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
        }
        return responseDTO;
    }

    @Override
    public WSResponseDTO calculateTaxPercentage(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        return findTaxPercentage(requestDTO, responseDTO);
    }

    private WSResponseDTO findTaxPercentage(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        Object object = CommonMasterUtility.castRequestToDataModel(requestDTO, RNLRateMaster.class);
        if (object != null) {
            RNLRateMaster ruleResult = ruleEngineService.findRnLRateMaster((RNLRateMaster) object);
            if (ruleResult != null) {
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            } else {
                throw new FrameworkException("No Tax found for requsted model" + object);
            }
            responseDTO.setResponseObj(ruleResult);
        } else {
            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
        }

        return responseDTO;
    }

    @Override
    public WSResponseDTO calculateRateForMultipleProperty(@RequestBody WSRequestDTO request) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (request.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        try {
            Map<Long, List<RNLRateMaster>> propRateMap = castRequestToDataModelMapRate(request, RNLRateMaster.class);
            if (propRateMap != null) {
                propRateMap.forEach((key, value) -> {
                    value.forEach(factor -> {
                        ruleEngineService.findRnLRateMaster((RNLRateMaster) factor);

                    });
                });
            }
            responseDTO.setResponseObj(propRateMap);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } catch (FrameworkException ex) {
            throw new FrameworkException(ex);
        }
        return responseDTO;
    }

    @SuppressWarnings("unchecked")
    private Map<Long, List<RNLRateMaster>> castRequestToDataModelMapRate(WSRequestDTO requestDTO,
            Class<?> clazz) {
        Map<Long, List<RNLRateMaster>> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<Long, List<RNLRateMaster>>> typeRef = new TypeReference<Map<Long, List<RNLRateMaster>>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                throw new FrameworkException(e);
            }
        }
        return dataModel;
    }

}
