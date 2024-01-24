package com.abm.mainet.brms.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.service.ICommonService;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.adh.datamodel.ADHRateMaster;
import com.abm.mainet.rule.bnd.datamodel.BndRateMaster;
import com.abm.mainet.rule.datamodel.CheckListModel;
import com.abm.mainet.rule.mrm.datamodel.MRMRateMaster;
import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;
import com.abm.mainet.rule.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rule.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rule.swm.datamodel.SWMRateMaster;
import com.abm.mainet.rule.water.datamodel.Consumption;
import com.abm.mainet.rule.water.datamodel.NoOfDays;
import com.abm.mainet.rule.water.datamodel.WaterRateMaster;
import com.abm.mainet.rule.water.datamodel.WaterTaxCalculation;
import com.abm.mainet.rule.wms.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.rule.additionalservices.datamodel.AdditionalServicesModel;
import com.abm.mainet.rule.bpmsratemaster.datamodel.BPMSRateMaster;

/**
 * 
 * @author Vivek.Kumar
 * @since 30 May 2016
 */
@Service
// @Component
public class CommonServiceImpl implements ICommonService {

    /**
     * LOGGER to log the error if something goes wrong.
     */
    private static final Logger LOGGER = Logger.getLogger(CommonServiceImpl.class);
    private static final String SERVICE_CODE_CANT_BE_NULL = "service code can not be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId can not be zero(0)";
    private static final String DATA_MODEL_CANT_BE_NULL = "WsRequestDTO field named dataModel cannot be null.";
    private static final String NO_CHECKLIST_FOUND = "No checklist found!";
    private static final String MODEL_NAME_CANT_BE_NULL = "Field  modelName cannot be null or empty";
    private static final String PIPE = "|";
    private static final String SPLIT_PIPE = "\\|";
    private static final String INVALID_MODEL_OR_IMPROPER_PIPE_DATA = "Field name modelName contain invalid Model name or improper pipe data passed";
    private static final String INITIALIZE_MODEL_ERROR = "Problem occurred while processing to initialize model";

    @Autowired
    private RuleEngineService ruleEngineService;

    private static Map<String, Object> modelMap;

    /**
     * purpose of this to initialize all data model at bean creation time
     */
    static {
        modelMap = initializeAllModelAtStartUp();
    }

    @Override
    public WSResponseDTO findApplicableCheckList(WSRequestDTO requestDTO) {
        LOGGER.info("In ChecklIstGroup Method");
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() != null) {
            CheckListModel checkListModel = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
                    CheckListModel.class);
            validateDataModel(responseDTO, checkListModel);
            if (responseDTO.getWsStatus().equals(MainetConstants.Status.SUCCESS)) {
                callBrmsForChecklist(responseDTO, checkListModel);
            }
        } else {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATA_MODEL_CANT_BE_NULL);
        }
        return responseDTO;
    }

    /**
     * validating ChecklistModel data
     * @param responseDTO
     * 
     * @param checkListModel
     * @return
     */
    private WSResponseDTO validateDataModel(WSResponseDTO response, CheckListModel checkListModel) {
        String errorMsg = "";
        if (checkListModel.getServiceCode() == null
                || checkListModel.getServiceCode().isEmpty()) {
            errorMsg = SERVICE_CODE_CANT_BE_NULL + ",";
        }
        if (checkListModel.getOrgId() == 0l) {
            errorMsg = ORG_ID_CANT_BE_ZERO;
        }
        if (!errorMsg.isEmpty()) {
            response.setWsStatus(MainetConstants.Status.FAIL);
            response.setErrorMessage(errorMsg);
        } else {
            response.setWsStatus(MainetConstants.Status.SUCCESS);
        }
        return response;
    }

    /**
     * 
     * @param responseDTO2
     * @return
     */
    private WSResponseDTO callBrmsForChecklist(WSResponseDTO responseDTO, CheckListModel checkListModel) {

        /*
         * as discussion of the meeting held on 26th March 2018,Currently "checklist verification applicable" is set to applicable
         * then checklist is displayed on UI for any service. It was discussed that for every module/service, system will call
         * BRMS to get the checklist without having the check for "checklist verification applicable" . If checklist is received
         * from BRMS (means it is defined in BRMS) then Checklist section should be displayed on UI. otherwise this section should
         * not be appear on UI If "checklist verification applicable" is equal to applicable then checklist verification task will
         * generated. For services like self-assessment (new, change, No change)of property module,
         * "checklist verification applicable" will always be set to Not applicable in service rule definition.
         */
        LOGGER.info("Before Rule engine fire rule");
        Object ruleResult = ruleEngineService.findCheckListGroup(checkListModel);
        if (ruleResult.equals(MainetConstants.StatusCode.INTERNAL_SERVER_ERROR)) {
            responseDTO.setErrorMessage(NO_CHECKLIST_FOUND);
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
        } else {
            responseDTO.setDocumentGroup(ruleResult);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        }
        return responseDTO;
    }

    @Override
    public WSResponseDTO returnInitializedModels(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            if (requestDTO.getModelName() == null
                    || requestDTO.getModelName().isEmpty()) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(MODEL_NAME_CANT_BE_NULL);
            } else {
                boolean isValidModelName = isValidModelName(
                        requestDTO.getModelName());
                List<Object> modelList = new ArrayList<>();
                if (isValidModelName && requestDTO.getModelName().contains(PIPE)) {
                    String[] models = requestDTO.getModelName().split(SPLIT_PIPE);
                    returnInitializedModelList(models, responseDTO);
                } else if (isValidModelName) {
                    returnInitializedModel(requestDTO.getModelName(),
                            responseDTO, modelList);
                } else {
                    responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                    responseDTO.setErrorMessage(INVALID_MODEL_OR_IMPROPER_PIPE_DATA);
                }
            }
        } catch (FrameworkException ex) {
            throw new FrameworkException(INITIALIZE_MODEL_ERROR, ex);
        }

        return responseDTO;
    }

    /**
     * 
     * @param models
     * @param responseDTO
     * @return
     */
    private WSResponseDTO returnInitializedModelList(String[] models,
            WSResponseDTO responseDTO) {
        List<Object> modelList = new ArrayList<>();
        for (String model : models) {
            returnInitializedModel(model, responseDTO, modelList);
            if (!responseDTO.getWsStatus().equals(MainetConstants.Status.SUCCESS)) {
                break;
            }
        }

        return responseDTO;
    }

    /**
     * initialize requested model
     * 
     * @param modelName
     * @param responseDTO
     * @return
     */
    private WSResponseDTO returnInitializedModel(String modelName, WSResponseDTO responseDTO, List<Object> modelList) {
        Object dataModel = getModelMap().get(modelName.toUpperCase());
        if (dataModel == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage("No model found for modelName=[" + modelName + "]");
        } else {
            modelList.add(getModelMap().get(modelName.toUpperCase()));
            responseDTO.setResponseObj(modelList);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
        }
        return responseDTO;
    }

    /**
     * this method will initialize all data model at application startup time and
     * @return
     */
    private static Map<String, Object> initializeAllModelAtStartUp() {

        Map<String, Object> modelMap = new HashMap<>();

        modelMap.put(MainetConstants.Rules.Model.CHECKLIST_MODEL, new CheckListModel());
        modelMap.put(MainetConstants.Rules.Model.CONSUMPTION, new Consumption());
        modelMap.put(MainetConstants.Rules.Model.NO_OF_DAYS, new NoOfDays());
        modelMap.put(MainetConstants.Rules.Model.WATER_RATE_MASTER, new WaterRateMaster());
        modelMap.put(MainetConstants.Rules.Model.WATER_TAX_CALCULATION, new WaterTaxCalculation());
        modelMap.put(MainetConstants.Rules.Model.RNL_RATE_MASTER, new RNLRateMaster());
        modelMap.put(MainetConstants.Rules.Model.FACTOR_MASTER_MODEL, new FactorMasterModel());
        modelMap.put(MainetConstants.Rules.Model.ALV_MASTER_MODEL, new ALVMasterModel());
        modelMap.put(MainetConstants.Rules.Model.PROPERTY_RATE_MASTER_MODEL, new PropertyRateMasterModel());
        modelMap.put(MainetConstants.Rules.Model.PROPERTY_TAX_DATA_MODEL, new PropertyTaxDataModel());
        modelMap.put(MainetConstants.Rules.Model.RTI_RATE_MASTER, new RtiRateMaster());
        modelMap.put(MainetConstants.Rules.Model.SWM_RATE_MASTER, new SWMRateMaster());
        modelMap.put(MainetConstants.Rules.Model.ROAD_CUTTING_RATE_MASTER,
                new RoadCuttingRateMaster());
        modelMap.put(MainetConstants.Rules.Model.ADH_RATE_MASTER, new ADHRateMaster());
        modelMap.put(MainetConstants.Rules.Model.MRM_RATE_MASTER, new MRMRateMaster());
        modelMap.put(MainetConstants.Rules.Model.BND_RATE_MASTER, new BndRateMaster());
        modelMap.put(MainetConstants.Rules.Model.ADDITIONAL_SERVICES_MODEL, new AdditionalServicesModel());
        modelMap.put(MainetConstants.Rules.Model.BPMS_RATER_MASTER, new BPMSRateMaster());

        return modelMap;
    }

    /**
     * method to check, passed model name are proper or not
     * 
     * @param modelName
     * @return true if modelName does't contains any special character otherwise false
     */
    private boolean isValidModelName(String modelName) {

        Matcher matcher = Pattern.compile(MainetConstants.Regex.SPECIAL_CHAR).matcher(modelName);

        return matcher.find() == true ? false : true;
    }

    public static Map<String, Object> getModelMap() {
        return modelMap;
    }

    public static void setModelMap(Map<String, Object> modelMap) {
        CommonServiceImpl.modelMap = modelMap;
    }

}
