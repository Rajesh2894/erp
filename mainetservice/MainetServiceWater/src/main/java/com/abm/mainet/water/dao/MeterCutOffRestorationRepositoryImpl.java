package com.abm.mainet.water.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWaterCutRestoration;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;

/**
 * @author Arun.Chavda
 *
 */
@Repository
public class MeterCutOffRestorationRepositoryImpl extends AbstractDAO<TbWaterCutRestoration>
        implements MeterCutOffRestorationRepository {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MeterCutOffRestorationRepositoryImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#getMeterDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    public TbMeterMasEntity getMeterDetails(final Long consumerId, final Long orgId) {

        final Query query = entityManager.createQuery(
                "SELECT m FROM TbMeterMasEntity m WHERE m.mmMtnid =(SELECT max(mm.mmMtnid) FROM  TbMeterMasEntity mm WHERE  mm.tbCsmrInfo.csIdn=?1 AND mm.orgid=?2)");
        query.setParameter(1, consumerId);
        query.setParameter(2, orgId);
        TbMeterMasEntity entity = null;
        try {
            entity = (TbMeterMasEntity) query.getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.error("Error Occured during get Meter details", e);
            return null;
        }
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#saveCutOffRestorationDetails(com.abm.mainet.water.domain.
     * TbWaterCutRestoration)
     */
    @Override
    public void saveCutOffRestorationDetails(final TbWaterCutRestoration tbWaterCutRestoration) {
        try {
            entityManager.persist(tbWaterCutRestoration);
        } catch (final Exception e) {
            logger.error(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#getPreviousCutOffDetails(java.lang.String, java.lang.Long)
     */
    @Override
    public List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetails(final Long meterId, final Long consumerId,
            final Long orgId, final String cutOffResFlag) {

        final Query query = createQuery("select c.mmCutResDate,c.mmCutResRead,c.mmCutResRemark,"
                + " d.mmMtrno,d.mmOwnership from "
                + " TbWaterCutRestoration c ,TbMeterMasEntity d WHERE c.mmMtnid=d.mmMtnid and c.mmMtnid=?1 and c.csIdn=?2 and c.orgId=?3 and c.mmCutResFlag=?4 ");

        query.setParameter(1, meterId);
        query.setParameter(2, consumerId);
        query.setParameter(3, orgId);
        query.setParameter(4, cutOffResFlag);
        @SuppressWarnings("unchecked")
        final List<Object> result = query.getResultList();
        final int listSize = result.size();
        final List<MeterCutOffRestorationDTO> waterReconnectionResponseDTOs = new ArrayList<>(0);
        MeterCutOffRestorationDTO responseDTO = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

        for (int iCounter = 0; iCounter < listSize; iCounter++) {

            final Object[] obj = (Object[]) result.get(iCounter);

            responseDTO = new MeterCutOffRestorationDTO();

            if (obj[0] instanceof java.util.Date) {
                final java.util.Date time = (java.util.Date) obj[0];
                responseDTO.setCutOffDate(simpleDateFormat.format(time));
            }

            if (obj[1] != null) {
                responseDTO.setCutResRead(Long.parseLong(obj[1].toString()));
            }

            if (obj[2] != null) {
                responseDTO.setCutResRemark(obj[2].toString());
            }

            if (obj[3] != null) {
                responseDTO.setMeterNo(obj[3].toString());
            }

            if (obj[4] != null) {
                String ownerShip = MainetConstants.BLANK;
                final Organisation organisation = UserSession.getCurrent().getOrganisation();
                final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.parseLong(obj[4].toString()),
                        organisation);
                ownerShip = lookUp.getLookUpDesc();
                responseDTO.setOwnership(ownerShip);
            }
            responseDTO.setApprovedBy(UserSession.getCurrent().getEmployee().getEmpname());
            waterReconnectionResponseDTOs.add(responseDTO);

        }

        return waterReconnectionResponseDTOs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#getNonMeterPreviousDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<MeterCutOffRestorationDTO> getNonMeterPreviousDetails(final Long consumerId, final Long orgId,
            final String cutOffResFlag) {

        final StringBuilder builder = new StringBuilder();

        builder.append(" select c.mmCutResDate,c.mmCutResRemark"
                + " from TbWaterCutRestoration c "
                + " WHERE c.csIdn=?1 and c.orgId=?2 and c.mmCutResFlag=?3 ");

        final Query query = createQuery(builder.toString());

        query.setParameter(1, consumerId);
        query.setParameter(2, orgId);
        query.setParameter(3, cutOffResFlag);
        @SuppressWarnings("unchecked")
        final List<Object> result = query.getResultList();

        final int listSize = result.size();
        final List<MeterCutOffRestorationDTO> waterReconnectionResponseDTOs = new ArrayList<>(0);
        MeterCutOffRestorationDTO responseDTO = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

        for (int iCounter = 0; iCounter < listSize; iCounter++) {

            final Object[] obj = (Object[]) result.get(iCounter);

            responseDTO = new MeterCutOffRestorationDTO();

            if (obj[0] instanceof java.util.Date) {
                final java.util.Date time = (java.util.Date) obj[0];

                responseDTO.setCutOffDate(simpleDateFormat.format(time));
            }

            if (obj[1] != null) {

                responseDTO.setCutResRemark(obj[1].toString());
            }

            responseDTO.setApprovedBy(UserSession.getCurrent().getEmployee().getEmpname());

            waterReconnectionResponseDTOs.add(responseDTO);

        }

        return waterReconnectionResponseDTOs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#getCutOffReadingAndDate(java.lang.Long, java.lang.Long,
     * long)
     */
    @Override
    public Object[] getCutOffReadingAndDate(final Long csIdn, final Long mmMtnid,
            final long orgid, final String flag) {
        final Query query = createQuery("select c.mmCutResDate,c.mmCutResRead from "
                + " TbWaterCutRestoration c  WHERE  c.mmMtnid=?1 and c.csIdn=?2 and c.orgId=?3 and c.mmCutResFlag=?4 order by c.mcrId desc");

        query.setParameter(1, mmMtnid);
        query.setParameter(2, csIdn);
        query.setParameter(3, orgid);
        query.setParameter(4, flag);
        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();
        if ((result != null) && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.MeterCutOffRestorationRepository#getCutOffReadingDataValues(long, long)
     */
    @Override
    public List<TbWaterCutRestoration> getCutOffReadingDataValues(final List<Long> csIdn,
            final long orgId) {
        final Query query = createQuery("select c from "
                + " TbWaterCutRestoration c  WHERE  c.csIdn in (?1) and c.orgId=?2 order by c.mcrId desc");
        query.setParameter(1, csIdn);
        query.setParameter(2, orgId);
        final List<TbWaterCutRestoration> result = query.getResultList();
        return result;
    }

	@Override
	public List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetailsOnCsIdn(Long consumerId, Long orgId,
			String cutOffResFlag) {
		final Query query = createQuery("select c.mmCutResDate,c.mmCutResRead,c.mmCutResRemark,"
                + " d.mmMtrno,d.mmOwnership from "
                + " TbWaterCutRestoration c ,TbMeterMasEntity d WHERE c.mmMtnid=d.mmMtnid and c.csIdn=?1 and c.orgId=?2 and c.mmCutResFlag=?3 ");

        query.setParameter(1, consumerId);
        query.setParameter(2, orgId);
        query.setParameter(3, cutOffResFlag);
        @SuppressWarnings("unchecked")
        final List<Object> result = query.getResultList();
        final int listSize = result.size();
        final List<MeterCutOffRestorationDTO> waterReconnectionResponseDTOs = new ArrayList<>(0);
        MeterCutOffRestorationDTO responseDTO = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

        for (int iCounter = 0; iCounter < listSize; iCounter++) {

            final Object[] obj = (Object[]) result.get(iCounter);

            responseDTO = new MeterCutOffRestorationDTO();

            if (obj[0] instanceof java.util.Date) {
                final java.util.Date time = (java.util.Date) obj[0];
                responseDTO.setCutOffDate(simpleDateFormat.format(time));
            }

            if (obj[1] != null) {
                responseDTO.setCutResRead(Long.parseLong(obj[1].toString()));
            }

            if (obj[2] != null) {
                responseDTO.setCutResRemark(obj[2].toString());
            }

            if (obj[3] != null) {
                responseDTO.setMeterNo(obj[3].toString());
            }

            if (obj[4] != null) {
                String ownerShip = MainetConstants.BLANK;
                final Organisation organisation = UserSession.getCurrent().getOrganisation();
                final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.parseLong(obj[4].toString()),
                        organisation);
                ownerShip = lookUp.getLookUpDesc();
                responseDTO.setOwnership(ownerShip);
            }
            responseDTO.setApprovedBy(UserSession.getCurrent().getEmployee().getEmpname());
            waterReconnectionResponseDTOs.add(responseDTO);

        }

        return waterReconnectionResponseDTOs;
	}

}
