package com.abm.mainet.council.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;

@Repository
public class CouncilMeetingMasterDaoImpl extends AbstractDAO<Long> implements ICouncilMeetingMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<CouncilMeetingMasterEntity> searchCouncilMeetingMasterData(Long meetingTypeId, String meetingNo,
            Date fromDate, Date toDate, Long orgid, String orderBy) {

        // D#117816 update from date and to Date
        if (fromDate != null)
            fromDate = Utility.getStartOfDay(fromDate);

        if (toDate != null)
            toDate = Utility.getEndOfDay(toDate);
        List<CouncilMeetingMasterEntity> councilMeetingMasterEntities = new ArrayList<CouncilMeetingMasterEntity>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT cm FROM CouncilMeetingMasterEntity cm  where cm.orgId = :orgid ");

            if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
                jpaQuery.append(" and cm.meetingTypeId  = :meetingTypeId");
            }

            if (StringUtils.isNotEmpty(meetingNo)) {
                jpaQuery.append(" and cm.meetingNo like :meetingNo ");
            }

            if (fromDate != null) {
                jpaQuery.append(" and cm.meetingDate >= :fromDate ");
            }
            if (toDate != null) {
                jpaQuery.append(" and cm.meetingDate <= :toDate ");
            }

            // check runtime orderBy value and based on this FORMATE the query
            if (StringUtils.isNotEmpty(orderBy)) {
                if (orderBy.equalsIgnoreCase("MEETING_DATE")) {
                    jpaQuery.append(" order by meetingDate asc");
                } else {
                    jpaQuery.append(" order by meetingId desc");
                }
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);

            if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
                hqlQuery.setParameter("meetingTypeId", meetingTypeId);
            }

            if (StringUtils.isNotEmpty(meetingNo)) {
                hqlQuery.setParameter("meetingNo", meetingNo);
            }

            if (fromDate != null) {
                hqlQuery.setParameter("fromDate", fromDate);
            }

            if (toDate != null) {
                hqlQuery.setParameter("toDate", toDate);
            }

            councilMeetingMasterEntities = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Reord", exception);
        }
        return councilMeetingMasterEntities;
    }

    @Override
    public Long getMemberCountByCondition(Long meetingId, Date fromDate, Date toDate, Long orgId, String condition) {
        Long count = 0L;
        try {
            if (fromDate != null)
                fromDate = Utility.getStartOfDay(fromDate);

            if (toDate != null)
                toDate = Utility.getEndOfDay(toDate);

            StringBuilder jpaQuery = new StringBuilder(
                    "select count(att.councilMemberId)  from CouncilMeetingMemberEntity att where att.orgId = :orgId and att.meetingId = :meetingId ");

            // ask to sir/madam for date
            if (fromDate != null) {
                jpaQuery.append(" AND att.createdDate >= :fromDate ");
            }
            if (toDate != null) {
                jpaQuery.append(" AND att.createdDate <= :toDate ");
            }

            if (condition.equalsIgnoreCase(MainetConstants.Council.Meeting.ATTENDANCE_STATUS_TRUE)) {
                jpaQuery.append(" AND att.attendanceStatus = 1");
            } else if (condition.equalsIgnoreCase(MainetConstants.Council.Meeting.ATTENDANCE_STATUS_FALSE)) {
                jpaQuery.append(" AND att.attendanceStatus = 0");
            } else if (condition.equalsIgnoreCase("ALL")) {

            }

            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("orgId", orgId);

            if (Optional.ofNullable(meetingId).orElse(0L) != 0) {
                hqlQuery.setParameter("meetingId", meetingId);
            }
            if (fromDate != null) {
                hqlQuery.setParameter("fromDate", fromDate);
            }

            if (toDate != null) {
                hqlQuery.setParameter("toDate", toDate);
            }

            count = (Long) hqlQuery.getSingleResult();

        } catch (Exception e) {
            throw new FrameworkException("Exception occured to Search Reord of Attendance ", e);
        }
        return count;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CouncilMeetingMasterEntity getMeetingDetailsByCondition(Long meetingId, Long meetingTypeId, String meetingNo,
            Long orgId) {
        CouncilMeetingMasterEntity entity = null;
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "FROM CouncilMeetingMasterEntity where meetingStatus = :meetingStatus AND orgId = :orgId");
            if (Optional.ofNullable(meetingId).orElse(0L) != 0) {
                jpaQuery.append(" AND meetingId= :meetingId");
            }

            if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
                jpaQuery.append(" AND meetingTypeId= :meetingTypeId");
            }

            if (meetingNo != null && (StringUtils.isNotEmpty(meetingNo))) {
                jpaQuery.append(" AND meetingNo like :meetingNo");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("meetingStatus", MainetConstants.Council.DB_STATUS_APPROVED);
            hqlQuery.setParameter("orgId", orgId);

            if (Optional.ofNullable(meetingId).orElse(0L) != 0) {
                hqlQuery.setParameter("meetingId", meetingId);
            }

            if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
                hqlQuery.setParameter("meetingTypeId", meetingTypeId);
            }

            if (meetingNo != null && (StringUtils.isNotEmpty(meetingNo))) {
                hqlQuery.setParameter("meetingNo", meetingNo);
            }
            entity = (CouncilMeetingMasterEntity) hqlQuery.getResultList().stream().findFirst().orElse(null);
        } catch (Exception e) {
            throw new FrameworkException("Exception occured when get meeting result by some condition ", e);
        }
        return entity;
    }

}
