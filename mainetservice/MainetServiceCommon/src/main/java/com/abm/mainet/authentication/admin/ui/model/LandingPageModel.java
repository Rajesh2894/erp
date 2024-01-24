package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component
@Scope(value = "session")
public class LandingPageModel extends AbstractFormModel implements Serializable {
    private static final long serialVersionUID = -1643180863109171050L;

    @Autowired
    private IOrganisationService iOrganisationService;

    private List<LookUp> districtList = new ArrayList<>(0);
    private List<LookUp> municipalList = new ArrayList<>(0);

    private String selectedMunicipal = "";
    private long selectedDistrict = -1;

    private String aboutOrgDesc;
    private String aboutOrgDescDetails;
    private List<String> listOfImgPaths;
    private List<Organisation> organisationsList = new ArrayList<>();

    /**
     * @see com.abm.mainet.ui.model.AbstractModel#initializeModel()
     */
    @Override
    public void initializeModel() {

        super.initializeModel();
        setOrganisationsList(iOrganisationService.findAllActiveOrganization("A"));
    }

    /**
     * To Set Municipal List
     */
    public void setLandingPage() {
        final UserSession userSession = UserSession.getCurrent();
        final int langId = userSession.getLanguageId();
        final List<LookUp> dList = getDistricts(langId);
        Collections.sort(dList);
        setDistrictList(dList);

        if ((getDistrictList() != null) && (getDistrictList().size() > 0)) {
            for (final LookUp lookUp : getDistrictList()) {
                if ((lookUp.getDefaultVal() != null) && !lookUp.getDefaultVal().equalsIgnoreCase("")
                        && lookUp.getDefaultVal().equalsIgnoreCase("Y")) {
                    setSelectedDistrict(lookUp.getLookUpId());
                    break;
                }
            }
        }
    }

    private List<LookUp> getDistricts(final int languageId) {
	return CommonMasterUtility.getLookUps(PrefixConstants.LookUp.DISTRICT, getAppSession().getSuperUserOrganization());
    }

    /**
     * @Method To get header logs details from "TIM" prefix.
     * @return List of {@link LookUp} for Tool Bar Image
     */
    @Override
    public List<LookUp> getHeaderDetails() {
        return Collections.emptyList();
    }

    /**
     * @Method To get ULB name from super organization.
     * @return {@link LookUp}
     */
    public String getOrganisationName() {
        final Organisation organisation = getAppSession().getSuperUserOrganization();

        final int languageId = Utility.getDefaultLanguageId(organisation);

        if (languageId == MainetConstants.ENGLISH) {
            return organisation.getONlsOrgname();
        } else {
            return organisation.getONlsOrgnameMar();
        }
    }

    public void setSessionAttributeValue(final long orgId) {
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        getUserSession().setOrganisation(organisation);
        final String loginName = getAppSession().getMessage("citizen.noUser.loginName");
        getUserSession().setEmployee(super.getEmployeeByLoginName(loginName, organisation));

        final Employee loggedEmployee = getUserSession().getEmployee();
        if ((loggedEmployee != null) && (loggedEmployee.getEmpId() != 0l)
                && (loggedEmployee.getOrganisation().getOrgid() != organisation.getOrgid())) {
            removeSessionEmployee();
        }
    }

    private void removeSessionEmployee() {
        getUserSession().setEmployee(new Employee());
    }

    /**
     * @return the districtList
     */
    public List<LookUp> getDistrictList() {
        return districtList;
    }

    /**
     * @param districtList the districtList to set
     */
    private void setDistrictList(final List<LookUp> districtList) {
        this.districtList = districtList;
    }

    /**
     * @return the selectedMunicipal
     */
    public String getSelectedMunicipal() {
        return selectedMunicipal;
    }

    /**
     * @param selectedMunicipal the selectedMunicipal to set
     */
    public void setSelectedMunicipal(final String selectedMunicipal) {
        this.selectedMunicipal = selectedMunicipal;
    }

    /**
     * @return the selectedDistrict
     */
    public long getSelectedDistrict() {
        return selectedDistrict;
    }

    /**
     * @param selectedDistrict the selectedDistrict to set
     */
    public void setSelectedDistrict(final long selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    /**
     * @return the municipalList
     */
    public List<LookUp> getMunicipalList() {
        return municipalList;
    }

    /**
     * @return the aboutOrgDesc
     */
    public String getAboutOrgDesc() {
        return aboutOrgDesc;
    }

    /**
     * @return the aboutOrgDescDetails
     */
    public String getAboutOrgDescDetails() {
        return aboutOrgDescDetails;
    }

    /**
     * @return the listOfImgPaths
     */
    public List<String> getListOfImgPaths() {
        return listOfImgPaths;
    }

    /**
     * @param listOfImgPaths the listOfImgPaths to set
     */
    public void setListOfImgPaths(final List<String> listOfImgPaths) {
        this.listOfImgPaths = listOfImgPaths;
    }

    /**
     * @return the outputPath
     */
    public String getImageOutputPath() {
        return MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                + getAppSession().getSuperUserOrganization().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR
                + MainetConstants.DirectoryTree.SLIDER_IMAGE;
    }

    public List<Organisation> getOrganisationsList() {
        return organisationsList;
    }

    /**
     * @param organisationsList the organisationsList to set
     */
    public void setOrganisationsList(final List<Organisation> organisationsList) {
        this.organisationsList = organisationsList;
    }
}
