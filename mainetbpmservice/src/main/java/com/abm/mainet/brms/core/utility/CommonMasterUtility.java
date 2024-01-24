/**
 * 
 */
package com.abm.mainet.brms.core.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abm.mainet.brms.core.dto.WSRequestDTO;
import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;
import com.abm.mainet.rule.water.datamodel.WaterRateMaster;
import com.abm.mainet.rule.water.datamodel.WaterTaxCalculation;

public class CommonMasterUtility {

    private static final Logger logger = LoggerFactory.getLogger(CommonMasterUtility.class);

    /**
     * use this method to cast single dataModel from request received
     * @param requestDTO
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object castRequestToDataModel(WSRequestDTO requestDTO, Class<?> clazz) {

        Object dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();

            String jsonString = new JSONObject(requestMap).toString();
            try {
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }

        }

        return dataModel;

    }

    @SuppressWarnings("unchecked")
    public static Map<String, List<FactorMasterModel>> castRequestToDataModelMapRate(WSRequestDTO requestDTO,
            Class<?> clazz) {
        Map<String, List<FactorMasterModel>> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, List<FactorMasterModel>>> typeRef = new TypeReference<Map<String, List<FactorMasterModel>>>() {
                };
                dataModel = new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, ALVMasterModel> castRequestToALVapModel(WSRequestDTO requestDTO) {
        Map<String, ALVMasterModel> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, ALVMasterModel>> typeRef = new TypeReference<Map<String, ALVMasterModel>>() {
                };
                dataModel = (Map<String, ALVMasterModel>) new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Map<Date, List<PropertyRateMasterModel>>> castRequestToTaxModel(WSRequestDTO requestDTO) {
        Map<String, Map<Date, List<PropertyRateMasterModel>>> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>> typeRef = new TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>>() {
                };
                dataModel = (Map<String, Map<Date, List<PropertyRateMasterModel>>>) new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, PropertyTaxDataModel> castRequestToSddrRate(WSRequestDTO requestDTO) {
        Map<String, PropertyTaxDataModel> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<String, Object> requestMap = (LinkedHashMap<String, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, PropertyTaxDataModel>> typeRef = new TypeReference<Map<String, PropertyTaxDataModel>>() {
                };
                dataModel = (Map<String, PropertyTaxDataModel>) new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<Date, List<PropertyRateMasterModel>> castRequestToTaxModelPropertyLevel(WSRequestDTO requestDTO) {
        Map<Date, List<PropertyRateMasterModel>> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<Date, List<PropertyRateMasterModel>>> typeRef = new TypeReference<Map<Date, List<PropertyRateMasterModel>>>() {
                };
                dataModel = (Map<Date, List<PropertyRateMasterModel>>) new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Map<Date, List<PropertyRateMasterModel>>> castRequestToTaxModel(WSResponseDTO response) {
        Map<String, Map<Date, List<PropertyRateMasterModel>>> dataModel = null;
        if (response.getResponseObj() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) response.getResponseObj();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>> typeRef = new TypeReference<Map<String, Map<Date, List<PropertyRateMasterModel>>>>() {
                };
                dataModel = (Map<String, Map<Date, List<PropertyRateMasterModel>>>) new ObjectMapper().readValue(jsonString,
                        typeRef);
            } catch (IOException e) {
            }
        }
        return dataModel;
    }

    /**
     * use this method to cast models of same type from request received
     * @param request
     * @param clazz :pass destination data model class to cast
     * @return list of passed data model to cast
     */

    @SuppressWarnings("unchecked")
    public static List<Object> castRequestToDataModels(WSRequestDTO request, Class<?> clazz) {
        Object dataModel = null;
        LinkedHashMap<Long, Object> requestMap = null;
        List<Object> dataModelList = new ArrayList<>();
        try {
            List<?> list = (List<?>) request.getDataModel();
            for (Object object : list) {
                requestMap = (LinkedHashMap<Long, Object>) object;
                String jsonString = new JSONObject(requestMap).toString();
                dataModel = new ObjectMapper().readValue(jsonString,
                        clazz);
                dataModelList.add(dataModel);
            }
        } catch (IOException e) {
            logger.error("Error while casting data Model:", e);
        }
        return dataModelList;
    }

    /**
     * use this method if WSRequestDTO's field dataModel hold different type of Object list
     * @param requestDTO
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object castRequestToDataModelByPosition(WSRequestDTO requestDTO,
            Class<?> clazz, int position) {
        Object dataModel = null;
        if (requestDTO.getDataModel() != null) {
            List<?> list = (List<?>) requestDTO.getDataModel();
            if (list.get(position) != null) {
                LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) list.get(position);
                String jsonString = new JSONObject(requestMap).toString();
                try {
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                } catch (IOException e) {
                    logger.error("Error while casting model from WSRequestDTO", e);
                }
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<Long, WaterRateMaster> castRequestToDataModelMapWaterRate(WSRequestDTO requestDTO, Class<?> clazz) {
        Map<Long, WaterRateMaster> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<HashMap<Long, WaterRateMaster>> typeRef = new TypeReference<HashMap<Long, WaterRateMaster>>() {
                };
                dataModel = (Map<Long, WaterRateMaster>) new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    public static Map<Long, WaterTaxCalculation> castRequestToDataModelMapWaterTax(WSRequestDTO requestDTO, Class<?> clazz) {
        Map<Long, WaterTaxCalculation> dataModel = null;
        if (requestDTO.getDataModel() != null) {
            LinkedHashMap<Long, Object> requestMap = (LinkedHashMap<Long, Object>) requestDTO.getDataModel();
            String jsonString = new JSONObject(requestMap).toString();
            try {
                TypeReference<LinkedHashMap<Long, WaterTaxCalculation>> typeRef = new TypeReference<LinkedHashMap<Long, WaterTaxCalculation>>() {
                };
                dataModel = (Map<Long, WaterTaxCalculation>) new ObjectMapper().readValue(jsonString, typeRef);
            } catch (IOException e) {
                logger.error("Error while casting model from WSRequestDTO", e);
            }
        }
        return dataModel;
    }

}
