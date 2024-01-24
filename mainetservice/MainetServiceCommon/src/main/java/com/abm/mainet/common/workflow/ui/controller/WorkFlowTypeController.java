
package com.abm.mainet.common.workflow.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkFlowTypeGrid;
import com.abm.mainet.common.workflow.dto.WorkflowMasDTO;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.ui.model.WorkFlowTypeModel;

/**
 * @author Hiren.Poriya
 *
 */

@Controller
@RequestMapping(MainetConstants.WORKFLOWTYPE.WORKFLOW_HTML)
public class WorkFlowTypeController extends AbstractFormController<WorkFlowTypeModel> {

	@Autowired
	private TbOrganisationService tbOrganisationService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IEntitlementService iEntitlementService;

	@Autowired
	private IComplaintTypeService iComplaintService;

	@Autowired
	private ILocationMasService iLocationMasService;
	
	@Autowired
	private IOrganisationService orgService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(Model uiModel, HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs(MainetConstants.WORKFLOWTYPE.WORKFLOW_HTML);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkFlowTypeModel workFlowTypeModel = this.getModel();
		workFlowTypeModel
				.setDeptList(tbDepartmentService.findAllDepartmentByOrganization(currentOrgId, MainetConstants.MENU.A));
		workFlowTypeModel.setOrgId(currentOrgId);
		return new ModelAndView(MainetConstants.WORKFLOWTYPE.WORKFLOW_TYPE_HOME, MainetConstants.FORM_NAME,
				workFlowTypeModel);

	}

	/**
	 * Shows a form page in order to create a new WorkFLow
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
	public ModelAndView formForCreate(
			@RequestParam(value = MainetConstants.Common_Constant.ID, required = false) Long flowId,
			@RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String modeType) {

		String formName = MainetConstants.WORKFLOWTYPE.WORKFLOW_TYPE_FORM;
		WorkFlowTypeModel workFlowTypeModel = this.getModel();

		workFlowTypeModel.setSourceLookUps(CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 1,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		populateModel(flowId, workFlowTypeModel, modeType);
		workFlowTypeModel
				.setOrgList(tbOrganisationService.findAllOrganization(MainetConstants.Common_Constant.ACTIVE_FLAG));

		// check whether environment is kdmc or not
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PRODUCT)) {
			workFlowTypeModel.setKdmcEnv(MainetConstants.FlagY);
		} else
			workFlowTypeModel.setKdmcEnv(MainetConstants.FlagN);

		if (MainetConstants.WORKFLOWTYPE.MODE_VIEW.equals(modeType)
				|| MainetConstants.WORKFLOWTYPE.MODE_EDIT.equals(modeType))
			formName = MainetConstants.WORKFLOWTYPE.WORKFLOW_TYPE_EDIT_FORM;
		LookUp lookup = null;
		LookUp lookup1 = null;
		try {
			lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("BTW", "AIC", 1,
					UserSession.getCurrent().getOrganisation());
			lookup1 = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WWC", "LCC", 1,
					UserSession.getCurrent().getOrganisation());
		} catch (FrameworkException e) {

		}
		if (lookup != null && lookup.getOtherField() != null && lookup.getOtherField().equals("Y")) {
			this.getModel().setBillType("Y");
		} else {
			this.getModel().setBillType("N");
		}

		if (lookup1 != null && lookup1.getOtherField() != null && lookup1.getOtherField().equals("Y")) {
			this.getModel().setCategory("Y");
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("ITC", 1,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setTriCodList1(lookUpList);
		} else {
			this.getModel().setCategory("N");
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			this.getModel().setAuditDeptWiseFlag(MainetConstants.FlagY);
		}	else {
			this.getModel().setAuditDeptWiseFlag(MainetConstants.FlagN);
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SFAC)) {
			Organisation org  = new Organisation();
			String shortCode = null;
			if (null != workFlowTypeModel.getWorkFlowMasDTO().getServiceId()) {
				shortCode = serviceMasterService.fetchServiceShortCode(workFlowTypeModel.getWorkFlowMasDTO().getServiceId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
			}
				if (shortCode !=null && (shortCode.equalsIgnoreCase(MainetConstants.Sfac.FPO)|| shortCode.equalsIgnoreCase(MainetConstants.Sfac.MILESTONE_COM_BPM_SHORTCODE) || shortCode.equalsIgnoreCase(MainetConstants.Sfac.DPR_ENTRY_BPM_SHORTCODE))) {
					org = tbOrganisationService.findByShortCode(MainetConstants.Sfac.IA);
					this.getModel().setCommanMasDtoList(employeeService.getMasterDetail(org.getOrgid()));
				}
			 else {
				this.getModel().setCommanMasDtoList(employeeService.getMasterDetail(null));
			}
			
		}
		return new ModelAndView(formName, MainetConstants.FORM_NAME, workFlowTypeModel);
	}

	/**
	 * Populates the Spring MVC model with the given entity and eventually other
	 * useful data
	 * 
	 * @param model
	 * @param WorkFlowTypeDTO
	 */
	private void populateModel(Long flowId, WorkFlowTypeModel workFlowTypeModel, String modeType) {

		if (flowId == null) {
			workFlowTypeModel.setWorkFlowMasDTO(new WorkflowMasDTO());
			workFlowTypeModel.setModeType(MainetConstants.WORKFLOWTYPE.MODE_CREATE);
		} else {
			WorkflowMasDTO dto = iWorkFlowTypeService.findById(flowId);
			workFlowTypeModel.setWorkFlowMasDTO(dto);
			if (dto != null) {
				final Long activeStatusId = CommonMasterUtility
						.getLookUpFromPrefixLookUpValue(MainetConstants.Common_Constant.ACTIVE_FLAG,
								MainetConstants.Common_Constant.ACN, UserSession.getCurrent().getLanguageId(),
								UserSession.getCurrent().getOrganisation())
						.getLookUpId();
				workFlowTypeModel.setCompList(iComplaintService.findAllComplaintsByOrg(dto.getCurrentOrgId()));
				workFlowTypeModel.setServiceList(serviceMasterService
						.findAllActiveServicesByDepartment(dto.getCurrentOrgId(), dto.getDeptId(), activeStatusId));
				workFlowTypeModel.setEventList(iWorkFlowTypeService
						.findAllEventsByDeptAndServiceId(dto.getCurrentOrgId(), dto.getDeptId(), dto.getServiceId()));
				workFlowTypeModel.setRoleList(iEntitlementService.findAllRolesByOrg(dto.getCurrentOrgId()));

				if (dto.getPrefixName() != null)
					this.getModel().setPrefixName(dto.getPrefixName());
				if (dto.getWmSchCodeId2() != null)
					this.getModel().setSourceLevelLookUps(
							CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
									UserSession.getCurrent().getOrganisation().getOrgid()));

			}

			if (MainetConstants.WORKFLOWTYPE.MODE_VIEW.equals(modeType)) {
				workFlowTypeModel.setModeType(MainetConstants.WORKFLOWTYPE.MODE_VIEW);
				workFlowTypeModel.getWorkFlowMasDTO().setHiddeValue(MainetConstants.WORKFLOWTYPE.MODE_VIEW);
				populateEmpRoleDesc(dto);
			} else {
				workFlowTypeModel.setModeType(MainetConstants.WORKFLOWTYPE.MODE_EDIT);
				workFlowTypeModel.getWorkFlowMasDTO().setHiddeValue(MainetConstants.WORKFLOWTYPE.MODE_EDIT);
			}

		}

		workFlowTypeModel.getWorkFlowMasDTO().setCurrentOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workFlowTypeModel.setDeptList(tbDepartmentService.findAllDepartmentByOrganization(
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MENU.A));
	}

	/**
	 * populate list of Department per Organization wise
	 * 
	 * @param orgId
	 * @return List<Object[]>
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.DEPARTMENT, method = RequestMethod.POST)
	public @ResponseBody List<Object[]> populateDepartmentByOrg(
			@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId) {
		return tbDepartmentService.findAllDepartmentByOrganization(orgId, MainetConstants.MENU.A);
	}

	/**
	 * list of services and complaints available in Department & org wise
	 * 
	 * @param orgId
	 * @param deptId
	 * @return List<Object[]>
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.SERVICES, method = RequestMethod.POST)
	public @ResponseBody Object[] populateServicesAndComplaint(
			@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId) {
		Object[] obj = new Object[] { null, null };
		final Long activeStatusId = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				MainetConstants.Common_Constant.ACTIVE_FLAG, MainetConstants.Common_Constant.ACN,
				UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation()).getLookUpId();
		obj[0] = serviceMasterService.findAllActiveServicesByDepartment(orgId, deptId, activeStatusId);
		obj[1] = iComplaintService.findAllComplaintsByOrg(orgId);
		return obj;
	}

	/**
	 * Operational Ward Zone is different from department to
	 * department.Administrative,Electoral and Revenue Ward Zone are common across
	 * all department.
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.WARDZONE_MAPPING, method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixName(
			@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@RequestParam(required = false, value = MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(required = false, value = MainetConstants.Common_Constant.SERVICECODE) String serviceCode) {

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)
				&& ("RCP").equals(serviceCode)) {
			this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.RCZ);
		} else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)
				&& ("RCW").equals(serviceCode)) {
			this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.RWS);
		} else {
			String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId, orgId);
			if (prefixName != null && !prefixName.isEmpty()) {
				// D#130416 check at least one level define or not in prefix
				// if not than it throws exception which is handle using try
				// catch and inside catch pass prefix CWZ
				try {
					CommonMasterUtility.getLevelData(prefixName, 1, UserSession.getCurrent().getOrganisation());
					this.getModel().setPrefixName(prefixName);
				} catch (FrameworkException e) {
					this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
				}
			} else {
				this.getModel().setPrefixName(MainetConstants.COMMON_PREFIX.CWZ);
			}
		}
		if (deptId != null && deptId > 0
				&& Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			String deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(deptId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (StringUtils.equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY, deptCode)
					&& Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PWZ")) {
				this.getModel().setPrefixName("PWZ");
			}
		}
		return new ModelAndView(MainetConstants.WORKFLOWTYPE.WORK_FLOW_WARD_ZONE,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.EMP_OR_ROLE, method = RequestMethod.POST)
	public @ResponseBody List<Object[]> getEmpOrRole(@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(MainetConstants.Common_Constant.FLAG) String flag) {
		List<Object[]> dataList = null;
		if (flag.equals(MainetConstants.CommonConstants.E))
			dataList = employeeService.findAllEmpByOrg(orgId);
		else
			dataList = iEntitlementService.findAllRolesByOrg(orgId);
		return dataList;
	}

	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.MAPPING, method = RequestMethod.POST)
	public ModelAndView getEventsList(@RequestParam(MainetConstants.Common_Constant.ORGID) Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(MainetConstants.Common_Constant.SERVICEID) Long serviceId) {
		List<Object[]> eventList = iWorkFlowTypeService.findAllEventsByDeptAndServiceId(orgId, deptId, serviceId);
		this.getModel().setEventList(eventList);
		return new ModelAndView(MainetConstants.WORKFLOWTYPE.MAPPING, MainetConstants.CommonConstants.COMMAND,
				getModel());
	}

	/**
	 * Get WorkFlow Grid data
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.Common_Constant.GET_GRID_DATA, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(HttpServletRequest httpServletRequest,
			@RequestParam String page, @RequestParam String rows) {
		this.sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long detp = Long.valueOf(httpServletRequest.getParameter("deptIdHidden"));
		Long service = Long.valueOf(httpServletRequest.getParameter("serviceIdHidden"));
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			this.getModel().setAuditDeptWiseFlag(MainetConstants.FlagY);
		}else{
			this.getModel().setAuditDeptWiseFlag(MainetConstants.FlagN);
		}
		List<WorkFlowTypeGrid> workFlowTypeGrids = iWorkFlowTypeService.findRecords(orgId, detp, service,
				MainetConstants.Common_Constant.YES);
		
		if (workFlowTypeGrids != null) {
			for (WorkFlowTypeGrid workFlowTypeGrid : workFlowTypeGrids) {
				if (UserSession.getCurrent().getLanguageId() != MainetConstants.ENGLISH) {
					workFlowTypeGrid.setDepName(workFlowTypeGrid.getDeptRegName());
					workFlowTypeGrid.setServiceName(workFlowTypeGrid.getServiceNameReg());
					workFlowTypeGrid.setOrgName(workFlowTypeGrid.getOrgRegName());
				}
				if (workFlowTypeGrid.getComplaint() != null) {
					workFlowTypeGrid.setCompId(workFlowTypeGrid.getComplaint().getCompId());
					if (UserSession.getCurrent().getLanguageId() != MainetConstants.ENGLISH) {
						workFlowTypeGrid.setCompDesc(workFlowTypeGrid.getComplaint().getComplaintDescreg());
					} else {
						workFlowTypeGrid.setCompDesc(workFlowTypeGrid.getComplaint().getComplaintDesc());

					}
				}
				// #152599-To show scheme name on summary page
				if (workFlowTypeGrid.getSchemeId() != null && workFlowTypeGrid.getSchemeId() > 0) {
					LookUp lookUpDesc = null;
					String deptCode = null;
					String deptValue = null;
					String serviceName = serviceMasterService.fetchServiceShortCode(workFlowTypeGrid.getServiceId(),
							UserSession.getCurrent().getOrganisation().getOrgid());
					if (serviceName != null && serviceName.equals("VB")) {
						lookUpDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(workFlowTypeGrid.getSchemeId(),
								UserSession.getCurrent().getOrganisation());
					} else if (serviceName != null && serviceName.equals("IARS")) {
						deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(workFlowTypeGrid.getSchemeId(),
								UserSession.getCurrent().getOrganisation().getOrgid());
					} else {
						lookUpDesc = CommonMasterUtility.getHierarchicalLookUp(workFlowTypeGrid.getSchemeId(),
								UserSession.getCurrent().getOrganisation());
					}
					deptValue=tbDepartmentService.findDepartmentShortCodeByDeptId(workFlowTypeGrid.getDeptId(),
							UserSession.getCurrent().getOrganisation().getOrgid());
					if(deptValue!=null && (deptValue.equals("CMT")  || (this.getModel().getAuditDeptWiseFlag().equals(MainetConstants.FlagY) && deptValue.equals("AD")))) {
						deptCode = tbDepartmentService.findDepartmentDescByDeptId(workFlowTypeGrid.getSchemeId());
					}

					if (deptCode != null) {
						workFlowTypeGrid.setCompDesc(deptCode);
					} else if (lookUpDesc != null) {
						if (UserSession.getCurrent().getLanguageId() != MainetConstants.ENGLISH)
							workFlowTypeGrid.setCompDesc(lookUpDesc.getDescLangSecond());
						else
							workFlowTypeGrid.setCompDesc(lookUpDesc.getDescLangFirst());
					}
					
				}
				// to show CBBO Name on summary page
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
						MainetConstants.ENV_SFAC)) {
 					 Organisation org  = new Organisation();
					 String shortCode = serviceMasterService.fetchServiceShortCode(workFlowTypeGrid.getServiceId(),
								UserSession.getCurrent().getOrganisation().getOrgid());
					 if (shortCode.equalsIgnoreCase(MainetConstants.Sfac.FPO) || shortCode.equalsIgnoreCase(MainetConstants.Sfac.MILESTONE_COM_BPM_SHORTCODE) || shortCode.equalsIgnoreCase(MainetConstants.Sfac.DPR_ENTRY_BPM_SHORTCODE)) {
						 org	= tbOrganisationService.findByShortCode(MainetConstants.Sfac.IA);
					 }else {
				       org = tbOrganisationService.findByShortCode(MainetConstants.Sfac.CBBO);
					 }
					if (workFlowTypeGrid.getExtIdentifier() != null && workFlowTypeGrid.getExtIdentifier() > 0) {
						String masterName = employeeService.getMasterName(org.getOrgid(),
								workFlowTypeGrid.getExtIdentifier());
						if (StringUtils.isNotEmpty(masterName))
							workFlowTypeGrid.setCompDesc(masterName);
					}
				}
			}
		}
		return this.getModel().paginate(httpServletRequest, page, rows, workFlowTypeGrids);
	}

	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.EMP_ROLE_WARD_ZONE, method = RequestMethod.POST)
	public @ResponseBody List<Object[]> getEmpOrRoleWardZoneWise(
			@RequestParam(MainetConstants.Common_Constant.ORGID) final Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) final Long deptId,
			@RequestParam(MainetConstants.Common_Constant.FLAG) final String flag,
			@RequestParam(MainetConstants.Common_Constant.FIRST_LEVEL) final Long firstLevel,
			@RequestParam(name = MainetConstants.Common_Constant.SECOND_LEVEL, required = false) final Long secondLevel,
			@RequestParam(name = MainetConstants.Common_Constant.THIRD_LEVEL, required = false) final Long thirdLevel,
			@RequestParam(name = MainetConstants.Common_Constant.FOURTH_LEVEL, required = false) final Long fourthLevel,
			@RequestParam(name = MainetConstants.Common_Constant.FIVE_LEVEL, required = false) final Long fiveLevel) {

		final List<Long> locationIds = iLocationMasService.findLocations(orgId, deptId, firstLevel, secondLevel,
				thirdLevel, fourthLevel, fiveLevel);

		List<Object[]> dataList = null;
		if (flag.equals(MainetConstants.CommonConstants.E)) {
			dataList = employeeService.findAllEmpByLocation(orgId, deptId, locationIds);
		} else {
			dataList = employeeService.findAllRoleByLocation(orgId, deptId, locationIds);
		}
		return dataList;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.DELETE_FLOW, method = RequestMethod.POST)
	public boolean deActiveWorkFLow(@RequestParam(MainetConstants.Common_Constant.ID) Long id) {
		iWorkFlowTypeService.deleteWorkFLow(MainetConstants.Common_Constant.NO,
				UserSession.getCurrent().getEmployee().getEmpId(), id);
		return true;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.CHECK_EXISTING, method = RequestMethod.POST)
	public boolean checkExistigWorkFLow(@RequestParam(MainetConstants.Common_Constant.ORGID) final Long orgId,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(MainetConstants.Common_Constant.SERVICEID) Long serviceId,
			@RequestParam(MainetConstants.Common_Constant.COMPID) Long compId,
			@RequestParam(MainetConstants.Common_Constant.WORDZONE_TYPE) String wardZoneType,
			@RequestParam(MainetConstants.Common_Constant.FIRST_LEVEL) final Long firstLevel,
			@RequestParam(name = MainetConstants.Common_Constant.SECOND_LEVEL, required = false) final Long secondLevel,
			@RequestParam(name = MainetConstants.Common_Constant.THIRD_LEVEL, required = false) final Long thirdLevel,
			@RequestParam(name = MainetConstants.Common_Constant.FOURTH_LEVEL, required = false) final Long fourthLevel,
			@RequestParam(name = MainetConstants.Common_Constant.FIVE_LEVEL, required = false) final Long fiveLeve,
			@RequestParam(name = MainetConstants.Common_Constant.FROM_AMOUNT, required = false) final BigDecimal fromAmount,
			@RequestParam(name = MainetConstants.Common_Constant.TO_AMOUNT, required = false) final BigDecimal toAmount,
			@RequestParam(name = MainetConstants.Common_Constant.SOURCE_OF_FUND, required = false) final Long souceOfFund,
			@RequestParam(name = "schemeId", required = false) final Long schemeId,
			@RequestParam(name = "extIdentifier", required = false) final Long extIdentifier) {
		boolean isExist = false;
		isExist = iWorkFlowTypeService.isWorkFlowExist(orgId, deptId, serviceId, compId, wardZoneType, firstLevel,
				secondLevel, thirdLevel, fourthLevel, fiveLeve, fromAmount, toAmount, souceOfFund, schemeId,
				extIdentifier);
		return isExist;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKFLOWTYPE.GET_TOTAL_EMP_COUNT, method = RequestMethod.POST, produces = "application/json")
	public int getTotalEmployeeCountByRoles(@RequestParam(MainetConstants.Common_Constant.ORGID) final Long orgId,
			@RequestParam(MainetConstants.WORKFLOWTYPE.ROLE_IDS) final String roleIds) {
		int count = 0;
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) 
				&& !this.getModel().getModeType().equals(MainetConstants.FlagE)) {
		    String extractedIDs = extractEmployeeIds(roleIds);
		    List<Long> roleIdList = createRoleIdList(extractedIDs, roleIds);
		    count = employeeService.getTotalEmployeeCountByRoles(roleIdList, orgId);
		} else {
		    List<Long> roleIdList = createRoleIdList(null, roleIds);
		    count = employeeService.getTotalEmployeeCountByRoles(roleIdList, orgId);
		}
		return count;
	}
	
	
	private List<Long> createRoleIdList(String extractedIDs, String roleIds) {
	    List<Long> roleIdList = new ArrayList<>();
	    
	    String[] array = (extractedIDs != null) ? extractedIDs.split(",") : roleIds.split(",");
	    
	    for (String roleId : array) {
	        roleIdList.add(Long.valueOf(roleId));
	    }
	    
	    return roleIdList;
	}

	/**
	 * Get All Fund Level By SourceId
	 * 
	 * @param sourceCode
	 * @return List<LookUp>
	 */
	@ResponseBody
	@RequestMapping(params = "schemeNames", method = RequestMethod.POST)
	public List<LookUp> getAllFundLevelBySourceId(@RequestParam("sourceCode") Long sourceCode) {
		List<LookUp> lookUpList = new ArrayList<>();
		List<LookUp> objList = CommonMasterUtility.getNextLevelData(MainetConstants.WorksManagement.SSF, 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!CollectionUtils.isEmpty(objList)) {
			objList.forEach(obj -> {
				if (obj.getLookUpParentId() == sourceCode.longValue())
					lookUpList.add(obj);
			});
		}
		return lookUpList;

	}

	@ResponseBody
	@RequestMapping(params = "getMasterDetail", method = RequestMethod.POST)
	List<CommonMasterDto> getMasterDetail(@RequestParam("serviceCode") String serviceCode) {
		List<CommonMasterDto> dtoList = new ArrayList<>();
		if (serviceCode.equals(MainetConstants.Sfac.FPO) || serviceCode.equals("MSC") || serviceCode.equals("DPR")) {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			dtoList = employeeService.getMasterDetail(org.getOrgid());
		} else {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
			dtoList = employeeService.getMasterDetail(org.getOrgid());
		}
		this.getModel().setCommanMasDtoList(dtoList);
		return dtoList;
	}
	
	public WorkflowMasDTO populateEmpRoleDesc(WorkflowMasDTO masDto) {
	    masDto.getWorkflowDet().forEach(detDto -> {
	        if (MainetConstants.FlagE.equals(detDto.getRoleType())) {
	            detDto.setRoleOrEmployeeDesc(getDescriptions(detDto.getRoleOrEmpIds(), id -> employeeService.getEmpFullNameById(Long.valueOf(id.trim()))));
	        } else if (MainetConstants.FlagR.equals(detDto.getRoleType())) {
	            detDto.setRoleOrEmployeeDesc(getDescriptions(detDto.getRoleOrEmpIds(), id -> {
	                GroupMaster role = iEntitlementService.findByRoleId(Long.valueOf(id.trim()), UserSession.getCurrent().getOrganisation().getOrgid());
	                return (role != null) ? role.getGrDescEng() : null;
	            }));
	        }
	    });

	    return masDto;
	}

	private String getDescriptions(String roleOrEmpIds, Function<String, String> getDescription) {
	    if (roleOrEmpIds != null && !roleOrEmpIds.trim().isEmpty()) {
	        return Arrays.stream(roleOrEmpIds.split(","))
	            .map(id -> {
	                try {
	                    return getDescription.apply(id);
	                } catch (Exception e) {
	                    return null;
	                }
	            })
	            .filter(Objects::nonNull)
	            .collect(Collectors.joining(", "));
	    }
	    return null;
	}
	
	public static String extractEmployeeIds(String employeeOrRoleWithIds) {
		List<String> extractedIds = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\((\\d+)\\)");
		Matcher matcher = pattern.matcher(employeeOrRoleWithIds);

		while (matcher.find()) {
			extractedIds.add(matcher.group(1));
		}

		// Join the extracted IDs with commas
		return String.join(", ", extractedIds);
	}

}
