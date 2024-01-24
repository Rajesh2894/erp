package com.abm.mainet.dashboard.citizen.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.plexus.util.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ActionDTOWithDoc;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.ProperySearchDto;
import com.abm.mainet.common.dto.ViewPropertyDetailRequestDto;
import com.abm.mainet.common.pg.dto.TbLoiMas;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IWorkflowActionService;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dashboard.citizen.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.dashboard.citizen.dto.CitizenDashBoardResDTO;
import com.abm.mainet.dashboard.citizen.dto.WaterDashboardDTO;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.security.JwtUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/CitizenDashBoard.html")
public class CitizenDashBoardController {

    private static final String EXCEPTION_AT_CITIZEN_DASHBOARD = "Exception occured at citizen Dashboard ";
    private static final String ACTIONS = "actions";
    private static final String ACTION_HISTORY = "ActionHistory";
    private static final Logger LOGGER = Logger.getLogger(CitizenDashBoardController.class);

    @Autowired
    private IWorkflowActionService workflowActionService;
    
    @Autowired
	ICommonBRMSService iCommonBRMSService;
    
    @Resource
 	IFileUploadService fileUpload;
    
    @Autowired
	JwtUtil jwtUtil;
    
     
     @Value("${upload.physicalPath}")
     private String filenetPath;

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getApplications", method = RequestMethod.POST)
    public @ResponseBody List<CitizenDashBoardResDTO> getGridForPending(final HttpServletRequest request, final Model model) {
        final UserSession userSession = UserSession.getCurrent();
		//final Long[] requestParam = { userSession.getEmployee().getEmpId(), userSession.getOrganisation().getOrgid() };
        CitizenDashBoardReqDTO requestParam = new CitizenDashBoardReqDTO();
        requestParam.setOrgId(userSession.getOrganisation().getOrgid());
        requestParam.setEmpId(userSession.getEmployee().getEmpId());
        requestParam.setMobileNo(userSession.getEmployee().getEmpmobno());

        try {
			new ObjectMapper().writeValueAsString(requestParam);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.JercyCallURL.DASHBOARD_DATA);
        List<CitizenDashBoardResDTO> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CitizenDashBoardResDTO app = new ObjectMapper().readValue(d, CitizenDashBoardResDTO.class);
                    //D#123820
                    if(app.getStatus().equalsIgnoreCase("PENDING"))
                    	app.setStatusDesc(ApplicationSession.getInstance().getMessage("citizen.application.pending"));
                    else if(app.getStatus().equalsIgnoreCase("CLOSED"))
                    	app.setStatusDesc(ApplicationSession.getInstance().getMessage("citizen.application.closed"));
                    else if(app.getStatus().equalsIgnoreCase("EXPIRED"))
                    	app.setStatusDesc(ApplicationSession.getInstance().getMessage("citizen.application.expired"));
                    else if(app.getStatus().equalsIgnoreCase("DRAFT"))
                    	app.setStatusDesc(ApplicationSession.getInstance().getMessage("citizen.application.draft"));
                    else
                    	app.setStatusDesc(app.getStatus());
                    
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        
        if(userSession.getLanguageId()!=MainetConstants.NUMBERS.ONE) {
            responseList.stream().forEach(resDto -> {
            	       if(resDto.getDeptNameMar()!=null) {
                    	resDto.setDeptName(resDto.getDeptNameMar());
            	       }
                    	});
            }
        return responseList;

    }

    @RequestMapping(method = RequestMethod.POST, params = "viewFormHistoryDetails")
    public ModelAndView showHistoryDetails(@RequestParam("appId") final String appId,
            final HttpServletRequest httpServletRequest, ModelMap modelMap) {
        List<ActionDTOWithDoc> actionHistory = workflowActionService.getWorkflowActionLogByApplicationId(appId,
                UserSession.getCurrent().getLanguageId());
        /*D#127224 - As per suggested by Rajesh Sir-> showing only completed action history*/
    	List<ActionDTOWithDoc> actHistory = actionHistory.stream()
    			.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());
        modelMap.put(ACTIONS, actHistory);
      //D#127160 download document in portal fileNet
        iCommonBRMSService.getChecklistDocument(appId, UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
        return new ModelAndView(ACTION_HISTORY, MainetConstants.FORM_NAME, modelMap);
    }
    
	/*Earlier getting both referenceId & applicationId from same column(apm_application_id) 
     * Now we have added one more column reference_id and now we are getting both values in respective columns.*/
    @RequestMapping(method = RequestMethod.POST, params = "showHistoryDetails")
    public ModelAndView showHistoryDetailsWithAppRefId(@RequestParam("appId") final String appId,
    		@RequestParam("refId") final String refId,
    		@RequestParam(value="shortCode",required=false) final String shortCode,
    		@RequestParam(value="orgId",required=false) final Long orgId,
    		final HttpServletRequest httpServletRequest, ModelMap modelMap) {
    	List<ActionDTOWithDoc> actionHistory = null;
    	if(StringUtils.isNotBlank(appId)) {
    		actionHistory = workflowActionService.getWorkflowActionLogByApplicationId(appId,
                UserSession.getCurrent().getLanguageId());
    	}else if(StringUtils.isNotBlank(refId)) {
    		actionHistory = workflowActionService.getWorkflowActionLogByReferenceId(refId,
                    UserSession.getCurrent().getLanguageId());
    	}
    	/*D#127224 - As per suggested by Rajesh Sir-> showing only records in action history whose action has been taken by user*/
    	List<ActionDTOWithDoc> actHistory = actionHistory.stream() .filter(action ->
		  !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());
		  modelMap.put(ACTIONS, actHistory);
        //D#127160 download document in portal fileNet
        iCommonBRMSService.getChecklistDocument(appId, UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
        return new ModelAndView(ACTION_HISTORY, MainetConstants.FORM_NAME, modelMap);
    }

    @RequestMapping(method = RequestMethod.POST, params = "viewFormDetails")
    public ModelAndView showDetails(@RequestParam("appId") final Long appId, @RequestParam("orgId") final Long orgId,
            @RequestParam("shortName") final String shortCode, final HttpServletRequest httpServletRequest) {

        ModelAndView modelAndView = null;

        try {
            switch (shortCode) {
            case MainetConstants.DASHBOARD.NEW_WATER_CONNECTION:
                modelAndView = getWaterConnectionDetails();
                break;
            default:
                break;
            }

        } catch (final Exception ex) {
            LOGGER.error(EXCEPTION_AT_CITIZEN_DASHBOARD, ex);
            modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
        return modelAndView;
    }

    private ModelAndView getWaterConnectionDetails() throws Exception {

        ApplicationContextProvider.getApplicationContext();
        return new ModelAndView("NewWaterConnectionFormDashBoard", MainetConstants.FORM_NAME, null);
    }

    @RequestMapping(method = RequestMethod.POST, params = "viewDetails")
    public @ResponseBody String viewDetails(@RequestParam("appId") final String appId,
            @RequestParam("serviceCode") final String seviceShortCode,
            final HttpServletRequest httpServletRequest) {

        final ApplicationSession appSession = ApplicationSession.getInstance();
        return appSession.getMessage("dashboard." + seviceShortCode);
    }

    @RequestMapping(method = RequestMethod.POST, params = "getUrlForBillPayStatement")
    public @ResponseBody String getUrlForBillPayStatement(@RequestParam("deptShortCode") final String deptShortCode,
            final HttpServletRequest httpServletRequest) {

        final ApplicationSession appSession = ApplicationSession.getInstance();
        appSession.getMessage("dashboard.billPaySate." + deptShortCode);

        return appSession.getMessage("dashboard.billPaySate." + deptShortCode);

    }

    @RequestMapping(params = "getBillingStatus", method = RequestMethod.POST)
    public @ResponseBody List<ProperySearchDto> getGridForBilling(final HttpServletRequest request, final Model model,
            ProperySearchDto searchDto) {
        final UserSession userSession = UserSession.getCurrent();
        // final Long[] requestParam = { userSession.getEmployee().getEmpId(), userSession.getOrganisation().getOrgid()};
        String empmobno = userSession.getEmployee().getEmpmobno();
        Long orgid = userSession.getOrganisation().getOrgid();
        ViewPropertyDetailRequestDto viewRequestDto = new ViewPropertyDetailRequestDto();
        viewRequestDto.setPropSearchDto(searchDto);
        viewRequestDto.getPropSearchDto().setMobileno(empmobno);
        viewRequestDto.getPropSearchDto().setOrgId(orgid);

        viewRequestDto.setPagingDTO(null);
        viewRequestDto.setGridSearchDTO(null);
        List<LinkedHashMap<Long, Object>> responseObj = null;
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")){
        	LOGGER.info("SEARCH_PROPERTY_DETAILS_SKDCL called getGridForBilling()");
        	responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(viewRequestDto,
                    ApplicationSession.getInstance().getMessage("SEARCH_PROPERTY_DETAILS_SKDCL"));
        } else {
        	LOGGER.info("SEARCH_PROPERTY_DETAILS called getGridForBilling()");
        	responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(viewRequestDto,
        			ApplicationSession.getInstance().getMessage("SEARCH_PROPERTY_DETAILS"));        	
        }
        
        List<ProperySearchDto> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    ProperySearchDto app = new ObjectMapper().readValue(d, ProperySearchDto.class);
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting ProperySearchDto to rest response :" + ex);
                }

            });
        }
        return responseList;

    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getPayFailureApp", method = RequestMethod.POST)
    public @ResponseBody List<CitizenDashBoardResDTO> getPayFailureApp(final HttpServletRequest request, final Model model) {
        final UserSession userSession = UserSession.getCurrent();
        final Long[] requestParam = { Long.valueOf(userSession.getEmployee().getEmpmobno()),
                userSession.getOrganisation().getOrgid() };
        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.JercyCallURL.DASHBOARD_PAY_FAIL_DATA);
        List<CitizenDashBoardResDTO> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CitizenDashBoardResDTO app = new ObjectMapper().readValue(d, CitizenDashBoardResDTO.class);
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        return responseList;

    }

    @RequestMapping(params = "getWaterBillingStatus", method = RequestMethod.POST)
    public @ResponseBody List<WaterDashboardDTO> getGridForWaterBilling(final HttpServletRequest request, final Model model) {
        final UserSession userSession = UserSession.getCurrent();
        String empmobno = userSession.getEmployee().getEmpmobno();
        Long orgid = userSession.getOrganisation().getOrgid();

        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                null,
                ApplicationSession.getInstance().getMessage(
                        "SEARCH_WATER_DETAILS") + "/orgId/" + orgid + "/refNo/" + empmobno);

        List<WaterDashboardDTO> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    WaterDashboardDTO app = new ObjectMapper().readValue(d, WaterDashboardDTO.class);
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting ProperySearchDto to rest response :" + ex);
                }

            });
        }
        return responseList;

    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "fetch-first-appeal-list", method = RequestMethod.POST)
    public @ResponseBody List<CitizenDashBoardResDTO> fetchFirstAppealList(final HttpServletRequest request, final Model model) {
        final UserSession userSession = UserSession.getCurrent();

        CitizenDashBoardReqDTO requestParam = new CitizenDashBoardReqDTO();
        requestParam.setOrgId(userSession.getOrganisation().getOrgid());
        requestParam.setEmpId(userSession.getEmployee().getEmpId());
        requestParam.setMobileNo(userSession.getEmployee().getEmpmobno());

        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.RTS.FETCH_FIRST_APPEAL_DATA);
        List<CitizenDashBoardResDTO> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CitizenDashBoardResDTO app = new ObjectMapper().readValue(d, CitizenDashBoardResDTO.class);
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        return responseList;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "fetch-second-appeal-list", method = RequestMethod.POST)
    public @ResponseBody List<CitizenDashBoardResDTO> fetchSecondAppealList(final HttpServletRequest request, final Model model) {
        final UserSession userSession = UserSession.getCurrent();
        CitizenDashBoardReqDTO requestParam = new CitizenDashBoardReqDTO();
        requestParam.setOrgId(userSession.getOrganisation().getOrgid());
        requestParam.setEmpId(userSession.getEmployee().getEmpId());
        requestParam.setMobileNo(userSession.getEmployee().getEmpmobno());

        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.RTS.FETCH_SECOND_APPEAL_DATA);
        List<CitizenDashBoardResDTO> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();
                try {
                    CitizenDashBoardResDTO app = new ObjectMapper().readValue(d, CitizenDashBoardResDTO.class);
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        return responseList;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(params = "getDocument", method = RequestMethod.POST)
	public @ResponseBody CitizenDashBoardResDTO getDocument(@RequestParam("docName") final String docName,
			@RequestParam("docPath") final String docPath,
			final HttpServletRequest request, final Model model) throws JsonParseException, JsonMappingException, IOException {
		CitizenDashBoardResDTO dto = new CitizenDashBoardResDTO();
		DocumentDetailsVO attachments = new DocumentDetailsVO();
		dto.setDocPath(docPath);
		dto.setDocName(docName);

		LinkedHashMap<Long, Object> responseObj = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(dto,
				ServiceEndpoints.JercyCallURL.DASHBOARD_DOCUMENT);
		String d = new JSONObject(responseObj).toString();
		CitizenDashBoardResDTO app = new ObjectMapper().readValue(d, CitizenDashBoardResDTO.class);
		attachments.setDocumentName(docName);
		attachments.setDocumentByteCode(app.getDoc());
		String existingPath=null;
        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
            existingPath = docPath.replace('/', '\\');
        } else {
            existingPath = docPath.replace('\\', '/');     
        }
		final boolean fileSavedSucessfully = fileUpload.convertAndSaveFile(attachments, filenetPath,
				existingPath,
				docName);
		return dto;
	}
    
    
    //Defect #117792
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getLoiInformation", method = RequestMethod.POST)
    public @ResponseBody List<TbLoiMas> getLoiInformation(final HttpServletRequest request, final Model model) {
    	LOGGER.info("Started call to get loi payment applications");
        final UserSession userSession = UserSession.getCurrent();
        Long[] requestParam = {        		
                userSession.getOrganisation().getOrgid(),
                userSession.getEmployee().getEmpId(),
                Long.valueOf(userSession.getEmployee().getEmpmobno())};
   
        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.JercyCallURL.DASHBOARD_LOI_INFORMATION);
        List<TbLoiMas> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();            
                try {
                	TbLoiMas app = new ObjectMapper().readValue(d, TbLoiMas.class);
					app.setApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(app.getLoiDate()));
					
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        LOGGER.info("End call to get loi payment applications");
        return responseList;

    }
    @RequestMapping(method = RequestMethod.POST, params = "redirectToApaniSarkarPage")
    public @ResponseBody String redirectToApaniSarkarPage(final HttpServletRequest httpServletRequest) {

        return jwtUtil.createJWToken(UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getEmployee().getEmpId());

    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getLoiInformationForPrayag", method = RequestMethod.POST)
    public @ResponseBody List<TbLoiMas> getLoiInformationForPrayag(final HttpServletRequest request) {
    	LOGGER.info("Started call to get loi payment applications");
        final UserSession userSession = UserSession.getCurrent();
        Long[] requestParam = {        		
                userSession.getOrganisation().getOrgid(),
                userSession.getEmployee().getEmpId(),
                Long.valueOf(userSession.getEmployee().getEmpmobno())};
   
        List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
                requestParam,
                ServiceEndpoints.JercyCallURL.DASHBOARD_LOI_INFORMATION);
        List<TbLoiMas> responseList = new ArrayList<>();
        if (responseObj != null && !responseObj.isEmpty()) {
            responseObj.forEach(obj -> {
                String d = new JSONObject(obj).toString();            
                try {
                	TbLoiMas app = new ObjectMapper().readValue(d, TbLoiMas.class);
					app.setApplicationDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(app.getLoiDate()));
					
                    responseList.add(app);
                } catch (Exception ex) {
                    LOGGER.error("Exception while casting CitizenDashBoardResDTO to rest response :" + ex);
                }

            });
        }
        LOGGER.info("End call to get loi payment applications");
        return responseList;

    }
}
