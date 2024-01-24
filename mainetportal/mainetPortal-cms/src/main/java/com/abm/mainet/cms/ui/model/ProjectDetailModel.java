package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.cms.service.IAboutProjectService;
import com.abm.mainet.cms.ui.validator.ProjectDetailValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Manish.Gawali
 */
@Component
@Scope("session")
public class ProjectDetailModel extends AbstractEntryFormModel<AboutProject> implements Serializable {

    private static final long serialVersionUID = -7348842270888246462L;
    private AboutProject aboutProject;

    @Autowired
    private IAboutProjectService iAboutProjectService;
    private List<AboutProject> aboutProjects;
    private String MODE = MainetConstants.Transaction.Mode.ADD;

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {

        this.makkerchekkerflag = makkerchekkerflag;

    }

    public boolean updateAboutProjectDetails() {
        getiAboutProjectService().saveAboutProjectDetails(aboutProject, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getEmployee(), UserSession.getCurrent().getLanguageId());
        return true;
    }

    public boolean deleteAboutProjectDetails() {
        aboutProject.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        return true;
    }

    @Override
    public void addForm() {
        setEntity(new AboutProject());
        setMODE(MainetConstants.Transaction.Mode.ADD);
    }

    @Override
    public void editForm(final long rowId) {
    	 setMODE(MainetConstants.Transaction.Mode.UPDATE);
        setEntity(iAboutProjectService.findAboutProjectDetail(rowId, UserSession.getCurrent().getOrganisation()));
    }

    @Override
    public boolean saveOrUpdateForm() {
        validateBean(getEntity(), ProjectDetailValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        iAboutProjectService.saveAboutProjectDetails(getEntity(), UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getEmployee(), UserSession.getCurrent().getLanguageId());
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractFormModel#delete(long)
     */
    @Override
    public void delete(final long rowId) {
        final AboutProject delAboutProject = iAboutProjectService.findAboutProjectDetail(rowId,
                UserSession.getCurrent().getOrganisation());
        if (delAboutProject != null) {
            iAboutProjectService.deleteAboutProject(delAboutProject);
        }

    }

    public AboutProject getAboutProject() {
        return aboutProject;
    }

    public void setAboutProject(final AboutProject aboutProject) {
        this.aboutProject = aboutProject;
    }

    public IAboutProjectService getiAboutProjectService() {
        return iAboutProjectService;
    }

    public void setiAboutProjectService(final IAboutProjectService iAboutProjectService) {
        this.iAboutProjectService = iAboutProjectService;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(final String mODE) {
        MODE = mODE;
    }

    public List<AboutProject> getAboutProjects() {
        return aboutProjects;
    }

    public void setAboutProjects(final List<AboutProject> aboutProjects) {
        this.aboutProjects = aboutProjects;
    }

    // ---------Limit rercord------
    public boolean checkMaxRows() {
        return iAboutProjectService.isAccessToAdd(UserSession.getCurrent().getOrganisation());

    }

}
