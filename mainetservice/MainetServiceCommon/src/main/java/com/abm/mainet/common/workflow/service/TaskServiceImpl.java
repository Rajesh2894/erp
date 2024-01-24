package com.abm.mainet.common.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;

/**
 * 
 * @author sanket.joshi
 *
 */
@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;

    @Autowired
    private IWorkflowTypeDAO workflowTypeDAO;

    @Override
    public List<UserTaskDTO> getTaskList(TaskSearchRequest requester) throws Exception {
        return workflowExecutionService.searchTask(requester);
    }

    @Override
    public List<UserTaskDTO> getTaskList(String uuid) throws Exception {
        List<UserTaskDTO> taskList = new ArrayList<>();
        if (uuid != null && !uuid.isEmpty()) {
            taskList = workflowExecutionService.getTaskList(uuid);
        }
        return taskList;
    }

    @Override
    public UserTaskDTO getTask(String uuid) throws Exception {
        List<UserTaskDTO> taskList = new ArrayList<>();
        if (uuid != null && !uuid.isEmpty()) {
            taskList = workflowExecutionService.getTaskList(uuid);
        }
        UserTaskDTO task = null;
        if (!taskList.isEmpty()) {
            taskList.subList(1, taskList.size()).clear();
            task = taskList.get(0);
        }
        return task;
    }

    @Override
    public List<UserTaskDTO> fetchClosedTaskList(TaskSearchRequest taskSearchRequest) {
        List<UserTaskDTO> userTaskList = new ArrayList<>();
        List<Object[]> objList = workflowTypeDAO.fetchClosedTaskList(taskSearchRequest);
        for (Object[] obj : objList) {
            UserTaskDTO crdto = new UserTaskDTO();
            String applicantName = "";
            crdto.setOrgId((obj[1] != null) ? Long.parseLong(obj[1].toString()) : null);
            crdto.setApplicationId((obj[0] != null) ? Long.parseLong(obj[0].toString()) : null);
            crdto.setDeptId((obj[2] != null) ? Long.parseLong(obj[2].toString()) : null);
            crdto.setServiceId((obj[5] != null) ? Long.parseLong(obj[5].toString()) : null);
            crdto.setActorId((obj[8] != null) ? obj[8].toString() : MainetConstants.BLANK);
            
            if (taskSearchRequest.getLangId() == 1) {
                crdto.setDeptName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
                crdto.setServiceName((obj[6] != null) ? obj[6].toString() : MainetConstants.BLANK);
            } else {
                crdto.setDeptName((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
                crdto.setServiceName((obj[7] != null) ? obj[7].toString() : MainetConstants.BLANK);
            }
            if (obj[8] != null) {
                applicantName += obj[8].toString();
            }
            if (obj[9] != null) {
                applicantName += obj[9].toString();
            }
            if (obj[10] != null) {
                applicantName += obj[10].toString();
            }
       
            crdto.setActorId(applicantName);
            String DATE_FORMAT = "dd/MM/yyyy";
            String TIME_FORMAT = "hh:mm a";
            String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

            crdto.setRequestDate(Utility.dateToString((Date) obj[11], DATE_TIME_FORMAT));
            crdto.setType((obj[12] != null) ? obj[12].toString() : MainetConstants.BLANK);
            userTaskList.add(crdto);
        }

        return userTaskList;
    }

    @Override
    public List<UserTaskDTO> fetchIssuedTaskList(TaskSearchRequest taskSearchRequest) {
        List<UserTaskDTO> userTaskList = new ArrayList<>();
        List<Object[]> objList = workflowTypeDAO.fetchIssuedTaskList(taskSearchRequest);
        for (Object[] obj : objList) {
            UserTaskDTO crdto = new UserTaskDTO();
            String applicantName = "";
            crdto.setOrgId((obj[1] != null) ? Long.parseLong(obj[1].toString()) : null);
            crdto.setApplicationId((obj[0] != null) ? Long.parseLong(obj[0].toString()) : null);
            crdto.setDeptId((obj[2] != null) ? Long.parseLong(obj[2].toString()) : null);
            crdto.setServiceId((obj[5] != null) ? Long.parseLong(obj[5].toString()) : null);
            crdto.setActorId((obj[8] != null) ? obj[8].toString() : MainetConstants.BLANK);

            if (taskSearchRequest.getLangId() == 1) {
                crdto.setDeptName((obj[3] != null) ? obj[3].toString() : MainetConstants.BLANK);
                crdto.setServiceName((obj[6] != null) ? obj[6].toString() : MainetConstants.BLANK);
            } else {
                crdto.setDeptNameReg((obj[4] != null) ? obj[4].toString() : MainetConstants.BLANK);
                crdto.setServiceNameReg((obj[7] != null) ? obj[7].toString() : MainetConstants.BLANK);
            }
            if (obj[8] != null) {
                applicantName += obj[8].toString();
            }
            if (obj[9] != null) {
                applicantName += obj[9].toString();
            }
            if (obj[10] != null) {
                applicantName += obj[10].toString();
            }
            crdto.setActorId(applicantName);
            String DATE_FORMAT = "dd/MM/yyyy";
            String TIME_FORMAT = "hh:mm a";
            String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

            crdto.setRequestDate(Utility.dateToString((Date) obj[11], DATE_TIME_FORMAT));
            crdto.setIssuedDate(Utility.dateToString((Date) obj[12], DATE_TIME_FORMAT));
            crdto.setType((obj[13] != null) ? obj[13].toString() : MainetConstants.BLANK);
            userTaskList.add(crdto);
        }

        return userTaskList;
    }
  
}
