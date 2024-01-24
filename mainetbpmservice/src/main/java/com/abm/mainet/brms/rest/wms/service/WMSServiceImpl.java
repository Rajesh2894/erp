package com.abm.mainet.brms.rest.wms.service;

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
import com.abm.mainet.rule.wms.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.rule.wms.datamodel.WMSRateMaster;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class WMSServiceImpl implements IWMSService{
	
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No EMD found for inserted model";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";
    private static final String NO_ROADCUTTIN_CHARGE_FOUND = "No RoadCuttingRateMaster found for inserted model";

    @Autowired
    private RuleEngineService ruleEngineService;
	
	/*
	 * (non-Javadoc)
	 * @see com.abm.mainet.brms.rest.wms.service.IWMSService#calculateEmdAmount(com.abm.mainet.brms.core.dto.WSRequestDTO)
	 */
	@Override
	public WSResponseDTO calculateEmdAmount(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        return findEmdAmount(requestDTO, responseDTO);
    }

    public WSResponseDTO findEmdAmount(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<WMSRateMaster> rnlRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, WMSRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
            	WMSRateMaster ruleResult = ruleEngineService.findWMSEmdMaster((WMSRateMaster) model);
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
	public WSResponseDTO calculateRoadCuttingCharges(WSRequestDTO requestDTO) {
		 WSResponseDTO responseDTO = new WSResponseDTO();
	        if (requestDTO.getDataModel() == null) {
	            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
	            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
	            return responseDTO;
	        }
	        return findRoadCuttingCharges(requestDTO, responseDTO);
	}
	
	 private WSResponseDTO findRoadCuttingCharges(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
	        List<RoadCuttingRateMaster> rnlRates = new ArrayList<>();
	        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, RoadCuttingRateMaster.class);
	        if (list != null && !list.isEmpty()) {
	            for (Object model : list) {
	            	RoadCuttingRateMaster ruleResult = ruleEngineService.findRoadCuttingRateMaster((RoadCuttingRateMaster) model);
	                if (ruleResult != null) {
	                    rnlRates.add(ruleResult);
	                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
	                } else {
	                    throw new FrameworkException(NO_ROADCUTTIN_CHARGE_FOUND + model);
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
