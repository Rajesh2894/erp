package com.abm.mainet.authentication.agency.ui.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
public class AgencySetPasswordModel extends AbstractFormModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2220310369960958073L;

    private String newPassword;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ICitizenRegistrationProcessService iCitizenRegistrationProcessService;

    /**
     *
     * @param validAgency
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public boolean setPassword(final Employee validAgency) throws IllegalAccessException, InvocationTargetException {
        boolean result = false;
        final String empPassword = new String(Base64.getDecoder().decode(getNewPassword()));
        final Employee updatedAgency = iCitizenRegistrationProcessService.setAgencyEmployeePassword(validAgency, empPassword,
                getUserSession().getEmployee());

        if (updatedAgency != null) {
            result = true;
            UserSession.getCurrent().setEmployee(updatedAgency);

        }

        return result;
    }

    /**
     *
     * @param empMobileNo
     * @return
     */
    public Employee getAgencyByMobile(final String empMobileNo) {
        final Employee existingAgency = iEmployeeService.getAgencyByEmplTypeAndMobile(empMobileNo, getUserSession().getEmplType(),
                getUserSession().getOrganisation(), MainetConstants.IsDeleted.ZERO);

        return existingAgency;
    }

    /**
     *
     * @return
     */
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
