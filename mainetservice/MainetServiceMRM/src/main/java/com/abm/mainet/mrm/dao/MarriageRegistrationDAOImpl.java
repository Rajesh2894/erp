package com.abm.mainet.mrm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class MarriageRegistrationDAOImpl extends AbstractDAO<Long> implements IMarriageRegistrationDAO {

    @Override
    @Transactional
    public void updateMarriageRegData(Long marId, String status, String urlParam, Long orgId, Long applicationId, Long updatedBy,
            Date updatedDate, String actionStatus) {
        Query query = createQuery(
                buildUpdateQuery(marId, status, urlParam, orgId, applicationId, updatedBy, updatedDate, actionStatus));

        if (updatedBy != null) {
            query.setParameter("updatedBy", updatedBy);
        }
        if (updatedDate != null) {
            query.setParameter("updatedDate", updatedDate);
        }

        if (status != null) {
            query.setParameter("status", status);
        }

        if (urlParam != null) {
            query.setParameter("urlParam", urlParam);
        }

        if (applicationId != null) {
            query.setParameter("applicationId", applicationId);
        }

        if (StringUtils.isNotBlank(actionStatus)) {
            query.setParameter("actionStatus", actionStatus);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

        if (marId != null) {
            query.setParameter("marId", marId);
        } else {
            query.setParameter("applicationId", applicationId);
        }

        query.executeUpdate();

    }

    // make dynamic query
    private String buildUpdateQuery(Long marId, String status, String urlParam, Long orgId, Long applicationId, Long updatedBy,
            Date updatedDate, String actionStatus) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update Marriage set status =:status ");

        builder.append(",updatedBy=:updatedBy ");
        builder.append(",updatedDate=:updatedDate ");

        if (updatedBy != null) {
            builder.append(",updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",updatedDate=:updatedDate");
        }

        if (urlParam != null) {
            builder.append(",urlParam=:urlParam");
        }

        if (applicationId != null) {
            builder.append(" ,applicationId=:applicationId");
        }
        if (StringUtils.isNotBlank(actionStatus)) {
            builder.append(" ,actionStatus=:actionStatus");
        }

        if (orgId != null) {
            builder.append(" where orgId=:orgId AND");
        }
        if (marId != null) {
            builder.append(" marId=:marId ");
        } else {
            builder.append(" applicationId=:applicationId");
        }
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchMarriageData(Date marriageDate, Date appDate, String status, String serialNo, Long orgId,
            Long husbandId, Long wifeId) {

        final Query query = entityManager
                .createNativeQuery(buildRuntimeQuery(marriageDate, appDate, status, serialNo, orgId, husbandId, wifeId));

        if (marriageDate != null) {
            query.setParameter("marriageDate", marriageDate);
        }

        if (StringUtils.isNotEmpty(serialNo)) {
            query.setParameter("serialNo", serialNo);
        }

        if (Optional.ofNullable(husbandId).orElse(0L) != 0) {
            query.setParameter("husbandId", husbandId);
        }

        if (Optional.ofNullable(wifeId).orElse(0L) != 0) {
            query.setParameter("wifeId", wifeId);
        }

        if (appDate != null) {
            query.setParameter("appDate", appDate);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        List<Object[]> objArry = query.getResultList();

        return objArry;
    }

    public String buildRuntimeQuery(Date marriageDate, Date appDate, String status, String serialNo, Long orgId, Long husbandId,
            Long wifeId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select fn.MAR_ID, fn.APPLICATION_ID, fn.MAR_DATE, fn.SERIAL_NO, fn.hsFirst,fn.hsMiddle,fn.hsLast,fn.HUSBAND_ID, "
                        + " fn.wiFirst,fn.wiMiddle,fn.wiLast , fn.WIFE_ID ,fn.STATUS,fn.ORGID,fn.APP_DATE"
                        + " from (select mr.MAR_ID,mr.APPLICATION_ID ,mr.MAR_DATE,mr.SERIAL_NO, mr.STATUS, mr.ORGID, mr.APP_DATE,"
                        + " hs.FIRST_NAME_E AS hsFirst, hs.MIDDLE_NAME_E AS hsMiddle, hs.LAST_NAME_E AS hsLast, hs.HUSBAND_ID,"
                        + " wi.FIRST_NAME_E AS wiFirst, wi.MIDDLE_NAME_E AS wiMiddle, wi.LAST_NAME_E AS wiLast, wi.WIFE_ID"
                        + " from TB_MRM_MARRIAGE mr left join TB_MRM_HUSBAND hs on mr.MAR_ID = hs.MAR_ID  left join  TB_MRM_WIFE wi on mr.MAR_ID = wi.MAR_ID ) fn ");

        final List<String> whereClause = new ArrayList<>();

        if (marriageDate != null) {
            whereClause.add(" fn.MAR_DATE =:marriageDate ");
        }

        if (StringUtils.isNotEmpty(serialNo)) {
            whereClause.add(" fn.SERIAL_NO =:serialNo ");
        }

        if (husbandId != null && husbandId != 0) {
            whereClause.add(" fn.HUSBAND_ID =:husbandId ");
        }

        if (wifeId != null && wifeId != 0) {
            whereClause.add(" fn.WIFE_ID =:wifeId ");
        }

        if (appDate != null) {
            whereClause.add(" fn.APP_DATE =:appDate ");
        }

        if (StringUtils.isNotEmpty(status)) {
            whereClause.add(" fn.STATUS =:status ");
        }

        if (orgId != null && orgId != 0) {
            whereClause.add(" fn.ORGID =:orgId ");
        }

        StringBuilder tempBuilder = new StringBuilder();
        for (String clause : whereClause) {
            if (tempBuilder.length() == 0) {
                tempBuilder.append(" WHERE ");
            } else {
                tempBuilder.append(" AND ");
            }
            tempBuilder.append(clause);
        }
        builder.append(tempBuilder.toString());

        return builder.toString();
    }

    @Override
    @Transactional
    public void updateAppointmentData(Long marId, Long pageNo) {
        Query query = createQuery("update Appointment set pageNo= :pageNo where marId.marId= :marId");
        query.setParameter("pageNo", pageNo);
        query.setParameter("marId", marId);

        query.executeUpdate();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> searchAppointmentData(Date appointmentDate, Long orgId) {

        final Query query = entityManager.createNativeQuery(buildRuntimeQuery(appointmentDate, orgId));

        if (appointmentDate != null) {
            query.setParameter("appointmentDate", appointmentDate);
        }
        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        List<Object[]> objArry = query.getResultList();

        return objArry;
    }

    public String buildRuntimeQuery(Date appointmentDate, Long orgId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select fn.APPOINTMENT_DATE ,fn.MAR_ID,fn.APPLICATION_ID ,fn.MAR_DATE,fn.APPOINTMENT_ID, fn.ORGID"
                        + " from (select ap.APPOINTMENT_DATE ,mr.MAR_ID,mr.APPLICATION_ID ,mr.MAR_DATE,ap.APPOINTMENT_ID, mr.ORGID "
                        + " from TB_MRM_APPOINTMENT ap left join TB_MRM_MARRIAGE mr on ap.MAR_ID = mr.MAR_ID AND mr.STATUS NOT IN ('CONCLUDE') ) fn ");

        final List<String> whereClause = new ArrayList<>();

        if (appointmentDate != null) {
            whereClause.add(" fn.APPOINTMENT_DATE =:appointmentDate ");
        }

        if (orgId != null && orgId != 0) {
            whereClause.add(" fn.ORGID =:orgId ");
        }

        StringBuilder tempBuilder = new StringBuilder();
        for (String clause : whereClause) {
            if (tempBuilder.length() == 0) {
                tempBuilder.append(" WHERE ");
            } else {
                tempBuilder.append(" AND ");
            }
            tempBuilder.append(clause);
        }
        builder.append(tempBuilder.toString());

        return builder.toString();
    }

    @Override
    @Transactional
    public void updateAppointmentResc(Long appointmentId, Date appointmentDate, Date appointmentTime, Long orgId, Long updatedBy,
            Date updatedDate) {
        Query query = createQuery(
                buildUpdateQueryForAppont(appointmentId, orgId, updatedBy, updatedDate));

        if (updatedBy != null) {
            query.setParameter("updatedBy", updatedBy);
        }
        if (updatedDate != null) {
            query.setParameter("updatedDate", updatedDate);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

        if (appointmentId != null) {
            query.setParameter("appointmentId", appointmentId);
        }

        query.executeUpdate();

    }

    private String buildUpdateQueryForAppont(Long appointmentId, Long orgId, Long updatedBy,
            Date updatedDate) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update Appointment set appointmentDate =:appointmentDate , appointmentTime =:appointmentTime");

        if (updatedBy != null) {
            builder.append(",updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",updatedDate=:updatedDate");
        }

        if (orgId != null) {
            builder.append(" where orgId=:orgId AND ");
        }
        if (appointmentId != null) {
            builder.append(" appointmentId =:appointmentId ");
        }
        return builder.toString();
    }

}
