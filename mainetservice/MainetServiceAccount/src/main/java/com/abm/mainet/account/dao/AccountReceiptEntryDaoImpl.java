
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author dharmendra.chouhan
 *
 */
@Repository
public class AccountReceiptEntryDaoImpl extends AbstractDAO<TbServiceReceiptMasEntity> implements AccountReceiptEntryDao {

    private static final Logger LOG = Logger.getLogger(AccountReceiptEntryDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<TbServiceReceiptMasEntity> getReceiptDetail(Long orgid, final BigDecimal rmmount,
            final Long rmrcptno,
            final String rmReceivedfrom, final Date rmDate) {

        String queryString = "select de from TbServiceReceiptMasEntity de where de.orgId =:orgId ";

        if (rmmount != null && rmmount != BigDecimal.ZERO) {
            queryString += " and de.rmAmount =:rmmount";
        }
        if (rmrcptno != null && rmrcptno != 0L) {
            queryString += " and de.rmRcptno =:rmrcptno";
        }
        if (rmReceivedfrom != null && !rmReceivedfrom.isEmpty()) {
            queryString += " and de.rmReceivedfrom =:rmReceivedfrom";
        }
        if (rmDate != null) {
            queryString += " and de.rmDate =:rmDate";
        }

        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgid);

        if (rmmount != null && rmmount != BigDecimal.ZERO) {
            query.setParameter("rmmount",
                    rmmount);
        }
        if (rmrcptno != null && rmrcptno != 0L) {
            query.setParameter("rmrcptno",
                    rmrcptno);
        }
        if (rmReceivedfrom != null && !rmReceivedfrom.isEmpty()) {
            query.setParameter("rmReceivedfrom",
                    rmReceivedfrom);
        }
        if (rmDate != null) {
            query.setParameter("rmDate",
                    rmDate);
        }
        List<TbServiceReceiptMasEntity> result = null;
        result = query.getResultList();
        return result;

    }

}
