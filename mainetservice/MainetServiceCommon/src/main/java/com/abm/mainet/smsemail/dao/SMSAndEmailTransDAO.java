package com.abm.mainet.smsemail.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.smsemail.domain.SmsEmailTransaction;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Repository
public class SMSAndEmailTransDAO extends AbstractDAO<SmsEmailTransaction> implements ISMSAndEmailTransDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.ISMSAndEmailDAO#saveMessageTemplate(com.abm.mainet.domain.core.SMSAndEmailInterface)
     */
    @Override
    public boolean saveMessageTemplate(final SmsEmailTransaction entity) {
        try {
            entityManager.merge(entity);
            return true;
        }

        catch (final Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsEmailTransaction> getSmsEmailTransaction(final long orgId, final Long empId) {
        final Query query = createQuery(
                "select master from  SmsEmailTransaction master  where "
                        + " master.orgId.orgid=?1 and "
                        + " master.userId.empId=?2 and "
                        + " master.refId4=?3 and master.refId3=?4 order by master.smsId desc ");

        query.setParameter(1, orgId);
        query.setParameter(2, empId);
        query.setParameter(3, "N");
        query.setParameter(4, "S");
        
        final List<SmsEmailTransaction> transList = query.getResultList();
        
        return transList;
        
    }
    
    
   /* @Override
    public void updateRefId4(final Long smsId) {
        final Query query = createQuery("update SmsEmailTransaction set isDeleted='N' where smsId=?1");
        query.setParameter(1, smsId);
        query.executeUpdate();
    }*/
    
}
