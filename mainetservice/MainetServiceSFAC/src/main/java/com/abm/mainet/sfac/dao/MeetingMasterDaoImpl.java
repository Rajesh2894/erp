/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class MeetingMasterDaoImpl extends AbstractDAO<Long> implements MeetingMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MeetingMasterEntity> searchMeetingMasterData(Long meetingTypeId, String meetingNo, Long orgid,
			String orderBy,Long meetingId) {

		List<MeetingMasterEntity> MeetingMasterEntities = new ArrayList<MeetingMasterEntity>();
		try {
			StringBuilder jpaQuery = new StringBuilder(
					"SELECT cm FROM MeetingMasterEntity cm  where ");

			if(Optional.ofNullable(orgid).orElse(0L) != 0) {
				jpaQuery.append(" cm.orgId = :orgid ");
			}
			if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
				if(Optional.ofNullable(orgid).orElse(0L) != 0) {
					jpaQuery.append(" and ");
				}
				jpaQuery.append("  cm.meetingTypeId  = :meetingTypeId");
			}
			
			if(Optional.ofNullable(meetingId).orElse(0L) != 0) {
				if(Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
					jpaQuery.append(" and ");
				}
				jpaQuery.append("  cm.meetingId = :meetingId ");
			}

			if (StringUtils.isNotEmpty(meetingNo)) {
				if(Optional.ofNullable(meetingId).orElse(0L) != 0) {
					jpaQuery.append(" and ");
				}
				jpaQuery.append(" and cm.meetingNo like :meetingNo ");
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
			
			if (Optional.ofNullable(orgid).orElse(0L) != 0) {
			hqlQuery.setParameter("orgid", orgid);
			}
			if (Optional.ofNullable(meetingTypeId).orElse(0L) != 0) {
				hqlQuery.setParameter("meetingTypeId", meetingTypeId);
			}
			
			if (Optional.ofNullable(meetingId).orElse(0L) != 0) {
				hqlQuery.setParameter("meetingId", meetingId);
			}
			
			

			if (StringUtils.isNotEmpty(meetingNo)) {
				hqlQuery.setParameter("meetingNo", meetingNo);
			}

			MeetingMasterEntities = hqlQuery.getResultList();

		} catch (Exception exception) {
			throw new FrameworkException("Exception occured to Search Reord", exception);
		}
		return MeetingMasterEntities;
	}

}
