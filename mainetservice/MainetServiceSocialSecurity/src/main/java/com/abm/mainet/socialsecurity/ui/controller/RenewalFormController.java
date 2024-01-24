/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.service.RenewalFormService;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.model.RenewalFormModel;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("RenewalForm.html")
public class RenewalFormController extends AbstractFormController<RenewalFormModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private RenewalFormService renewalFormService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private SchemeApplicationFormRepository schemeApplicationFormRepository;

    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    private static final Logger LOGGER = Logger.getLogger(RenewalFormController.class);

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        
        List<RenewalFormDto> getAllData = renewalFormService.getAllData(UserSession.getCurrent().getOrganisation().getOrgid());
        if (CollectionUtils.isNotEmpty(getAllData)) {
            getAllData.forEach(singleData -> {
                StringBuilder benefnoNname = new StringBuilder();
                if (StringUtils.isNotBlank(singleData.getNameofApplicant())) {
                    benefnoNname.append(singleData.getNameofApplicant());
                }

                if (StringUtils.isNotBlank(singleData.getBeneficiaryno())) {
                    benefnoNname.append(" - " + singleData.getBeneficiaryno());
                }

                singleData.setBenefnoNname(benefnoNname.toString());

            });
        }
        this.getModel().setRenewalFormDtoList(getAllData);
        this.getModel().setCommonHelpDocs("RenewalForm.html");
        return index();
    }

    @RequestMapping(params = MainetConstants.SocialSecurity.SHOWDETAILS, method = RequestMethod.POST)
    public ModelAndView workorder(@RequestParam("appNo") final Long applicationId,
            @RequestParam(value = MainetConstants.SocialSecurity.ACTUAL_TASKID, required = false) final Long actualTaskId,
            @RequestParam(value = MainetConstants.SocialSecurity.TASK_ID, required = false) final Long serviceId,
            @RequestParam(value = MainetConstants.SocialSecurity.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.SocialSecurity.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        fileUpload.sessionCleanUpForFileUpload();
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        RenewalFormModel renmodel = this.getModel();
        RenewalFormDto dtoo = renmodel.getRenewalFormDto();
        renmodel.getWorkflowActionDto().setTaskId(actualTaskId);
        renmodel.getWorkflowActionDto().setApplicationId(applicationId);
        TbCfcApplicationMstEntity cfcdetail = iCFCApplicationMasterService.getCFCApplicationByApplicationId(applicationId, orgId);

        SocialSecurityApplicationForm entity = schemeApplicationFormRepository.fetchDataOnBenef(cfcdetail.getRefNo(), orgId);

        ServiceMaster service2 = serviceMaster.getServiceMaster(entity.getSelectSchemeName(), orgId);
        RenewalFormDto dto = new RenewalFormDto();
        BeanUtils.copyProperties(entity, dto);
        renmodel.getRenewalFormDto().setBeneficiaryno(entity.getBeneficiarynumber());
        if(renmodel.getRenewalFormDto().getApplicationId()!=null) {
        renmodel.getRenewalFormDto().setApplicationId(applicationId);
        }
        renmodel.getRenewalFormDto().setNameofApplicant(entity.getNameofApplicant());
        renmodel.getRenewalFormDto().setSelectSchemeNamedesc(service2.getSmServiceName());
        renmodel.getRenewalFormDto().setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
        renmodel.getRenewalFormDto().setValidtoDate(entity.getValidtoDate());
        if(entity.getApmApplicationId()!=null) {
        renmodel.getRenewalFormDto().setMasterAppId(Long.valueOf(entity.getApmApplicationId()));
        }
        renmodel.getRenewalFormDto().setOrgId(entity.getOrgId());

        renmodel.setCfcAttachment(iChecklistVerificationService.getDocumentUploadedByRefNo(applicationId.toString(), orgId));

        return new ModelAndView("RenewalFormView", MainetConstants.FORM_NAME, renmodel);
    }

    // save renewal form details

    @RequestMapping(params = "saveRenewalFormDetails", method = RequestMethod.POST)
    public ModelAndView saveRenewalFormDetails(final Model model, final HttpServletRequest httpServletRequest) {

        this.getModel().bind(httpServletRequest);
        JsonViewObject respObj;
        ModelAndView mv = null;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Long langId = (long) UserSession.getCurrent().getLanguageId();
        String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();

        String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
        RenewalFormModel renModel = this.getModel();
        RenewalFormDto dto = renModel.getRenewalFormDto();

        dto.setOrgId(orgId);
        dto.setCreatedBy(empId);
        dto.setCreatedDate(new Date());
        dto.setLgIpMac(ipMacAddress);
        dto.setLangId(langId);
        dto.setUlbName(ulbName);

        List<DocumentDetailsVO> docs = renModel.getUploadFileList();
        if (docs != null) {
            docs = fileUpload.prepareFileUpload(docs);
        }
        renModel.getRenewalFormDto().setUploaddoc(docs);

        if (renModel.validateInputs()) {

            if (renModel.saveForm()) {

                return jsonResult(
                        JsonViewObject.successResult(ApplicationSession.getInstance().getMessage(renModel.getSuccessMessage())));
            } else {

                return jsonResult(
                        JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success")));
            }

        }
        mv = new ModelAndView("RenewalFormValidn", MainetConstants.FORM_NAME, getModel());

        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    // workflow save decision call
    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

        JsonViewObject respObj = null;

        this.bindModel(httpServletRequest);

        RenewalFormModel renModel = this.getModel();

        String decision = renModel.getWorkflowActionDto().getDecision();

        boolean updFlag = renModel.callWorkFlow();

        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("social.info.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("social.info.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("social.info.application.sendBack"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("social.info.application.failure"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }

    @RequestMapping(params = "getDataonBenefNo", method = RequestMethod.POST)
    public ModelAndView getDataonBenefNo(final HttpServletRequest httpServletRequest) {
        
        fileUpload.sessionCleanUpForFileUpload();
        getModel().bind(httpServletRequest);
        RenewalFormModel model = this.getModel();
        RenewalFormDto dto = model.getRenewalFormDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        dto.setOrgId(orgId);
        SocialSecurityApplicationForm entity = renewalFormService.fetchDataOnBenef(dto);

        if (entity != null) {

            ServiceMaster service = serviceMaster
                    .getServiceByShortName(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE, orgId);

            ServiceMaster service1 = serviceMaster.getServiceMaster(entity.getSelectSchemeName(), orgId);
            dto.setNameofApplicant(entity.getNameofApplicant());
            dto.setSelectSchemeNamedesc(service1.getSmServiceName());
            dto.setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
            /*
             * if(model.getRenewalFormDto().getApplicationId()!=null) { dto.setApplicationId(entity.getApplicationId()); }
             */
            if(entity.getApmApplicationId()!=null) {
            dto.setApplicationId(Long.valueOf(entity.getApmApplicationId()));
            }
            dto.setOrgId(entity.getOrgId());
            dto.setServiceId(service.getSmServiceId());
            dto.setDeptId(service.getTbDepartment().getDpDeptid());
            dto.setMobNum(entity.getMobNum());
            
        } else {
            model.addValidationError(ApplicationSession.getInstance().getMessage("social.sec.valid.benefnum"));
        }
        return defaultMyResult();
    }
}
