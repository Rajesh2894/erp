package com.abm.mainet.workManagement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.domain.TenderWorkEntity;

@Repository
public class BIDMasterDaoImpl extends AbstractDAO<TenderWorkEntity> implements BIDMasterDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(BIDMasterDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TenderWorkEntity> searchTenderByTendernoAndDate(Long orgId, String tenderNo, Date tenderDate) {
		// TODO Auto-generated method stub

		List<TenderWorkEntity> tenderWorkEntities = new ArrayList<TenderWorkEntity>();

		try {
			StringBuilder hql = new StringBuilder("from TenderWorkEntity tm where tm.orgId =:orgId");

			if (tenderNo != null && !tenderNo.equals("0")) {
				hql.append(" and tm.tenderMasEntity.tenderNo =:tenderNo");
			}

			if (tenderDate != null) {
				hql.append(" and tm.tndSubmitDate=:tenderDate");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (tenderNo != null && !tenderNo.equals("0")) {
				query.setParameter("tenderNo", tenderNo);
			}

			if (tenderDate != null) {
				query.setParameter("tenderDate", tenderDate);
			}

			tenderWorkEntities = (List<TenderWorkEntity>) query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Exception occur while callling method searchTenderByTendernoAndDate() ", e);
			throw new FrameworkException("Exception occur while callling method searchTenderByTendernoAndDate() ", e);
		}
		return tenderWorkEntities;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BIDMasterEntity searchBIdDetByVendorIdandBidId(Long orgId, String bidIdDesc, Long vendorId,Long tndId) {
		
		BIDMasterEntity bidMasterEntity=new BIDMasterEntity();
	
		try {
			StringBuilder hql = new StringBuilder("from BIDMasterEntity bm where bm.orgId =:orgId");

			if (bidIdDesc != null && !bidIdDesc.isEmpty()) {
				hql.append(" and bm.bidIdDesc =:bidIdDesc");
			}

			if (vendorId != null) {
				hql.append(" and bm.vendorId=:vendorId");
			}
			
			if(tndId != null) {
				hql.append(" and bm.tenderMasterEntity.tndId=:tndId");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (bidIdDesc != null && !bidIdDesc.isEmpty()) {
				query.setParameter("bidIdDesc", bidIdDesc);
			}

			if (vendorId != null) {
				query.setParameter("vendorId", vendorId);
			}
			
			if (tndId != null) {
				query.setParameter("tndId", tndId);
			}

			bidMasterEntity = (BIDMasterEntity) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("Exception occur while callling method searchBIdDetByVendorIdandBidId() ", e);
			throw new FrameworkException("Exception occur while callling method searchBIdDetByVendorIdandBidId() ", e);
		}
		
		return bidMasterEntity;
	}

}
