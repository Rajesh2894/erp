package com.abm.mainet.water.ui.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.service.ChangeOfOwnerShipService;
import com.abm.mainet.water.service.WaterDisconnectionService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.model.ChangeOfOwnerShipModel;
import com.abm.mainet.water.ui.model.ChangeOfUsageModel;

/**
 * @author Hiren.Poriya
 * @since 07-July-2016
 */
@Controller
@RequestMapping("/ChangeOfOwnership.html")
public class ChangeOfOwnerShipController extends AbstractFormController<ChangeOfOwnerShipModel> {

	@Resource
	private ChangeOfOwnerShipService iChangeOfOwnerShipService;

	@Autowired
	private transient ServiceMasterService serviceMaster;

	@Autowired
	IFileUploadService fileUpload;
	
	@Autowired
    private WaterNoDuesCertificateService waterNoDuesCertificateService;
	
	@Autowired
    private WaterDisconnectionService waterDisconnectionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnerShipController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		bindModel(httpServletRequest);
		this.getModel().setCommonHelpDocs("ChangeOfOwnership.html");
		try {
			getModel().initializeApplicantDetail();
			final ServiceMaster service = serviceMaster.getServiceByShortName(
					MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER,
					UserSession.getCurrent().getOrganisation().getOrgid());
			getModel().setServiceId(service.getSmServiceId());
			getModel().setServiceName(service.getSmServiceName());
			final Long deptId = service.getTbDepartment().getDpDeptid();
			getModel().setDeptId(deptId);
		} catch (final Exception ex) {
			LOGGER.error("Error Occurred while rendering Form:", ex);
			return defaultExceptionView();
		}

		return new ModelAndView("ChangeOfOwnershipFromDept", MainetConstants.CommonConstants.COMMAND, getModel());

	}

	/**
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST }, params = "getConnectionRecords")
	public ModelAndView getConnectionRecord(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		ChangeOfOwnerResponseDTO responseDTO = null;
		final ChangeOfOwnerShipModel model = getModel();

		try {
			
			
			final ChangeOfOwnerRequestDTO requestVo = model.getChangeOwnerMaster();
			requestVo.setOrgnId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestVo.setServiceId(getModel().getServiceId()); // test it
			requestVo.setUserEmpId(UserSession.getCurrent().getEmployee().getEmpId());
			requestVo.setLangId(UserSession.getCurrent().getLanguageId());
			requestVo.setConnectionNo(model.getChangeOwnerResponse().getConnectionNumber());
			
			responseDTO = iChangeOfOwnerShipService.fetchAndVelidatetConnectionData(requestVo.getConnectionNo(),
					requestVo.getOrgnId());
			
			
			String respMsg = "";
            if (responseDTO.getApplicationNo()!=null) {
                String status = waterDisconnectionService.getWorkflowRequestByAppId(responseDTO.getApplicationNo(), requestVo.getOrgnId());
                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                    respMsg = ApplicationSession.getInstance().getMessage(
                            "water.change.of.ownership.search.error");
                    //model.setResultFound(false);
                    return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
                }
            }
			
			
			getModel().setChangeOwnerResponse(responseDTO);
			getModel().getChangeOwnerMaster().setOldOwnerInfo(responseDTO);
			if (responseDTO != null) {
				if (MainetConstants.RnLCommon.Y_FLAG.equals(responseDTO.getCanApplyOrNot())) {

					getModel().setValidConnectionNo(responseDTO.getConnectionNumber());
					getModel().getChangeOwnerMaster().setCsIdn(responseDTO.getConId());
					getModel().setChangeOwnerResponse(responseDTO);

					if (responseDTO.getCooOtitle() != null) {
						responseDTO.setOldOwnerFullName(
								getOldOwnerFullName(responseDTO.getOldOwnerFullName(), responseDTO.getCooOtitle()));
					}
					getModel().getOwnerDTO().getCsmrInfoDTO().setTrmGroup1(responseDTO.getTrmGroup1());
					getModel().getOwnerDTO().getCsmrInfoDTO().setTrmGroup2(responseDTO.getTrmGroup2());
					getModel().getOwnerDTO().getCsmrInfoDTO().setTrmGroup3(responseDTO.getTrmGroup3());

				} else {
					getModel().addValidationError(responseDTO.getCanApplyOrNot());
					getModel().setChangeOwnerResponse(responseDTO);
					
				}
				//D#146950
				if(responseDTO.getErrMsg()!=null)
					getModel().addValidationError(responseDTO.getErrMsg());
			}
		} catch (final Exception e) {
			LOGGER.error("Exception while fetching the old connection Record for change of ownership", e);
			return defaultExceptionFormView();
		}

		final ModelAndView modelAndView = new ModelAndView("OldOwnerDetails", MainetConstants.CommonConstants.COMMAND,
				getModel());

		if (getModel().getBindingResult() != null) {
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					getModel().getBindingResult());
		}

		return modelAndView;

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "resetConnectionRecord")
	public ModelAndView resetConnectionRecord(final HttpServletRequest httpServletRequest) {
		final ChangeOfOwnerShipModel model = getModel();
		model.getOwnerDTO().getCsmrInfoDTO().setTrmGroup1(0l);
		model.setChangeOwnerResponse(new ChangeOfOwnerResponseDTO());
		return new ModelAndView("OldOwnerDetails", MainetConstants.CommonConstants.COMMAND, model);
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		try {
			if (!getModel().validateFormData(getModel().getChangeOwnerMaster())) {

				getModel().findApplicableCheckListAndCharges(MainetConstants.ServiceShortCode.WATER_CHANGEOFOWNER,
						orgId);
				// change it accordingly as above

				getModel().setEnableSubmit(true);

			}
			modelAndView = new ModelAndView("OldOwnerDetails", MainetConstants.CommonConstants.COMMAND, getModel());
			if (getModel().getBindingResult() != null) {
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
			}
			getModel().setCheckFlag(MainetConstants.RnLCommon.Y_FLAG);
		} catch (final Exception ex) {
			modelAndView = defaultExceptionFormView();
			LOGGER.error("Problem while finding checklist and charges: ", ex);
		}

		return modelAndView;
	}

	private String getOldOwnerFullName(final String name, final long title) {

		return getModel().titleDesc(title) + MainetConstants.WHITE_SPACE + name;
	}

	@RequestMapping(params = "proceed", method = { RequestMethod.POST })
	public ModelAndView proceedAfterEdit(HttpServletRequest request) {
		getModel().bind(request);
		return new ModelAndView("ChangeOfOwnerShipViewAfterEdit", MainetConstants.CommonConstants.COMMAND,
				this.getModel());

	}
	
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);
        final ChangeOfOwnerShipModel model = getModel();
        if (model.saveForm()) {
            if ((model.getSuccessMessage() != null) && !model.getSuccessMessage().isEmpty()) {
            	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
            		&& MainetConstants.DEPT_SHORT_NAME.WATER.equals(model.getDepartmentCode())) {
            		return new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME, getModel());
            	}
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            } else {
                return jsonResult(
                        JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
            }
        }

        return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
                : customDefaultMyResult(model.getCustomViewName());

    }

	  @RequestMapping(params = "getDuesForConnNoSKDCL", method = RequestMethod.POST) 
	    public @ResponseBody NoDuesCertificateReqDTO getDuesForConnNoSKDCL(final HttpServletRequest request) {
	    	LOGGER.info("Start the getConnectionDetail()");
	        getModel().bind(request);
	        NoDuesCertificateReqDTO noDuesCertificateReqDTO = new NoDuesCertificateReqDTO();
	        final ChangeOfOwnerShipModel model = getModel();
	        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        model.getChangeOwnerMaster().setOrgnId(orgId);
	        final String connectionNo = model.getChangeOwnerResponse().getConnectionNumber();
	        try {
	            if ((connectionNo != null) && !connectionNo.isEmpty() && (UserSession.getCurrent() != null)) {
	            	final NoDuesCertificateReqDTO requestDTO = new NoDuesCertificateReqDTO();
	            	requestDTO.setConsumerNo(connectionNo);
		            requestDTO.setOrgId(orgId);
		            requestDTO.setServiceId(model.getServiceId());
		            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		            requestDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		           
		            NoDuesCertificateRespDTO resDTO = null;
					resDTO = waterNoDuesCertificateService.getWaterDuesByPropNoNConnNo(requestDTO);
					
					if (resDTO != null) {
		                noDuesCertificateReqDTO.setConsumerName(resDTO.getConsumerName());
		                if (resDTO.getConsumerAddress() != null) {
		                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getConsumerAddress());
		                } else {
		                    noDuesCertificateReqDTO.setConsumerAddress(resDTO.getCsAdd());
		                }
		                for (final Map.Entry<String, Double> map : resDTO.getDuesList().entrySet()) {
		                	if(MainetConstants.NoDuesCertificate.PROPERTYDUES.equals(map.getKey())) {
		                		noDuesCertificateReqDTO.setPropDueAmt(map.getValue());
		                	}
		                    noDuesCertificateReqDTO.setWaterDues(map.getKey());
		                    noDuesCertificateReqDTO.setDuesAmount(map.getValue());
		                }
		                noDuesCertificateReqDTO.setDues(resDTO.isDues());
		                noDuesCertificateReqDTO.setConsumerNo(connectionNo);
		                noDuesCertificateReqDTO.setOrgId(model.getOrgId());
		                noDuesCertificateReqDTO.setServiceId(model.getServiceId());
		                noDuesCertificateReqDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		                noDuesCertificateReqDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		                noDuesCertificateReqDTO.setMobileNo(resDTO.getCsContactno());
		                noDuesCertificateReqDTO.setEmail(resDTO.getEmail());
		                noDuesCertificateReqDTO.setCcnDuesList(resDTO.getCcnDuesList());
		                if (resDTO.isBillGenerated()) {
		                    noDuesCertificateReqDTO.setBillGenerated(true);
		                } else {
		                    noDuesCertificateReqDTO.setBillGenerated(false);
		                }
					}
	            }
	        } catch (final Exception exception) {
	            LOGGER.error("Error occured during call to no dues certificate " + exception.getMessage());           
	        }
	        return  noDuesCertificateReqDTO;
	    }
	
}
