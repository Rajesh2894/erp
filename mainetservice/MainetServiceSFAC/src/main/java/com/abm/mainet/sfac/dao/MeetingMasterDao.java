/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface MeetingMasterDao {

	/**
	 * @param meetingTypeId
	 * @param meetingNo
	 * @param orgid
	 * @param orderBy
	 * @return
	 */
	List<MeetingMasterEntity> searchMeetingMasterData(Long meetingTypeId, String meetingNo, Long orgid, String orderBy,Long meetingId);

}
