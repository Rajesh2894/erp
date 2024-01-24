package com.abm.mainet.additionalservices.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.domain.CFCCollectionMasterEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterScheduleEntity;
import com.abm.mainet.common.dao.AbstractDAO;

@Component
public class CFCSchedulingDaoImpl extends AbstractDAO<CFCCollectionMasterEntity> implements CFCSchedulingDao {

	@Override
	@Transactional
	public List<CFCCounterScheduleEntity> searchCollectionData(String collectionNo, String counterNo, Long userId, String status,
			Long orgId) {

		List<CFCCounterScheduleEntity> cfcCollectionMasterEntity = new ArrayList<CFCCounterScheduleEntity>();

		try {

			StringBuilder hql = new StringBuilder("FROM CFCCounterScheduleEntity cs WHERE cs.orgId =:orgId");

			if (collectionNo != null && !collectionNo.isEmpty()) {
				hql.append(" and cs.cfcCounterMasterEntity.cfcCollectionMasterEntity.cmCollncentreno ='"+collectionNo+"'");
			}

			if (counterNo != null && !counterNo.isEmpty()) {
				hql.append(" and cs.cfcCounterMasterEntity.cuCountcentreno ='"+counterNo+"'");
			}

			if (userId != null && userId != 0) {
				hql.append(" and cs.csUserId ="+userId);
			}

			if (status != null && !status.equals("")) {
				hql.append(" and cs.csStatus ='"+status+"'");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			/*
			 * if (collectionNo != null && collectionNo != 0) {
			 * query.setParameter("collectionNo", collectionNo); }
			 * 
			 * if (counterNo != null && counterNo != 0) { query.setParameter("counterNo",
			 * counterNo); }
			 * 
			 * if (userId != null && userId != 0) { query.setParameter("userId", userId); }
			 * 
			 * if (status != null && !status.isEmpty() && status.equals("")) {
			 * query.setParameter("status", status); }
			 */

			cfcCollectionMasterEntity = (List<CFCCounterScheduleEntity>) query.getResultList();

		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}

		return cfcCollectionMasterEntity;
	}
	
	@Override
	@Transactional
	public CFCCounterScheduleEntity searchCounterScheduleBuId(Long orgId,Long counterScheduleId) {
		CFCCounterScheduleEntity cfcCounterScheduleEntity=new CFCCounterScheduleEntity();
		try {

			StringBuilder hql = new StringBuilder("FROM CFCCounterScheduleEntity cs WHERE cs.orgId =:orgId");
			
			hql.append(" and cs.csScheduleid ="+counterScheduleId);
			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);
			
			cfcCounterScheduleEntity = (CFCCounterScheduleEntity)query.getSingleResult();
		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}
		return cfcCounterScheduleEntity;
	}
}
