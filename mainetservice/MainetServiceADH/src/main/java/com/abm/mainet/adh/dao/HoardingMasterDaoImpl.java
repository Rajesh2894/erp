/**
 * 
 */
package com.abm.mainet.adh.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.HoardingMasterEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Anwarul.Hassan
 * @since 22-Aug-2019
 */
@Repository
public class HoardingMasterDaoImpl extends AbstractDAO<HoardingMasterEntity> implements IHoardingMasterDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.adh.dao.IHoardingMasterDao#searchHoardingMasterData(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<HoardingMasterEntity> searchHoardingMasterData(Long orgId, String hoardingNumber,
            Long hoardingStatus, Long hoardingType, Long hoardingSubType, Long hoardingSubType3, Long hoardingSubType4,
            Long hoardingSubType5, Long hoardingLocation) {
        List<HoardingMasterEntity> entity = null;

        try {
            StringBuilder hql = new StringBuilder(
                    "SELECT hm FROM HoardingMasterEntity hm WHERE hm.orgId =:orgId");
            if (StringUtils.isNotBlank(hoardingNumber)) {
                hql.append(" and hm.hoardingNumber =:hoardingNumber");
            }
            if (hoardingStatus != null) {
                hql.append(" and hm.hoardingStatus =:hoardingStatus");
            }
            if (hoardingType != null) {
                hql.append(" and hm.hoardingTypeId1 =:hoardingType");
            }
            if (hoardingSubType != null) {
                hql.append(" and hm.hoardingTypeId2 =:hoardingSubType");
            }
            if (hoardingSubType3 != null) {
                hql.append(" and hm.hoardingTypeId3 =:hoardingSubType3");
            }
            if (hoardingSubType4 != null) {
                hql.append(" and hm.hoardingTypeId4 =:hoardingSubType4");
            }
            if (hoardingSubType5 != null) {
                hql.append(" and hm.hoardingTypeId5 =:hoardingSubType5");
            }
            if (hoardingLocation != null) {
                hql.append(" and hm.locationId =:hoardingLocation");
            }
            // hql.append(" ORDER BY hoardingNumber DESC");
            final Query query = this.createQuery(hql.toString());
            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

            if (StringUtils.isNotBlank(hoardingNumber)) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_NUMBER, hoardingNumber);
            }
            if (hoardingStatus != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_STATUS, hoardingStatus);
            }
            if (hoardingType != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_TYPE, hoardingType);
            }
            if (hoardingSubType != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE, hoardingSubType);
            }
            if (hoardingSubType3 != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE3, hoardingSubType3);
            }
            if (hoardingSubType4 != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE4, hoardingSubType4);
            }
            if (hoardingSubType5 != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_SUB_TYPE5, hoardingSubType5);
            }
            if (hoardingLocation != null) {
                query.setParameter(MainetConstants.AdvertisingAndHoarding.HOARDING_LOCATION, hoardingLocation);
            }
            entity = (List<HoardingMasterEntity>) query.getResultList();
        } catch (Exception exception) {
            throw new FrameworkException("Error Occured While fetching the Hoarding Master Data", exception);
        }
        return entity;
    }
}
