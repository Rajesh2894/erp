/**
 * 
 */
package com.abm.mainet.brms.rest.swm.service;

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
import com.abm.mainet.rule.swm.datamodel.SWMRateMaster;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class SWMServiceImpl implements ISWMService {

    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No Charge found for inserted model";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";

    @Autowired
    private RuleEngineService ruleEngineService;
	
	/* (non-Javadoc)
	 * @see com.abm.mainet.brms.rest.swm.service.ISWMService#calculateServiceCharges(com.abm.mainet.brms.core.dto.WSRequestDTO)
	 */
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
        List<SWMRateMaster> rnlRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, SWMRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
            	SWMRateMaster ruleResult = ruleEngineService.findSWMRateMaster((SWMRateMaster) model);
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
}
