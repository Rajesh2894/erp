package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.GridPaginationUtility;
import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TBCsmrrCmdMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.WaterServiceMapper;

/**
 * @author deepika.pimpale
 *
 */
@Repository
public class NewWaterRepositoryImpl extends AbstractDAO<TbKCsmrInfoMH> implements NewWaterRepository {

    final Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private WaterServiceMapper waterServiceMapper;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.WaterRepository#saveCsmrInfo(com
     * .abm.mainetservice.rest.water.entity.TbKCsmrInfoMH)
     */
    @Override
    public void saveCsmrInfo(final TbKCsmrInfoMH csmrInfo) {

        entityManager.persist(csmrInfo);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository#getVerifiedPlumberId (java.lang.String)
     */
    @Override
    public PlumberMaster getVerifiedPlumberId(final String plumNo) {
        PlumberMaster plumber = null;
        try {
            final Query query = entityManager.createQuery(
                    "SELECT m FROM PlumberMaster m,PlumberRenewalRegisterMaster r WHERE m.plumId=r.plum_id and m.plumLicNo =? AND ?  between m.plumLicFromDate and m.plumLicToDate or ? between r.rn_fromdate and r.rn_todate");
            query.setParameter(1, plumNo);
            query.setParameter(2, new Date());
            query.setParameter(3, new Date());
            plumber = (PlumberMaster) query.getSingleResult();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return plumber;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository# getApplicantInformationById(long)
     */
    @Override
    public TbKCsmrInfoMH getApplicantInformationById(final long applicationId, final long orgId) {
        try {
            final Query query = entityManager
                    .createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.applicationNo=? and c.orgId=?");
            query.setParameter(1, applicationId);
            query.setParameter(2, orgId);
            final TbKCsmrInfoMH master = (TbKCsmrInfoMH) query.getSingleResult();
            master.getOwnerList();
            master.getLinkDetails();
            return master;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository#updateCsmrInfo
     * (com.abm.mainetservice.rest.water.entity.TbKCsmrInfoMH)
     */
    @Override
    public void updateCsmrInfo(final TbKCsmrInfoMH master) {

        entityManager.merge(master);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository#getAvgConsumtionById (java.lang.Long, long)
     */
    @Override
    public Long getAvgConsumtionById(final Long instId, final long orgid) {
        // TODO Auto-generated method stub
        final Query query = entityManager
                .createQuery("SELECT i.instLitPerDay FROM InstituteEntity i " + "WHERE i.instId =?  AND i.orgid=?");

        query.setParameter(1, instId);
        query.setParameter(2, orgid);
        final Long average = (Long) query.getSingleResult();
        return average;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository#getSlopeValueByLength (java.lang.Long, long)
     */
    @Override
    public Double getSlopeValueByLength(final Long rcLength, final Organisation org) {

        final Query query = entityManager.createQuery(
                "SELECT s.spValue FROM SlopeMaster s " + "WHERE ? between s.spFrom AND s.spTo AND s.orgId=?");

        query.setParameter(1, rcLength);
        query.setParameter(2, org);
        final Double slopeValue = (Double) query.getSingleResult();
        return slopeValue;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.rest.water.dao.NewWaterRepository#getDiameteSlab (java.lang.Double,
     * com.abm.mainet.common.domain.Organisation)
     */
    @Override
    public Double getDiameteSlab(final Double diameter, final Organisation organisation) {
        final Query query = entityManager.createQuery(
                "SELECT d.cnsValue FROM DiameterEntity d " + "WHERE ? between d.cnsFrom AND d.cnsTo AND d.orgId=?");

        query.setParameter(1, diameter);
        query.setParameter(2, organisation);
        final Double diameterSlab = (Double) query.getSingleResult();
        return diameterSlab;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# findWaterRecordsForMeterReading
     * (com.abm.mainet.water.dto.MeterReadingDTO)
     */
    @Override
    public List<Object[]> findWaterRecordsForMeterReading(final MeterReadingDTO entityDTO, final Long meteredtype,
            final Date endDate) {

        final StringBuilder queryString = new StringBuilder(
                QueryConstants.WATER_MODULE_QUERY.METER_READING.METER_READING_DATAMAX);
        if ((entityDTO.getMeterType() != null) && entityDTO.getMeterType().equals(MainetConstants.FlagS)) {
            queryString.append(" ) ");
            queryString.append(" and s.csCcn=:csCcn ");
        }

        else {
            if (endDate != null) {
                queryString.append(" and d.mrdTo < :endDate ) ");
            } else {
                queryString.append(" ) ");
            }

            if ((entityDTO.getWardZone1() != null) && (entityDTO.getWardZone1() > 0)) {
                queryString.append(" and s.codDwzid1=:codDwzid1 ");
            }
            if ((entityDTO.getWardZone2() != null) && (entityDTO.getWardZone2() > 0)) {
                queryString.append(" and s.codDwzid2=:codDwzid2 ");
            }
            if ((entityDTO.getWardZone3() != null) && (entityDTO.getWardZone3() > 0)) {
                queryString.append(" and s.codDwzid3=:codDwzid3 ");
            }
            if ((entityDTO.getWardZone4() != null) && (entityDTO.getWardZone4() > 0)) {
                queryString.append(" and s.codDwzid4=:codDwzid4 ");
            }
            if ((entityDTO.getWardZone5() != null) && (entityDTO.getWardZone5() > 0)) {
                queryString.append(" and s.codDwzid5=:codDwzid5 ");
            }

            if ((entityDTO.getTariff1() != null) && (entityDTO.getTariff1() > 0)) {
                queryString.append(" and s.trmGroup1=:trmGroup1 ");
            }
            if ((entityDTO.getTariff2() != null) && (entityDTO.getTariff2() > 0)) {
                queryString.append(" and s.trmGroup2=:trmGroup2 ");
            }
            if ((entityDTO.getTariff3() != null) && (entityDTO.getTariff3() > 0)) {
                queryString.append(" and s.trmGroup3=:trmGroup3 ");
            }
            if ((entityDTO.getTariff4() != null) && (entityDTO.getTariff4() > 0)) {
                queryString.append(" and s.trmGroup4=:trmGroup4 ");
            }
            if ((entityDTO.getTariff5() != null) && (entityDTO.getTariff5() > 0)) {
                queryString.append(" and s.trmGroup5=:trmGroup5 ");
            }
            if ((entityDTO.getConSize() != null) && (entityDTO.getConSize() > 0)) {
                queryString.append(" and s.csCcnsize=:csCcnsize ");
            }
        }
        final Query query = entityManager.createQuery(queryString.toString());

        query.setParameter("orgId", entityDTO.getOrgid());
        query.setParameter("csMeteredccn", meteredtype);

        if ((entityDTO.getMeterType() != null) && entityDTO.getMeterType().equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", entityDTO.getCsCcn());
        } else {
            if (endDate != null) {
                query.setParameter("endDate", endDate);
            }
            if ((entityDTO.getWardZone1() != null) && (entityDTO.getWardZone1() > 0)) {
                query.setParameter("codDwzid1", entityDTO.getWardZone1());
            }
            if ((entityDTO.getWardZone2() != null) && (entityDTO.getWardZone2() > 0)) {
                query.setParameter("codDwzid2", entityDTO.getWardZone2());
            }
            if ((entityDTO.getWardZone3() != null) && (entityDTO.getWardZone3() > 0)) {
                query.setParameter("codDwzid3", entityDTO.getWardZone3());
            }
            if ((entityDTO.getWardZone4() != null) && (entityDTO.getWardZone4() > 0)) {
                query.setParameter("codDwzid4", entityDTO.getWardZone4());
            }
            if ((entityDTO.getWardZone5() != null) && (entityDTO.getWardZone5() > 0)) {
                query.setParameter("codDwzid5", entityDTO.getWardZone5());
            }

            if ((entityDTO.getTariff1() != null) && (entityDTO.getTariff1() > 0)) {
                query.setParameter("trmGroup1", entityDTO.getTariff1());
            }
            if ((entityDTO.getTariff2() != null) && (entityDTO.getTariff2() > 0)) {
                query.setParameter("trmGroup2", entityDTO.getTariff2());
            }
            if ((entityDTO.getTariff3() != null) && (entityDTO.getTariff3() > 0)) {
                query.setParameter("trmGroup3", entityDTO.getTariff3());
            }
            if ((entityDTO.getTariff4() != null) && (entityDTO.getTariff4() > 0)) {
                query.setParameter("trmGroup4", entityDTO.getTariff4());
            }
            if ((entityDTO.getTariff5() != null) && (entityDTO.getTariff5() > 0)) {
                query.setParameter("trmGroup5", entityDTO.getTariff5());
            }
            if ((entityDTO.getConSize() != null) && (entityDTO.getConSize() > 0)) {
                query.setParameter("csCcnsize", entityDTO.getConSize());
            }
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();

        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# findNewWaterRecordsForMeterReading
     * (com.abm.mainet.water.dto.MeterReadingDTO)
     */
    @Override
    public List<Object[]> findNewWaterRecordsForMeterReading(final MeterReadingDTO entityDTO, final Long meteredtype) {

        final StringBuilder queryString = new StringBuilder(
                QueryConstants.WATER_MODULE_QUERY.METER_READING.NEW_METER_READING_DATAMAX);
        if ((entityDTO.getMeterType() != null) && entityDTO.getMeterType().equals(MainetConstants.FlagS)) {
            queryString.append(" and s.csCcn=:csCcn ");
        } else {
            if ((entityDTO.getWardZone1() != null) && (entityDTO.getWardZone1() > 0)) {
                queryString.append(" and s.codDwzid1=:codDwzid1 ");
            }
            if ((entityDTO.getWardZone2() != null) && (entityDTO.getWardZone2() > 0)) {
                queryString.append(" and s.codDwzid2=:codDwzid2 ");
            }
            if ((entityDTO.getWardZone3() != null) && (entityDTO.getWardZone3() > 0)) {
                queryString.append(" and s.codDwzid3=:codDwzid3 ");
            }
            if ((entityDTO.getWardZone4() != null) && (entityDTO.getWardZone4() > 0)) {
                queryString.append(" and s.codDwzid4=:codDwzid4 ");
            }
            if ((entityDTO.getWardZone5() != null) && (entityDTO.getWardZone5() > 0)) {
                queryString.append(" and s.codDwzid5=:codDwzid5 ");
            }

            if ((entityDTO.getTariff1() != null) && (entityDTO.getTariff1() > 0)) {
                queryString.append(" and s.trmGroup1=:trmGroup1 ");
            }
            if ((entityDTO.getTariff2() != null) && (entityDTO.getTariff2() > 0)) {
                queryString.append(" and s.trmGroup2=:trmGroup2 ");
            }
            if ((entityDTO.getTariff3() != null) && (entityDTO.getTariff3() > 0)) {
                queryString.append(" and s.trmGroup3=:trmGroup3 ");
            }
            if ((entityDTO.getTariff4() != null) && (entityDTO.getTariff4() > 0)) {
                queryString.append(" and s.trmGroup4=:trmGroup4 ");
            }
            if ((entityDTO.getTariff5() != null) && (entityDTO.getTariff5() > 0)) {
                queryString.append(" and s.trmGroup5=:trmGroup5 ");
            }
            if ((entityDTO.getConSize() != null) && (entityDTO.getConSize() > 0)) {
                queryString.append(" and s.csCcnsize=:csCcnsize ");
            }
        }

        final Query query = entityManager.createQuery(queryString.toString());

        query.setParameter("orgId", entityDTO.getOrgid());
        query.setParameter("csMeteredccn", meteredtype);

        if ((entityDTO.getMeterType() != null) && entityDTO.getMeterType().equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", entityDTO.getCsCcn());
        } else {
            if ((entityDTO.getWardZone1() != null) && (entityDTO.getWardZone1() > 0)) {
                query.setParameter("codDwzid1", entityDTO.getWardZone1());
            }
            if ((entityDTO.getWardZone2() != null) && (entityDTO.getWardZone2() > 0)) {
                query.setParameter("codDwzid2", entityDTO.getWardZone2());
            }
            if ((entityDTO.getWardZone3() != null) && (entityDTO.getWardZone3() > 0)) {
                query.setParameter("codDwzid3", entityDTO.getWardZone3());
            }
            if ((entityDTO.getWardZone4() != null) && (entityDTO.getWardZone4() > 0)) {
                query.setParameter("codDwzid4", entityDTO.getWardZone4());
            }
            if ((entityDTO.getWardZone5() != null) && (entityDTO.getWardZone5() > 0)) {
                query.setParameter("codDwzid5", entityDTO.getWardZone5());
            }

            if ((entityDTO.getTariff1() != null) && (entityDTO.getTariff1() > 0)) {
                query.setParameter("trmGroup1", entityDTO.getTariff1());
            }
            if ((entityDTO.getTariff2() != null) && (entityDTO.getTariff2() > 0)) {
                query.setParameter("trmGroup2", entityDTO.getTariff2());
            }
            if ((entityDTO.getTariff3() != null) && (entityDTO.getTariff3() > 0)) {
                query.setParameter("trmGroup3", entityDTO.getTariff3());
            }
            if ((entityDTO.getTariff4() != null) && (entityDTO.getTariff4() > 0)) {
                query.setParameter("trmGroup4", entityDTO.getTariff4());
            }
            if ((entityDTO.getTariff5() != null) && (entityDTO.getTariff5() > 0)) {
                query.setParameter("trmGroup5", entityDTO.getTariff5());
            }
            if ((entityDTO.getConSize() != null) && (entityDTO.getConSize() > 0)) {
                query.setParameter("csCcnsize", entityDTO.getConSize());
            }
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();

        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# getWaterConnectionDetailsById(java.lang.Long, java.lang.Long)
     */
    @Override
    public TbKCsmrInfoMH getWaterConnectionDetailsById(final Long consumerIdNo, final Long orgId) {

        final Query query = entityManager.createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csIdn=?1 and c.orgId=?2 ");
        query.setParameter(1, consumerIdNo);
        query.setParameter(2, orgId);
        final TbKCsmrInfoMH master = (TbKCsmrInfoMH) query.getSingleResult();
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository#getRoadListById(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TbWtCsmrRoadTypes> getRoadListById(final TbKCsmrInfoMH master) {
        List<TbWtCsmrRoadTypes> roadList = null;
        final Query query = entityManager.createQuery("SELECT r FROM TbWtCsmrRoadTypes r " + "WHERE  r.crtCsIdn =?");

        query.setParameter(1, master);
        roadList = query.getResultList();
        return roadList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# getReconnectionRoadDiggingListByAppId(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TbWtCsmrRoadTypes> getReconnectionRoadDiggingListByAppId(final Long applicationId, final Long orgId) {

        List<TbWtCsmrRoadTypes> roadList = null;
        final Query query = entityManager.createQuery("SELECT r FROM TbWtCsmrRoadTypes r "
                + "WHERE  r.apmApplicationId =? AND r.orgId.orgid =? AND r.isDeleted =? ");

        query.setParameter(1, applicationId);
        query.setParameter(2, orgId);
        query.setParameter(3, MainetConstants.RnLCommon.N_FLAG);

        roadList = query.getResultList();

        return roadList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# saveReconnectionRoadDiggingDetails(java.util.List)
     */
    @Override
    public void saveReconnectionRoadDiggingDetails(final List<TbWtCsmrRoadTypes> csmrRoadTypesEntity) {

        for (final TbWtCsmrRoadTypes roadTypes : csmrRoadTypesEntity) {
            entityManager.merge(roadTypes);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository#getwaterRecordsForBill (com.abm.mainet.water.dto.TbCsmrInfoDTO)
     */
    @Override
    public List<TbKCsmrInfoMH> getwaterRecordsForBill(final TbCsmrInfoDTO entity, final String contype) {
        final Organisation org = new Organisation();
        org.setOrgid(entity.getOrgId());
        String meterType = null;
        String queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_RECORD;
        if (contype.equals(MainetConstants.NewWaterServiceConstants.DUE_DATE_Meter)) {
            meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getCsMeteredccn(), org)
                    .getLookUpCode();
            if (MainetConstants.NewWaterServiceConstants.METER.equals(meterType)) {
                queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_METER_QUERY;
            } else {
                queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_NONMETER_QUERY;
            }
        }
        StringBuilder queryString = new StringBuilder(queryData);

        if ((contype != null) && contype.equals(MainetConstants.FlagS)) {
            queryString.append(" and w.csCcn=:csCcn ");
        } else {
            if ((entity.getCodDwzid1() != null) && (entity.getCodDwzid1() > 0)) {
                queryString.append(" and w.codDwzid1=:codDwzid1 ");
            }
            if ((entity.getCodDwzid2() != null) && (entity.getCodDwzid2() > 0)) {
                queryString.append(" and w.codDwzid2=:codDwzid2 ");
            }
            if ((entity.getCodDwzid3() != null) && (entity.getCodDwzid3() > 0)) {
                queryString.append(" and w.codDwzid3=:codDwzid3 ");
            }
            if ((entity.getCodDwzid4() != null) && (entity.getCodDwzid4() > 0)) {
                queryString.append(" and w.codDwzid4=:codDwzid4 ");
            }
            if ((entity.getCodDwzid5() != null) && (entity.getCodDwzid5() > 0)) {
                queryString.append(" and w.codDwzid5=:codDwzid5 ");
            }
            if ((entity.getTrmGroup1() != null) && (entity.getTrmGroup1() > 0)) {
                queryString.append(" and w.trmGroup1=:trmGroup1 ");
            }
            if ((entity.getTrmGroup2() != null) && (entity.getTrmGroup2() > 0)) {
                queryString.append(" and w.trmGroup2=:trmGroup2 ");
            }
            if ((entity.getTrmGroup3() != null) && (entity.getTrmGroup3() > 0)) {
                queryString.append(" and w.trmGroup3=:trmGroup3 ");
            }
            if ((entity.getTrmGroup4() != null) && (entity.getTrmGroup4() > 0)) {
                queryString.append(" and w.trmGroup4=:trmGroup4 ");
            }
            if ((entity.getTrmGroup5() != null) && (entity.getTrmGroup5() > 0)) {
                queryString.append(" and w.trmGroup5=:trmGroup5 ");
            }
            if ((entity.getCsCcnsize() != null) && (entity.getCsCcnsize() > 0)) {
                queryString.append(" and w.csCcnsize=:csCcnsize ");
            }
            if ((entity.getCsMeteredccn() != null) && (entity.getCsMeteredccn() > 0)) {
                queryString.append(" and w.csMeteredccn=:csMeteredccn ");
            }
        }

        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", entity.getOrgId());
        if ((contype != null) && contype.equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", entity.getCsCcn());
        } else {
            if ((entity.getCodDwzid1() != null) && (entity.getCodDwzid1() > 0)) {
                query.setParameter("codDwzid1", entity.getCodDwzid1());
            }
            if ((entity.getCodDwzid2() != null) && (entity.getCodDwzid2() > 0)) {
                query.setParameter("codDwzid2", entity.getCodDwzid2());
            }
            if ((entity.getCodDwzid3() != null) && (entity.getCodDwzid3() > 0)) {
                query.setParameter("codDwzid3", entity.getCodDwzid3());
            }
            if ((entity.getCodDwzid4() != null) && (entity.getCodDwzid4() > 0)) {
                query.setParameter("codDwzid4", entity.getCodDwzid4());
            }
            if ((entity.getCodDwzid5() != null) && (entity.getCodDwzid5() > 0)) {
                query.setParameter("codDwzid5", entity.getCodDwzid5());
            }
            if ((entity.getTrmGroup1() != null) && (entity.getTrmGroup1() > 0)) {
                query.setParameter("trmGroup1", entity.getTrmGroup1());
            }
            if ((entity.getTrmGroup2() != null) && (entity.getTrmGroup2() > 0)) {
                query.setParameter("trmGroup2", entity.getTrmGroup2());
            }
            if ((entity.getTrmGroup3() != null) && (entity.getTrmGroup3() > 0)) {
                query.setParameter("trmGroup3", entity.getTrmGroup3());
            }
            if ((entity.getTrmGroup4() != null) && (entity.getTrmGroup4() > 0)) {
                query.setParameter("trmGroup4", entity.getTrmGroup4());
            }
            if ((entity.getTrmGroup5() != null) && (entity.getTrmGroup5() > 0)) {
                query.setParameter("trmGroup5", entity.getTrmGroup5());
            }
            if ((entity.getCsCcnsize() != null) && (entity.getCsCcnsize() > 0)) {
                query.setParameter("csCcnsize", entity.getCsCcnsize());
            }
            if ((entity.getCsMeteredccn() != null) && (entity.getCsMeteredccn() > 0)) {
                query.setParameter("csMeteredccn", entity.getCsMeteredccn());
            }
            if (MainetConstants.NewWaterServiceConstants.NON_METER.equals(meterType)) {
                query.setParameter("currDate", new Date());
            }
        }
        @SuppressWarnings("unchecked")
        List<TbKCsmrInfoMH> result = query.getResultList();
        if (contype.equals(MainetConstants.FlagS) && (result != null) && !result.isEmpty()) {
            final TbKCsmrInfoMH water = result.get(0);
            meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(water.getCsMeteredccn(), org)
                    .getLookUpCode();
            if (MainetConstants.NewWaterServiceConstants.METER.equals(meterType)) {
                queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_METER_QUERY;
            } else {
                queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_NONMETER_QUERY;
            }
            queryString = new StringBuilder(queryData);
            queryString.append(" and w.csCcn=" + "'" + water.getCsCcn() + "'");
            final Query query1 = entityManager.createQuery(queryString.toString());
            query1.setParameter("orgId", entity.getOrgId());
            if (MainetConstants.NewWaterServiceConstants.NON_METER.equals(meterType)) {
                query1.setParameter("currDate", new Date());
            }
            result = query1.getResultList();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# getBillPrintingSearchDetail (com.abm.mainet.water.dto.TbCsmrInfoDTO,
     * java.lang.String)
     */
    @Override
    public List<Object[]> getBillPrintingSearchDetail(final TbCsmrInfoDTO entity, final String conType) {
        final StringBuilder queryString = new StringBuilder(
                QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.BILL_PRINTING);
        if ((conType != null) && !conType.equals(MainetConstants.FlagS)) {
            if ((entity.getCodDwzid1() != null) && (entity.getCodDwzid1() > 0)) {
                queryString.append(" and w.codDwzid1=:codDwzid1 ");
            }
            if ((entity.getCodDwzid2() != null) && (entity.getCodDwzid2() > 0)) {
                queryString.append(" and w.codDwzid2=:codDwzid2 ");
            }
            if ((entity.getCodDwzid3() != null) && (entity.getCodDwzid3() > 0)) {
                queryString.append(" and w.codDwzid3=:codDwzid3 ");
            }
            if ((entity.getCodDwzid4() != null) && (entity.getCodDwzid4() > 0)) {
                queryString.append(" and w.codDwzid4=:codDwzid4 ");
            }
            if ((entity.getCodDwzid5() != null) && (entity.getCodDwzid5() > 0)) {
                queryString.append(" and w.codDwzid5=:codDwzid5 ");
            }
            if ((entity.getCsMeteredccn() != null) && (entity.getCsMeteredccn() > 0)) {
                queryString.append(" and w.csMeteredccn=:csMeteredccn ");
            }
            if (entity.getCsFromdt() != null) {
                queryString.append(" and b.bmBilldt >=:fromDate ");
            }
            if (entity.getCsTodt() != null) {
                queryString.append(" and b.bmBilldt <=:toDate ");
            }
        }
        if ((conType != null) && conType.equals(MainetConstants.FlagS)) {
            queryString.append(" and w.csCcn=:csCcn ");
        }

        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", entity.getOrgId());
        if ((conType != null) && !conType.equals(MainetConstants.FlagS)) {
            if ((entity.getCodDwzid1() != null) && (entity.getCodDwzid1() > 0)) {
                query.setParameter("codDwzid1", entity.getCodDwzid1());
            }
            if ((entity.getCodDwzid2() != null) && (entity.getCodDwzid2() > 0)) {
                query.setParameter("codDwzid2", entity.getCodDwzid2());
            }
            if ((entity.getCodDwzid3() != null) && (entity.getCodDwzid3() > 0)) {
                query.setParameter("codDwzid3", entity.getCodDwzid3());
            }
            if ((entity.getCodDwzid4() != null) && (entity.getCodDwzid4() > 0)) {
                query.setParameter("codDwzid4", entity.getCodDwzid4());
            }
            if ((entity.getCodDwzid5() != null) && (entity.getCodDwzid5() > 0)) {
                query.setParameter("codDwzid5", entity.getCodDwzid5());
            }
            if ((entity.getCsMeteredccn() != null) && (entity.getCsMeteredccn() > 0)) {
                query.setParameter("csMeteredccn", entity.getCsMeteredccn());
            }
            if (entity.getCsFromdt() != null) {
                query.setParameter("fromDate", entity.getCsFromdt());
            }
            if (entity.getCsTodt() != null) {
                query.setParameter("toDate", entity.getCsTodt());
            }
        }
        if ((conType != null) && conType.equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", entity.getCsCcn());
        }

        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository# getWaterConnectionDetailsConNumber(java.lang.String, long)
     */
    @Override
    public TbKCsmrInfoMH getWaterConnectionDetailsConNumber(final String ccnNumber, final long orgid) {
        TbKCsmrInfoMH master = null;

        final Query query = entityManager.createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csCcn=? and c.orgId=?");
        query.setParameter(1, ccnNumber);
        query.setParameter(2, orgid);
        master = (TbKCsmrInfoMH) query.getSingleResult();

        return master;
    }

    @Override
    public TbKCsmrInfoMH fetchConnectionDetails(final String csCcn, final Long orgid, final String active) {
        TbKCsmrInfoMH master = null;

        try {
            final Query query = entityManager.createQuery(
                    "SELECT c FROM TbKCsmrInfoMH c WHERE c.conActive is null and c.csCcn=? and c.orgId=? and c.csIsBillingActive=?");
            query.setParameter(1, csCcn);
            query.setParameter(2, orgid);
            query.setParameter(3, active);
            master = (TbKCsmrInfoMH) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception infetchConnectionDetails " + e);
        }
        return master;
    }

    @Override
    public List<Object[]> fetchBillsForDistributionEntry(final WardZoneBlockDTO entity, final String billCcnType,
            final Long distriutionType, final String connectionNo, final long orgId) {
        final StringBuilder queryString = new StringBuilder(
                QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.BILL_PRINTING);
        queryString.append(" and b.bmDuedate is null ");

        if ((billCcnType != null) && billCcnType.equals(MainetConstants.FlagS)) {
            queryString.append(" and w.csCcn=:csCcn ");
        } else {
            if (entity.getAreaDivision1() > 0) {
                queryString.append(" and w.codDwzid1=:codDwzid1 ");
            }
            if (entity.getAreaDivision2() > 0) {
                queryString.append(" and w.codDwzid2=:codDwzid2 ");
            }
            if (entity.getAreaDivision3() > 0) {
                queryString.append(" and w.codDwzid3=:codDwzid3 ");
            }
            if (entity.getAreaDivision4() > 0) {
                queryString.append(" and w.codDwzid4=:codDwzid4 ");
            }
            if (entity.getAreaDivision5() > 0) {
                queryString.append(" and w.codDwzid5=:codDwzid5 ");
            }
        }
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        if ((billCcnType != null) && billCcnType.equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", connectionNo);
        } else {
            if (entity.getAreaDivision1() > 0) {
                query.setParameter("codDwzid1", entity.getAreaDivision1());
            }
            if (entity.getAreaDivision2() > 0) {
                query.setParameter("codDwzid2", entity.getAreaDivision2());
            }
            if (entity.getAreaDivision3() > 0) {
                query.setParameter("codDwzid3", entity.getAreaDivision3());
            }
            if (entity.getAreaDivision4() > 0) {
                query.setParameter("codDwzid4", entity.getAreaDivision4());
            }
            if (entity.getAreaDivision5() > 0) {
                query.setParameter("codDwzid5", entity.getAreaDivision5());
            }
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();
        return result;
    }

    @Override
    public List<Object[]> fetchNoticesForDistributionEntry(final WardZoneBlockDTO entity, final String billCcnType,
            final String connectionNo, final long orgId, final Long noticeType) {
        final StringBuilder queryString = new StringBuilder(
                QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.NOTICE_DISTRIBUTION);

        if ((billCcnType != null) && billCcnType.equals(MainetConstants.FlagS)) {
            queryString.append(" and w.csCcn=:csCcn ");
        } else {
            if (entity.getAreaDivision1() > 0) {
                queryString.append(" and w.codDwzid1=:codDwzid1 ");
            }
            if (entity.getAreaDivision2() > 0) {
                queryString.append(" and w.codDwzid2=:codDwzid2 ");
            }
            if (entity.getAreaDivision3() > 0) {
                queryString.append(" and w.codDwzid3=:codDwzid3 ");
            }
            if (entity.getAreaDivision4() > 0) {
                queryString.append(" and w.codDwzid4=:codDwzid4 ");
            }
            if (entity.getAreaDivision5() > 0) {
                queryString.append(" and w.codDwzid5=:codDwzid5 ");
            }
        }
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("cpdNottype", noticeType);
        if ((billCcnType != null) && billCcnType.equals(MainetConstants.FlagS)) {
            query.setParameter("csCcn", connectionNo);
        } else {
            if (entity.getAreaDivision1() > 0) {
                query.setParameter("codDwzid1", entity.getAreaDivision1());
            }
            if (entity.getAreaDivision2() > 0) {
                query.setParameter("codDwzid2", entity.getAreaDivision2());
            }
            if (entity.getAreaDivision3() > 0) {
                query.setParameter("codDwzid3", entity.getAreaDivision3());
            }
            if (entity.getAreaDivision4() > 0) {
                query.setParameter("codDwzid4", entity.getAreaDivision4());
            }
            if (entity.getAreaDivision5() > 0) {
                query.setParameter("codDwzid5", entity.getAreaDivision5());
            }
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> result = query.getResultList();
        return result;
    }

    @Override
    public TbKCsmrInfoMH fetchConnectionDetailsByConnNo(String csCcn, Long orgid) {
        TbKCsmrInfoMH master = null;

        final Query query = entityManager.createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csCcn=? and c.orgId=?");
        query.setParameter(1, csCcn);
        query.setParameter(2, orgid);
        try {
            master = (TbKCsmrInfoMH) query.getSingleResult();
        } catch (Exception ex) {
            logger.warn("exception fetchConnectionDetailsByConnNo() " + ex);
        }

        return master;
    }

    @Override
    public TbKCsmrInfoMH fetchConnectionByCsIdn(Long csIdn, Long orgid) {
        TbKCsmrInfoMH master = null;
        final Query query = entityManager.createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csIdn=? and c.orgId=?");
        query.setParameter(1, csIdn);
        query.setParameter(2, orgid);
        try {
            master = (TbKCsmrInfoMH) query.getSingleResult();
        } catch (Exception ex) {
            logger.warn("exception fetchConnectionByCsIdn() " + ex);
        }
        return master;
    }

    @Override
    public TbKCsmrInfoMH fetchConnectionFromOldCcnNumber(String csOldccn, long orgid) {
        try {
            final Query query = entityManager
                    .createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csOldccn=? and c.orgId=?");
            query.setParameter(1, csOldccn);
            query.setParameter(2, orgid);
            return (TbKCsmrInfoMH) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;
    }

    @Override
    public List<TbKCsmrInfoMH> getAllConnectionMasterForBillSchedulerMeter(long orgid) {

        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        Long meterType = CommonMasterUtility.getValueFromPrefixLookUp("MTR", "WMN", org).getLookUpId();
        String queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_METER_QUERY;
        StringBuilder queryString = new StringBuilder(queryData);
        queryString.append(" and w.csMeteredccn=:csMeteredccn ");
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", orgid);
        query.setParameter("csMeteredccn", meterType);
        @SuppressWarnings("unchecked")
        List<TbKCsmrInfoMH> result = query.getResultList();
        return result;

    }

    @Override
    public List<TbKCsmrInfoMH> getAllConnectionMasterForBillSchedulerNonMeter(long orgid) {

        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        Long meterType = CommonMasterUtility.getValueFromPrefixLookUp("NMR", "WMN", org).getLookUpId();
        String queryData = QueryConstants.WATER_MODULE_QUERY.WATER_TABLE_CSMRINFO.BILLING_NONMETER_QUERY;
        StringBuilder queryString = new StringBuilder(queryData);
        queryString.append(" and w.csMeteredccn=:csMeteredccn ");
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("orgId", orgid);
        query.setParameter("csMeteredccn", meterType);
        query.setParameter("currDate", new Date());
        @SuppressWarnings("unchecked")
        List<TbKCsmrInfoMH> result = query.getResultList();
        return result;

    }

    @Override
    public int getTotalSearchCount(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder("from TbKCsmrInfoMH csmr where csmr.orgId=:orgId");
        if (gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.WaterConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }
        if (!StringUtils.isEmpty(searchDTO.getPropertyNo())) {
            queryString.append(" and csmr.propertyNo=:propertyNo ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsCcn())) {
            queryString.append(" and csmr.csCcn=:csCcn ");
        }
        if (searchDTO.getLocId() != null && searchDTO.getLocId() > 0) {
            queryString.append(" and csmr.loccationId=:loccationId ");
        }
        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            queryString.append(" and csmr.codDwzid1=:codDwzid1 ");
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            queryString.append(" and csmr.codDwzid2=:codDwzid2 ");
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            queryString.append(" and csmr.codDwzid3=:codDwzid3 ");
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            queryString.append(" and csmr.codDwzid4=:codDwzid4 ");
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            queryString.append(" and csmr.codDwzid5=:codDwzid5 ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsName())) {
            queryString.append(" and csmr.csName=:csName ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsContactno())) {
            queryString.append(" and csmr.csContactno=:csContactno ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsOldccn())) {
            queryString.append(" and csmr.csOldccn=:csOldccn ");
        }

        referenceNumber = "csmr.";

        GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                MainetConstants.WaterConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "csmr.");
        final Query query = createQuery(queryString.toString());
        query.setParameter("orgId", searchDTO.getOrgId());
        if (!StringUtils.isEmpty(searchDTO.getPropertyNo())) {
            query.setParameter("propertyNo", searchDTO.getPropertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDTO.getCsCcn())) {
            query.setParameter("csCcn", searchDTO.getCsCcn().trim());
        }
        if (searchDTO.getLocId() != null && searchDTO.getLocId() > 0) {
            query.setParameter("loccationId", searchDTO.getLocId());
        }
        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            query.setParameter("codDwzid1", searchDTO.getCodDwzid1());
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            query.setParameter("codDwzid2", searchDTO.getCodDwzid2());
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            query.setParameter("codDwzid3", searchDTO.getCodDwzid3());
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            query.setParameter("codDwzid4", searchDTO.getCodDwzid4());
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            query.setParameter("codDwzid5", searchDTO.getCodDwzid5());
        }
        if (!StringUtils.isEmpty(searchDTO.getCsName())) {
            query.setParameter("csName", searchDTO.getCsName().trim());
        }
		if (!StringUtils.isEmpty(searchDTO.getCsContactno())) {
			query.setParameter("csContactno", searchDTO.getCsContactno().trim());
		}
		if (!StringUtils.isEmpty(searchDTO.getCsOldccn())) {
			query.setParameter("csOldccn", searchDTO.getCsOldccn().trim());
		}
        return query.getResultList().size();
    }

    @Override
    public List<TbKCsmrInfoMH> searchConnectionForView(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO) {
        String coloumnName = null;
        String referenceNumber = null;
        StringBuilder queryString = new StringBuilder("from TbKCsmrInfoMH csmr where csmr.conActive is null and csmr.orgId=:orgId");

        if (gridSearchDTO != null && gridSearchDTO.getSearchField() != null) {
            coloumnName = MainetConstants.WaterConnectionSearch.valueOf(gridSearchDTO.getSearchField()).getColName();
        }

        if (!StringUtils.isEmpty(searchDTO.getPropertyNo())) {
            queryString.append(" and csmr.propertyNo=:propertyNo ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsCcn())) {
            queryString.append(" and csmr.csCcn=:csCcn ");
        }
        if (searchDTO.getLocId() != null && searchDTO.getLocId() > 0) {
            queryString.append(" and csmr.loccationId=:loccationId ");
        }
        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            queryString.append(" and csmr.codDwzid1=:codDwzid1 ");
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            queryString.append(" and csmr.codDwzid2=:codDwzid2 ");
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            queryString.append(" and csmr.codDwzid3=:codDwzid3 ");
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            queryString.append(" and csmr.codDwzid4=:codDwzid4 ");
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            queryString.append(" and csmr.codDwzid5=:codDwzid5 ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsName())) {         
            queryString.append(" and csmr.csName like :csName ");
            
        }
        if (!StringUtils.isEmpty(searchDTO.getCsContactno())) {
            queryString.append(" and csmr.csContactno=:csContactno ");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsOldccn())) {
            queryString.append(" and csmr.csOldccn=:csOldccn ");
        }

        if (gridSearchDTO != null) {
            referenceNumber = "csmr.";
            GridPaginationUtility.doGridPgination(queryString, pagingDTO, gridSearchDTO, coloumnName, referenceNumber);
        }
        if (gridSearchDTO != null) {
            GridPaginationUtility.doGridOrderBy(queryString, pagingDTO,
                    MainetConstants.WaterConnectionSearch.valueOf(pagingDTO.getSidx()).getPropertyName(), "csmr.");
        }
        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", searchDTO.getOrgId());
        if (!StringUtils.isEmpty(searchDTO.getPropertyNo())) {
            query.setParameter("propertyNo", searchDTO.getPropertyNo().trim());
        }
        if (!StringUtils.isEmpty(searchDTO.getCsCcn())) {
            query.setParameter("csCcn", searchDTO.getCsCcn().trim());
        }
        if (searchDTO.getLocId() != null && searchDTO.getLocId() > 0) {
            query.setParameter("loccationId", searchDTO.getLocId());
        }
        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            query.setParameter("codDwzid1", searchDTO.getCodDwzid1());
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            query.setParameter("codDwzid2", searchDTO.getCodDwzid2());
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            query.setParameter("codDwzid3", searchDTO.getCodDwzid3());
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            query.setParameter("codDwzid4", searchDTO.getCodDwzid4());
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            query.setParameter("codDwzid5", searchDTO.getCodDwzid5());
        }
        if (!StringUtils.isEmpty(searchDTO.getCsName())) {         
            query.setParameter("csName",  "%" + searchDTO.getCsName().trim() + "%");
        }
        if (!StringUtils.isEmpty(searchDTO.getCsContactno())) {
            query.setParameter("csContactno", searchDTO.getCsContactno().trim());
        }
		
		if (!StringUtils.isEmpty(searchDTO.getCsOldccn())) {
			query.setParameter("csOldccn", searchDTO.getCsOldccn().trim());
		}

        if (pagingDTO != null) {
            GridPaginationUtility.doGridPaginationLimit(query, pagingDTO);
        }
        return query.getResultList();
    }

    @Override
    public TbKCsmrInfoMH getConnectionDetailsById(Long csIdn) {
        TbKCsmrInfoMH master = null;
        final Query query = entityManager.createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csIdn=?");
        query.setParameter(1, csIdn);
        master = (TbKCsmrInfoMH) query.getSingleResult();
        return master;
    }

    @Override
    public List<TbKCsmrInfoMH> getAllConnectionMasterByMoblieNo(String mobNo, Long orgid) {
        final Query query = entityManager.createQuery(
                "select w from TbKCsmrInfoMH w where w.orgId=:orgId and w.csIsBillingActive='A' and w.csContactno=:csContactno");
        query.setParameter("orgId", orgid);
        query.setParameter("csContactno", mobNo);
        @SuppressWarnings("unchecked")
        List<TbKCsmrInfoMH> result = query.getResultList();
        return result;
    }

    @Override
    public List<TbKCsmrInfoMH> getAllIllegalConnectionNotice(WaterDataEntrySearchDTO searchDTO) {
        StringBuilder queryString = new StringBuilder(
                "from TbKCsmrInfoMH csmr where csmr.orgId=:orgId and csmr.csIllegalNoticeNo IS NOT NULL");

        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            queryString.append(" and csmr.codDwzid1=:codDwzid1 ");
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            queryString.append(" and csmr.codDwzid2=:codDwzid2 ");
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            queryString.append(" and csmr.codDwzid3=:codDwzid3 ");
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            queryString.append(" and csmr.codDwzid4=:codDwzid4 ");
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            queryString.append(" and csmr.codDwzid5=:codDwzid5 ");
        }

        final Query query = createQuery(queryString.toString());

        query.setParameter("orgId", searchDTO.getOrgId());
        if (searchDTO.getCodDwzid1() != null && searchDTO.getCodDwzid1() > 0) {
            query.setParameter("codDwzid1", searchDTO.getCodDwzid1());
        }
        if (searchDTO.getCodDwzid2() != null && searchDTO.getCodDwzid2() > 0) {
            query.setParameter("codDwzid2", searchDTO.getCodDwzid2());
        }
        if (searchDTO.getCodDwzid3() != null && searchDTO.getCodDwzid3() > 0) {
            query.setParameter("codDwzid3", searchDTO.getCodDwzid3());
        }
        if (searchDTO.getCodDwzid4() != null && searchDTO.getCodDwzid4() > 0) {
            query.setParameter("codDwzid4", searchDTO.getCodDwzid4());
        }
        if (searchDTO.getCodDwzid5() != null && searchDTO.getCodDwzid5() > 0) {
            query.setParameter("codDwzid5", searchDTO.getCodDwzid5());
        }
        @SuppressWarnings("unchecked")
        List<TbKCsmrInfoMH> result = query.getResultList();
        return result;
    }

    @Override
    public TbKCsmrInfoMH fetchConnectionByIllegalNoticeNo(String noticeNo, Long orgId) {
        TbKCsmrInfoMH result = null;
        try {
            final Query query = entityManager.createQuery(
                    "select w from TbKCsmrInfoMH w where applicationNo IS NULL and  w.csIllegalNoticeNo=? and  w.orgId=?");
            query.setParameter(1, noticeNo);
            query.setParameter(2, orgId);
            return (TbKCsmrInfoMH) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception fetchConnectionByIllegalNoticeNo() " + e);
            return result;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository#checkValidConnectionNumber(java.lang.String, java.lang.Long)
     */
    @Override
    public Long checkValidConnectionNumber(String ccnNo, Long orgId) {
        Long count = null;

        try {
            final Query query = entityManager.createQuery(
                    "SELECT count(c) FROM TbKCsmrInfoMH c WHERE c.conActive is null and c.csCcn=? and c.orgId=?");
            query.setParameter(1, ccnNo);
            query.setParameter(2, orgId);
            count = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.NewWaterRepository#checkEntryFlag(java.lang.Long, java.lang.Long)
     */
    @Override
    public String checkEntryFlag(Long csIdn, Long orgId) {
        String flag = null;

        try {
            final Query query = entityManager.createQuery(
                    "SELECT csEntryFlag FROM TbKCsmrInfoMH c WHERE c.csIdn=? and c.orgId=?");
            query.setParameter(1, csIdn);
            query.setParameter(2, orgId);
            flag = (String) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
        return flag;
    }
    
    @Override
    public Long getconnIdByConnNo(String connNo,Long orgId) {
    	Long csidn=null;
    	try {
            final Query query = entityManager.createQuery(
                    "SELECT csIdn FROM TbKCsmrInfoMH c WHERE c.csCcn=? and c.orgId=?");
            query.setParameter(1, connNo);
            query.setParameter(2, orgId);
            csidn = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
		return csidn;
    	
    }
    
    @Override
    public Long getconnIdByOldConnNo(String oldConnNo,Long orgId) {
    	Long csidn=null;
    	try {
            final Query query = entityManager.createQuery(
                    "SELECT csIdn FROM TbKCsmrInfoMH c WHERE c.csOldccn=? and c.orgId=?");
            query.setParameter(1, oldConnNo);
            query.setParameter(2, orgId);
            csidn = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
		return csidn;
    	
    }

	@Override
	public Long getPlumIdByApplicationId(Long applicationId, Long orgId) {
		Long plumId=null;
    	try {
            final Query query = entityManager.createQuery(
                    "SELECT plumId FROM TbKCsmrInfoMH c WHERE c.applicationNo=? and c.orgId=?");
            query.setParameter(1, applicationId);
            query.setParameter(2, orgId);
            plumId = (Long) query.getSingleResult();
        } catch (Exception e) {
            logger.info("exception while checking valid connection number " + e);
        }
		return plumId;
	}
	
	@Override
	public Long getCsIdnByApplicationId(Long applicationId, Long orgId) {
        try {
            final Query query = entityManager
                    .createQuery("SELECT c.csIdn FROM TbKCsmrInfoMH c WHERE c.applicationNo=? and c.orgId=?");
            query.setParameter(1, applicationId);
            query.setParameter(2, orgId);
            final Long connectionId = (Long) query.getSingleResult();
            return connectionId;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getCsidnsByPropNo(String PropNo) {
		try {
			final Query query = entityManager.createQuery(
	                "select w.csIdn from TbKCsmrInfoMH w where w.propertyNo=?");
	        query.setParameter(1, PropNo);
	        List<Long> csIdList = query.getResultList();
	        return csIdList;
		}catch (Exception e) {
			logger.error("Exception while fetching cs id no by prop no");
			return null;
		}
		  
	}

	@Override
	public void updateMobileNumberOfConMaster(Long csIdn, Long orgId, String mobileNo,String emailId) {
		try {
            final Query query = entityManager.createQuery("UPDATE TbKCsmrInfoMH m set m.csContactno=?,m.csEmail=?"
                    + "WHERE m.csIdn = ? AND m.orgId =?");
            query.setParameter(1, mobileNo);
            query.setParameter(2, emailId);
            query.setParameter(3, csIdn);
            query.setParameter(4, orgId);
            query.executeUpdate();
        } catch (final Exception e) {
            logger.error(e);
        }
	}
	
	 	@Override
	    public Long getMeternoByConNo(final String csCcn, final Long orgid, final String active) {
	        Long meterNo = null;

	        try {
	            final Query query = entityManager.createQuery(
	                    "SELECT m.mmMtrno FROM TbKCsmrInfoMH c,TbMeterMasEntity m WHERE c.conActive is null and c.csCcn=? and c.orgId=? and c.csIsBillingActive=? and c.csIdn=m.tbCsmrInfo.csIdn");
	            query.setParameter(1, csCcn);
	            query.setParameter(2, orgid);
	            query.setParameter(3, active);
	           
	            meterNo = (Long) query.getSingleResult();
	        } catch (Exception e) {
	            logger.info("exception in getMeternoByConNo " + e);
	        }
	        return meterNo;
	    }
	 	
	 	
	 	@Override
	    public Map<Long,Date> getReceiptDet(Long apmId,Long orgId) {
	        Map<Long,Date> map=new HashMap<Long, Date>();
	        TbServiceReceiptMasEntity tbServiceReceiptMasEntity=null;
	        try {
	            final Query query = entityManager.createQuery(
	                    "SELECT r FROM TbServiceReceiptMasEntity r WHERE r.apmApplicationId=? and r.orgId=?");
	            query.setParameter(1, apmId);
	            query.setParameter(2, orgId);
	            
	            tbServiceReceiptMasEntity= (TbServiceReceiptMasEntity) query.getSingleResult();
	            if(tbServiceReceiptMasEntity !=null) {
	            	map.put(tbServiceReceiptMasEntity.getRmRcptno(), tbServiceReceiptMasEntity.getRmDate());
	            }
	            	
	        } catch (Exception e) {
	            logger.info("exception in getMeternoByConNo " + e);
	        }
	        return map;
	    }

        // #95890->Required search option with old connection number for water bill deletion
		@Override
		public TbKCsmrInfoMH fetchConnectionDet(String csCcn, String csOldccn, Long orgid, String csIsBillingActive) {
			   TbKCsmrInfoMH master = null;
		        try {
		            StringBuilder hql = new StringBuilder("SELECT c FROM TbKCsmrInfoMH c WHERE c.conActive is null ");
		            if (orgid != null && orgid != 0) {
						hql.append(" and c.orgId =:orgid ");
					}
					if (csCcn != null && !csCcn.isEmpty()) {
						hql.append(" and c.csCcn =:csCcn");
					}

					if (csOldccn != null && !csOldccn.isEmpty()) {
						hql.append(" and c.csOldccn =:csOldccn ");
					}
					if (csIsBillingActive != null && !csIsBillingActive.isEmpty()) {
						hql.append(" and c.csIsBillingActive =:csIsBillingActive ");
					}

					final Query query = createQuery(hql.toString());
					if (orgid != null && orgid != 0) {
					 query.setParameter("orgid", orgid);
					}
					if (csCcn != null && !csCcn.isEmpty()) {
					 query.setParameter("csCcn", csCcn);
					}
					if (csOldccn != null && !csOldccn.isEmpty()) {
		             query.setParameter("csOldccn", csOldccn);
					}
					if (csIsBillingActive != null && !csIsBillingActive.isEmpty()) {
		             query.setParameter("csIsBillingActive", csIsBillingActive);
					}
		            master = (TbKCsmrInfoMH) query.getSingleResult();
		        } catch (Exception e) {
		            logger.info("exception in  fetchConnectionDet " + e);
		        }
		        return master;
		}
	 	
		@Override
		public Double getSlopeValueByRoadLengthAndOrg(Long rcLength, Organisation org) {

			final Query query = entityManager.createQuery(
					"SELECT s.spValue FROM SlopeParameterEntity s " + "WHERE ? between s.spFrom AND s.spTo AND s.orgid=?");
			query.setParameter(1, rcLength * 1.0);
			query.setParameter(2, org.getOrgid());
			final Double slopeValue = (Double) query.getSingleResult();
			return slopeValue;
		}

		@Override
		public Double getConnectionSizeByDFactor(Long d_Factor, Organisation org) {
			final Query query = entityManager.createQuery(
					"SELECT c.cnsValue FROM TbWtConnectionSize c " + "WHERE ? between c.cnsFrom AND c.cnsTo AND c.orgId=?");
			query.setParameter(1, d_Factor * 1.0);
			query.setParameter(2, org.getOrgid());
			final Double ccnSize = (Double) query.getSingleResult();
			return ccnSize;
		}

		@Override
		public CsmrInfoHistEntity getCsmrHistByCsIdAndOrgId(Long csIdn, Long orgId) {
			CsmrInfoHistEntity csmrHistory = null;
			try {
				final Query query = entityManager.createQuery(
						"SELECT c FROM CsmrInfoHistEntity c WHERE c.csIdn=? AND c.orgId=?");
				query.setParameter(1, csIdn);
				query.setParameter(2, orgId);
				csmrHistory = (CsmrInfoHistEntity) query.getSingleResult();
			}catch(Exception e) {
				logger.info("Error occuring during call to getCsmrHistByCsIdAndOrgId " + e.getMessage());
			}
			
			return csmrHistory;
		}
		
		/**
		 * This method is used to initialize lazy association of object distribution
		 * @param applicationId
		 * @param orgId
		 * @return TbKCsmrInfoMH
		 */
		@Override
	    public TbKCsmrInfoMH getCsmrInfoByAppIdAndOrgId(final long applicationId, final long orgId) {
	        try {
	            final Query query = entityManager.createQuery(
	            		"SELECT c FROM TbKCsmrInfoMH c LEFT JOIN FETCH c.distribution d WHERE c.applicationNo=? and c.orgId=?", TbKCsmrInfoMH.class);
	            query.setParameter(1, applicationId);
	            query.setParameter(2, orgId);
	            final TbKCsmrInfoMH master = (TbKCsmrInfoMH) query.getSingleResult();
	            TBCsmrrCmdMas child = null;
	            try{
	            	final Query subQuery = entityManager.createQuery(
	            			"SELECT d FROM TBCsmrrCmdMas d  WHERE d.csIdn=?");
	            	subQuery.setParameter(1, master.getCsIdn());
		            child = (TBCsmrrCmdMas) subQuery.getSingleResult();

	            }catch(Exception e) {
	            	logger.error("Error while fetching associated distribution object" + e.getMessage());
	            }
	            master.setDistribution(child);
	            return master;
	        } catch (Exception e) {
	        	logger.error("Error while calling getCsmrInfoByAppIdAndOrgId " + e.getMessage());
	        }
	        return null;
	    }
		
		/**
		 * Update csmr History
		 */
		@Override
	    public void updateCsmrInfoHistory(final TbKCsmrInfoMH master) {
			try {
	            final Query query = entityManager.createQuery("UPDATE CsmrInfoHistEntity c set c.noOfFamilies = ? , c.csNooftaps = ?, c.csCcntype = ? "
	                    + " WHERE c.csIdn = ? AND c.orgId =?");
	            query.setParameter(1, master.getNoOfFamilies());
	            query.setParameter(2, master.getCsNooftaps());
	            query.setParameter(3, master.getCsCcntype());
	            query.setParameter(4, master.getCsIdn());
	            query.setParameter(5, master.getOrgId());
	            query.executeUpdate();
	        } catch (final Exception e) {
	            logger.error(e);
	        }
	    }
		
		@Override
		public TBCsmrrCmdMas getDistributionByCsIdn(final long csIdn, final long orgId) {
			 try {
		            final Query query = entityManager
		                    .createQuery("SELECT c FROM TBCsmrrCmdMas c WHERE c.csIdn=?");
		            query.setParameter(1, csIdn);
		            final TBCsmrrCmdMas distribution = (TBCsmrrCmdMas) query.getSingleResult();
		           
		            return distribution;
		        } catch (Exception e) {
		            // TODO: handle exception
		        }
		        return null;
		}

		/**
		 * Get active connection no. by csCcn, orgId and conActive flag
		 */
		@Override
		public TbKCsmrInfoMH getActiveConnectionByCsCcnAndOrgId(String csCcn, Long orgId, String activeFlag) {
			TbKCsmrInfoMH master = null;
			try {
	            final Query query = entityManager
	                .createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csCcn=? and c.orgId=? and c.conActive=?");
	            query.setParameter(1, csCcn);
	            query.setParameter(2, orgId);
	            query.setParameter(3, activeFlag);
	            master = (TbKCsmrInfoMH) query.getSingleResult();
	        } catch (Exception e) {
	        	logger.error("Active connection does not exist for csCcn: " + csCcn + e.getMessage());
        	}
			return master;
		}

		@Override
		public TbKCsmrInfoMH getConnectionByCsCcnAndOrgId(String csCcn, Long orgId) {
			TbKCsmrInfoMH master = null;
			try {
	            final Query query = entityManager
	                .createQuery("SELECT c FROM TbKCsmrInfoMH c WHERE c.csCcn=? and c.orgId=?");
	            query.setParameter(1, csCcn);
	            query.setParameter(2, orgId);
	            master = (TbKCsmrInfoMH) query.getSingleResult();
	        } catch (Exception e) {
	        	logger.error("Connection does not exist for csCcn: " + csCcn +" and orgId "+ orgId + " " + e.getMessage());
        	}
			return master;
		}

	
}