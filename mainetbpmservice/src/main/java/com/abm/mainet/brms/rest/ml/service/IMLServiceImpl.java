package com.abm.mainet.brms.rest.ml.service;

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
import com.abm.mainet.rule.ml.datamodel.MLNewTradeLicense;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class IMLServiceImpl implements IMLService {

    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No EMD found for inserted model";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";

    @Autowired
    private RuleEngineService ruleEngineService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.brms.rest.wms.service.IWMSService#calculateEmdAmount(com.abm.mainet.brms.core.dto.WSRequestDTO)
     */
    @Override
    public WSResponseDTO getNewTradeLicenseFee(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        return findTradeLicenseFee(requestDTO, responseDTO);
    }

    public WSResponseDTO findTradeLicenseFee(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<MLNewTradeLicense> mlRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, MLNewTradeLicense.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
                MLNewTradeLicense ruleResult = ruleEngineService.findNewTradeLicenseFee((MLNewTradeLicense) model);
                if (ruleResult != null) {
                    mlRates.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    throw new FrameworkException(NO_CHARGE_FOUND + model);
                }
            }
            responseDTO.setResponseObj(mlRates);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        } else {
            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
        }
        return responseDTO;
    }

}
