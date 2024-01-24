package com.abm.mainet.water.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.service.DisconnectWaterConnectionService;
import com.abm.mainet.water.service.IDisconnectWaterConnectionService;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.ui.model.NewWaterConnectionFormModel;
import com.abm.mainet.water.ui.model.WaterDisconnectionFormModel;
import com.abm.mainet.water.ui.model.WaterReconnectionFormModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping("/WaterDisconnectionForm.html")
public class WaterDisconnectionFormController extends AbstractFormController<WaterDisconnectionFormModel> {
	private static Logger log = Logger.getLogger(WaterDisconnectionFormController.class);

    private static final String WATER_CONNECTION_NO_SEARCH = "WATER_CONNECTION_NO_SEARCH";

    private static final String SEARCH_CONNECTION_DETAILS = "searchConnectionDetails";

    private static final String SEARCH_WATER_CONNECTION_NO = "WaterDisconnectionFormValidn";

    @Autowired
    private transient IServiceMasterService serviceMaster;

    @Autowired
    private IDisconnectWaterConnectionService iDisconnectWaterConnectionService;
    
	@Autowired
	private INewWaterConnectionService iNewWaterConnectionService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        PortalService portalService;
        
        final WaterDisconnectionFormModel model = getModel();
		final WaterDeconnectionRequestDTO reqDTO = model.getWaterRequestDto();
		
        getModel().initializeApplicantDetail();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        getModel().initializeAgencyApplicantDetail();
        portalService = serviceMaster.getPortalServiceMaster(MainetConstants.ServiceCode.WATER_DISCONNECTION,
                orgId);
        getModel().setServiceId(portalService.getServiceId());
        getModel().setDeptId(portalService.getPsmDpDeptid());
        getModel().setUlbPlumber(MainetConstants.YES);
        getModel().setCommonHelpDocs("WaterDisconnectionForm.html");
        getModel().setPlumberList(iNewWaterConnectionService.getListofplumber(orgId));
        
        ModelAndView mv = new ModelAndView("WaterDisconnectionForm", MainetConstants.FORM_NAME, getModel());
		
        String authentication="";
		
		if(null!=str && null!=ns && null!=ULBID && null!=ULBDistrict){
			EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
			log.info("Encrypted Key: " + str);
			authentication=encryptDecrypt.authentication(str,ns);	
			
			if(!authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)){
				model.getWaterRequestDto().setTenant(authentication);
			}
		}
		
		
		Employee emp = UserSession.getCurrent().getEmployee();
		if(emp.getEmploginname().equalsIgnoreCase("NOUSER") && !authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE) && !authentication.equalsIgnoreCase(MainetConstants.BLANK)) {
			mv= new ModelAndView("WaterDisconnectionForm", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("WaterDisconnectionForm", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}
		return super.index();

    }

    @RequestMapping(params = SEARCH_CONNECTION_DETAILS, method = RequestMethod.POST)
    public ModelAndView getConnectionDetails(final HttpServletRequest httpServletRequest)
            throws JsonParseException, JsonMappingException, IOException {
        bindModel(httpServletRequest);
        ModelAndView mv;

        final WaterDeconnectionRequestDTO waterRequestDto = new WaterDeconnectionRequestDTO();
        waterRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        waterRequestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        waterRequestDto.setConnectionNo(getModel().getConnectionNo());

        final WaterDisconnectionResponseDTO waterResponseDto = iDisconnectWaterConnectionService
                .fetchConnectionDetails(waterRequestDto);
        if (waterResponseDto.getConnectionList().size() == 1) {
            final CustomerInfoDTO customerInfoDTO = waterResponseDto.getConnectionList().get(0);
            getModel().setConnectionNo(customerInfoDTO.getCsCcn());
            getModel().setConsumerName(customerInfoDTO.getConsumerName());
            getModel().setAreaName(customerInfoDTO.getCsAdd());
            getModel().setConnectionList(waterResponseDto.getConnectionList());
			/*
			 * if(waterResponseDto.getBilloutstandingAmoount() > 0) {
			 * getModel().addValidationError(ApplicationSession.getInstance()
			 * .getMessage("WaterDisconnection.duesExistsAgainst") + " " +
			 * customerInfoDTO.getCsCcn() + " " +
			 * ApplicationSession.getInstance().getMessage(
			 * "WaterDisconnection.PleaseClearDues")); }
			 */
            if(waterResponseDto.getBilloutstandingAmoount() > 0) {
            	getModel().addValidationError(ApplicationSession.getInstance()
						.getMessage("Dues.Pending.Against.the.Connection.Number") + " " + customerInfoDTO.getCsCcn() + ". "
						+ ApplicationSession.getInstance().getMessage("Please.Clear.the.Dues."));
            }

        } else {
            getModel().setConsumerName(MainetConstants.BLANK);
            getModel().setAreaName(MainetConstants.BLANK);
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.discon.norecod",
                    new Object[] { getModel().getConnectionNo() }));
        }

        mv = new ModelAndView(SEARCH_WATER_CONNECTION_NO, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

        return mv;
    }

    /**
     * Do get applicable check list and charges.
     *
     * @param request the request
     * @return the model and view
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest request) {
        bindModel(request);
        final WaterDisconnectionFormModel model = getModel();
        model.validateApplicantDetail();
        model.validateConnectionDetail();
        if (!model.hasValidationErrors()) {

            try {

                final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                model.findApplicableCheckListAndCharges(model.getServiceId(), orgId);
                model.setEnableSubmit(true);
            } catch (final Exception ex) {
                throw new FrameworkException(ex);
            }
        }

        return defaultMyResult();
    }

    @RequestMapping(params = WATER_CONNECTION_NO_SEARCH, produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<CustomerInfoDTO> getConnectionData(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {
        return getModel().paginate(httpServletRequest, page, rows, getModel().getConnectionList());

    }
    
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appId") final long appId, final HttpServletRequest httpServletRequest) throws FrameworkException {  
        getModel().bind(httpServletRequest);
        
        WaterDisconnectionFormModel model = this.getModel();
        try {
        	 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
             final WaterDeconnectionRequestDTO response = iDisconnectWaterConnectionService.getWaterDisconnecDetails(appId,orgId);
             
             if(response != null) {
            	model.setWaterRequestDto(response);
            	model.setApplicantDetailDto(response.getApplicantDetailsDto());
            	model.setConnectionNo(response.getConnectionNo());
            	model.setConsumerName(response.getConsumerName());
            	model.setDisconnectionEntity(response.getDisconnectionDto());
            	
             }
        }
       catch(Exception exception) {
    	   log.error("Error while fetching values from service: " + exception.getMessage(), exception);
    	   throw new FrameworkException(exception);
       }
        return new ModelAndView("waterDisconnectionDashboardView", MainetConstants.FORM_NAME, getModel());
    }
    
    
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final WaterDisconnectionFormModel model = getModel();
        try {
            if (model.saveForm()) {
            	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
    				return jsonResult(
    						JsonViewObject.successResult(model.getSuccessMessage()));
    			}
                if ((model.getSuccessMessage() != null)
                        && !model.getSuccessMessage().isEmpty()) {
                    return jsonResult(JsonViewObject.successResult(model
                            .getSuccessMessage()));
				} 
				else {
					if (MainetConstants.PAY_GATEWAY_AVAIL.equalsIgnoreCase(
							ApplicationSession.getInstance().getMessage("ulb.payment_gateway.available"))) {
						return jsonResult(JsonViewObject
								.successResult(getApplicationSession().getMessage("continue.forpayment")));
					} else {
						return jsonResult(JsonViewObject
								.successResult(getApplicationSession().getMessage("continue.forchallan")));
					}
				}

			}
        } catch (final Exception ex) {
            logger.error(MainetConstants.ERROR_OCCURED, ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

		
		return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
				: customDefaultMyResult(model.getCustomViewName());

    }

}