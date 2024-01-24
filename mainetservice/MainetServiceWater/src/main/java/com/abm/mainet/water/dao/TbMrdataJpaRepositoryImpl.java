package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.TbMrdataEntity;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class TbMrdataJpaRepositoryImpl extends AbstractDAO<TbMrdataEntity> implements TbMrdataJpaRepository {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MeterCutOffRestorationRepositoryImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.TbMrdataJpaRepository#saveTbMrDataEntity(com.abm.mainet.water.domain.TbMrdataEntity)
     */
    @Override
    public void saveTbMrDataEntity(final TbMrdataEntity tbMrdataEntity) {
        try {
            entityManager.merge(tbMrdataEntity);
        } catch (final Exception e) {
            LOGGER.error("Exception in meter reading save", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.TbMrdataJpaRepository#findOne(java.lang.Long, long)
     */
    @Override
    public TbMrdataEntity findOne(final Long mrdId, final long orgid) {
        TbMrdataEntity data = null;
        final StringBuilder queryString = new StringBuilder(QueryConstants.WATER_MODULE_QUERY.METER_READING.METERDATA_BYMRID);

        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("mrdId", mrdId);
        query.setParameter("orgid", orgid);
        try {
            data = (TbMrdataEntity) query.getSingleResult();
        } catch (final Exception e) {
            LOGGER.error("Exception in meter reading search data mrdId:" + mrdId, e);
        }
        return data;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.TbMrdataJpaRepository#getMrDataByCsidnAndOrgId(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<TbMrdataEntity> getMrDataByCsidnAndOrgId(final Long csIdn, final Long orgid) {
        final StringBuilder queryString = new StringBuilder(QueryConstants.WATER_MODULE_QUERY.METER_READING.METERDATA_BYCSIDN);

        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("csIdn", csIdn);
        query.setParameter("orgid", orgid);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.TbMrdataJpaRepository#getMrDataByCsidnAndOrgId(java.util.List, long)
     */
    @Override
    public List<TbMrdataEntity> getMrDataByCsidnAndOrgId(final List<Long> csIdn,
            final long orgid) {
        final String queryString = new String(QueryConstants.WATER_MODULE_QUERY.METER_READING.MAX_METERDATA_BYCSIDN);
        final Query query = entityManager.createQuery(queryString);
        query.setParameter("csIdn", csIdn);
        query.setParameter("orgid", orgid);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.TbMrdataJpaRepository#updateBillGeneratedFlagInMeter(long, long)
     */
    @Override
    public void updateBillGeneratedFlagInMeter(final Set<Long> connectionids, final long orgnisation) {
        final String queryString = new String(
                "update TbMrdataEntity m set m.billGen='Y' where m.orgid=:orgid and m.tbCsmrInfo.csIdn in(:csIdn)");
        final Query query = entityManager.createQuery(queryString);
        query.setParameter("csIdn", connectionids);
        query.setParameter("orgid", orgnisation);
        query.executeUpdate();
    }

    @Override
    public String validateBillPresentOrNot(final Long mmMtnid, final long orgid, final Date endDate) {
        String data = null;
        final Query query = entityManager.createQuery("select max(b.bmNo)  from TbMrdataEntity m ,TbWtBillMasEntity b where "
                + " m.tbCsmrInfo.csIdn=b.csIdn and m.orgid=b.orgid and m.mrdTo >= :bmTodt "
                + "and m.orgid=:orgid and m.mmMtnid=:mmMtnid");
        query.setParameter("mmMtnid", mmMtnid);
        query.setParameter("orgid", orgid);
        query.setParameter("bmTodt", endDate);
        try {
            data = (String) query.getSingleResult();
        } catch (final Exception e) {
            LOGGER.error("Exception in meter reading search data mmMtnid:" + mmMtnid, e);
        }
        return data;
    }

    @Override
    public List<TbMrdataEntity> meterReadingDataByCsidnAndOrgId(final List<Long> csIdn, final long orgid) {
        final StringBuilder queryString = new StringBuilder(
                "select m from TbMrdataEntity m where m.tbCsmrInfo.csIdn in(:csIdn) and m.orgid=:orgid order by m.mrdId desc ");
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("csIdn", csIdn);
        query.setParameter("orgid", orgid);
        return query.getResultList();
    }

    /**
     * Get list of meter reading entries whose bill has not been generated
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<TbMrdataEntity> meterReadingDataForBillGen(List<Long> csIdn, Long orgid) {
        
            final String queryString = new String(QueryConstants.WATER_MODULE_QUERY.METER_READING.UNBILLED_METER_READINGS);
            final Query query = entityManager.createQuery(queryString);
            query.setParameter("csIdn", csIdn);
            query.setParameter("orgid", orgid);
            return query.getResultList();

	}

}
