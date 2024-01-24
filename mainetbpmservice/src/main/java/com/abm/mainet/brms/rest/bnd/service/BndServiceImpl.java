package com.abm.mainet.brms.rest.bnd.service;

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
import com.abm.mainet.rule.bnd.datamodel.BndRateMaster;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class BndServiceImpl implements IBndService{
	
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";
    private static final String NO_BND_CHARGE_FOUND = "No Bnd found for inserted model";

    @Autowired
    private RuleEngineService ruleEngineService;
	


	@Override
	public WSResponseDTO calculateBndCharges(WSRequestDTO requestDTO) {
		 WSResponseDTO responseDTO = new WSResponseDTO();
	        if (requestDTO.getDataModel() == null) {
	            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
	            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
	            return responseDTO;
	        }
	        return findBndCharges(requestDTO, responseDTO);
	}
	
	 private WSResponseDTO findBndCharges(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
	        List<BndRateMaster> bndRateMaster = new ArrayList<>();
	     
	        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, BndRateMaster.class);
	        if (list != null && !list.isEmpty()) {
	            for (Object model : list) {
	            	BndRateMaster ruleResult = ruleEngineService.findBndCharges((BndRateMaster) model);
	                if (ruleResult != null) {
	                    bndRateMaster.add(ruleResult);
	                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
	                } else {
	                    throw new FrameworkException(NO_BND_CHARGE_FOUND + model);
	                }
	            }
	            responseDTO.setResponseObj(bndRateMaster);
	            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
	        } else {
	            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
	        }
	        return responseDTO;
	    }

}
