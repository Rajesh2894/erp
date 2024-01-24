package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewOrgDetails;
import com.abm.mainet.common.utility.UserSession;

@Repository
public class OrganisationDAO extends AbstractDAO<Organisation> implements IOrganisationDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IOrganisationDAO#getOrganisationById(long, java.lang.String)
     */
    @Override
    public Organisation getOrganisationById(final long orgid, final String orgStatus) {

        final Query query = createQuery("select org from  Organisation org where "
                + " org.orgid=?1 and org.orgStatus=?2");

        query.setParameter(1, orgid);
        query.setParameter(2, orgStatus);

        final List<Organisation> organisationList = query.getResultList();
        if ((organisationList == null) || organisationList.isEmpty()) {
            return null;
        } else {
            return organisationList.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IOrganisationDAO#getOrganisations(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Organisation> getOrganisations(final String defaultStatus, final String orgStatus) {

        final Query query = createQuery("select org from  Organisation org where"
                + " org.defaultStatus= ?1 and org.orgStatus= ?2");

        query.setParameter(1, defaultStatus);
        query.setParameter(2, orgStatus);

        final List<Organisation> organisationList = query.getResultList();
        return organisationList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IOrganisationDAO#getOrganisations(java.lang.String, java.lang.String, long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Organisation> getOrganisations(final String defaultStatus, final String orgStatus, final long districtCpdId) {

        final StringBuilder queyBuilder = new StringBuilder();

        queyBuilder.append("select org from  Organisation org where"
                + " org.orgStatus= ?1 and org.orgCpdIdDis= ?2");

        if ((defaultStatus != null) && !defaultStatus.equalsIgnoreCase("")) {
            queyBuilder.append(" and org.defaultStatus= ?3");
        }

        final Query query = createQuery(queyBuilder.toString());

        query.setParameter(1, orgStatus);
        query.setParameter(2, districtCpdId);
        if ((defaultStatus != null) && !defaultStatus.equalsIgnoreCase("")) {
            query.setParameter(3, defaultStatus);
        }

        final List<Organisation> organisationList = query.getResultList();
        return organisationList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ViewOrgDetails> getOrganisationsNew(final long districtCpdId) {

        final StringBuilder queyBuilder = new StringBuilder(
                "select vod from  ViewOrgDetails vod where vod.deptId= ?1 order by  ");

        if (UserSession.getCurrent().getLanguageId() == 1) {
            queyBuilder.append("UPPER(vod.oNlsOrgname) ASC");
        } else {
            queyBuilder.append(" UPPER(vod.oNlsOrgnameMar) ASC");
        }

        final Query query = createQuery(queyBuilder.toString());
        query.setParameter(1, districtCpdId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Organisation> findAllActiveOrganization(final String orgStatus) {

        final Query query = createQuery("SELECT org FROM Organisation org WHERE org.orgStatus=?1 order by org.ONlsOrgname asc");
        query.setParameter(1, orgStatus);
        final List<Organisation> organisationList = query.getResultList();
        return organisationList;
    }
    
	@Override
	public Organisation getOrganisationByShortName(String orgShortName) {
				
		final Query query=createQuery("Select org from Organisation org where org.orgShortNm=:orgShortNm");
		if(orgShortName!=null && orgShortName!="") {
			query.setParameter("orgShortNm", orgShortName);
		}
		return (Organisation) query.getSingleResult();	
	}
	
	
	@Override
	public Organisation getActiveOrgByUlbShortCode(String ulbShortCode, String orgStatus) {
		final Query query=createQuery("Select org from Organisation org where org.orgShortNm=:orgShortNm");
		if(ulbShortCode!=null && ulbShortCode!="") {
			query.setParameter("orgShortNm", ulbShortCode);
		}
		return (Organisation) query.getSingleResult();	
	}
	
	 @SuppressWarnings("unchecked")
	    @Override
	    public List<Object[]> getOrganizationActiveWithWorkflow(Long deptId) {
	    	List<Object[]> entityList = null;
	        final Query query = createQuery("SELECT distinct org.orgid,org.ONlsOrgname,org.oNlsOrgnameMar FROM Organisation org , WorkflowMas wm  WHERE org.orgStatus='A' and wm.status='Y' and  wm.organisation.orgid=org.orgid and  wm.department.dpDeptid=:deptId");
	        if (deptId != null && deptId > 0) {
	            query.setParameter("deptId", deptId);
	        }
	        entityList = (List<Object[]>) query.getResultList();
	        return entityList;
	    }

	@Override
	public Organisation getOrganisationByOrgName(String orgName) {
		final Query query=createQuery("Select org from Organisation org where org.ONlsOrgname=:ONlsOrgname");
		if(orgName!=null && orgName!="") {
			query.setParameter("ONlsOrgname", orgName);
		}
		return (Organisation) query.getSingleResult();	
	}
}
