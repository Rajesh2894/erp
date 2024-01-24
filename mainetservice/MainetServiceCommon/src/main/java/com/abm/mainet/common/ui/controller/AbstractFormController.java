package com.abm.mainet.common.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

public abstract class AbstractFormController<TModel extends AbstractFormModel> extends AbstractController<TModel> {
    protected final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;
    
    @Autowired
    private TbCfcApplicationMstService tbCfcservice;
    
    @Autowired
    private  IEmployeeService employeeServcie; 
    
    @Autowired
    private IReceiptEntryService receiptEntryService;
    
    @Resource
    private TbTaxMasService tbTaxMasService;
    @Autowired
    private PaymentService paymentService;
    
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);
        final TModel model = getModel();
        if (model.saveForm()) {
            if ((model.getSuccessMessage() != null) && !model.getSuccessMessage().isEmpty()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            } else {
                return jsonResult(
                        JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
            }
        }

        return (model.getCustomViewName() == null) || model.getCustomViewName().isEmpty() ? defaultMyResult()
                : customDefaultMyResult(model.getCustomViewName());

    }

    @RequestMapping(params = "PrintReport")
    public ModelAndView printRTIStatusReport(final HttpServletRequest request) {
        logger.info("Start the PrintReport()");
        try {
            bindModel(request);
            final TModel model = getModel();
            //US#134797
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
            	if (model.getReceiptDTO() != null && model.getReceiptDTO().getDeptShortCode() != null && model.getReceiptDTO().getReceiptNo() != null)
            	model.getReceiptDTO().setReceiptNo(model.getReceiptDTO().getDeptShortCode().concat(model.getReceiptDTO().getReceiptNo()));
            	
            	if(model.getReceiptDTO() != null && model.getReceiptDTO().getDeptShortCode() != null &&  MainetConstants.DEPT_SHORT_NAME.WATER.equals(model.getReceiptDTO().getDeptShortCode()) ) {
            			model.getReceiptDTO().setLoiNo(model.getOfflineDTO().getLoiNo());
            			model.getReceiptDTO().setBillYearDetails(model.getReceiptDTO().getBillYearDetails()); 
            			model.getReceiptDTO().setBillDetails(model.getReceiptDTO().getBillDetails());
            			model.getReceiptDTO().setSubject(model.getOfflineDTO().getServiceName());
        			}
            }
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_TSCL) && model.getReceiptDTO().getDeptShortCode() !=null && !model.getReceiptDTO().getDeptShortCode().isEmpty() && !model.getReceiptDTO().getDeptShortCode().equals("RL")){
            	model.getReceiptDTO().setReceiverName(UserSession.getCurrent().getEmployee().getFullName());
            	model.getReceiptDTO().setReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(model.getReceiptDTO().getFieldId(), model.getReceiptDTO().getServiceId(),Long.valueOf(model.getReceiptDTO().getReceiptNo()),model.getReceiptDTO().getRmDate(),UserSession.getCurrent().getOrganisation().getOrgid()));
            	if(model.getReceiptDTO().getPaymentList()!=null) {
					for (ChallanReportDTO rebateReceipt :model.getReceiptDTO().getPaymentList()) {
						String desc=tbTaxMasService.getAcHeadFromTaxMaster(rebateReceipt.getTaxId(),model.getReceiptDTO().getServiceId(),UserSession.getCurrent().getOrganisation().getOrgid());
						rebateReceipt.setDetails(desc);
					}
				}
            }
            
            if (model.getOfflineDTO().getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
                final CommonChallanDTO challanDTO = model.getOfflineDTO();
                final ApplicationFormChallanDTO challanDetails = iChallanService.getChallanData(challanDTO,
                        UserSession.getCurrent().getOrganisation());
                model.setChallanDTO(challanDetails);
                Long offlinePaymentMode = 0L;
                if (model.getOfflineDTO().getOflPaymentMode() != 0) {
                    offlinePaymentMode = model.getOfflineDTO().getOflPaymentMode();
                }
                final LookUp paymentModeLookup = model.getNonHierarchicalLookUpObject(offlinePaymentMode);
                final String offlinePayMode = paymentModeLookup.getLookUpCode();

                if (offlinePayMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCU_MODE)) {
                    return new ModelAndView(MainetConstants.COMMON_CHALLAN_ULB_REPORT, MainetConstants.FORM_NAME,
                            model);
                } else if (offlinePayMode.equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCB_MODE)) {
                    return new ModelAndView(MainetConstants.COMMON_CHALLAN_BANK_REPORT, MainetConstants.FORM_NAME,
                            model);
                }
            } else if (model.getOfflineDTO().getOnlineOfflineCheck()
                    .equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            	//#112656
            	try {
					LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
							UserSession.getCurrent().getOrganisation());
					CFCSchedulingCounterDet counterDet=null;
					if(lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
						counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(), 
							UserSession.getCurrent().getOrganisation().getOrgid());
						//Defect #130728  to show cfc employee name on receipt
						Employee empDto = employeeServcie.findEmployeeById(counterDet.getEmpId());
						if (empDto != null) {
						String empName = empDto.getEmpname().concat(" ").concat(empDto.getEmpmname()).concat(" ").concat(empDto.getEmplname()) ;
						model.getReceiptDTO().setUserName(empName);
						}
						//#147528- to get counter details with new format
						if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
						if (model.getReceiptDTO() != null) 
						if (StringUtils.isNotEmpty(model.getReceiptDTO().getCfcColCenterNo()))
						   counterDet.setCollcntrno(model.getReceiptDTO().getCfcColCenterNo());
						if (StringUtils.isNotEmpty(model.getReceiptDTO().getCfcColCounterNo()))
						    counterDet.setCounterno(model.getReceiptDTO().getCfcColCounterNo());
						}
						this.getModel().setCfcSchedulingCounterDet(counterDet);
						
					}
				} catch (Exception e) {
					logger.info("Exception occure while seting the Counter scheduling info:"+e);
				}
            	
            	if (model.getOfflineDTO().getChallanServiceType().equals(MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED)
                        || model.getOfflineDTO().getChallanServiceType()
                                .equals(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED)) {
					if (model.getOfflineDTO().getServiceCode() != null
							&& (model.getOfflineDTO().getServiceCode().equals("PBP")
									|| model.getOfflineDTO().getServiceCode().equals("BPW")
									|| model.getOfflineDTO().getServiceCode().equals("DES")
									|| model.getOfflineDTO().getServiceCode().equals("SAS")
									|| model.getOfflineDTO().getServiceCode().equals("CIA")
									|| model.getOfflineDTO().getServiceCode().equals("NCA"))) {
						model.getReceiptDTO().getPaymentList().sort(Comparator.comparing(
								ChallanReportDTO::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
            		 }
            		if(model.getOfflineDTO().getServiceCode().equals("CBP")) {
            			return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME, model);
            		}else {
            			return new ModelAndView("revenueReceiptPrint", MainetConstants.FORM_NAME, model);
            		}
                    
                }

                // D#77037
                if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_ASCL) && StringUtils.isNotEmpty(model.getReceiptDTO().getDeptShortCode()) && ( model.getReceiptDTO().getDeptShortCode().equals("ADH"))){
                	// model.getReceiptDTO().setTotalAmountPayable(model.getReceiptDTO().get);
           }else {
                if (model.getOfflineDTO().getServiceCode().equals("ACP")) {
                    model.getReceiptDTO().setTotalAmountPayable(model.getReceiptDTO().getAmount());
                }
                             
           }
            
           if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_ASCL) && StringUtils.isNotEmpty(model.getReceiptDTO().getDeptShortCode()) && ( model.getReceiptDTO().getDeptShortCode().equals("BND"))){
                	model.getReceiptDTO().setDwz1(model.getOfflineDTO().getApplicantAddress());
                	model.getReceiptDTO().setSubject(model.getReceiptDTO().getSubject()+model.getOfflineDTO().getApplicantName());
                	String ward =model.getOfflineDTO().getApplicantAddress();
                	String receiptno=model.getReceiptDTO().getReceiptNo();
                	
                	String wardreceiptno=ward+"/"+receiptno;
                   model.getReceiptDTO().setReceiptNo(wardreceiptno);
                   model.getReceiptDTO().setOccupierName(model.getOfflineDTO().getOccupierName());
                   model.getReceiptDTO().setDdOrPpDate(UtilityService.convertDateToDDMMYYYY(model.getOfflineDTO().getDdDate()));
                   model.getReceiptDTO().setUsageType1_L(model.getOfflineDTO().getRdV2());
                   model.getReceiptDTO().setUsageType2_L(model.getOfflineDTO().getRdV3());
                   model.getReceiptDTO().setDate(UtilityService.convertDateToDDMMYYYY(model.getOfflineDTO().getPoDate()));
                   
                	
           }

                return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME, model);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            logger.error("Exception while printing challan in offfline pay mode:", e);
        }
        return null;
    }

    /**
     * common method to display service charge details
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
    public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {

        return new ModelAndView(MainetConstants.CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND, getModel());
    }
    

    protected ModelAndView redirectToHome(final ActionResponse actResponse, final HttpServletRequest request,
            final String moduleUrl, final RedirectAttributes redirectAttributes, final String decision) {
        String message = MainetConstants.BLANK;
        if (actResponse != null) {
            if ((null != actResponse.getError()) && !MainetConstants.BLANK.equals(actResponse.getError())) {
                request.getSession().setAttribute(MainetConstants.DELETE_ERROR, actResponse.getError());
            } else {
                final String actionKey = actResponse.getResponseData(MainetConstants.RESPONSE);
                final String decision_ = actResponse.getResponseData(MainetConstants.DECISION);
                if (null != actionKey) {
                    final String requestNo = actResponse.getResponseData(MainetConstants.REQUEST_NO);
                    if (null != requestNo) {
                        message = MainetConstants.REQUEST_NUMBER + requestNo + " " + decision_
                                + MainetConstants.SUCCESSFULLY;
                        redirectAttributes.addFlashAttribute(MainetConstants.SUCCESS_MESSAGE, message);
                        redirectAttributes.addFlashAttribute(MainetConstants.ERROR_MESSAGE, actResponse.getErrorList());
                    }
                }
            }
        }
        return new ModelAndView("redirect:/" + moduleUrl);
    }

    protected ModelAndView redirectToModulePage(final String moduleUrl, final Long id, final String taskId,
            final String taskName) {
        String pageUrl = MainetConstants.BLANK;
        if ((null != taskId) && !MainetConstants.BLANK.equals(taskId)) {
            pageUrl = "redirect:/" + moduleUrl + "?";
        } else if ((null != id) && (id != 0)) {
            pageUrl = "redirect:/" + moduleUrl + "?";
        } else {
            pageUrl = "redirect:/" + moduleUrl + "?";
        }
        return new ModelAndView(pageUrl);
    }

    protected String getRequestParam(final String key, final HttpServletRequest request, final ModelMap model) {
        String value = request.getParameter(key) != null ? request.getParameter(key) : MainetConstants.BLANK;
        if (value.isEmpty()) {
            value = (String) (request.getSession().getAttribute(key) != null ? request.getSession().getAttribute(key)
                    : MainetConstants.BLANK);
            value = (String) (value == null ? request.getAttribute(key) : MainetConstants.BLANK);
        }
        if (value.isEmpty() && (model != null)) {
            value = (String) model.get(key) != null ? (String) model.get(key) : MainetConstants.BLANK;
        }
        return value;
    }

    @RequestMapping(params = "getWorkFlowActionDetail", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> getWorkFlowActionDetail(final HttpServletRequest httpServletRequest,
            @RequestParam(value = "decision") String decision, @RequestParam(value = "serEventId") String serEventId) {
        Map<String, String> map = null;

        this.getModel().getWorkflowActionDto().setSendBackToGroup(null);
        this.getModel().getWorkflowActionDto().setSendBackToLevel(null);

        if (this.getModel().getWorkflowActionDto().getApplicationId() == null
                && (this.getModel().getWorkflowActionDto().getReferenceId() == null
                        || this.getModel().getWorkflowActionDto().getReferenceId().equals(""))
                || this.getModel().getWorkflowActionDto().getTaskId() == null) {
            logger.error("Application No or ReferenceId or TaskId is not set");
        } else {
            WorkflowRequest workflowRequest = null;
            // Condition check for #34143
            if (this.getModel().getOrgId() != null) {
                workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(
                        this.getModel().getWorkflowActionDto().getApplicationId(),
                        this.getModel().getWorkflowActionDto().getReferenceId(),
                        this.getModel().getOrgId());
            } else {
                workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(
                        this.getModel().getWorkflowActionDto().getApplicationId(),
                        this.getModel().getWorkflowActionDto().getReferenceId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
            }
            if (decision.equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
                map = iWorkflowActionService.getEmpByworkFlowId(workflowRequest.getWorkflowTypeId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                this.getModel().setCheckActMap(map);
                this.getModel().getWorkflowActionDto().setForwardToEmployeeType(MainetConstants.WorkFlow.EMPLOYEE);
            } else if (decision.equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
                this.getModel().setCheckActMap(null);
                this.getModel().getWorkflowActionDto().setForwardToEmployee(null);
                this.getModel().getWorkflowActionDto().setForwardToEmployeeType(null);
                if (serEventId != null && !serEventId.isEmpty()) {
                    map = iWorkflowActionService.getEmpsByEvent(this.getModel().getWorkflowEventEmpList(), serEventId,
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    this.getModel().setCheckActMap(map);

                    this.getModel().getWorkflowActionDto().setSendBackToGroup(
                            Integer.parseInt(serEventId.split(MainetConstants.operator.UNDER_SCORE)[0]));
                    this.getModel().getWorkflowActionDto().setSendBackToLevel(
                            Integer.parseInt(serEventId.split(MainetConstants.operator.UNDER_SCORE)[1]));

                } else {
                    map = new HashMap<>();
                    Set<LookUp> lookList = iWorkFlowTypeService.getCheckerSendBackEventList(
                            workflowRequest.getWorkflowTypeId(), this.getModel().getWorkflowActionDto().getTaskId());
                    for (LookUp lookup : lookList) {
                        map.put(lookup.getLookUpCode(), lookup.getDescLangFirst());
                    }
                    this.getModel().setWorkflowEventEmpList(lookList);
                }
            }
        }
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.COMMON_FILE_ATTACHMENT)
    public ModelAndView commonFileAttachment(final HttpServletRequest request) {
        bindModel(request);

        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getCommonFileAttachment().size(); i++)
            attachments.add(new DocumentDetailsVO());
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getCommonFileAttachment().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }
        if (attachments.get(this.getModel().getCommonFileAttachment().size()).getDoc_DESC_ENGL() == null)
            attachments.get(this.getModel().getCommonFileAttachment().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }
        
        this.getModel().setCommonFileAttachment(attachments);
        return new ModelAndView(MainetConstants.COMMON_FILE_ATTACHMENT, MainetConstants.FORM_NAME, this.getModel());
    }

	@RequestMapping(params = "proceedToPayment")
	public ModelAndView proceedTopAyment(final HttpServletRequest request,final RedirectAttributes redirectAttributes) {
		try {
			bindModel(request);
			final TModel model = getModel();
			PaymentRequestDTO dto=new PaymentRequestDTO();
			getModel().redirectToPayDetails(request, dto);
			paymentService.proceesTransactionOnApplication(request, dto);
			getModel().setPaymentReqDto(dto);
		} catch (final Exception e) {
			e.printStackTrace();

		}
		if(this.getModel().getPaymentReqDto()!=null && StringUtils.equalsIgnoreCase(this.getModel().getPaymentReqDto().getPayModeorType(), "PayU")) {
			return new ModelAndView(MainetConstants.PayUView, MainetConstants.FORM_NAME, this.getModel());
		}else
		return new ModelAndView(MainetConstants.EGRASSVIEW, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "confirmToPay", method = RequestMethod.POST)
	public ModelAndView confirm(final HttpServletRequest request) {
		try {
			bindModel(request);
			final TModel model = getModel();
			String url=paymentService.generateNicGateWayRequestUrl(getModel().getPaymentReqDto(),getModel().getOfflineDTO(),request);
			getModel().getPaymentReqDto().setPayRequestMsg(url);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView(MainetConstants.Egrass_Redirect, MainetConstants.FORM_NAME, this.getModel());
	}
	 

}
