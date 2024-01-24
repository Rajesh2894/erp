package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public interface IAboutProjectService {

    /**
     * @param aboutProject
     * @return
     */
    public List<AboutProject> getAboutProjectList(Organisation organisation, String flag);

    /**
     * To save {@link AboutProject} object objects.
     * @param aboutProject the {@link AboutProject} object to be saved
     * @return {@link Boolean} <code>true</code> if saved successfully otherwise <code>false</code>
     */
    public boolean saveAboutProjectDetails(AboutProject aboutProject, Organisation organisation, Employee employee,
            Integer langId);

    /**
     * @param aboutProject
     * @return
     */
    public boolean deleteAboutProject(AboutProject aboutProject);

    /**
     * To find {@link AboutProject} object objects.
     * @param aboutProject the {@link AboutProject} object to be find
     * @return
     */
    public AboutProject findAboutProjectDetail(long infoId, Organisation organisation);

    public boolean isAccessToAdd(Organisation organisation);

    public AboutProject getAboutProject(Organisation organisation, String isDeleted);

}
