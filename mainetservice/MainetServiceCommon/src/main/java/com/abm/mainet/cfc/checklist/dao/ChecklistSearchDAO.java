package com.abm.mainet.cfc.checklist.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.Utility;

@Repository
public class ChecklistSearchDAO extends AbstractDAO<ChecklistStatusView> implements IChecklistSearchDAO {

	@Autowired
	private IOrganisationDAO organisationDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<ChecklistStatusView> queryChecklist(final long orgId, final Long applicationId, final Long serviceId,
			final String name, final Date fromDate, final Date toDate, final String applicationStatus) {
		final StringBuilder Hql = new StringBuilder(
				"SELECT v FROM ChecklistStatusView v where v.organisationId = :organisationId ");

		if (null != applicationId) {
			Hql.append("and v.apmApplicationId = :apmApplicationId ");
		}
		if ((null != serviceId) && (serviceId > 0)) {

			Hql.append("and v.smServiceId = :smServiceId ");
		}
		if (null != fromDate) {
			Hql.append("and v.apmApplicationDate >= :apmApplicationDate ");
		}
		if (null != toDate) {
			Hql.append("and v.apmApplicationDate <= :apmApplicationDate ");
		}
		if (!StringUtils.isEmpty(name)) {
			Hql.append("and v.applicantsName like %:applicantsName% ");
		}
		if (!StringUtils.isBlank(applicationStatus)) {
			Hql.append("and v.apmChklstVrfyFlag = :apmChklstVrfyFlag ");
		}
		Hql.append(" ORDER BY v.apmChklstVrfyFlag DESC ");

		final Query query = createQuery(Hql.toString());

		query.setParameter("organisationId", orgId);

		if (null != applicationId) {
			query.setParameter("apmApplicationId", applicationId);
		}
		if ((null != serviceId) && (serviceId > 0)) {
			query.setParameter("smServiceId", serviceId);
		}
		if (null != fromDate) {
			query.setParameter("apmApplicationDate", fromDate);
		}
		if (null != toDate) {
			query.setParameter("apmApplicationDate", toDate);
		}
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("applicantsName", name);
		}
		if (!StringUtils.isBlank(applicationStatus)) {
			query.setParameter("apmChklstVrfyFlag", applicationStatus);
		}

		return query.getResultList();

	}

	@Override
	public ChecklistStatusView queryChecklistByApplication(final long orgId, final long applicationId) {
		//Defect#122930
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		final StringBuilder Hql = new StringBuilder(
				"FROM ChecklistStatusView v WHERE v.apmApplicationId = :apmApplicationId ");
		Boolean flag = Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_DSCL);
		//Defect#122930
		if (!flag) {
			Hql.append("AND v.organisationId = :organisationId");
		}

		final Query query = createQuery(Hql.toString());
		//Defect#122930
		if (!flag) {
			query.setParameter("organisationId", orgId);
		}
		query.setParameter("apmApplicationId", applicationId);
		try {
			return (ChecklistStatusView) query.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public boolean updateChecklistFlag(final long applicationId, final long orgId, final String checklistFlag) {
		final Query updateChecklistQuery = createQuery(QueryConstants.QUERY.UPDATE_CHECKLIST_VERIFY);
		updateChecklistQuery.setParameter(MainetConstants.TABLE_COLUMN.APPLICATION_ID, applicationId)
				.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId)
				.setParameter(MainetConstants.TABLE_COLUMN.CHECKLIST_FLAG, checklistFlag)
				.setParameter(MainetConstants.TABLE_COLUMN.APPLICATION_SUCESS_FLAG, null);
		updateHqlAuditDetailsPrimitive(updateChecklistQuery);
		final int rowCount = updateChecklistQuery.executeUpdate();
		return rowCount == 1 ? true : false;
	}

}
