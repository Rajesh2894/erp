package com.abm.mainet.common.master.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TaxDefinationEntity;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Repository
public class TaxDefinationDaoImpl extends AbstractDAO<Long> implements TaxDefinationDao {

	private static final Logger LOGGER = Logger.getLogger(TaxDefinationDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TaxDefinationEntity> findByAllGridSearchData(Long taxId, String panApp, Long orgId) {
		List<TaxDefinationEntity> entity = null;
		try {
			StringBuilder hql = new StringBuilder("SELECT pm FROM TaxDefinationEntity pm  where pm.orgId = :orgId");

			if (taxId != null) {
				hql.append(" and pm.taxId= :taxId");
			}

			if (panApp != null && !panApp.isEmpty()) {
				hql.append(" and pm.taxPanApp= :panApp");
			}

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

			if (taxId != null) {
				query.setParameter(MainetConstants.TAX_ID, taxId);
			}

			if (panApp != null && !panApp.isEmpty()) {
				query.setParameter(MainetConstants.PAN_APPLICABLE, panApp);
			}

			entity = (List<TaxDefinationEntity>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  findByAllGridSearchData() ", exception);

		}
		return entity;
	}

}
