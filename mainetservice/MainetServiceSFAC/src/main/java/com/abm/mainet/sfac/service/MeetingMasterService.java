/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.sfac.dto.MeetingDetailDto;
import com.abm.mainet.sfac.dto.MeetingMasterDto;

/**
 * @author pooja.maske
 *
 */
public interface MeetingMasterService {

	/**
	 * @param meetingTypeId
	 * @param meetingNumber
	 * @param fromDate
	 * @param toDate
	 * @param orgId
	 * @param orderBy
	 * @return
	 */
	List<MeetingMasterDto> searchMeetingMasterData(Long meetingTypeId, String meetingNumber,
			Long orgId, String orderBy,Long meetingId);

	/**
	 * @param masterDto
	 * @param attachments
	 * @param uploadDTO
	 * @param object
	 * @param removeYearIdList
	 */
	MeetingMasterDto saveMeetingDetails(MeetingMasterDto masterDto, List<DocumentDetailsVO> attachmentList, FileUploadDTO uploadDTO,
			Long deleteFileId, List<Long> removeYearIdList);

	/**
	 * @param meetingId
	 * @return
	 */
	MeetingMasterDto findById(Long meetingId);

	/**
	 * @param masterDto
	 * @param removedMemtDetIdsList
	 */
	void inactiveRemovedMemDetails(MeetingMasterDto masterDto, List<Long> removedMemtDetIdsList);

	/**
	 * @param masterDto
	 * @param removedMomDetIdsList
	 */
	void inactiveRemovedMomDetails(MeetingMasterDto masterDto, List<Long> removedMomDetIdsList);

	/**
	 * @param meetingTypeId
	 * @return
	 */
	List<MeetingDetailDto> getCommitteeMemDetById(Long meetingTypeId);

	/**
	 * @param masterDto
	 * @param removedAgendaDetIdsList
	 */
	void inactiveRemovedAgendaDetails(MeetingMasterDto masterDto, List<Long> removedAgendaDetIdsList);

	

}
