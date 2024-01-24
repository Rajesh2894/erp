/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetValuationDetails;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetValuationRepositoryImpl extends AbstractDAO<Long> implements AssetValuationRepositoryCustom {

 

    @Override
    public Date findLatestBookEndDate(Long orgId, Long assetId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AssetValuationDetails> cq = cb.createQuery(AssetValuationDetails.class);
        Root<AssetValuationDetails> rt = cq.from(AssetValuationDetails.class);
        cq.select(rt.get("bookEndDate"));
        cq.where(cb.equal(rt.get("orgId"), orgId), cb.equal(rt.get("assetId"), assetId));
        cq.orderBy(cb.desc(rt.get("valuationDetId")));
        Query query = entityManager.createQuery(cq);
        query.setMaxResults(1);
        Date date = (Date) query.getSingleResult();
        return date;
    }

}
