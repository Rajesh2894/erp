/**
 * 
 */
package com.abm.mainet.adh.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.NewAdvertisementApplication;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author anwarul.hassan
 * @since 28-Nov-2019
 */
@Repository
public class AdvertisementDataEntryDaoImpl extends AbstractDAO<NewAdvertisementApplication>
        implements IAdvertisementDataEntryDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.dao.IAdvertisementDataEntryDao#searchDataEntry(java.lang.Long, java.lang.Long, java.lang.String,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NewAdvertisementApplication> searchDataEntry(Long orgId, Long agencyId, Long licenseType, String adhStatus,
            Long locId,java.sql.Date licenseFromDate,java.sql.Date licenseToDate) {
        List<NewAdvertisementApplication> entity = null;
        try {
            StringBuilder hql = new StringBuilder("SELECT nap FROM NewAdvertisementApplication nap WHERE nap.orgId =:orgId");
            if (agencyId != null) {
                hql.append(" and nap.agencyId =:agencyId");
            }
            if (licenseType != null) {
                hql.append(" and nap.licenseType =:licenseType");
            }
            if (!StringUtils.isEmpty(adhStatus)) {
                hql.append(" and nap.adhStatus =:adhStatus");
            }
            if (locId != null) {
                hql.append(" and nap.locId =:locId");
            }
            if (licenseFromDate != null) {
                hql.append(" and nap.licenseFromDate =:licenseFromDate");
            }
            if (licenseToDate != null) {
                hql.append(" and nap.licenseToDate =:licenseToDate");
            }
            final Query query = this.createQuery(hql.toString());
            query.setParameter(MainetConstants.AdvertisingAndHoarding.ORG_ID, orgId);
            if (agencyId != null) {
                query.setParameter("agencyId", agencyId);
            }
            if (licenseType != null) {
                query.setParameter("licenseType", licenseType);
            }
            if (!StringUtils.isEmpty(adhStatus)) {
                query.setParameter("adhStatus", adhStatus);
            }
            if (locId != null) {
                query.setParameter("locId", locId);
            }
            if (licenseFromDate != null) {
                query.setParameter("licenseFromDate", licenseFromDate);
            }
            if (licenseToDate != null) {
                query.setParameter("licenseToDate", licenseToDate);
            }
            entity = (List<NewAdvertisementApplication>) query.getResultList();
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Advertisement Data", exception);
        }
        return entity;

    }

}
