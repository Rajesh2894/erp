/**
 * 
 */
package com.abm.mainet.brms.rest.water.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.utility.CommonMasterUtility;
import com.abm.mainet.brms.rest.service.RuleEngineService;
import com.abm.mainet.brms.rest.water.service.WaterService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.water.datamodel.Consumption;
import com.abm.mainet.rule.water.datamodel.NoOfDays;
import com.abm.mainet.rule.water.datamodel.WaterRateMaster;
import com.abm.mainet.rule.water.datamodel.WaterTaxCalculation;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author Vivek.Kumar
 *
 */
@Service
public class WaterServiceImpl implements WaterService {

    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String NO_CHARGE_FOUND = "No Charge found for inserted model";
    private static final String DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST = "WsRequestDTO's dataModel field must not contain an empty list";
    private static final String NO_WATER_CONSUMPTION_FOUND = "No water consumption found for provided input model";
    private static final String NO_OF_DAYS_NOT_FOUND = "NoOfDays not found for provided input model";
    private static final String NO_WATER_RATE_FOUND = "No Water Rate found for provided input model";
    private static final String NO_WATER_TAX_FOUND = "No Water Tax found for provided input model";

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterServiceImpl.class);

    @Autowired
    private RuleEngineService ruleEngineService;

    @Override
    public WSResponseDTO calculateServiceCharges(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();

        // check data model is there or not.
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        }
        return findApplicableCharges(requestDTO, responseDTO);
    }

    // find applicable charges
    public WSResponseDTO findApplicableCharges(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        List<WaterRateMaster> waterRates = new ArrayList<>();
        List<Object> list = CommonMasterUtility.castRequestToDataModels(requestDTO, WaterRateMaster.class);
        if (list != null && !list.isEmpty()) {
            for (Object model : list) {
                WaterRateMaster ruleResult = ruleEngineService.findWaterRateMaster((WaterRateMaster) model);
                if (ruleResult != null) {
                    waterRates.add(ruleResult);
                    responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                } else {
                    LOGGER.error(NO_CHARGE_FOUND + model);
                    throw new FrameworkException(NO_CHARGE_FOUND);
                }
            }
            responseDTO.setResponseObj(waterRates);
        } else {
            throw new FrameworkException(DATAMODEL_MUST_NOT_CONTAIN_EMPTY_LIST);
        }
        return responseDTO;
    }

    @Override
    public WSResponseDTO findWaterConsumption(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        // check data model is there or not.
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            Consumption consumption = (Consumption) CommonMasterUtility.castRequestToDataModel(requestDTO, Consumption.class);
            String ruleResult = ruleEngineService.findWaterConsumption(consumption);
            // if rule result found than set status as success and return response DTO with rule result
            if (ruleResult.equals(MainetConstants.StatusCode.INTERNAL_SERVER_ERROR)) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_WATER_CONSUMPTION_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(Double.parseDouble(ruleResult));
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }
        }
    }

    @Override
    public WSResponseDTO findNoOfDays(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            NoOfDays noOfDays = (NoOfDays) CommonMasterUtility.castRequestToDataModel(requestDTO, NoOfDays.class);
            String ruleResult = ruleEngineService.findNoOfDays(noOfDays);
            // if rule result found than set status as success and return response DTO with rule result
            if (ruleResult.equals(MainetConstants.StatusCode.INTERNAL_SERVER_ERROR)) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_OF_DAYS_NOT_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(Long.parseLong(ruleResult));
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }
        }

    }

    @Override
    public WSResponseDTO findWaterRate(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            WaterRateMaster rateMaster = (WaterRateMaster) CommonMasterUtility.castRequestToDataModel(requestDTO,
                    WaterRateMaster.class);
            WaterRateMaster ruleResult = ruleEngineService.findWaterRateMaster(rateMaster);
            if (ruleResult == null) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_WATER_RATE_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(ruleResult);
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }
        }
    }

    @Override
    public WSResponseDTO findWaterRateBill(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            Map<Long, WaterRateMaster> rateMasterResult = new HashMap<>(0);

            Map<Long, WaterRateMaster> rateMaster = CommonMasterUtility
                    .castRequestToDataModelMapWaterRate(requestDTO, Map.class);
            rateMaster.forEach((taxId, rates) -> {
                rateMasterResult.put(taxId, ruleEngineService.findWaterRateMaster(rates));
            });
            if (rateMasterResult == null || rateMasterResult.isEmpty()) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_WATER_RATE_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(rateMasterResult);
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }
        }
    }

    @Override
    public WSResponseDTO findWaterTax(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            WaterTaxCalculation taxCalculation = (WaterTaxCalculation) CommonMasterUtility.castRequestToDataModel(requestDTO,
                    WaterTaxCalculation.class);
            WaterTaxCalculation ruleResult = ruleEngineService.findWaterTaxMaster(taxCalculation);
            if (ruleResult == null) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_WATER_TAX_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(ruleResult);
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }

        }
    }

    @Override
    public WSResponseDTO findWaterTaxBill(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            Map<Long, WaterTaxCalculation> taxCalculationResult = new HashMap<>(0);
            Map<Long, WaterTaxCalculation> taxCalculation = CommonMasterUtility
                    .castRequestToDataModelMapWaterTax(requestDTO, Map.class);
            AtomicDouble demandAmount = new AtomicDouble(0);
            taxCalculation.forEach((taxId, taxCal) -> {
                taxCal.setGeneralTax(demandAmount.doubleValue());
                WaterTaxCalculation taxResult = ruleEngineService.findWaterTaxMaster(taxCal);
                if (MainetConstants.TaxCategory.DEMAND.equals(taxCal.getTaxCategory())) {
                    demandAmount.addAndGet(taxResult.getTax());
                }
                taxCalculationResult.put(taxId, taxResult);
            });
            if (taxCalculationResult == null || taxCalculationResult.isEmpty()) {
                responseDTO.setWsStatus(MainetConstants.Status.FAIL);
                responseDTO.setErrorMessage(NO_WATER_TAX_FOUND);
                return responseDTO;
            } else {
                responseDTO.setResponseObj(taxCalculationResult);
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                return responseDTO;
            }

        }
    }

    @Override
    public WSResponseDTO findWaterConsumptionAndDays(WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        if (requestDTO.getDataModel() == null) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            return responseDTO;
        } else {
            List<Object> responseList = new ArrayList<>();
            Consumption consumption = (Consumption) CommonMasterUtility.castRequestToDataModelByPosition(requestDTO,
                    Consumption.class, MainetConstants.INDEX_ZERO);
            findConsumption(consumption, responseList, responseDTO);
            if (responseDTO.getWsStatus().equals(MainetConstants.Status.SUCCESS)) {
                NoOfDays noOfDays = (NoOfDays) CommonMasterUtility.castRequestToDataModelByPosition(requestDTO, NoOfDays.class,
                        MainetConstants.INDEX_ONE);
                findNoOfDays(noOfDays, responseList, responseDTO);
            }
        }
        return responseDTO;
    }

    private WSResponseDTO findNoOfDays(NoOfDays noOfDays, List<Object> responseList, WSResponseDTO responseDTO) {

        String ruleResult = ruleEngineService.findNoOfDays(noOfDays);
        if (ruleResult.equals(MainetConstants.StatusCode.INTERNAL_SERVER_ERROR)) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(NO_OF_DAYS_NOT_FOUND);
            return responseDTO;
        } else {
            responseList.add(Long.parseLong(ruleResult));
            responseDTO.setResponseObj(responseList);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            return responseDTO;
        }

    }

    private WSResponseDTO findConsumption(Consumption consumption, List<Object> responseList, WSResponseDTO responseDTO) {
        String ruleResult = ruleEngineService.findWaterConsumption(consumption);
        if (ruleResult.equals(MainetConstants.StatusCode.INTERNAL_SERVER_ERROR)) {
            responseDTO.setWsStatus(MainetConstants.Status.FAIL);
            responseDTO.setErrorMessage(NO_WATER_CONSUMPTION_FOUND);
            return responseDTO;
        } else {
            responseList.add(Double.parseDouble(ruleResult));
            responseDTO.setResponseObj(responseList);
            responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
            return responseDTO;
        }

    }

}
