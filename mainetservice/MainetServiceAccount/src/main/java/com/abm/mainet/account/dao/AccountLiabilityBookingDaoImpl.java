
package com.abm.mainet.account.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountLiabilityBookingEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class AccountLiabilityBookingDaoImpl extends AbstractDAO<AccountLiabilityBookingEntity>
        implements AccountLiabilityBookingDao {

    private static final String TR_TENDER_ID = "trTenderId";

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountLiabilityBookingDao#isLiabilityExists(java.lang.Long, java.lang.Long)
     */
    @Override
    public Boolean isLiabilityExists(final Long tenderId, final Long orgId) {

        final Query query = createQuery(QueryConstants.MASTERS.LIABILITY_BOOKING.CHECK_IF_LIABILITY_EXISTS);

        query.setParameter(TR_TENDER_ID, tenderId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

}
