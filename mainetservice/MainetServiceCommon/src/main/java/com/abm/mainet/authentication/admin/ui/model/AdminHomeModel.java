package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = "session")
public class AdminHomeModel extends AbstractFormModel implements Serializable {
    private static final long serialVersionUID = -8089318502962092944L;

    @Autowired
    private IEmployeeService iEmployeeService;

    private int preTrnId;
    private int postTrnId;
    private String aboutUsDescFirstPara;
    private String aboutUsDescSecondPara;

    private Employee currentEmpForEditProfile;

    private String empNameForEditProfile;

    private Boolean applicableENV;
    
    private Boolean sudaCheck;
    
    private List<TbDepartment> departmentsList;

    public int getPreTrnId() {
        return preTrnId;
    }

    public void setPreTrnId(final int preTrnId) {
        this.preTrnId = preTrnId;
    }

    public int getPostTrnId() {
        return postTrnId;
    }

    public void setPostTrnId(final int postTrnId) {
        this.postTrnId = postTrnId;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.DirectoryTree.SLIDER_IMAGE;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(final String outputPath) {
    }

    @Override
    public String getActiveClass() {
        return "home";
    }

    /**
     * @return the aboutUsDescFirstPara
     */
    public String getAboutUsDescFirstPara() {
        return aboutUsDescFirstPara;
    }

    /**
     * @return the aboutUsDescSecondPara
     */
    public String getAboutUsDescSecondPara() {
        return aboutUsDescSecondPara;
    }

    /**
     * @return the currentEmpForEditProfile
     */
    public Employee getCurrentEmpForEditProfile() {
        return currentEmpForEditProfile;
    }

    /**
     * @param currentEmpForEditProfile the currentEmpForEditProfile to set
     */
    public void setCurrentEmpForEditProfile(final Employee currentEmpForEditProfile) {
        this.currentEmpForEditProfile = currentEmpForEditProfile;
    }

    /**
     * @param sudaCheck the sudaEnvironment to set US 141152
     */
    
    public Boolean getSudaCheck() {
		return sudaCheck;
	}

	public void setSudaCheck(Boolean sudaCheck) {
		this.sudaCheck = sudaCheck;
	}

	/**
     * @return the empNameForEditProfile
     */
    public String getEmpNameForEditProfile() {
        return empNameForEditProfile;
    }

    /**
     * @param empNameForEditProfile the empNameForEditProfile to set
     */
    public void setEmpNameForEditProfile(final String empNameForEditProfile) {
        this.empNameForEditProfile = empNameForEditProfile;
    }

    public Boolean getApplicableENV() {
        return applicableENV;
    }

    public void setApplicableENV(Boolean applicableENV) {
        this.applicableENV = applicableENV;
    }

    public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

    public void saveEditedInformation(final Employee modifiedEmployee) {

        iEmployeeService.saveEditProfileInfo(modifiedEmployee);
    }

    public boolean isUniqueEmailAddress(final String empEMail) {
        boolean isUnique = false;

        final List<Employee> employeeList = iEmployeeService.getEmployeeByEmpEMailAndType(empEMail,
                UserSession.getCurrent().getEmployee().getEmplType(), UserSession.getCurrent().getOrganisation(),
                MainetConstants.IsDeleted.ZERO, false);

        final LookUp lookUp = getCitizenLooUp();
        if ((employeeList == null) || (employeeList.size() == 0)) {
            return true;
        }

        else {
            for (final Employee employee : employeeList) {

                if (employee.getEmplType() != null) {
                    if ((employee.getEmplType() == lookUp.getLookUpId())) {
                        return false;
                    } else {
                        isUnique = true;
                    }
                } else {
                    isUnique = true;

                }

            }

            return isUnique;
        }

    }

    public LookUp getCitizenLooUp() {
        final List<LookUp> lookUpList = super.getLookUpList(PrefixConstants.NEC.PARENT);

        for (final LookUp lookUp : lookUpList) {
            if (lookUp.getLookUpCode().equalsIgnoreCase(PrefixConstants.NEC.CITIZEN)) {
                return lookUp;
            }
        }
        return null;
    }

    public String setEmailId(final String mailId) {
        final Employee updateEmployee = UserSession.getCurrent().getEmployee();
        if (updateEmployee.getEmpGender().length() > 1) {
            updateEmployee
                    .setEmpGender(getNonHierarchicalLookUpObject(new Long(updateEmployee.getEmpGender())).getLookUpCode());
        }
        updateEmployee.setEmpemail(mailId);
        iEmployeeService.saveEmployee(updateEmployee);

        if (updateEmployee != null) {
            UserSession.getCurrent().setEmployee(updateEmployee);
        }
        return "";
    }

    public void setEmpGender() {
        final List<LookUp> genderLookUp = getLevelData(MainetConstants.GENDER);

        for (final LookUp lookUp : genderLookUp) {
            if ((getCurrentEmpForEditProfile().getEmpGender() != null)
                    && getCurrentEmpForEditProfile().getEmpGender().equalsIgnoreCase(lookUp.getLookUpCode())) {
                getCurrentEmpForEditProfile().setEmpGender(lookUp.getLookUpId() + "");
            }
        }
    }

}
