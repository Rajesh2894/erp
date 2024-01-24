package com.abm.mainet.common.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.common.domain.WorkTimeMaster;

@Repository
public class HolidayMasterDaoImpl extends AbstractDAO<Long> implements HolidayMasterDao {

    private static final Logger LOGGER = Logger.getLogger(HolidayMasterDaoImpl.class);

    @Override
    public WorkTimeMaster saveWorkTimeMaster(WorkTimeMaster workTimeEntity) {
        LOGGER.info("start the saveWorkTimeMaster()....");
        try {
            entityManager.merge(workTimeEntity);

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in saveWorkTimeMaster() ", exception);

        }
        return workTimeEntity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public WorkTimeMaster getworkTimeEntity(Long orgId) {
        LOGGER.info("Start the getworkTimeEntity");

        WorkTimeMaster entity = new WorkTimeMaster();

        final StringBuilder hql = new StringBuilder(
                "SELECT wm FROM WorkTimeMaster wm  where wm.orgId = :orgId and wrValidEndDate is null order by wm.wrId desc");
        final Query query = createQuery(hql.toString());
        query.setParameter("orgId", orgId);

        try {
        	
            entity = (WorkTimeMaster) query.getSingleResult();

        } catch (final NoResultException  exception) {
            LOGGER.error("Exception occur in  getworkTimeEntity() ", exception);

        }
        return entity;

    }

    @Override
    public boolean saveDeleteHolidayMaster(long orgId, Long hoId) {

        LOGGER.info("Start the saveDeleteHolidayMaster");

        boolean result = true;
        final StringBuilder hql = new StringBuilder(
                "update HolidayMaster set hoActive = :hoActive  where orgId = :orgId and hoId = :hoId");
        final Query query = createQuery(hql.toString());
        query.setParameter("hoActive", MainetConstants.Common_Constant.NO);
        query.setParameter("orgId", orgId);
        query.setParameter("hoId", hoId);

        try {
            query.executeUpdate();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in saveDeleteHolidayMaster() ", exception);
            result = false;

        }
        return result;

    }

	@Override
	public HolidayMaster saveHolidayDetailsList(HolidayMaster holidayMaster) {

		LOGGER.info("start the saveHolidayDetailsList()....");
		HolidayMaster master = null;
		try {
			master = entityManager.merge(holidayMaster);

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in saveHolidayDetailsList() ", exception);

		}
		return master;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<HolidayMaster> getGridData(Date yearStartDate, Date yearEndDate, long orgId) {
        LOGGER.info("Start the getGridDataHilidayMaster Entity");

        List<HolidayMaster> entity = null;
        final StringBuilder hql = new StringBuilder(
                "SELECT wm FROM HolidayMaster wm  where wm.orgId = :orgId and wm.hoActive = :hoActive and  wm.hoDate BETWEEN  :hoYearStartDate And :hoYearEndDate order by wm.hoDate asc");
        final Query query = createQuery(hql.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("hoYearStartDate", yearStartDate);
        query.setParameter("hoYearEndDate", yearEndDate);
        query.setParameter("hoActive", MainetConstants.Common_Constant.YES);

        try {
            entity = (List<HolidayMaster>) query.getResultList();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getGridDataHilidayMaster ", exception);

        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HolidayMaster> getHolidayDetailsList(Date yearStartDate, Date yearEndDate, long orgId) {
        List<HolidayMaster> entity = null;

        final StringBuilder hql = new StringBuilder(
                "SELECT wm FROM HolidayMaster wm  where wm.orgId = :orgId and wm.hoActive = :hoActive and  wm.hoDate BETWEEN  :hoYearStartDate And :hoYearEndDate order by wm.hoDate asc");
        final Query query = createQuery(hql.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("hoYearStartDate", yearStartDate);
        query.setParameter("hoYearEndDate", yearEndDate);
        query.setParameter("hoActive", MainetConstants.Common_Constant.YES);
        try {
            entity = (List<HolidayMaster>) query.getResultList();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getGridDataHilidayMaster ", exception);

        }
        return entity;
    }

    @Override
    public void deleteHolidayDetailsList(HolidayMaster holidayMasterOld, long orgId) {

        LOGGER.info("Start the deleteHolidayDetailsList");

        final StringBuilder hql = new StringBuilder(
                "Delete FROM HolidayMaster wm  where wm.orgId = :orgId and wm.hoId = :hoId");
        final Query query = createQuery(hql.toString());
        query.setParameter("orgId", orgId);
        query.setParameter("hoId", holidayMasterOld.getHoId());

        try {
            query.executeUpdate();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in deleteHolidayDetailsList() ", exception);

        }
    }

}
