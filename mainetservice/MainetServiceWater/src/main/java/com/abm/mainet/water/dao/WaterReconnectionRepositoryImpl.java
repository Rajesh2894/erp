package com.abm.mainet.water.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;

/**
 * @author Arun.Chavda
 *
 */
@Repository
public class WaterReconnectionRepositoryImpl extends AbstractDAO<TbWaterReconnection> implements WaterReconnectionRepository {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterReconnectionRepository#checkIsRegisteredPlumberLicNo(com.abm.mainet.water.dto.
     * WaterReconnectionRequestDTO)
     */
    @Override
    public WaterReconnectionResponseDTO checkIsRegisteredPlumberLicNo(final WaterReconnectionRequestDTO requestDTO) {

        final WaterReconnectionResponseDTO responseDTO = new WaterReconnectionResponseDTO();

        final Query query = entityManager.createQuery("FROM PlumberMaster m WHERE m.plumLicNo=?1 AND m.orgid=?2");
        query.setParameter(1, requestDTO.getPlumberLicNo());
        query.setParameter(2, requestDTO.getOrgId());
        final PlumberMaster plumberMaster = (PlumberMaster) query.getSingleResult();

        if (plumberMaster != null) {
            responseDTO.setStatus(MainetConstants.Req_Status.SUCCESS);
            responseDTO.setPlumberId(plumberMaster.getPlumId());
        } else {
            responseDTO.setStatus(MainetConstants.Req_Status.FAIL);
        }
        return responseDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterReconnectionRepository#saveWaterReconnectionDetails(com.abm.mainet.water.dto.
     * WaterReconnectionRequestDTO)
     */
    @Override
    public void saveWaterReconnectionDetails(final TbWaterReconnection entity) {

        entityManager.persist(entity);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterReconnectionRepository#getReconnectionDetails(java.lang.Long)
     */
    @Override
    public TbWaterReconnection getReconnectionDetails(final Long applicationId, final Long orgId) {

        final Query query = entityManager.createQuery("FROM TbWaterReconnection w WHERE w.apmApplicationId=?1 AND w.orgId=?2");
        query.setParameter(1, applicationId);
        query.setParameter(2, orgId);
        final TbWaterReconnection reconnection = (TbWaterReconnection) query.getSingleResult();
        return reconnection;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.water.dao.WaterReconnectionRepository#updatedWaterReconnectionDetailsByDept(com.abm.mainet.water.domain.
     * TbWaterReconnection)
     */
    @Override
    public void updatedWaterReconnectionDetailsByDept(final TbWaterReconnection reconnection) {

        try {
            entityManager.merge(reconnection);
        } catch (final Exception e) {
            logger.error(e);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterReconnectionRepository#updatedBillingStatusOfCSMRInfo(java.lang.Long, java.lang.Long)
     */
    @Override
    public void updatedBillingStatusOfCSMRInfo(final Long consumerIdNo, final Long orgId, final String billingFlag) {

        try {

            final Query query = entityManager.createQuery("UPDATE TbKCsmrInfoMH t set t.csIsBillingActive=?"
                    + "WHERE t.csIdn = ? AND t.orgId =?");

            query.setParameter(1, billingFlag);
            query.setParameter(2, consumerIdNo);
            query.setParameter(3, orgId);
            query.executeUpdate();
        } catch (final Exception e) {
            logger.error(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterReconnectionRepository#updatedMeterStatusOfMeterMaster(java.lang.Long, java.lang.Long)
     */
    @Override
    public void updatedMeterStatusOfMeterMaster(final Long consumerIdNo, final Long orgId, final String meterStatus) {

        try {

            final Query query = entityManager.createQuery("UPDATE TbMeterMasEntity m set m.mmStatus=?"
                    + "WHERE m.tbCsmrInfo.csIdn = ? AND m.orgid =?");

            query.setParameter(1, meterStatus);
            query.setParameter(2, consumerIdNo);
            query.setParameter(3, orgId);
            query.executeUpdate();
        } catch (final Exception e) {
            logger.error(e);
        }
    }

    @Override
    public long isAlreadyAppliedForReConn(final Long csIdn) {
        final Query query = entityManager.createQuery("select count(*) FROM TbWaterReconnection m WHERE m.csIdn=?1 ");
        query.setParameter(1, csIdn);
        final Long count = (Long) query.getSingleResult();
        return count;

    }

	@Override
	public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
		Long plumId=null;
    	try {
            final Query query = entityManager.createQuery(
                    "SELECT plumId FROM TbWaterReconnection c WHERE c.apmApplicationId=? and c.orgId=?");
            query.setParameter(1, applicationId);
            query.setParameter(2, orgId);
            plumId = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking plum id by application number " + e);
        }
		return plumId;
	}

}
