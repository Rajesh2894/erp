
package com.abm.mainet.care.service;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.ActionDTOWithDoc;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sanket.joshi
 *
 */
@Service
public class CareRequestService implements ICareRequestService {

    @Autowired
    ISMSAndEmailService ismsAndEmailService;
    
    @Autowired
	ICommonBRMSService iCommonBRMSService;
    
    private static final Logger LOGGER = Logger.getLogger(CareRequestService.class);


    @Override
    @SuppressWarnings("unchecked")
    public List<CareRequestDTO> getAllComplaintRaisedByCitizenById(Long empId, Long emplType, String searchString)
            throws Exception {

        List<LinkedHashMap<Long, Object>> requestList = null;
        if (empId != null && emplType != null)
            requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(null,
                    ServiceEndpoints.CARE_SERVICE_GRIEVANCES_RAISED_BY_EMPID_AND_EMPLTYPE_URL
                            + MainetConstants.WINDOWS_SLASH + empId
                            + MainetConstants.WINDOWS_SLASH
                            + emplType);
        List<CareRequestDTO> crs = new ArrayList<>();
        if (requestList != null) {
            requestList.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CareRequestDTO cr = new ObjectMapper().readValue(d, CareRequestDTO.class);
                    if (cr != null) {
                        if (searchString == null || searchString.length() == 0) {
                            crs.add(cr);
                        } else {
                            if (cr.getApplicationId().toString().contains(searchString.toString())
                                    || cr.getComplaintId().toString().contains(searchString.toString()))
                                crs.add(cr);
                        }
                    }
                } catch (Exception e) {
                }

            });
        }
        return crs;
    }

    @Override
    public CareRequestDTO getCareRequestById(Long id) throws Exception {
        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUESTS_BY_ID_URL + MainetConstants.WINDOWS_SLASH + id);
        String d = new JSONObject(responseVo).toString();
        CareRequestDTO careRequest = new ObjectMapper().readValue(d, CareRequestDTO.class);
        return careRequest;
    }

    @Override
    public CareRequestDTO getCareRequestByapplicationId(String applicationId) throws Exception {

        CareRequestDTO careRequest = null;
        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUESTS_BY_APPLICATION_NUMBAR_URL
                        + MainetConstants.WINDOWS_SLASH + applicationId);
        if (responseVo != null) {
            String d = new JSONObject(responseVo).toString();
            careRequest = new ObjectMapper().readValue(d, CareRequestDTO.class);
        }
        return careRequest;
    }

    @Override
    public ActionDTO findActionById(Long id) throws Exception {
        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUEST_ACTION_BY_ID_URL + MainetConstants.WINDOWS_SLASH
                        + id);
        String d = new JSONObject(responseVo).toString();
        ActionDTO action = new ObjectMapper().readValue(d, ActionDTO.class);
        return action;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ActionDTOWithDoc> getWorkflowActionLogByApplicationId(Long applicationId, Long orgId, int langId) {
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_WORFLOW_ACTION_LOG_URL + MainetConstants.WINDOWS_SLASH + applicationId
                        + MainetConstants.WINDOWS_SLASH + orgId +
                        MainetConstants.WINDOWS_SLASH +
                        MainetConstants.GrievanceConstants.LANG + MainetConstants.WINDOWS_SLASH + langId);
        List<ActionDTOWithDoc> actionLog = new ArrayList<>();
        requestList.forEach(obj -> {
            String d = new JSONObject(obj).toString();
            try {
                ActionDTOWithDoc a = new ObjectMapper().readValue(d, ActionDTOWithDoc.class);
                actionLog.add(a);
            } catch (Exception e) {
            }
        });
        return actionLog;
    }

    @Override
    public ComplaintRegistrationAcknowledgementDTO constructRequestStatusAcknowledgement(Long applicationId, int langId)
            throws Exception {

        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUEST_ACKNOWLEDGEMENT_BY_APPLICATIONID
                        + MainetConstants.WINDOWS_SLASH
                        + applicationId
                        + MainetConstants.WINDOWS_SLASH
                        + langId);
        if (responseVo == null)
            return null;
        String d = new JSONObject(responseVo).toString();
        ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel = new ObjectMapper().readValue(d,
                ComplaintRegistrationAcknowledgementDTO.class);
        return complaintAcknowledgementModel;
    }

    @Override
    public ComplaintRegistrationAcknowledgementDTO constructRequestStatusAcknowledgement(String complaintId, int langId)
            throws Exception {

        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUEST_ACKNOWLEDGEMENT_BY_COMPLAINTID
                        + complaintId
                        + MainetConstants.WINDOWS_SLASH
                        + langId);
        if (responseVo == null)
            return null;
        String d = new JSONObject(responseVo).toString();
        ComplaintRegistrationAcknowledgementDTO complaintAcknowledgementModel = new ObjectMapper().readValue(d,
                ComplaintRegistrationAcknowledgementDTO.class);
        //download document for attached complaint
        iCommonBRMSService.getChecklistDocument(complaintAcknowledgementModel.getApplicationId().toString(), UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
        return complaintAcknowledgementModel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LocationDTO> getLocationByOrgIdAndDeptId(final Long orgId, final long deptId, final int lang) throws Exception {
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null,
                        ServiceEndpoints.CARE_SERVICE_LOCATION_BY_ORGID_AND_DEPTID + orgId + MainetConstants.WINDOWS_SLASH + deptId);
        final List<LocationDTO> locations = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final LocationDTO l = new ObjectMapper().readValue(d, LocationDTO.class);
                if (l != null) {
                    final String lname = (lang == 1) ? l.getLocNameEng()
                            : (lang == 2) ? l.getLocNameReg() : MainetConstants.BLANK;
                    l.setLocName(lname);
                    locations.add(l);
                }
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
        return locations;
    }

    @Override
    public void sendOTPDetails(String mobileNo, String mobileOTP, RequestDTO applicantDetailDTO) {

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(applicantDetailDTO.getEmail());
        dto.setMobnumber(mobileNo);
        dto.setOneTimePassword(mobileOTP);
        dto.setAppName(applicantDetailDTO.getfName() + " " + applicantDetailDTO.getlName());
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        dto.setLangId(UserSession.getCurrent().getLanguageId());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, MainetConstants.EIP_CHKLST.COMPLAINT,
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    @Override
    public List<OrganisationDTO> getOrgnizationsByDist(Long dist) {

        @SuppressWarnings("unchecked")
        final List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall
                .callRestTemplateClient(null,
                        ServiceEndpoints.CARE_SERVICE_ORGS
                                + MainetConstants.WINDOWS_SLASH + dist);

        final List<OrganisationDTO> organisations = new ArrayList<>();
        requestList.forEach(obj -> {
            final String d = new JSONObject(obj).toString();
            try {
                final OrganisationDTO o = new ObjectMapper().readValue(d, OrganisationDTO.class);
                organisations.add(o);
            } catch (final Exception e) {
                throw new FrameworkException(e);
            }
        });
        return organisations;

    }

    @Override
    public List<CareRequestDTO> complaintStatusAcknowledgement(Long searchString) throws Exception {
        List<CareRequestDTO> crs = new ArrayList<>();
        ComplaintSearchDTO searchFilters = new ComplaintSearchDTO();
        if (searchString != null) {
            searchFilters.setApplicationId(searchString);
            searchFilters.setMobileNumber(searchString.toString());
            searchFilters.setComplaintId(searchString.toString());
        } else {
            return crs;
        }

        @SuppressWarnings("unchecked")
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                searchFilters,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUEST_SEARCH);

        if (requestList != null) {
            requestList.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CareRequestDTO cr = new ObjectMapper().readValue(d, CareRequestDTO.class);
                    crs.add(cr);
                } catch (Exception e) {
                }
            });
        }
        return crs;
    }

    @Override
    public CareRequestDTO getCareRequestBycomplaintId(String complaintId) throws Exception {

        CareRequestDTO careRequest = null;
        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUESTS_BY_COMPLAINTID_URL
                        + MainetConstants.WINDOWS_SLASH + complaintId);
        if (responseVo != null) {
            String d = new JSONObject(responseVo).toString();
            careRequest = new ObjectMapper().readValue(d, CareRequestDTO.class);
        }
        return careRequest;

    }

    @Override
    public GrievanceReqDTO getCareRequestDetails(Long applicationId,Long orgId)throws Exception {

    	GrievanceReqDTO careRequest = null;
        @SuppressWarnings("unchecked")
        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(null,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUESTS_DETAIL+ MainetConstants.WINDOWS_SLASH + applicationId);
        if (responseVo != null) {
            String d = new JSONObject(responseVo).toString();
            careRequest = new ObjectMapper().readValue(d, GrievanceReqDTO.class);
          //download document for attached complaint
            iCommonBRMSService.getChecklistDocument(careRequest.getCareRequest().getApplicationId().toString(), orgId, "Y");
        }
        return careRequest;
    }

	@SuppressWarnings("unchecked")
	@Override
	//D#110049
	public CareRequestDTO getGrievanceOpWardZone(CareRequestDTO careRequestDTO) throws Exception {
		
		LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>)JersyCall.callRestTemplateClient(careRequestDTO,
                ServiceEndpoints.CARE_SERVICE_OP_WARD_ZONE_BY_DEPTID);
		
		 if (responseVo != null) {
	            String d = new JSONObject(responseVo).toString();
	            careRequestDTO = new ObjectMapper().readValue(d, CareRequestDTO.class);
	        }
	        return careRequestDTO;
        
	}
	@Override
    public List<CareRequestDTO> getComplaintByRefIdAndMobNo(CareRequestDTO reqDto) throws Exception {
		List<CareRequestDTO> crs = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<LinkedHashMap<Long, Object>> requestList = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
        		reqDto,
                ServiceEndpoints.CARE_SERVICE_CARE_REQUEST_SEARCH);

        if (requestList != null) {
            requestList.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CareRequestDTO cr = new ObjectMapper().readValue(d, CareRequestDTO.class);
                    crs.add(cr);
                } catch (Exception e) {
                }
            });
        }
        return crs;
    }
	
	
    //#164437
    @SuppressWarnings("unchecked")
    @Override
    public boolean isFlatListEmpty(@RequestParam("refNo") final String refNo){
    	
    
    	boolean flatList = false;
    	
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	
    	Map<String, String> requestParam = new HashMap<>();
    	DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
        if (StringUtils.isNotBlank(refNo)) {
            requestParam.put("propNo", refNo.replaceAll("\\s", ""));
        } else {
        	LOGGER.info("reference no can't be empty");
        	requestParam.put("propNo", "");
        }
    	

		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));
		URI uri = uriHandler.expand(ApplicationSession.getInstance().getMessage("GET_FLAT_LIST_BY_PROP_NO"),
				requestParam);
		List<String> flatNoList = (List<String>) JersyCall.callRestTemplateClient(requestParam, uri.toString());
        
		if(flatNoList.toString().equalsIgnoreCase(MainetConstants.List_With_Empty_Null))
        {
        	LOGGER.info("Returning True from isFlatListEmpty: " + flatNoList);
        	return true;
        }  
		LOGGER.info("Returning False from isFlatListEmpty: " + flatNoList);
		
    	return flatList;
    	
    }
}
