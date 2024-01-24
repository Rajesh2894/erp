package com.abm.mainet.lqp.ui.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.lqp.dto.DocumentDto;
import com.abm.mainet.lqp.dto.LegislativeQueryResponseDto;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.lqp.service.IQueryAnswerService;
import com.abm.mainet.lqp.service.IQueryRegistrationService;
import com.abm.mainet.lqp.ui.model.QueryRegistrationModel;

@Controller
@RequestMapping(value = "LegislativeQuestion.html")
public class QueryRegistrationController extends AbstractFormController<QueryRegistrationModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private TbDepartmentService departmentService;

    @Autowired
    private IQueryRegistrationService queryRegistrationService;

    @Autowired
    private IQueryAnswerService queryAnswerService;

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("LegislativeQuestion.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbDepartment> depList = departmentService.findAllActive(orgId);
        this.getModel().setDepartmentList(depList);

        List<QueryRegistrationMasterDto> queryRegMasrDtoList = queryRegistrationService.searchQueryRegisterMasterData(null,
                null, null, null, null, orgId);
        this.getModel().setQueryRegMasrDtoList(queryRegMasrDtoList);

        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchQueryReg", method = RequestMethod.POST)
    public LegislativeQueryResponseDto searchQueryReg(@RequestParam("deptId") final Long deptId,
            @RequestParam("questionTypeId") final Long questionTypeId,
            @RequestParam("questionId") final String questionId, @RequestParam("fromDate") final Date fromDate,
            @RequestParam("toDate") final Date toDate, final HttpServletRequest request) {
        getModel().bind(request);
        LegislativeQueryResponseDto response = new LegislativeQueryResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        List<QueryRegistrationMasterDto> queryRegMasrDtoList = queryRegistrationService.searchQueryRegisterMasterData(deptId,
                questionTypeId, questionId, fromDate, toDate, orgId);
        response.setQueryRegMasrDtoList(queryRegMasrDtoList);

        return response;
    }

    @RequestMapping(params = "addQueryReg", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addQueryReg(final HttpServletRequest request) {
        getModel().bind(request);

        this.getModel().setSaveMode(MainetConstants.CommonConstants.ADD);
        populateQuestionData(null);
        return new ModelAndView("QuestionRegForm", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "editQueryReg", method = RequestMethod.POST)
    public ModelAndView editQueryReg(@RequestParam("questionRegId") final Long questionRegId) {
    	addAttachedDoc(questionRegId);
        this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);

        return new ModelAndView("QuestionRegForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewQueryReg", method = RequestMethod.POST)
    public ModelAndView viewQueryReg(@RequestParam("questionRegId") final Long questionRegId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        populateQuestionData(questionRegId);
        QueryRegistrationMasterDto queryRegMasrDto = queryRegistrationService
                .getQueryRegisterMasterDataByQuestRegId(questionRegId);
        addAttachedDoc(questionRegId);
        this.getModel().setQueryRegMasrDto(queryRegMasrDto);
        return new ModelAndView("QuestionRegForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewQueryAnswer", method = RequestMethod.POST)
    public ModelAndView viewQueryAnswer(@RequestParam("questionRegId") final Long questionRegId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        populateQuestionData(questionRegId);
        QueryAnswerMasterDto answerDto = queryAnswerService.getQuestionDataByQueId(questionRegId);
        this.getModel().setQueryAnsrMstDto(answerDto);
        this.getModel().setApprovalViewFlag(MainetConstants.MODE_VIEW);
        addAttachedDoc(questionRegId);
        return new ModelAndView("AnswerRegForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "reopenQueryReg", method = RequestMethod.POST)
    public ModelAndView viewAttendanceMasterData(@RequestParam("questionRegId") final Long questionRegId) {
        this.getModel().setSaveMode(MainetConstants.LQP.STATUS.REOPEN);
        populateQuestionData(questionRegId);
        QueryRegistrationMasterDto queryRegMasrDto = queryRegistrationService
                .getQueryRegisterMasterDataByQuestRegId(questionRegId);
        addAttachedDoc(questionRegId);
        this.getModel().setQueryRegMasrDto(queryRegMasrDto);
        return new ModelAndView("QuestionRegForm", MainetConstants.FORM_NAME, this.getModel());
    }

    // common method for populate data
    private void populateQuestionData(Long questionRegId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbDepartment> depList = departmentService.findAllActive(orgId);
        this.getModel().setDepartmentList(depList);
        List<EmployeeBean> employeeList = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
                .getAllEmployee(orgId);
        employeeList.forEach(employee -> {
            employee.setEmpname(employee.getEmpname() + " " + employee.getEmplname());
        });
        employeeList.sort(Comparator.comparing(EmployeeBean::getEmpname));
        this.getModel().setEmployeeList(employeeList);
    }
    
    //Add attached doc to model
    private void addAttachedDoc(Long questionRegId) {
    	final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.FILE_PATH_SEPARATOR
                                + questionRegId);

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
    }

}
