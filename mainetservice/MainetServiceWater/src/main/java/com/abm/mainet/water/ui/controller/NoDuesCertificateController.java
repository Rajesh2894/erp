package com.abm.mainet.water.ui.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

@Controller
@RequestMapping("/NoDuesCertificateController.html")
public class NoDuesCertificateController extends AbstractFormController<NoDuesCertificateModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoDuesCertificateController.class);

    @Resource
    private IFileUploadService fileUpload;

    @Autowired
	ServiceMasterService serviceMasterService;
    
    @Resource
    private WaterNoDuesCertificateService noDuesCertificateService;

    @Resource
    private TbFinancialyearService financialyearService;

    @Autowired
    private BRMSWaterService brmsWaterService;

    @Autowired
    ISMSAndEmailService smsAndEmailService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    EntityValidationContext<NoDuesCertificateModel> entityValidationContext;

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        if (charges != null) {
            for (final ChargeDetailDTO charge : charges) {
                amountSum = amountSum + charge.getChargeAmount();
            }
        }
        return amountSum;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getCharges", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {

        getModel().bind(httpServletRequest);
        ModelAndView mv = null;

        final NoDuesCertificateModel model = getModel();

        NoDuesCertificateReqDTO reqDTO = model.getReqDTO();
        NoDuesCertificateRespDTO responseDTO = model.getResponseDTO();
        reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        // [START] BRMS call initialize model

        if (model.validateInputs()) {
            final WSRequestDTO dto = new WSRequestDTO();
            dto.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
            WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
                final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
                final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
                final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
                populateCheckListModel(model, checkListModel2);
                dto.setDataModel(checkListModel2);
                /*
                 * response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL); if
                 * (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response. getWsStatus())) { final List<?> docs =
                 * RestClient.castResponse(response, DocumentDetailsVO.class); model.setCheckList((List<DocumentDetailsVO>) docs);
                 * // checklist done
                 */
                String checkListApplFlag = model.getCheckListApplFlag();
                if (checkListApplFlag.equals("A")) {
                    WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(dto);
                    if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                        List<DocumentDetailsVO> checkListList = Collections.emptyList();
                        checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

                        long cnt = 1;
                        for (final DocumentDetailsVO doc : checkListList) {
                            doc.setDocumentSerialNo(cnt);
                            cnt++;
                        }
                        if ((checkListList != null) && !checkListList.isEmpty()) {
                            model.setCheckList(checkListList);
                        }
                    } else {
                        throw new FrameworkException(
                                "Problem while checking Application level charges and Checklist applicable or not.");
                    }
                }
                LookUp lookUp=null; try { 
                	lookUp =CommonMasterUtility.getValueFromPrefixLookUp("SCW", MainetConstants.ENV,
                				UserSession.getCurrent().getOrganisation());
                	} catch (Exception e) {
           		  
           		  }
                if(lookUp != null && StringUtils.equals(MainetConstants.FlagY,lookUp.getOtherField())) {
                	  model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
                      model.setFree(MainetConstants.PAYMENT.FREE);
                      model.getReqDTO().setCharges(0.0d);
                      model.getReqDTO().setFree(true);
                }else {
               
                WaterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                WaterRateMaster.setServiceCode("WND");
                WaterRateMaster.setDeptCode("WT");
                WaterRateMaster.setIsBPL(model.getApplicantDetailDto().getIsBPL());
                WaterRateMaster.setChargeApplicableAt(Long.toString(
                        CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
                                MainetConstants.NewWaterServiceConstants.CAA).getLookUpId()));
                WaterRateMaster.setUsageSubtype1(responseDTO.getUsageSubtype1());
                WaterRateMaster.setMeterType(CommonMasterUtility.getCPDDescription(Long.valueOf(responseDTO.getCsMeteredccn()),
                        PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
                WaterRateMaster.setRateStartDate(new Date().getTime());
                dto.setDataModel(WaterRateMaster);
                // / final WSResponseDTO res = RestClient.callBRMS(dto,
                // ServiceEndpoints.BRMSMappingURL.WATER_INITIALIZE_OTHER_FIELDS);
                final WSResponseDTO res = brmsWaterService.getApplicableTaxes(dto);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
                	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
                		if (!res.isFree()) {
                            final List<?> rates = RestClient.castResponse(res, WaterRateMaster.class);
                            final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
                            for (final Object rate : rates) {
                                WaterRateMaster master1 = (WaterRateMaster) rate;
                                master1 = populateChargeModel(model, master1);
                                requiredCHarges.add(master1);
                            }
                            dto.setDataModel(requiredCHarges);
                            final WSResponseDTO output = brmsWaterService.getApplicableCharges(dto);
                            List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) output.getResponseObj();

                            model.setDocumentSubmitted(true);
                            model.setFree(MainetConstants.PAYMENT.ONLINE);
                            model.setChargesInfo(detailDTOs);

                            model.setCharges((chargesToPay(detailDTOs)));
                            if (model.getCharges() == 0.0d) {
                                throw new FrameworkException("Service charge amountToPay cannot be " + model.getCharges()
                                        + " if service configured as Chargeable");
                            }
                            setChargeMap(model, detailDTOs);
                            model.getOfflineDTO().setAmountToShow(model.getCharges());
                            model.getReqDTO().setCharges(model.getCharges());

                        } else {
                            model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
                            model.setFree(MainetConstants.PAYMENT.FREE);
                            model.getReqDTO().setCharges(0.0d);
                        }
                      		
                	}
                	else {
                		if (res.isFree()) {
                            final List<?> rates = RestClient.castResponse(res, WaterRateMaster.class);
                            final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
                            for (final Object rate : rates) {
                                WaterRateMaster master1 = (WaterRateMaster) rate;
                                master1 = populateChargeModel(model, master1);
                                requiredCHarges.add(master1);
                            }
                            dto.setDataModel(requiredCHarges);
                            final WSResponseDTO output = brmsWaterService.getApplicableCharges(dto);
                            List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) output.getResponseObj();

                            model.setDocumentSubmitted(true);
                            model.setFree(MainetConstants.PAYMENT.ONLINE);
                            model.setChargesInfo(detailDTOs);

                            model.setCharges((chargesToPay(detailDTOs)));
                            if (model.getCharges() == 0.0d) {
                                throw new FrameworkException("Service charge amountToPay cannot be " + model.getCharges()
                                        + " if service configured as Chargeable");
                            }
                            setChargeMap(model, detailDTOs);
                            model.getOfflineDTO().setAmountToShow(model.getCharges());
                            model.getReqDTO().setCharges(model.getCharges());

                        } else {
                            model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
                            model.setFree(MainetConstants.PAYMENT.FREE);
                            model.getReqDTO().setCharges(0.0d);
                        }
                      		
                	}
                    
                    mv = new ModelAndView("NoDuesCertificateFormValid", MainetConstants.FORM_NAME, getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                            getModel().getBindingResult());
                }
                
                else {
                    mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
                }
                /*
                 * else { mv = new ModelAndView( PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW); }
                 */
                }
            }
        }
        if (getModel().getBindingResult() != null) {
            mv = new ModelAndView("NoDuesCertificateFormValid", MainetConstants.FORM_NAME, getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        // mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
        // getModel().getBindingResult());

        return mv;
    }

    @RequestMapping(params = "getConnectionDetail", method = RequestMethod.POST)
    @ResponseBody
    public NoDuesCertificateReqDTO getConnectionDetail(final HttpServletRequest request) {
        LOGGER.info("Start the getConnectionDetail()");
        getModel().bind(request);
        NoDuesCertificateReqDTO reqDTO = null;
        final NoDuesCertificateModel model = getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.getReqDTO().setOrgId(orgId);

        final String connectionNo = model.getReqDTO().getConsumerNo();
        try {
            if ((connectionNo != null) && !connectionNo.isEmpty() && (UserSession.getCurrent() != null)) {
                reqDTO = noDuesCertificateService.getConnectionDetailForNoDuesCer(UserSession.getCurrent(),
                        connectionNo, model.getReqDTO(), model);
                if (reqDTO.getDuesAmount()!=null && reqDTO.getDuesAmount() > 0) {
                    reqDTO.setDues(true);
                }
                reqDTO.setError(false);
                model.setReqDTO(reqDTO);
            }
        } catch (final Exception exception) {
            reqDTO = new NoDuesCertificateReqDTO();
            reqDTO.setError(true);
            model.setReqDTO(reqDTO);
            return model.getReqDTO();
        }
        return model.getReqDTO();
    }
    
    @RequestMapping(params = "getConnectionDetailForSKDCL", method = RequestMethod.POST) 
    public @ResponseBody NoDuesCertificateReqDTO getConnectionDetailForSKDCL(final HttpServletRequest request) {
    	LOGGER.info("Start the getConnectionDetail()");
        getModel().bind(request);
        NoDuesCertificateReqDTO reqDTO = null;
        final NoDuesCertificateModel model = getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.getReqDTO().setOrgId(orgId);
        final String connectionNo = model.getReqDTO().getConsumerNo();
        try {
            if ((connectionNo != null) && !connectionNo.isEmpty() && (UserSession.getCurrent() != null)) {
            	//D#146950
                reqDTO = noDuesCertificateService.getConnectionDetailForNoDuesCer(UserSession.getCurrent(),
                        connectionNo, model.getReqDTO(), model);
                reqDTO.setError(false);
                if (reqDTO.getDuesAmount()!=null && reqDTO.getDuesAmount() > 0) {
                    reqDTO.setDues(true);
                    reqDTO.setError(true);
                }
             
                model.setReqDTO(reqDTO);
            }
        } catch (final Exception exception) {
        	reqDTO = new NoDuesCertificateReqDTO();
            reqDTO.setError(true);
            model.setReqDTO(reqDTO);
            LOGGER.error("Error occured during call to no dues certificate " + exception.getMessage());           
        }
        return  reqDTO;
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
    	LOGGER.info("Start the index page of the NoDuesCertificateController");
	    sessionCleanup(httpServletRequest);
	    getModel().bind(httpServletRequest);
	    final NoDuesCertificateModel model = getModel();
	    ModelAndView mv = null;
	   // final Employee emp = UserSession.getCurrent().getEmployee();
	    final NoDuesCertificateReqDTO noDuesDto = model.getReqDTO();
		/*
		 * if (emp != null) {
		 * 
		 * model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
		 * model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpmname());
		 * model.getApplicantDetailDto().setApplicantLastName(emp.getEmplname());
		 * model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
		 * model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
		 * model.getApplicantDetailDto().setApplicantTitle(emp.getCpdTtlId()); if
		 * (emp.getEmppincode() != null) {
		 * model.getApplicantDetailDto().setPinCode(String.valueOf(emp.getEmppincode()))
		 * ; } }
		 */
	    noDuesDto.setApplicantDTO(model.getApplicantDetailDto());
	    noDuesCertificateService.setCommonField(UserSession.getCurrent().getOrganisation(), model);
	    /*
	     * final List<FinYearDTO> list = financialyearService.getFinancialYear(); if (list != null) { model.setFinYear(list); }
	     */
		
		  LookUp lookUp=null;
		  try { 
			  lookUp  =CommonMasterUtility.getValueFromPrefixLookUp("SCW", MainetConstants.ENV,
					  		UserSession.getCurrent().getOrganisation());
			  } catch (Exception e) {
		  
		  }
		  if(lookUp != null && StringUtils.equals(MainetConstants.FlagY,
				  lookUp.getOtherField())) 
		  {
			  model.setScrutinyFlag(MainetConstants.FlagA);
		  }
		  
		
	    mv = new ModelAndView("NoDuesCertificateForm", MainetConstants.FORM_NAME, getModel());
	    mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
	    return mv;
	}

    private WaterRateMaster populateChargeModel(final NoDuesCertificateModel model, final WaterRateMaster chargeModel) {

        chargeModel.setOrgId(model.getReqDTO().getOrgId());
        chargeModel.setServiceCode(PrefixConstants.NewWaterServiceConstants.WND);
        chargeModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
        chargeModel.setConnectionSize(Double.valueOf(CommonMasterUtility
                .getNonHierarchicalLookUpObject(model.getConnectionSize(), UserSession.getCurrent().getOrganisation())
                .getDescLangFirst()));
        chargeModel.setFactor1("NA");
        chargeModel.setNoOfCopies(model.getReqDTO().getNoOfCopies().intValue());
        return chargeModel;

    }

    private void populateCheckListModel(final NoDuesCertificateModel model, final CheckListModel checklistModel) {

        checklistModel.setOrgId(model.getReqDTO().getOrgId());
        checklistModel.setServiceCode("WND");
        checklistModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());

    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final NoDuesCertificateModel model = getModel();
        ModelAndView mv = null;
        List<DocumentDetailsVO> docs = model.getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        if (model.validateInputs() && model.validatePayment()) {
            final NoDuesCertificateReqDTO reqDTO = model.getReqDTO();
            final UserSession session = UserSession.getCurrent();
            reqDTO.setUserId(session.getEmployee().getEmpId());
            reqDTO.setLangId((long) session.getLanguageId());
            reqDTO.setUpdatedBy(session.getEmployee().getEmpId());
            reqDTO.setOrgId(session.getOrganisation().getOrgid());
            reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
            reqDTO.setDocumentList(docs);
            reqDTO.setServiceId(model.getServiceId());
            reqDTO.setApplicantDTO(model.getApplicantDetailDto());
            try {
                if ((model.getFree() != null) && !model.getFree().equals(MainetConstants.PAYMENT.FREE)) {
                    reqDTO.setPayMode(MainetConstants.PAYMENT.FREE);
                } else {
                    reqDTO.setPayMode(model.getOfflineDTO().getOnlineOfflineCheck());
                }
                reqDTO.setCharges(model.getCharges());
                final NoDuesCertificateRespDTO response = noDuesCertificateService.saveForm(reqDTO);

                if (response != null) {
                    if (response.getApplicationNo() != null) {
                        model.getResponseDTO().setApplicationNo(response.getApplicationNo());
                        noDuesCertificateService.initiateWorkflowForFreeService(reqDTO);
                        if ((model.getFree() != null) && !model.getFree().equals(MainetConstants.PAYMENT.FREE)) {
                            final CommonChallanDTO offline = model.getOfflineDTO();
                            model.populateNoDuesPaymentDetails(offline, model, session); 
                            if ((offline.getOnlineOfflineCheck() != null)
                                    && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
                                return jsonResult(JsonViewObject
                                        .successResult(getApplicationSession().getMessage("continue.forchallan")));
                            } else {
                                return jsonResult(JsonViewObject
                                        .successResult(getApplicationSession().getMessage("continue.forpayment")));
                            }
                        } else {
                            final String msg = MessageFormat.format(
                                    getApplicationSession().getMessage("water.noduescerti.success"),
                                    response.getApplicationNo().toString());
                            return jsonResult(JsonViewObject.successResult(msg));
                        }
                    }

                    else {
                        if (!response.getErrorList().isEmpty()) {
                            for (final String msg : response.getErrorList()) {
                                model.addValidationError(msg);
                            }
                        } else {
                            return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
                        }
                    }
                }
            }

            catch (final Exception exception) {
                logger.error("Exception found in save method: ", exception);
                return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
            }
        }
        mv = new ModelAndView("NoDuesCertificateFormValid", MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveExecutionForm")
    public ModelAndView saveExecutionForm(final HttpServletRequest httpServletRequest) {

        LOGGER.info("Start the saveExecutionForm()");
        try {
            getModel().bind(httpServletRequest);
            final NoDuesCertificateModel model = getModel();
            final Boolean flag = model.saveExeFormData();
            if (flag) {
                return new ModelAndView("NoDuesCertiFormPrint", MainetConstants.CommonConstants.COMMAND, getModel());
            } else {
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
            }
        } catch (final Exception exception) {
            logger.error("Exception found in saveExecutionForm method: ", exception);
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
    }

    /*
     * @RequestMapping(method = RequestMethod.POST, params = "noDueCertificatePrint") public ModelAndView noDueCertificatePrint(
     * final HttpServletRequest httpServletRequest ,
     * @RequestParam(value = "appId") final Long appId ) { LOGGER.info("Start the noDueCertificatePrint()"); try {
     * getModel().bind(httpServletRequest); final NoDuesCertificateModel model = getModel(); // model.setApmApplicationId(appId);
     * final NoDueCerticateDTO dto = noDuesCertificateService.getNoDuesApplicationData( model.getResponseDTO().getApplicationNo(),
     * UserSession.getCurrent().getOrganisation().getOrgid()); if (dto != null) {
     * dto.setApproveBy(UserSession.getCurrent().getEmployee().getEmploginname());
     * dto.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname()); model.setNodueCertiDTO(dto); return new
     * ModelAndView("NoDuesCertiFormPrint", MainetConstants.CommonConstants.COMMAND, getModel()); } } catch (final Exception
     * exception) { logger.error("Exception found in noDueCertificatePrint  method : ", exception); } return new
     * ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW); }
     */

    @RequestMapping(method = RequestMethod.POST, params = "noDueCertificatePrint")
    public ModelAndView noDueCertificatePrint(final HttpServletRequest httpServletRequest) {
        LOGGER.info("Start the noDueCertificatePrint()");
        try {
            getModel().bind(httpServletRequest);
            final NoDuesCertificateModel model = getModel();
            // model.setApmApplicationId(appId);
            final NoDueCerticateDTO dto = noDuesCertificateService.getNoDuesApplicationData(
                    model.getResponseDTO().getApplicationNo(), UserSession.getCurrent().getOrganisation().getOrgid());
            if (dto != null) {
                dto.setApproveBy(UserSession.getCurrent().getEmployee().getEmploginname());
                dto.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
                model.setNodueCertiDTO(dto);
                return new ModelAndView("NoDuesCertiFormPrint", MainetConstants.CommonConstants.COMMAND, getModel());
            }

        } catch (final Exception exception) {
            logger.error("Exception found in noDueCertificatePrint  method : ", exception);
        }
        return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
    }

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView showDetails(final HttpServletRequest httpServletRequest,
            @RequestParam(value = "appId") final Long appId, @RequestParam(value = "serviceId") final Long serviceId) {

        LOGGER.info("Start the showDetails()");
        getModel().bind(httpServletRequest);

        final NoDuesCertificateModel model = getModel();
        model.setApmApplicationId(appId);
        model.setServiceId(serviceId);
        final Employee emp = UserSession.getCurrent().getEmployee();
        if (emp != null) {

            model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
            model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpmname());
            model.getApplicantDetailDto().setApplicantLastName(emp.getEmplname());
            model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
            model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
            model.getApplicantDetailDto().setApplicantTitle(emp.getCpdTtlId());
            if (emp.getEmppincode() != null) {
                model.getApplicantDetailDto().setPinCode(String.valueOf(emp.getEmppincode()));
            }
        }
        model.setApproveEmpName(UserSession.getCurrent().getEmployee().getEmploginname());
        noDuesCertificateService.setCommonField(UserSession.getCurrent().getOrganisation(), model);
        model.setApplcationDeatil(appId, serviceId);
        model.setApmApplicationId(appId);
        model.setServiceId(serviceId);
        return new ModelAndView("NoDuesExecutionForm", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    private void setChargeMap(final NoDuesCertificateModel model, final List<ChargeDetailDTO> charges) {
        final Map<Long, Double> chargesMap = new HashMap<>();
        for (final ChargeDetailDTO dto : charges) {
            chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
        }
        model.setChargesMap(chargesMap);
    }
    

}
