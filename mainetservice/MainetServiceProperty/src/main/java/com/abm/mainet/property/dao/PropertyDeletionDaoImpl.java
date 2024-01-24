package com.abm.mainet.property.dao;

import java.math.BigDecimal;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class PropertyDeletionDaoImpl extends AbstractDAO<Long> implements IPropertyDeletionDao {
	 private static Logger log = Logger.getLogger(PropertyDeletionDaoImpl.class);

    @Override
    @Transactional
    public int validatePropertyForBillExistOrNot(String propNo, long orgId) {

        StringBuilder queryString = new StringBuilder(
                "select sum(cnt) from (select count(1) cnt from tb_as_bill_mas a where a.mn_prop_no=:propNo  and a.orgid=:orgId union select count(1) cnt from tb_as_pro_bill_mas b where b.pro_prop_no=:propNo and b.orgid=:orgId) z");
        log.info("Final Query String------------>>>>>>>>>>>>>>>>>>>>>>> "+queryString.toString());
        final Query query = createNativeQuery(queryString.toString());

        query.setParameter("orgId", orgId);
        query.setParameter("propNo", propNo);

        Object singleResult = query.getSingleResult();

        BigDecimal count = (BigDecimal) query.getSingleResult();
        return count.intValue();

    }

}
