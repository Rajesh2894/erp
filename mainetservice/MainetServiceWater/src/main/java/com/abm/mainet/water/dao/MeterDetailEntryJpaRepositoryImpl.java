package com.abm.mainet.water.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;

/**
 * Repository : TbMeterMasEntity .
 */
@Repository
public class MeterDetailEntryJpaRepositoryImpl extends AbstractDAO<TbKCsmrInfoMH> implements MeterDetailEntryJpaRepository {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MeterDetailEntryJpaRepositoryImpl.class);
    private static final String YES = MainetConstants.Common_Constant.YES;

    private static final String SELECT_FROM_CSMR_INFO = "select csmrInfo from TbKCsmrInfoMH csmrInfo  WHERE csmrInfo.orgId=?1 and csmrInfo.applicationNo=?2"
            + " and csmrInfo.pcFlg='Y' and csmrInfo.csMeteredccn=?3";

    private static final String SELECT_FROM_WORK_ORDER = "select workOrd from TbWorkOrderEntity workOrd  WHERE workOrd.orgid=?1 and workOrd.woPrintFlg=?2 order by 1 desc";

    private static final String SELECT_FROM_METER_MAS = "select am from TbMeterMasEntity am  WHERE  am.orgid=?1 and am.tbCsmrInfo.csIdn=?2";

    private static final String UPDATE_METER_DETAILS = "update TbMeterMasEntity as  c set c.mmMtrmake = :mmMtrmake,"
    		+ " c.mmMtrno =:mmMtrno, c.mmInitialReading=:mmInitialReading, c.maxMeterRead =:maxMeterRead, "
    		+ "c.mmInstallDate=:mmInstallDate, c.mmMtrcost=:mmMtrcost, c.mmOwnership=:mmOwnership, c.lgIpMacUpd=:lgIpMacUpd, "
    		+ "c.updatedDate =:updatedDate, c.updatedBy=:updatedBy where c.orgid =:orgid and c.tbCsmrInfo.csIdn =:csIdn";

    @Override
    public boolean updateFormData(final TbMeterMasEntity entity) {
        entityManager.merge(entity);
        return true;
    }

    @Override
    public List<TbWorkOrderEntity> queryDataFromWorkOrderTab(final BigDecimal orgId) {
        final Query query = createQuery(SELECT_FROM_WORK_ORDER);

        query.setParameter(1, orgId.longValue());
        query.setParameter(2, YES);

        @SuppressWarnings("unchecked")
        final List<TbWorkOrderEntity> workOrderEntity = query.getResultList();
        return workOrderEntity;
    }

    @Override
    public TbKCsmrInfoMH getConnectionIdFromCsmrInfoUsingAppId(final Long applicationNumber, final Long orgId, final Long meter) {
        final Query query = createQuery(SELECT_FROM_CSMR_INFO);

        query.setParameter(1, orgId);
        query.setParameter(2, applicationNumber);
        query.setParameter(3, meter);

        try {
            final TbKCsmrInfoMH csmrInfo = (TbKCsmrInfoMH) query.getSingleResult();
            return csmrInfo;
        } catch (final NoResultException e) {
            LOGGER.error("No record for application number: " + applicationNumber, e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterDetailEntryJpaRepository#getMeterMasEntity(java.lang.Long)
     */
    @Override
    public boolean getMeterMasEntity(final Long csIdn, final Long orgId) {

        boolean flag = false;
        final Query query = createQuery(SELECT_FROM_METER_MAS);

        query.setParameter(1, orgId);
        query.setParameter(2, csIdn);

        final List<TbMeterMasEntity> csmrInfo = query.getResultList();
        if ((csmrInfo != null) && !csmrInfo.isEmpty()) {
            flag = true;
        }

        return flag;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<TbMeterMasEntity> getMeterMasEntities(final Long csIdn, final Long orgId) {
    	List<TbMeterMasEntity> meterMasEntity = null;
        final Query query = createQuery(SELECT_FROM_METER_MAS);
        query.setParameter(1, orgId);
        query.setParameter(2, csIdn);
        try {
        	 meterMasEntity = query.getResultList();
        }catch(Exception e) {
        	LOGGER.error("Failed to fetch meter details for csIdn "+ csIdn + e.getMessage());
        }
        return meterMasEntity;
    }

    private static final String upc = "update TbMeterMasEntity as  c set c.mmMtrmake = :mmMtrmake,"
    		+ " c.mmMtrno =:mmMtrno, c.mmInitialReading=:mmInitialReading, c.maxMeterRead =:maxMeterRead, "
    		+ "c.mmInstallDate=:mmInstallDate, c.mmMtrcost=:mmMtrcost, c.mmOwnership=:mmOwnership, c.lgIpMacUpd=:lgIpMacUpd "
    		+ "where c.orgid = :orgid and c.tbCsmrInfo.csIdn = :csIdn";

	@Override
	public boolean saveOrUpdateData(TbMeterMasEntity queryEntity) {
		final Query updateMeterMasQuery = createQuery(UPDATE_METER_DETAILS);
		updateMeterMasQuery.setParameter(MainetConstants.METER_MAS_COLUMNS.METER_MAKE, queryEntity.getMmMtrmake())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.METER_NO, queryEntity.getMmMtrno())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.INITIAL_READING, queryEntity.getMmInitialReading())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.MAX_READING, queryEntity.getMaxMeterRead())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.INSTALL_DATE, queryEntity.getMmInstallDate())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.METER_COST, queryEntity.getMmMtrcost())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.OWNERSHIP, queryEntity.getMmOwnership())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.ORG_ID, queryEntity.getOrgid())
				.setParameter(MainetConstants.METER_MAS_COLUMNS.CS_IDN, queryEntity.getTbCsmrInfo().getCsIdn());
		updateHqlAuditDetailsPrimitive(updateMeterMasQuery);

		try {
			final int rowCount = updateMeterMasQuery.executeUpdate();
			return rowCount == 1 ? true : false;

		}catch(Exception e) {
        	LOGGER.error("Failed to update meter details for csIdn "+ queryEntity.getTbCsmrInfo().getCsIdn() + e.getMessage());
        	return false;
		}
	}
}