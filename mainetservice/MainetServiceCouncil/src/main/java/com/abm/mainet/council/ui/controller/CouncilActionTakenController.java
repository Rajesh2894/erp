package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilActionTakenDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.service.ICouncilActionTakenService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilActionTakenModel;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("/CouncilActionTaken.html")
public class CouncilActionTakenController extends AbstractFormController<CouncilActionTakenModel> {

    @Autowired
    private ICouncilProposalMasterService councilProposalMasterService;
    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ICouncilActionTakenService iCouncilActionTakenService;

    // private CouncilProposalMasterDto councilProposalDto;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(Model model, HttpServletRequest request) {
        sessionCleanup(request);
        List<CouncilProposalMasterDto> proposalList = iCouncilActionTakenService
                .fetchProposalsByAgendaId(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute("proposalList", proposalList);
        return index();
    }

    // Search Proposals
    @ResponseBody
    @RequestMapping(params = "searchProposals", method = RequestMethod.POST)
    public CouncilProposalMasterDto getCouncilProposalList(Model model,
            @RequestParam(MainetConstants.Council.Proposal.PROPOSALNO) final String proposalNo,
            final HttpServletRequest request) {
        CouncilProposalMasterDto councilProposalDto = null;
        getModel().bind(request);
        if (!StringUtils.isEmpty(proposalNo)) {
            councilProposalDto = councilProposalMasterService.getCouncilProposalMasterByproposalNo(proposalNo,
                    UserSession.getCurrent().getOrganisation().getOrgid());

        }
        return councilProposalDto;

    }

    // Edit proposal
    @ResponseBody
    @RequestMapping(params = "EDIT", method = RequestMethod.POST)
    public ModelAndView editCouncilProposalMasterData(@RequestParam String mode,
            @RequestParam(MainetConstants.Common_Constant.ID) final Long proposalId, HttpServletRequest request) {
        this.getModel().bind(request);
        this.getModel().setSaveMode(MainetConstants.Council.EDIT);
        CouncilActionTakenModel model = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        CouncilProposalMasterDto proposalMasterDto = councilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);
        this.getModel().setCouProposalMasterDto(proposalMasterDto);

        model.setDepartmentsList(iTbDepartmentService.findMappedDepartments(orgId));

        model.setLookupListLevel1(
                CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, orgId));
        this.getModel().setDepartments(loadDepartmentList());
        List<EmployeeBean> loadEmployee = loadEmployee();
        this.getModel().setListOfAllemployee(loadEmployee());

        List<EmployeeBean> loadActiveEmployee = new ArrayList<>();

        for (EmployeeBean employeeBean : loadEmployee) {
            EmployeeBean emp = new EmployeeBean();
            if (StringUtils.equals(employeeBean.getIsDeleted(), "0")) {
                BeanUtils.copyProperties(employeeBean, emp);
                loadActiveEmployee.add(emp);
            }
        }

        this.getModel().setEmployee(loadActiveEmployee);
        List<CouncilActionTakenDto> councilActionList = new ArrayList<>();
        List<CouncilActionTakenDto> councActionDtoList = iCouncilActionTakenService
                .getActionTakenDetailsByPropsalId(proposalId, orgId);
        councActionDtoList.forEach(action -> {
            CouncilActionTakenDto actionTakenDto = new CouncilActionTakenDto();
            BeanUtils.copyProperties(action, actionTakenDto);
            if (action.getPatDate() != null) {
                actionTakenDto.setPropDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(action.getPatDate()));
            }

            actionTakenDto.setPropDisableFlag(MainetConstants.FlagY);
            councilActionList.add(actionTakenDto);
        });

        this.getModel().setCouncilActionTakenDto(councilActionList);

        // get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                                + proposalId);
        this.getModel().setAttachDocsList(attachDocs);

        return new ModelAndView("CouncilActionTakenEditForm", MainetConstants.FORM_NAME, this.getModel());
    }

    // View Proposal
    @ResponseBody
    @RequestMapping(params = "VIEW", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewCouncilMemberMasterData(
            @RequestParam(MainetConstants.Common_Constant.ID) final Long proposalId, final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.Council.VIEW);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        CouncilActionTakenModel model = this.getModel();
        // CouncilActionTakenDto dto = new CouncilActionTakenDto();
        // get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                                + proposalId);
        this.getModel().setAttachDocsList(attachDocs);
        CouncilProposalMasterDto proposalMasterDto = councilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);

        this.getModel().setCouProposalMasterDto(proposalMasterDto);

        model.setDepartmentsList(iTbDepartmentService.findMappedDepartments(orgId));

        this.getModel().setDepartments(loadDepartmentList());
        model.setLookupListLevel1(
                CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, orgId));
        List<EmployeeBean> loadEmployee = loadEmployee();
        this.getModel().setListOfAllemployee(loadEmployee());

        List<EmployeeBean> loadActiveEmployee = new ArrayList<>();

        for (EmployeeBean employeeBean : loadEmployee) {
            EmployeeBean emp = new EmployeeBean();
            if (StringUtils.equals(employeeBean.getIsDeleted(), "0")) {
                BeanUtils.copyProperties(employeeBean, emp);
                loadActiveEmployee.add(emp);
            }
        }

        this.getModel().setEmployee(loadActiveEmployee);

        List<CouncilActionTakenDto> councActionDtoList = iCouncilActionTakenService
                .getActionTakenDetailsByPropsalId(proposalId, orgId);
        councActionDtoList.forEach(actioTaken -> {
            if (actioTaken.getPatDate() != null) {
                actioTaken.setPropDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(actioTaken.getPatDate()));
            }
        });

        this.getModel().setCouncilActionTakenDto(councActionDtoList);

        return new ModelAndView("CouncilActionTakenEditForm", MainetConstants.FORM_NAME, this.getModel());

    }

    // get department list
    private List<Department> loadDepartmentList() {
        List<Department> departments = departmentService
                .getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); // Active
        // = "A"
        return departments;
    };

    // get employee List
    private List<EmployeeBean> loadEmployee() {
        IEmployeeService employeeService = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
                .getBean("employeeService");
        List<EmployeeBean> employee = employeeService
                .getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
        return employee;
    }

    @RequestMapping(params = "checkEmployeeActive", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmployeeActive(@RequestParam("empId") Long empId, HttpServletRequest request) {
        this.getModel().bind(request);
        String flag = MainetConstants.FlagN;
        for (EmployeeBean employee : this.getModel().getListOfAllemployee()) {
            if (employee.getEmpId().equals(empId)) {
                if (StringUtils.equals(employee.getIsDeleted(), "0")) {
                    flag = MainetConstants.FlagY;
                }
            }
        }
        return flag;
    }
}