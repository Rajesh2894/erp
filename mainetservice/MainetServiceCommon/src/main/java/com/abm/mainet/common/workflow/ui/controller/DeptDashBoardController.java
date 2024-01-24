/**
 * 
 */
package com.abm.mainet.common.workflow.ui.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.ITaskService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

import io.jsonwebtoken.lang.Objects;

/**
 * @author ritesh.patil
 *
 */
@Controller
@RequestMapping(value = "/DeptDashBoard.html")
public class DeptDashBoardController extends AbstractController {

	private static final String APP_DATE = "appDate";
	private static final String ACTIONS = "actions";
	private static final String APP_NAME = "appName";
	private static final String ACTION_HISTORY = "ActionHistory";
	private static final String SERV_NAME = "servName";
	private static final String APP_ID = "appId";
	private static final String REF_ID = "refId";
	private static final String IS_ENABLE_DMS_FEATURE ="isEnableDMSFeature";
	private static final String APPLICATION_FATCH_ERROR = "Exception while fatching dashboard application list";

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IWorkflowActionService workflowActionService;

	@Autowired
	private IEmployeeService employeeService;

	/**
	 * @param controllerClass
	 * @param entityName
	 */
	public DeptDashBoardController() {
		super(DeptDashBoardController.class, "DeptDashBoard");
	}

	/**
	 * #User Story #1611 used for show all assigned application to department
	 * employee.
	 * 
	 * @param request
	 * @param model
	 * @return application list
	 * @author hiren.poriya
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getGridDataList")
	public @ResponseBody List<UserTaskDTO> gridDataList(@RequestParam("status") String status,
			@RequestParam(value = "fromDay", required = false) Long fromDay,
			@RequestParam(value = "toDay", required = false) Long toDay, HttpServletRequest request, Model model,
			HttpSession session) throws Exception {
		log("Action 'Get grid Data'");
		List<UserTaskDTO> list = new ArrayList<>();
		try {

			List<UserTaskDTO> sessionTaskList = new ArrayList<>();
			sessionTaskList = (List<UserTaskDTO>) session.getAttribute("taskList");
			if (fromDay == null) {
				// flag to check bpm is enabled or not
				final String isBpmActive = ApplicationSession.getInstance().getMessage("bpm.enabled");
				UserSession userSession = UserSession.getCurrent();
				if (isBpmActive.equals(MainetConstants.Common_Constant.YES)) {
					TaskSearchRequest taskSearchRequest = new TaskSearchRequest();
					taskSearchRequest.setEmpId(userSession.getEmployee().getEmpId());
					taskSearchRequest.setLangId(userSession.getLanguageId());
					taskSearchRequest.setStatus(status);
					// D112223 As per discussion with @Rajesh sir
					/*
					 * if (!UserSession.getCurrent().getOrganisation().getDefaultStatus()
					 * .equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
					 * taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid()); }
					 */
					taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid());
					list = taskService.getTaskList(taskSearchRequest);
					session.setAttribute("taskList", list);
				}
			} else {
				// D#132328 filter based on fromDay and toDay
				List<UserTaskDTO> filterList = new ArrayList<>();

				sessionTaskList.stream().forEach(task -> {
					if (task.getTaskSlaDurationInMS() != null && task.getTaskSlaDurationInMS() > 0) {
						Date miliToDate = Utility.getStartOfDay(task.getDateOfAssignment());
						LocalDate appDate = miliToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Period periDate = Period.between(LocalDate.now(), appDate);

						// get no of days between two dates
						int subDays = Utility.getDaysBetDates(miliToDate, Utility.getStartOfDay(new Date()));
						// int subDays = periDate.getDays();
						Duration duration = Duration.ofMillis(task.getTaskSlaDurationInMS());
						int slaDays = (int) duration.toDays();
						int remainDays = slaDays - subDays;
						if (toDay != null && remainDays >= fromDay && remainDays <= toDay) {
							filterList.add(task);
						} else if (fromDay == -1 && task.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REOPENED)) {
							filterList.add(task);
						} else if (fromDay == 0 && remainDays < 0) {
							filterList.add(task);
						}
					}
				});
				return filterList;
			}

		} catch (Exception ex) {
			Log.error(APPLICATION_FATCH_ERROR, ex);
		}

		return list;
	}

	// User story 141152 Done By Niraj Sharma ABM3514
	// Grid data for selected dropdown value code is as below
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getDeptGridDataList")
	public @ResponseBody List<UserTaskDTO> gridDeptDataList(@RequestParam("deptId") Long deptId,HttpServletRequest request, Model model, HttpSession session) throws Exception {
		log("Action 'Get grid Data Department wise'");
		List<UserTaskDTO> list = new ArrayList<>();
		try {
			ApplicationSession applicationSession = ApplicationSession.getInstance();
			List<UserTaskDTO> sessionTaskList = new ArrayList<>();
			List<UserTaskDTO> taskList = new ArrayList<>();
			sessionTaskList = (List<UserTaskDTO>) session.getAttribute("taskList");

			if (CollectionUtils.isNotEmpty(sessionTaskList)) {
				taskList = sessionTaskList.stream()
						.filter(stask -> stask.getDeptId() != null && stask.getDeptId().equals(deptId))
						.collect(Collectors.toList());
				return taskList;
			}
			// flag to check bpm is enabled or not
			final String isBpmActive = ApplicationSession.getInstance().getMessage("bpm.enabled");
			UserSession userSession = UserSession.getCurrent();
			if (isBpmActive.equals(MainetConstants.Common_Constant.YES)) {
				TaskSearchRequest taskSearchRequest = new TaskSearchRequest();
				taskSearchRequest.setEmpId(userSession.getEmployee().getEmpId());
				taskSearchRequest.setLangId(userSession.getLanguageId());
				// taskSearchRequest.setStatus(status);
				if (null != applicationSession.getMessage("app.name")
						&& applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.SKDCL)) {
					if (taskSearchRequest.getLangId() == 1) {
						list = taskService.getTaskList(taskSearchRequest);
						session.setAttribute("taskList", list);

					} else {
						list = taskService.getTaskList(taskSearchRequest);
						session.setAttribute("taskList", list);
					}
				}
				taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid());
				list = taskService.getTaskList(taskSearchRequest);
				session.setAttribute("taskList", list);
			}

		} catch (Exception ex) {
			Log.error(APPLICATION_FATCH_ERROR, ex);
		}

		return list;
	}

	/**
	 * #User Story #1611 Added for application History purpose.
	 * 
	 * @param appId
	 * @param httpServletRequest
	 * @param modelMap
	 * @return application History jsp
	 * @author hiren.poriya
	 */
	@RequestMapping(method = RequestMethod.POST, params = "viewFormHistoryDetails")
	public ModelAndView showHistoryDetails(@RequestParam("appId") String appId, @RequestParam("refId") String refId,
			@RequestParam("appDate") final String appDate, @RequestParam("servName") final String servName,
			@RequestParam("workflowReqId") final Long workflowReqId, final HttpServletRequest httpServletRequest,
			ModelMap modelMap) {
		
		String tsclEnv = MainetConstants.N_FLAG;
		List<WorkflowTaskActionWithDocs> acHistory = null;
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)){
			acHistory = workflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(
					appId, refId, null, (short) UserSession.getCurrent().getLanguageId());
		}else{
			acHistory = workflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(
					appId, refId, workflowReqId, (short) UserSession.getCurrent().getLanguageId());
		}
		
	    
		/*
		 * D#127224 - As per suggested by Rajesh Sir-> showing only records in action
		 * history whose action has been taken by user
		 */
		/* */
		List<WorkflowTaskActionWithDocs> actionHistory = acHistory.stream()
				.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());

	

		// Defect #76675-->added -> on history form employee name, designation and email
		// should display
		
		for (WorkflowTaskActionWithDocs workflowTaskAction : actionHistory) {
			/*
			 * Following code is duplicate & is commented because already actionHistory
			 * object gets all the emp data
			 */
			/*
			 * Employee employee =
			 * employeeService.findEmployeeByIdAndOrgId(workflowTaskAction.getEmpId(),
			 * workflowTaskAction.getOrgId()); if(employee != null) { String EmployeName =
			 * employee.getEmpname() + MainetConstants.WHITE_SPACE; EmployeName +=
			 * employee.getEmplname(); workflowTaskAction.setEmpName(EmployeName);
			 * workflowTaskAction.setEmpEmail(employee.getEmpemail());
			 * workflowTaskAction.setEmpGroupDescEng(employee.getDesignation().getDsgname())
			 * ;
			 * workflowTaskAction.setEmpGroupDescReg(employee.getDesignation().getDsgnameReg
			 * ()); }else {
			 * workflowTaskAction.setEmpName(ApplicationSession.getInstance().getMessage(
			 * MainetConstants.WorkFlow.Designation. CITIZEN)); }
			 */
			if ((workflowTaskAction.getTaskName().equals("Start")) && (!workflowTaskAction.getTaskName().isEmpty())) {

				workflowTaskAction.setComments(MainetConstants.operator.EMPTY);
			} else {
				workflowTaskAction.setComments(workflowTaskAction.getComments());
			}
			//US#148162
				Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
				
				if (isEmployeeUpdated(workflowTaskAction.getEmpId(), orgid, workflowTaskAction.getCreatedDate())) {
					Map<String,String> bean = employeeService.latestUpdatedEmployeeDet(workflowTaskAction.getEmpId(),
							orgid, appId, workflowTaskAction.getTaskId().toString(),
							workflowTaskAction.getCreatedDate(),workflowTaskAction.getEmpName());
					 if(!workflowTaskAction.getTaskName().equals("Start"))
					workflowTaskAction.setForwardToEmployee(bean.get("FORWARD_TO_EMPLOYEE"));
					if(bean.get("empName")!=null) {
						workflowTaskAction.setEmpName(bean.get("empName"));	
					}
				
				}

				
			modelMap.addAllAttributes(actionHistory);

		}
		String empName = "";
		if (CollectionUtils.isNotEmpty(actionHistory)) {
			empName = actionHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getId))
					.collect(Collectors.toList()).get(0).getEmpName();
		}
		if (StringUtils.isEmpty(empName)) {
			if (CollectionUtils.isNotEmpty(acHistory)) {
				empName = acHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getId))
						.collect(Collectors.toList()).get(0).getEmpName();
			}
		}

		if (appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL)
				|| (!appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL))) {
			appId = refId;
		}
		if (!appId.equals(MainetConstants.NULL) && refId.equals(MainetConstants.NULL)) {
			refId = appId;
		}
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
			LinkedHashSet<WorkflowTaskActionWithDocs> sortedActionHistory = new LinkedHashSet<>();
			actionHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getDateOfAction))
					.forEach(a -> {
						sortedActionHistory.add(a);
					});

			modelMap.put(ACTIONS, sortedActionHistory);
		} else {
			modelMap.put(ACTIONS, actionHistory);
		}
		modelMap.put(APP_NAME, empName);
		modelMap.put(APP_DATE, appDate);
		modelMap.put(SERV_NAME, servName);
		modelMap.put(APP_ID, appId);
		modelMap.put(REF_ID, refId);
		modelMap.put(IS_ENABLE_DMS_FEATURE, MainetConstants.Common_Constant.YES
				.equals(ApplicationSession.getInstance().getMessage("dms.configure")));
		
		//#157753
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_TSCL))
	              tsclEnv = MainetConstants.Y_FLAG;
		modelMap.put("tsclEnv", tsclEnv);
		
		return new ModelAndView(ACTION_HISTORY, MainetConstants.FORM_NAME, modelMap);
	}

	// 119534 This method is giving the status of counter schedule
	@RequestMapping(params = "getEmployeeStatus")
	public @ResponseBody String getEmployeeStatus(final HttpServletRequest httpServletRequest, final Model model) {
		return UserSession.getCurrent().getCountersts();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getComplaintGridDataList")
	public @ResponseBody List<UserTaskDTO> getComplaintGridDataList(@RequestParam("status") String status,
			@RequestParam(value = "fromDay", required = false) Long fromDay,
			@RequestParam(value = "toDay", required = false) Long toDay, HttpServletRequest request, Model model,
			HttpSession session) throws Exception {
		log("Action 'Get complaint grid Data'");

		List<UserTaskDTO> list = new ArrayList<>();
		try {

			List<UserTaskDTO> sessionTaskList = new ArrayList<>();
			sessionTaskList = (List<UserTaskDTO>) session.getAttribute("comTaskList");
			if (fromDay == null) {
				final String isBpmActive = ApplicationSession.getInstance().getMessage("bpm.enabled");
				UserSession userSession = UserSession.getCurrent();
				if (isBpmActive.equals(MainetConstants.Common_Constant.YES)) {
					TaskSearchRequest taskSearchRequest = new TaskSearchRequest();
					taskSearchRequest.setEmpId(userSession.getEmployee().getEmpId());
					taskSearchRequest.setLangId(userSession.getLanguageId());
					taskSearchRequest.setStatus(status);
					// D112223 As per discussion with @Rajesh sir
					/*
					 * if (!UserSession.getCurrent().getOrganisation().getDefaultStatus()
					 * .equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
					 * taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid()); }
					 */
					taskSearchRequest.setOrgId(userSession.getOrganisation().getOrgid());
					
					//#161047
					LookUp rfcLookup = CommonMasterUtility.getValueFromPrefixLookUp("AL", "RFM",
		                    UserSession.getCurrent().getOrganisation());
					
					log("rfcLookup" + rfcLookup.toString());
					
					taskSearchRequest.setReferenceMode(rfcLookup.getLookUpId() + "");
					
					log("Lookup ID: " + rfcLookup.getLookUpId());
					
					// list = taskService.getTaskList(taskSearchRequest);
					list = workflowActionService.findComplaintTaskForEmployee(taskSearchRequest);
					log("Complaint LIST SIZE " + list.size());
					session.setAttribute("comTaskList", list);
				}
			} else {
				// D#132328 filter based on fromDay and toDay
				List<UserTaskDTO> filterList = new ArrayList<>();

				sessionTaskList.stream().forEach(task -> {
					if (task.getTaskSlaDurationInMS() != null && task.getTaskSlaDurationInMS() > 0) {

						Date miliToDate = Utility.getStartOfDay(task.getDateOfAssignment());
						LocalDate appDate = miliToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						Period periDate = Period.between(LocalDate.now(), appDate);
						// get no of days between two dates
						int subDays = Utility.getDaysBetDates(miliToDate, Utility.getStartOfDay(new Date()));
						Duration duration = Duration.ofMillis(task.getTaskSlaDurationInMS());
						int slaDays = (int) duration.toDays();
						int remainDays = slaDays - subDays;
						if (toDay != null && remainDays >= fromDay && remainDays <= toDay) {
							filterList.add(task);
						} else if (fromDay == -1 && task.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REOPENED)) {
							filterList.add(task);
						} else if (fromDay == 0 && remainDays < 0) {
							filterList.add(task);
						}
					} else if (fromDay == -1 && task.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REOPENED)) {
						filterList.add(task);
					}
				});
				return filterList;
			}

		} catch (Exception ex) {
			Log.error(APPLICATION_FATCH_ERROR, ex);
		}

		return list;
	}

	/* Defect #127224 */
	@RequestMapping(method = RequestMethod.POST, params = "viewCompletedFormHistoryDetails")
	public ModelAndView showCompletedHistoryDetails(@RequestParam("appId") String appId,
			@RequestParam("refId") String refId, @RequestParam("appDate") final String appDate,
			@RequestParam("servName") final String servName, @RequestParam("workflowReqId") final Long workflowReqId,
			final HttpServletRequest httpServletRequest, ModelMap modelMap) {

		String tsclEnv = MainetConstants.N_FLAG;
		
		List<WorkflowTaskActionWithDocs> acHistory = null;
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)){
			acHistory = workflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(
					appId, refId, null, (short) UserSession.getCurrent().getLanguageId());
		}else{
			acHistory = workflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(
					appId, refId, workflowReqId, (short) UserSession.getCurrent().getLanguageId());
		}

		List<WorkflowTaskActionWithDocs> actionHistory = acHistory.stream()
				.filter(action -> !(action.getTaskDecision().equals("PENDING"))).collect(Collectors.toList());

		// Defect #76675-->added -> on history form employee name, designation and email
		// should display
		for (WorkflowTaskActionWithDocs workflowTaskAction : actionHistory) {
			if ((workflowTaskAction.getTaskName().equals("Start")) && (!workflowTaskAction.getTaskName().isEmpty())) {

				workflowTaskAction.setComments(MainetConstants.operator.EMPTY);
			} else {
				workflowTaskAction.setComments(workflowTaskAction.getComments());
			}
			//US#148162
               if (!workflowTaskAction.getTaskName().equals("Start")) {
				Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
				Date empUpdateDate = employeeService.getEmployeeUpdatedDateByEmpId(workflowTaskAction.getEmpId(), orgid);
				
				if(empUpdateDate != null) {
					if(Utility.compareDate(workflowTaskAction.getDateOfAction(), empUpdateDate)) {
						Map<String, String> bean = employeeService.latestUpdatedEmployeeDet(workflowTaskAction.getEmpId(),
								orgid, appId, workflowTaskAction.getTaskId().toString(),
								workflowTaskAction.getCreatedDate(), workflowTaskAction.getEmpName());
						if (bean.get("empName") != null) {
							workflowTaskAction.setEmpName(bean.get("empName"));
						}
						if (bean.get("loginName") != null) {
							workflowTaskAction.setEmpLoginName(bean.get("loginName"));
						}
					}else if(Utility.compareDate(workflowTaskAction.getCreatedDate(), empUpdateDate) && Utility.compareDate(empUpdateDate,workflowTaskAction.getDateOfAction())) {

						Map<String, String> bean = employeeService.latestUpdatedEmployeeDet(workflowTaskAction.getEmpId(),
								orgid, appId, workflowTaskAction.getTaskId().toString(),
								workflowTaskAction.getCreatedDate(), workflowTaskAction.getEmpName());
						if (bean != null && !bean.isEmpty()) {
							if (!workflowTaskAction.getTaskName().equals("Start"))
								workflowTaskAction.setForwardToEmployee(bean.get("FORWARD_TO_EMPLOYEE"));
						}
					
					}
					
				}
				
				
				/*
				 * if (isEmployeeUpdated(workflowTaskAction.getEmpId(), orgid,
				 * workflowTaskAction.getCreatedDate())) { Map<String, String> bean =
				 * employeeService.latestUpdatedEmployeeDet(workflowTaskAction.getEmpId(),
				 * orgid, appId, workflowTaskAction.getTaskId().toString(),
				 * workflowTaskAction.getCreatedDate(), workflowTaskAction.getEmpName()); if
				 * (bean != null && !bean.isEmpty()) { if
				 * (!workflowTaskAction.getTaskName().equals("Start"))
				 * workflowTaskAction.setForwardToEmployee(bean.get("FORWARD_TO_EMPLOYEE")); if
				 * (bean.get("empName") != null) {
				 * workflowTaskAction.setEmpName(bean.get("empName")); } } }
				 */
				 
				 
			}
			modelMap.addAllAttributes(actionHistory);

		}
		String empName = actionHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getId))
				.collect(Collectors.toList()).get(0).getEmpName();

		if (appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL)
				|| (!appId.equals(MainetConstants.NULL) && !refId.equals(MainetConstants.NULL))) {
			appId = refId;
		}
		if (!appId.equals(MainetConstants.NULL) && refId.equals(MainetConstants.NULL)) {
			refId = appId;
		}
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) {
			LinkedHashSet<WorkflowTaskActionWithDocs> sortedActionHistory = new LinkedHashSet<>();
			actionHistory.stream().sorted(Comparator.comparing(WorkflowTaskActionWithDocs::getDateOfAction))
					.forEach(a -> {
						sortedActionHistory.add(a);
					});

			modelMap.put(ACTIONS, sortedActionHistory);
		} else {
			modelMap.put(ACTIONS, actionHistory);
		}
		modelMap.put(APP_NAME, empName);
		modelMap.put(APP_DATE, appDate);
		modelMap.put(SERV_NAME, servName);
		modelMap.put(APP_ID, appId);
		modelMap.put(REF_ID, refId);
		
        //#157753
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_TSCL))
	              tsclEnv = MainetConstants.Y_FLAG;
		modelMap.put("tsclEnv", tsclEnv);
		
		return new ModelAndView(ACTION_HISTORY, MainetConstants.FORM_NAME, modelMap);
	}

	private boolean isEmployeeUpdated(Long empId, long orgid, Date date) {
		boolean flag = false;
		flag = employeeService.isEmployeeUpdated(empId, orgid, date);
		return flag;
	}

}
