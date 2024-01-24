/*
 * Created on 19 Aug 2015 ( Time 17:12:00 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.EmployeeBean;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class EmployeeServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public EmployeeServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }

    /**
     * Mapping from 'EmployeeEntity' to 'Employee'
     * @param employeeEntity
     */
    public EmployeeBean mapEmployeeEntityToEmployee(final Employee employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }

        Employee updatedBy = employeeEntity.getUpdatedBy();
        Employee userId = employeeEntity.getUserId();

        employeeEntity.setUpdatedBy(null);
        employeeEntity.setUserId(null);

        // --- Generic mapping
        final EmployeeBean employee = map(employeeEntity, EmployeeBean.class);

        // --- Link mapping ( link to TbOrganisation )
        if (employeeEntity.getOrganisation() != null) {
            employee.setOrgid(Long.valueOf(employeeEntity.getOrganisation().getOrgid()));
        }
        // --- Link mapping ( link to Designation )
        if (employeeEntity.getDesignation() != null) {
            employee.setDsgid(employeeEntity.getDesignation().getDsgid());
            if (UserSession.getCurrent().getLanguageId() == 1) {
                employee.setDesignName(employeeEntity.getDesignation().getDsgname());
            } else {
                employee.setDesignName(employeeEntity.getDesignation().getDsgnameReg());
            }
        }
        // --- Link mapping ( link to TbDepartment )
        if (employeeEntity.getTbDepartment() != null) {
            employee.setDpDeptid(Long.valueOf(employeeEntity.getTbDepartment().getDpDeptid()));
            if (UserSession.getCurrent().getLanguageId() == 1) {
                employee.setDeptName(employeeEntity.getTbDepartment().getDpDeptdesc());
            } else {
                employee.setDeptName(employeeEntity.getTbDepartment().getDpNameMar());
            }
        }

        if (employeeEntity.getEmpphotopath() != null) {
            employee.setEmpphotopath(employeeEntity.getEmpphotopath());
        }
        if (employeeEntity.getScansignature() != null) {
            employee.setScansignature(employeeEntity.getScansignature());
        }
        if (employeeEntity.getEmpuiddocpath() != null) {
            employee.setEmpuiddocpath(employeeEntity.getEmpuiddocpath());
        }

        if (null != updatedBy) {
            employee.setUpdatedBy(updatedBy);
        }
        if (null != userId) {
            employee.setUserId(userId);
        }

        return employee;
    }

    /**
     * Mapping from 'Employee' to 'EmployeeEntity'
     * @param employee
     * @param employeeEntity
     */
    public void mapEmployeeToEmployeeEntity(final EmployeeBean employee, final Employee employeeEntity) {
        if (employee == null) {
            return;
        }

        // --- Generic mapping
        map(employee, employeeEntity);

        // --- Link mapping ( link : employee )
        if (hasLinkToTbOrganisation(employee)) {
            final Organisation tbOrganisation1 = new Organisation();
            tbOrganisation1.setOrgid(employee.getOrgid().longValue());
            employeeEntity.setOrganisation(tbOrganisation1);
        } else {
            employeeEntity.setOrganisation(null);
        }

        // --- Link mapping ( link : employee )
        if (hasLinkToDesignation(employee)) {
            final Designation designation2 = new Designation();
            designation2.setDsgid(employee.getDsgid());
            employeeEntity.setDesignation(designation2);
        } else {
            employeeEntity.setDesignation(null);
        }

        // --- Link mapping ( link : employee )
        if (hasLinkToTbDepartment(employee)) {
            final Department tbDepartment4 = new Department();
            tbDepartment4.setDpDeptid(employee.getDpDeptid().longValue());
            employeeEntity.setTbDepartment(tbDepartment4);
        } else {
            employeeEntity.setTbDepartment(null);
        }

        if (employee.getIsEmpPhotoDeleted().equals(MainetConstants.Common_Constant.YES)) {
            employeeEntity.setEmpphotopath(null);
        } else {
            if (employee.getEmpphotopath() != null) {
                employeeEntity.setEmpphotopath(employee.getEmpphotopath());
            }
            if (employee.getScansignature() != null) {
                employeeEntity.setScansignature(employee.getScansignature());
            }
            if (employee.getEmpuiddocpath() != null) {
                employeeEntity.setEmpuiddocpath(employee.getEmpuiddocpath());
            }
        }

    }

    /**
     * Verify that TbOrganisation id is valid.
     * @param TbOrganisation TbOrganisation
     * @return boolean
     */
    private boolean hasLinkToTbOrganisation(final EmployeeBean employee) {
        if (employee.getOrgid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that Designation id is valid.
     * @param Designation Designation
     * @return boolean
     */
    private boolean hasLinkToDesignation(final EmployeeBean employee) {
        if (employee.getDsgid() != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify that TbDepartment id is valid.
     * @param TbDepartment TbDepartment
     * @return boolean
     */
    private boolean hasLinkToTbDepartment(final EmployeeBean employee) {
        if (employee.getDpDeptid() != null) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}