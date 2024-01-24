package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
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
 * @since 10 september 2019
 */
@Controller
@RequestMapping("/AgencyRegistrationRenewalAuth.html")
public class AgencyRegistrationRenewalApprovalController extends AbstractFormController<AgencyRegistrationModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgencyRegistrationRenewalApprovalController.class);
    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IWorkFlowTypeService workFlowTypeService;

    @Autowired
    private IAdvertiserMasterService advertiserMasterservice;
    @Autowired
    private TbApprejMasService tbApprejMasService;

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
    public ModelAndView executeAgencyRenewalRegistration(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
            @RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
            HttpServletRequest request) {

        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().bind(request);

        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = serviceMaster
                .getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
        }
        this.getModel().setServiceId(serviceDto.getSmServiceId());
        this.getModel().setServiceName(serviceDto.getSmServiceName());
        this.getModel().setApmApplicationId(applicationId);
        try {
            this.getModel().populateApplicationData(applicationId);
        } catch (Exception exception) {
            LOGGER.error("Error While Rendoring the form", exception);
            return defaultExceptionFormView();
        }
        this.getModel().setPayableFlag(MainetConstants.FlagN);
        // START Defect#126605
        ServiceMaster service = serviceMaster.getServiceMaster(Long.valueOf(taskId),
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (service != null) {
            LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM, UserSession.getCurrent().getOrganisation());
            getModel().setRemarkList(tbApprejMasService.findByRemarkType(Long.valueOf(taskId), artTypeLookUp.getLookUpId()));
        }// END Defect#126605
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_RENEWAL_APPROVAL,
                MainetConstants.CommonConstants.COMMAND, getModel());

    }

    @Override
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = { RequestMethod.POST })
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
                this.getModel().getAgencyRequestDto().getMasterDto().setAgencyStatus(MainetConstants.FlagA);
                advertiserMasterservice
                        .updateAdvertiserMasterData(this.getModel().getAgencyRequestDto().getMasterDto());
                model.sendSmsEmail(model, MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_REN_AUTH_SMS_URL,
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
                model.sendSmsEmail(model, MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_REN_AUTH_SMS_URL,
                        MainetConstants.AdvertisingAndHoarding.REJECTED_MESSAGE);
                responseObj = JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("Application rejected successfully"));
            } else {
                responseObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("Application rejection failed"));
            }

        }

        return jsonResult(responseObj);

    }

    @RequestMapping(params = "checkLastApproval", method = RequestMethod.POST)
    public ModelAndView checkLastApproval(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        AgencyRegistrationModel model = this.getModel();
        ModelAndView mv = new ModelAndView();
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

                    model.getLoiDetail().forEach(detail -> {
                        Double totalAmount = new Double(0);
                        TbLoiDet loiDetail = new TbLoiDet();
                        BeanUtils.copyProperties(detail, loiDetail);
                        String taxDesc = ApplicationContextProvider.getApplicationContext()
                                .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
                                        UserSession.getCurrent().getOrganisation().getOrgid());
                        loiDetail.setLoiRemarks(taxDesc);
                        totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
                        loiDetailList.add(loiDetail);
                        model.setTotalLoiAmount(totalAmount.doubleValue());
                    });

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
