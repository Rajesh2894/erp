package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;

/**
 * @author hiren.poriya
 * @Since 17-May-2018
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class WorkAssigneeModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private WorkOrderDto workOderDto;
    private List<Object[]> empList;
    private List<Object[]> workOderList;
    private String mode;
    private String assignedWorkIds;
    private Long empId;
    private Long workOrderId;

    @Override
    public boolean saveForm() {

        // assigned work Id
        List<Long> workIdList = Pattern.compile(MainetConstants.operator.COMMA).splitAsStream(assignedWorkIds)
                .map(Long::parseLong).collect(Collectors.toList());
        List<Long> removedWorkId = new ArrayList<>();
        // Removed Assigned Works
        if (mode.equals(MainetConstants.MODE_EDIT)) {
            workOderDto.getTenderMasterDto().getWorkDto().stream()
                    .filter(work -> !workIdList.contains(work.getWorkId())).forEach(tw -> {
                        removedWorkId.add(tw.getWorkId());
                    });
        }

        ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class).updateTenderWorkAssignee(
                workIdList, empId,
                new Date(), removedWorkId);
        setSuccessMessage(ApplicationSession.getInstance().getMessage("work.assignee.success"));
        return true;
    }

    public WorkOrderDto getWorkOderDto() {
        return workOderDto;
    }

    public void setWorkOderDto(WorkOrderDto workOderDto) {
        this.workOderDto = workOderDto;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Object[]> getWorkOderList() {
        return workOderList;
    }

    public void setWorkOderList(List<Object[]> workOderList) {
        this.workOderList = workOderList;
    }

    public List<Object[]> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Object[]> empList) {
        this.empList = empList;
    }

    public String getAssignedWorkIds() {
        return assignedWorkIds;
    }

    public void setAssignedWorkIds(String assignedWorkIds) {
        this.assignedWorkIds = assignedWorkIds;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

}
