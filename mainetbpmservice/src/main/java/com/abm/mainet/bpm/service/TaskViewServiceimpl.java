package com.abm.mainet.bpm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bpm.domain.TaskView;
import com.abm.mainet.bpm.repository.TaskViewRepository;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class TaskViewServiceimpl implements ITaskViewService {

    @Autowired
    private TaskViewRepository taskViewRepository;

    //need to refactor
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<TaskView> getAllTaskById(Long applicationnId, String referenceNo,Long workFlowId) {
    	
    	List<TaskView> taskList=taskViewRepository.getAllTaskById(applicationnId, referenceNo);
    	
    	Optional<TaskView> workflowIds = taskList.stream().filter(c -> c.getWorkflowId() != null).findAny();
    	if(workflowIds.isPresent()) {
    		return taskList.stream().filter(c-> c.getWorkflowId()!= null && c.getWorkflowId().equals(workFlowId)).collect(Collectors.toList());
    	}else {
    		return taskList;
    	}
    }

}
