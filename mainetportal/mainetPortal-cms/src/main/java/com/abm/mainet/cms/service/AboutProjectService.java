package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IAboutProjectDAO;
import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.Utility;

/**
 * @author Manish.Gawali
 */
@Service
@Transactional(readOnly = true)
public class AboutProjectService implements IAboutProjectService {
    @Autowired
    private IAboutProjectDAO aboutProjectDAO;

    @Override
    public List<AboutProject> getAboutProjectList(final Organisation organisation, String flag) {

        return aboutProjectDAO.getAllAboutProjects(organisation, MainetConstants.IsDeleted.NOT_DELETE, flag);

    }

    @Override
    @Transactional
    public boolean saveAboutProjectDetails(final AboutProject aboutProject, final Organisation organisation,
            final Employee employee, final Integer langId) {

        if (aboutProject.getId() == 0L) {
            setCreateAboutProjectDetails(organisation, employee, langId, aboutProject);
        } else {
            setUpdateAboutProjectDetails(aboutProject);
        }

        aboutProject.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        return aboutProjectDAO.saveOrUpdateAboutProject(aboutProject);
    }

    private AboutProject setUpdateAboutProjectDetails(final AboutProject aboutProject) {
        aboutProject.setUpdatedDate(new Date());
        aboutProject.setLgIpMacUpd(Utility.getMacAddress());
        return aboutProject;
    }

    private AboutProject setCreateAboutProjectDetails(final Organisation organisation, final Employee employee,
            final Integer langId, final AboutProject aboutProject) {
        aboutProject.setLmodDate(new Date());
        aboutProject.setLgIpMac(Utility.getMacAddress());
        aboutProject.setOrgId(organisation);
        aboutProject.setUserId(employee);
        aboutProject.setLangId(langId);

        return aboutProject;
    }

    @Override
    public AboutProject findAboutProjectDetail(final long infoId, final Organisation organisation) {
        return aboutProjectDAO.getAboutProject(infoId, organisation, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    @Transactional
    public boolean deleteAboutProject(final AboutProject aboutProject) {
        aboutProject.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        return (aboutProjectDAO.saveOrUpdateAboutProject(aboutProject));

    }

    @Override
    public boolean isAccessToAdd(final Organisation organisation) {
        return aboutProjectDAO.isAccess(organisation, MainetConstants.IsDeleted.NOT_DELETE);
    }

    @Override
    public AboutProject getAboutProject(final Organisation organisation, final String isDeleted) {
        return aboutProjectDAO.getAboutProject(organisation, isDeleted);

    }

}
