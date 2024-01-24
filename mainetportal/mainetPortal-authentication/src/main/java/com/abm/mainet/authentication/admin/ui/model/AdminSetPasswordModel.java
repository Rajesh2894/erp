package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.authentication.citizen.service.ICitizenRegistrationProcessService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Component
@Scope(value = "session")
public class AdminSetPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 3178267818325509951L;

    private String newPassword;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    /**
     *
     * @param mobileNo
     * @return
     */
    public boolean setPassword(final String mobileNo) {
        boolean result = false;

        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
        final Employee updateEmployee = iCitizenRegistrationProcessService.setAdminEmployeePassword(mobileNo, empPassword,
                getUserSession().getEmployee());

        if (updateEmployee != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return result;
    }

    public Employee getAdminByMobile(final String empMobileNo) {
        Employee validateEmployee = null;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(empMobileNo,
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO,MainetConstants.ADMIN_EMP_TYPE);

        if ((employeeList != null) && (employeeList.size() == 1)) {
            if (employeeList.get(0).getEmplType() == null) {
                validateEmployee = employeeList.get(0);
            }
        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() == null) {
                    validateEmployee = employee;
                }
            }
        }

        return validateEmployee;
    }

    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getLookUpList(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

}
