/**
 * 
 */
package com.abm.mainet.account.dao;

import java.util.Date;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Anwarul.Hassan
 * @since 12-Dec-2019
 */
@Repository
public class StopPaymentDaoImpl extends AbstractDAO<AccountPaymentMasterEntity> implements IStopPaymentDao {
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.IStopPaymentDao#searchPaymentDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    public AccountPaymentMasterEntity searchPaymentDetails(String paymentNo, Long instrumentNumber, Date paymentDate,
            Long orgId) {
        AccountPaymentMasterEntity entity = null;
        try {
            StringBuilder hql = new StringBuilder("select m from AccountPaymentMasterEntity m where m.orgId =:orgId");
            if (StringUtils.isNotBlank(paymentNo)) {
                hql.append(" and m.paymentNo =:paymentNo");
            }
            if (instrumentNumber != null) {
                hql.append(" and m.instrumentNumber =:instrumentNumber");
            }
            if (paymentDate != null) {
                hql.append(" and m.paymentDate =:paymentDate");
            }
            final Query query = this.createQuery(hql.toString());
            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
            if (StringUtils.isNotBlank(paymentNo)) {
                query.setParameter("paymentNo", paymentNo);
            }
            if (instrumentNumber != null) {
                query.setParameter("instrumentNumber", instrumentNumber);
            }
            if (paymentDate != null) {
                query.setParameter("paymentDate", paymentDate);
            }
            try {
            	 entity = (AccountPaymentMasterEntity) query.getSingleResult();
            }catch (Exception e) {
				// TODO: handle exception
			}
           

            if (entity == null) {
                return null;
            }

        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the payment details", exception);
        }
        return entity;

    }
}
