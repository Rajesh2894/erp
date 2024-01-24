package com.abm.mainet.water.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.TbWtBillScheduleEntity;

@Repository
public class WaterBillScheduleDAOImpl extends AbstractDAO<TbWtBillScheduleEntity>    implements WaterBillScheduleDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbWtBillScheduleEntity> searchBillingData(String string) {
		  
        final Query query = entityManager.createQuery(string);
        return query.getResultList();
	}

	@Override
	public int validateBillingData(String string) {
		   
        final Query query = entityManager.createQuery(string);

        return query.getResultList().size();
	}

	
}
