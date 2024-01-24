package com.abm.mainet.rnl.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.BookingResDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ritesh.patil
 *
 */
@Service
public class RNLChecklistAndChargeServiceImpl implements IRNLChecklistAndChargeService {

    /*
     * @Override public WSResponseDTO initializeModel() { final WSRequestDTO dto = new WSRequestDTO();
     * dto.setModelName(ServiceEndpoints.BRMS_RNL_URL.MODEL_NAME); final WSResponseDTO response = JersyCall.callBRMS(dto,
     * BRMS_URL.INITIALIZE_MODEL_URI); return response; }
     */

    /*
     * @Override
     * @SuppressWarnings("unchecked") public List<DocumentDetailsVO> getChecklist(final CheckListModel checklistModel) {
     * List<DocumentDetailsVO> checklist = Collections.emptyList(); final WSRequestDTO dto = new WSRequestDTO();
     * dto.setDataModel(checklistModel); final WSResponseDTO response = JersyCall.callBRMS(dto, BRMS_URL.CHECKLIST_URI); if
     * (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) { final List<?> docs =
     * JersyCall.castResponse(response, DocumentDetailsVO.class); checklist = (List<DocumentDetailsVO>) docs; } return checklist;
     * }
     */

    @Override
    public WSResponseDTO getApplicableTaxes(final RNLRateMaster rate, final long orgid, final String serviceShortCode) {
        WSResponseDTO applicableTaxes = null;
        try {
            final WSRequestDTO dto = new WSRequestDTO();
            rate.setOrgId(orgid);
            rate.setServiceCode(serviceShortCode);
            rate.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp("APL", "CAA")
                    .getLookUpId()));
            dto.setDataModel(rate);
            applicableTaxes = JersyCall.callServiceBRMSRestClient(dto,
                    ServiceEndpoints.BRMS_RNL_URL.RNL_DEPENDENT_PARAM_URL);
        } catch (Exception ex) {
            throw new FrameworkException("Exception while fetching getApplicableTaxes from service call:", ex);
        }

        return applicableTaxes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChargeDetailDTO> getApplicableCharges(final List<RNLRateMaster> requiredCHarges) {
        final WSRequestDTO request = new WSRequestDTO();
        request.setDataModel(requiredCHarges);
        final WSResponseDTO response = JersyCall.callServiceBRMSRestClient(request,
                ServiceEndpoints.BRMS_RNL_URL.RNL_SERVICE_CHARGE_URL);
        if (response.getWsStatus() != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<?> charges = JersyCall.castResponse(response, ChargeDetailDTO.class);
            // final List<?> charges = JersyCall.castResponse(response, RNLRateMaster.class);
            /*
             * final List<RNLRateMaster> finalRateMaster = new ArrayList<>(); for (final Object rate : charges) { final
             * RNLRateMaster masterRate = (RNLRateMaster) rate; finalRateMaster.add(masterRate); } ChargeDetailDTO chargedto =
             * null; final List<ChargeDetailDTO> detailDTOs = new ArrayList<>(); for (final RNLRateMaster rateCharge :
             * finalRateMaster) { chargedto = new ChargeDetailDTO(); chargedto.setChargeCode(rateCharge.getTaxId());
             * chargedto.setChargeAmount(rateCharge.getFlatRate()); chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
             * chargedto.setChargeDescReg(rateCharge.getChargeDescReg()); detailDTOs.add(chargedto); }
             */
            return (List<ChargeDetailDTO>) charges;
        } else {
            throw new FrameworkException(
                    "Exception while fetching getApplicableCharges from service call: " + response.getErrorMessage());
        }
    }

    @Override
    public double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    @Override
    public List<DocumentDetailsVO> getFileUploadList(final List<DocumentDetailsVO> checkList,
            final Map<Long, Set<File>> fileMap) {

        final Map<Long, String> listString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();

        try {
            final List<DocumentDetailsVO> docs = checkList;
            if ((fileMap != null) && !fileMap.isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

                    final List<File> list = new ArrayList<>(entry.getValue());
                    for (final File file : list) {
                        try {
                            final Base64 base64 = new Base64();

                            final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                            fileName.put(entry.getKey(), file.getName());
                            listString.put(entry.getKey(), bytestring);

                        } catch (final IOException e) {

                            throw new FrameworkException("Exception found in getFileUploadList during file I/O :", e);
                        }
                    }
                }
            }
            if (docs != null) {
                if (!docs.isEmpty() && !listString.isEmpty()) {
                    for (final DocumentDetailsVO d : docs) {
                        final long count = d.getDocumentSerialNo() - 1;
                        if (listString.containsKey(count) && fileName.containsKey(count)) {
                            d.setDocumentByteCode(listString.get(count));
                            d.setDocumentName(fileName.get(count));

                            if ((d.getDoc_DESC_Mar() != null) && !d.getDoc_DESC_Mar().isEmpty()) {

                                d.setDoc_DESC_Mar(Utility.encodeField(d.getDoc_DESC_Mar()));
                            }

                        }
                        if ((d.getDoc_DESC_ENGL() == null) || d.getDoc_DESC_ENGL().equals("")) {
                            d.setDoc_DESC_ENGL(d.getDocumentSerialNo().toString());
                        }

                    }

                }
            }
            return docs;
        } catch (final Exception e) {
            throw new FrameworkException("FileUploading Exception occur in getFileUploadList");
        }
    }

    /*
     * get property list by search filter with total rent(BRMS call)
     */

    @SuppressWarnings("unchecked")
    @Override
    public PropertyResDTO getFilteredRentedProperties(final Integer categoryId, final Long eventId, final long capacityFrom,
            final long capacityTo, final double rentFrom, final double rentTo, final Organisation org) {

        EstatePropReqestDTO reqDto = new EstatePropReqestDTO();
        reqDto.setCategoryTypeId(categoryId);
        reqDto.setEventId(eventId);
        reqDto.setCapcityFrom(capacityFrom);
        reqDto.setCapcityTo(capacityTo);
        reqDto.setRentFrom(rentFrom);
        reqDto.setRentTo(rentTo);
        reqDto.setOrgId(org.getOrgid());
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(reqDto,
                ApplicationSession.getInstance().getMessage(
                        "RNL_FILTERED_PROPERTY_URL"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    PropertyResDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public PropertyResDTO getFilteredWaterTanker(final Integer categoryId, final Long eventId, final Organisation org) {

        EstatePropReqestDTO reqDto = new EstatePropReqestDTO();
        reqDto.setCategoryTypeId(categoryId);
        reqDto.setEventId(eventId);
        reqDto.setOrgId(org.getOrgid());
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(reqDto,
                ApplicationSession.getInstance().getMessage(
                        "RNL_FILTERED_WATER_TANKER_URL"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
                    PropertyResDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }

    }

	@Override
	public BookingResDTO saveOrUpdateChangeUsage(final BookingReqDTO bookingResDTO) throws JsonParseException, JsonMappingException, IOException {

		 @SuppressWarnings("unchecked")
	        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
	        		bookingResDTO, ServiceEndpoints.WebServiceUrl.SAVE_BOOKING);
		    final String response = new JSONObject(responseVo).toString();
	        return new ObjectMapper().readValue(response,BookingResDTO.class);
		
	}

	@Override
	public BookingResDTO saveOrUpdateWaterTanker(BookingReqDTO bookingResDTO) throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
        
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(bookingResDTO,
                ApplicationSession.getInstance().getMessage(
                        "SAVE_WATER_TANKER_BOOKING"));
        final String d = new JSONObject(responseVo).toString();
        try {
            return new ObjectMapper().readValue(d,
            		BookingResDTO.class);
        } catch (IOException e) {
            throw new FrameworkException(e);
        }
	}
	
	@Override
	public BookingReqDTO getApplicationData(BookingReqDTO requestDTO)
			throws JsonParseException, JsonMappingException, IOException {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(requestDTO, ServiceEndpoints.WebServiceUrl.GET_WATER_TANKER_DETAILS_BY_APPID);
		final String response = new JSONObject(responseVo).toString();
		return new ObjectMapper().readValue(response, BookingReqDTO.class);

	}

}
