package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.common.domain.Organisation;

public interface IAboutProjectDAO {

    public abstract List<AboutProject> getAllAboutProjects(Organisation organisation,
            String deleteStatus, String flag);

    public abstract boolean saveOrUpdateAboutProject(AboutProject aboutProject);

    public abstract AboutProject getAboutProject(long infoId, Organisation organisation,
            String deleteStatus);

    // ----Method for checking record in grid
    public abstract boolean isAccess(Organisation organisation, String notDelete);

    public abstract AboutProject getAboutProject(Organisation organisation, String isDeleted);

}