package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.dto.SLRMEmployeeMasterDTO;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class EmployeeScheduleReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 6524827804382185438L;

    List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
    EmployeeScheduleDTO employeeScheduleDTO = new EmployeeScheduleDTO();
    EmployeeScheduleDTO employeeDTO = new EmployeeScheduleDTO();
    List<SLRMEmployeeMasterDTO> employeeBeanList = new ArrayList<>();
    List<EmployeeScheduleDTO> employeeScheduleList;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public List<SLRMEmployeeMasterDTO> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<SLRMEmployeeMasterDTO> employeeList) {
        this.employeeList = employeeList;
    }

    public EmployeeScheduleDTO getEmployeeScheduleDTO() {
        return employeeScheduleDTO;
    }

    public void setEmployeeScheduleDTO(EmployeeScheduleDTO employeeScheduleDTO) {
        this.employeeScheduleDTO = employeeScheduleDTO;
    }

    public List<EmployeeScheduleDTO> getEmployeeScheduleList() {
        return employeeScheduleList;
    }

    public void setEmployeeScheduleList(List<EmployeeScheduleDTO> employeeScheduleList) {
        this.employeeScheduleList = employeeScheduleList;
    }

    public List<SLRMEmployeeMasterDTO> getEmployeeBeanList() {
        return employeeBeanList;
    }

    public void setEmployeeBeanList(List<SLRMEmployeeMasterDTO> employeeBeanList) {
        this.employeeBeanList = employeeBeanList;
    }

    public EmployeeScheduleDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeScheduleDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

}
