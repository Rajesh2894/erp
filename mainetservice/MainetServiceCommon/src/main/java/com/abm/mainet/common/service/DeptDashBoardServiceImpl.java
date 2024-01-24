package com.abm.mainet.common.service;

import java.util.Collections;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Service
@WebService(endpointInterface = "com.abm.mainet.common.service.DeptDashBoardService")
@Api("/deptDashBoardService")
@Path("/deptDashBoardService")
@Transactional(readOnly = true)
public class DeptDashBoardServiceImpl implements DeptDashBoardService {

	private static final Logger log = LoggerFactory.getLogger(DeptDashBoardServiceImpl.class);

	@Autowired
    private IWorkflowExecutionService iWorkflowExecutionService;

	@POST
	@Path("/emp/assignedTask")
	@ApiOperation(value = "Return Dept emp assigned tasks", notes = "Return Dept emp assigned tasks", response = UserTaskDTO.class)
	@Override
	public  List<UserTaskDTO> getDeptEmpAssignedTask(@ApiParam(value = "Request for search TaskSearchRequest", required = true) @RequestBody final TaskSearchRequest taskSearchRequest){
    	List<UserTaskDTO> taskList = Collections.emptyList();
    	if (taskSearchRequest != null) {
    		  TaskSearchRequest searchRequest = new TaskSearchRequest();
    		try {
    				searchRequest.setEmpId(taskSearchRequest.getEmpId());
    				searchRequest.setOrgId(taskSearchRequest.getOrgId());
    				searchRequest.setLangId(taskSearchRequest.getLangId());
    				searchRequest.setStatus(taskSearchRequest.getStatus());
    				taskList= iWorkflowExecutionService.searchTask(searchRequest);
    				
               }catch (final Exception ex) {
    		     log.error("problem occurred while getting dashboard data for dept employee:", ex);
    		     UserTaskDTO dto = new UserTaskDTO();
    		     dto.setTaskStatusDesc("Failed while calling BPM API");
    		     taskList.add(dto);
          }

	   }else { 
		  log.error("Json Body has error"); 
		  UserTaskDTO dto = new UserTaskDTO();
		  dto.setTaskStatusDesc("Bad request paramteres are missing");
		  taskList.add(dto);
		}
    return taskList;
    }

}
