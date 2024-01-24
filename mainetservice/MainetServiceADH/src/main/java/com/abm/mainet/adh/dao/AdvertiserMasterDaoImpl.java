package com.abm.mainet.adh.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.AdvertiserMasterHistoryEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author cherupelli.srikanth
 * @since 03 august 2019
 */
@Repository
public class AdvertiserMasterDaoImpl extends AbstractDAO<AdvertiserMasterEntity> implements IAdvertiserMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<AdvertiserMasterEntity> searchAdvertiserData(Long orgId, String advertiserNumber,
            String advertiserOldNumber, String advertiserName, String advertiserStatus) {

        List<AdvertiserMasterEntity> entity = null;
        try {

            StringBuilder hql = new StringBuilder("SELECT am FROM AdvertiserMasterEntity am WHERE am.orgId =:orgId");
            if (StringUtils.isNotBlank(advertiserNumber)) {
                hql.append(" and am.agencyLicNo =:advertiserNumber");
            }
            if (StringUtils.isNotBlank(advertiserOldNumber)) {
                hql.append(" and am.agencyOldLicNo =:advertiserOldNumber");
            }
            if (StringUtils.isNotBlank(advertiserName)) {
                hql.append(" and am.agencyName =:advertiserName");
            }
            if (StringUtils.isNotBlank(advertiserStatus)) {
                hql.append(" and am.agencyStatus =:advertiserStatus");
            }

            final Query query = this.createQuery(hql.toString());

            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

            if (StringUtils.isNotBlank(advertiserNumber)) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.ADVERTISER_NUMBER, advertiserNumber);
            }
            if (StringUtils.isNotBlank(advertiserOldNumber)) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.ADVERTISER_OLD_NUMBER, advertiserOldNumber);
            }
            if (StringUtils.isNotBlank(advertiserName)) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.ADVERTISER_NAME, advertiserName);
            }
            if (StringUtils.isNotBlank(advertiserStatus)) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.ADVERTISER_STATUS, advertiserStatus);
            }
            entity = (List<AdvertiserMasterEntity>) query.getResultList();

        } catch (Exception exception) {

            throw new FrameworkException("Error Occured While fetching the Advertiser Master Data", exception);
        }
        return entity;
    }

    @Override
    public void saveInAdvertiserMasterHistoryEntity(AdvertiserMasterHistoryEntity entity) {
        this.entityManager.persist(entity);
    }

}
