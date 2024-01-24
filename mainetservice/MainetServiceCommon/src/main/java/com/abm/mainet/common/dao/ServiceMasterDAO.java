/**
 *
 */
package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rajendra.Bhujbal
 *
 */
@Repository
public class ServiceMasterDAO extends AbstractDAO<ServiceMaster> implements IServiceMasterDAO {

	@Override
	public String getServiceNameByServiceId(final long smServiceId) {

		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			final Query query = entityManager
					.createQuery("SELECT am.smServiceName FROM ServiceMaster am WHERE am.smServiceId=?1");
			query.setParameter(1, smServiceId);
			return (String) query.getSingleResult();
		} else {
			final Query query = entityManager
					.createQuery("SELECT am.smServiceNameMar FROM ServiceMaster am WHERE am.smServiceId=?1");
			query.setParameter(1, smServiceId);
			return (String) query.getSingleResult();
		}

	}

	@Override
	public String getServiceShortDescByServiceId(final long smServiceId) {
		final Query query = entityManager
				.createQuery("SELECT am.smShortdesc FROM ServiceMaster am WHERE am.smServiceId=?1");
		query.setParameter(1, smServiceId);
		return (String) query.getSingleResult();
	}

	@Override
	public ServiceMaster getServiceMasterByServiceId(final long serviceId, final Long orgId) {
		ServiceMaster serviceMaster = null;
		final Query query = entityManager
				.createQuery("SELECT sm FROM ServiceMaster sm WHERE sm.orgid=:orgId AND sm.smServiceId=:serviceId ");
		query.setParameter("orgId", orgId);
		query.setParameter("serviceId", serviceId);
		serviceMaster = (ServiceMaster) query.getSingleResult();
		return serviceMaster;
	}

	@Override
	public ServiceMaster getServiceMasterByShortCode(final String shortCode, final Long orgId) {
		ServiceMaster serviceMaster = null;
		final Query query = entityManager
				.createQuery("SELECT sm FROM ServiceMaster sm WHERE sm.orgid=:orgId AND sm.smShortdesc=:shortCode");
		query.setParameter("orgId", orgId);
		query.setParameter("shortCode", shortCode);
		serviceMaster = (ServiceMaster) query.getSingleResult();
		return serviceMaster;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMaster> getServicesByShortCodes(List<String> serviceShortCodeList, Long orgId, Long deptId) {

		List<ServiceMaster> serviceEntityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT sm  FROM ServiceMaster sm where sm.orgid =:orgId and sm.tbDepartment.dpDeptid =:dpDeptid and sm.smShortdesc in:smShortdesc");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter("dpDeptid", deptId);
			query.setParameter("smShortdesc", serviceShortCodeList);
			serviceEntityList = (List<ServiceMaster>) query.getResultList();
		} catch (Exception exception) {
			throw new FrameworkException(exception.getMessage());
		}

		return serviceEntityList;
	}
	
	@Override
	public String getServiceNameByServiceIdLangId(final long smServiceId, int langId) {

		if (langId == MainetConstants.ENGLISH) {
			final Query query = entityManager
					.createQuery("SELECT am.smServiceName FROM ServiceMaster am WHERE am.smServiceId=?1");
			query.setParameter(1, smServiceId);
			return (String) query.getSingleResult();
		} else {
			final Query query = entityManager
					.createQuery("SELECT am.smServiceNameMar FROM ServiceMaster am WHERE am.smServiceId=?1");
			query.setParameter(1, smServiceId);
			return (String) query.getSingleResult();
		}

	}

}
