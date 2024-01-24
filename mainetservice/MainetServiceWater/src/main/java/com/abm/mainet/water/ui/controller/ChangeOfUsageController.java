package com.abm.mainet.water.ui.controller;

import java.util.Date;
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
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.ChangeOfUsageDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.ChangeOfUsageResponseDTO;
import com.abm.mainet.water.dto.CustomerInfoDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.ChangeOfUsageService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterDisconnectionService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.model.ChangeOfUsageModel;

@Controller
@RequestMapping(ChangeOfUsageController.CHANGE_OF_USAGE_URL)
public class ChangeOfUsageController extends AbstractFormController<ChangeOfUsageModel> {

    private static final String RENDERING_FORM_ERROR = "Error Occurred while rendering Form: ";

    private static final String GET_CONNECTION_INFO = "getConnectionInfo";
    private static final String GET_CHECK_LIST_AND_CHARGES = "getCheckListAndCharges";

    /**
     * URL Of Change Of Usage Service
     */
    static final String CHANGE_OF_USAGE_URL = "/ChangeOfUsage.html";

    /**
     * Form View Of Change Of Usage
     */
    private static final String CHANGE_OF_USAGE_FORM_VIEW = "ChangeOfUsageValidn";

    /**
     * Service Code Of Change Of Usage
     */
    private static final String CHANGE_OF_USAGESERVICE_CODE = MainetConstants.WaterServiceShortCode.CHANGE_OF_USAGE;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private WaterCommonService waterCommonService;

    @Autowired
    private ChangeOfUsageService changeOfUsageService;

    @Autowired
    private WaterDisconnectionService waterDisconnectionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfUsageController.class);

    @Autowired
    private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
    @Autowired
    private TbWtBillMasService billMasService;
    
    @Autowired
    private WaterNoDuesCertificateService waterNoDuesCertificateService;
    
    @Autowired
	private BillMasterService billMasterService;
    
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUploadService.sessionCleanUpForFileUpload();
        final ChangeOfUsageModel model = getModel();
        model.setCommonHelpDocs("ChangeOfUsage.html");
        try {

            model.initializeApplicantDetail();
            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final ServiceMaster service = serviceMaster.getServiceByShortName(CHANGE_OF_USAGESERVICE_CODE, orgId);
            model.setServiceId(service.getSmServiceId());
            model.setServiceName(service.getSmServiceName());
            model.setDeptId(service.getTbDepartment().getDpDeptid());
        } catch (final Exception ex) {

            LOGGER.error(RENDERING_FORM_ERROR, ex);
            return defaultExceptionView();
        }

        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = GET_CONNECTION_INFO)
    public ModelAndView getConnectionRecord(final HttpServletRequest request) {
        bindModel(request);
        final ChangeOfUsageModel model = getModel();
        ModelAndView modelAndView = new ModelAndView(CHANGE_OF_USAGE_FORM_VIEW, MainetConstants.FORM_NAME, model);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
        	model.validateConnectionNo();
        } else {
        	model.validateApplicantDetail();
        }
        String respMsg = "";
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) 
        		&& !model.hasValidationErrors()) {
            final ChangeOfUsageRequestDTO requestVo = model.getRequestDTO();

            CustomerInfoDTO csmrInfo = waterCommonService.getConnectionDetailDto(
            		UserSession.getCurrent().getOrganisation().getOrgid(), requestVo.getConnectionNo());
        	List<TbWtBillMasEntity> billList = tbWtBillMasJpaRepository
 		            .getBillMasByConnectionId(csmrInfo.getCsIdn());
		    if(CollectionUtils.isNotEmpty(billList)) {
		    	Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
	            Boolean billUpToDateForGivenFinYear = billMasService.isBillUpToDateForGivenFinYear(billList, finYearId, csmrInfo.getCsMeteredccn());
			    if(!billUpToDateForGivenFinYear) {
			    	 respMsg = ApplicationSession.getInstance().getMessage(
		                        "Bill generation is not up to date for connection no. " + model.getRequestDTO().getConnectionNo() +
		                        " Please generate the bills and pay any dues to proceed with usage conversion");
		             model.setResultFound(false);
		             return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
			    }
	    
			    final ChangeOfUsageResponseDTO response = new ChangeOfUsageResponseDTO();
		        requestVo.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		        requestVo.setServiceId(model.getServiceId());
		        requestVo.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		        requestVo.setLangId((long) UserSession.getCurrent().getLanguageId());
		        requestVo.setConnectionNo(model.getRequestDTO().getConnectionNo());

		        final CustomerInfoDTO connectionDetail = waterCommonService.getConnectionDetailDto(requestVo.getOrgId(),
		                requestVo.getConnectionNo());
		        if (null != connectionDetail) {

		            List<Long> applicationIdList = changeOfUsageService.getChangeOfUsageApplicationId(requestVo.getOrgId(),
		                    connectionDetail.getCsIdn());
		            if (CollectionUtils.isNotEmpty(applicationIdList)) {
		                Long applicationId = applicationIdList.get(applicationIdList.size() - 1);
		                String status = waterDisconnectionService.getWorkflowRequestByAppId(applicationId, requestVo.getOrgId());
		                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
		                    respMsg = ApplicationSession.getInstance().getMessage(
		                            "water.change.of.usage.search.error");
		                    model.setResultFound(false);
		                    return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		                }
		            }

		            response.setCustomerInfoDTO(connectionDetail);
		            model.setResultFound(true);
		            model.setDualReturn(false);
		            final ChangeOfUsageDTO dto = getModel().getRequestDTO().getChangeOfUsage();
		            dto.setOldTrmGroup1(response.getCustomerInfoDTO().getTrmGroup1());
		            dto.setOldTrmGroup2(response.getCustomerInfoDTO().getTrmGroup2());
		            dto.setOldTrmGroup3(response.getCustomerInfoDTO().getTrmGroup3());
		            dto.setOldTrmGroup4(response.getCustomerInfoDTO().getTrmGroup4());
		            dto.setOldTrmGroup5(response.getCustomerInfoDTO().getTrmGroup5());
		            model.setCustomerInfoDTO(response.getCustomerInfoDTO());
		            model.getRequestDTO().setChangeOfUsage(dto);
		            model.getRequestDTO().setConnectionSize(response.getCustomerInfoDTO().getCsCcnsize());
		            model.getEntity().setCsMeteredccn(response.getCustomerInfoDTO().getCsMeteredccn());
		        } else {
		            /*
		             * getModel().addValidationError(ApplicationSession.getInstance().getMessage( "water.discon.norecod", new Object[]
		             * { requestVo.getConnectionNo() }));
		             */
		            respMsg = ApplicationSession.getInstance().getMessage(
		                    "No Record Found for Connection No." + " " + model.getRequestDTO().getConnectionNo());
		            model.setResultFound(false);
		            return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		        }    
		    } 
        } else if (!model.hasValidationErrors()) {
            final ChangeOfUsageResponseDTO response = new ChangeOfUsageResponseDTO();
            final ChangeOfUsageRequestDTO requestVo = model.getRequestDTO();
            requestVo.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestVo.setServiceId(model.getServiceId());
            requestVo.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            requestVo.setLangId((long) UserSession.getCurrent().getLanguageId());
            requestVo.setConnectionNo(model.getRequestDTO().getConnectionNo());

            final CustomerInfoDTO connectionDetail = waterCommonService.getConnectionDetailDto(requestVo.getOrgId(),
                    requestVo.getConnectionNo());
            if (null != connectionDetail) {

				/*
				 * List<Long> applicationIdList =
				 * changeOfUsageService.getChangeOfUsageApplicationId(requestVo.getOrgId(),
				 * connectionDetail.getCsIdn());
				 */
                String trfLookupCode1=null;
                try {
                 trfLookupCode1 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(connectionDetail.getCsCcnstatus(), UserSession.getCurrent().getOrganisation().getOrgid(), "CNS").getLookUpCode();
                }
                catch(Exception e){
                	
                }
                if (connectionDetail.getApplicationNo()!=null) {
	                Long applicationId = connectionDetail.getApplicationNo();
	                String status = waterDisconnectionService.getWorkflowRequestByAppId(applicationId, requestVo.getOrgId());
	                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
	                    respMsg = ApplicationSession.getInstance().getMessage(
	                            "water.change.of.usage.search.error");
	                    model.setResultFound(false);
	                    return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
	                }
	            }

                else if(( 
                		 Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) && trfLookupCode1!=null && trfLookupCode1.equalsIgnoreCase("D")) {
                	respMsg = ApplicationSession.getInstance().getMessage(
                            "water.DeptCOO.ConNo") + MainetConstants.WHITE_SPACE  + " "+   model.getRequestDTO().getConnectionNo() +" "+ ApplicationSession.getInstance().getMessage(
                                    "changeofowner.searchCriteria.disconnected");
                    model.setResultFound(false);
                    return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
                	
                }
               
                
				List<TbBillMas> billPendingList = billMasterService
						.getBillMasterListByUniqueIdentifier(connectionDetail.getCsIdn(), requestVo.getOrgId());

				if (CollectionUtils.isNotEmpty(billPendingList)) {
					final ServiceMaster service = serviceMaster.getServiceByShortName(CHANGE_OF_USAGESERVICE_CODE, requestVo.getOrgId());
					
					respMsg=(ApplicationSession.getInstance().getMessage("Dues.are.pending.against.the.connection.number") + " " + requestVo.getConnectionNo()+" "+ApplicationSession.getInstance().getMessage("unable.to.proceed.with.the.service"));
                    model.setResultFound(false);
                    return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
				}
                response.setCustomerInfoDTO(connectionDetail);
                model.setResultFound(true);
                model.setDualReturn(false);
                final ChangeOfUsageDTO dto = getModel().getRequestDTO().getChangeOfUsage();
                dto.setOldTrmGroup1(response.getCustomerInfoDTO().getTrmGroup1());
                dto.setOldTrmGroup2(response.getCustomerInfoDTO().getTrmGroup2());
                dto.setOldTrmGroup3(response.getCustomerInfoDTO().getTrmGroup3());
                dto.setOldTrmGroup4(response.getCustomerInfoDTO().getTrmGroup4());
                dto.setOldTrmGroup5(response.getCustomerInfoDTO().getTrmGroup5());
                dto.setNewCsMeteredccn(response.getCustomerInfoDTO().getCsMeteredccn());
                String trfLookupCode = null;
				try {
					trfLookupCode=CommonMasterUtility.getHierarchicalLookUp(dto.getOldTrmGroup2(),
						UserSession.getCurrent().getOrganisation().getOrgid()).getLookUpCode();
				}
				catch(Exception e){
					
				}
				if (trfLookupCode!=null && (trfLookupCode.equalsIgnoreCase(MainetConstants.WATER_DOMESTIC) || trfLookupCode.equalsIgnoreCase(MainetConstants.WATER_DOM_DM) || trfLookupCode.equalsIgnoreCase(MainetConstants.SEWER_DOMESTIC)) && Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
					respMsg = ApplicationSession.getInstance().getMessage(
                            "water.arrear.validmsg");
                    model.setResultFound(false);
					/*
					 * getModel().addValidationError(ApplicationSession.getInstance().getMessage(
					 * "water.arrear.validmsg"));
					 */
					 return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);

				}
				 
	            
                model.setCustomerInfoDTO(response.getCustomerInfoDTO());
                model.getRequestDTO().setChangeOfUsage(dto);
                model.getRequestDTO().setConnectionSize(response.getCustomerInfoDTO().getCsCcnsize());
                if(model.getEntity()!=null && response.getCustomerInfoDTO()!=null)
                model.getEntity().setCsMeteredccn(response.getCustomerInfoDTO().getCsMeteredccn());
            } else {
                /*
                 * getModel().addValidationError(ApplicationSession.getInstance().getMessage( "water.discon.norecod", new Object[]
                 * { requestVo.getConnectionNo() }));
                 */
                respMsg = ApplicationSession.getInstance().getMessage(
                        "No Record Found for Connection No." + " " + model.getRequestDTO().getConnectionNo());
                model.setResultFound(false);
                return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
            }
        } 
        modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        return modelAndView;

    }

    @RequestMapping(method = { RequestMethod.POST }, params = GET_CHECK_LIST_AND_CHARGES)
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView modelAndView = null;
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
        	getModel().validateConnectionNo();
        }else {
            getModel().validateApplicantDetail();
            getModel().validateChangeDetail();

        }
        if (getModel().hasValidationErrors()) {

            modelAndView = new ModelAndView(CHANGE_OF_USAGE_FORM_VIEW, MainetConstants.FORM_NAME, getModel());
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    getModel().getBindingResult());
            return modelAndView;
        }

        try {

            if (!getModel().hasValidationErrors()) {
                getModel().findApplicableCheckListAndCharges(getModel().getServiceId(), orgId);
                getModel().setEnableSubmit(true);
            }
            modelAndView = new ModelAndView(CHANGE_OF_USAGE_FORM_VIEW, MainetConstants.FORM_NAME, getModel());
            if (getModel().getBindingResult() != null) {
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());
            }
        } catch (final Exception ex) {
            LOGGER.error(GET_CHECK_LIST_AND_CHARGES, ex);
            modelAndView = defaultExceptionFormView();
        }
        getModel().setDualReturn(false);
        getModel().setEnableSubmit(true);
        return modelAndView;
    }

    @RequestMapping(params = "saveScrutinyForm", method = RequestMethod.POST)
    public ModelAndView searchAllDefaulter(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final ChangeOfUsageModel model = getModel();

        try {
            if (model.saveScrutinyForm()) {

                return jsonResult(JsonViewObject.successResult("Edited Successfully"));
            } else {

            }
        } catch (final Exception ex) {
            logger.error(ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        return defaultMyResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "resetConnectionRecord")
    public ModelAndView resetConnectionRecord(final HttpServletRequest httpServletRequest) {
        final ChangeOfUsageModel model = getModel();
        model.setCustomerInfoDTO(new CustomerInfoDTO());
        model.getRequestDTO().setChangeOfUsage(new ChangeOfUsageDTO());
        model.setResultFound(false);
        return new ModelAndView(CHANGE_OF_USAGE_FORM_VIEW, MainetConstants.CommonConstants.COMMAND, model);
    }

    @RequestMapping(params = "proceed", method = { RequestMethod.POST })
    public ModelAndView proceedAfterEdit(HttpServletRequest request) {
        getModel().bind(request);
        return new ModelAndView("ChangeOfUsageViewAfterEdit", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }
    
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);
        final ChangeOfUsageModel model = getModel();
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
        final ChangeOfUsageModel model = getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.getRequestDTO().setOrgId(orgId);
        final String connectionNo = model.getRequestDTO().getConnectionNo();
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
