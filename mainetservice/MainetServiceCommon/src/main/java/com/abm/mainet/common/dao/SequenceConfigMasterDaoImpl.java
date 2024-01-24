package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author sadik.shaikh
 *
 */
@Repository
public class SequenceConfigMasterDaoImpl extends AbstractDAO<SequenceConfigMasterEntity>
		implements ISequenceConfigMasterDAO {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SequenceConfigMasterEntity> searchAdvertiserData(Long orgId, Long seqName, Long deptId, Long seqType,
			Long catId, String seqStatus) {

		List<SequenceConfigMasterEntity> configMasterEntities = null;

		try {

			StringBuilder hql = new StringBuilder("FROM SequenceConfigMasterEntity sm WHERE sm.orgId =:orgId");

			if (seqName != null && seqName != 0) {
				hql.append(" and sm.seqName =:seqName");
			}

			if (deptId != null && deptId != 0) {
				hql.append(" and sm.deptId =:deptId");
			}

			if (seqType != null && seqType != 0) {
				hql.append(" and sm.seqType =:seqType");
			}

			if (catId != null && catId != 0) {
				hql.append(" and sm.catId =:catId");
			}

			/* StringUtils.isNotBlank(seqStatus) && seqStatus!="0" */
			if (seqStatus != null) {
				hql.append(" and sm.seqStatus =:seqStatus");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (seqName != null && seqName != 0) {
				query.setParameter("seqName", seqName);
			}

			if (deptId != null && deptId != 0) {
				query.setParameter("deptId", deptId);
			}

			if (seqType != null && seqType != 0) {
				query.setParameter("seqType", seqType);
			}

			if (catId != null && catId != 0) {
				query.setParameter("catId", catId);
			}

			if (seqStatus != null) {
				query.setParameter("seqStatus", seqStatus);
			}

			configMasterEntities = (List<SequenceConfigMasterEntity>) query.getResultList();

		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}

		return configMasterEntities;
	}

	@Override
	@Transactional
	public SequenceConfigMasterEntity searchSequenceById(Long seqConfigId, Long orgId) {

		SequenceConfigMasterEntity configMasterEntity = null;

		try {

			StringBuilder hql = new StringBuilder("FROM SequenceConfigMasterEntity sm WHERE sm.orgId =:orgId");

			if (seqConfigId != null && seqConfigId != 0) {
				hql.append(" and sm.seqConfigId =:seqConfigId");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (seqConfigId != null && seqConfigId != 0) {
				query.setParameter("seqConfigId", seqConfigId);
			}

			configMasterEntity = (SequenceConfigMasterEntity) query.getSingleResult();

		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}

		return configMasterEntity;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public boolean checkSequenceByPattern(Long orgId, Long
	 * seqName, Long catId, Long deptId, Long seqType, String seqStatus) {
	 * List<SequenceConfigMasterEntity> configMasterEntity = null; boolean flag =
	 * false; try {
	 * 
	 * StringBuilder hql = new
	 * StringBuilder("FROM SequenceConfigMasterEntity sm WHERE sm.orgId =:orgId");
	 * 
	 * if (seqName != null && seqName != 0) {
	 * hql.append(" and sm.seqName =:seqName"); }
	 * 
	 * if (catId != null && catId != 0) { hql.append(" and sm.catId =:catId"); }
	 * 
	 * if (deptId != null && deptId != 0) { hql.append(" and sm.deptId =:deptId"); }
	 * 
	 * if (seqType != null && seqType != 0) {
	 * hql.append(" and sm.seqType =:seqType"); }
	 * 
	 * if (seqStatus != null && seqStatus != "I") {
	 * hql.append(" and sm.seqStatus =:seqStatus"); }
	 * 
	 * final Query query = this.createQuery(hql.toString());
	 * 
	 * query.setParameter("orgId", orgId);
	 * 
	 * if (seqName != null && seqName != 0) { query.setParameter("seqName",
	 * seqName); }
	 * 
	 * if (catId != null && catId != 0) { query.setParameter("catId", catId); }
	 * 
	 * if (deptId != null && deptId != 0) { query.setParameter("deptId", deptId); }
	 * 
	 * if (seqType != null && seqType != 0) { query.setParameter("seqType",
	 * seqType); }
	 * 
	 * if (seqStatus != null && seqStatus != "I") { query.setParameter("seqStatus",
	 * seqStatus); }
	 * 
	 * configMasterEntity = (List<SequenceConfigMasterEntity>)
	 * query.getResultList();
	 * 
	 * } catch (NoResultException e) { flag=true; return flag; }
	 * 
	 * catch (Exception e) {
	 * System.out.println("exception occure while getting the data::::=======" + e);
	 * } if (configMasterEntity.isEmpty()) { return true; } return false; }
	 */

	@Override
	@Transactional
	public boolean checkSequenceByPattern(Long orgId, Long deptId, Long seqName) {
		List<SequenceConfigMasterEntity> configMasterEntity = null;
		try {

			StringBuilder hql = new StringBuilder("FROM SequenceConfigMasterEntity sm WHERE sm.orgId =:orgId");

			if (seqName != null && seqName != 0) {
				hql.append(" and sm.seqName =:seqName");
			}

			if (deptId != null && deptId != 0) {
				hql.append(" and sm.deptId =:deptId");
			}

			hql.append(" and sm.seqStatus ='A'");

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (seqName != null && seqName != 0) {
				query.setParameter("seqName", seqName);
			}

			if (deptId != null && deptId != 0) {
				query.setParameter("deptId", deptId);
			}

			configMasterEntity = (List<SequenceConfigMasterEntity>) query.getResultList();

		} /*
			 * catch (NoResultException e) { flag=true; return flag; }
			 */
		catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}
		if (configMasterEntity.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public SequenceConfigMasterEntity loadSequenceData(Long orgId, Long deptId, Long seqName) {
		SequenceConfigMasterEntity configMasterEntity = null;
		try {

			StringBuilder hql = new StringBuilder("FROM SequenceConfigMasterEntity sm WHERE sm.orgId =:orgId");

			if (seqName != null && seqName != 0) {
				hql.append(" and sm.seqName =:seqName");
			}

			if (deptId != null && deptId != 0) {
				hql.append(" and sm.deptId =:deptId");
			}

			hql.append(" and sm.seqStatus ='A'");

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (seqName != null && seqName != 0) {
				query.setParameter("seqName", seqName);
			}

			if (deptId != null && deptId != 0) {
				query.setParameter("deptId", deptId);
			}

			configMasterEntity = (SequenceConfigMasterEntity) query.getSingleResult();

		} catch (Exception e) {
			System.out.println("Exception occure while getting the data from database");
		}

		return configMasterEntity;
	}

}
