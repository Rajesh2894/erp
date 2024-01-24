package com.abm.mainet.common.workflow.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.mapper.TbDepartmentServiceMapper;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.RoleDecisionDTO;
import com.abm.mainet.common.workflow.dto.RoleDecisionMstDTO;
import com.abm.mainet.common.workflow.dto.RoleDecisionMstResponse;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.RoleMasterConfigService;

@Controller
@RequestMapping("/RolesMaster.html")
public class RolesMasterConfigController extends AbstractController {

	private static Logger LOG = Logger.getLogger(EventMasterController.class);
	public static final String ORG_LIST = "orgList";
	public static final String EVENT_LIST = "eventList";
	public static final String DEP_LIST = "depList";
	private static final String JSP_LIST = "rolesMasterList";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM_ERROR = "workflowHomePageException";

	private String activeClass;

	@Resource
	private RoleMasterConfigService roleMasterConfigService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private TbDepartmentServiceMapper tbDepartmentServiceMapper;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private IEmployeeService employeeService;

	private static final String REQUESTDTO_CANT_NULL = "dto can not be null";

	Logger logger = Logger.getLogger(EventMasterController.class);

	private List<RoleDecisionDTO> list;
	private List<String> rolesMapList;

	public RolesMasterConfigController() {
		super(RolesMasterConfigController.class, "RolesMaster");

	}

	@RequestMapping()
	public String list(final Model model) {
		log("Action 'list'");
		list = new ArrayList<>();
		final List<TbDepartment> deptList = populateDepartment();
		model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
		return JSP_LIST;
	}

	@RequestMapping(params = "servicesAndRoles", method = RequestMethod.POST)
	public @ResponseBody Map<String, List<Object[]>> populateServicesAndRoles(@RequestParam("deptId") final Long deptId,
			final HttpServletRequest request, final Model model) {
		LOG.info("start the services ");
		Map<String, List<Object[]>> result = new HashMap<>();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> services = serviceMasterService.findAllActiveServicesForDepartment(orgId, deptId);
		List<Object[]> roles = employeeService.findAllRolesForDept(orgId, "A", deptId);
		result.put("services", services);
		result.put("roles", roles);
		return result;
	}

	@RequestMapping(params = "form")
	public String index(final HttpServletRequest request, final Model model,
			@RequestParam("dpDeptId") final Long deptId, @RequestParam("serviceId") final Long serviceId,
			@RequestParam("roleId") final Long roleId) {

		final UserSession userSession = UserSession.getCurrent();
		final Organisation organisation = new Organisation();
		final RoleDecisionMstDTO dto = new RoleDecisionMstDTO(); // need to create decision list dto
		final Department deptEntity = new Department();
		organisation.setOrgid(userSession.getOrganisation().getOrgid());
		final TbDepartment deptBean = tbDepartmentService.findById(deptId);
		tbDepartmentServiceMapper.mapTbDepartmentToTbDepartmentEntity(deptBean, deptEntity);
		final List<TbDepartment> deptList = populateDepartment();
		final Map<String, List<Object[]>> servicesAndRolesList = populateServicesAndRoles(deptId, request, model);
		List<RoleDecisionDTO> decisionList = populateDecisions(organisation);
		dto.setOrganisation(organisation);
		dto.setSmServiceId(serviceId);
		dto.setDeptId(deptEntity);
		dto.setRoleId(roleId);
		model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
		model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.CREATE);
		model.addAttribute(MainetConstants.CommonConstants.COMMAND, this);
		model.addAttribute("roleDesicionDto", dto);
		model.addAttribute("decisionNotSelected", decisionList);
		model.addAttribute(MainetConstants.SERVICE_LIST, servicesAndRolesList.get("services"));
		model.addAttribute("rolesList", servicesAndRolesList.get("roles"));
		model.addAttribute(MainetConstants.ACTIVE_CLASS, activeClass);
		return "rolesMasterConfig";
	}

	private List<RoleDecisionDTO> populateDecisions(Organisation organisation) {
		List<RoleDecisionDTO> decisionList = new ArrayList<>();
		List<LookUp> lookUpList = CommonMasterUtility.getLookUps("RDL", organisation);
		RoleDecisionDTO decision = null;
		for (LookUp lookUp : lookUpList) {
			decision = new RoleDecisionDTO();
			decision.setDecisionId(lookUp.getLookUpId());
			decision.setDecisionDescFirst(lookUp.getDescLangFirst());
			decision.setDecisionDescSecond(lookUp.getDescLangSecond());
			decisionList.add(decision);
		}
		return decisionList;
	}

	@RequestMapping(params = "department")
	public @ResponseBody List<TbDepartment> populateDepartment() {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final List<TbDepartment> deptList = tbDepartmentService.findActualDept(orgId);
		return deptList;
	}

	@RequestMapping(params = "existServiceRole", method = RequestMethod.POST)
	public @ResponseBody String checkRolesAlreadyExist(final RoleDecisionMstDTO roleDecisionDTO,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model) {

		String response = MainetConstants.MENU.FALSE;
		if (!bindingResult.hasErrors()) {
			final boolean status = true;
			if (status) {
				response = MainetConstants.MENU.TRUE;
			}

		} else {
			logger.info("Object Binding problems");
			response = MainetConstants.INTERNAL_ERROR;
		}

		return response;
	}

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public @ResponseBody String createRoleMaster(@Valid final RoleDecisionMstDTO roleDecisionDTO,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model) {

		String isBeingSaved = MainetConstants.MENU.FALSE;
		Assert.notNull(roleDecisionDTO, REQUESTDTO_CANT_NULL);

		if (!bindingResult.hasErrors()) {

			try {
				final UserSession userSession = UserSession.getCurrent();
				final boolean status = roleMasterConfigService.saveOrUpdateEventMaster(roleDecisionDTO,
						userSession.getEmployee(), userSession.getOrganisation());

				if (status == true) {
					isBeingSaved = MainetConstants.MENU.TRUE;
				}
			} catch (final Exception ex) {
				logger.info(ex);
				isBeingSaved = JSP_FORM_ERROR;
			}

		} else {
			isBeingSaved = JSP_LIST;
		}

		return isBeingSaved;
	}

	@RequestMapping(params = "searchDecision", method = RequestMethod.POST)
	public @ResponseBody int searchDecision(@RequestParam("dpDeptId") final Long deptId,
			@RequestParam("serviceId") final Long serviceId, @RequestParam("roleId") final Long roleId) {
		log("Action 'searchRoles'");
		int count = 0;
		list = new ArrayList<>();
		list = roleMasterConfigService.findDecisionByData(deptId, roleId, serviceId,"Y",
				UserSession.getCurrent().getOrganisation());
		count = list.size();
		return count;
	}

	@RequestMapping(params = "getGridData")
	public @ResponseBody RoleDecisionMstResponse gridData(final HttpServletRequest request, final Model model) {
		log("Action 'Get grid Data'");

		final RoleDecisionMstResponse response = new RoleDecisionMstResponse();

		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		response.setRows(list);
		response.setTotal(list.size());
		response.setRecords(list.size());
		response.setPage(page);
		model.addAttribute(MAIN_LIST_NAME, list);
		return response;
	}

	@RequestMapping(params = "formforUpdate", method = RequestMethod.POST)
	public String formforUpdate(final HttpServletRequest request, final Model model,
			@RequestParam("deptId") final Long deptId, @RequestParam("serviceId") final Long serviceId,
			@RequestParam("roleId") final Long roleId, @RequestParam("mode") final String mode) {

		log("Action 'formforUpdate'");
		rolesMapList = new ArrayList<>();
		final Long sessionOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Organisation organisation = new Organisation();
		organisation.setOrgid(sessionOrgId);
		final Department deptEntity = new Department();
		final RoleDecisionMstDTO bean = new RoleDecisionMstDTO();
		final List<TbDepartment> deptList = populateDepartment();
		final Map<String, List<Object[]>> servicesAndRolesList = populateServicesAndRoles(deptId, request, model);
		final TbDepartment department = tbDepartmentService.findById(deptId);
		tbDepartmentServiceMapper.mapTbDepartmentToTbDepartmentEntity(department, deptEntity);
		List<RoleDecisionDTO> selectedList = roleMasterConfigService.findDecisionByData(deptId, roleId, serviceId,"Y",
				UserSession.getCurrent().getOrganisation());
		List<RoleDecisionDTO> decisionList = populateDecisions(organisation);
		List<RoleDecisionDTO> notSelectedList = decisionList.stream()
				.filter(decision -> selectedList.stream()
						.noneMatch(selectedDecision -> selectedDecision.getDecisionId() == decision.getDecisionId()))
				.collect(Collectors.toList());
		bean.setOrganisation(organisation);
		bean.setDeptId(deptEntity);
		bean.setRoleId(roleId);
		bean.setSmServiceId(serviceId);
		for (final RoleDecisionDTO dto : selectedList) {
			rolesMapList.add(String.valueOf(dto.getDecisionId()));
		}

		if (mode.equals(MainetConstants.EDIT)) {
			model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.EDIT);
		} else {
			model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.VIEW);
		}
		model.addAttribute(MainetConstants.CommonConstants.COMMAND, this);
		model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
		model.addAttribute(MainetConstants.SERVICE_LIST, servicesAndRolesList.get("services"));
		model.addAttribute("rolesList", servicesAndRolesList.get("roles"));
		model.addAttribute("decisionNotSelected", notSelectedList);
		model.addAttribute("decisionSelected", selectedList);
		model.addAttribute("roleDesicionDto", bean);
		model.addAttribute(MainetConstants.ACTIVE_CLASS, activeClass);
		return "rolesMasterConfig";
	}

	@RequestMapping(params = "update", method = RequestMethod.POST)
	public @ResponseBody boolean updateDecision(@Valid final RoleDecisionMstDTO roleDecisionMstDTO,
			final BindingResult bindingResult, final HttpServletRequest request, final Model model) {

		final Organisation org = UserSession.getCurrent().getOrganisation();
		final Employee emp = UserSession.getCurrent().getEmployee();
		boolean response = false;

		int counter = 0;
		final List<String> deletedList = new ArrayList<>();
		if(null!=roleDecisionMstDTO.getDecisionMapId()) {
			for (final String curDecisionId : rolesMapList) {
				for (final String decisionId : roleDecisionMstDTO.getDecisionMapId()) {
					if (!curDecisionId.equals(decisionId)) {
						counter++;
					} else {
						counter = 0;
						break;
					}
				}
				if (counter > 0) {
					deletedList.add(curDecisionId);
				}
			}
		}
		response = roleMasterConfigService.updateDecisionMapping(roleDecisionMstDTO, deletedList, org, emp);
		return response;

	}
	
	@RequestMapping(params = "validateDescision", method = RequestMethod.POST)
    public @ResponseBody boolean validateDescision(@RequestParam("dpDeptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId, @RequestParam("roleId") final Long roleId) {
        boolean isValid = true;
        List<RoleDecisionDTO> decisionList = roleMasterConfigService.findDecisionByData(deptId, roleId, serviceId,"Y",
				UserSession.getCurrent().getOrganisation());
        if (decisionList.size() > 0) {
            isValid = false;
        }
        return isValid;
    }


}
