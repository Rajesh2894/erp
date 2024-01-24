package com.abm.mainet.property.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.PropertyInputDto;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ExcelDownloadDTO;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.PropertyEditNameAddressDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SelfAssessmentServiceImpl implements SelfAssessmentService {

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentDeaultValueDTO setAllDefaultFields(Long orgid, Long deptId) {
        SelfAssessmentDeaultValueDTO dto = new SelfAssessmentDeaultValueDTO();
        dto.setOrgId(orgid);
        dto.setDeptId(deptId);
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage("PROPERTY_DEFAULTDATA"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentDeaultValueDTO.class);
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssessmentChargeCalDTO fetchChecklistAndCharges(SelfAssessmentDeaultValueDTO defaultData) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(defaultData,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_CHECKLISt_CHARGE"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    AssessmentChargeCalDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentFinancialDTO fetchScheduleDate(SelfAssessmentDeaultValueDTO defaultData) {
        PropertyInputDto propInpDto = new PropertyInputDto();
        propInpDto.setScheduleId(defaultData.getScheduleDetId());
        propInpDto.setOrgId(defaultData.getOrgId());

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propInpDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_FINYEAR_GIVENDATE"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentFinancialDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentSaveDTO saveSelfAssessment(SelfAssessmentSaveDTO saveDto) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(saveDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_SAVE_ASSESSMENT"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentSaveDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @Override
    public Map<String, List<BillDisplayDto>> getTaxMapForDisplayCategoryWise(List<BillDisplayDto> displayDto,
            Organisation organisation, Long deptId, List<Long> taxCatList) {
        Map<String, List<BillDisplayDto>> taxCategoryWiseChargeMap = new HashMap<>(0);

        if(taxCatList!= null) {
        	taxCatList.forEach(tacCat -> {
                String taxCatDesc = CommonMasterUtility.getHierarchicalLookUp(
                        tacCat).getDescLangFirst();
                List<BillDisplayDto> billDisDtoList = new ArrayList<>();
                displayDto.forEach(billPresent -> {
                    if (billPresent.getTaxCategoryId().equals(tacCat)) {
                        billDisDtoList.add(billPresent);
                    }
                });
                if (!billDisDtoList.isEmpty()) {
                    billDisDtoList.sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));// Sorting List by collection sequence
                    taxCategoryWiseChargeMap.put(taxCatDesc, billDisDtoList);
                }
            });
        }
        
        return taxCategoryWiseChargeMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentFinancialDTO getFinyearDate(SelfAssessmentDeaultValueDTO request) {
        PropertyInputDto propInpDto = new PropertyInputDto();
        propInpDto.setAcquisitionDate(request.getUiDate());
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propInpDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_FINID_DATE"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentFinancialDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentFinancialDTO fetchFromGivenDate(SelfAssessmentDeaultValueDTO request) {
        PropertyInputDto propInpDto = new PropertyInputDto();
        propInpDto.setFinYearId(request.getGivenfinYearId());
        propInpDto.setOrgId(request.getOrgId());

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propInpDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_FETCH_GIVENDATE"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentFinancialDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentFinancialDTO displayYearList(SelfAssessmentDeaultValueDTO defaultData) {
        PropertyInputDto propInpDto = new PropertyInputDto();
        propInpDto.setFinYearId(defaultData.getGivenfinYearId());
        propInpDto.setOrgId(defaultData.getOrgId());
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(propInpDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_DISPLAY_YEAR"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentFinancialDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentDeaultValueDTO checkForValidProperty(SelfAssessmentDeaultValueDTO requestData) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(requestData,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_CHECk_PROPERTY_VALID"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentDeaultValueDTO.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssessmentChargeCalDTO fetchChecklistAndChargesForChangeAndNoChange(SelfAssessmentDeaultValueDTO arrearsRequest) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
                arrearsRequest,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_CHARGE_WITH_ARREAR"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    AssessmentChargeCalDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentDeaultValueDTO fetchAllLastPaymentDetails(SelfAssessmentDeaultValueDTO defaultData) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(defaultData,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_LAST_PAYMENT"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentDeaultValueDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ExcelDownloadDTO getAllBillschedulByOrgid(ExcelDownloadDTO data) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(data,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_EXCEL_DOWNLOAD"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    ExcelDownloadDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BillPaymentDetailDto getPropertyPaymentDetails(PropertyBillPaymentDto data, Organisation organisation) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(data,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_BILL_PAYMENT"));
        if (MapUtils.isNotEmpty(responseVo)) {
            final String d = new JSONObject(responseVo).toString();

            try {
                return new ObjectMapper().readValue(d,
                        BillPaymentDetailDto.class);
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
            	 throw new FrameworkException(e);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
            	 throw new FrameworkException(e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            	 throw new FrameworkException(e);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public SelfAssessmentDeaultValueDTO fetchPropertyByApplicationId(SelfAssessmentDeaultValueDTO defaultData) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(defaultData,
                ApplicationSession.getInstance().getMessage(
                        "FETCH_PROPERTY_BY_APPLICATION_ID"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentDeaultValueDTO.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentDetailsVO> fetchAppDocuments(Long applicationId, Long orgId) {
        List<DocumentDetailsVO> dataModel = new ArrayList<>();
        ProperySearchDto requestDto = new ProperySearchDto();
        requestDto.setApplicationId(applicationId);
        requestDto.setOrgId(orgId);
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(requestDto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_UPLOADED_DOCUMENTS"));

        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                DocumentDetailsVO l = new ObjectMapper().readValue(d,
                        DocumentDetailsVO.class);
                dataModel.add(l);
            }
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        }
        return dataModel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AssessmentChargeCalDTO fetchTaxListForDisplay(AssessmentChargeCalDTO dto) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_FETCH_TAXLIST"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    AssessmentChargeCalDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LookUp> findDistrictByLandType(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_DISTRICT_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LookUp> getTehsilListByDistrict(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_TEHSIL_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LookUp> getVillageListByTehsil(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_VILLAGE_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	 throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LookUp> getMohallaListByVillageId(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_MOHALLA_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LookUp> getStreetListByMohallaId(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_STREET_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayOfKhasraDetails getKhasraDetails(LandTypeApiDetailRequestDto dto) {

        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_LAND_KHASRA_DETAILS"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    ArrayOfKhasraDetails.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayOfPlotDetails getNajoolDetails(LandTypeApiDetailRequestDto dto) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_LAND_NAJOOL_DETAILS"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    ArrayOfPlotDetails.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayOfDiversionPlotDetails getDiversionDetails(LandTypeApiDetailRequestDto dto) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_LAND_DIVERSION_DETAILS"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    ArrayOfDiversionPlotDetails.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<LookUp> getKhasraNoList(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_KHASRA_NO_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<LookUp> getNajoolPlotList(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_NAJOOL_PLOTNO_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<LookUp> getDiversionPlotList(LandTypeApiDetailRequestDto dto) {
        List<LookUp> lookup = new ArrayList<>();
        final ArrayList<LinkedHashMap<Long, Object>> responseVo = (ArrayList<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(dto,
                        ApplicationSession.getInstance().getMessage(
                                "PROPERTY_LAND_DIVERSION_PLOTNO_LIST"));
        try {
            for (LinkedHashMap<Long, Object> obj : responseVo) {
                final String d = new JSONObject(obj).toString();
                LookUp l = new ObjectMapper().readValue(d,
                        LookUp.class);
                lookup.add(l);
            }
            return lookup;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public AssessmentChargeCalDTO fetchCharges(SelfAssessmentDeaultValueDTO defaultData) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(defaultData,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_TAX_CALCULATOR_CHARGES"));
        final String d = new JSONObject(responseVo).toString();

        try {
            return new ObjectMapper().readValue(d,
                    AssessmentChargeCalDTO.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
        	throw new FrameworkException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LandTypeApiDetailRequestDto getVsrNo(LandTypeApiDetailRequestDto dto) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_VSR_NO"));
        if (responseVo != null) {
            final String d = new JSONObject(responseVo).toString();

            try {
                return new ObjectMapper().readValue(d,
                        LandTypeApiDetailRequestDto.class);
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
            	throw new FrameworkException(e);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
            	throw new FrameworkException(e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            	throw new FrameworkException(e);
            }
        }
        return null;
    }

    @Override
    public void callWorkflowInCaseOfZeroBillPayment(CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDt) {
        SelfAssessmentDeaultValueDTO defaultDto = new SelfAssessmentDeaultValueDTO();
        defaultDto.setCommonChallanDTO(offline);
        defaultDto.setProvisionalMas(asseMstDt);
        JersyCall.callRestTemplateClient(defaultDto,
                ApplicationSession.getInstance().getMessage(
                        "PROPERTY_WORKFLOW_ZERO_BILL_PAY"));

    }

    @SuppressWarnings("unchecked")
    @Override
    public SelfAssessmentSaveDTO saveAndUpdateDraftApplicationAfterEdit(SelfAssessmentSaveDTO saveDto) {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(saveDto,
                ApplicationSession.getInstance().getMessage(
                        "SAVE_DRAFT_APPLICATION"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    SelfAssessmentSaveDTO.class);
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }
    
	@Override
	public Boolean checkWhetherPropertyIsActive(String propNo, String oldPropNo, Long orgId) {
		ProvisionalAssesmentMstDto dto = new ProvisionalAssesmentMstDto();
		dto.setOrgId(orgId);
		dto.setAssNo(propNo);
		dto.setAssOldpropno(oldPropNo);
		try {
			Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(dto,
					ServiceEndpoints.PROPERTY_URL.PROPERTY_ACTIVE_CHECK);
			return responseVo;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<String> fetchFlstList(String propNo, Long orgid) {
			
			// call for flat no list
			Map<String, String> requestParam = new HashMap<>();
			DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
			uriHandler.setParsePath(true);
			if (StringUtils.isNotBlank(propNo)) {
				requestParam.put("propNo", propNo.replaceAll("\\s", ""));
			} 
			requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgid));
			URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_FLAT_LIST_BY_PROP_NO_ORGID"),
					requestParam);
			List<String> flatNoList = (List<String>) JersyCall.callRestTemplateClient(requestParam, uri.toString());
			if (flatNoList != null && !flatNoList.isEmpty()) {		
				return flatNoList;
			} else {
				return flatNoList;
			}
			// return null;

		}

	@SuppressWarnings("unchecked")
	@Override
	public ProvisionalAssesmentMstDto getPropertyEdidNameAddress(ProvisionalAssesmentMstDto dto) {
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_FETCH_EDID_NAME_ADDRESS);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, ProvisionalAssesmentMstDto.class);
			} catch (JsonParseException e) {
	            // TODO Auto-generated catch block
            	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PropertyEditNameAddressDto saveEditNameAndAddress(PropertyEditNameAddressDto dto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_SAVE_EDID_NAME_ADDRESS);
		if (MapUtils.isNotEmpty(responseVo)) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, PropertyEditNameAddressDto.class);
			} catch (JsonParseException e) {
	            // TODO Auto-generated catch block
            	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
		}
		return null;
	
	}

	@Override
	public boolean checkPropertyExistByPropNo(String propNo, Long orgId) {
		try {
			Boolean responseVo = (Boolean) JersyCall.callRestTemplateClient(propNo,
					ServiceEndpoints.PROPERTY_URL.PPOPERTY_EXIST_BY_PROP_NO+"propNo/"+propNo+"/orgId/"+orgId);
			return responseVo;
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProvisionalAssesmentMstDto savePropertyElectricRelatedData(ProvisionalAssesmentMstDto assesmentMstDto) {

		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(assesmentMstDto,
				ServiceEndpoints.PROPERTY_URL.PROPERTY_EDIT_ELECTRIC_KYC_DATA);
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, ProvisionalAssesmentMstDto.class);
			} catch (JsonParseException e) {
	            // TODO Auto-generated catch block
            	throw new FrameworkException(e);
	        } catch (JsonMappingException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	throw new FrameworkException(e);
	        }
		}
		return null;
	
	}
}
