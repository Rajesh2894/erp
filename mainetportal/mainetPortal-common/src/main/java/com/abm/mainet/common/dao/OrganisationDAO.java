package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.OrganisationEntity;
import com.abm.mainet.common.domain.ViewOrgDetails;
import com.abm.mainet.common.util.UserSession;

@Repository
public class OrganisationDAO extends AbstractDAO<Organisation> implements IOrganisationDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IOrganisationDAO#getOrgid()
     */
    @Override
    public Long getOrgid() {
        return 0l;
    }

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

        @SuppressWarnings("unchecked")
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

        if ((defaultStatus != null) && !defaultStatus.equalsIgnoreCase(MainetConstants.BLANK)) {
            queyBuilder.append(" and org.defaultStatus= ?1");
        }

        final Query query = createQuery(queyBuilder.toString());

        query.setParameter(1, orgStatus);
        query.setParameter(2, districtCpdId);
        if ((defaultStatus != null) && !defaultStatus.equalsIgnoreCase(MainetConstants.BLANK)) {
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Organisation> findAllActiveOrganizationByOrgId(final String orgStatus) {

        final Query query = createQuery(
                "SELECT org FROM Organisation org WHERE org.orgStatus=?1 and coalesce(defaultStatus,'N') is not 'Y' order by org.ulbOrgID asc");
        query.setParameter(1, orgStatus);
        final List<Organisation> organisationList = query.getResultList();
        return organisationList;
    }

    @Override
    public List<Organisation> findAllActiveOrganizationForHomePage(final String orgStatus, int langId) {

        StringBuilder jpaQuery = new StringBuilder(
                "SELECT org FROM Organisation org WHERE org.orgStatus=?1 and coalesce(defaultStatus,'N') is not 'Y' ");
        if (langId == MainetConstants.ENGLISH) {
            jpaQuery.append(" order by ONlsOrgname asc");
        }
        if (langId == MainetConstants.MARATHI) {
            jpaQuery.append(" order by ONlsOrgnameMar asc");
        }
        final Query query = createQuery(jpaQuery.toString());
        query.setParameter(1, orgStatus);

        @SuppressWarnings("unchecked")
        final List<Organisation> organisationList = query.getResultList();
        return organisationList;
    }

    @Override
    public Long findCountOfOrg() {
        final Query query = createQuery("SELECT count(org.orgid) FROM Organisation org WHERE org.orgStatus=?1");
        query.setParameter(1, MainetConstants.STATUS.ACTIVE);
        return (Long) query.getSingleResult();
    }

    @Override
    public OrganisationEntity create(OrganisationEntity org) {
        this.entityManager.merge(org);
        return org;
    }

	@Override
	public Organisation getActiveOrgByUlbShortCode(String ulbShortCode, String orgStatus) {
		final Query query=createQuery("Select org from Organisation org where org.orgShortNm=:orgShortNm");
		if(ulbShortCode!=null && ulbShortCode!="") {
			query.setParameter("orgShortNm", ulbShortCode);
		}
		return (Organisation) query.getSingleResult();
	}
	
	@Override
	public Organisation getOrgByOrgShortCode(String ulbShortCode) {
		final Query query=createQuery("Select org from Organisation org where org.orgShortNm=:orgShortNm");
		if(ulbShortCode!=null && ulbShortCode!="") {
			query.setParameter("orgShortNm", ulbShortCode);
		}
		return (Organisation) query.getSingleResult();
	}

}
