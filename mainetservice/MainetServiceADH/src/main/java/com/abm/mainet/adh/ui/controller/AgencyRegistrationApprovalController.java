package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

/**
 * @author cherupelli.srikanth
 * @since 16-08-2019
 */

@Controller
@RequestMapping("/AgencyRegistrationAuth.html")
public class AgencyRegistrationApprovalController extends AbstractFormController<AgencyRegistrationModel> {

    private static final Logger logger = LoggerFactory.getLogger(AgencyRegistrationApprovalController.class);
    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @Autowired
    private IAdvertiserMasterService advertiserMasterservice;

    @Autowired
    private TbApprejMasService tbApprejMasService;

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
    public ModelAndView executeAgencyRegistration(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
            @RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
            final HttpServletRequest request) {
        try {
            getData(applicationId, taskId, actualTaskId, request);
            /*
             * this.getModel().setOwnerCategoryLookUp(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.
             * ADVERTISEMENT_AND_HOARDING_PREFIX.Partnership, MainetConstants.AdvertisingAndHoarding.AOT,
             * UserSession.getCurrent().getOrganisation()));
             * this.getModel().getAgencyRequestDto().getMasterDtolist().get(0).setTrdFtype(this.getModel().getOwnerCategoryLookUp(
             * ).getLookUpId());
             */

        } catch (Exception exception) {
            logger.error("Error While Rendoring the form", exception);
            return defaultExceptionFormView();
        }
        // this.getModel().setViewMode(MainetConstants.FlagV);
        // START Defect#126605
        ServiceMaster service = serviceMaster.getServiceMaster(Long.valueOf(taskId),
                UserSession.getCurrent().getOrganisation().getOrgid());
        
        if (service != null) {
            LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM, UserSession.getCurrent().getOrganisation());
            getModel().setRemarkList(tbApprejMasService.findByRemarkType(Long.valueOf(taskId), artTypeLookUp.getLookUpId()));
        }// END Defect#126605
        
		
        LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(this.getModel().getAgencyRequestDto().getMasterDto().getAgencyCategory(), "AGC", UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getAgencyRequestDto().getMasterDto()
		.setAgencyCategory(lookUp.getLookUpId());
       

        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_APPROVAL,
                MainetConstants.CommonConstants.COMMAND, getModel());

    }

    @Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
            @RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
            final HttpServletRequest httpServletRequest) throws Exception {
        try {
            getData(Long.valueOf(applicationId), String.valueOf(serviceId), taskId, httpServletRequest);
        } catch (Exception exception) {
            logger.error("Error While Rendoring the form", exception);
            return defaultExceptionFormView();
        }
        return new ModelAndView("AgencyRegistrationApprovalView", MainetConstants.FORM_NAME, getModel());
    }

    public void getData(Long applicationId, String taskId, Long actualTaskId, HttpServletRequest httpServletRequest)
            throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().bind(httpServletRequest);

        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = serviceMaster
                .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
            this.getModel().setServiceId(serviceDto.getSmServiceId());
            this.getModel().setServiceName(serviceDto.getSmServiceName());
        }

        this.getModel().setApmApplicationId(applicationId);

        this.getModel().populateApplicationData(applicationId);
        this.getModel().setDocumentListAtchdAtApprovalTm(
                ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class)
                        .getDocumentUploadedAtApprovalTimeInAgencyApproval(applicationId,
                                UserSession.getCurrent().getOrganisation().getOrgid()));

        this.getModel().setPayableFlag(MainetConstants.FlagN);
    }

    @Override
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = RequestMethod.POST)
    public ModelAndView saveform(HttpServletRequest request) {
        JsonViewObject responseObj = null;
        this.getModel().bind(request);

        AgencyRegistrationModel model = this.getModel();
        if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("adh.approval.decision"));

            return defaultMyResult();
        }

        if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("adh.approval.remarks"));

            return defaultMyResult();
        }

        RequestDTO requestDto = new RequestDTO();
        requestDto.setReferenceId(String.valueOf(model.getApmApplicationId()));
        requestDto.setDepartmentName(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
        requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDto.setServiceId(model.getServiceId());
        requestDto.setDeptId(model.getServiceMaster().getTbDepartment().getDpDeptid());
        requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDto.setApplicationId(model.getApmApplicationId());
        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);
        model.setApprovalDocumentAttachment(fileUpload.prepareFileUpload(docs));
        fileUpload.doFileUpload(model.getApprovalDocumentAttachment(), requestDto);
        List<Long> documentIds = ApplicationContextProvider.getApplicationContext()
                .getBean(IChecklistVerificationService.class)
                .fetchAllAttachIdByReferenceId(String.valueOf(model.getApmApplicationId()),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        model.getWorkflowActionDto().setAttachementId(documentIds);
        boolean lastApproval = workFlowTypeService
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());

        if (lastApproval
                && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {
            // START D#126605
        	
            TbLoiMas tbLoiMas = ApplicationContextProvider.getApplicationContext().getBean(TbLoiMasService.class).findById(
                    this.getModel().getLoiDetail().get(0).getLoiMasId(), UserSession.getCurrent().getOrganisation().getOrgid());
            if (CollectionUtils.isNotEmpty(this.getModel().getRemarkList())) {
                List<String> remList = new ArrayList<>();
                for (TbApprejMas rem : this.getModel().getRemarkList()) {
                    if (rem != null) {
                        remList.add(rem.getArtRemarks());
                    }
                }
                tbLoiMas.setLoiRemark(StringUtils.join(remList, ","));
            }
            ApplicationContextProvider.getApplicationContext().getBean(TbLoiMasService.class).update(tbLoiMas);
            // END D#126605
            boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
            if (approvalFlag) {
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("Loi charges generated succeesfully. Your LOI No : "
                                + model.getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber()));

            } else {
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("Loi charges not found. Please define rule"));
            }

        } else if (lastApproval
                && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
                        MainetConstants.FlagN)) {
            if (model.closeWorkFlowTask()) {
                String agencyLicNum = advertiserMasterservice.generateAgencyLicenceNumber(
                        UserSession.getCurrent().getOrganisation(),
                        this.getModel().getAgencyRequestDto().getMasterDto().getAgencyCategory());
                this.getModel().getAgencyRequestDto().getMasterDto().setAgencyLicNo(agencyLicNum);
                this.getModel().getAgencyRequestDto().getMasterDto().setAgencyStatus(MainetConstants.FlagA);
                this.getModel().getAgencyRequestDto().getMasterDto().setAgencyLicIssueDate(new Date());
                advertiserMasterservice
                        .updateAdvertiserMasterData(this.getModel().getAgencyRequestDto().getMasterDto());
                model.sendSmsEmail(model, MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_AUTH_SMS_URL,
                        PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL);

                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
            } else {
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
            }

        } else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                MainetConstants.WorkFlow.Decision.APPROVED)) {

            boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
            if (approvalFlag) {
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
            } else {
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
            }

        } else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                MainetConstants.WorkFlow.Decision.REJECTED)) {
            boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
            if (approvalFlag) {
                model.sendSmsEmail(model, MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_AUTH_SMS_URL,
                        MainetConstants.AdvertisingAndHoarding.REJECTED_MESSAGE);
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("Application rejected successfully"));
            } else {
                responseObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("Application rejected Failed"));
            }

        }

        return jsonResult(responseObj);

    }

    @RequestMapping(params = "checkLastApproval", method = RequestMethod.POST)
    public ModelAndView checkLastApproval(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        AgencyRegistrationModel model = this.getModel();
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("adh.approval.decision"));

        }
        if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("adh.approval.remarks"));

        }
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        boolean lastApproval = workFlowTypeService
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
        if (lastApproval
                && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
                        MainetConstants.WorkFlow.Decision.APPROVED)
                && StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
                        MainetConstants.FlagY)) {
            model.setSuccessFlag(MainetConstants.FlagY);
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
            model.setSuccessFlag(MainetConstants.FlagN);
            this.getModel().setPayableFlag(MainetConstants.FlagN);
        }
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

        mv = defaultMyResult();
        return mv;

    }

}
