/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.BlockAllocationEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class AllocationOfBlocksDaoImpl extends AbstractDAO<BlockAllocationEntity> implements AllocationOfBlocksDao {
	private static final Logger LOGGER = Logger.getLogger(AllocationOfBlocksDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<BlockAllocationEntity> getBlockDetailsByIds(Long orgTypeId, Long organizationNameId,
			Long allocationYearId,Long sdb1,Long sdb2,Long sdb3) {

		List<BlockAllocationEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from BlockAllocationEntity b , BlockAllocationDetailEntity d where b.blockId=d.blockMasterEntity.blockId ");

			if (orgTypeId != null && orgTypeId != 0)
				hql.append(" and  b.orgTypeId=:orgTypeId");

			if (organizationNameId != null && organizationNameId != 0) {
				if (orgTypeId != null && orgTypeId != 0)
					hql.append(" and ");
				hql.append(" b.organizationNameId=:organizationNameId");
			}
			if (allocationYearId != null && allocationYearId != 0) {
				if (organizationNameId != null && organizationNameId != 0)
					hql.append(" and ");
				hql.append(" b.allocationYearId=:allocationYearId");
			}
			
			if (sdb1 != null && sdb1 != 0) {
				if ((allocationYearId != null && allocationYearId != 0) || (organizationNameId != null && organizationNameId != 0) ||
						(orgTypeId != null && orgTypeId != 0))
					hql.append(" and ");
				hql.append(" d.sdb1=:sdb1");
			}
			
			if (sdb2 != null && sdb2 != 0) {
				if ((sdb1 != null && sdb1 != 0) || (allocationYearId != null && allocationYearId != 0) || (organizationNameId != null && organizationNameId != 0) ||
						(orgTypeId != null && orgTypeId != 0))
					hql.append(" and ");
				hql.append(" d.sdb2=:sdb2");
			}
			
			if (sdb3 != null && sdb3 != 0) {
				if ((sdb2 != null && sdb2 != 0) || (sdb1 != null && sdb1 != 0) || (allocationYearId != null && allocationYearId != 0) || (organizationNameId != null && organizationNameId != 0) ||
						(orgTypeId != null && orgTypeId != 0))
					hql.append(" and ");
				hql.append(" d.sdb3=:sdb3");
			}
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (orgTypeId != null && orgTypeId != 0)
				query.setParameter("orgTypeId", orgTypeId);
			if (organizationNameId != null && organizationNameId != 0)
				query.setParameter("organizationNameId", organizationNameId);
			if (allocationYearId != null && allocationYearId != 0)
				query.setParameter("allocationYearId", allocationYearId);
			if (sdb1 != null && sdb1 != 0)
				query.setParameter("sdb1", sdb1);
			if (sdb2 != null && sdb2 != 0)
				query.setParameter("sdb2", sdb2);
			if (sdb3 != null && sdb3 != 0)
				query.setParameter("sdb3", sdb3);
			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetching block details by  id's" + ex);
			return entityList;
		}
		return entityList;

	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.dao.AllocationOfBlocksDao#findBlockDetails(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlockAllocationEntity> findBlockDetails(Long orgId, Long masId) {

		List<BlockAllocationEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from BlockAllocationEntity b  where ");

			if (orgId != null && orgId != 0)
				hql.append("  b.orgId =:orgId");

			if (masId != null && masId != 0) {
				if (orgId != null && orgId != 0) {
					hql.append(" and ");
				}
			hql.append("  b.organizationNameId =:masId");
			}

			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (orgId != null && orgId != 0)
				query.setParameter("orgId", orgId);

			if (masId != null && masId != 0)
				query.setParameter("masId", masId);

			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetching block details by  id's in findBlockDetails" + ex);
			return entityList;
		}
		return entityList;

	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.dao.AllocationOfBlocksDao#findBlockDetailsForCbbo(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlockAllocationEntity> findBlockDetailsForCbbo(Long orgId, Long masId) {


		List<BlockAllocationEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from BlockAllocationEntity b, BlockAllocationDetailEntity d  where b.blockId=d.blockMasterEntity.blockId");

			if (orgId != null && orgId != 0)
				hql.append("  and b.orgTypeId =:orgId");

			if (masId != null && masId != 0) 
			hql.append(" and  d.cbboId =:masId");
			
			hql.append(" group by b.blockId");
		
			
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (orgId != null && orgId != 0)
				query.setParameter("orgId", orgId);

			if (masId != null && masId != 0)
				query.setParameter("masId", masId);

			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetching block details by mastId and orgid in findBlockDetailsForCbbo" + ex);
			return entityList;
		}
		return entityList;

	}
}
