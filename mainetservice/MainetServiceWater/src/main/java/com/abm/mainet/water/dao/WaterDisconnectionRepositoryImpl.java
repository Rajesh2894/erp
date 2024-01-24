package com.abm.mainet.water.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.TBWaterDisconnection;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.WaterDeconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterDisconnectionResponseDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * The Class WaterDisconnectionRepositoryImpl.
 *
 * @author Jeetendra.Pal
 */
@Repository
public class WaterDisconnectionRepositoryImpl extends AbstractDAO<TbKCsmrInfoMH>
        implements WaterDisconnectionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterDisconnectionRepositoryImpl.class);

    @Resource
    private WaterServiceMapper mapper;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.WaterDisconnectionRepository#
     * getConnectionDetails(com.abm.mainetservice.rest.water. bean.WaterDeconnectionRequestDTO)
     */
    @Override
    public WaterDisconnectionResponseDTO getConnAccessibleForDisConnectionDetails(
            final WaterDeconnectionRequestDTO requestDTO) {

        final WaterDisconnectionResponseDTO waterResponseDTO = new WaterDisconnectionResponseDTO();
        final Query query = createQuery(
                "select am  from TbKCsmrInfoMH am   WHERE am.orgId = :orgId AND am.csCcn = :connectionNo and am.csCcn is not null AND am.csIdn NOT IN(select dm.csIdn from TBWaterDisconnection dm where dm.discGrantFlag is not null)");
        query.setParameter("orgId", requestDTO.getOrgId());
        query.setParameter("connectionNo", requestDTO.getConnectionNo());

        @SuppressWarnings("unchecked")
        final List<TbKCsmrInfoMH> tbKCsmrInfoMH = query.getResultList();

        waterResponseDTO.setConnectionList(mapper.mapTbKCsmrInfoMHToCustomerInfoDTO(tbKCsmrInfoMH));

        return waterResponseDTO;
    }

    @Override
    public TbKCsmrInfoMH getApplicantDetails(final long applicationId, final long orgId) {

        Query query = createQuery(QUERY_DISCONNECTION_DETAIL);
        query.setParameter("orgId", orgId);
        query.setParameter("applicationNo", applicationId);
        final Long csIdn = (Long) query.getSingleResult();

        query = createQuery(QUERY_APPLICATION_DETAIL);
        query.setParameter("orgId", orgId);
        query.setParameter("csIdn", csIdn);
        return (TbKCsmrInfoMH) query.getSingleResult();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.WaterDisconnectionRepository#
     * saveDisconnectionDetails(com.abm.mainetservice.rest.water .bean.WaterDeconnectionRequestDTO)
     */
    @Override
    public WaterDisconnectionResponseDTO saveDisconnectionDetails(final WaterDeconnectionRequestDTO requestDTO) {

        final WaterDisconnectionResponseDTO waterResponseDTO = new WaterDisconnectionResponseDTO();

        final TBWaterDisconnection entity = mapper
                .mapWaterDisconnectionDtoTOEntity(requestDTO.getDisconnectionDto());

        entity.setOrgId(requestDTO.getOrgId());

        entity.setUserId(requestDTO.getUserId());
        entity.setLangId(requestDTO.getLangId().intValue());

        entityManager.persist(entity);
        waterResponseDTO.setApplicationNo(entity.getApmApplicationId());

        return waterResponseDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.WaterDisconnectionRepository# getApplicantionDetails(long, long)
     */
    @Override
    public TBWaterDisconnection getDisconnectionAppDetails(final long applicationId, final long orgId) {
        final Query query = createQuery(QUERY_DISCONNECTION_APPLICATION_DETAIL);
        query.setParameter("orgId", orgId);
        query.setParameter("applicationNo", applicationId);
        return (TBWaterDisconnection) query.getSingleResult();
    }

    @Override
    public boolean updateDisconnectionDetails(final TBWaterDisconnection entity) {

        entityManager.merge(entity);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterDisconnectionRepository#getDisconnectionDetails (java.lang.Long, java.lang.Long)
     */
    @Override
    public List<WaterReconnectionResponseDTO> getDisconnectionDetails(final Long userId, final Long orgId,
            final String connectionNo) {

        final Query query = createQuery("select c.csCcn,c.csName,c.trmGroup1,"
                + " c.trmGroup2,c.csCcnsize,d.csIdn,d.discMethod,d.discType,d.discAppdate,d.discReason,c.applicantType,c.csMeteredccn,c.csIdn,c.csName,c.csMname,c.csLname from "
                + " TBWaterDisconnection d,TbKCsmrInfoMH c  WHERE  d.csIdn= c.csIdn and d.orgId=?1 and c.csCcn =?2");

        // query.setParameter(1, userId);
        query.setParameter(1, orgId);
        query.setParameter(2, connectionNo);

        @SuppressWarnings("unchecked")
        final List<Object> result = query.getResultList();

        final int listSize = result.size();
        final List<WaterReconnectionResponseDTO> waterReconnectionResponseDTOs = new ArrayList<>(0);
        WaterReconnectionResponseDTO responseDTO = null;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);

        for (int iCounter = 0; iCounter < listSize; iCounter++) {

            final Object[] obj = (Object[]) result.get(iCounter);

            responseDTO = new WaterReconnectionResponseDTO();

            if (obj[0] != null) {
                responseDTO.setConnectionNo(obj[0].toString());
            }

            if (obj[1] != null) {
                responseDTO.setConsumerName(obj[1].toString());
            }

            if (obj[2] != null) {
                responseDTO.setTarrifCategoryId(Long.parseLong(obj[2].toString()));
            }

            if (obj[3] != null) {
                responseDTO.setPremiseTypeId(Long.parseLong(obj[3].toString()));
            }

            if (obj[4] != null) {
                responseDTO.setConnectionSize(Long.parseLong(obj[4].toString()));
            }

            if (obj[5] != null) {
                responseDTO.setConsumerIdNo(Long.parseLong(obj[5].toString()));
            }

            if (obj[6] != null) {
                responseDTO.setDiscMethodId(Long.parseLong(obj[6].toString()));
            }

            if (obj[7] != null) {
                responseDTO.setDiscType(Long.parseLong(obj[7].toString()));
            }

            if (obj[8] instanceof java.util.Date) {
                final java.util.Date time = (java.util.Date) obj[8];
                responseDTO.setDiscDate(simpleDateFormat.format(time));
                responseDTO.setDiscAppDate(time);
            }

            if (obj[9] != null) {
                responseDTO.setDiscRemarks(obj[9].toString());
            }
            if (obj[9] != null) {
                responseDTO.setDiscRemarks(obj[9].toString());
            }
            if (obj[10] != null) {
                responseDTO.setApplicantTypeId(Long.valueOf(obj[10].toString()));
            }
            if (obj[11] != null) {
                responseDTO.setMeterTypeId(Long.valueOf(obj[11].toString()));
            }
            if (obj[12] != null) {
                responseDTO.setCsIdn(Long.parseLong(obj[12].toString()));
            }
            String fullName = MainetConstants.CommonConstants.BLANK;
            if (obj[13] != null) {
                fullName += obj[13].toString();
            }
            if (obj[14] != null) {
                fullName = fullName + MainetConstants.CommonConstants.BLANK + obj[14].toString();
            }
            if (obj[15] != null) {
                fullName = fullName + MainetConstants.CommonConstants.BLANK + obj[15].toString();
            }
            responseDTO.setConsumerName(fullName);

            waterReconnectionResponseDTOs.add(responseDTO);

        }

        return waterReconnectionResponseDTOs;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterDisconnectionRepository#getDisconnectionDate( java.lang.Long, long)
     */
    @Override
    public Date getDisconnectionDate(final Long csIdn, final long orgid) {
        try {
            final Query query = createQuery("select  d.discAppdate from "
                    + " TBWaterDisconnection d  WHERE  d.csIdn=?1 and  and d.orgId=?2 order by d.discId desc");
            query.setParameter(1, csIdn);
            query.setParameter(2, orgid);
            final List<Date> result = query.getResultList();
            if ((result != null) && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (final NoResultException e) {
            LOGGER.error("No disconnection date for csIdn: " + csIdn, e);
        }
        return null;
    }

    @Override
    public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
        Long plumId = null;
        try {
            final Query query = entityManager.createQuery(
                    "SELECT plumId  FROM TBWaterDisconnection c WHERE c.apmApplicationId=? and c.orgId=?");
            query.setParameter(1, applicationId);
            query.setParameter(2, orgId);
            plumId = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
        return plumId;
    }

    @Override
    public TBWaterDisconnection getDisconnectionAppDetailsByCsIdn(final long csIdn, final long orgId) {
        final Query query = createQuery(QUERY_DISCONNECTION_APPLICATION_DETAILByCsIdn);
        query.setParameter("orgId", orgId);
        query.setParameter("csIdn", csIdn);
        return (TBWaterDisconnection) query.getSingleResult();
    }

    @Override
    public WaterDisconnectionResponseDTO getConnForDisConnectionDetails(
            final WaterDeconnectionRequestDTO requestDTO) {

        final WaterDisconnectionResponseDTO waterResponseDTO = new WaterDisconnectionResponseDTO();
        final Query query = createQuery(
                "select am  from TbKCsmrInfoMH am   WHERE am.orgId = :orgId AND am.csCcn = :connectionNo and am.csCcn is not null");
        query.setParameter("orgId", requestDTO.getOrgId());
        query.setParameter("connectionNo", requestDTO.getConnectionNo());

        @SuppressWarnings("unchecked")
        final List<TbKCsmrInfoMH> tbKCsmrInfoMH = query.getResultList();

        waterResponseDTO.setConnectionList(mapper.mapTbKCsmrInfoMHToCustomerInfoDTO(tbKCsmrInfoMH));

        return waterResponseDTO;
    }

}
