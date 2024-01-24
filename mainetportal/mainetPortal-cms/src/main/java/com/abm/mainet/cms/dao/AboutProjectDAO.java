package com.abm.mainet.cms.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.AboutProject;
import com.abm.mainet.cms.domain.AboutProjectHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
//import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Manish.Gawali
 */
@Repository
public class AboutProjectDAO extends AbstractDAO<AboutProject> implements IAboutProjectDAO {

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.rti.dao.IAboutProjectDAO#getAllAboutProjects(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public List<AboutProject> getAllAboutProjects(final Organisation organisation, final String deleteStatus, String flag) {
        flag = "null";
        final TypedQuery<AboutProject> query = createTypedQuery(
                "Select a from AboutProject a where a.orgId = ?1 and a.isDeleted = ?2 and a.chekkerflag in ?3");
        query.setParameter(1, organisation);
        query.setParameter(2, deleteStatus);
        query.setParameter(3, flag);
        final List<AboutProject> aboutProjects = query.getResultList();
        return aboutProjects;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.rti.dao.IAboutProjectDAO#saveOrUpdateAboutProject(com.abm.mainet.eip.domain.core.AboutProject)
     */
    @Override
    public boolean saveOrUpdateAboutProject(final AboutProject aboutProject) {
        //try {

            AboutProjectHistory projectHistory = new AboutProjectHistory();

            if (aboutProject.getId() == 0L) {

                projectHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());

            } else {
                if (aboutProject.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    projectHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    projectHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }

            }

            entityManager.merge(aboutProject);
            auditService.createHistory(aboutProject, projectHistory);

        /*} catch (

        final Exception e) {
            throw new FrameworkException(e);
        }*/
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.rti.dao.IAboutProjectDAO#getAboutProject(long, com.abm.mainet.domain.core.Organisation,
     * java.lang.String)
     */
    @Override
    public AboutProject getAboutProject(final long infoId,
            final Organisation organisation, final String deleteStatus) {
        final TypedQuery<AboutProject> query = createTypedQuery(
                "Select a from AboutProject a where a.id= ?1 and a.orgId = ?2 and a.isDeleted = ?3");
        query.setParameter(1, infoId);
        query.setParameter(2, organisation);
        query.setParameter(3, deleteStatus);
        final List<AboutProject> aboutProjects = query.getResultList();
        if ((aboutProjects == null) || aboutProjects.isEmpty()) {
            return null;
        } else {
            return aboutProjects.get(0);
        }
    }

    // ----Method for checking record in grid
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.rti.dao.IAboutProjectDAO#isAccess(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public boolean isAccess(final Organisation organisation, final String notDelete) {

        final Query query = createQuery("select count(a.id) from AboutProject a where a.orgId = ?1 and a.isDeleted = ?2");
        query.setParameter(1, organisation);
        query.setParameter(2, notDelete);
        final int totalResult = ((Number) (query.getSingleResult())).intValue();
        if (totalResult == 0) {
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.rti.dao.IAboutProjectDAO#getAboutProject(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public AboutProject getAboutProject(final Organisation organisation, final String isDeleted) {
        final TypedQuery<AboutProject> query = createTypedQuery(
                "Select a from AboutProject a where  a.orgId = ?1 and a.isDeleted = ?2 and a.chekkerflag='Y'");
        query.setParameter(1, organisation);
        query.setParameter(2, isDeleted);
        final List<AboutProject> aboutProjects = query.getResultList();
        if ((aboutProjects == null) || aboutProjects.isEmpty()) {
            return null;
        } else {
            return aboutProjects.get(0);
        }
    }

}
