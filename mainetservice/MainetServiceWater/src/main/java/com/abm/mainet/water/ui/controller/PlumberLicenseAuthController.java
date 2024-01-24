/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/PlumberLicenseAuth.html")
public class PlumberLicenseAuthController extends AbstractFormController<PlumberLicenseFormModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlumberLicenseAuthController.class);

    @Autowired
    IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    ServiceMasterService iServiceMasterService;

    @Autowired
    PlumberLicenseService plumberLicenseService;
    @Resource
    private IFileUploadService fileUpload;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView executeChangeOfOwner(@RequestParam("appNo") final Long applicationId,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            final HttpServletRequest request) {
    	try {
          getData( applicationId,  actualTaskId,request) ;
    	}catch (final Exception ex) {
            LOGGER.error("Problem while rendering form:", ex);
           return defaultExceptionFormView();
        }
        return new ModelAndView("PlumberLicenseAuthorisation", MainetConstants.CommonConstants.COMMAND, getModel());
        
    }
    
    @Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {	
    	try {
    	  getData( Long.valueOf(applicationId), taskId,httpServletRequest) ;   
    	}catch (final Exception ex) {
            LOGGER.error("Problem while rendering form:", ex);
           return defaultExceptionFormView();
        }
        return new ModelAndView("PlumberLicenseAuthorisationView", MainetConstants.FORM_NAME, getModel());
	}
    
    
    public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
    	sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
    	fileUpload.sessionCleanUpForFileUpload();
        PlumberLicenseFormModel model = this.getModel();
        model.getWorkflowActionDto().setTaskId(actualTaskId);
        model.getWorkflowActionDto().setApplicationId(applicationId);
        model.setApplicationId(applicationId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final ServiceMaster service = iServiceMasterService
                .getServiceMasterByShortCode(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE, orgId);
        model.setServiceId(service.getSmServiceId());
        model.setServiceCode(service.getSmShortdesc());
        model.setSmScrutinyChargeFlag(service.getSmScrutinyChargeFlag());
        model.setFromEvent(true);
        this.getModel()
                .setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
        model.populateApplicationData(applicationId);

        this.getModel().setPayableFlag(MainetConstants.FlagN);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		JsonViewObject respObj = null;
		this.bindModel(httpServletRequest);
		PlumberLicenseFormModel model = this.getModel();
		String decision = model.getWorkflowActionDto().getDecision();
		Long taskId = model.getWorkflowActionDto().getTaskId();
		Long applicationId = model.getWorkflowActionDto().getApplicationId();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		model.getPlumberLicenseReqDTO().setOrgId(orgId);
		model.getPlumberLicenseReqDTO().setApplicationId(applicationId);
		model.getPlumberLicenseReqDTO().setLangId(Long.valueOf(langId));
		if (model.getWorkflowActionDto().getDecision() == null
				|| model.getWorkflowActionDto().getDecision().isEmpty()) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.decision"));
			return defaultMyResult();
		}
		if (model.getWorkflowActionDto().getComments() == null
				|| model.getWorkflowActionDto().getComments().isEmpty()) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.comment"));
			return defaultMyResult();
		}
		model.setServiceName(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE);
		final ServiceMaster serviceId = iServiceMasterService
				.getServiceMasterByShortCode(MainetConstants.WaterServiceShortCode.PlUMBER_LICENSE, orgId);
		model.setServiceId(serviceId.getSmServiceId());

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(applicationId.toString());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(MainetConstants.WATER_DEPT);
		requestDTO.setServiceId(serviceId.getSmServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO.setDeptId(serviceId.getTbDepartment().getDpDeptid());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		model.setAttachments(fileUpload.prepareFileUpload(docs));
		fileUpload.doFileUpload(model.getAttachments(), requestDTO);
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(applicationId.toString(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		model.getWorkflowActionDto().setAttachementId(attacheMentIds);

		boolean lastProcess = iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId);
		model.setFinalProcess(lastProcess);
		if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED) && lastProcess && StringUtils.equalsIgnoreCase(model.getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {
		    boolean apprvlFlag = model.plumberUpdateAction();
                    if (apprvlFlag)
                    respObj = JsonViewObject.successResult(
                            ApplicationSession.getInstance().getMessage("Loi.charges.generated.succeesfully")
                                    + ApplicationSession.getInstance().getMessage (model.getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber()));
                    else
                      respObj = JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));

		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
			boolean apprvlFlag = model.plumberUpdateAction();
			if (apprvlFlag)
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.approved"));
			else
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));
		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
			model.plumberUpdateAction();
			/*
			 * // plumberLicenseService.sendSMSandEMail(applicantDto, applicationNo,
			 * payAmount, // serviceShortCode, organisation);
			 */
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("water.application.reject"));
		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("water.application.sendback"));
		}
		return jsonResult(respObj);
	}

    //D#91264
    @RequestMapping(params = "checkLastApproval", method = RequestMethod.POST)
    public ModelAndView checkLastApproval(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        PlumberLicenseFormModel model = this.getModel();
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("water.decision"));

        }
        if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("water.comment"));

        }
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        boolean lastApproval = workFlowTypeService
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
        if (lastApproval
                && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(model.getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {
            if (model.saveLoiData()) {

                model.setPayableFlag(MainetConstants.FlagY);
                List<TbLoiDet> loiDetailList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(model.getLoiDetail())) {
                    Double totalAmount = new Double(0);

                    for (TbLoiDet detail : model.getLoiDetail()) {

                        TbLoiDet loiDetail = new TbLoiDet();
                        BeanUtils.copyProperties(detail, loiDetail);
                        String taxDesc = ApplicationContextProvider.getApplicationContext()
                                .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
                                        UserSession.getCurrent().getOrganisation().getOrgid());
                        loiDetail.setLoiRemarks(taxDesc);
                        totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
                        loiDetailList.add(loiDetail);
                        model.setTotalLoiAmount(totalAmount);
                    }
                }
                model.setLoiDetail(loiDetailList);
            } else {
                model.addValidationError(getApplicationSession()
                        .getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));
            }
        } else {
            this.getModel().setPayableFlag(MainetConstants.FlagN);
        }
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

        mv = defaultMyResult();
        return mv;

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "printRejectionReport", method = RequestMethod.POST)
    public ModelAndView printRejAppReport(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        PlumberLicenseFormModel model = this.getModel();
        String fileName = MainetConstants.PlumberJasperFiles.REJECTION_JRXML.getColDescription();
        Map oParms = new HashMap();
        oParms.put("APM_APPLICATION_ID", model.getPlumberLicenseReqDTO().getApplicationId().toString());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String menuURL = MainetConstants.URLBasedOnShortCode.PLCA.getUrl();
        String type = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
        String path = plumberLicenseService.generateJasperReportPDF(model.getPlumberLicenseReqDTO(), outputStream,
                oParms, fileName, menuURL, type);
        if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
            getModel().setFilePath(path);
        }
        getModel().setRedirectURL("AdminHome.html");
        return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, "command", model);
    }

}
