package com.abm.mainet.lqp.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.lqp.dto.DocumentDto;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.lqp.service.IQueryAnswerService;
import com.abm.mainet.lqp.service.IQueryRegistrationService;
import com.abm.mainet.lqp.ui.model.AnswerRegistrationModel;

@Controller
@RequestMapping(value = "LegislativeAnswer.html")
public class AnswerRegistrationController extends AbstractFormController<AnswerRegistrationModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IQueryRegistrationService queryRegistrationService;

    @Autowired
    private IQueryAnswerService answerService;

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(params = "showDetails", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView showDetails(@RequestParam("appNo") final String appNo,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam(value = "taskName", required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {

        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);

        this.getModel().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setReferenceId(appNo);
        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);

        // data set in QueryAnswerMasterDto
        QueryRegistrationMasterDto queryRegistrationMasterDto = queryRegistrationService
                .getQueryRegisterMasterDataByQuestId(appNo);
        if (queryRegistrationMasterDto.getQuestionRegId() == null) {
            // doing this because when REOPEN the same Question that time reference no is change due to WF
            String arr[] = appNo.split("/");// LQP/LQE/7/1
            Long qusnRegId = Long.valueOf(arr[2]);
            queryRegistrationMasterDto = queryRegistrationService.getQueryRegisterMasterDataByQuestRegId(qusnRegId);
        }
        QueryAnswerMasterDto answerDto = answerService.getQuestionDataByQueId(queryRegistrationMasterDto.getQuestionRegId());
        this.getModel().getQueryAnsrMstDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setQueryAnsrMstDto(answerDto);
        this.getModel().getQueryAnsrMstDto().setQueryRegistrationMasterDto(queryRegistrationMasterDto);
        // this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        // get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.FILE_PATH_SEPARATOR
                                + queryRegistrationMasterDto.getQuestionRegId());
        this.getModel().setAttachDocsList(attachDocs);
        List<DocumentDto> documentDtos = new ArrayList<>();
        // iterate and set document details
        attachDocs.forEach(doc -> {
            DocumentDto docDto = new DocumentDto();
            // get employee name who attach this image
            Employee emp = employeeService.findEmployeeById(doc.getUserId());
            docDto.setAttBy(emp.getFullName());
            docDto.setAttFname(doc.getAttFname());
            docDto.setAttId(doc.getAttId());
            docDto.setAttPath(doc.getAttPath());
            documentDtos.add(docDto);
        });
        this.getModel().setDocumentDtos(documentDtos);
        this.getModel().setApprovalViewFlag(MainetConstants.MODE_CREATE);
        return new ModelAndView("AnswerRegForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * Maker Checker submit
     * 
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;
        this.bindModel(httpServletRequest);
        AnswerRegistrationModel answerModel = this.getModel();
        // server side validation
        if (StringUtils.isBlank(answerModel.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("lqp.validation.approval.decision"));
            return defaultMyResult();
        }
        if (StringUtils.isBlank(answerModel.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("lqp.validation.approval.remarks"));
            return defaultMyResult();
        }
        String decision = answerModel.getWorkflowActionDto().getDecision();
        boolean updFlag = answerModel.updateAnswerWrkflowApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid());
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("lqp.answer.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("lqp.answer.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("lqp.answer.application.sendBack"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("lqp.answer.application.forward"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("lqp.answer.application.failure"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }

}
