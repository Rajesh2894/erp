/**
 * 
 */
package com.abm.mainet.rnl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.rnl.domain.EstateEntity;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public class EstateMasterDaoImpl extends AbstractDAO<EstateEntity> implements EstateMasterDao{
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Object[]> searchData(Long orgId, Long locId, Long esId, Long purpose,Integer type1, Integer type2, Long acqType) {
		
		List<Object[]> entityList = new ArrayList<>();
		
		final StringBuilder builder = new StringBuilder();
		builder.append("select e.esId, e.code, e.nameEng, e.nameReg, e.locationMas.locNameEng, e.locationMas.locNameReg, e.category, e.type1, "
				+ " e.type2, e.purpose,e.acqType from EstateEntity e where e.orgId=:orgId and e.isActive = 'Y' ");
		
		if(locId != null) {
			builder.append(" and e.locationMas.locId=:locId");
		}
		if(esId != null) {
			builder.append(" and e.esId=:esId");
		}
		if(purpose != null && purpose != 0 ) {
			builder.append(" and e.purpose=:purpose");
		}
		if(type1 != null && type1 != 0) {
			builder.append(" and e.type1=:type1");
		}
		if(type2 != null && type2 != 0) {
			builder.append(" and e.type2=:type2");
		}	
		if(acqType != null  && acqType != 0) {
			builder.append(" and e.acqType=:acqType");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(locId != null) {
			query.setParameter("locId", locId);
		}
		if(esId != null) {
			query.setParameter("esId", esId);
		}
		if(purpose != null && purpose != 0 ) {
			query.setParameter("purpose", purpose);
		}
		if(type1 != null && type1 != 0) {
			query.setParameter("type1", type1);
		}
		if(type2 != null && type2 != 0) {
			query.setParameter("type2", type2);
		}
		if(acqType != null && acqType != 0) {
			query.setParameter("acqType", acqType);
		}
		
		entityList = (List<Object[]>) query.getResultList();

		
		return entityList;
	}

}
