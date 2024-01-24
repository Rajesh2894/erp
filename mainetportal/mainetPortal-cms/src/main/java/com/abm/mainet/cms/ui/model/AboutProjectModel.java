package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.cms.service.IAboutProjectService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;

/**
 * @author manish.gawali
 */
@Component
@Scope("session")
public class AboutProjectModel extends AbstractEntryFormModel<AboutProject> implements Serializable {
    private static final long serialVersionUID = 3374963892341402277L;
    private AboutProject aboutProject;

    @Autowired
    private IAboutProjectService iAboutProjectService;
    private List<AboutProject> aboutProjects;
    private Organisation orgid;
    private String MODE = MainetConstants.Transaction.Mode.ADD;
    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {

        this.makkerchekkerflag = makkerchekkerflag;

    }

    @Override
    public void addForm() {
        setEntity(new AboutProject());

    }

    public AboutProject getAboutProject() {
        return aboutProject;
    }

    public void setAboutProject(final AboutProject aboutProject) {
        this.aboutProject = aboutProject;
    }

    public List<AboutProject> getAboutProjects() {
        return aboutProjects;
    }

    public void setAboutProjects(final List<AboutProject> aboutProjects) {
        final List<AboutProject> aboutProjectDTOs = new ArrayList<>();
        aboutProjectDTOs.add(prepareDTOObject(aboutProject));
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(final String mODE) {
        MODE = mODE;
    }

    public List<AboutProject> generateAboutProjectList(String flag) {

        final List<AboutProject> aboutProjects = getiAboutProjectService()
                .getAboutProjectList(UserSession.getCurrent().getOrganisation(), flag);
        return aboutProjects;

    }

    public void emptyGrid() {
        if (getAboutProjects() != null) {
            getAboutProjects().clear();
        }
    }

    public AboutProject findAboutProjectDetail(final long rowId) {
        final ListIterator<AboutProject> listIterator = getAboutProjects().listIterator();
        while (listIterator.hasNext()) {
            final AboutProject aboutProject = listIterator.next();
            if (aboutProject.getId() == rowId) {
                return aboutProject;
            }
        }

        return null;
    }

    private AboutProject prepareDTOObject(final AboutProject aboutProject) {
        final AboutProject aboutProjectDTO = new AboutProject();
        aboutProjectDTO.setId(aboutProject.getId());
        aboutProjectDTO.setDescTitleEng(aboutProject.getDescTitleEng());
        aboutProjectDTO.setDescTitleReg(aboutProject.getDescTitleReg());
        aboutProjectDTO.setDescInfoEng(aboutProject.getDescInfoEng());
        aboutProjectDTO.setDescInfoReg(aboutProject.getDescInfoReg());
        return aboutProjectDTO;
    }

    public Organisation getOrgid() {
        return orgid;
    }

    public void setOrgid(final Organisation orgid) {
        this.orgid = orgid;
    }

    private IAboutProjectService getiAboutProjectService() {
        return iAboutProjectService;
    }

    public void setiAboutProjectService(
            final IAboutProjectService iAboutProjectService) {
        this.iAboutProjectService = iAboutProjectService;
    }

}
