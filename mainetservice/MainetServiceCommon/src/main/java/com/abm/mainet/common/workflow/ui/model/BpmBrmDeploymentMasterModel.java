package com.abm.mainet.common.workflow.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.STATUS;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.BpmBrmDeploymentMasterDTO;
import com.abm.mainet.common.workflow.service.BpmBrmDeploymentService;
import com.abm.mainet.common.workflow.ui.validator.BpmBrmDeploymentValidator;

@Component
@Scope("session")
public class BpmBrmDeploymentMasterModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String saveMode;
    private BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto;
    private List<BpmBrmDeploymentMasterDTO> bpmBrmDeploymentMasterDtos = new ArrayList<>();
    private Set<LookUp> departments = new HashSet<>();

    @Autowired
    private BpmBrmDeploymentService bpmBrmDeploymentService;

    @Autowired
    private DepartmentJpaRepository departmentJpaRepository;

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public BpmBrmDeploymentMasterDTO getBpmBrmDeploymentMasterDto() {
        return bpmBrmDeploymentMasterDto;
    }

    public void setBpmBrmDeploymentMasterDto(BpmBrmDeploymentMasterDTO bpmBrmDeploymentMasterDto) {
        this.bpmBrmDeploymentMasterDto = bpmBrmDeploymentMasterDto;
    }

    public List<BpmBrmDeploymentMasterDTO> getBpmBrmDeploymentMasterDtos() {
        return bpmBrmDeploymentMasterDtos;
    }

    public void setBpmBrmDeploymentMasterDtos(List<BpmBrmDeploymentMasterDTO> bpmBrmDeploymentMasterDtos) {
        this.bpmBrmDeploymentMasterDtos = bpmBrmDeploymentMasterDtos;
    }

    public Set<LookUp> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<LookUp> departments) {
        this.departments = departments;
    }

    @Override
    public boolean saveForm() {

        validateBean(bpmBrmDeploymentMasterDto, BpmBrmDeploymentValidator.class);

        Employee employee = UserSession.getCurrent().getEmployee();
        if (getSaveMode().equals(MainetConstants.WorkFlow.BpmBrmDeployment.SaveMode.EDIT)) {
            bpmBrmDeploymentMasterDto.setUpdatedBy(employee.getEmpId());
            bpmBrmDeploymentMasterDto.setUpdatedDate(new Date());
            bpmBrmDeploymentMasterDto.setLgIpMacUpd(employee.getEmppiservername());
            bpmBrmDeploymentService.updateBpmBrmDeploymentMaster(bpmBrmDeploymentMasterDto);
        } else {
            if (hasValidationErrors())
                return false;
            bpmBrmDeploymentMasterDto.setCreatedBy(employee.getEmpId());
            bpmBrmDeploymentMasterDto.setCreateDate(new Date());
            bpmBrmDeploymentMasterDto.setLgIpMac(employee.getEmppiservername());
            bpmBrmDeploymentMasterDto.setStatus(MainetConstants.FlagY);
            bpmBrmDeploymentService.saveBpmBrmDeploymentMaster(bpmBrmDeploymentMasterDto);
        }
        return true;
    }

    @Override
    protected void initializeModel() {
        List<Department> dpts = departmentJpaRepository.findByStatus(STATUS.ACTIVE);
        dpts.forEach(d -> {
            LookUp detData = new LookUp();
            detData.setDescLangFirst(d.getDpDeptdesc());
            detData.setDescLangSecond(d.getDpNameMar());
            detData.setLookUpId(d.getDpDeptid());
            detData.setLookUpCode(d.getDpDeptcode());
            departments.add(detData);
        });
    }
}
