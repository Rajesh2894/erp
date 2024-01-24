package com.abm.mainet.cfc.challan.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Repository
public class ChallanAtULBCounterDAO extends AbstractDAO<ChallanMaster> implements IChallanAtULBCounterDAO {
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.cfc.challan.dao.IChallanAtULBCounterDAO#queryChallanDetails (long, long)
     */
    @Override
    public Object[] queryChallanDetails(final Long challanNo, final Long applicationNo,final String payStaus, final long orgId) {
        Object[] object = null;
        final StringBuilder hql = new StringBuilder(QueryConstants.QUERY.CHALLAN_DETAILS);
        if (null != challanNo) {
            hql.append(QueryConstants.QUERY.CHALLAN_RESTRICTION);
        }
        if (null != applicationNo) {
            hql.append(QueryConstants.QUERY.APPLICATION_RESTRICTION);
        }

        hql.append(QueryConstants.QUERY.CHALLAN_ORDER);

        final Query query = entityManager.createQuery(hql.toString());

        if (null != challanNo) {
            query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_NO, challanNo.toString());
        }
        if (null != applicationNo) {
            query.setParameter(MainetConstants.TABLE_COLUMN.APPLICATION_NO, applicationNo);
        }
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_STATUS, MainetConstants.CHALLAN_STATUS.ACTIVE);

        final Long paymentMode = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.PRIFIX_CODE.PAY_AT_ULB_COUNTER, PrefixConstants.PRIFIX.ONLINE_OFFLINE)
                .getLookUpId();

        query.setParameter(MainetConstants.TABLE_COLUMN.PAYMENT_MODE, paymentMode);
        query.setParameter(MainetConstants.TABLE_COLUMN.RECEIVE_FLAG, payStaus);

        final List<Object[]> object1 = query.getResultList();
        if ((object1 != null) && !object1.isEmpty()) {
            object = object1.get(0);
        }

        return object;
    }

    @Override
    public boolean updateChallanDetails(final ChallanDetailsDTO challanDetails) {
        boolean status = false;
        final Query query = entityManager.createQuery(QueryConstants.QUERY.UPDATE_CHALLAN_RECEIVE_FLAG);

        query.setParameter(MainetConstants.TABLE_COLUMN.RECEIVE_FLAG, MainetConstants.PAY_STATUS.PAID);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, challanDetails.getOrgId());
        query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_NO, String.valueOf(challanDetails.getChallanNo()));
        query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_ID, challanDetails.getChallanId());
        query.setParameter(MainetConstants.TABLE_COLUMN.RECEIVE_DATE, new Date());
        query.setParameter(MainetConstants.TABLE_COLUMN.RECEIVE_BY, UserSession.getCurrent().getEmployee().getEmpId());
        query.setParameter("updatedBy", UserSession.getCurrent().getEmployee());
        query.setParameter("updatedDate", new Date());
        query.setParameter("lgIpMacUpd", Utility.getMacAddress());
        final int i = query.executeUpdate();
        if (i > 0) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.IChallanAtULBCounterDAO#inactiveOldChallan(java.lang.Long, java.lang.Long,
     * long)
     */
    @Override
    public void inactiveOldChallan(final Long applicationNo, final Long challanNo,
            final long orgid) {
        final StringBuilder builString = new StringBuilder("update ChallanMaster as c set "
                + " c.status=:status ,c.updatedBy =:updatedBy, "
                + " c.updatedDate=:updatedDate, c.lgIpMacUpd=:lgIpMacUpd where c.organisationId = :orgId ");
        if (challanNo != null) {
            builString.append(" and c.challanNo = :challanNo ");
        }
        if (applicationNo != null) {
            builString.append(" and c.apmApplicationId = :applicationNo ");
        }

        final Query query = entityManager.createQuery(builString.toString());

        query.setParameter("status", MainetConstants.CHALLAN_STATUS.INACTIVE);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgid);
        if (challanNo != null) {
            query.setParameter(MainetConstants.TABLE_COLUMN.CHALLAN_NO, String.valueOf(challanNo));
        }
        if (applicationNo != null) {
            query.setParameter(MainetConstants.TABLE_COLUMN.APPLICATION_NO, applicationNo);
        }
        query.setParameter("updatedBy", UserSession.getCurrent().getEmployee());
        query.setParameter("updatedDate", new Date());
        query.setParameter("lgIpMacUpd", Utility.getMacAddress());
        query.executeUpdate();
    }

}
