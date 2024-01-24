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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class FPOMasterDaoImpl extends AbstractDAO<FPOMasterEntity> implements FPOMasterDao {

	private static final Logger LOGGER = Logger.getLogger(FPOMasterDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.dao.FPOMasterDao#getfpoByIdAndRegNo(java.lang.Long,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FPOMasterEntity> getfpoByIdAndRegNo(Long fpoId, String fpoRegNo,Long iaId,Long masId,String orgShortNm,String uniqueId) {
		List<FPOMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from FPOMasterEntity b , CBBOMasterEntity c , IAMasterEntity i  where b.cbboId=c.cbboId and  c.iaId=i.IAId");

			if (StringUtils.isNotEmpty(uniqueId) && orgShortNm.equals(MainetConstants.Sfac.CBBO)) {
				hql.append(" and c.cbboUniqueId=:uniqueId ");
			}

			else if (masId != null && orgShortNm.equals(MainetConstants.Sfac.IA)) {
				hql.append(" and i.IAId=:masId ");
			}

			else if (masId != null && orgShortNm.equals(MainetConstants.Sfac.FPO)) {
				hql.append(" and b.fpoId=:masId ");
			}
			
			if (fpoId != null && fpoId != 0) {
				if (masId != null){
					hql.append(" and ");
				}
				hql.append(" b.fpoId=:fpoId ");
			}

			if (StringUtils.isNotEmpty(fpoRegNo)) {
				if ((fpoId != null && fpoId != 0) || masId != null) {
					hql.append(" and ");
				}
				hql.append(" b.fpoRegNo=:fpoRegNo");
			}
			
			if (iaId != null && iaId != 0) {
				if (StringUtils.isNotEmpty(fpoRegNo) || (fpoId != null && fpoId != 0) || masId != null){
					hql.append(" and ");
				}
				hql.append(" b.iaId=:iaId");
			}
			
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());

			if (fpoId != null && fpoId != 0)
				query.setParameter("fpoId", fpoId);

			if (StringUtils.isNotEmpty(fpoRegNo)) 
				query.setParameter("fpoRegNo", fpoRegNo);

			if (iaId != null && iaId != 0)
				query.setParameter("iaId", iaId);
			
			if (StringUtils.isNotEmpty(uniqueId) && orgShortNm.equals(MainetConstants.Sfac.CBBO)) {
				if (StringUtils.isNotEmpty(uniqueId)) {
					query.setParameter("uniqueId", uniqueId);
				}
			} else{
				if (masId != null && masId != 0)
					query.setParameter("masId", masId);

			}
			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetchinfg Fpo  details by  id's in getfpoByIdAndRegNo()" + ex);
			return entityList;
		}
		return entityList;

	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.dao.FPOMasterDao#findFpoByMasId(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FPOMasterEntity> findFpoByMasId(Long masId, String orgShortNm,String uniqueId) {
		List<FPOMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder(
					"Select f from FPOMasterEntity f , CBBOMasterEntity c , IAMasterEntity i  where f.cbboId=c.cbboId and  c.iaId=i.IAId");

			if (StringUtils.isNotEmpty(uniqueId) && orgShortNm.equals(MainetConstants.Sfac.CBBO)) {
				hql.append(" and c.cbboUniqueId=:uniqueId ");
			}

			else if (masId != null && orgShortNm.equals(MainetConstants.Sfac.IA)) {
				hql.append(" and i.IAId=:masId ");
			}

			else if (masId != null && orgShortNm.equals(MainetConstants.Sfac.FPO)) {
				hql.append(" and f.fpoId=:masId ");
			}

			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (StringUtils.isNotEmpty(uniqueId) && orgShortNm.equals(MainetConstants.Sfac.CBBO)) {
				if (StringUtils.isNotEmpty(uniqueId)) {
					query.setParameter("uniqueId", uniqueId);
				}
			} else{
			if (masId != null && masId != 0)
				query.setParameter("masId", masId);
			}
			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetchinfg Fpo  details by  id's in findFpoByMasId()" + ex);
			return entityList;
		}
		return entityList;

	}

}
