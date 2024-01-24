package com.abm.mainet.authentication.citizen.ui.model;

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
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

@Component
@Scope(value = "session")
public class CitizenSetPasswordModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 1748779249610374627L;

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

        Employee loggedInUser = getUserSession().getEmployee();
        if (getUserSession().getEmployee().getEmpId() == 0l) {
            final String loginName = getAppSession().getMessage("citizen.noUser.loginName");
            loggedInUser = super.getEmployeeByLoginName(loginName, ApplicationSession.getInstance().getSuperUserOrganization());
        }
        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
        final Employee updateEmployee = iCitizenRegistrationProcessService.setEmployeePassword(mobileNo, empPassword,
                loggedInUser);

        if (updateEmployee != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return result;
    }

    /**
     *
     * @param empMobileNo
     * @return
     */
    public Employee getCitizenByMobile(final String empMobileNo) {
        Employee validateEmployee = null;
        final LookUp citizenLookUp = getCitizenLooUp();
        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpMobileNo(empMobileNo,
                ApplicationSession.getInstance().getSuperUserOrganization(), MainetConstants.IsDeleted.ZERO,((citizenLookUp!=null)?citizenLookUp.getLookUpId():0));
        
        if ((employeeList != null) && (employeeList.size() == 1)) {
            if (employeeList.get(0).getEmplType() != null) {
                if (employeeList.get(0).getEmplType() == citizenLookUp.getLookUpId()) {
                    validateEmployee = employeeList.get(0);
                }
            }

        } else {
            for (final Employee employee : employeeList) {
                if (employee.getEmplType() != null) {
                    if (employee.getEmplType() == citizenLookUp.getLookUpId()) {
                        validateEmployee = employee;
                    }
                }
            }
        }

        return validateEmployee;
    }

    /**
     *
     * @return LookUp Object Of Citizen
     */
    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getCitizenLookUpList(MainetConstants.NEC.PARENT);

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
