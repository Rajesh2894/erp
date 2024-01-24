/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.FarmerMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class FarmerMasterDaoImpl extends AbstractDAO<FarmerMasterEntity> implements FarmerMasterDao {

	private static final Logger LOGGER = Logger.getLogger(FarmerMasterDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.dao.FarmerMasterDao#getFarmerDetailsByIds(java.lang.Long,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FarmerMasterEntity> getFarmerDetailsByIds(Long frmId, String frmFPORegNo) {

		List<FarmerMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from FarmerMasterEntity b  where ");
			if (frmId != null && frmId != 0)
				hql.append(" b.frmId=:frmId ");

			if (StringUtils.isNotEmpty(frmFPORegNo)) {
				if (frmId != null && frmId != 0) {
					hql.append(" and ");
				}
				hql.append(" b.frmFPORegNo=:frmFPORegNo");
			}
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());

			if (frmId != null && frmId != 0)
				query.setParameter("frmId", frmId);

			if (StringUtils.isNotEmpty(frmFPORegNo))
				query.setParameter("frmFPORegNo", frmFPORegNo);
			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetchinfg farmer  details by  id's in getFarmerDetailsByIds()" + ex);
			return entityList;
		}
		return entityList;
	}

}
