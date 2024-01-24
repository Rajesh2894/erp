package com.abm.mainet.common.workflow.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.domain.WorkflowDet;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.ehcache.hibernate.management.impl.CacheRegionUtils;

/**
 * To identify task owner at runtime BPM process can make REST calls, back to service layer. Service layer is place where we
 * configure workflow and it's related task with its owner details as master data. TaskAssignmentService provide API's to retrieve
 * task owner details from workflow master. All API provided by this service are in use by BPM process depending on the business
 * logic and runtime data.
 * 
 * Task owner identification is depends on various business rules. This service enables the dynamic task assignment feature where
 * task owner configuration and identification logic decoupled from BPM processes.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA API's
 * 
 * 
 * @author sanket.joshi
 * @see ITaskAssignmentService
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.common.workflow.service.ITaskAssignmentService")
@Api("Task Assignment Service")
@Path("/workflow/taskAssignment")
public class TaskAssignmentServiceImpl implements ITaskAssignmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskAssignmentServiceImpl.class);

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private WorkFlowTypeRepository workFlowTypeRepository;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    private Organisation org;
    
    @Autowired
    private IWorkflowTaskService workflowTaskService;
    
    @Autowired
    private DepartmentService departmentService;

    @POST
    @Path("/initial")
    @Override
    @ApiOperation(value = "Initial TaskAssignment By WorkflowType", notes = "Initial TaskAssignment By WorkflowType", response = TaskAssignment.class)
    public TaskAssignment getInitialByWorkflowType(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
    	UserTaskDTO userTaskDTO = null;
    	LookUp lookUp = null;
        final WorkflowMas workflowType = workFlowTypeRepository.findOne(taskAssignmentRequest.getWorkflowTypeId());
        /*#D121383 if any complaint is reopen then it should get assigned to last approved level for care module*/
        if(StringUtils.isNotBlank(taskAssignmentRequest.getServiceEventName()) &&
                taskAssignmentRequest.getServiceEventName().equals(MainetConstants.ServiceEventName.COMPLAINT_RESOLUTION) &&
                !Utility.isCallCenterApplicable(workflowType.getOrganisation().getOrgid())
                && taskAssignmentRequest.getApplicationId() != null) {
        	List<UserTaskDTO>  userTaskDTOList= workflowTaskService.findByUUId(taskAssignmentRequest.getApplicationId());
			try {
				lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.WorkFlow.LAST_APPROVE_LEVEL,
						MainetConstants.WorkFlow.WORKFLOW_LEVEL_DECIDE, workflowType.getOrganisation());
			}catch (Exception e) {
                 LOGGER.error("No Prefix data found for value:"+MainetConstants.WorkFlow.LAST_APPROVE_LEVEL);
            }
        	if(CollectionUtils.isNotEmpty(userTaskDTOList)) {
        		userTaskDTO = userTaskDTOList.get(userTaskDTOList.size()-1);
        	}
        	//Checking if request is for reopen type or newly initiated.
        	if(userTaskDTO != null && userTaskDTO.getTaskName().contains("Hidden_Task") && userTaskDTO.getTaskStatus().equals("PENDING")
        			&& lookUp != null && lookUp.getOtherField().equalsIgnoreCase("Y")) {
				return getTaskAassignmentByLastApproval(workflowType,userTaskDTOList);
			
        	}else
        		return getInitialTaskAassignment(workflowType);
			
		}else
        	return getInitialTaskAassignment(workflowType);
    }

    @POST
    @Path("/nextEscalation")
    @Override
    @ApiOperation(value = "Next escalation TaskAssignment By WorkflowType", notes = "Next escalation TaskAssignment By WorkflowType", response = TaskAssignment.class)
    public TaskAssignment getNextEscalationByWorkflowTypeAndCurrentEscalationIndex(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        final WorkflowMas workflowType = workFlowTypeRepository.findOne(taskAssignmentRequest.getWorkflowTypeId());
        int currentEscalationIndex = taskAssignmentRequest.getCurrentEscalationIndex();
        currentEscalationIndex = (currentEscalationIndex <= 0) ? 0 : currentEscalationIndex - 1;
        LOGGER.info("currentEscalationIndex before getNextEscalationTaskAassignment - "+currentEscalationIndex);
        LOGGER.info("workflowType before getNextEscalationTaskAassignment - "+workflowType);
        LOGGER.info("taskAssignmentRequest.getServiceEventName() before getNextEscalationTaskAassignment - "+taskAssignmentRequest.getServiceEventName());
        return getNextEscalationTaskAassignment(workflowType, currentEscalationIndex,
                taskAssignmentRequest.getServiceEventName());
    }

    @POST
    @Path("/servicesEvent/name")
    @Override
    @ApiOperation(value = "ServicesEvent TaskAssignment by WorkflowTypeId and ServiceEventName", notes = "ServicesEvent TaskAssignment by WorkflowTypeId and ServiceEventName", response = TaskAssignment.class)
    public TaskAssignment getServicesEventByWorkflowTypeAndServiceEventName(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        final WorkflowMas workflowType = workFlowTypeRepository.findOne(taskAssignmentRequest.getWorkflowTypeId());
        return getServicesEventTaskAassignment(workflowType,taskAssignmentRequest);
    }

    @POST
    @Path("/eventLevels")
    @Override
    @ApiOperation(value = "ServicesEvent levels with TaskAssignment by WorkflowTypeId and ServiceEventName", notes = "ServicesEvent levels with TaskAssignment by WorkflowTypeId and ServiceEventName", response = TaskAssignment.class)
    public LinkedHashMap<String, TaskAssignment> getEventLevelsByWorkflowTypeAndEventName(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {

        String eventRegex = taskAssignmentRequest.getServiceEventName();
        final WorkflowMas workflowType = workFlowTypeRepository.findOne(taskAssignmentRequest.getWorkflowTypeId());
        LOGGER.info("workflowTypeID before getWorkflowDetList - "+taskAssignmentRequest.getWorkflowTypeId());
        List<WorkflowDet> workflowDetList = workflowType.getWorkflowDetList();
        LinkedHashMap<String, TaskAssignment> eventLevels = new LinkedHashMap<String, TaskAssignment>();
        org = workflowType.getOrganisation();
        LOGGER.info("Retrieving Event Level from work-flow master id |" + workflowType.getWfId() + "using regx "
                + eventRegex);
        workflowDetList.forEach(wd -> {
            if (Pattern.compile(Pattern.quote(wd.getServicesEventEntity().getSystemModuleFunction().getSmfdescription()),
                    Pattern.CASE_INSENSITIVE).matcher(eventRegex).find()) {
                TaskAssignment taskAassignment = getTaskAassignment(workflowDetList, wd);
                eventLevels.put(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + (eventLevels.size() + 1),
                        taskAassignment);
            }
        });
        LOGGER.info("Returning eventLevels - "+eventLevels);
        return (eventLevels.isEmpty()) ? null : eventLevels;
    }

    @POST
    @Path("/eventLevelGroups")
    @Override
    @ApiOperation(value = "ServicesEvent level groups with TaskAssignment by WorkflowTypeId and ServiceEventName", notes = "ServicesEvent level groups with TaskAssignment by WorkflowTypeId and ServiceEventName", response = TaskAssignment.class)
    public LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> getEventLevelGroupsByWorkflowTypeAndEventName(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        String eventRegex = taskAssignmentRequest.getServiceEventName();
        final WorkflowMas workflowType = workFlowTypeRepository.findOne(taskAssignmentRequest.getWorkflowTypeId());
        LOGGER.info("workflowTypeID before getWorkflowDetList - "+taskAssignmentRequest.getWorkflowTypeId());
        List<WorkflowDet> workflowDetList = workflowType.getWorkflowDetList();
        LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> eventLevelGroups = new LinkedHashMap<>();
        org = workflowType.getOrganisation();
        if (Utility.isEnvPrefixAvailable(workflowType.getOrganisation(), MainetConstants.ENV_ASCL)
				&& StringUtils.equals(workflowType.getService().getSmShortdesc(), "MUT")) {
        	 workflowDetList.forEach(workDet ->{
             	Department department2 = departmentService.getDepartment("AS", MainetConstants.STATUS.ACTIVE);
             	workDet.setEventDepartment(department2);
             });
        }
        // Grouping by department name
        Map<String, List<WorkflowDet>> workflowDetGrp = workflowDetList.stream()
                .collect(orderedroupingBy(
                        wd -> wd.getEventDepartment().getDpDeptdesc()));

        workflowDetGrp.forEach((department, wds) -> {
            LinkedHashMap<String, TaskAssignment> eventLevels = new LinkedHashMap<String, TaskAssignment>();
            LOGGER.info("Retrieving Event Level from work-flow master id |" + workflowType.getWfId() + "using regx "
                    + eventRegex);
            wds.forEach(wd -> {
                if (Pattern.compile(Pattern.quote(wd.getServicesEventEntity().getSystemModuleFunction().getSmfdescription()),
                        Pattern.CASE_INSENSITIVE).matcher(eventRegex).find()) {
                    TaskAssignment taskAassignment = getTaskAassignment(workflowDetList, wd);
                    eventLevels.put(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + (eventLevels.size() + 1),
                            taskAassignment);
                }
            });
            if (!eventLevels.isEmpty())
                eventLevelGroups.put(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + (eventLevelGroups.size() + 1),
                        eventLevels);
        });
        LOGGER.info("Returning eventLevelGroups - "+eventLevelGroups);
        return (eventLevelGroups.isEmpty()) ? null : eventLevelGroups;
    }

    @POST
    @Path("/department/service")
    @Override
    @ApiOperation(value = "Initial TaskAssignment By OrgId, departmentId, serviceId and WordZoneLevels", notes = "Initial TaskAssignment By OrgId, departmentId, serviceId and WordZoneLevels", response = TaskAssignment.class)
    public TaskAssignment resolveServiceWorkflowTypeAndGetInitialTaskAassignment(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        LOGGER.info("Before resolveServiceWorkflowType - "+ taskAssignmentRequest.getOrgId()+"|"+taskAssignmentRequest.getDeptId()+"|"+taskAssignmentRequest.getServiceId()+"|"+taskAssignmentRequest.getCodIdOperLevel1()+"|"+taskAssignmentRequest.getCodIdOperLevel2()+"|"+taskAssignmentRequest.getCodIdOperLevel3()+"|"+taskAssignmentRequest.getCodIdOperLevel4()+"|"+taskAssignmentRequest.getCodIdOperLevel5());
        final WorkflowMas workflowType = workflowTyepResolverService.resolveServiceWorkflowType(
                taskAssignmentRequest.getOrgId(),
                taskAssignmentRequest.getDeptId(),
                taskAssignmentRequest.getServiceId(),
                taskAssignmentRequest.getCodIdOperLevel1(),
                taskAssignmentRequest.getCodIdOperLevel2(),
                taskAssignmentRequest.getCodIdOperLevel3(),
                taskAssignmentRequest.getCodIdOperLevel4(),
                taskAssignmentRequest.getCodIdOperLevel5());
        LOGGER.info("Returning TaskAssignment from resolveServiceWorkflowTypeAndGetInitialTaskAassignment.");
        return getInitialTaskAassignment(workflowType);
    }

    @POST
    @Path("/department/complaint")
    @Override
    @ApiOperation(value = "Initial TaskAssignment By OrgId, departmentId, complaintId, and WordZoneLevels", notes = "Initial TaskAssignment By OrgId, departmentId, complaintId and WordZoneLevels", response = TaskAssignment.class)
    public TaskAssignment resolveComplaintWorkflowTypeAndGetInitialTaskAassignment(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        LOGGER.info("Before resolveComplaintWorkflowType - "+ taskAssignmentRequest.getOrgId()+"|"+taskAssignmentRequest.getDeptId()+"|"+taskAssignmentRequest.getCompTypeId()+"|"+taskAssignmentRequest.getCodIdOperLevel1()+"|"+taskAssignmentRequest.getCodIdOperLevel2()+"|"+taskAssignmentRequest.getCodIdOperLevel3()+"|"+taskAssignmentRequest.getCodIdOperLevel4()+"|"+taskAssignmentRequest.getCodIdOperLevel5());
    	final WorkflowMas workflowType = workflowTyepResolverService.resolveComplaintWorkflowType(
                taskAssignmentRequest.getOrgId(),
                taskAssignmentRequest.getDeptId(),
                taskAssignmentRequest.getCompTypeId(),
                taskAssignmentRequest.getCodIdOperLevel1(),
                taskAssignmentRequest.getCodIdOperLevel2(),
                taskAssignmentRequest.getCodIdOperLevel3(),
                taskAssignmentRequest.getCodIdOperLevel4(),
                taskAssignmentRequest.getCodIdOperLevel5());
        LOGGER.info("Returning TaskAssignment from resolveComplaintWorkflowTypeAndGetInitialTaskAassignment.");
        return getInitialTaskAassignment(workflowType);
    }

    /**
     * This method retrieve first task from given WorkflowMas task list and prepares TaskAssignment
     * 
     * @param workflowType
     * @return
     */
    private TaskAssignment getInitialTaskAassignment(final WorkflowMas workflowType) {
        LOGGER.info("workflowType in getInitialTaskAassignment  - " + workflowType);
    	if (workflowType == null)
            return null;
        org = workflowType.getOrganisation();
        List<WorkflowDet> workflowMappings = workflowType.getWorkflowDetList();
        WorkflowDet wfm = workflowMappings.get(0);
        LOGGER.info("workflowMappings in getInitialTaskAassignment  - " + wfm);
        TaskAssignment taskAassignment = getTaskAassignment(workflowMappings, wfm);
        LOGGER.info("Returning taskAassignment  - " + taskAassignment);
        return taskAassignment;
    }

    /**
     * This method retrieve next escalation task from given WorkflowMas task list and prepares TaskAssignment. If
     * currentEscalationIndex is last level of task list then it will return same task(last task) again.
     * 
     * @param workflowType
     * @param currentEscalationIndex
     * @param serviceEventName
     * @return
     */
    private TaskAssignment getNextEscalationTaskAassignment(final WorkflowMas workflowType, final int currentEscalationIndex,
            final String serviceEventName) {
        org = workflowType.getOrganisation();
        TaskAssignment taskAassignment = null;
        WorkflowDet current = null;
        WorkflowDet next = null;
        List<WorkflowDet> workflowMappings = workflowType.getWorkflowDetList();
        if (serviceEventName != null)
            LOGGER.info("ServiceEventName is not null - "+serviceEventName);
            workflowMappings = workflowMappings.stream().filter(p -> p.getServicesEventEntity().getSystemModuleFunction()
                    .getSmfdescription().equalsIgnoreCase(serviceEventName)).collect(Collectors.toList());

        if ((workflowMappings.size() - 1) >= currentEscalationIndex) {
            LOGGER.info("Setting value of current - "+workflowMappings.get(currentEscalationIndex));
            current = workflowMappings.get(currentEscalationIndex);
        } else {
            LOGGER.info("Setting value of next - "+workflowMappings.get((workflowMappings.size() - 1)));
            next = workflowMappings.get((workflowMappings.size() - 1));
        }
        if (current != null) {
            if ((workflowMappings.size() - 1) >= (currentEscalationIndex + 1)) {
                next = workflowMappings.get((currentEscalationIndex + 1));
            } else {
                next = current;
            }
        }
        LOGGER.info("Returning taskAssignment - "+taskAassignment);
        if (next != null) {
            taskAassignment = getTaskAassignment(workflowMappings, next);
        }
        return taskAassignment;
    }

    /**
     * This methods will filter task of by event name from given WorkflowDet task list and returns TaskAssignment.
     * 
     * @param workflowType
     * @param taskAssignmentRequest
     * @return
     */
    private TaskAssignment getServicesEventTaskAassignment(final WorkflowMas workflowType,
            final TaskAssignmentRequest taskAssignmentRequest) {
        org = workflowType.getOrganisation();
        TaskAssignment taskAassignment = null;

        if (taskAssignmentRequest.getCurrentEscalationIndex() != null) {
            LOGGER.info("Current excalation id is not null - " + taskAssignmentRequest.getCurrentEscalationIndex());
            taskAassignment = getCurrentIndexTaskAsignment(workflowType, taskAssignmentRequest);
        } else {
            LOGGER.info("Current excalation id is null - " + taskAssignmentRequest.getCurrentEscalationIndex());
            final List<WorkflowDet> workflowMappings = workflowType.getWorkflowDetList();
            final ListIterator<WorkflowDet> itr = workflowMappings.listIterator();
            while (itr.hasNext()) {
                final WorkflowDet wfm = itr.next();
                final ServicesEventEntity see = wfm.getServicesEventEntity();
                LOGGER.info("Find matching sysmodfunction - " + see.getSystemModuleFunction().getSmfdescription()+" For Service event name - "+taskAssignmentRequest.getServiceEventName());
                if (see.getSystemModuleFunction().getSmfdescription()
                        .equalsIgnoreCase(taskAssignmentRequest.getServiceEventName())) {
                    LOGGER.info("Found matching sysmodfunction for Service event name - " + taskAssignmentRequest.getServiceEventName());
                	
                    if(Utility.isEnvPrefixAvailable(org, "TCP")){
                    	taskAassignment = getTaskAassignment(workflowMappings, wfm,taskAssignmentRequest);
                    }else{
                    taskAassignment = getTaskAassignment(workflowMappings, wfm);
                    }
                    break;
                }
            }
        }
        LOGGER.info("Returning taskAassignment  - " + taskAassignment);
        return taskAassignment;
    }

    private TaskAssignment getCurrentIndexTaskAsignment(WorkflowMas workflowType,
            TaskAssignmentRequest taskAssignmentRequest) {
        TaskAssignment taskAssignment = null;
        String eventRegex = taskAssignmentRequest.getServiceEventName();
        List<WorkflowDet> workflowDetList = workflowType.getWorkflowDetList();
        LinkedHashMap<String, LinkedHashMap<String, WorkflowDet>> eventLevelGroups = new LinkedHashMap<>();
        WorkflowDet obj = null;
        int escalationIndex = 0;

        if (taskAssignmentRequest.getCurrentEscalationGroupIndex() == null) {
            workflowDetList = workflowDetList.stream().filter(p -> p.getServicesEventEntity().getSystemModuleFunction()
                    .getSmfdescription().equalsIgnoreCase(taskAssignmentRequest.getServiceEventName()))
                    .collect(Collectors.toList());
            int currentLevelIndex = taskAssignmentRequest.getCurrentEscalationIndex() - 1;
            taskAssignmentRequest.setCurrentEscalationGroupIndex(1);// if there is no group level checker(by default 1).
            if (workflowDetList.size() >= taskAssignmentRequest.getCurrentEscalationIndex()) {
            	
            	
            	if(Utility.isEnvPrefixAvailable(org, "TCP")){
            		taskAssignment = getTaskAassignment(workflowDetList,
                            workflowDetList.get(currentLevelIndex),taskAssignmentRequest);
                }
            	else{
                taskAssignment = getTaskAassignment(workflowDetList,
                        workflowDetList.get(currentLevelIndex));
            	}
                escalationIndex = taskAssignmentRequest.getCurrentEscalationIndex();
            } else {
            	if(Utility.isEnvPrefixAvailable(org, "TCP")){
                    taskAssignment = getTaskAassignment(workflowDetList,
                            workflowDetList.get(workflowDetList.size() - 1),taskAssignmentRequest);
            	}
            	else{
                taskAssignment = getTaskAassignment(workflowDetList,
                        workflowDetList.get(workflowDetList.size() - 1));
            	}
                escalationIndex = workflowDetList.size();
            }
            taskAssignment.setEscalationIndex(escalationIndex);
        } else {
            Map<String, List<WorkflowDet>> workflowDetGrp = workflowDetList.stream()
                    .collect(orderedroupingBy(
                            wd -> wd.getEventDepartment().getDpDeptdesc()));

            workflowDetGrp.forEach((department, wds) -> {
                LinkedHashMap<String, WorkflowDet> eventLevels = new LinkedHashMap<String, WorkflowDet>();
                eventLevelGroups.put(MainetConstants.LEVEL + (eventLevelGroups.size() + 1), eventLevels);
                wds.forEach(wd -> {
                    if (Pattern.compile(Pattern.quote(wd.getServicesEventEntity().getSystemModuleFunction().getSmfdescription()),
                            Pattern.CASE_INSENSITIVE).matcher(eventRegex).find())
                        eventLevels.put(
                                MainetConstants.LEVEL + (eventLevels.size() + 1),
                                wd);
                });
                eventLevelGroups.put(MainetConstants.LEVEL + (eventLevelGroups.size()), eventLevels);
            });

            if (!eventLevelGroups.isEmpty()
                    && eventLevelGroups
                            .containsKey(MainetConstants.LEVEL + taskAssignmentRequest.getCurrentEscalationGroupIndex())
                    && eventLevelGroups.get(MainetConstants.LEVEL + taskAssignmentRequest.getCurrentEscalationGroupIndex())
                            .size() >= taskAssignmentRequest.getCurrentEscalationIndex()) {

                obj = eventLevelGroups
                        .get(MainetConstants.LEVEL + (taskAssignmentRequest.getCurrentEscalationGroupIndex()))
                        .get(MainetConstants.LEVEL + taskAssignmentRequest.getCurrentEscalationIndex());
                taskAssignment = getTaskAassignment(workflowDetList, obj);
                taskAssignment.setEscalationGroupIndex(taskAssignmentRequest.getCurrentEscalationGroupIndex());
                escalationIndex = taskAssignmentRequest.getCurrentEscalationIndex();
            } else if (taskAssignmentRequest.getCurrentEscalationGroupIndex() < eventLevelGroups.size()) {
                obj = eventLevelGroups
                        .get(MainetConstants.LEVEL + (taskAssignmentRequest.getCurrentEscalationGroupIndex() + 1))
                        .get(MainetConstants.LEVEL + 1);
                taskAssignment = getTaskAassignment(workflowDetList, obj);
                escalationIndex = 1;
                taskAssignment.setEscalationGroupIndex(taskAssignmentRequest.getCurrentEscalationGroupIndex() + 1);

            } else if (!eventLevelGroups.isEmpty()) {
                int size = eventLevelGroups.get(MainetConstants.LEVEL + (eventLevelGroups.size())).size();
                obj = eventLevelGroups
                        .get(MainetConstants.LEVEL + (eventLevelGroups.size()))
                        .get(MainetConstants.LEVEL + size);
                taskAssignment = getTaskAassignment(workflowDetList, obj);
                escalationIndex = size;
                taskAssignment.setEscalationGroupIndex(eventLevelGroups.size());
            }

            if (taskAssignment != null)
                taskAssignment.setEscalationIndex(escalationIndex);
        }

        return taskAssignment;
    }

    /**
     * 
     * This method prepares TaskAssignment instance using WorkflowDet.
     * 
     * @param workflowMappings
     * @param wfm
     * @return
     */
    private TaskAssignment getTaskAassignment(List<WorkflowDet> workflowMappings, WorkflowDet wfm) {

        if ((wfm.getRoleOrEmpIds() != null && !wfm.getRoleOrEmpIds().isEmpty())) {
            LOGGER.info("getRoleOrEmpIds is not empty  - " + wfm.getRoleOrEmpIds());
            final TaskAssignment assignee = new TaskAssignment();

            if (wfm.getRoleType().equals(MainetConstants.Common_Constant.ROLE)) {
                assignee.setActorRole(wfm.getRoleOrEmpIds());
                // Retrieve employee by GMID
                String[] roles = wfm.getRoleOrEmpIds().split(MainetConstants.operator.COMMA);
                List<Employee> employees = employeeService
                        .getByGmId(Arrays.stream(roles).map(Long::parseLong).collect(Collectors.toList()));
                Set<String> empIds = employees.stream().map(e -> e.getEmpId().toString()).collect(Collectors.toSet());
                assignee.setActorIds(empIds);
                assignee.setActorId(empIds.stream().collect(Collectors.joining(MainetConstants.operator.COMMA)));

            } else {
                assignee.setActorId(wfm.getRoleOrEmpIds());
                String[] emps = wfm.getRoleOrEmpIds().split(MainetConstants.operator.COMMA);
                assignee.setActorIds(Arrays.stream(emps).collect(Collectors.toSet()));
            }

            assignee.setOrgId(wfm.getEventOrganisation().getOrgid());
            assignee.setApproverCount(wfm.getApprCount());
            // Event Details
            assignee.setUrl(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfaction());
            assignee.setServiceEventId(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfid());
            assignee.setServiceEventName(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfname());
            assignee.setServiceEventNameReg(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfname_mar());
            // Service details
            assignee.setServiceId(wfm.getWfId().getService().getSmServiceId());
            assignee.setServiceName(wfm.getWfId().getService().getSmServiceName());
            assignee.setServiceNameReg(wfm.getWfId().getService().getSmServiceNameMar());
            // Department details
            assignee.setDeptId(wfm.getEventDepartment().getDpDeptid());
            assignee.setDeptName(wfm.getEventDepartment().getDpDeptdesc());
            assignee.setDeptNameReg(wfm.getEventDepartment().getDpNameMar());
            // SLA Details
            assignee.setSla(wfm.getSla());
            assignee.setSlaUnit(getSlaUnitDesc(wfm.getUnit().toString()));
            assignee.setTaskSlaDurationInMS(wfm.getSlaCalPrimitiveLongValue());
            assignee.setApplicationSlaDurationInMS(
                    workflowMappings.parallelStream().mapToLong(WorkflowDet::getSlaCalPrimitiveLongValue).sum());
            // Escalation details
            int index = workflowMappings.indexOf(wfm);
            int escalationIndex = (index < 0) ? index : index + 1;
            assignee.setEscalationIndex(escalationIndex);
            LOGGER.info("Returning assignee  - " + assignee);
            return assignee;
        } else {
            LOGGER.info("Returning null assignee.");
            return null;
        }
    }

    /**
     * This method retrieve SLA duration code for given slaUnit from prefixes
     * 
     * @param slaUnit
     * @return
     */
    private String getSlaUnitDesc(String slaUnit) {
        String slaUnitDesc = MainetConstants.BLANK;
        if (slaUnit != null && slaUnit.isEmpty())
            return slaUnitDesc;
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.ServiceCareCommon.UTS, org);
        for (LookUp lookUp : lookUps) {
            String lid = Long.toString(lookUp.getLookUpId());
            if (lid.equals(slaUnit)) {
                slaUnitDesc = lookUp.getLookUpCode();
                break;
            }
        }
        return slaUnitDesc.toLowerCase();
    }

    /**
     * This is ordered grouping collector.
     * 
     * @param function
     * @return
     */
    private static <T, K extends Comparable<K>> Collector<T, ?, LinkedHashMap<K, List<T>>> orderedroupingBy(
            Function<T, K> function) {
        return Collectors.groupingBy(function,
                LinkedHashMap::new, Collectors.toList());
    }

    private TaskAssignment getTaskAassignmentByLastApproval(final WorkflowMas workflowType,final List<UserTaskDTO> userTaskDTOList) {
		LOGGER.info("workflowType in getTaskAassignmentByLastApproval  - " + workflowType);
		if (workflowType == null)
			return null;
		
		//Get taskdto object of last approved record
		UserTaskDTO userTaskDTO =userTaskDTOList.get(userTaskDTOList.size()-2);
		LOGGER.info("Last approved taskDTO id -"+userTaskDTO.getWorkflowTaskId());
		
		List<WorkflowDet> workflowMappings = workflowType.getWorkflowDetList();
		//getting workflowdet as per last level apporved record 
		WorkflowDet wfm = workflowMappings.get(userTaskDTO.getCurrentEscalationLevel().intValue()-1);
		LOGGER.info("workflowMappings by last approval level  - " + wfm);
		TaskAssignment taskAassignment = getTaskAassignment(workflowMappings, wfm);
		
		//Assigning actorId as per last approved record
		//if below condition is true that means particular task is *Forwarded* task
		if (!taskAassignment.getActorId().equals(userTaskDTO.getActorId())) {
			taskAassignment.setActorId(userTaskDTO.getActorId());
			String[] emps = userTaskDTO.getActorId().split(MainetConstants.operator.COMMA);
			taskAassignment.setActorIds(Arrays.stream(emps).collect(Collectors.toSet()));
		}
		LOGGER.info("Returning taskAassignment  - " + taskAassignment);
		return taskAassignment;
	}
    
    @POST
    @Path("/department/service/locationWise")
    @Override
    @ApiOperation(value = "Initial TaskAssignment By OrgId, departmentId, serviceId and WordZoneLevels", notes = "Initial TaskAssignment By OrgId, departmentId, serviceId and WordZoneLevels", response = TaskAssignment.class)
    public TaskAssignment resolveServiceWorkflowTypeByWardZoneAndServiceEventName(
            @ApiParam(value = "Task assignment request", required = true) TaskAssignmentRequest taskAssignmentRequest) {
        LOGGER.info("Before resolveServiceWorkflowType - "+ taskAssignmentRequest.getOrgId()+"|"+taskAssignmentRequest.getDeptId()+"|"+taskAssignmentRequest.getServiceId()+"|"+taskAssignmentRequest.getCodIdOperLevel1()+"|"+taskAssignmentRequest.getCodIdOperLevel2()+"|"+taskAssignmentRequest.getCodIdOperLevel3()+"|"+taskAssignmentRequest.getCodIdOperLevel4()+"|"+taskAssignmentRequest.getCodIdOperLevel5());
        final WorkflowMas workflowType = workflowTyepResolverService.resolveServiceWorkflowType(
                taskAssignmentRequest.getOrgId(),
                taskAssignmentRequest.getDeptId(),
                taskAssignmentRequest.getServiceId(),
                taskAssignmentRequest.getCodIdOperLevel1(),
                taskAssignmentRequest.getCodIdOperLevel2(),
                taskAssignmentRequest.getCodIdOperLevel3(),
                taskAssignmentRequest.getCodIdOperLevel4(),
                taskAssignmentRequest.getCodIdOperLevel5());
        LOGGER.info("Returning TaskAssignment from resolveServiceWorkflowTypeAndGetInitialTaskAassignment.");
        
        return getServicesEventTaskAassignment(workflowType,taskAssignmentRequest);
    }
    
    
    private TaskAssignment getTaskAassignment(List<WorkflowDet> workflowMappings, WorkflowDet wfm,TaskAssignmentRequest taskAssignmentRequest) {

        if ((wfm.getRoleOrEmpIds() != null && !wfm.getRoleOrEmpIds().isEmpty())) {
            LOGGER.info("getRoleOrEmpIds is not empty  - " + wfm.getRoleOrEmpIds());
            final TaskAssignment assignee = new TaskAssignment();

            if (wfm.getRoleType().equals(MainetConstants.Common_Constant.ROLE)) {
                assignee.setActorRole(wfm.getRoleOrEmpIds());
                // Retrieve employee by GMID
                String[] roles = wfm.getRoleOrEmpIds().split(MainetConstants.operator.COMMA);
                String loc1=MainetConstants.NULL;
                String loc2=MainetConstants.NULL;
                String loc3=MainetConstants.NULL;
                String loc4=MainetConstants.NULL;
                String loc5=MainetConstants.NULL;
                
                
                if(null!=taskAssignmentRequest.getCodIdOperLevel1()){
                	loc1=taskAssignmentRequest.getCodIdOperLevel1().toString();
                }
                
                
                if(null!=taskAssignmentRequest.getCodIdOperLevel2()){
                	loc2=taskAssignmentRequest.getCodIdOperLevel2().toString();
                }
                
                if(null!=taskAssignmentRequest.getCodIdOperLevel3()){
                	loc3=taskAssignmentRequest.getCodIdOperLevel3().toString();
                }
                
                
                if(null!=taskAssignmentRequest.getCodIdOperLevel4()){
                	loc4=taskAssignmentRequest.getCodIdOperLevel4().toString();
                }
                
                if(null!=taskAssignmentRequest.getCodIdOperLevel5()){
                	loc5=taskAssignmentRequest.getCodIdOperLevel5().toString();
                }
               
                List<Employee> employees = employeeService
                        .getByGmIdAndWardZone(Arrays.stream(roles).map(Long::parseLong).collect(Collectors.toList()),loc1,
                        		loc2,loc3,loc4,loc5);
                Set<String> empIds = employees.stream().map(e -> e.getEmpId().toString()).collect(Collectors.toSet());
                assignee.setActorIds(empIds);
                assignee.setActorId(empIds.stream().collect(Collectors.joining(MainetConstants.operator.COMMA)));

            } else {
                assignee.setActorId(wfm.getRoleOrEmpIds());
                String[] emps = wfm.getRoleOrEmpIds().split(MainetConstants.operator.COMMA);
                assignee.setActorIds(Arrays.stream(emps).collect(Collectors.toSet()));
            }

            assignee.setOrgId(wfm.getEventOrganisation().getOrgid());
            assignee.setApproverCount(wfm.getApprCount());
            // Event Details
            assignee.setUrl(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfaction());
            assignee.setServiceEventId(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfid());
            assignee.setServiceEventName(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfname());
            assignee.setServiceEventNameReg(wfm.getServicesEventEntity().getSystemModuleFunction().getSmfname_mar());
            // Service details
            assignee.setServiceId(wfm.getWfId().getService().getSmServiceId());
            assignee.setServiceName(wfm.getWfId().getService().getSmServiceName());
            assignee.setServiceNameReg(wfm.getWfId().getService().getSmServiceNameMar());
            // Department details
            assignee.setDeptId(wfm.getEventDepartment().getDpDeptid());
            assignee.setDeptName(wfm.getEventDepartment().getDpDeptdesc());
            assignee.setDeptNameReg(wfm.getEventDepartment().getDpNameMar());
            // SLA Details
            assignee.setSla(wfm.getSla());
            assignee.setSlaUnit(getSlaUnitDesc(wfm.getUnit().toString()));
            assignee.setTaskSlaDurationInMS(wfm.getSlaCalPrimitiveLongValue());
            assignee.setApplicationSlaDurationInMS(
                    workflowMappings.parallelStream().mapToLong(WorkflowDet::getSlaCalPrimitiveLongValue).sum());
            // Escalation details
            int index = workflowMappings.indexOf(wfm);
            int escalationIndex = (index < 0) ? index : index + 1;
            assignee.setEscalationIndex(escalationIndex);
            LOGGER.info("Returning assignee  - " + assignee);
            return assignee;
        } else {
            LOGGER.info("Returning null assignee.");
            return null;
        }
    }

}
